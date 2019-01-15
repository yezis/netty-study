package org.yezi.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.yezi.netty.protocol.Packet;
import org.yezi.netty.protocol.PacketCodeC;
import org.yezi.netty.protocol.request.LoginRequestPacket;
import org.yezi.netty.protocol.response.LoginResponsePacket;
import org.yezi.netty.serializer.Serializer;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if(packet instanceof LoginRequestPacket){
            System.out.println("客户端开始登陆......");
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());
            if(valid(loginRequestPacket)){
                // 登陆成功
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + "客户端登陆成功");
            }
            else {
                // 登陆失败
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("账号密码校验失败");
                System.out.println(new Date() + "客户端登陆失败");
            }

            // 编码
            ByteBuf responseBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket, Serializer.JSON_SERIALIZER);
            ctx.channel().writeAndFlush(responseBuf);
        }

    }

    private boolean valid(LoginRequestPacket loginRequestPacket){
        return true;
    }

}
