package com.durex.lauchat.mapper;

import com.durex.lauchat.pojo.MyFriends;
import com.durex.lauchat.pojo.vo.MyFriendVO;
import com.durex.lauchat.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface MyFriendsMapper extends MyMapper<MyFriends> {

    int batchInsert(@Param("friendsList") List<MyFriends> friendsList);

    List<MyFriendVO> queryMyFriendList(@Param("userId") String userId);
}