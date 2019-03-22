package org.yezi.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

public class ServerChannelHandlerStatus implements ChannelInboundHandler {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ServerChannelHandlerStatus ： channel 已经注册到eventLoop上");
        ctx.fireChannelRegistered(); // 传递事件到下一个channelHandler
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ServerChannelHandlerStatus ： channel 从eventLoop上注销");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ServerChannelHandlerStatus ： channel 已经连接远程节点，处于活动状态，已经准备好传输数据");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ServerChannelHandlerStatus ： channel 与远程节点断开，且变为不活动状态");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("ServerChannelHandlerStatus ： channel 中读取数据");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ServerChannelHandlerStatus ： channel 中的数据读取完毕");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ServerChannelHandlerStatus ： 该channelHandler被加载到pipeline中");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ServerChannelHandlerStatus ： 该channelHandler从pipeLine中移除");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }
}
