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
import java.util.TreeSet;

import me.zhangls.rilintech.utils.L;

/**
 * Created by YANG on 15/9/22.
 */
public class TableAshworthInfo implements Parcelable{

    //评定日期
    private String date;
    //评定说明
    private String instructions;
    //评定人
    private String maker;
    //左侧分数字符串
    private ArrayList<String> left_scores;
    //右侧分数字符串
    private ArrayList<String> right_scores;
    //记录当前操作表的ID
    private ArrayList<Integer> record_id;
    //部位
    private ArrayList<String> parts;


    public ArrayList<Integer> getRecord_id() {
        return record_id;
    }

    public void setRecord_id(ArrayList<Integer> record_id) {
        this.record_id = record_id;
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

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public ArrayList<String> getLeft_scores() {
        return left_scores;
    }

    public void setLeft_scores(ArrayList<String> left_scores) {
        this.left_scores = left_scores;
    }

    public ArrayList<String> getRight_scores() {
        return right_scores;
    }

    public void setRight_scores(ArrayList<String> right_scores) {
        this.right_scores = right_scores;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public ArrayList<String> getParts() {
        return parts;
    }

    public void setParts(ArrayList<String> parts) {
        this.parts = parts;
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
        dest.writeList(left_scores);
        dest.writeList(right_scores);
        dest.writeList(record_id);
        dest.writeList(parts);

    }
    // 用来创建自定义的Parcelable的对象
    public static final Parcelable.Creator<TableAshworthInfo> CREATOR = new Creator<TableAshworthInfo>() {

        @Override
        public TableAshworthInfo createFromParcel(Parcel source) {

            TableAshworthInfo mInfo = new TableAshworthInfo();

            mInfo.date = source.readString();
            mInfo.instructions = source.readString();
            mInfo.maker = source.readString();
            mInfo.left_scores = source.readArrayList(List.class.getClassLoader());
            mInfo.right_scores = source.readArrayList(List.class.getClassLoader());
            mInfo.record_id = source.readArrayList(List.class.getClassLoader());
            mInfo.parts = source.readArrayList(List.class.getClassLoader());

            return mInfo;

        }

        public TableAshworthInfo[] newArray(int size) {
            return new TableAshworthInfo[size];
        }
    };

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
    public static ArrayList<TableAshworthInfo> parseCache(JSONObject object) throws JSONException {

        ArrayList<TableAshworthInfo> list_infos = new ArrayList<>();
        TableAshworthInfo info = null;
        ArrayList<String> list_left = null;
        ArrayList<Integer> list_record = null;
        ArrayList<String> list_right = null;
        ArrayList<String> list_parts;
        JSONArray jsonArray = null;

        for (int i = 1; i < 8; i++) {
            try {
                jsonArray = object.getJSONArray(i + "");
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            info = new TableAshworthInfo();
            list_left = new ArrayList();
            list_right = new ArrayList();
            list_record = new ArrayList<>();
            list_parts = new ArrayList<>();
            for (int j = 0; j < jsonArray.length(); j++) {

                JSONObject jsonObject = jsonArray.getJSONObject(j);

                info.setMaker(jsonObject.getString("evaluation_person"));
                info.setDate(jsonObject.getString("evaluation_time"));
                info.setInstructions(jsonObject.getString("evaluation_time_note"));

                list_parts.add(jsonObject.getString("evaluation_content"));
                list_record.add(jsonObject.getInt("id"));
                if ("左侧".equals(jsonObject.getString("evaluation_note"))){
                    list_left.add(jsonObject.getString("evaluation_value"));
                }else if ("右侧".equals(jsonObject.getString("evaluation_note"))){
                    list_right.add(jsonObject.getString("evaluation_value"));
                }
            }

            info.setRecord_id(list_record);
            info.setLeft_scores(list_left);
            info.setRight_scores(list_right);
            info.setParts(list_parts);

            list_infos.add(info);

        }
        return list_infos;
    }

    /**
     * 发送更新请求
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
        HttpURLConnection conn = null;
        int r = 0;
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

}
