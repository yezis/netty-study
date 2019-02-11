package org.yezi.netty.protocol.response;

import lombok.Data;
import org.yezi.netty.protocol.Packet;
import org.yezi.netty.protocol.PacketCommand;

@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return PacketCommand.MESSAGE_RESPONSE;
    }

}
