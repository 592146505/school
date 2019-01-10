package com.roamer.school.shiro.util;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * @author roamer
 * @version V1.0
 * @date 2019/1/7 14:32
 */
public abstract class MD5Utils {

    /** 字典表 */
    private static final String[] HEX_DIG_ITS;

    static {
        HEX_DIG_ITS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    }

    /**
     * MD5编码
     *
     * @param charset 字符集
     * @param origin  字符
     *
     * @return 编码后的16进制字符
     */
    public static String MD5Encode(Charset charset, String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (null == charset) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charset)));
            }
        } catch (Exception e) {
        }
        return resultString;
    }


    /**
     * 转为32位16进制字符串
     *
     * @param bytes 字节数组
     *
     * @return 16进制字符串
     */
    private static String byteArrayToHexString(@NotNull byte[] bytes) {
        String sb = new BigInteger(1, bytes).toString(16);
        StringBuilder resultSb = new StringBuilder(sb);
        while (resultSb.length() < 32) {
            resultSb.insert(0, "0");
        }
        return resultSb.toString();
    }
}
