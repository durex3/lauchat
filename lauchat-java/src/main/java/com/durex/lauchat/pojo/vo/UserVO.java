package com.durex.lauchat.pojo.vo;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    private String id;

    private String username;

    private String faceImage;

    private String faceImageBig;

    private String nickname;

    private String qrcode;

}