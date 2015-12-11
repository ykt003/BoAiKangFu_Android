package me.zhangls.rilintech.model;

import android.os.Parcel;
import android.os.Parcelable;

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
import java.util.List;

/**
 * Created by YANG on 15/9/24.
 */
public class TableRomInfo implements Parcelable {


    //评定日期
    private String date;
    //评定说明
    private String instructions;
    //评定人
    private String maker;
    //左侧主动分数
    private String left_init_score;
    //左被动分数
    private String left_passive_score;
    //右侧主动分数
    private String right_init_score;
    //右侧被动分数
    private String right_passive_score;
    //记录当前操作表的ID
    private int record_id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getLeft_init_score() {
        return left_init_score;
    }

    public void setLeft_init_score(String left_init_score) {
        this.left_init_score = left_init_score;
    }

    public String getLeft_passive_score() {
        return left_passive_score;
    }

    public void setLeft_passive_score(String left_passive_score) {
        this.left_passive_score = left_passive_score;
    }

    public String getRight_init_score() {
        return right_init_score;
    }

    public void setRight_init_score(String right_init_score) {
        this.right_init_score = right_init_score;
    }

    public String getRight_passive_score() {
        return right_passive_score;
    }

    public void setRight_passive_score(String right_passive_score) {
        this.right_passive_score = right_passive_score;
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public static final Creator<TableRomInfo> CREATOR = new Creator<TableRomInfo>() {
        @Override
        public TableRomInfo createFromParcel(Parcel in) {

            TableRomInfo info = new TableRomInfo();

            info.date = in.readString();
            info.instructions = in.readString();
            info.maker = in.readString();
            info.left_init_score = in.readString();
            info.left_passive_score = in.readString();
            info.right_init_score = in.readString();
            info.right_passive_score = in.readString();
            info.record_id = in.readInt();

            return info;
        }

        @Override
        public TableRomInfo[] newArray(int size) {
            return new TableRomInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(date);
        dest.writeString(instructions);
        dest.writeString(maker);
        dest.writeString(left_init_score);
        dest.writeString(left_passive_score);
        dest.writeString(right_init_score);
        dest.writeString(right_passive_score);
        dest.writeInt(record_id);

    }

    /**
     * 解析发出请求返回的数据
     * @param jsonArray
     * @return list_infos
     * @throws JSONException
     */
    public static ArrayList<TableRomInfo> parseCache(JSONArray jsonArray) throws JSONException {

        ArrayList<TableRomInfo> list_infos = new ArrayList<>();
        TableRomInfo info = null;
        JSONObject jsonObject = null;

        for (int j = 0; j < jsonArray.length(); j++) {

            jsonObject = jsonArray.getJSONObject(j);
            info = new TableRomInfo();

            info.setMaker(jsonObject.getString("evaluation_person"));
            info.setDate(jsonObject.getString("evaluation_time"));
            info.setInstructions(jsonObject.getString("evaluation_time_note"));
            info.setRecord_id(jsonObject.getInt("id"));
            info.setLeft_init_score(jsonObject.getString("left_zd"));
            info.setLeft_passive_score(jsonObject.getString("left_bd"));
            info.setRight_init_score(jsonObject.getString("right_zd"));
            info.setRight_passive_score(jsonObject.getString("right_bd"));

            list_infos.add(info);

        }
        return list_infos;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static int doPost(String url, String param) {

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        int r = 0;
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

            r = conn.getResponseCode();

        } catch (Exception e) {
            e.printStackTrace();
            r = 100;
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
        return r;
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


}
