package me.zhangls.rilintech.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Model_four implements Parcelable{
    private String content;
    private String value;


    public static final Creator<Model_four> CREATOR = new Creator<Model_four>() {
        @Override
        public Model_four createFromParcel(Parcel in) {
            Model_four model_four=new Model_four();
            model_four.content=in.readString();
            model_four.value=in.readString();
            return model_four;
        }

        @Override
        public Model_four[] newArray(int size) {
            return new Model_four[size];
        }
    };

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(value);
    }
}
