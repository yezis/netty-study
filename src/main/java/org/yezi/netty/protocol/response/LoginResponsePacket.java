package org.yezi.netty.protocol.response;

import lombok.Data;
import org.yezi.netty.protocol.Packet;
import org.yezi.netty.protocol.PacketCommand;

@Data
public class LoginResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return PacketCommand.LOGIN_RESPONSE;
    }
}
