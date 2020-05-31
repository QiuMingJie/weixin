package com.wechat.detal.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AES {
    public AES() {
    }

    public static byte[] encrypt(byte[] data, byte[] key) {
        if (key.length != 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        } else {
            try {
                SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(1, seckey);
                byte[] result = cipher.doFinal(data);
                return result;
            } catch (Exception var7) {
                throw new RuntimeException("encrypt fail!", var7);
            }
        }
    }

    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        if (key.length != 16) {
            throw new Exception("Invalid AES key length (must be 16 bytes)");
        } else {
            try {
                SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(2, seckey);
                byte[] result = cipher.doFinal(data);
                return result;
            } catch (Exception var7) {
                throw new Exception("decrypt fail!", var7);
            }
        }
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptToBase64(String data, String key) throws Exception {
        try {
            byte[] valueByte = encrypt(data.getBytes("UTF-8"), key.getBytes("UTF-8"));
            return new String(Base64.encodeBase64String(valueByte));
        } catch (UnsupportedEncodingException var3) {
            throw new Exception("encrypt fail!", var3);
        }
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptFromBase64(String data, String key) throws Exception {
        try {
            byte[] originalData = Base64.decodeBase64(data.getBytes());
            byte[] valueByte = decrypt(originalData, key.getBytes("UTF-8"));
            return new String(valueByte, "UTF-8");
        } catch (UnsupportedEncodingException var4) {
            throw new Exception("decrypt fail!", var4);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(encryptToBase64("12", "1234567812345678"));
        System.out.println(decryptFromBase64("R1Xs8Xcj0ioSykk9O2KaHw==", "1234567812345678"));

    }

    public static String encryptWithKeyBase64(String data, String key) throws Exception {
        try {
            byte[] valueByte = encrypt(data.getBytes("UTF-8"), Base64.decodeBase64(key.getBytes()));
            return new String(Base64.encodeBase64String(valueByte));
        } catch (UnsupportedEncodingException var3) {
            throw new Exception("encrypt fail!", var3);
        }
    }

    public static String decryptWithKeyBase64(String data, String key) throws Exception {
        try {
            byte[] originalData = Base64.decodeBase64(data.getBytes());
            byte[] valueByte = decrypt(originalData, Base64.decodeBase64(key.getBytes()));
            return new String(valueByte, "UTF-8");
        } catch (UnsupportedEncodingException var4) {
            throw new Exception("decrypt fail!", var4);
        }
    }

    public static byte[] genarateRandomKey() throws Exception {
        KeyGenerator keygen = null;

        try {
            keygen = KeyGenerator.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException var3) {
            throw new Exception(" genarateRandomKey fail!", var3);
        }

        SecureRandom random = new SecureRandom();
        keygen.init(random);
        Key key = keygen.generateKey();
        return key.getEncoded();
    }


}

