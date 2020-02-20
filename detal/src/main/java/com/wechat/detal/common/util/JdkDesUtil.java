package com.wechat.detal.common.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;

/**
 * @author QiuMingJie
 * @date 2020-02-20 22:01
 * @description 用于加密解密
 */
public class JdkDesUtil {
    /**
     * 对称加密的加密步骤
     */
    //JDK的实现
    public static String jdkDES(String content) {
        try {
            //1.生成KEY
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            DESKeySpec keySpec = new DESKeySpec("wxid_b8lq4voq6wvp22".getBytes("utf-8"));
            keyFactory.generateSecret(keySpec);
            SecretKey secretKey = keyFactory.generateSecret(keySpec);


//            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");//Key的生成器
//            keyGenerator.init(56);//指定keySize
//            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();

            //2.KEY转换
            DESKeySpec desKeySpec = new DESKeySpec(bytesKey);//实例化DESKey秘钥的相关内容
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");//实例一个秘钥工厂，指定加密方式
            Key convertSecretKey = factory.generateSecret(desKeySpec);


            //3.加密    DES/ECB/PKCS5Padding--->算法/工作方式/填充方式
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");//通过Cipher这个类进行加解密相关操作
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
            byte[] result = cipher.doFinal(content.getBytes());//输入要加密的内容
            // System.out.println("加密的结果：" + printHexString(result));
            // jdkDESRe(printHexString(result));

            return printHexString(result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    //JDK的实现
    public static String jdkDESRe(String result) {
        try {
            //1.生成KEY
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            DESKeySpec keySpec = new DESKeySpec("wxid_b8lq4voq6wvp22".getBytes("utf-8"));
            keyFactory.generateSecret(keySpec);
            SecretKey secretKey = keyFactory.generateSecret(keySpec);
            byte[] bytesKey = secretKey.getEncoded();

            //2.KEY转换
            DESKeySpec desKeySpec = new DESKeySpec(bytesKey);//实例化DESKey秘钥的相关内容
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");//实例一个秘钥工厂，指定加密方式
            Key convertSecretKey = factory.generateSecret(desKeySpec);


            //3.加密    DES/ECB/PKCS5Padding--->算法/工作方式/填充方式
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");//通过Cipher这个类进行加解密相关操作
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);

            byte[] aa = new byte[]{};
            //4.解密
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            aa = cipher.doFinal(hexStringToBytes(result));
            //System.out.println("解密结果：" + new String(aa));
            return new String(aa);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

        }
        return d;
    }


    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    public static String printHexString(byte[] b) {
        String a = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            a = a + hex;
        }
        return a;
    }

}
