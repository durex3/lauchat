package com.durex.lauchat.mapper;

import com.durex.lauchat.pojo.ChatMsg;
import com.durex.lauchat.utils.MyMapper;
import java.util.List;


public interface ChatMsgMapper extends MyMapper<ChatMsg> {
    void batchUpdateMsgSigned(List<String> msgIdList);
}