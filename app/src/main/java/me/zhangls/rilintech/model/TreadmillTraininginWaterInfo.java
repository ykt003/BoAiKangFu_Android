package me.zhangls.rilintech.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zsn on 15/9/10.
 */
public class TreadmillTraininginWaterInfo implements Serializable {
    private int id;
    //日期
    private String date_time;
    //治疗时间
    private int time_of_therapy;
    //设定速度
    private double speed;
    //实际距离
    private double actual_distance;
    //速度评价
    private String speed_evaluation;
    //水深（负重比）
    private double depth_of_water;
    //水温
    private double water_temperature;
    //涡流
    private String vortex;
    //步态指导
    private String gait_guidance;
    //视频记录
    private String video_record;
    //治疗作用
    private String therapeutic_effect;
    //SOAP记录
    private String soap_record;
    //备注
    private String remark;
    //执行者
    private String executor;

    public static ArrayList<TreadmillTraininginWaterInfo> parse(JSONArray postsArray) {

        ArrayList<TreadmillTraininginWaterInfo> treadmillTraininginWaterInfos = new ArrayList<TreadmillTraininginWaterInfo>();

        for (int i = 0; i < postsArray.length(); i++) {

            TreadmillTraininginWaterInfo treadmillTraininginWaterInfo= new TreadmillTraininginWaterInfo();
            JSONObject jsonObject = postsArray.optJSONObject(i);

            treadmillTraininginWaterInfo.setId(jsonObject.optInt("id"));
            treadmillTraininginWaterInfo.setDate_time(jsonObject.optString("date_time"));
            treadmillTraininginWaterInfo.setActual_distance(jsonObject.optDouble("actual_distance"));
            treadmillTraininginWaterInfo.setDepth_of_water(jsonObject.optDouble("depth_of_water"));
            treadmillTraininginWaterInfo.setExecutor(jsonObject.optString("executor"));
            treadmillTraininginWaterInfo.setGait_guidance(jsonObject.optString("gait_guidance"));
            treadmillTraininginWaterInfo.setRemark(jsonObject.optString("remark"));
            treadmillTraininginWaterInfo.setSoap_record(jsonObject.optString("soap_record"));
            treadmillTraininginWaterInfo.setSpeed(jsonObject.optDouble("speed"));
            treadmillTraininginWaterInfo.setSpeed_evaluation(jsonObject.optString("speed_evaluation"));
            treadmillTraininginWaterInfo.setTherapeutic_effect(jsonObject.optString("therapeutic_effect"));
            treadmillTraininginWaterInfo.setVideo_record(jsonObject.optString("video_record"));
            treadmillTraininginWaterInfo.setVortex(jsonObject.optString("vortex"));
            treadmillTraininginWaterInfo.setTime_of_therapy(jsonObject.optInt("time_of_therapy"));
            treadmillTraininginWaterInfo.setWater_temperature(jsonObject.optDouble("water_temperature"));

            treadmillTraininginWaterInfos.add(treadmillTraininginWaterInfo);

        }
        return treadmillTraininginWaterInfos;
    }

    public static ArrayList<TreadmillTraininginWaterInfo> parseCache(JSONArray postsArray) {

        ArrayList<TreadmillTraininginWaterInfo> treadmillTraininginWaterInfos = new ArrayList<TreadmillTraininginWaterInfo>();

        for (int i = 0; i < postsArray.length(); i++) {

            TreadmillTraininginWaterInfo treadmillTraininginWaterInfo= new TreadmillTraininginWaterInfo();
            JSONObject jsonObject = postsArray.optJSONObject(i);

            treadmillTraininginWaterInfo.setId(jsonObject.optInt("id"));
            treadmillTraininginWaterInfo.setDate_time(jsonObject.optString("date_time"));
            treadmillTraininginWaterInfo.setActual_distance(jsonObject.optDouble("actual_distance"));
            treadmillTraininginWaterInfo.setDepth_of_water(jsonObject.optDouble("depth_of_water"));
            treadmillTraininginWaterInfo.setExecutor(jsonObject.optString("executor"));
            treadmillTraininginWaterInfo.setGait_guidance(jsonObject.optString("gait_guidance"));
            treadmillTraininginWaterInfo.setRemark(jsonObject.optString("remark"));
            treadmillTraininginWaterInfo.setSoap_record(jsonObject.optString("soap_record"));
            treadmillTraininginWaterInfo.setSpeed(jsonObject.optDouble("speed"));
            treadmillTraininginWaterInfo.setSpeed_evaluation(jsonObject.optString("speed_evaluation"));
            treadmillTraininginWaterInfo.setTherapeutic_effect(jsonObject.optString("therapeutic_effect"));
            treadmillTraininginWaterInfo.setVideo_record(jsonObject.optString("video_record"));
            treadmillTraininginWaterInfo.setVortex(jsonObject.optString("vortex"));
            treadmillTraininginWaterInfo.setTime_of_therapy(jsonObject.optInt("time_of_therapy"));
            treadmillTraininginWaterInfo.setWater_temperature(jsonObject.optDouble("water_temperature"));

            treadmillTraininginWaterInfos.add(treadmillTraininginWaterInfo);

        }
        return treadmillTraininginWaterInfos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public int getTime_of_therapy() {
        return time_of_therapy;
    }

    public void setTime_of_therapy(int time_of_therapy) {
        this.time_of_therapy = time_of_therapy;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getActual_distance() {
        return actual_distance;
    }

    public void setActual_distance(double actual_distance) {
        this.actual_distance = actual_distance;
    }

    public String getSpeed_evaluation() {
        return speed_evaluation;
    }

    public void setSpeed_evaluation(String speed_evaluation) {
        this.speed_evaluation = speed_evaluation;
    }

    public double getDepth_of_water() {
        return depth_of_water;
    }

    public void setDepth_of_water(double depth_of_water) {
        this.depth_of_water = depth_of_water;
    }

    public double getWater_temperature() {
        return water_temperature;
    }

    public void setWater_temperature(double water_temperature) {
        this.water_temperature = water_temperature;
    }

    public String getVortex() {
        return vortex;
    }

    public void setVortex(String vortex) {
        this.vortex = vortex;
    }

    public String getGait_guidance() {
        return gait_guidance;
    }

    public void setGait_guidance(String gait_guidance) {
        this.gait_guidance = gait_guidance;
    }

    public String getVideo_record() {
        return video_record;
    }

    public void setVideo_record(String video_record) {
        this.video_record = video_record;
    }

    public String getTherapeutic_effect() {
        return therapeutic_effect;
    }

    public void setTherapeutic_effect(String therapeutic_effect) {
        this.therapeutic_effect = therapeutic_effect;
    }

    public String getSoap_record() {
        return soap_record;
    }

    public void setSoap_record(String soap_record) {
        this.soap_record = soap_record;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }
}
