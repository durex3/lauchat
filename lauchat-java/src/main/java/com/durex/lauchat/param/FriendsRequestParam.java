package com.durex.lauchat.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FriendsRequestParam {

    @NotBlank(message = "发送者ID不可以为空")
    private String sendUserId;

    @NotBlank(message = "接受者ID不可以为空")
    private String acceptUserId;
}
