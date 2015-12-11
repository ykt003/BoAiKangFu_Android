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
 * Created by YANG on 15/10/8.
 */
public class TableFmaInfo implements Parcelable {

    //评定日期
    private String date;
    //评定说明
    private String instructions;
    //评定人
    private String maker;
    //记录当前操作表的ID
    private int record_id;
    //临床意义
    private String evaluation_content;
    //临床意义说明
    private String evaluation_catalog;
    //各项测试值
    private String up_1_1;
    private String up_1_2;
    private String up_2_1;
    private String up_2_2;
    private String up_2_3;
    private String up_2_4;
    private String up_2_5;
    private String up_2_6;
    private String up_3_1;
    private String up_3_2;
    private String up_3_3;
    private String up_4_1;
    private String up_4_2;
    private String up_4_3;
    private String up_5_1;
    private String up_5_2;
    private String up_5_3;
    private String up_6_1;
    private String up_7_1;
    private String up_7_2;
    private String up_7_3;
    private String up_7_4;
    private String up_7_5;
    private String up_8_1;
    private String up_8_2;
    private String up_8_3;
    private String up_8_4;
    private String up_8_5;
    private String up_8_6;
    private String up_8_7;
    private String up_9_1;
    private String up_9_2;
    private String up_9_3;
    private String down_1_1;
    private String down_1_2;
    private String down_2_1;
    private String down_2_2;
    private String down_2_3;
    private String down_2_4;
    private String down_2_5;
    private String down_2_6;
    private String down_2_7;
    private String down_3_1;
    private String down_3_2;
    private String down_4_1;
    private String down_4_2;
    private String down_5_1;
    private String down_6_1;
    private String down_6_2;
    private String down_6_3;

    //集合
    private ArrayList<String> list_value;

    public ArrayList<String> getList_value() {
        return list_value;
    }

    public void setList_value(ArrayList<String> list_value) {
        this.list_value = list_value;
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

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public String getEvaluation_content() {
        return evaluation_content;
    }

    public void setEvaluation_content(String evaluation_content) {
        this.evaluation_content = evaluation_content;
    }

    public String getEvaluation_catalog() {
        return evaluation_catalog;
    }

    public void setEvaluation_catalog(String evaluation_catalog) {
        this.evaluation_catalog = evaluation_catalog;
    }

    public String getUp_1_1() {
        return up_1_1;
    }

    public void setUp_1_1(String up_1_1) {
        this.up_1_1 = up_1_1;
    }

    public String getUp_1_2() {
        return up_1_2;
    }

    public void setUp_1_2(String up_1_2) {
        this.up_1_2 = up_1_2;
    }

    public String getUp_2_1() {
        return up_2_1;
    }

    public void setUp_2_1(String up_2_1) {
        this.up_2_1 = up_2_1;
    }

    public String getUp_2_2() {
        return up_2_2;
    }

    public void setUp_2_2(String up_2_2) {
        this.up_2_2 = up_2_2;
    }

    public String getUp_2_3() {
        return up_2_3;
    }

    public void setUp_2_3(String up_2_3) {
        this.up_2_3 = up_2_3;
    }

    public String getUp_2_4() {
        return up_2_4;
    }

    public void setUp_2_4(String up_2_4) {
        this.up_2_4 = up_2_4;
    }

    public String getUp_2_5() {
        return up_2_5;
    }

    public void setUp_2_5(String up_2_5) {
        this.up_2_5 = up_2_5;
    }

    public String getUp_2_6() {
        return up_2_6;
    }

    public void setUp_2_6(String up_2_6) {
        this.up_2_6 = up_2_6;
    }

    public String getUp_3_1() {
        return up_3_1;
    }

    public void setUp_3_1(String up_3_1) {
        this.up_3_1 = up_3_1;
    }

    public String getUp_3_2() {
        return up_3_2;
    }

    public void setUp_3_2(String up_3_2) {
        this.up_3_2 = up_3_2;
    }

    public String getUp_3_3() {
        return up_3_3;
    }

    public void setUp_3_3(String up_3_3) {
        this.up_3_3 = up_3_3;
    }

    public String getUp_4_1() {
        return up_4_1;
    }

    public void setUp_4_1(String up_4_1) {
        this.up_4_1 = up_4_1;
    }

    public String getUp_4_2() {
        return up_4_2;
    }

    public void setUp_4_2(String up_4_2) {
        this.up_4_2 = up_4_2;
    }

    public String getUp_4_3() {
        return up_4_3;
    }

    public void setUp_4_3(String up_4_3) {
        this.up_4_3 = up_4_3;
    }

    public String getUp_5_1() {
        return up_5_1;
    }

    public void setUp_5_1(String up_5_1) {
        this.up_5_1 = up_5_1;
    }

    public String getUp_5_2() {
        return up_5_2;
    }

    public void setUp_5_2(String up_5_2) {
        this.up_5_2 = up_5_2;
    }

    public String getUp_5_3() {
        return up_5_3;
    }

    public void setUp_5_3(String up_5_3) {
        this.up_5_3 = up_5_3;
    }

    public String getUp_6_1() {
        return up_6_1;
    }

    public void setUp_6_1(String up_6_1) {
        this.up_6_1 = up_6_1;
    }

    public String getUp_7_1() {
        return up_7_1;
    }

    public void setUp_7_1(String up_7_1) {
        this.up_7_1 = up_7_1;
    }

    public String getUp_7_2() {
        return up_7_2;
    }

    public void setUp_7_2(String up_7_2) {
        this.up_7_2 = up_7_2;
    }

    public String getUp_7_3() {
        return up_7_3;
    }

    public void setUp_7_3(String up_7_3) {
        this.up_7_3 = up_7_3;
    }

    public String getUp_7_4() {
        return up_7_4;
    }

    public void setUp_7_4(String up_7_4) {
        this.up_7_4 = up_7_4;
    }

    public String getUp_7_5() {
        return up_7_5;
    }

    public void setUp_7_5(String up_7_5) {
        this.up_7_5 = up_7_5;
    }

    public String getUp_8_1() {
        return up_8_1;
    }

    public void setUp_8_1(String up_8_1) {
        this.up_8_1 = up_8_1;
    }

    public String getUp_8_2() {
        return up_8_2;
    }

    public void setUp_8_2(String up_8_2) {
        this.up_8_2 = up_8_2;
    }

    public String getUp_8_3() {
        return up_8_3;
    }

    public void setUp_8_3(String up_8_3) {
        this.up_8_3 = up_8_3;
    }

    public String getUp_8_4() {
        return up_8_4;
    }

    public void setUp_8_4(String up_8_4) {
        this.up_8_4 = up_8_4;
    }

    public String getUp_8_5() {
        return up_8_5;
    }

    public void setUp_8_5(String up_8_5) {
        this.up_8_5 = up_8_5;
    }

    public String getUp_8_6() {
        return up_8_6;
    }

    public void setUp_8_6(String up_8_6) {
        this.up_8_6 = up_8_6;
    }

    public String getUp_8_7() {
        return up_8_7;
    }

    public void setUp_8_7(String up_8_7) {
        this.up_8_7 = up_8_7;
    }

    public String getUp_9_1() {
        return up_9_1;
    }

    public void setUp_9_1(String up_9_1) {
        this.up_9_1 = up_9_1;
    }

    public String getUp_9_2() {
        return up_9_2;
    }

    public void setUp_9_2(String up_9_2) {
        this.up_9_2 = up_9_2;
    }

    public String getUp_9_3() {
        return up_9_3;
    }

    public void setUp_9_3(String up_9_3) {
        this.up_9_3 = up_9_3;
    }

    public String getDown_1_1() {
        return down_1_1;
    }

    public void setDown_1_1(String down_1_1) {
        this.down_1_1 = down_1_1;
    }

    public String getDown_1_2() {
        return down_1_2;
    }

    public void setDown_1_2(String down_1_2) {
        this.down_1_2 = down_1_2;
    }

    public String getDown_2_1() {
        return down_2_1;
    }

    public void setDown_2_1(String down_2_1) {
        this.down_2_1 = down_2_1;
    }

    public String getDown_2_2() {
        return down_2_2;
    }

    public void setDown_2_2(String down_2_2) {
        this.down_2_2 = down_2_2;
    }

    public String getDown_2_3() {
        return down_2_3;
    }

    public void setDown_2_3(String down_2_3) {
        this.down_2_3 = down_2_3;
    }

    public String getDown_2_4() {
        return down_2_4;
    }

    public void setDown_2_4(String down_2_4) {
        this.down_2_4 = down_2_4;
    }

    public String getDown_2_5() {
        return down_2_5;
    }

    public void setDown_2_5(String down_2_5) {
        this.down_2_5 = down_2_5;
    }

    public String getDown_2_6() {
        return down_2_6;
    }

    public void setDown_2_6(String down_2_6) {
        this.down_2_6 = down_2_6;
    }

    public String getDown_2_7() {
        return down_2_7;
    }

    public void setDown_2_7(String down_2_7) {
        this.down_2_7 = down_2_7;
    }

    public String getDown_3_1() {
        return down_3_1;
    }

    public void setDown_3_1(String down_3_1) {
        this.down_3_1 = down_3_1;
    }

    public String getDown_3_2() {
        return down_3_2;
    }

    public void setDown_3_2(String down_3_2) {
        this.down_3_2 = down_3_2;
    }

    public String getDown_4_1() {
        return down_4_1;
    }

    public void setDown_4_1(String down_4_1) {
        this.down_4_1 = down_4_1;
    }

    public String getDown_4_2() {
        return down_4_2;
    }

    public void setDown_4_2(String down_4_2) {
        this.down_4_2 = down_4_2;
    }

    public String getDown_5_1() {
        return down_5_1;
    }

    public void setDown_5_1(String down_5_1) {
        this.down_5_1 = down_5_1;
    }

    public String getDown_6_1() {
        return down_6_1;
    }

    public void setDown_6_1(String down_6_1) {
        this.down_6_1 = down_6_1;
    }

    public String getDown_6_2() {
        return down_6_2;
    }

    public void setDown_6_2(String down_6_2) {
        this.down_6_2 = down_6_2;
    }

    public String getDown_6_3() {
        return down_6_3;
    }

    public void setDown_6_3(String down_6_3) {
        this.down_6_3 = down_6_3;
    }

    public static final Creator<TableFmaInfo> CREATOR = new Creator<TableFmaInfo>() {
        @Override
        public TableFmaInfo createFromParcel(Parcel in) {
            TableFmaInfo info = new TableFmaInfo();

            info.date = in.readString();
            info.instructions = in.readString();
            info.maker = in.readString();
            info.record_id = in.readInt();
            info.evaluation_content = in.readString();
            info.evaluation_catalog = in.readString();
            info.up_1_1 = in.readString();
            info.up_1_2 = in.readString();
            info.up_2_1 = in.readString();
            info.up_2_2 = in.readString();
            info.up_2_3 = in.readString();
            info.up_2_4 = in.readString();
            info.up_2_5 = in.readString();
            info.up_2_6 = in.readString();
            info.up_3_1 = in.readString();
            info.up_3_2 = in.readString();
            info.up_3_3 = in.readString();
            info.up_4_1 = in.readString();
            info.up_4_2 = in.readString();
            info.up_4_3 = in.readString();
            info.up_5_1 = in.readString();
            info.up_5_2 = in.readString();
            info.up_5_3 = in.readString();
            info.up_6_1 = in.readString();
            info.up_7_1 = in.readString();
            info.up_7_2 = in.readString();
            info.up_7_3 = in.readString();
            info.up_7_4 = in.readString();
            info.up_7_5 = in.readString();
            info.up_8_1 = in.readString();
            info.up_8_2 = in.readString();
            info.up_8_3 = in.readString();
            info.up_8_4 = in.readString();
            info.up_8_5 = in.readString();
            info.up_8_6 = in.readString();
            info.up_8_7 = in.readString();
            info.up_9_1 = in.readString();
            info.up_9_2 = in.readString();
            info.up_9_3 = in.readString();
            info.down_1_1 = in.readString();
            info.down_1_2 = in.readString();
            info.down_2_1 = in.readString();
            info.down_2_2 = in.readString();
            info.down_2_3 = in.readString();
            info.down_2_4 = in.readString();
            info.down_2_5 = in.readString();
            info.down_2_6 = in.readString();
            info.down_2_7 = in.readString();
            info.down_3_1 = in.readString();
            info.down_3_2 = in.readString();
            info.down_4_1 = in.readString();
            info.down_4_2 = in.readString();
            info.down_5_1 = in.readString();
            info.down_6_1 = in.readString();
            info.down_6_2 = in.readString();
            info.down_6_3 = in.readString();

            info.list_value = in.readArrayList(List.class.getClassLoader());

            return info;
        }

        @Override
        public TableFmaInfo[] newArray(int size) {
            return new TableFmaInfo[size];
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
        dest.writeInt(record_id);
        dest.writeString(evaluation_content);
        dest.writeString(evaluation_catalog);
        dest.writeString(up_1_1);
        dest.writeString(up_1_2);
        dest.writeString(up_2_1);
        dest.writeString(up_2_2);
        dest.writeString(up_2_3);
        dest.writeString(up_2_4);
        dest.writeString(up_2_5);
        dest.writeString(up_2_6);
        dest.writeString(up_3_1);
        dest.writeString(up_3_2);
        dest.writeString(up_3_3);
        dest.writeString(up_4_1);
        dest.writeString(up_4_2);
        dest.writeString(up_4_3);
        dest.writeString(up_5_1);
        dest.writeString(up_5_2);
        dest.writeString(up_5_3);
        dest.writeString(up_6_1);
        dest.writeString(up_7_1);
        dest.writeString(up_7_2);
        dest.writeString(up_7_3);
        dest.writeString(up_7_4);
        dest.writeString(up_7_5);
        dest.writeString(up_8_1);
        dest.writeString(up_8_2);
        dest.writeString(up_8_3);
        dest.writeString(up_8_4);
        dest.writeString(up_8_5);
        dest.writeString(up_8_6);
        dest.writeString(up_8_7);
        dest.writeString(up_9_1);
        dest.writeString(up_9_2);
        dest.writeString(up_9_3);
        dest.writeString(down_1_1);
        dest.writeString(down_1_2);
        dest.writeString(down_2_1);
        dest.writeString(down_2_2);
        dest.writeString(down_2_3);
        dest.writeString(down_2_4);
        dest.writeString(down_2_5);
        dest.writeString(down_2_6);
        dest.writeString(down_2_7);
        dest.writeString(down_3_1);
        dest.writeString(down_3_2);
        dest.writeString(down_4_1);
        dest.writeString(down_4_2);
        dest.writeString(down_5_1);
        dest.writeString(down_6_1);
        dest.writeString(down_6_2);
        dest.writeString(down_6_3);
        dest.writeList(list_value);
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
     * @param jsonArray
     * @return list_infos
     * @throws JSONException
     */
    public static ArrayList<TableFmaInfo> parseCache(JSONArray jsonArray) throws JSONException {

        ArrayList<TableFmaInfo> list_infos = new ArrayList<>();
        TableFmaInfo info = null;
        JSONObject jsonObject = null;

        for (int j = 0; j < jsonArray.length(); j++) {

            jsonObject = jsonArray.getJSONObject(j);
            info = new TableFmaInfo();

            info.setMaker(jsonObject.getString("evaluation_person"));
            info.setDate(jsonObject.getString("evaluation_time"));
            info.setInstructions(jsonObject.getString("evaluation_time_note"));
            info.setRecord_id(jsonObject.getInt("id"));
            info.setEvaluation_content(jsonObject.getString("evaluation_content"));
            info.setEvaluation_catalog(jsonObject.getString("evaluation_catalog"));
            info.setUp_1_1(jsonObject.getString("sz_getjfs"));
            info.setUp_1_2(jsonObject.getString("sz_gstjfs"));
            info.setUp_2_1(jsonObject.getString("sz_jgjst"));
            info.setUp_2_2(jsonObject.getString("sz_jgjhs"));
            info.setUp_2_3(jsonObject.getString("sz_wz"));
            info.setUp_2_4(jsonObject.getString("sz_wx"));
            info.setUp_2_5(jsonObject.getString("sz_zgjqq"));
            info.setUp_2_6(jsonObject.getString("sz_qbxh"));
            info.setUp_3_1(jsonObject.getString("sz_jgjns"));
            info.setUp_3_2(jsonObject.getString("sz_zgjsz"));
            info.setUp_3_3(jsonObject.getString("sz_qbxq"));
            info.setUp_4_1(jsonObject.getString("sz_scyz"));
            info.setUp_4_2(jsonObject.getString("sz_jgjqq"));
            info.setUp_4_3(jsonObject.getString("sz_qbxqxh"));
            info.setUp_5_1(jsonObject.getString("sz_fl_jgjwz"));
            info.setUp_5_2(jsonObject.getString("sz_fl_jgjqq"));
            info.setUp_5_3(jsonObject.getString("sz_fl_zjgjqq"));
            info.setUp_6_1(jsonObject.getString("sz_zcfs_getjfs"));
            info.setUp_7_1(jsonObject.getString("sz_w_swbs"));
            info.setUp_7_2(jsonObject.getString("sz_w_swqq"));
            info.setUp_7_3(jsonObject.getString("sz_w_swgjbs"));
            info.setUp_7_4(jsonObject.getString("sz_w_swgjqq"));
            info.setUp_7_5(jsonObject.getString("sz_w_hzyd"));
            info.setUp_8_1(jsonObject.getString("sz_s_szlhqq"));
            info.setUp_8_2(jsonObject.getString("sz_s_szlhsz"));
            info.setUp_8_3(jsonObject.getString("sz_s_gzzw"));
            info.setUp_8_4(jsonObject.getString("sz_s_cn"));
            info.setUp_8_5(jsonObject.getString("sz_s_dn"));
            info.setUp_8_6(jsonObject.getString("sz_s_yzzzw"));
            info.setUp_8_7(jsonObject.getString("sz_s_qxzw"));
            info.setUp_9_1(jsonObject.getString("sz_zc"));
            info.setUp_9_2(jsonObject.getString("sz_bjbl"));
            info.setUp_9_3(jsonObject.getString("sz_sd"));
            info.setDown_1_1(jsonObject.getString("xz_yww_gjfs"));
            info.setDown_1_2(jsonObject.getString("xz_yww_xjfs"));
            info.setDown_2_1(jsonObject.getString("xz_yww_kgjqq"));
            info.setDown_2_2(jsonObject.getString("xz_yww_xgjqq"));
            info.setDown_2_3(jsonObject.getString("xz_yww_hgjbq"));
            info.setDown_2_4(jsonObject.getString("xz_yww_kgjsz"));
            info.setDown_2_5(jsonObject.getString("xz_yww_kgjns"));
            info.setDown_2_6(jsonObject.getString("xz_yww_xgjsz"));
            info.setDown_2_7(jsonObject.getString("xz_yww_hgjtq"));
            info.setDown_3_1(jsonObject.getString("xz_zw_xgjqq"));
            info.setDown_3_2(jsonObject.getString("xz_zw_hbq"));
            info.setDown_4_1(jsonObject.getString("xz_zlw_xgjqq"));
            info.setDown_4_2(jsonObject.getString("xz_zlw_hbq"));
            info.setDown_5_1(jsonObject.getString("xz_zw_xbqj"));
            info.setDown_6_1(jsonObject.getString("xz_yww_zc"));
            info.setDown_6_2(jsonObject.getString("xz_yww_bjbl"));
            info.setDown_6_3(jsonObject.getString("xz_yww_sd"));

            info.list_value = new ArrayList();
            info.list_value.add(isNull(info.up_1_1));
            info.list_value.add(isNull(info.up_1_2));
            info.list_value.add(isNull(info.up_2_1));
            info.list_value.add(isNull(info.up_2_2));
            info.list_value.add(isNull(info.up_2_3));
            info.list_value.add(isNull(info.up_2_4));
            info.list_value.add(isNull(info.up_2_5));
            info.list_value.add(isNull(info.up_2_6));
            info.list_value.add(isNull(info.up_3_1));
            info.list_value.add(isNull(info.up_3_2));
            info.list_value.add(isNull(info.up_3_3));
            info.list_value.add(isNull(info.up_4_1));
            info.list_value.add(isNull(info.up_4_2));
            info.list_value.add(isNull(info.up_4_3));
            info.list_value.add(isNull(info.up_5_1));
            info.list_value.add(isNull(info.up_5_2));
            info.list_value.add(isNull(info.up_5_3));
            info.list_value.add(isNull(info.up_6_1));
            info.list_value.add(isNull(info.up_7_1));
            info.list_value.add(isNull(info.up_7_2));
            info.list_value.add(isNull(info.up_7_3));
            info.list_value.add(isNull(info.up_7_4));
            info.list_value.add(isNull(info.up_7_5));
            info.list_value.add(isNull(info.up_8_1));
            info.list_value.add(isNull(info.up_8_2));
            info.list_value.add(isNull(info.up_8_3));
            info.list_value.add(isNull(info.up_8_4));
            info.list_value.add(isNull(info.up_8_5));
            info.list_value.add(isNull(info.up_8_6));
            info.list_value.add(isNull(info.up_8_7));
            info.list_value.add(isNull(info.up_9_1));
            info.list_value.add(isNull(info.up_9_2));
            info.list_value.add(isNull(info.up_9_3));
            info.list_value.add(isNull(info.down_1_1));
            info.list_value.add(isNull(info.down_1_2));
            info.list_value.add(isNull(info.down_2_1));
            info.list_value.add(isNull(info.down_2_2));
            info.list_value.add(isNull(info.down_2_3));
            info.list_value.add(isNull(info.down_2_4));
            info.list_value.add(isNull(info.down_2_5));
            info.list_value.add(isNull(info.down_2_6));
            info.list_value.add(isNull(info.down_2_7));
            info.list_value.add(isNull(info.down_3_1));
            info.list_value.add(isNull(info.down_3_2));
            info.list_value.add(isNull(info.down_4_1));
            info.list_value.add(isNull(info.down_4_2));
            info.list_value.add(isNull(info.down_5_1));
            info.list_value.add(isNull(info.down_6_1));
            info.list_value.add(isNull(info.down_6_2));
            info.list_value.add(isNull(info.down_6_3));

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
