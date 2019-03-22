package org.yezi.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    private static final int MAX_RETRY = 5;

    public static void main(String[] args){
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workGroup)   // 指定线程模型
                .channel(NioSocketChannel.class) // 指定IO模型 NioSocketChannel为NIO，OioSocketChannel为BIO
                .handler(new ChannelInitializer<SocketChannel>() { // 处理逻辑
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        // 指定连接数据读写逻辑
//                        socketChannel.pipeline().addLast(new ChannelHandlerStatus());
                    }
                });
        // 建立连接
        connect(bootstrap, "127.0.0.1", 8000, 5);

    }

    private static void connect(final Bootstrap bootstrap, final String host, final int port, final int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if(future.isSuccess()) {
                System.out.println("连接成功!");
            }
            else if(retry == 0){
                System.out.println("连接失败!");
            }
            else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }
}
