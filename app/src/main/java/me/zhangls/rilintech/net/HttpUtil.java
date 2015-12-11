package me.zhangls.rilintech.net;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import me.zhangls.rilintech.utils.L;

/**
 * Created by rilintech on 15/9/8.
 */
public class HttpUtil {
    private static AsyncHttpClient client =new AsyncHttpClient();    //实例话对象
    static
    {
        client.setTimeout(11000);   //设置链接超时，如果不设置，默认为10s
    }
    public static void get(String urlString,AsyncHttpResponseHandler res)    //用一个完整url获取一个string对象
    {
        client.get(urlString, res);
    }
    public static void get(String urlString,RequestParams params,AsyncHttpResponseHandler res)   //url里面带参数
    {
        client.get(urlString, params,res);
    }
    public static void get(String urlString,JsonHttpResponseHandler res)   //不带参数，获取json对象或者数组
    {
        client.get(urlString, res);
    }
    public static void get(String urlString,RequestParams params,JsonHttpResponseHandler res)   //带参数，获取json对象或者数组
    {
        client.get(urlString, params,res);
    }
    public static void get(String uString, BinaryHttpResponseHandler bHandler)   //下载数据使用，会返回byte数据
    {
        client.get(uString, bHandler);
    }
    public static AsyncHttpClient getClient()
    {
        return client;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     */
    public static int doPost(String url, String param) {

//        PrintWriter out = null;
//        BufferedReader in = null;
//        String result = "";
        int r = 0;
        HttpURLConnection conn = null;
        try {
            URL realUrl = new URL(url);
            conn = (HttpURLConnection) realUrl.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);//设置允许输出
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Fiddler");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Charset", "utf-8");
            OutputStream s = conn.getOutputStream();
            s.write(param.getBytes());
            s.close();

            r = conn.getResponseCode();
            L.d("aa","r="+r);


//            HttpClient client = new DefaultHttpClient();
//            HttpPost post = new HttpPost(url);
//           try {
//
//                StringEntity entity = new StringEntity(param);
//                post.setEntity(entity);
//                HttpResponse responString = client.execute(post);
//                r=responString.toString();
//               L.d("aa","r="+r);


//            // 打开和URL之间的连接
//            conn = (HttpURLConnection) realUrl
//                    .openConnection();
//            // 设置通用的请求属性
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type",
//                    "application/x-www-form-urlencoded");
//            conn.setRequestProperty("charset", "utf-8");
//            conn.setUseCaches(false);
//            // 发送POST请求必须设置如下两行
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            conn.setReadTimeout(MyApplication.TIMEOUT_IN_MILLIONS);
//            conn.setConnectTimeout(MyApplication.TIMEOUT_IN_MILLIONS);
//
//            if (param != null && !param.trim().equals("")) {
//                // 获取URLConnection对象对应的输出流
//                out = new PrintWriter(conn.getOutputStream());
//                // 发送请求参数
//                out.print(param);
//                // flush输出流的缓冲
//                out.flush();
//            }
//               //  定义BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//
//                r = conn.getResponseCode();
//
            } catch (Exception e) {
                e.printStackTrace();
                r = 100;
            }
             //使用finally块来关闭输出流、输入流
//            finally {
//                try {
//                    if (out != null) {
//                        out.close();
//                    }
//                    if (in != null) {
//                        in.close();
//                    }
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//                conn.disconnect();
//            }
            return r;
        }


    /**
     * 发送请求
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static int doPost2(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        int r = 0;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

            r= conn.getResponseCode();

        } catch (Exception e) {
            e.printStackTrace();
            L.d("text","MmTtableInfo.delete  失败");
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return r;
    }

    }