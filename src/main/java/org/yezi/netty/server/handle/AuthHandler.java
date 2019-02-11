package org.yezi.netty.server.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.yezi.netty.utils.LoginUtil;

public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if(!LoginUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
            System.out.println("用户未登陆！！！");
            return;
        }

        System.out.println("用户已登陆，验证通过");
        ctx.pipeline().remove(this); // 验证通过后，此连接不需要再进行验证
        super.channelRead(ctx, msg);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if(LoginUtil.hasLogin(ctx.channel())){
            System.out.println("当前连接登陆验证完毕，无需再次验证，AuthHandler被移除");
        }
        else {
            System.out.println("无登录验证，强制关闭连接");
        }
    }
}
