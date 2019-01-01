package org.yezi.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ByteBufTest {
    public static void main(String[] args) {

        // 初始化容量9， 最大容量100
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("allocator ByteBuf(9, 100)", buffer);

        // write 方法改变写指针，写完之后写指针未到capacity的时候，buffer仍然可写
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(1, 2, 3, 4)", buffer);

        // 写完 int 类型之后，写指针增加4
        buffer.writeInt(12);
        print("writeInt(12)", buffer);

        // write 方法改变写指针, 写完之后写指针等于 capacity 的时候，buffer 不可写
        buffer.writeBytes(new byte[]{5});
        print("writeBytes(5)", buffer);

        buffer.writeBytes(new byte[]{6});
        print("writeBytes(6)", buffer);

        // get 方法不改变读写指针
        System.out.println("getByte(3) return: " + buffer.getByte(3));
        System.out.println("getShort(3) return: " + buffer.getShort(3));
        System.out.println("getInt(3) return: " + buffer.getInt(3));
        print("getByte()", buffer);

        // set 方法不会改变读写指针
        System.out.println(buffer.getByte(buffer.readableBytes() + 1));
        buffer.setByte(buffer.readableBytes() + 1, 2);
        System.out.println(buffer.getByte(buffer.readableBytes() + 1));
        print("setByte()", buffer);

        // read 方法改变读指针
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);

    }

    private static void print(String action, ByteBuf byteBuf){
        System.out.println("after ===========" + action + "===========");
        System.out.println("capacity(): " + byteBuf.capacity());
        System.out.println("maxCapacity(): " + byteBuf.maxCapacity());
        System.out.println("readerIndex(): " + byteBuf.readerIndex());
        System.out.println("readableBytes(): " + byteBuf.readableBytes());
        System.out.println("isReadable(): " + byteBuf.isReadable());
        System.out.println("writerIndex(): " + byteBuf.writerIndex());
        System.out.println("writableBytes(): " + byteBuf.writableBytes());
        System.out.println("isWritable(): " + byteBuf.isWritable());
        System.out.println("maxWritable(): " + byteBuf.maxWritableBytes());
    }
}
