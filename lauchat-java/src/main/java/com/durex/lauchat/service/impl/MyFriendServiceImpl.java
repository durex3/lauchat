package com.durex.lauchat.service.impl;

import com.durex.lauchat.exception.LauChatException;
import com.durex.lauchat.mapper.MyFriendsMapper;
import com.durex.lauchat.param.MyFriendParam;
import com.durex.lauchat.pojo.MyFriends;
import com.durex.lauchat.pojo.vo.MyFriendVO;
import com.durex.lauchat.pojo.vo.UserVO;
import com.durex.lauchat.service.MyFriendService;
import com.durex.lauchat.service.UserService;
import com.durex.lauchat.utils.BeanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class MyFriendServiceImpl implements MyFriendService {

    @Autowired
    private UserService userService;
    @Autowired
    private MyFriendsMapper myFriendsMapper;

    @Override
    public UserVO preconditionSearchFriend(MyFriendParam myFriendParam) {
        BeanValidator.check(myFriendParam);
        UserVO userVO = userService.queryUserByUsername(myFriendParam.getMyFriendUsername());
        if (userVO == null) {
            // 1. 搜索的用户如果不存在
            throw new LauChatException("搜索的用户不存在");
        } else if(userVO.getId().equals(myFriendParam.getMyUserId())) {
            // 2. 搜索账号是你自己
            throw new LauChatException("不能添加自己为好友");
        }
        // 3. 搜索的朋友已经是你的好友
        Example example = new Example(MyFriends.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("myUserId", myFriendParam.getMyUserId());
        criteria.andEqualTo("myFriendUserId", userVO.getId());
        MyFriends myFriend = myFriendsMapper.selectOneByExample(example);
        if (myFriend != null) {
            throw new LauChatException("该用户已经是你的好友");
        }
        return userVO;
    }

    @Override
    public List<MyFriendVO> getMyFriendList(String userId) {
        return myFriendsMapper.queryMyFriendList(userId);
    }
}
