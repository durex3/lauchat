package com.durex.lauchat.mapper;

import com.durex.lauchat.pojo.UserGroupMsg;
import com.durex.lauchat.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGroupMsgMapper extends MyMapper<UserGroupMsg> {

    List<UserGroupMsg> selectUnReadMsgList(@Param("acceptUserId") String acceptUserId);
}