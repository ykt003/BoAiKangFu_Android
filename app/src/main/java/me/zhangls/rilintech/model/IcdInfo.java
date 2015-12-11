package me.zhangls.rilintech.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rilintech on 15/9/8.
 */
public class IcdInfo {
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



    public static ArrayList<IcdInfo> parse(JSONArray postsArray) {

        ArrayList<IcdInfo> icdInfos = new ArrayList<>();

        for (int i = 0; i < postsArray.length(); i++) {

            IcdInfo icdInfo = new IcdInfo();
            JSONObject jsonObject = postsArray.optJSONObject(i);

            icdInfo.setId(jsonObject.optInt("id"));
            icdInfo.setCode(jsonObject.optString("code"));
            icdInfo.setCreated_at(jsonObject.optString("created_at"));
            icdInfo.setDescribe(jsonObject.optString("describe"));
            icdInfo.setPin_yin(jsonObject.optString("pin_yin"));
            icdInfo.setTerm(jsonObject.optString("term"));
            icdInfo.setUpdated_at(jsonObject.optString("updated_at"));

            icdInfos.add(icdInfo);

        }
        return icdInfos;
    }


    public static ArrayList<IcdInfo> parseCache(JSONArray postsArray) {

        ArrayList<IcdInfo> icdInfos = new ArrayList<>();

        for (int i = 0; i < postsArray.length(); i++) {

            IcdInfo icdInfo = new IcdInfo();
            JSONObject jsonObject = postsArray.optJSONObject(i);

            icdInfo.setId(jsonObject.optInt("id"));
            icdInfo.setCode(jsonObject.optString("code"));
            icdInfo.setCreated_at(jsonObject.optString("created_at"));
            icdInfo.setDescribe(jsonObject.optString("describe"));
            icdInfo.setPin_yin(jsonObject.optString("pin_yin"));
            icdInfo.setTerm(jsonObject.optString("term"));
            icdInfo.setUpdated_at(jsonObject.optString("updated_at"));

            icdInfos.add(icdInfo);

        }
        return icdInfos;
    }



    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
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
}
