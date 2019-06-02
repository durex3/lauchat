package com.durex.lauchat.pojo.vo;

import lombok.*;

import java.util.List;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupVO {
    private String id;
    private String name;
    private String adminId;
    private String icon;
    List<UserVO> userList;
}
