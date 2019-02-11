package org.yezi.netty.server.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.yezi.netty.protocol.request.MessageRequestPacket;
import org.yezi.netty.protocol.response.MessageResponsePacket;
import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        System.out.println(new Date() + "服务器收到客户端消息 ： " + msg.getMessage());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("服务器回复【"+ msg.getMessage() +"】");
        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
