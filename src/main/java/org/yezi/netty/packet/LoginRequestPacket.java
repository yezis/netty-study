package org.yezi.netty.packet;


import static org.yezi.netty.packet.PacketCommand.LOGIN_REQUEST;

public class LoginRequestPacket extends Packet {

    private Integer userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }

}
