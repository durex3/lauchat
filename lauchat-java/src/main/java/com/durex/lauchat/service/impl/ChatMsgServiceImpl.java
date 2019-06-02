package com.durex.lauchat.service.impl;

import com.durex.lauchat.mapper.ChatMsgMapper;
import com.durex.lauchat.netty.ChatMsg;
import com.durex.lauchat.pojo.Users;
import com.durex.lauchat.service.ChatMsgService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class ChatMsgServiceImpl implements ChatMsgService {
    @Autowired
    private Sid sid;
    @Autowired
    private ChatMsgMapper chatMsgMapper;

    @Override
    public String saveMsg(ChatMsg chatMsg) {
        com.durex.lauchat.pojo.ChatMsg chatMsgDB = com.durex.lauchat.pojo.ChatMsg.builder()
                .id(sid.nextShort())
                .sendUserId(chatMsg.getSenderId())
                .acceptUserId(chatMsg.getReceiverId())
                .msg(chatMsg.getMsg())
                .type(chatMsg.getType())
                .signFlag(0)
                .createTime(new Date())
                .build();
        chatMsgMapper.insert(chatMsgDB);
        return chatMsgDB.getId();
    }

    @Override
    public void updateMsgSigned(List<String> msgIdList) {
        chatMsgMapper.batchUpdateMsgSigned(msgIdList);
    }

    @Override
    public List<com.durex.lauchat.pojo.ChatMsg> getUnReadMsgList(String acceptUserId) {
        Example example = new Example(com.durex.lauchat.pojo.ChatMsg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("acceptUserId", acceptUserId);
        criteria.andEqualTo("signFlag", 0);
        return chatMsgMapper.selectByExample(example);
    }
}
