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

import me.zhangls.rilintech.application.MyApplication;
import me.zhangls.rilintech.utils.L;

/**
 * Created by YANG on 15/10/12.
 */
public class TableGmfmInfo implements Parcelable {

    //评定日期
    private String date;
    //评定说明
    private String instructions;
    //评定人
    private String maker;
    //分数的集合
    private ArrayList<Integer> list_scores;
    //题目集合
    private ArrayList<String> list_titles;
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

    public ArrayList<Integer> getList_scores() {
        return list_scores;
    }

    public void setList_scores(ArrayList<Integer> list_scores) {
        this.list_scores = list_scores;
    }

    public ArrayList<String> getList_titles() {
        return list_titles;
    }

    public void setList_titles(ArrayList<String> list_titles) {
        this.list_titles = list_titles;
    }

    public ArrayList<Integer> getList_record_id() {
        return list_record_id;
    }

    public void setList_record_id(ArrayList<Integer> list_record_id) {
        this.list_record_id = list_record_id;
    }

    public static final Creator<TableGmfmInfo> CREATOR = new Creator<TableGmfmInfo>() {
        @Override
        public TableGmfmInfo createFromParcel(Parcel in) {
            TableGmfmInfo info = new TableGmfmInfo();

            info.date = in.readString();
            info.instructions = in.readString();
            info.maker = in.readString();
            info.list_scores = in.readArrayList(List.class.getClassLoader());
            info.list_titles = in.readArrayList(List.class.getClassLoader());
            info.list_record_id = in.readArrayList(List.class.getClassLoader());

            return info;
        }

        @Override
        public TableGmfmInfo[] newArray(int size) {
            return new TableGmfmInfo[size];
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
        dest.writeList(list_scores);
        dest.writeList(list_titles);
        dest.writeList(list_record_id);

    }


    /**
     * 解析发出请求返回的数据
     *
     * @param
     * @return
     * @throws JSONException
     */
    public static ArrayList<TableGmfmInfo> parseCache(JSONObject object) throws JSONException {

        ArrayList<TableGmfmInfo> list_infos = new ArrayList<>();
        TableGmfmInfo info = null;
        ArrayList<Integer> list_scores = null;
        ArrayList<String> list_titles = null;
        ArrayList<Integer> list_record_id = null;
        JSONArray jsonArray = null;

        for (int i = 1; i < 8; i++) {
            try {
                jsonArray = object.getJSONArray(i + "");
            } catch (Exception e) {

                e.printStackTrace();
                continue;
            }

            info = new TableGmfmInfo();
            list_scores = new ArrayList();
            list_titles = new ArrayList<>();
            list_record_id = new ArrayList<>();

            for (int j = 0; j < jsonArray.length(); j++) {

                JSONObject jsonObject = jsonArray.getJSONObject(j);

                info.setMaker(jsonObject.getString("evaluation_person"));
                info.setDate(jsonObject.getString("evaluation_time"));
                info.setInstructions(jsonObject.getString("evaluation_time_note"));

                list_record_id.add(jsonObject.getInt("id"));
                list_titles.add(jsonObject.getString("evaluation_content"));
                list_scores.add(jsonObject.getInt("evaluation_value"));
            }

            /*int score = list_scores.get(89);
            list_scores.remove(89);
            list_scores.add(17,score);*/



            info.setList_record_id(list_record_id);
            info.setList_titles(list_titles);
            info.setList_scores(list_scores);

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
            conn.setReadTimeout(MyApplication.TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(MyApplication.TIMEOUT_IN_MILLIONS);

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
            conn.setReadTimeout(MyApplication.TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(MyApplication.TIMEOUT_IN_MILLIONS);

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
