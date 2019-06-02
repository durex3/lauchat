package com.durex.lauchat.service;

import com.durex.lauchat.param.FaceParam;
import com.durex.lauchat.param.NicknameParam;
import com.durex.lauchat.param.RegisterOrLoginParam;
import com.durex.lauchat.pojo.vo.UserVO;

public interface UserService {

    boolean userRegister(RegisterOrLoginParam registerOrLoginParam);

    UserVO queryUserFormLogin(RegisterOrLoginParam registerOrLoginParam);

    UserVO updateUserFace(FaceParam faceParam);

    UserVO updateUserNickname(NicknameParam nicknameParam);

    UserVO queryUserByUsername(String username);
}
