package com.durex.lauchat.netty;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;


import java.util.List;

/**
 * 用于检测channel的心跳handler
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 判断evt是否是IdleStateEvent（用于触发用户事件，包含 读空闲/写空闲/读写空闲 ）
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)evt;		// 强制类型转换
            if (event.state() == IdleState.READER_IDLE) {
            } else if (event.state() == IdleState.WRITER_IDLE) {
            } else if (event.state() == IdleState.ALL_IDLE) {
                Channel channel = ctx.channel();
                // 关闭无用的channel，以防资源浪费
                channel.close();
            }
        }
    }
}
