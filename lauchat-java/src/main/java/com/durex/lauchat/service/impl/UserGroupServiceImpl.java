package com.durex.lauchat.service.impl;

import com.durex.lauchat.mapper.UserGroupMapper;
import com.durex.lauchat.pojo.vo.UserGroupVO;
import com.durex.lauchat.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Override
    public List<UserGroupVO> getUserGroupByUserId(String userId) {
        return userGroupMapper.selectByUserId(userId);
    }
}
