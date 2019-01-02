package org.yezi.netty.serializer;

public interface SerializerAlgorithm {

    /**
     * json 序列化标示
     */
    byte JSON = 1;

    /**
     * java 序列化标示
     */
    byte JAVA = 2;

}
