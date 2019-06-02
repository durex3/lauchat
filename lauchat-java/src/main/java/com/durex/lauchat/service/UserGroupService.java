package com.durex.lauchat.service;

import com.durex.lauchat.pojo.vo.UserGroupVO;

import java.util.List;

public interface UserGroupService {

    List<UserGroupVO> getUserGroupByUserId(String userId);
}
