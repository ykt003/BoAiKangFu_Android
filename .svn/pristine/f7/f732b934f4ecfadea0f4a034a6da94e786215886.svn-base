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
 * Created by YANG on 15/9/12.
 */
public class TableTreatmentPlanInfo implements Parcelable{


    //记录ID
    private int record_id;
    //次数
    private int counts;
    //干预目的
    private String purpose;
    //项目名称
    private String project_name;
    //剂量
    private String dose;
    //频率
    private String frequency;
    //强度
    private String intensity;
    //持续时间
    private String duration;
    //备注
    private String remarker;
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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRemarker() {
        return remarker;
    }

    public void setRemarker(String remarker) {
        this.remarker = remarker;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(record_id);
        dest.writeInt(counts);
        dest.writeString(purpose);
        dest.writeString(project_name);
        dest.writeString(dose);
        dest.writeString(frequency);
        dest.writeString(intensity);
        dest.writeString(duration);
        dest.writeString(remarker);
        dest.writeString(makers);
        dest.writeString(set_time);
    }

    // 用来创建自定义的Parcelable的对象
    public static final Parcelable.Creator<TableTreatmentPlanInfo> CREATOR = new Creator<TableTreatmentPlanInfo>() {

        @Override
        public TableTreatmentPlanInfo createFromParcel(Parcel source) {

            TableTreatmentPlanInfo mInfo = new TableTreatmentPlanInfo();
            mInfo.record_id = source.readInt();
            mInfo.counts = source.readInt();
            mInfo.purpose = source.readString();
            mInfo.project_name = source.readString();
            mInfo.dose = source.readString();
            mInfo.frequency = source.readString();
            mInfo.intensity = source.readString();
            mInfo.duration = source.readString();
            mInfo.remarker = source.readString();
            mInfo.makers = source.readString();
            mInfo.set_time = source.readString();

            return mInfo;

        }

        public TableTreatmentPlanInfo[] newArray(int size) {
            return new TableTreatmentPlanInfo[size];
        }
    };


    /**
     * 解析发出请求返回的数据
     * @param jsonArray
     * @return list_infos
     * @throws JSONException
     */
    public static ArrayList<TableTreatmentPlanInfo> parseData(JSONArray jsonArray) throws JSONException {

        ArrayList<TableTreatmentPlanInfo> list_infos = new ArrayList<>();
        TableTreatmentPlanInfo info = null;
        for (int i = 0; i < jsonArray.length(); i++) {

            info = new TableTreatmentPlanInfo();

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            info.setRecord_id(jsonObject.getInt("id"));
            info.setCounts(jsonObject.getInt("cishu"));
            info.setPurpose(jsonObject.getString("trp_gymd"));
            info.setProject_name(jsonObject.getString("trp_xmmc"));
            info.setDose(jsonObject.getString("trp_jl"));
            info.setFrequency(jsonObject.getString("trp_pl"));
            info.setIntensity(jsonObject.getString("trp_qd"));
            info.setDuration(jsonObject.getString("trp_cxdate"));
            info.setRemarker(jsonObject.getString("trp_bzh"));
            info.setMakers(jsonObject.getString("executor"));
            info.setSet_time(jsonObject.getString("zhid_time"));

            list_infos.add(info);
        }
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
