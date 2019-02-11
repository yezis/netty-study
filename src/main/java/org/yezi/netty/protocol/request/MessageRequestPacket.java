package org.yezi.netty.protocol.request;

import lombok.Data;
import org.yezi.netty.protocol.Packet;
import org.yezi.netty.protocol.PacketCommand;

@Data
public class MessageRequestPacket extends Packet {

    private String message;

    public MessageRequestPacket(String message){
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return PacketCommand.MESSAGE_REQUEST;
    }

}
