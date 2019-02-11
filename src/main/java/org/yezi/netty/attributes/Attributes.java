package org.yezi.netty.attributes;

import io.netty.util.AttributeKey;

public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

}
