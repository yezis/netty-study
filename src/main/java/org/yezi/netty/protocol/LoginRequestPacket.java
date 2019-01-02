package org.yezi.netty.protocol;

import lombok.Data;
import org.yezi.netty.protocol.base.Command;
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

