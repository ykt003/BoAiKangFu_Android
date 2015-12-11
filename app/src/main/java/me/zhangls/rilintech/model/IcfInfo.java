package me.zhangls.rilintech.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rilintech on 15/9/8.
 */
public class IcfInfo {
    //自增长id
    private int id;
    //编码
    private String code;
    //名称
    private String term;
    //描述
    private String describe;
    //创建日期
    private String created_at;
    //相对应的拼音简写
    private String pin_yin;
    //更新日期
    private String updated_at;


    public static ArrayList<IcfInfo> parse(JSONArray postsArray) {

        ArrayList<IcfInfo> icfInfos = new ArrayList<>();

        for (int i = 0; i < postsArray.length(); i++) {

            IcfInfo icfInfo = new IcfInfo();
            JSONObject jsonObject = postsArray.optJSONObject(i);

            icfInfo.setId(jsonObject.optInt("id"));
            icfInfo.setCode(jsonObject.optString("code"));
            icfInfo.setCreated_at(jsonObject.optString("created_at"));
            icfInfo.setDescribe(jsonObject.optString("describe"));
            icfInfo.setPin_yin(jsonObject.optString("pin_yin"));
            icfInfo.setTerm(jsonObject.optString("term"));
            icfInfo.setUpdated_at(jsonObject.optString("updated_at"));

            icfInfos.add(icfInfo);

        }
        return icfInfos;
    }


    public static ArrayList<IcfInfo> parseCache(JSONArray postsArray) {

        ArrayList<IcfInfo> icfInfos = new ArrayList<>();

        for (int i = 0; i < postsArray.length(); i++) {

            IcfInfo icfInfo = new IcfInfo();
            JSONObject jsonObject = postsArray.optJSONObject(i);

            icfInfo.setId(jsonObject.optInt("id"));
            icfInfo.setCode(jsonObject.optString("code"));
            icfInfo.setCreated_at(jsonObject.optString("created_at"));
            icfInfo.setDescribe(jsonObject.optString("describe"));
            icfInfo.setPin_yin(jsonObject.optString("pin_yin"));
            icfInfo.setTerm(jsonObject.optString("term"));
            icfInfo.setUpdated_at(jsonObject.optString("updated_at"));

            icfInfos.add(icfInfo);

        }
        return icfInfos;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPin_yin() {
        return pin_yin;
    }

    public void setPin_yin(String pin_yin) {
        this.pin_yin = pin_yin;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
