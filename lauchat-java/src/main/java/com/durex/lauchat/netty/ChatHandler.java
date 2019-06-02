package com.durex.lauchat.netty;

import com.beust.jcommander.internal.Lists;
import com.durex.lauchat.common.ApplicationContextHelper;
import com.durex.lauchat.enums.MsgActionEnum;
import com.durex.lauchat.param.UserGroupMsgParam;
import com.durex.lauchat.pojo.UserGroupMsgToUser;
import com.durex.lauchat.pojo.UserGroupToUser;
import com.durex.lauchat.service.ChatMsgService;
import com.durex.lauchat.service.UserGroupMsgToUserService;
import com.durex.lauchat.service.UserGroupToUserService;
import com.durex.lauchat.utils.JsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 处理文本消息
 * TextWebSocketFrame：专门处理文本的对象，frame是消息的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 用于记录和管理所有的客户端的channel
    private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel currentChannel = ctx.channel();
        // 获取客户端传输过来的消息
        String content = msg.text();
        // 1. 获取客户端发来的消息
        DataContent dataContent = JsonMapper.string2Object(content, new TypeReference<DataContent>() {});
        // 2. 判断消息类型，根据不同的类型来处理不同的业务
        Integer action = dataContent.getAction();
        if (action == MsgActionEnum.CONNECT.type) {
            //  2.1 当websocket第一次open的时候，初始化channel，把用户的userId和channel关联起来
            String senderId = dataContent.getChatMsg().getSenderId();
            UserChannelRel.put(senderId, currentChannel);
            UserChannelRel.out();
        } else if (action == MsgActionEnum.CHAT.type) {
            //  2.2 聊天类型的消息，把聊天纪录保存到数据库，同时标记消息的签收状态
            ChatMsg chatMsg = dataContent.getChatMsg();
            String receiverId = chatMsg.getReceiverId();
            chatMsg.setMsgId(ApplicationContextHelper.popBean(ChatMsgService.class).saveMsg(chatMsg));
            DataContent dataContentMsg = new DataContent();
            dataContentMsg.setAction(MsgActionEnum.CHAT.type);
            dataContentMsg.setChatMsg(chatMsg);
            Channel receiverChannel = UserChannelRel.get(receiverId);
            if (receiverChannel == null) {
                // 用户离线
            } else {
                Channel channel = users.find(receiverChannel.id());
                if (channel == null) {
                    // 用户离线
                } else {
                    receiverChannel.writeAndFlush(new TextWebSocketFrame(JsonMapper.object2String(dataContentMsg)));
                }
            }
        } else if (action == MsgActionEnum.SIGNED.type) {
            //  2.3 签收消息类型，针对具体的消息进行签收，修改数据库中对应消息的签收状态
            String msgIdStr = dataContent.getExtend();
            if (StringUtils.isEmpty(msgIdStr)) {
                return;
            }
            String[] msgIds = msgIdStr.split(",");
            List<String> msgIdList = Lists.newArrayList(msgIds);
            if (CollectionUtils.isNotEmpty(msgIdList)) {
                ApplicationContextHelper.popBean(ChatMsgService.class).updateMsgSigned(msgIdList);
            }
        } else if (action == MsgActionEnum.KEEPALIVE.type) {
            //  2.4 心跳类型的消息
        } else if (action == MsgActionEnum.GROUP_CHAT.type) {
            // 3. 群消息处理
            ChatMsg chatMsg = dataContent.getChatMsg();
            String userGroupId = chatMsg.getReceiverId();
            // 返回用户所在的用户组的用户排除当前的用户
            List<UserGroupToUser> userGroupToUserList = ApplicationContextHelper.popBean(UserGroupToUserService.class)
                    .getUserIdListExcludeCurrentUserIdByUserGroupId(userGroupId, chatMsg.getSenderId());
            if (CollectionUtils.isEmpty(userGroupToUserList)) {
                return;
            }
            UserGroupMsgParam userGroupMsgParam = UserGroupMsgParam.builder()
                    .userGroupId(userGroupId)
                    .sendUserId(chatMsg.getSenderId())
                    .msg(chatMsg.getMsg())
                    .type(chatMsg.getType())
                    .build();
            List<UserGroupMsgToUser> userGroupMsgToUserList = Lists.newArrayList();

            // 待接受消息的用户
            userGroupToUserList.stream().forEach(userGroupToUser -> {
                UserGroupMsgToUser userGroupMsgToUser = UserGroupMsgToUser.builder()
                        .acceptUserId(userGroupToUser.getUserId())
                        .signFlag(0)
                        .build();
                userGroupMsgToUserList.add(userGroupMsgToUser);
            });
            // 批量插入用户组的消息
            String userGroupMsgId = ApplicationContextHelper.popBean(UserGroupMsgToUserService.class).saveUserGroupMsgToUser(userGroupMsgParam, userGroupMsgToUserList);
            // 待签收的消息ID
            chatMsg.setMsgId(userGroupMsgId);
            DataContent dataContentMsg = new DataContent();
            dataContentMsg.setAction(MsgActionEnum.GROUP_CHAT.type);
            dataContentMsg.setChatMsg(chatMsg);
            // 推送给用户组里面的其他用户
            userGroupToUserList.stream().forEach(userGroupToUser -> {
                Channel receiverChannel = UserChannelRel.get(userGroupToUser.getUserId());
                if (receiverChannel == null) {
                    // 用户离线
                } else {
                    Channel channel = users.find(receiverChannel.id());
                    if (channel == null) {
                        // 用户离线
                    } else {
                        receiverChannel.writeAndFlush(new TextWebSocketFrame(JsonMapper.object2String(dataContentMsg)));
                    }
                }
            });
        } else if (action == MsgActionEnum.GROUP_SIGNED.type) {
            // 群消息签收
            String msgIdStr = dataContent.getExtend();
            // 签收者
            String userId = dataContent.getChatMsg().getReceiverId();
            if (StringUtils.isEmpty(msgIdStr)) {
                return;
            }
            String[] msgIds = msgIdStr.split(",");
            List<String> msgIdList = Lists.newArrayList(msgIds);
            if (CollectionUtils.isNotEmpty(msgIdList)) {
                ApplicationContextHelper.popBean(UserGroupMsgToUserService.class).updateMsgSigned(userId, msgIdList);
            }
        }
    }

    /**
     * 当客户端连接服务器之后（打开连接）
     * 获取客户端的channel，并且放到channelGroup中进行管理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当这个方法触发后，channelGroup会自动移除对应客户端的channel
        users.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       cause.printStackTrace();
       ctx.channel().close();
       users.remove(ctx.channel());
    }
}
