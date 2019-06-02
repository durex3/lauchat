package com.durex.lauchat.service.impl;

import com.durex.lauchat.mapper.UserGroupToUserMapper;
import com.durex.lauchat.pojo.UserGroupToUser;
import com.durex.lauchat.service.UserGroupToUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import java.util.List;

@Service
public class UserGroupToUserServiceImpl implements UserGroupToUserService {

    @Autowired
    private UserGroupToUserMapper userGroupToUserMapper;

    @Override
    public List<UserGroupToUser> getUserIdListExcludeCurrentUserIdByUserGroupId(String userGroupId, String senderId) {
        Example example = new Example(UserGroupToUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userGroupId", userGroupId);
        criteria.andNotEqualTo("userId", senderId);
        List<UserGroupToUser> userGroupToUserList = userGroupToUserMapper.selectByExample(example);
        return userGroupToUserList;
    }
}
