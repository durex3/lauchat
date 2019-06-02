package com.durex.lauchat.pojo.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 好友请求发送方的信息
 */
@Getter
@Setter
public class FriendRequestVO {
	
    private String sendUserId;
    private String sendUsername;
    private String sendFaceImage;
    private String sendNickname;
    private Integer status;
    private Integer isReady;
}