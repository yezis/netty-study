package org.yezi.netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.yezi.netty.protocol.request.LoginRequestPacket;
import org.yezi.netty.protocol.response.LoginResponsePacket;
import org.yezi.netty.utils.LoginUtil;

import java.util.Date;
import java.util.UUID;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(new Date() + ": 客户端开始登录");

        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUsername("yezi");
        packet.setPassword("775643");

        ctx.channel().writeAndFlush(packet);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if(msg.isSuccess()){
            LoginUtil.makeAsLogin(ctx.channel());
            System.out.println(new Date() + "客户端登陆成功");
        }
        else {
            System.out.println(new Date() + "客户端登陆失败， 原因：" + msg.getReason());
        }
    }
}
