package com.durex.lauchat.mapper;

import com.durex.lauchat.pojo.UserGroup;
import com.durex.lauchat.pojo.vo.UserGroupVO;
import com.durex.lauchat.utils.MyMapper;

import java.util.List;

public interface UserGroupMapper extends MyMapper<UserGroup> {

    List<UserGroupVO> selectByUserId(String userId);
}