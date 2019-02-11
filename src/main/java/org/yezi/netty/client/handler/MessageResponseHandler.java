package org.yezi.netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.yezi.netty.protocol.response.MessageResponsePacket;

import java.util.Date;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        System.out.println(new Date() + "客户端收到服务器消息：" + msg.getMessage());
    }
}
