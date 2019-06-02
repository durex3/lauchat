package com.durex.lauchat.service;

import com.durex.lauchat.param.UserGroupMsgParam;
import com.durex.lauchat.pojo.UserGroupMsg;

import java.util.List;

public interface UserGroupMsgService {

    String addUserGroupMsg(UserGroupMsgParam userGroupMsgParam);

    List<UserGroupMsg> getUnReadMsgList(String acceptUserId);
}
