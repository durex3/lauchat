package com.durex.lauchat.service;

import com.durex.lauchat.param.UserGroupMsgParam;
import com.durex.lauchat.pojo.UserGroupMsgToUser;
import java.util.List;

public interface UserGroupMsgToUserService {

    String saveUserGroupMsgToUser(UserGroupMsgParam userGroupMsgParam, List<UserGroupMsgToUser> userGroupMsgToUserList);

    void updateMsgSigned(String userId, List<String> msgIdList);
}
