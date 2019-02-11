package org.yezi.netty.server.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.yezi.netty.protocol.request.LoginRequestPacket;
import org.yezi.netty.protocol.response.LoginResponsePacket;
import org.yezi.netty.utils.LoginUtil;

import java.util.Date;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        System.out.println("客户端开始登陆......");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());
        if(valid(msg)){
            // 登陆成功
            loginResponsePacket.setSuccess(true);
            System.out.println(new Date() + "客户端登陆成功");
            LoginUtil.makeAsLogin(ctx.channel());
        }
        else {
            // 登陆失败
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号密码校验失败");
            System.out.println(new Date() + "客户端登陆失败");
        }

        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket){
        return true;
    }

}
