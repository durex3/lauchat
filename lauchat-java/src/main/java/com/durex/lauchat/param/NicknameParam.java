package com.durex.lauchat.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NicknameParam {

    @NotBlank(message = "用户ID不可以为空")
    private String id;

    @NotBlank(message = "昵称不能为空")
    @Length(min = 1, max = 8, message = "昵称长度必须小于8位")
    private String nickname;
}
