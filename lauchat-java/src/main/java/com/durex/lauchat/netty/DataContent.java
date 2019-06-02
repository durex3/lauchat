package com.durex.lauchat.netty;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class DataContent implements Serializable {
    private static final long serialVersionUID = -4540761495059289714L;
    // 动作类型
    private Integer action;
    // 用户聊天内容
    private ChatMsg chatMsg;
    // 扩展字段
    private String extend;
}
