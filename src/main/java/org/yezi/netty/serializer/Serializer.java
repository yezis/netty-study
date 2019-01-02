package org.yezi.netty.serializer;

public interface Serializer {

    /**
     * json 序列化
     */
    byte JSON_SERIALIZER = 1;

    /**
     * 默认序列化为json
     */
    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换二进制
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成为 java 对象
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);

}
