package com.durex.lauchat.pojo.vo;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyFriendVO {

    private String friendUserId;
    private String friendUsername;
    private String friendFaceImage;
    private String friendNickname;

}
