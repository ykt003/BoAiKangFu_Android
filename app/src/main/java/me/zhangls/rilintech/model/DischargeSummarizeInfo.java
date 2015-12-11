package me.zhangls.rilintech.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rilintech on 15/9/11.
 */
public class DischargeSummarizeInfo implements Serializable{
    private int id;
    //次数
    private int cishu;
    //治疗开始时间
    private String start_time;
    //治疗结束时间
    private String end_time;
    //治疗次数
    private int times;
    //初评得分
    private int start_score;
    //末评得分
    private int end_score;
    //评定统计分析
    private String analysis;
    //进步点
    private String progress;
    //不足处
    private String weak;
    //疗效总结
    private String summarize;
    //出院指导/家庭训练
    private String guidance;
    //备注
    private String remark;
    //总结时间
    private String zj_time;
    //总结者
    private String zjze;


    public static ArrayList<DischargeSummarizeInfo> parse(JSONArray postsArray) {

        ArrayList<DischargeSummarizeInfo> dischargeSummarizeInfos = new ArrayList<DischargeSummarizeInfo>();

        for (int i = 0; i < postsArray.length(); i++) {

            DischargeSummarizeInfo dischargeSummarizeInfo= new DischargeSummarizeInfo();
            JSONObject jsonObject = postsArray.optJSONObject(i);

            dischargeSummarizeInfo.setRemark(jsonObject.optString("remark"));
            dischargeSummarizeInfo.setAnalysis(jsonObject.optString("analysis"));
            dischargeSummarizeInfo.setCishu(jsonObject.optInt("cishu"));
            dischargeSummarizeInfo.setEnd_score(jsonObject.optInt("end_score"));
            dischargeSummarizeInfo.setEnd_time(jsonObject.optString("end_time"));
            dischargeSummarizeInfo.setGuidance(jsonObject.optString("guidance"));
            dischargeSummarizeInfo.setId(jsonObject.optInt("id"));
            dischargeSummarizeInfo.setProgress(jsonObject.optString("progress"));
            dischargeSummarizeInfo.setStart_time(jsonObject.optString("start_time"));
            dischargeSummarizeInfo.setStart_score(jsonObject.optInt("start_score"));
            dischargeSummarizeInfo.setSummarize(jsonObject.optString("summarize"));
            dischargeSummarizeInfo.setTimes(jsonObject.optInt("times"));
            dischargeSummarizeInfo.setWeak(jsonObject.optString("weak"));
            dischargeSummarizeInfo.setZj_time(jsonObject.optString("zj_time"));
            dischargeSummarizeInfo.setZjze(jsonObject.optString("zjze"));

            dischargeSummarizeInfos.add(dischargeSummarizeInfo);
        }
        return dischargeSummarizeInfos;
    }


    public static ArrayList<DischargeSummarizeInfo> parseCache(JSONArray postsArray) {

        ArrayList<DischargeSummarizeInfo> dischargeSummarizeInfos = new ArrayList<DischargeSummarizeInfo>();

        for (int i = 0; i < postsArray.length(); i++) {

            DischargeSummarizeInfo dischargeSummarizeInfo= new DischargeSummarizeInfo();
            JSONObject jsonObject = postsArray.optJSONObject(i);

            dischargeSummarizeInfo.setRemark(jsonObject.optString("remark"));
            dischargeSummarizeInfo.setAnalysis(jsonObject.optString("analysis"));
            dischargeSummarizeInfo.setCishu(jsonObject.optInt("cishu"));
            dischargeSummarizeInfo.setEnd_score(jsonObject.optInt("end_score"));
            dischargeSummarizeInfo.setEnd_time(jsonObject.optString("end_time"));
            dischargeSummarizeInfo.setGuidance(jsonObject.optString("guidance"));
            dischargeSummarizeInfo.setId(jsonObject.optInt("id"));
            dischargeSummarizeInfo.setProgress(jsonObject.optString("progress"));
            dischargeSummarizeInfo.setStart_time(jsonObject.optString("start_time"));
            dischargeSummarizeInfo.setStart_score(jsonObject.optInt("start_score"));
            dischargeSummarizeInfo.setSummarize(jsonObject.optString("summarize"));
            dischargeSummarizeInfo.setTimes(jsonObject.optInt("times"));
            dischargeSummarizeInfo.setWeak(jsonObject.optString("weak"));
            dischargeSummarizeInfo.setZj_time(jsonObject.optString("zj_time"));
            dischargeSummarizeInfo.setZjze(jsonObject.optString("zjze"));

            dischargeSummarizeInfos.add(dischargeSummarizeInfo);
        }
        return dischargeSummarizeInfos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZjze() {
        return zjze;
    }

    public void setZjze(String zjze) {
        this.zjze = zjze;
    }

    public int getCishu() {
        return cishu;
    }

    public void setCishu(int cishu) {
        this.cishu = cishu;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getStart_score() {
        return start_score;
    }

    public void setStart_score(int start_score) {
        this.start_score = start_score;
    }

    public int getEnd_score() {
        return end_score;
    }

    public void setEnd_score(int end_score) {
        this.end_score = end_score;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getWeak() {
        return weak;
    }

    public void setWeak(String weak) {
        this.weak = weak;
    }

    public String getSummarize() {
        return summarize;
    }

    public void setSummarize(String summarize) {
        this.summarize = summarize;
    }

    public String getGuidance() {
        return guidance;
    }

    public void setGuidance(String guidance) {
        this.guidance = guidance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZj_time() {
        return zj_time;
    }

    public void setZj_time(String zj_time) {
        this.zj_time = zj_time;
    }
}
