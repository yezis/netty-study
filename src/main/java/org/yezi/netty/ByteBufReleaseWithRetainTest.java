package org.yezi.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.nio.charset.Charset;

public class ByteBufReleaseWithRetainTest {

    public static void main(String[] args) {

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10, 100);
        String msg = "this is a test message";
        byte[] msgBytes = msg.getBytes(Charset.forName("utf-8"));
        buffer.writeBytes(msgBytes);

        // 因为调用的方法会释放掉ByteBuf的引用，并且在调用方法之后还需要使用ByteBuf，所以调用retain()增加引用
        buffer.retain();
        String msg1 = byteBufToStringAndRelease(buffer);
        System.out.println(msg1);

        String msg2 = byteBufToString(buffer);
        System.out.println(msg2);

    }

    // ByteBuf转为可读字符串后释放
    private static String byteBufToStringAndRelease(ByteBuf bytebuf){
        String msg = bytebuf.toString(Charset.forName("utf-8"));
        bytebuf.release();
        return msg;
    }

    // ByteBuf转为可读字符串
    private static String byteBufToString(ByteBuf bytebuf){
        return bytebuf.toString(Charset.forName("utf-8"));
    }

}
