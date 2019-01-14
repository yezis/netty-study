package org.yezi.netty.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.yezi.netty.serializer.JsonSerializer;
import org.yezi.netty.serializer.Serializer;
import org.yezi.netty.serializer.SerializerAlgorithm;

import static org.yezi.netty.packet.PacketCommand.LOGIN_REQUEST;

public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0X123456;

    /**
     * 解码
     * @param byteBuf
     * @return
     */
    public Packet decode(ByteBuf byteBuf){

        // 跳过魔数，不处理
        byteBuf.skipBytes(4);

        // 跳过版本号，不处理
        byteBuf.skipBytes(1);

        // 序列化算法标示
        byte serializerAlgorithm = byteBuf.readByte();

        // 请求指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> clazz = getRequestType(command);
        Serializer serializer = getSerializer(serializerAlgorithm);

        if(clazz != null && serializer != null){
            return serializer.deserializer(clazz, bytes);
        }

        return null;
    }

    public ByteBuf encode(Packet packet, byte serializerAlgorithm){

        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        Serializer serializer = getSerializer(serializerAlgorithm);
        byte[] bytes = serializer.serialize(packet);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(serializerAlgorithm);
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    private Class<? extends Packet> getRequestType(byte command){
        if(command == LOGIN_REQUEST){
            return LoginRequestPacket.class;
        }

        return null;
    }

    private Serializer getSerializer(byte serializerAlgorithm){
        if(serializerAlgorithm == SerializerAlgorithm.JSON){
            return new JsonSerializer();
        }

        return null;
    }

}
