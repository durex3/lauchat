package com.durex.lauchat.param;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterOrLoginParam {

    /**
     * 用户名，账号，琉信号
     */
    @NotBlank(message = "用户名不可以为空")
    @Length(min = 1, max = 10, message = "用户名长度必须在1-10位之间")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不可以为空")
    @Length(min = 5, max = 10, message = "密码长度必须在5-10位之间")
    private String password;

    private String cid;

}
