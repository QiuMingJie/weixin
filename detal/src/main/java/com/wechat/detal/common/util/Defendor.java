package com.wechat.detal.common.util;



public class Defendor {
    public Defendor() {
    }

    public static String encryptHeader(String header) throws Exception {
        return AES.encryptToBase64(header, "Pk3TM5kLy2q1dwn1");
    }

    public static String decryptHeader(String base64String) throws Exception {
        return AES.decryptFromBase64(base64String, "Pk3TM5kLy2q1dwn1");
    }

    public static String encryptContent(String content) throws Exception {
        return AES.encryptToBase64(content, "c6FIWIQ8Pe3Lz008");
    }

    public static String decryptContent(String base64String) throws Exception {
        return AES.decryptFromBase64(base64String, "c6FIWIQ8Pe3Lz008");
    }

    public static String encryptResponse(String response) throws Exception {
        return AES.encryptToBase64(response, "7iECU6H6W80t2B01");
    }

    public static String decryptResponse(String base64String) throws Exception {
        return AES.decryptFromBase64(base64String, "7iECU6H6W80t2B01");
    }

    public static String decrypt(String key, String base64String) throws Exception {
        return AES.decryptFromBase64(base64String, key);
    }

    public static String encrypt(String key, String base64String) throws Exception {
        return AES.encryptToBase64(base64String, key);
    }
}

