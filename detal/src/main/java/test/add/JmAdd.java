package test.add;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 这个类作为中间传送类
 * 自己的微信idwxid_b8lq4voq6wvp22
 */
public class JmAdd {

    public static void main(String[] args) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
//        Map<String, String> parm = new ConcurrentHashMap<>();
//        parm.put("App-Token-Nursing", "51e827c9-d80e-40a1-a95a-1edc257596e7");
//        parm.put("Auth-Token-Nursing", "51e08208-d519-4732-bd53-58b3e1a796e3");
//        parm.put("Content-Type", "application/json");
//        try {
//            System.out.println(sendPost("http://171.105.47.66:9091/crNursing/api/hisHengXianExecute/getOrdersExecute", "{\n" +
//                    "    \"startDate\": \"2019-10-21\",\n" +
//                    "    \"endDate\": \"2019-11-27\",\n" +
//                    "    \"executeType\": \"\",\n" +
//                    "    \"patientId\": \"10675194\",\n" +
//                    "    \"visitId\": \"1\"\n" +
//                    "}", parm));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
        try {
            add("aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(jdkDES("a"));
        System.out.println(jdkDESRe(jdkDES("aaa")));


    }

    /**
     *
     * 这个是调用类，调用远程的java
     */
    public static String add(String messageType, String expand, String expand1, String expand2, String expand3, String expand4, String expand5, String expand6) throws IOException {

        Map<String, String> parm = new ConcurrentHashMap<>();
        parm.put("App-Token-Nursing", "51e827c9-d80e-40a1-a95a-1edc257596e7");
        parm.put("Auth-Token-Nursing", "51e08208-d519-4732-bd53-58b3e1a796e3");
        parm.put("Content-Type", "application/json");
        try {
            System.out.println(sendPost("http://localhost:8080/wechat/msg", "{\"text\":"+ "\""+jdkDES("{" +
                    "    \"messageType\": \""+messageType+"\"," +
                    "    \"expand\": \""+expand+"\"," +
                    "    \"expand1\": \""+expand1+"\"," +
                    "    \"expand2\": \""+expand2+"\"," +
                    "    \"expand3\": \""+expand3+"\"," +
                    "    \"expand4\": \""+expand4+"\"," +
                    "    \"expand5\": \""+expand5+"\"," +
                    "    \"expand6\": \""+expand6+"\"" +
                    "}")+"\"}", parm));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return messageType + expand + expand1 + expand2 + expand3 + expand4 + expand5 + expand6;
        return sendPost("http://localhost:8080/wechat/msg", "{\n" +
                "    \"startDate\": \"2019-10-21\",\n" +
                "    \"endDate\": \"2019-11-27\",\n" +
                "    \"executeType\": \"\",\n" +
                "    \"patientId\": \"10675194\",\n" +
                "    \"visitId\": \"1\"\n" +
                "}", parm);

    }


    private static String sendPost(String url, String param, Map<String, String> header) throws IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        URLConnection conn = realUrl.openConnection();
        //设置超时时间
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(15000);
        // 设置通用的请求属性
        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // 获取URLConnection对象对应的输出流
        out = new PrintWriter(conn.getOutputStream());
        // 发送请求参数
        out.print(param);
        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应
        in = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        if (out != null) {
            out.close();
        }
        if (in != null) {
            in.close();
        }
        return result;
    }



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
