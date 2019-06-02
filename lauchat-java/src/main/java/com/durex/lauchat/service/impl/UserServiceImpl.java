package com.durex.lauchat.service.impl;

import com.durex.lauchat.common.QRCodeUtils;
import com.durex.lauchat.exception.LauChatException;
import com.durex.lauchat.exception.ParamException;
import com.durex.lauchat.mapper.UsersMapper;
import com.durex.lauchat.param.FaceParam;
import com.durex.lauchat.param.NicknameParam;
import com.durex.lauchat.param.RegisterOrLoginParam;
import com.durex.lauchat.pojo.Users;
import com.durex.lauchat.pojo.vo.UserVO;
import com.durex.lauchat.service.UserService;
import com.durex.lauchat.utils.BeanValidator;
import com.durex.lauchat.common.FastDFSClient;
import com.durex.lauchat.utils.FileUtils;
import com.durex.lauchat.utils.MD5Util;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private FastDFSClient fastDFSClient;
    @Autowired
    QRCodeUtils qrCodeUtils;
    @Autowired
    private Sid sid;
    @Value("${init-face}")
    private String initFace;
    @Value("${init-face-big}")
    private String initFaceBig;
    @Value("${qrCode-base-path}")
    private String qrCodeBasePath;
    @Value("${face-base-path}")
    private String faceBasePath;

    @Override
    public boolean userRegister(RegisterOrLoginParam registerOrLoginParam) {
        BeanValidator.check(registerOrLoginParam);
        Users user = new Users();
        user.setUsername(registerOrLoginParam.getUsername());
        int count = usersMapper.selectCount(user);
        if (count > 0) {
            throw new ParamException("用户名已存在");
        }
        user.setId(sid.nextShort());
        String qrCodePath = qrCodeBasePath + "user" + user.getId() + "qrcode.png";
        // lauchat_qrcode:[username]
        qrCodeUtils.createQRCode(qrCodePath, "lauchat_qrcode:" + registerOrLoginParam.getUsername());
        MultipartFile qrCodeFile = FileUtils.fileToMultipart(qrCodePath);
        String qrCodeUrl = "";
        try {
            qrCodeUrl = fastDFSClient.uploadQRCode(qrCodeFile);
        } catch (IOException e) {
            throw new LauChatException("用户注册失败");
        }
        user.setQrcode(qrCodeUrl);
        user.setPassword(MD5Util.encrypt(registerOrLoginParam.getPassword()));
        user.setNickname(registerOrLoginParam.getUsername());
        user.setFaceImage(initFace);
        user.setFaceImageBig(initFaceBig);
        user.setCid(registerOrLoginParam.getCid());
        int effected = usersMapper.insert(user);
        return effected == 1 ? true : false;
    }

    @Override
    public UserVO queryUserFormLogin(RegisterOrLoginParam registerOrLoginParam) {
        BeanValidator.check(registerOrLoginParam);
        Example example = new Example(Users.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", registerOrLoginParam.getUsername());
        criteria.andEqualTo("password", MD5Util.encrypt(registerOrLoginParam.getPassword()));
        Users result = usersMapper.selectOneByExample(example);
        if (result == null) {
            throw new LauChatException("用户名或密码错误");
        }
        UserVO userVO = UserVO.builder().build();
        BeanUtils.copyProperties(result, userVO);
        return userVO;
    }

    @Transactional
    @Override
    public UserVO updateUserFace(FaceParam faceParam) {
        BeanValidator.check(faceParam);
        Users before = usersMapper.selectByPrimaryKey(faceParam.getId());
        if (before == null) {
            throw new ParamException("待上传头像的用户不存在");
        }
        // 获取前端传过来的字符串
        String base64Data = faceParam.getFaceData();
        String userFacePath = faceBasePath + faceParam.getId() + "userface64.png";
        String url = null;
        try {
            FileUtils.base64ToFile(userFacePath, base64Data);
            MultipartFile faceFile = FileUtils.fileToMultipart(userFacePath);
            // 上传文件到fastdfs
            url = fastDFSClient.uploadBase64(faceFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new LauChatException("上传头像失败");
        }
        // 缩略图
        String thump = "_80x80.";
        String[] arr = url.split("\\.");
        String thumpImgUrl = arr[0] + thump + arr[1];
        Users user = Users.builder()
                        .id(faceParam.getId())
                        .faceImage(thumpImgUrl)
                        .faceImageBig(url)
                        .build();
        // 更新用户头像信息
        int effected = usersMapper.updateByPrimaryKeySelective(user);
        if (effected != 1) {
            throw new LauChatException("上传头像失败");
        }
        // 返回更新过后的用户信息
        user = usersMapper.selectByPrimaryKey(faceParam.getId());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Transactional
    @Override
    public UserVO updateUserNickname(NicknameParam nicknameParam) {
        BeanValidator.check(nicknameParam);
        Users user = Users.builder()
                        .id(nicknameParam.getId())
                        .nickname(nicknameParam.getNickname())
                        .build();
        int effected = usersMapper.updateByPrimaryKeySelective(user);
        if (effected != 1) {
            throw new LauChatException("修改昵称失败");
        }
        user = usersMapper.selectByPrimaryKey(nicknameParam.getId());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public UserVO queryUserByUsername(String username) {
        Example example = new Example(Users.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        Users user = usersMapper.selectOneByExample(example);
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
