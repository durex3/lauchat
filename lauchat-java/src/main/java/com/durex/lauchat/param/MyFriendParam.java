package com.durex.lauchat.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MyFriendParam {

    @NotBlank(message = "用户ID不可以为空")
    private String myUserId;
    @NotBlank(message = "好友琉信号不可以为空")
    private String myFriendUsername;
}
