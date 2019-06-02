package com.durex.lauchat.service;

import com.durex.lauchat.pojo.UserGroupToUser;

import java.util.List;

public interface UserGroupToUserService {

    /**
     * 返回用户所在的用户组的用户排除当前的用户
     * @param userGroupId
     * @param senderId
     * @return
     */
    List<UserGroupToUser> getUserIdListExcludeCurrentUserIdByUserGroupId(String userGroupId, String senderId);
}
