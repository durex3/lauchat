package com.durex.lauchat.service.impl;

import com.durex.lauchat.enums.MsgActionEnum;
import com.durex.lauchat.exception.LauChatException;
import com.durex.lauchat.exception.ParamException;
import com.durex.lauchat.mapper.FriendsRequestMapper;
import com.durex.lauchat.mapper.MyFriendsMapper;
import com.durex.lauchat.netty.DataContent;
import com.durex.lauchat.netty.UserChannelRel;
import com.durex.lauchat.param.FriendsRequestParam;
import com.durex.lauchat.param.MyFriendParam;
import com.durex.lauchat.pojo.FriendsRequest;
import com.durex.lauchat.pojo.MyFriends;
import com.durex.lauchat.pojo.vo.FriendRequestVO;
import com.durex.lauchat.pojo.vo.MyFriendVO;
import com.durex.lauchat.pojo.vo.UserVO;
import com.durex.lauchat.service.FriendRequestService;
import com.durex.lauchat.service.MyFriendService;
import com.durex.lauchat.service.UserService;
import com.durex.lauchat.utils.BeanValidator;
import com.durex.lauchat.utils.JsonMapper;
import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    @Autowired
    private UserService userService;
    @Autowired
    private MyFriendService myFriendService;
    @Autowired
    private FriendsRequestMapper friendsRequestMapper;
    @Autowired
    private MyFriendsMapper myFriendsMapper;
    @Autowired
    private Sid sid;

    @Override
    public void sendFriendRequest(MyFriendParam myFriendParam) {
        BeanValidator.check(myFriendParam);
        UserVO friend = userService.queryUserByUsername(myFriendParam.getMyFriendUsername());
        if (friend == null) {
            throw new LauChatException("添加的用户不存在");
        }
        Example example = new Example(FriendsRequest.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sendUserId", myFriendParam.getMyUserId());
        criteria.andEqualTo("acceptUserId", friend.getId());
        // 已经有了好友添加请求或者请求已经通过了
        int count = friendsRequestMapper.selectCountByExample(example);
        if (count > 0) {
            return;
        }
        FriendsRequest friendsRequest = FriendsRequest.builder()
                                        .id(sid.nextShort())
                                        .sendUserId(myFriendParam.getMyUserId())
                                        .acceptUserId(friend.getId())
                                        .status(0)
                                        .isReady(0)
                                        .requestDateTime(new Date())
                                        .build();
        int effected = friendsRequestMapper.insert(friendsRequest);
        if (effected != 1) {
            throw new LauChatException("添加好友失败");
        }
    }

    @Transactional
    @Override
    public List<FriendRequestVO> getFriendRequestList(String acceptUserId) {
        if (StringUtils.isBlank(acceptUserId)) {
            throw new ParamException("参数非法");
        }
        List<FriendRequestVO> friendRequestList = friendsRequestMapper.queryFriendRequestList(acceptUserId);
        if (CollectionUtils.isEmpty(friendRequestList)) {
            return Lists.newArrayList();
        }
        List<String> sendUserIdList = friendRequestList.stream()
                    .filter(friendRequestVO -> friendRequestVO.getIsReady() == 0)
                    .map(friendRequestVO -> friendRequestVO.getSendUserId())
                    .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(sendUserIdList)) {
            return friendRequestList;
        }
        int effected = friendsRequestMapper.updateReadyStateBySendUserIdList(acceptUserId, sendUserIdList);
        if (effected < 1) {
            throw new LauChatException("更新好友请求失败");
        }
        return friendRequestList;
    }

    @Override
    public int getCountNotReadyFriendRequestList(String acceptUserId) {
        if (StringUtils.isBlank(acceptUserId)) {
            throw new ParamException("参数非法");
        }
        return friendsRequestMapper.countNotReadyFriendRequestList(acceptUserId);
    }


    @Transactional
    @Override
    public List<MyFriendVO> agreeFriendRequest(FriendsRequestParam friendsRequestParam) {
        BeanValidator.check(friendsRequestParam);
        Example example = new Example(FriendsRequest.class);
        Example.Criteria criteria1 = example.createCriteria();
        criteria1.andEqualTo("sendUserId", friendsRequestParam.getSendUserId());
        criteria1.andEqualTo("acceptUserId", friendsRequestParam.getAcceptUserId());
        criteria1.andEqualTo("status", 1);
        Example.Criteria criteria2 = example.createCriteria();
        criteria2.andEqualTo("acceptUserId", friendsRequestParam.getSendUserId());
        criteria2.andEqualTo("sendUserId", friendsRequestParam.getAcceptUserId());
        criteria2.andEqualTo("status", 1);
        example.or(criteria2);
        int count = friendsRequestMapper.selectCountByExample(example);
        if (count > 0) {
            throw new LauChatException("该用户已是你的好友");
        }
        MyFriends myFriend1 = MyFriends.builder()
                            .id(sid.nextShort())
                            .myUserId(friendsRequestParam.getSendUserId())
                            .myFriendUserId(friendsRequestParam.getAcceptUserId())
                            .build();
        MyFriends myFriend2 = MyFriends.builder()
                            .id(sid.nextShort())
                            .myFriendUserId(friendsRequestParam.getSendUserId())
                            .myUserId(friendsRequestParam.getAcceptUserId())
                            .build();
        List<MyFriends> friendsList = Lists.newArrayList(myFriend1, myFriend2);
        int effected = myFriendsMapper.batchInsert(friendsList);
        if (effected < 1) {
            throw  new LauChatException("添加好友失败");
        }
        FriendsRequest friendsRequest = FriendsRequest.builder()
                .sendUserId(friendsRequestParam.getSendUserId())
                .acceptUserId(friendsRequestParam.getAcceptUserId())
                .status(1)
                .build();
        effected = friendsRequestMapper.updateFriendRequest(friendsRequestParam.getSendUserId(), friendsRequestParam.getAcceptUserId());
        if (effected < 1) {
            throw  new LauChatException("添加好友失败");
        }

        // 推送好友添加成功给对方
        Channel channel = UserChannelRel.get(friendsRequestParam.getSendUserId());
        if (channel != null) {
            DataContent dataContent = new DataContent();
            dataContent.setAction(MsgActionEnum.PULL_FRIEND.type);
            channel.writeAndFlush(new TextWebSocketFrame(JsonMapper.object2String(dataContent)));
        }
        //添加成功返回最新的通讯录列表
        return myFriendService.getMyFriendList(friendsRequestParam.getAcceptUserId());
    }
}
