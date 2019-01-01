package org.yezi.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

// 客户端业务处理器
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    // channelActive方法会在客户端连接建立成功过后被调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(new Date() + ": 客户端写出数据");

        // 1、获取数据
        ByteBuf buffer = getByteBuf(ctx);

        // 2、写数据
        ctx.channel().writeAndFlush(buffer);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": 客户端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));
    }

    // ByteBuf 是netty对二进制数据的抽象，netty中的数据都是以ByteBuf为单位的
    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {

        ByteBuf byteBuf = ctx.alloc().buffer();

        byte[] bytes = "你好， 夜子".getBytes(Charset.forName("utf-8"));

        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

}
