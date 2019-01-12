package org.yezi.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println("接收到消息：" + byteBuf.toString(Charset.forName("utf-8")));

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ByteBuf buffer = ctx.alloc().buffer();

        String msg = "欢迎访问我的服务器";
        byte[] bytes = msg.getBytes(Charset.forName("utf-8"));
        buffer.writeBytes(bytes);

        ctx.channel().writeAndFlush(buffer);
    }
}
