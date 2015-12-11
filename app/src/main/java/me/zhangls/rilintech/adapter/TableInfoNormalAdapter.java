package me.zhangls.rilintech.adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import me.zhangls.rilintech.R;
import me.zhangls.rilintech.model.NormalTableFinalModel;

/**
 * Created by YANG on 15/11/23.
 */
public class TableInfoNormalAdapter extends BaseAdapter {

    private static final String TEXTVIEW = "Textview";
    private static final String SPINNER = "Spinner";
    private static final String RADIOGROUP = "Radiogroup";
    private static final String MAXTEXT = "Maxtext";
    private static final String EDITTEXT = "Edittext";

    private Context mContext;
    private int ITEM_LAYOUT_ID = -1;
    private LayoutInflater inflater;
    //item布局的类型
    private String type = "";
    //EditText布局
    private TextView et_question;
    private EditText et_answer;
    private TextView et_unit;
    //段落式的EditText布局
    private TextView lt_question;
    private EditText lt_answer;
    //RadioGroup布局
    private TextView rg_question;
    private RadioGroup rg_answer;
    //Spinner布局
    private TextView sp_question;
    private Spinner sp_answer;
    private List<String> sp_list_value;
    private SpinnerBaseAdapter adapter;
    //TextView布局
    private TextView tv_question;
    private TextView tv_value;
    private TextView tv_unit;
    //model集合
    private static NormalTableFinalModel finalModel;
    //新建
    private boolean isAdd;

    public TableInfoNormalAdapter(Context context, NormalTableFinalModel finalModel, boolean isAdd) {
        this.mContext = context;
        this.finalModel = finalModel;
        this.isAdd = isAdd;
        this.inflater = LayoutInflater.from(context);
        if (isAdd) {
            clearFinalModelListModelData();
        }
    }

    /**
     * 清空默认值
     */
    private void clearFinalModelListModelData(){
        for (int i=0;i<finalModel.getList_model().size();i++){
            finalModel.getList_model().get(i).setMiddleText("");
        }
    }

    @Override
    public int getCount() {
        return finalModel.getList_model().size();
    }

    @Override
    public Object getItem(int position) {
        return finalModel.getList_model().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //获取加载类型
        type = finalModel.getList_model().get(position).getType();
        switch (type) {
            case EDITTEXT:
                ITEM_LAYOUT_ID = R.layout.item_app_edittext_layout;
                break;
            case MAXTEXT:
                ITEM_LAYOUT_ID = R.layout.item_app_labletext_layout;
                break;
            case RADIOGROUP:
                ITEM_LAYOUT_ID = R.layout.item_app_radiogroup_layout;
                break;
            case SPINNER:
                ITEM_LAYOUT_ID = R.layout.item_app_spinner_layout;
                break;
            case TEXTVIEW:
                ITEM_LAYOUT_ID = R.layout.item_app_textview_layout;
                break;

        }
        convertView = inflater.inflate(ITEM_LAYOUT_ID, null);

        initView(type, convertView,position);

        initData(type, position);

        return convertView;
    }

    //1.初始化控件
    private void initView(String type, View convertView,int position) {

        switch (type) {
            case EDITTEXT:
                et_question = (TextView) convertView.findViewById(R.id.tv_question);
                et_answer = (EditText) convertView.findViewById(R.id.et_value);
                et_unit = (TextView) convertView.findViewById(R.id.tv_unit);
                et_answer.addTextChangedListener(new MyTextWatcher(position));
                break;
            case MAXTEXT:
                lt_question = (TextView) convertView.findViewById(R.id.tv_question);
                lt_answer = (EditText) convertView.findViewById(R.id.et_value);
                lt_answer.addTextChangedListener(new MyTextWatcher(position));
                break;
            case RADIOGROUP:
                rg_question = (TextView) convertView.findViewById(R.id.tv_question);
                rg_answer = (RadioGroup) convertView.findViewById(R.id.radiogroup);
                rg_answer.setOnCheckedChangeListener(new MyOnCheckedChangeListener(position));
                break;
            case SPINNER:
                sp_question = (TextView) convertView.findViewById(R.id.tv_value);
                sp_answer = (Spinner) convertView.findViewById(R.id.sp_value);
                sp_list_value = new ArrayList<>();
                sp_answer.setOnItemSelectedListener(new MyOnItemSelectedListener(position));
                break;
            case TEXTVIEW:
                tv_question = (TextView) convertView.findViewById(R.id.tv_question);
                tv_value = (TextView) convertView.findViewById(R.id.et_value);
                tv_unit = (TextView) convertView.findViewById(R.id.tv_unit);
                tv_value.setOnClickListener(new MyOnClickListener(position));
                break;
        }

    }
    //2.给控件赋值
    private void initData(String type, int position) {

        switch (type) {
            case EDITTEXT:
                try {
                    initEditTextLayout(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MAXTEXT:
                try {
                    initLableTextLayout(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case RADIOGROUP:
                try {
                    initRadioGroupLayout(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case SPINNER:
                try {
                    initSpinnerLayout(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case TEXTVIEW:
                try {
                    initTextViewLayout(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }

    }

    //1.EditText布局
    private void initEditTextLayout(int position) {
        et_question.setText(finalModel.getList_model().get(position).getLeftText());
        et_unit.setText(finalModel.getList_model().get(position).getRightText());
        if (isAdd) {

        } else {
            et_answer.setText(nullToStr(finalModel.getList_model().get(position).getMiddleText()));
        }

    }

    //2.段落式的EditText布局
    private void initLableTextLayout(int position) {
        lt_question.setText(finalModel.getList_model().get(position).getLeftText());
        if (isAdd) {
            if (finalModel.getList_model().get(position).getDicText() != null) {
                String str = "";
                for (int i = 0; i < finalModel.getList_model().get(position).getDicText().size(); i++) {
                    str += finalModel.getList_model().get(position).getDicText().get(i) + "\n";
                }
                lt_answer.setText(str);
            }
        } else {
            if (finalModel.getList_model().get(position).getMiddleText() != null) {
                lt_answer.setText(nullToStr(finalModel.getList_model().get(position).getMiddleText()));
            }
        }
    }

    //3.RadioGroup布局
    private void initRadioGroupLayout(int position) {
        rg_question.setText(finalModel.getList_model().get(position).getLeftText());
        for (int i = 0; i < rg_answer.getChildCount(); i++) {
            View view = rg_answer.getChildAt(i);
            if (view instanceof RadioButton) {
                ((RadioButton) view).setText(finalModel.getList_model().get(position).getDicText().get(i));
                if (isAdd){

                }else {
                    if (finalModel.getList_model().get(position).getMiddleText() != null
                            && finalModel.getList_model().get(position).getMiddleText().
                            equals(finalModel.getList_model().get(position).getDicText().get(i))) {
                        ((RadioButton) view).setChecked(true);
                    }
                }
            }
        }
    }

    //4.Spinner布局
    private void initSpinnerLayout(int position) {
        sp_question.setText(finalModel.getList_model().get(position).getLeftText());
        sp_list_value = finalModel.getList_model().get(position).getDicText();
        adapter = new SpinnerBaseAdapter(mContext, android.R.layout.simple_spinner_item, sp_answer, sp_list_value);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        sp_answer.setAdapter(adapter);
        if (isAdd) {

        } else {
            for (int i = 0; i < sp_list_value.size(); i++) {
                if (sp_list_value.get(i).equals(finalModel.getList_model().get(position).getMiddleText())) {
                    sp_answer.setSelection(i);
                }
            }
        }
    }

    //5.TextView布局
    private void initTextViewLayout(int position) {
        tv_question.setText(finalModel.getList_model().get(position).getLeftText());
        tv_unit.setText(finalModel.getList_model().get(position).getRightText());
        if (isAdd) {

        } else {
            tv_value.setText(nullToStr(finalModel.getList_model().get(position).getMiddleText()));
        }
    }

    //1.监听edittext
    private class MyTextWatcher implements TextWatcher{

        private int position;

        public MyTextWatcher(int position){
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isAdd = false;
            finalModel.getList_model().get(position).setMiddleText(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    //2.监听spinner
    private class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener{

        private int pi;

        public MyOnItemSelectedListener(int pi){
            this.pi = pi;
        }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            isAdd = false;
            finalModel.getList_model().get(pi).setMiddleText(sp_list_value.get(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    //3.监听radioGroup
    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{

        private int pi;

        public MyOnCheckedChangeListener(int pi){
            this.pi = pi;
        }
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            View view = group.findViewById(group.getCheckedRadioButtonId());
            if (view instanceof RadioButton){
                isAdd = false;
                finalModel.getList_model().get(pi).setMiddleText(((RadioButton) view).getText().toString());
            }
        }
    }
    //4.监听TextView
    private class MyOnClickListener implements View.OnClickListener{

        private int pi;

        public MyOnClickListener(int pi){
            this.pi = pi;
        }
        @Override
        public void onClick(View v) {
            final Calendar objTime = Calendar.getInstance();
            int iYear = objTime.get(Calendar.YEAR);
            int iMonth = objTime.get(Calendar.MONTH);
            int iDay = objTime.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(mContext, AlertDialog.THEME_HOLO_LIGHT,new MyOnDateSetListener(pi,v),
                    iYear, iMonth, iDay).show();
        }
    }

    /**
     * 4.1.DatePickerDialog监听
     */
    private class MyOnDateSetListener implements DatePickerDialog.OnDateSetListener{
        private int pi;
        private View v;
        public MyOnDateSetListener(int pi,View view){
            this.pi = pi;
            this.v = view;
        }
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            isAdd = false;
            finalModel.getList_model().get(pi).setMiddleText(year+"-"+add0(monthOfYear+1)+"-"+add0(dayOfMonth));
            if (v instanceof TextView){
                ((TextView) v).setText(year+"-"+add0(monthOfYear+1)+"-"+add0(dayOfMonth));
            }
        }
    }

    /**
     * 得到操作的对象
     * @return
     */
    public static NormalTableFinalModel getFinalModelValue(){

        return finalModel;
    }

    /**
     * 将字符串 “null”转化为空字符串“”
     * @param str
     * @return
     */
    private String nullToStr(String str){
        String s = str;
        if (str.equals("null")){
            s = "";
        }else {
            s = str;
        }
        return  s;
    }

    /**
     * 补零2015-6-8------>2015-06-08
     * @param i
     * @return
     */
    private String add0(int i){
        String s = ""+i;
        if (i<10){
            s = "0"+i;
        }
        return s;
    }
}
