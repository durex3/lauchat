package com.durex.lauchat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

@Component
public class WebSocketServer {

    private static class SingletionWebSocketServer {
        static final WebSocketServer instance = new WebSocketServer();
    }

    // 主线程组，用于接收客户端的连接，但是不做任何处理
    private EventLoopGroup masterGroup;
    // 从线程组，处理客户端的请求
    private EventLoopGroup slaveGroup;
    private ServerBootstrap serverBootstrap;
    private ChannelFuture channelFuture;

    public WebSocketServer() {
        // 主线程组，用于接收客户端的连接，但是不做任何处理
        masterGroup = new NioEventLoopGroup();
        // 从线程组，处理客户端的请求
        slaveGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(masterGroup, slaveGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WebSocketServerInitializer());
    }

    public void start(){
        channelFuture = serverBootstrap.bind(8088);
    }

    public static WebSocketServer getInstance() {
        return SingletionWebSocketServer.instance;
    }
}
