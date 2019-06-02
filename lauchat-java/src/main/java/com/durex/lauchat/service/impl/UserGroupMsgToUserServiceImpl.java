package com.durex.lauchat.service.impl;

import com.durex.lauchat.mapper.UserGroupMsgToUserMapper;
import com.durex.lauchat.param.UserGroupMsgParam;
import com.durex.lauchat.pojo.UserGroupMsgToUser;
import com.durex.lauchat.service.UserGroupMsgService;
import com.durex.lauchat.service.UserGroupMsgToUserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserGroupMsgToUserServiceImpl implements UserGroupMsgToUserService {

    @Autowired
    private Sid sid;
    @Autowired
    private UserGroupMsgService userGroupMsgService;
    @Autowired
    private UserGroupMsgToUserMapper userGroupMsgToUserMapper;

    @Transactional
    @Override
    public String saveUserGroupMsgToUser(UserGroupMsgParam userGroupMsgParam, List<UserGroupMsgToUser> userGroupMsgToUserList) {
        String userGroupMsgId = userGroupMsgService.addUserGroupMsg(userGroupMsgParam);
        userGroupMsgToUserList.stream().forEach(userGroupMsgToUser -> {
            userGroupMsgToUser.setId(sid.nextShort());
            userGroupMsgToUser.setUserGroupMsgId(userGroupMsgId);
        });
        userGroupMsgToUserMapper.batchInsert(userGroupMsgToUserList);
        return userGroupMsgId;
    }

    @Override
    public void updateMsgSigned(String userId, List<String> msgIdList) {
        userGroupMsgToUserMapper.batchUpdateMsgSigned(userId, msgIdList);
    }
}
