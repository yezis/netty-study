package org.yezi.netty.protocol.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.yezi.netty.serializer.Serializer;

public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;

    public ByteBuf encode(Packet packet){
        // 1. 创建 ByteBuf 对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        // 2. 序列化 Java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

}
