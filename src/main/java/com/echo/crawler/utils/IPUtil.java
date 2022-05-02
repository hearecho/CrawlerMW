package com.echo.crawler.utils;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP相关的工具
 */
public class IPUtil {
    public static boolean ping(String ip) {
        boolean res = false;
        try {
            InetAddress host = InetAddress.getByName(ip);
            res = host.isReachable(3000);
        } catch (IOException ignored) {
            return false;
        }
        return res;
    }
}
