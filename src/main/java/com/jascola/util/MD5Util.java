package com.jascola.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class MD5Util {
    private static Logger logger = LoggerFactory.getLogger(MD5Util.class);
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    public MD5Util() {
    }

    private static String asHex(byte[] buf) {
        char[] chars = new char[2 * buf.length];

        for (int i = 0; i < buf.length; ++i) {
            chars[2 * i] = HEX_CHARS[(buf[i] & 240) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[buf[i] & 15];
        }

        return new String(chars);
    }

    public static String getMD5(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes("UTF-8"));
            byte[] messageDigest = digest.digest();
            return asHex(messageDigest);
        } catch (Exception var3) {
            logger.error("Exception", var3);
            return "";
        }
    }
}