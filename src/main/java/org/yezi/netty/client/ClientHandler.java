package org.yezi.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.yezi.netty.protocol.Packet;
import org.yezi.netty.protocol.PacketCodeC;
import org.yezi.netty.protocol.request.LoginRequestPacket;
import org.yezi.netty.protocol.response.LoginResponsePacket;
import org.yezi.netty.serializer.Serializer;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(new Date() + ": 客户端开始登录");

        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUsername("yezi");
        packet.setPassword("775643");

        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), packet, Serializer.JSON_SERIALIZER);
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if(packet instanceof LoginResponsePacket){
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if(loginResponsePacket.isSuccess()){
                System.out.println(new Date() + "客户端登陆成功");
            }
            else {
                System.out.println(new Date() + "客户端登陆失败， 原因：" + loginResponsePacket.getReason());
            }

        }

    }
}
