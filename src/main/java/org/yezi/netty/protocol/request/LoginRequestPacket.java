package org.yezi.netty.protocol.request;

import lombok.Data;
import org.yezi.netty.protocol.command.Command;
import org.yezi.netty.protocol.base.Packet;

@Data
public class LoginRequestPacket extends Packet {

    private Integer userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {

        return Command.LOGIN_REQUEST;
    }

}

