package com.durex.lauchat.param;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupMsgParam {

    @NotBlank(message = "用户组ID不可以为空")
    private String userGroupId;
    @NotBlank(message = "发送者ID不可以为空")
    private String sendUserId;
    @NotBlank(message = "消息不可以为空")
    private String msg;
    @NotBlank(message = "消息类型不可以为空")
    private String type;
}
