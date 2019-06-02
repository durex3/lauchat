package com.durex.lauchat.service;

import com.durex.lauchat.param.MyFriendParam;
import com.durex.lauchat.pojo.vo.MyFriendVO;
import com.durex.lauchat.pojo.vo.UserVO;

import java.util.List;

public interface MyFriendService {
    UserVO preconditionSearchFriend(MyFriendParam myFriendParam);

    List<MyFriendVO> getMyFriendList(String userId);
}
