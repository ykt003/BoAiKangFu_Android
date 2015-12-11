package me.zhangls.rilintech.utils;

import java.util.UUID;

/**
 * Created by rilintech on 15/8/5.
 */
public class UuidFactory {
    public static String getUuid(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

}
