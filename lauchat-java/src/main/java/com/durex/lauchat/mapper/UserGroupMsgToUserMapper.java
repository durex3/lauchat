package com.durex.lauchat.mapper;

import com.durex.lauchat.pojo.UserGroupMsgToUser;
import com.durex.lauchat.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserGroupMsgToUserMapper extends MyMapper<UserGroupMsgToUser> {

    void batchInsert(@Param("userGroupMsgToUserList") List<UserGroupMsgToUser> userGroupMsgToUserList);

    void batchUpdateMsgSigned(@Param("userId") String userId, @Param("msgIdList") List<String> msgIdList);
}