package org.yezi.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {
    public static void main(String[] args) {
        // 监听端口，accept新连接的线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 处理每一条连接的数据读写的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        // 服务器引导类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup) // 配置引导类的两大线程组
                .channel(NioServerSocketChannel.class) // 指定服务器的IO模型， NioServerSocketChannel为NIO，OioServerSocketChannel为BIO
                .childHandler(new ChannelInitializer<NioSocketChannel>() { // 定义后续每条连接的数据读写，业务处理逻辑
                      protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new FirstServerHandler());
                      }
                });

        bind(serverBootstrap, 8000);
    }

    // 自动绑定递增端口
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("端口[" + port + "]绑定成功");
                }
                else {
                    System.out.println("端口[" + port + "]绑定失败");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}
