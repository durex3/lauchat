package com.durex.lauchat.service;

import com.durex.lauchat.netty.ChatMsg;
import java.util.List;

public interface ChatMsgService {
    String saveMsg(ChatMsg chatMsg);

    void updateMsgSigned(List<String> msgIdList);

    List<com.durex.lauchat.pojo.ChatMsg> getUnReadMsgList(String acceptUserId);
}
