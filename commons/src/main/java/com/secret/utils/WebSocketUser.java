package com.secret.utils;


import com.secret.dto.applet.User;

import java.util.concurrent.ConcurrentHashMap;

public class WebSocketUser {

    private static ConcurrentHashMap<String, User> concurrentHashMap = new ConcurrentHashMap();

    public static ConcurrentHashMap<String, User> getConcurrentHashMap() {
        return concurrentHashMap;
    }

    public static void setConcurrentHashMap(ConcurrentHashMap<String, User> concurrentHashMap) {
        WebSocketUser.concurrentHashMap = concurrentHashMap;
    }
}
