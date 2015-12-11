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

import me.zhangls.rilintech.utils.L;

/**
 * Created by YANG on 15/9/10.
 */
public class TableRehabilitationGoalInfo implements Parcelable{

    //记录ID
    private int record_id;
    //次数
    private int counts;
    //主要问题
    private String main_problems;
    //次要问题
    private String minor_problems;
    //近期目标
    private String short_goals;
    //目标状态
    private String state_goals_short;
    //中期目标
    private String medium_goals;
    //目标状态
    private String state_goals_medium;
    //远期目标
    private String long_goals;
    //目标状态
    private String state_goals_long;
    //家属预期
    private String family_expectations;
    //患者预期
    private String patient_expectations;
    //设定者
    private String makers;
    //设定时间
    private String set_time;

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public String getMain_problems() {
        return main_problems;
    }

    public void setMain_problems(String main_problems) {
        this.main_problems = main_problems;
    }

    public String getMinor_problems() {
        return minor_problems;
    }

    public void setMinor_problems(String minor_problems) {
        this.minor_problems = minor_problems;
    }

    public String getShort_goals() {
        return short_goals;
    }

    public void setShort_goals(String short_goals) {
        this.short_goals = short_goals;
    }

    public String getState_goals_short() {
        return state_goals_short;
    }

    public void setState_goals_short(String state_goals_short) {
        this.state_goals_short = state_goals_short;
    }

    public String getMedium_goals() {
        return medium_goals;
    }

    public void setMedium_goals(String medium_goals) {
        this.medium_goals = medium_goals;
    }

    public String getState_goals_medium() {
        return state_goals_medium;
    }

    public void setState_goals_medium(String state_goals_medium) {
        this.state_goals_medium = state_goals_medium;
    }

    public String getLong_goals() {
        return long_goals;
    }

    public void setLong_goals(String long_goals) {
        this.long_goals = long_goals;
    }

    public String getState_goals_long() {
        return state_goals_long;
    }

    public void setState_goals_long(String state_goals_long) {
        this.state_goals_long = state_goals_long;
    }

    public String getFamily_expectations() {
        return family_expectations;
    }

    public void setFamily_expectations(String family_expectations) {
        this.family_expectations = family_expectations;
    }

    public String getPatient_expectations() {
        return patient_expectations;
    }

    public void setPatient_expectations(String patient_expectations) {
        this.patient_expectations = patient_expectations;
    }

    public String getMakers() {
        return makers;
    }

    public void setMakers(String makers) {
        this.makers = makers;
    }

    public String getSet_time() {
        return set_time;
    }

    public void setSet_time(String set_time) {
        this.set_time = set_time;
    }


    /**
     * 解析发出请求返回的数据
     * @param jsonArray
     * @return list_infos
     * @throws JSONException
     */
    public static ArrayList<TableRehabilitationGoalInfo> parseData(JSONArray jsonArray) throws JSONException {

        ArrayList<TableRehabilitationGoalInfo> list_infos = new ArrayList<>();
        TableRehabilitationGoalInfo info = null;
        for (int i = 0; i < jsonArray.length(); i++) {

            info = new TableRehabilitationGoalInfo();

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            info.setRecord_id(jsonObject.getInt("id"));
            info.setCounts(jsonObject.getInt("cishu"));
            info.setMain_problems(jsonObject.getString("re_mainq"));
            info.setMinor_problems(jsonObject.getString("re_secondq"));
            info.setShort_goals(jsonObject.getString("re_near_tm"));
            info.setMedium_goals(jsonObject.getString("re_mid_tm"));
            info.setLong_goals(jsonObject.getString("re_long_tm"));
            info.setState_goals_long(jsonObject.getString("target_statey"));
            info.setState_goals_medium(jsonObject.getString("target_statez"));
            info.setState_goals_short(jsonObject.getString("target_state"));
            info.setFamily_expectations(jsonObject.getString("re_home_yq"));
            info.setPatient_expectations(jsonObject.getString("re_patien_yq"));
            info.setMakers(jsonObject.getString("executor"));
            info.setSet_time(jsonObject.getString("zhid_time"));

            list_infos.add(info);
        }
        L.d("re", list_infos.size()+"");
        return list_infos;
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
            conn.disconnect();
        }
        return r;
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

        dest.writeInt(record_id);
        dest.writeInt(counts);
        dest.writeString(main_problems);
        dest.writeString(minor_problems);
        dest.writeString(short_goals);
        dest.writeString(state_goals_short);
        dest.writeString(medium_goals);
        dest.writeString(state_goals_medium);
        dest.writeString(long_goals);
        dest.writeString(state_goals_long);
        dest.writeString(family_expectations);
        dest.writeString(patient_expectations);
        dest.writeString(makers);
        dest.writeString(set_time);

    }
    // 用来创建自定义的Parcelable的对象
    public static final Parcelable.Creator<TableRehabilitationGoalInfo> CREATOR = new Creator<TableRehabilitationGoalInfo>() {

        @Override
        public TableRehabilitationGoalInfo createFromParcel(Parcel source) {

                TableRehabilitationGoalInfo mInfo = new TableRehabilitationGoalInfo();
                mInfo.record_id = source.readInt();
                mInfo.counts = source.readInt();
                mInfo.main_problems = source.readString();
                mInfo.minor_problems = source.readString();
                mInfo.short_goals = source.readString();
                mInfo.state_goals_short = source.readString();
                mInfo.medium_goals = source.readString();
                mInfo.state_goals_medium = source.readString();
                mInfo.long_goals = source.readString();
                mInfo.state_goals_long = source.readString();
                mInfo.family_expectations = source.readString();
                mInfo.patient_expectations = source.readString();
                mInfo.makers = source.readString();
                mInfo.set_time = source.readString();

                return mInfo;

        }

        public TableRehabilitationGoalInfo[] newArray(int size) {
            return new TableRehabilitationGoalInfo[size];
        }
    };

}
