package com.ems.utils;

import com.ems.common.Global;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 密码工具类
 *
 * @author litairan on 2018/7/31.
 */
public class PasswordUtils {

    /**
     * 指定散列算法为MD5,还有别的算法如：SHA256、SHA1、SHA512
     */
    private static String algorithmName = "md5";

    /**
     * 散列迭代次数 md5(md5(pwd)): new Md5Hash(pwd, salt, 2).toString()
     */
    private static int hashIterations = 1;

    /**
     * 加密：输入明文得到密文
     *
     * @param pwd
     * @param salt
     * @return
     */
    public static String encodePassword(String pwd, String salt) {
        return new SimpleHash(algorithmName, pwd, ByteSource.Util.bytes(salt), hashIterations).toHex();
    }

    /**
     * 校验密码
     *
     * @param targetPassword 比较的对象
     * @param pwd            未加密的密码
     * @param salt           盐
     * @return
     */
    public static boolean validatePassword(String targetPassword, String pwd, String salt) {
        String newPassword = encodePassword(pwd, salt);
        if (newPassword.equals(targetPassword)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(encodePassword("0000", Global.DEFAULT_MD5_SALT));
    }
}
