package com.durex.lauchat.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FaceParam {

    @NotBlank(message = "用户ID不可以为空")
    private String id;

    @NotBlank(message = "用户头像不能为空")
    private String faceData;
}
