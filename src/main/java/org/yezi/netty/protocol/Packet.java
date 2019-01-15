package org.yezi.netty.protocol;

import lombok.Data;

@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 协议指令
     */
    public abstract Byte getCommand();

}
