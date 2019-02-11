package org.yezi.netty.utils;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import org.yezi.netty.attributes.Attributes;

public class LoginUtil {

    public static void makeAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel){
        Attribute<Boolean> loginArr = channel.attr(Attributes.LOGIN);

        return loginArr.get() != null;
    }

}
