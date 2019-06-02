package com.durex.lauchat.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // WebSocket基于http协议，所以要有http编解码器
        pipeline.addLast(new HttpServerCodec());
        // 对大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
        // ========================以上用于支持http协议===========================

        pipeline.addLast(new IdleStateHandler(20, 40, 60));
        // 自定义的空闲状态检测
        pipeline.addLast(new HeartBeatHandler());

        /**
         *  websocket服务器处理的协议，用于指定给客户端连接访问的路由
         *  会处理握手动作handshaking(Close, Ping, Pong) Ping + Pong = 心跳
         *  对于websocket来说，都是以frames进行传输的，不同的数据类型对应的frames也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // 自定义
        pipeline.addLast(new ChatHandler());
    }
}
