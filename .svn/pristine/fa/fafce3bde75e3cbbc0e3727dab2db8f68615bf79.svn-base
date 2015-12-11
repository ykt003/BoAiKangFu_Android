package me.zhangls.rilintech.model;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import me.zhangls.rilintech.activity.LoginActivity;
import me.zhangls.rilintech.manager.MenuLibManager;
import me.zhangls.rilintech.utils.L;

/**
 * Created by YANG on 15/11/10.
 */
public class MenuLibModel {


    private int _id;
    //叙述
    private String z_name;
    //ID
    private String z_id;
    //父类ID
    private String z_menu_lib_id;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getZ_name() {
        return z_name;
    }

    public void setZ_name(String z_name) {
        this.z_name = z_name;
    }

    public String getZ_id() {
        return z_id;
    }

    public void setZ_id(String z_id) {
        this.z_id = z_id;
    }

    public String getZ_menu_lib_id() {
        return z_menu_lib_id;
    }

    public void setZ_menu_lib_id(String z_menu_lib_id) {
        this.z_menu_lib_id = z_menu_lib_id;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static String getData(String url, String param) {

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        HttpURLConnection conn = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = (HttpURLConnection) realUrl
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
        } catch (Exception e) {
            e.printStackTrace();
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
            conn.disconnect();
        }
        return result;
    }
    /**
     * 保存到数据表
     *
     * @param
     * @return
     * @throws JSONException
     */
    public static void saveToSql(JSONArray jsonArray,Context context) throws JSONException {

        MenuLibManager manager = new MenuLibManager(context);
        manager.openDataBase();

        MenuLibModel model = null;
        JSONObject jsonObject = null;

        for (int i=0;i<jsonArray.length();i++){

            model = new MenuLibModel();
            jsonObject = jsonArray.getJSONObject(i).getJSONObject("parent");
            model.setZ_id(jsonObject.getString("id"));
            model.setZ_name(jsonObject.getString("name"));
            //model.setZ_menu_lib_id(jsonObject.getString("menu_lib_id"));
            model.setZ_menu_lib_id("0");

            manager.insertData(model);

            JSONArray array = jsonArray.getJSONObject(i).getJSONArray("children");
            if (array != null) {
                for (int j = 0; j < array.length(); j++) {
                    model = new MenuLibModel();

                    JSONObject object = array.getJSONObject(j);
                    model.setZ_id(object.getString("id"));
                    model.setZ_name(object.getString("name"));
                    model.setZ_menu_lib_id(jsonObject.getString("id"));
                    manager.insertData(model);
                }
            }
        }

        manager.closeDataBase();

    }

}
