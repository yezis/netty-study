package org.yezi.netty.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

public class NettyClient {

    private static final int MAX_RETRY = 3;

    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                });
        connect(bootstrap, "127.0.0.1", 8000,    MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry){
        bootstrap.connect(host, port).addListener(future -> {
            if(future.isSuccess()){
                System.out.printf("连接成功");
            } else if(retry == 0){
                System.out.println("连接失败，停止重试");
            } else {
                int order = MAX_RETRY - retry;
                int wait = 1 << order;
                System.out.println("重连。。。。");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), wait, TimeUnit.SECONDS);
            }
        });
    }

}
