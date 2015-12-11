package me.zhangls.rilintech.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class Model_three implements Parcelable {
    private ArrayList<Model_four> options=new ArrayList<Model_four>();
    private String question;
    private String value;
    private String id;
    private RadioGroup radioGroup;
    private LinearLayout ll_common_layout;
    private EditText editText;
//    private ImageView text_image_add;
//    private ImageView text_image_delete;
    private boolean in_sum;

    public LinearLayout getLl_common_layout() {
        return ll_common_layout;
    }

    public void setLl_common_layout(LinearLayout ll_common_layout) {
        this.ll_common_layout = ll_common_layout;
    }

//    public ImageView getText_image_add() {
//        return text_image_add;
//    }
//
//    public void setText_image_add(ImageView text_image_add) {
//        this.text_image_add = text_image_add;
//    }
//
//    public ImageView getText_image_delete() {
//        return text_image_delete;
//    }
//
//    public void setText_image_delete(ImageView text_image_delete) {
//        this.text_image_delete = text_image_delete;
//    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public boolean isIn_sum() {
        return in_sum;
    }

    public void setIn_sum(boolean in_sum) {
        this.in_sum = in_sum;
    }

    public static Creator<Model_three> getCREATOR() {
        return CREATOR;
    }

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    public void setRadioGroup(RadioGroup radioGroup) {
        this.radioGroup = radioGroup;
    }

    public static final Creator<Model_three> CREATOR = new Creator<Model_three>() {
        @Override
        public Model_three createFromParcel(Parcel in) {
            Model_three model_three=new Model_three();
            model_three.question=in.readString();
            model_three.value=in.readString();
            model_three.id=in.readString();
            model_three.options=in.readArrayList(Model_four.class.getClassLoader());
            model_three.in_sum = in.readByte() != 0;
            return model_three;
        }

        @Override
        public Model_three[] newArray(int size) {
            return new Model_three[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Model_four> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Model_four> options) {
        this.options = options;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(value);
        dest.writeString(id);
        dest.writeList(options);
        dest.writeByte((byte)(isIn_sum() ?1:0));
    }
}
