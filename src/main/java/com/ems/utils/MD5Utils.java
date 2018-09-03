package com.ems.utils;

import com.ems.common.Global;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author litairan on 2018/7/2.
 */
public class MD5Utils {

    /**
     * 默认盐
     */
    private static final String DEFAULT_SALT = "TDMHEMS";

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 返回大写MD5
     *
     * @param origin
     * @param charsetname
     * @return
     */
    public static String MD5Encode(String origin, String salt, String charsetname)  {
        String resultString;
        if (charsetname == null || "".equals(charsetname)) {
            resultString = byteArrayToHexString(hash(origin.getBytes(), salt.getBytes(), 1));
        } else {
            try {
                resultString = byteArrayToHexString(hash(origin.getBytes(charsetname), salt.getBytes(), 1));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("编码格式不支持");
            }
        }
        return resultString.toUpperCase();
    }

    public static String MD5EncodeUtf8(String origin) {
        return MD5Encode(origin, Global.DEFAULT_MD5_SALT,"utf-8");
    }

    public static byte[] hash(byte[] bytes, byte[] salt, int hashIterations) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("不支持该算法");
        }
        if (salt != null) {
            md.reset();
            md.update(salt);
        }
        byte[] hashed = md.digest(bytes);
        int iterations = hashIterations - 1;

        for(int i = 0; i < iterations; ++i) {
            md.reset();
            hashed = md.digest(hashed);
        }
        return hashed;
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static void main(String[] args) {
        System.out.println(MD5EncodeUtf8("0000"));
    }
}
