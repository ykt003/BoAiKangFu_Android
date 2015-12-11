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

import me.zhangls.rilintech.utils.L;

/**
 * Created by YANG on 15/9/16.
 */
public class TableBarthelInfo implements Parcelable {

    private static final int TIMEOUT_IN_MILLIONS = 5000;


    //评定日期
    private String date;
    //评定说明
    private String instructions;
    //评定人
    private String maker;
    //分数和评级的集合
    private ArrayList<Integer> scores;
    //记录当前操作表的ID
    private ArrayList<Integer> record_id;


    public ArrayList<Integer> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Integer> scores) {
        this.scores = scores;
    }

    public ArrayList<Integer> getRecord_id() {
        return record_id;
    }

    public void setRecord_id(ArrayList<Integer> list_record_id) {
        this.record_id = list_record_id;
    }

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


    /**
     * 解析发出请求返回的数据
     *
     * @param
     * @return
     * @throws JSONException
     */
    public static ArrayList<TableBarthelInfo> parseCache(JSONObject object) throws JSONException {

        ArrayList<TableBarthelInfo> list_infos = new ArrayList<>();
        TableBarthelInfo info = null;
        ArrayList<Integer> list = null;
        ArrayList<Integer> list_record = null;
        JSONArray jsonArray = null;

        for (int i = 1; i < 8; i++) {
            try {
                jsonArray = object.getJSONArray(i + "");
            } catch (Exception e) {

                e.printStackTrace();
                continue;
            }


            info = new TableBarthelInfo();
            list = new ArrayList();
            list_record = new ArrayList<>();

            for (int j = 0; j < jsonArray.length(); j++) {

                JSONObject jsonObject = jsonArray.getJSONObject(j);

                L.d("json", jsonObject.toString());

                info.setMaker(jsonObject.getString("evaluation_person"));
                info.setDate(jsonObject.getString("evaluation_time"));
                info.setInstructions(jsonObject.getString("evaluation_time_note"));

                list_record.add(jsonObject.getInt("id"));
                list.add(jsonObject.getInt("evaluation_value"));
            }

            info.setRecord_id(list_record);
            info.setScores(list);

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
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

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
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

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

    // 写数据进行保存
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 将实体类数据写入Parcel
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(date);
        dest.writeString(instructions);
        dest.writeString(maker);
        dest.writeList(scores);
        dest.writeList(record_id);

    }
    // 用来创建自定义的Parcelable的对象
    public static final Parcelable.Creator<TableBarthelInfo> CREATOR = new Creator<TableBarthelInfo>() {

        @Override
        public TableBarthelInfo createFromParcel(Parcel source) {

            TableBarthelInfo mInfo = new TableBarthelInfo();

            mInfo.date = source.readString();
            mInfo.instructions = source.readString();
            mInfo.maker = source.readString();
            mInfo.scores = source.readArrayList(List.class.getClassLoader());
            mInfo.record_id = source.readArrayList(List.class.getClassLoader());

            return mInfo;

        }

        public TableBarthelInfo[] newArray(int size) {
            return new TableBarthelInfo[size];
        }
    };
}
