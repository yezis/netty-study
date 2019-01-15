package org.yezi.netty.protocol.request;


import lombok.Data;
import org.yezi.netty.protocol.Packet;
import org.yezi.netty.protocol.PacketCommand;

@Data
public class LoginRequestPacket extends Packet {

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return PacketCommand.LOGIN_REQUEST;
    }

}
