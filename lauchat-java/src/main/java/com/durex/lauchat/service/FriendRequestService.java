package com.durex.lauchat.service;

import com.durex.lauchat.param.FriendsRequestParam;
import com.durex.lauchat.param.MyFriendParam;
import com.durex.lauchat.pojo.vo.FriendRequestVO;
import com.durex.lauchat.pojo.vo.MyFriendVO;

import java.util.List;

public interface FriendRequestService {

    void sendFriendRequest(MyFriendParam myFriendParam);

    List<FriendRequestVO> getFriendRequestList(String acceptUserId);

    int getCountNotReadyFriendRequestList(String acceptUserId);

    List<MyFriendVO> agreeFriendRequest(FriendsRequestParam friendsRequestParam);
}
