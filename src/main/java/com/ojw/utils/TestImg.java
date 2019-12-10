package com.ojw.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;


/**
 * Created by Administrator on 2018/5/11 0011.
 */
public class TestImg {

    public static void main(String [] args){
        String result = "";
        BufferedReader in=null;
        try {
            URL url = new URL("https://blog.csdn.net/littleatp2008/article/details/47661133");
//            System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
//            System.setProperty("http.proxyHost", "39.106.199.52");
//            System.setProperty("http.proxyPort", "80");
            // 创建代理服务器
            InetSocketAddress addr = new InetSocketAddress("127.123.114.223",9999);
            System.out.println(addr.getAddress());
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
            URLConnection conn = url.openConnection(proxy);


            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            String headerKey = "Proxy-Authorization";
//            String password="123456";
//            String headerValue = "Basic " + base64String2ByteFun(user+":"+password);
//            conn.setRequestProperty(headerKey, headerValue);
            // 建立实际的连接
            conn.connect();
            // 获取所有响应头字段
            Map<String, java.util.List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    public static byte[] base64String2ByteFun(String base64Str){
        return Base64.decodeBase64(base64Str);
    }

}
