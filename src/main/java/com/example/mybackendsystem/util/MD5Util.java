package com.example.mybackendsystem.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class MD5Util {

    /**
     * 使用MD5算法加密字符串
     *
     * @param data 待加密的字符串
     * @return MD5加密后的十六进制字符串
     */
    public static String encrypt(String data) {
        try {
            // 获取MD5算法的消息摘要对象
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 使用UTF-8编码将字符串转换为字节数组
            byte[] inputBytes = data.getBytes(StandardCharsets.UTF_8);

            // 更新消息摘要对象
            md.update(inputBytes);

            // 计算最终的消息摘要
            byte[] digest = md.digest();

            // 将字节数组转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

            // 返回十六进制字符串形式的MD5摘要
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    public static void main(String[] args) {
        String data = "123456";
        String encryptedData = encrypt(data);
        System.out.println("MD5 encrypted data: " + encryptedData);
        String encryptedData1 = encrypt(data);
        System.out.println("MD5 encrypted data: " + encryptedData1);
    }
}
