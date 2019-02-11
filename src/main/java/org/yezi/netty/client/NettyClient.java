package org.yezi.netty.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.yezi.netty.client.handler.LoginResponseHandler;
import org.yezi.netty.client.handler.MessageResponseHandler;
import org.yezi.netty.protocol.codec.PacketDecoder;
import org.yezi.netty.protocol.codec.PacketEncoder;
import org.yezi.netty.protocol.codec.Spliter;
import org.yezi.netty.protocol.request.MessageRequestPacket;
import org.yezi.netty.utils.LoginUtil;

import java.util.Date;
import java.util.Scanner;
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
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        connect(bootstrap, "127.0.0.1", 8000,    MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry){
        bootstrap.connect(host, port).addListener(future -> {
            if(future.isSuccess()){
                System.out.println(new Date() + "连接成功, 启动控制台线程......");
                Channel channel = ((ChannelFuture)future).channel();
                startConsoleThread(channel);
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

    public static void startConsoleThread(Channel channel){
        new Thread(() -> {
            while(!Thread.interrupted()){
                System.out.println("输入消息发送至服务端：");
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();

                channel.writeAndFlush(new MessageRequestPacket(line));
            }
        }).start();

    }

}
