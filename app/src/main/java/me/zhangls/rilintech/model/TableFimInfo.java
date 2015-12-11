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
 * Created by YANG on 15/10/15.
 */
public class TableFimInfo implements Parcelable {


    //评定日期
    private String date;
    //评定说明
    private String instructions;
    //评定人
    private String maker;
    //功能评分
    private ArrayList<Integer> list_score;
    //记录当前操作表的ID
    private ArrayList<Integer> list_record_id;


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

    public ArrayList<Integer> getList_score() {
        return list_score;
    }

    public void setList_score(ArrayList<Integer> list_score) {
        this.list_score = list_score;
    }

    public ArrayList<Integer> getList_record_id() {
        return list_record_id;
    }

    public void setList_record_id(ArrayList<Integer> list_record_id) {
        this.list_record_id = list_record_id;
    }


    public static final Creator<TableFimInfo> CREATOR = new Creator<TableFimInfo>() {
        @Override
        public TableFimInfo createFromParcel(Parcel in) {
            TableFimInfo info = new TableFimInfo();

            info.date = in.readString();
            info.instructions = in.readString();
            info.maker = in.readString();
            info.list_score = in.readArrayList(List.class.getClassLoader());
            info.list_record_id = in.readArrayList(List.class.getClassLoader());

            return info;
        }

        @Override
        public TableFimInfo[] newArray(int size) {
            return new TableFimInfo[size];
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
        dest.writeList(list_score);
        dest.writeList(list_record_id);

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
     * 解析发出请求返回的数据
     *
     * @param
     * @return
     * @throws JSONException
     */
    public static ArrayList<TableFimInfo> parseCache(JSONObject object) throws JSONException {

        ArrayList<TableFimInfo> list_infos = new ArrayList<>();
        TableFimInfo info = null;
        ArrayList<Integer> list_scores = null;
        ArrayList<Integer> list_record_id = null;
        JSONArray jsonArray = null;

        for (int i = 1; i < 8; i++) {
            try {
                jsonArray = object.getJSONArray(i + "");
            } catch (Exception e) {

                e.printStackTrace();
                continue;
            }

            info = new TableFimInfo();
            list_scores = new ArrayList();
            list_record_id = new ArrayList<>();

            for (int j = 0; j < jsonArray.length(); j++) {

                JSONObject jsonObject = jsonArray.getJSONObject(j);

                info.setMaker(isNull(jsonObject.getString("evaluation_person")));
                info.setDate(isNull(jsonObject.getString("evaluation_time")));
                info.setInstructions(isNull(jsonObject.getString("evaluation_time_note")));

                list_record_id.add(jsonObject.getInt("id"));
                list_scores.add(jsonObject.getInt("evaluation_value"));
            }

            info.setList_record_id(list_record_id);
            info.setList_score(list_scores);

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
     * 判断是否为null值
     * @param s
     * @return
     */
    static String isNull(Object s){
        if (s.toString().equals("null")){
            return "";
        }else {
            return s.toString();
        }

    }


}
