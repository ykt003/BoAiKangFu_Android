package me.zhangls.rilintech.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

/**
 * 康复评价会记录表
 */
public class NormalTableModel implements Parcelable,Serializable {
    private String type;
    private String name;
    private String leftText;
    private String middleText;
    private String rightText;
    private List<String> dicText;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(name);
        dest.writeString(leftText);
        dest.writeString(middleText);
        dest.writeString(rightText);
        dest.writeList(dicText);
    }


    public final static Creator<NormalTableModel> CREATOR = new Creator<NormalTableModel>() {
        @Override
        public NormalTableModel createFromParcel(Parcel in) {
            NormalTableModel model = new NormalTableModel();

            model.type = in.readString();
            model.name = in.readString();
            model.leftText = in.readString();
            model.middleText = in.readString();
            model.rightText = in.readString();
            model.dicText = in.readArrayList(List.class.getClassLoader());

            return model;
        }

        @Override
        public NormalTableModel[] newArray(int size) {
            return new NormalTableModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDicText() {
        return dicText;
    }

    public void setDicText(List<String> dicText) {
        this.dicText = dicText;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLeftText() {
        return leftText;
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
    }

    public String getMiddleText() {
        return middleText;
    }

    public void setMiddleText(String middleText) {
        this.middleText = middleText;
    }

    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }


}
