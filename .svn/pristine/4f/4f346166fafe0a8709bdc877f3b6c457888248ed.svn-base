package me.zhangls.rilintech.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rilintech on 15/8/3.
 */
public class PatientInfo implements Serializable {

    //id
    private int id;
    //uuid
    private String mPatient_uuid;
    //病案号
    private  int mP_bah;
    //姓名
    private String mName;
    //性别
    private String mSex;
    //年龄
    private int mAge;
    //生日
    private String mBirthday;
    //籍贯
    private String mProvince_id;
   private String mState_id;
   private String mCity_id;
    //民族
    private String mNation;
    //职业
    private String mZhiwei;
    //文化程度
    private String mWh_chd;
    //婚姻状况
    private String mHy_zhk;
    //科室
    private String mP_keshi;
    //费用来源
    private String mP_jfly;
    //联系人
    private String mFirst_releate_person;
    //联系电话
    private String  mTel;
    //身高(m)
    private String mHeight;
    //体重(kg)
    private int mWeight;
    //BMI
    private double mP_bmi;
    //体型
    private String mP_sqks;
    //临床诊断
    private String  mP_zhenduan;
    //临床诊断ICD编码
    private String mLczd_icd;
    //病变部位
    private String mP_bbbw;
    //病变部位ICD编码
    private String mBbbw_icd;
    //发病日期
    private String mP_startdate;
    //病程
    private String mP_bingcheng;
    //功能障碍
    private String mP_gnza;
    //发病经过
    private String mP_fbjg;
    //治疗经过
    private String mP_zljg;
    //康复经过
    private String mP_kfjg;
    //收缩压(mmHg)
    private int mP_xy;
    //舒张压(mmHg)
    private int mShu_z_ya;
    //心率(bpm)
    private int mP_xl;
    //靶心率(bpm)
    private int mP_bxl;
    //水中靶心率(bpm)
    private int mP_szbxl;
    //脉搏
    private int mP_mb;
    //血糖(mmol/L)
    private int mP_xt;
    //治疗项目
    private String mZl_xm;
    //诊疗状态
    private String mZl_zt;
    //时间安排
    private String mSj_ap;
    //治疗部位
    private String mZhliao_bw;
    //录入者
    private String mExecutor;
    //录入时间
    private String mLr_time;
    // 籍贯（中文）
    private String birth_locate;




    public static ArrayList<PatientInfo> parse(JSONArray postsArray) {

        ArrayList<PatientInfo> patientInfos = new ArrayList<>();

        for (int i = 0; i < postsArray.length(); i++) {

            PatientInfo patientInfo = new PatientInfo();
            JSONObject jsonObject = postsArray.optJSONObject(i);

            patientInfo.setId(jsonObject.optInt("id"));
            patientInfo.setmPatient_uuid(jsonObject.optString("patient_uuid"));
            patientInfo.setmP_bah(jsonObject.optInt("p_bah"));
            patientInfo.setmName(jsonObject.optString("name"));
            patientInfo.setmSex(jsonObject.optString("sex"));
            patientInfo.setmAge(jsonObject.optInt("age"));
            patientInfo.setmBirthday(jsonObject.optString("birthday"));
            patientInfo.setBirth_locate(jsonObject.optString("birth_locate"));
            patientInfo.setmProvince_id(jsonObject.optString("province_id"));
            patientInfo.setmState_id(jsonObject.optString("state_id"));
            patientInfo.setmCity_id(jsonObject.optString("city_id"));
            patientInfo.setmNation(jsonObject.optString("nation"));
            patientInfo.setmZhiwei(jsonObject.optString("zhiwei"));
            patientInfo.setmWh_chd(jsonObject.optString("wh_chd"));
            patientInfo.setmHy_zhk(jsonObject.optString("hy_zhk"));
            patientInfo.setmP_keshi(jsonObject.optString("p_keshi"));
            patientInfo.setmP_jfly(jsonObject.optString("p_jfly"));
            patientInfo.setmFirst_releate_person(jsonObject.optString("first_releate_person"));
            patientInfo.setmTel(jsonObject.optString("tel"));
            patientInfo.setmHeight(jsonObject.optString("height"));
            patientInfo.setmWeight(jsonObject.optInt("weight"));
            patientInfo.setmP_bmi(jsonObject.optInt("p_bmi"));
            patientInfo.setmP_sqks(jsonObject.optString("p_sqks"));
            patientInfo.setmP_zhenduan(jsonObject.optString("p_zhenduan"));
            patientInfo.setmLczd_icd(jsonObject.optString("lczd_icd"));
            patientInfo.setmP_bbbw(jsonObject.optString("p_bbbw"));
            patientInfo.setmBbbw_icd(jsonObject.optString("bbbw_icd"));
            patientInfo.setmP_startdate(jsonObject.optString("p_startdate"));
            patientInfo.setmP_bingcheng(jsonObject.optString("p_bingcheng"));
            patientInfo.setmP_gnza(jsonObject.optString("p_gnza"));
            patientInfo.setmP_fbjg(jsonObject.optString("p_fbjg"));
            patientInfo.setmP_zljg(jsonObject.optString("p_zljg"));
            patientInfo.setmP_kfjg(jsonObject.optString("p_kfjg"));
            patientInfo.setmP_xy(jsonObject.optInt("p_xy"));
            patientInfo.setmShu_z_ya(jsonObject.optInt("shu_z_ya"));
            patientInfo.setmP_xl(jsonObject.optInt("p_xl"));
            patientInfo.setmP_bxl(jsonObject.optInt("p_bxl"));
            patientInfo.setmP_szbxl(jsonObject.optInt("p_szbxl"));
            patientInfo.setmP_mb(jsonObject.optInt("p_mb"));
            patientInfo.setmP_xt(jsonObject.optInt("p_xt"));
            patientInfo.setmZl_xm(jsonObject.optString("zl_xm"));
            patientInfo.setmZl_zt(jsonObject.optString("zl_zt"));
            patientInfo.setmSj_ap(jsonObject.optString("sj_ap"));
            patientInfo.setmZhliao_bw(jsonObject.optString("zhliao_bw"));
            patientInfo.setmExecutor(jsonObject.optString("executor"));
            patientInfo.setmLr_time(jsonObject.optString("lr_time"));

            patientInfos.add(patientInfo);

        }
        return patientInfos;
    }


    public static ArrayList<PatientInfo> parseCache(JSONArray postsArray) {

        ArrayList<PatientInfo> patientInfos = new ArrayList<>();

        for (int i = 0; i < postsArray.length(); i++) {

            PatientInfo patientInfo = new PatientInfo();
            JSONObject jsonObject = postsArray.optJSONObject(i);

            patientInfo.setId(jsonObject.optInt("id"));
            patientInfo.setmPatient_uuid(jsonObject.optString("patient_uuid"));
            patientInfo.setmP_bah(jsonObject.optInt("p_bah"));
            patientInfo.setmName(jsonObject.optString("name"));
            patientInfo.setmSex(jsonObject.optString("sex"));
            patientInfo.setmAge(jsonObject.optInt("age"));
            patientInfo.setmBirthday(jsonObject.optString("birthday"));
            patientInfo.setBirth_locate(jsonObject.optString("birth_locate"));
            patientInfo.setmProvince_id(jsonObject.optString("province_id"));
            patientInfo.setmState_id(jsonObject.optString("state_id"));
            patientInfo.setmCity_id(jsonObject.optString("city_id"));
            patientInfo.setmNation(jsonObject.optString("nation"));
            patientInfo.setmZhiwei(jsonObject.optString("zhiwei"));
            patientInfo.setmWh_chd(jsonObject.optString("wh_chd"));
            patientInfo.setmHy_zhk(jsonObject.optString("hy_zhk"));
            patientInfo.setmP_keshi(jsonObject.optString("p_keshi"));
            patientInfo.setmP_jfly(jsonObject.optString("p_jfly"));
            patientInfo.setmFirst_releate_person(jsonObject.optString("first_releate_person"));
            patientInfo.setmTel(jsonObject.optString("tel"));
            patientInfo.setmHeight(jsonObject.optString("height"));
            patientInfo.setmWeight(jsonObject.optInt("weight"));
            patientInfo.setmP_bmi(jsonObject.optInt("p_bmi"));
            patientInfo.setmP_sqks(jsonObject.optString("p_sqks"));
            patientInfo.setmP_zhenduan(jsonObject.optString("p_zhenduan"));
            patientInfo.setmLczd_icd(jsonObject.optString("lczd_icd"));
            patientInfo.setmP_bbbw(jsonObject.optString("p_bbbw"));
            patientInfo.setmBbbw_icd(jsonObject.optString("bbbw_icd"));
            patientInfo.setmP_startdate(jsonObject.optString("p_startdate"));
            patientInfo.setmP_bingcheng(jsonObject.optString("p_bingcheng"));
            patientInfo.setmP_gnza(jsonObject.optString("p_gnza"));
            patientInfo.setmP_fbjg(jsonObject.optString("p_fbjg"));
            patientInfo.setmP_zljg(jsonObject.optString("p_zljg"));
            patientInfo.setmP_kfjg(jsonObject.optString("p_kfjg"));
            patientInfo.setmP_xy(jsonObject.optInt("p_xy"));
            patientInfo.setmShu_z_ya(jsonObject.optInt("shu_z_ya"));
            patientInfo.setmP_xl(jsonObject.optInt("p_xl"));
            patientInfo.setmP_bxl(jsonObject.optInt("p_bxl"));
            patientInfo.setmP_szbxl(jsonObject.optInt("p_szbxl"));
            patientInfo.setmP_mb(jsonObject.optInt("p_mb"));
            patientInfo.setmP_xt(jsonObject.optInt("p_xt"));
            patientInfo.setmZl_xm(jsonObject.optString("zl_xm"));
            patientInfo.setmZl_zt(jsonObject.optString("zl_zt"));
            patientInfo.setmSj_ap(jsonObject.optString("sj_ap"));
            patientInfo.setmZhliao_bw(jsonObject.optString("zhliao_bw"));
            patientInfo.setmExecutor(jsonObject.optString("executor"));
            patientInfo.setmLr_time(jsonObject.optString("lr_time"));

            patientInfos.add(patientInfo);

        }
        return patientInfos;
    }

    public String getBirth_locate() {
        return birth_locate;
    }

    public void setBirth_locate(String birth_locate) {
        this.birth_locate = birth_locate;
    }

    public String getmState_id() {
        return mState_id;
    }

    public void setmState_id(String mState_id) {
        this.mState_id = mState_id;
    }

    public String getmCity_id() {
        return mCity_id;
    }

    public void setmCity_id(String mCity_id) {
        this.mCity_id = mCity_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getmP_bah() {
        return mP_bah;
    }

    public void setmP_bah(int mP_bah) {
        this.mP_bah = mP_bah;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmSex() {
        return mSex;
    }

    public void setmSex(String mSex) {
        this.mSex = mSex;
    }

    public int getmAge() {
        return mAge;
    }

    public void setmAge(int mAge) {
        this.mAge = mAge;
    }

    public String getmBirthday() {
        return mBirthday;
    }

    public void setmBirthday(String mBirthday) {
        this.mBirthday = mBirthday;
    }

    public String getmProvince_id() {
        return mProvince_id;
    }

    public void setmProvince_id(String mProvince_id) {
        this.mProvince_id = mProvince_id;
    }

    public String getmNation() {
        return mNation;
    }

    public void setmNation(String mNation) {
        this.mNation = mNation;
    }

    public String getmPatient_uuid() {
        return mPatient_uuid;
    }

    public void setmPatient_uuid(String mPatient_uuid) {
        this.mPatient_uuid = mPatient_uuid;
    }

    public String getmZhiwei() {
        return mZhiwei;
    }

    public void setmZhiwei(String mZhiwei) {
        this.mZhiwei = mZhiwei;
    }

    public String getmWh_chd() {
        return mWh_chd;
    }

    public void setmWh_chd(String mWh_chd) {
        this.mWh_chd = mWh_chd;
    }

    public String getmHy_zhk() {
        return mHy_zhk;
    }

    public void setmHy_zhk(String mHy_zhk) {
        this.mHy_zhk = mHy_zhk;
    }

    public String getmP_keshi() {
        return mP_keshi;
    }

    public void setmP_keshi(String mP_keshi) {
        this.mP_keshi = mP_keshi;
    }

    public String getmP_jfly() {
        return mP_jfly;
    }

    public void setmP_jfly(String mP_jfly) {
        this.mP_jfly = mP_jfly;
    }

    public String getmFirst_releate_person() {
        return mFirst_releate_person;
    }

    public void setmFirst_releate_person(String mFirst_releate_person) {
        this.mFirst_releate_person = mFirst_releate_person;
    }

    public String  getmTel() {
        return mTel;
    }

    public void setmTel(String  mTel) {
        this.mTel = mTel;
    }

    public String getmHeight() {
        return mHeight;
    }

    public void setmHeight(String mHeight) {
        this.mHeight = mHeight;
    }

    public int getmWeight() {
        return mWeight;
    }

    public void setmWeight(int mWeight) {
        this.mWeight = mWeight;
    }

    public double getmP_bmi() {
        return mP_bmi;
    }

    public void setmP_bmi(double mP_bmi) {
        this.mP_bmi = mP_bmi;
    }

    public String getmP_sqks() {
        return mP_sqks;
    }

    public void setmP_sqks(String mP_sqks) {
        this.mP_sqks = mP_sqks;
    }

    public String getmP_zhenduan() {
        return mP_zhenduan;
    }

    public void setmP_zhenduan(String mP_zhenduan) {
        this.mP_zhenduan = mP_zhenduan;
    }

    public String getmLczd_icd() {
        return mLczd_icd;
    }

    public void setmLczd_icd(String mLczd_icd) {
        this.mLczd_icd = mLczd_icd;
    }

    public String getmP_bbbw() {
        return mP_bbbw;
    }

    public void setmP_bbbw(String mP_bbbw) {
        this.mP_bbbw = mP_bbbw;
    }

    public String getmBbbw_icd() {
        return mBbbw_icd;
    }

    public void setmBbbw_icd(String mBbbw_icd) {
        this.mBbbw_icd = mBbbw_icd;
    }

    public String getmP_startdate() {
        return mP_startdate;
    }

    public void setmP_startdate(String mP_startdate) {
        this.mP_startdate = mP_startdate;
    }

    public String getmP_bingcheng() {
        return mP_bingcheng;
    }

    public void setmP_bingcheng(String mP_bingcheng) {
        this.mP_bingcheng = mP_bingcheng;
    }

    public String getmP_gnza() {
        return mP_gnza;
    }

    public void setmP_gnza(String mP_gnza) {
        this.mP_gnza = mP_gnza;
    }

    public String getmP_fbjg() {
        return mP_fbjg;
    }

    public void setmP_fbjg(String mP_fbjg) {
        this.mP_fbjg = mP_fbjg;
    }

    public String getmP_zljg() {
        return mP_zljg;
    }

    public void setmP_zljg(String mP_zljg) {
        this.mP_zljg = mP_zljg;
    }

    public String getmP_kfjg() {
        return mP_kfjg;
    }

    public void setmP_kfjg(String mP_kfjg) {
        this.mP_kfjg = mP_kfjg;
    }

    public int getmP_xy() {
        return mP_xy;
    }

    public void setmP_xy(int mP_xy) {
        this.mP_xy = mP_xy;
    }

    public int getmShu_z_ya() {
        return mShu_z_ya;
    }

    public void setmShu_z_ya(int mShu_z_ya) {
        this.mShu_z_ya = mShu_z_ya;
    }

    public int getmP_xl() {
        return mP_xl;
    }

    public void setmP_xl(int mP_xl) {
        this.mP_xl = mP_xl;
    }

    public int getmP_bxl() {
        return mP_bxl;
    }

    public void setmP_bxl(int mP_bxl) {
        this.mP_bxl = mP_bxl;
    }

    public int getmP_szbxl() {
        return mP_szbxl;
    }

    public void setmP_szbxl(int mP_szbxl) {
        this.mP_szbxl = mP_szbxl;
    }

    public int getmP_mb() {
        return mP_mb;
    }

    public void setmP_mb(int mP_mb) {
        this.mP_mb = mP_mb;
    }

    public int getmP_xt() {
        return mP_xt;
    }

    public void setmP_xt(int mP_xt) {
        this.mP_xt = mP_xt;
    }

    public String getmZl_xm() {
        return mZl_xm;
    }

    public void setmZl_xm(String mZl_xm) {
        this.mZl_xm = mZl_xm;
    }

    public String getmZl_zt() {
        return mZl_zt;
    }

    public void setmZl_zt(String mZl_zt) {
        this.mZl_zt = mZl_zt;
    }

    public String getmSj_ap() {
        return mSj_ap;
    }

    public void setmSj_ap(String mSj_ap) {
        this.mSj_ap = mSj_ap;
    }

    public String getmZhliao_bw() {
        return mZhliao_bw;
    }

    public void setmZhliao_bw(String mZhliao_bw) {
        this.mZhliao_bw = mZhliao_bw;
    }

    public String getmExecutor() {
        return mExecutor;
    }

    public void setmExecutor(String mExecutor) {
        this.mExecutor = mExecutor;
    }

    public String getmLr_time() {
        return mLr_time;
    }

    public void setmLr_time(String mLr_time) {
        this.mLr_time = mLr_time;
    }
}
