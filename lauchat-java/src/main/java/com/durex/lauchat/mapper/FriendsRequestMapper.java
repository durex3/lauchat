package com.durex.lauchat.mapper;

import com.durex.lauchat.pojo.FriendsRequest;
import com.durex.lauchat.pojo.vo.FriendRequestVO;
import com.durex.lauchat.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendsRequestMapper extends MyMapper<FriendsRequest> {

    List<FriendRequestVO> queryFriendRequestList(@Param("acceptUserId") String acceptUserId);

    // 统计最新十条请求记录里面未读的记录
    int countNotReadyFriendRequestList(@Param("acceptUserId") String acceptUserId);

    /**
     * 更新用户未读好友添加请求的状态
     * @param acceptUserId
     * @param sendUserIdList
     * @return
     */
    int updateReadyStateBySendUserIdList(@Param("acceptUserId") String acceptUserId, @Param("sendUserIdList") List<String> sendUserIdList);

    int updateFriendRequest(@Param("sendUserId") String sendUserId, @Param("acceptUserId") String acceptUserId);
}