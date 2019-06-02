package com.durex.lauchat.netty;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ChatMsg implements Serializable {
    private static final long serialVersionUID = 5363648165272544540L;
    // 发送者id
    private String senderId;
    // 接收者
    private String receiverId;
    // 消息
    private String msg;
    private String type;
    // 用于签收
    private String msgId;
}
