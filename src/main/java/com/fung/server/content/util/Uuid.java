package com.fung.server.content.util;

import java.util.UUID;

/**
 * @author skytrc@163.com
 * @date 2020/6/8 15:44
 */
public class Uuid {
    public static String createUuid() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
