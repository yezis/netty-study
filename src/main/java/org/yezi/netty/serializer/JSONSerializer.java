package org.yezi.netty.serializer;

import com.alibaba.fastjson.JSON;

/**
 * json 序列化
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlogrithm() {

        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {

        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {

        return JSON.parseObject(bytes, clazz);
    }

}
