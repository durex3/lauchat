package com.durex.lauchat.service.impl;

import com.durex.lauchat.mapper.UserGroupMsgMapper;
import com.durex.lauchat.param.UserGroupMsgParam;
import com.durex.lauchat.pojo.UserGroupMsg;
import com.durex.lauchat.service.UserGroupMsgService;
import com.durex.lauchat.utils.BeanValidator;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserGroupMsgServiceImpl implements UserGroupMsgService {

    @Autowired
    private Sid sid;
    @Autowired
    private UserGroupMsgMapper userGroupMsgMapper;

    @Override
    public String addUserGroupMsg(UserGroupMsgParam userGroupMsgParam) {
        BeanValidator.check(userGroupMsgParam);
        UserGroupMsg userGroupMsg = UserGroupMsg.builder()
                .id(sid.nextShort())
                .userGroupId(userGroupMsgParam.getUserGroupId())
                .sendUserId(userGroupMsgParam.getSendUserId())
                .msg(userGroupMsgParam.getMsg())
                .type(userGroupMsgParam.getType())
                .createTime(new Date())
                .build();
        userGroupMsgMapper.insert(userGroupMsg);
        return userGroupMsg.getId();
    }

    @Override
    public List<UserGroupMsg> getUnReadMsgList(String acceptUserId) {
        return userGroupMsgMapper.selectUnReadMsgList(acceptUserId);
    }
}
