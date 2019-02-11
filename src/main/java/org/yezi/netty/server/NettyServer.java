package org.yezi.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.yezi.netty.protocol.codec.PacketDecoder;
import org.yezi.netty.protocol.codec.PacketEncoder;
import org.yezi.netty.protocol.codec.Spliter;
import org.yezi.netty.server.handle.AuthHandler;
import org.yezi.netty.server.handle.LoginRequestHandler;
import org.yezi.netty.server.handle.MessageRequestHandler;

public class NettyServer {
    public static void main(String[] args) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boosGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // inBound 处理读数据的逻辑链
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginRequestHandler());

                        ch.pipeline().addLast(new AuthHandler());
                        ch.pipeline().addLast(new MessageRequestHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });

        bind(serverBootstrap, 8000);

    }

    private static void bind(ServerBootstrap serverBootstrap, int port){
        serverBootstrap.bind(port).addListener((ChannelFutureListener) future -> {
            if(future.isSuccess()){
                System.out.println("端口["+ port +"]绑定成功");
            }
            else {
                System.out.println("端口["+ port +"]绑定失败");
                bind(serverBootstrap, port + 1);
            }
        });
    }

}
