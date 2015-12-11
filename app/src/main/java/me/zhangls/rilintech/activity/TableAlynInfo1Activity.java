package me.zhangls.rilintech.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.adapter.SpinnerBaseAdapter;
import me.zhangls.rilintech.manager.TableAlyn1Manager;
import me.zhangls.rilintech.model.TableAlynInfo1;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;
import me.zhangls.rilintech.utils.L;


/**
 * Created by YANG on 15/8/22.
 */

/**
 * Alyn 水中适应性评定详细信息类
 */
public class TableAlynInfo1Activity extends BaseActivity implements View.OnClickListener {

    /**
     * 最终的spinner数据
     */
    private List<String>list_s = new ArrayList<>();
    /**
     *
     */
    private RadioGroup rg1,rg2,rg3,rg4,rg5,rg6,rg7,rg8,rg9,rg10,rg11,rg12,rg13;
    /**
     * UUID
     */
    private String uuid_s;
    /**
     * SharedPreferences里保存的值
     */
    private String instruction;
    /**
     * 计算总分
     */
    private Button alyn1_total_scores_bt, alyn1_total_scores_centesmal_bt;
    /**
     * 总分
     */
    private TextView alyn1_total_scores_tv, alyn1_total_scores_centesmal_tv;
    /**
     * 选中的答案
     */
    private String answer1, answer2, answer3, answer4, answer5, answer6, answer7, answer8, answer9, answer10,
            answer11, answer13, answer12;
    /**
     * 计分器
     */
    private int counts = 0;
    private int counts1 = 0;
    private int counts2 = 0;
    private int counts3 = 0;
    private int counts4 = 0;
    private int counts5 = 0;
    private int counts6 = 0;
    private int counts7 = 0;
    private int counts8 = 0;
    private int counts9 = 0;
    private int counts10 = 0;
    private int counts11 = 0;
    private int counts12 = 0;
    private int counts13 = 0;
    /**
     * 评定说明当前选中的值
     */
    private String spinner_value;
    /**
     * spinner加载器
     */
    private ArrayAdapter<String> adapter;
    /**
     * 评定说明的选项
     */
    private String[] arr_instructions = new String[]{"初评", "中评1", "中评2", "中评3", "中评4", "中评5", "末评"};
    /**
     * 评定说明
     */
    private Spinner alyn1_instructions;
    /**
     * 时间选择器
     */
    private TimePopupWindow pwTime;
    private String msg = "请检查是否填写完整...";
    /**
     * 是否新建
     */
    private boolean isAdd;
    /**
     * 返回键
     */
    private ImageView key_back;
    /**
     * 保存键
     */
    private ImageView key_save;
    /**
     * 编辑键
     */
    private ImageView key_edit;
    /**
     * 评定日期
     */
    private TextView alyn1_date_tv;
    /**
     * 所有的选项
     */
    private RadioButton alyn1_title1_rb1, alyn1_title1_rb2, alyn1_title1_rb3, alyn1_title1_rb4;
    private RadioButton alyn1_title2_rb1, alyn1_title2_rb2, alyn1_title2_rb3, alyn1_title2_rb4;
    private RadioButton alyn1_title3_rb1, alyn1_title3_rb2, alyn1_title3_rb3, alyn1_title3_rb4;
    private RadioButton alyn1_title4_rb1, alyn1_title4_rb2, alyn1_title4_rb3, alyn1_title4_rb4;
    private RadioButton alyn1_title5_rb1, alyn1_title5_rb2, alyn1_title5_rb3, alyn1_title5_rb4;
    private RadioButton alyn1_title6_rb1, alyn1_title6_rb2, alyn1_title6_rb3, alyn1_title6_rb4;
    private RadioButton alyn1_title7_rb1, alyn1_title7_rb2, alyn1_title7_rb3, alyn1_title7_rb4;
    private RadioButton alyn1_title8_rb1, alyn1_title8_rb2, alyn1_title8_rb3, alyn1_title8_rb4;
    private RadioButton alyn1_title9_rb1, alyn1_title9_rb2, alyn1_title9_rb3, alyn1_title9_rb4;
    private RadioButton alyn1_title10_rb1, alyn1_title10_rb2, alyn1_title10_rb3, alyn1_title10_rb4;
    private RadioButton alyn1_title11_rb1, alyn1_title11_rb2, alyn1_title11_rb3, alyn1_title11_rb4;
    private RadioButton alyn1_title12_rb1, alyn1_title12_rb2, alyn1_title12_rb3, alyn1_title12_rb4;
    private RadioButton alyn1_title13_rb1, alyn1_title13_rb2, alyn1_title13_rb3, alyn1_title13_rb4;
    /**
     * 所有的选项集
     */
    private List<RadioButton> list_alyn1_title1 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title2 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title3 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title4 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title5 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title6 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title7 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title8 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title9 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title10 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title11 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title12 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title13 = new ArrayList<RadioButton>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("instructions", Context.MODE_PRIVATE);
        instruction = sharedPreferences.getString("instruction", "");
        L.d(instruction);

        Intent intent = getIntent();
        isAdd = intent.getBooleanExtra("isAdd", true);

        setContentView(R.layout.activity_table_alyn_info1);

        queryInstructions();
        initView();

    }

    /**
     * 去除掉已经有的评定说明
     */
    public void queryInstructions() {
        TableAlyn1Manager manager = new TableAlyn1Manager(this);
        manager.openDataBase();
        List<String>list = manager.getAllInstructions("admin");


        for(String s:arr_instructions){
            list_s.add(s);
        }

        for(int i=0;i<list.size();i++){
            if(list_s.contains(list.get(i))){
                list_s.remove(list.get(i));
            }
        }
        if(isAdd == false){//如果是编辑界面，需要把当前的评定说明加回去
            list_s.add(0, instruction);
        }
        //arr_instructions = new String[list_s.size()];
        //list_s.toArray(arr_instructions);

    }

    /**
     * 初始化控件
     */
    private void initView() {
        rg1 = (RadioGroup) findViewById(R.id.alyn1_title1_rg);
        rg2 = (RadioGroup) findViewById(R.id.alyn1_title2_rg);
        rg3 = (RadioGroup) findViewById(R.id.alyn1_title3_rg);
        rg4 = (RadioGroup) findViewById(R.id.alyn1_title4_rg);
        rg5 = (RadioGroup) findViewById(R.id.alyn1_title5_rg);
        rg6 = (RadioGroup) findViewById(R.id.alyn1_title6_rg);
        rg7 = (RadioGroup) findViewById(R.id.alyn1_title7_rg);
        rg8 = (RadioGroup) findViewById(R.id.alyn1_title8_rg);
        rg9 = (RadioGroup) findViewById(R.id.alyn1_title9_rg);
        rg10 = (RadioGroup) findViewById(R.id.alyn1_title10_rg);
        rg11 = (RadioGroup) findViewById(R.id.alyn1_title11_rg);
        rg12 = (RadioGroup) findViewById(R.id.alyn1_title12_rg);
        rg13 = (RadioGroup) findViewById(R.id.alyn1_title13_rg);

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo1Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer1 = s.getText().toString();
                counts1 = Integer.parseInt(answer1.substring(0, 1));
            }
        });
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo1Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer2 = s.getText().toString();
                counts2 = Integer.parseInt(answer2.substring(0, 1));
            }
        });
        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo1Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer3 = s.getText().toString();
                counts3 = Integer.parseInt(answer3.substring(0, 1));
            }
        });
        rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo1Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer4 = s.getText().toString();
                counts4 = Integer.parseInt(answer4.substring(0, 1));
            }
        });
        rg5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo1Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer5 = s.getText().toString();
                counts5 = Integer.parseInt(answer5.substring(0, 1));
            }
        });
        rg6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo1Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer6 = s.getText().toString();
                counts6 = Integer.parseInt(answer6.substring(0, 1));
            }
        });
        rg7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo1Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer7 = s.getText().toString();
                counts7 = Integer.parseInt(answer7.substring(0, 1));
            }
        });
        rg8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo1Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer8 = s.getText().toString();
                counts8 = Integer.parseInt(answer8.substring(0, 1));
            }
        });
        rg9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo1Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer9 = s.getText().toString();
                counts9 = Integer.parseInt(answer9.substring(0, 1));
            }
        });
        rg10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo1Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer10 = s.getText().toString();
                counts10 = Integer.parseInt(answer10.substring(0, 1));
            }
        });
        rg11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo1Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer11 = s.getText().toString();
                counts11 = Integer.parseInt(answer11.substring(0, 1));
            }
        });
        rg12.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo1Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer12 = s.getText().toString();
                counts12 = Integer.parseInt(answer12.substring(0, 1));
            }
        });
        rg13.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo1Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer13 = s.getText().toString();
                counts13 = Integer.parseInt(answer13.substring(0, 1));
            }
        });



        key_back = (ImageView) findViewById(R.id.tittle_back);
        key_back.setOnClickListener(this);
        key_save = (ImageView) findViewById(R.id.patient_save);
        key_save.setOnClickListener(this);
        key_edit = (ImageView) findViewById(R.id.patient_edit);
        key_edit.setOnClickListener(this);

        alyn1_date_tv = (TextView) findViewById(R.id.alyn1_date_tv);
        alyn1_date_tv.setOnClickListener(this);
        //时间选择器
        pwTime = new TimePopupWindow(this, TimePopupWindow.Type.YEAR_MONTH_DAY);
        pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                alyn1_date_tv.setText(getTime(date));
            }
        });
        pwTime.setCyclic(true);

        //评定说明
        alyn1_instructions = (Spinner) findViewById(R.id.medical_history_relator_edit);

        adapter = new SpinnerBaseAdapter(this,android.R.layout.simple_spinner_item,alyn1_instructions,list_s);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        alyn1_instructions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_value = list_s.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        alyn1_instructions.setAdapter(adapter);


        alyn1_title1_rb1 = (RadioButton) findViewById(R.id.alyn1_title1_rb1);
        alyn1_title1_rb2 = (RadioButton) findViewById(R.id.alyn1_title1_rb2);
        alyn1_title1_rb3 = (RadioButton) findViewById(R.id.alyn1_title1_rb3);
        alyn1_title1_rb4 = (RadioButton) findViewById(R.id.alyn1_title1_rb4);
        list_alyn1_title1.add(alyn1_title1_rb1);
        list_alyn1_title1.add(alyn1_title1_rb2);
        list_alyn1_title1.add(alyn1_title1_rb3);
        list_alyn1_title1.add(alyn1_title1_rb4);

        alyn1_title2_rb1 = (RadioButton) findViewById(R.id.alyn1_title2_rb1);
        alyn1_title2_rb2 = (RadioButton) findViewById(R.id.alyn1_title2_rb2);
        alyn1_title2_rb3 = (RadioButton) findViewById(R.id.alyn1_title2_rb3);
        alyn1_title2_rb4 = (RadioButton) findViewById(R.id.alyn1_title2_rb4);
        list_alyn1_title2.add(alyn1_title2_rb1);
        list_alyn1_title2.add(alyn1_title2_rb2);
        list_alyn1_title2.add(alyn1_title2_rb3);
        list_alyn1_title2.add(alyn1_title2_rb4);

        alyn1_title3_rb1 = (RadioButton) findViewById(R.id.alyn1_title3_rb1);
        alyn1_title3_rb2 = (RadioButton) findViewById(R.id.alyn1_title3_rb2);
        alyn1_title3_rb3 = (RadioButton) findViewById(R.id.alyn1_title3_rb3);
        alyn1_title3_rb4 = (RadioButton) findViewById(R.id.alyn1_title3_rb4);
        list_alyn1_title3.add(alyn1_title3_rb1);
        list_alyn1_title3.add(alyn1_title3_rb2);
        list_alyn1_title3.add(alyn1_title3_rb3);
        list_alyn1_title3.add(alyn1_title3_rb4);

        alyn1_title4_rb1 = (RadioButton) findViewById(R.id.alyn1_title4_rb1);
        alyn1_title4_rb2 = (RadioButton) findViewById(R.id.alyn1_title4_rb2);
        alyn1_title4_rb3 = (RadioButton) findViewById(R.id.alyn1_title4_rb3);
        alyn1_title4_rb4 = (RadioButton) findViewById(R.id.alyn1_title4_rb4);
        list_alyn1_title4.add(alyn1_title4_rb1);
        list_alyn1_title4.add(alyn1_title4_rb2);
        list_alyn1_title4.add(alyn1_title4_rb3);
        list_alyn1_title4.add(alyn1_title4_rb4);

        alyn1_title5_rb1 = (RadioButton) findViewById(R.id.alyn1_title5_rb1);
        alyn1_title5_rb2 = (RadioButton) findViewById(R.id.alyn1_title5_rb2);
        alyn1_title5_rb3 = (RadioButton) findViewById(R.id.alyn1_title5_rb3);
        alyn1_title5_rb4 = (RadioButton) findViewById(R.id.alyn1_title5_rb4);
        list_alyn1_title5.add(alyn1_title5_rb1);
        list_alyn1_title5.add(alyn1_title5_rb2);
        list_alyn1_title5.add(alyn1_title5_rb3);
        list_alyn1_title5.add(alyn1_title5_rb4);

        alyn1_title6_rb1 = (RadioButton) findViewById(R.id.alyn1_title6_rb1);
        alyn1_title6_rb2 = (RadioButton) findViewById(R.id.alyn1_title6_rb2);
        alyn1_title6_rb3 = (RadioButton) findViewById(R.id.alyn1_title6_rb3);
        alyn1_title6_rb4 = (RadioButton) findViewById(R.id.alyn1_title6_rb4);
        list_alyn1_title6.add(alyn1_title6_rb1);
        list_alyn1_title6.add(alyn1_title6_rb2);
        list_alyn1_title6.add(alyn1_title6_rb3);
        list_alyn1_title6.add(alyn1_title6_rb4);

        alyn1_title7_rb1 = (RadioButton) findViewById(R.id.alyn1_title7_rb1);
        alyn1_title7_rb2 = (RadioButton) findViewById(R.id.alyn1_title7_rb2);
        alyn1_title7_rb3 = (RadioButton) findViewById(R.id.alyn1_title7_rb3);
        alyn1_title7_rb4 = (RadioButton) findViewById(R.id.alyn1_title7_rb4);
        list_alyn1_title7.add(alyn1_title7_rb1);
        list_alyn1_title7.add(alyn1_title7_rb2);
        list_alyn1_title7.add(alyn1_title7_rb3);
        list_alyn1_title7.add(alyn1_title7_rb4);

        alyn1_title8_rb1 = (RadioButton) findViewById(R.id.alyn1_title8_rb1);
        alyn1_title8_rb2 = (RadioButton) findViewById(R.id.alyn1_title8_rb2);
        alyn1_title8_rb3 = (RadioButton) findViewById(R.id.alyn1_title8_rb3);
        alyn1_title8_rb4 = (RadioButton) findViewById(R.id.alyn1_title8_rb4);
        list_alyn1_title8.add(alyn1_title8_rb1);
        list_alyn1_title8.add(alyn1_title8_rb2);
        list_alyn1_title8.add(alyn1_title8_rb3);
        list_alyn1_title8.add(alyn1_title8_rb4);

        alyn1_title9_rb1 = (RadioButton) findViewById(R.id.alyn1_title9_rb1);
        alyn1_title9_rb2 = (RadioButton) findViewById(R.id.alyn1_title9_rb2);
        alyn1_title9_rb3 = (RadioButton) findViewById(R.id.alyn1_title9_rb3);
        alyn1_title9_rb4 = (RadioButton) findViewById(R.id.alyn1_title9_rb4);
        list_alyn1_title9.add(alyn1_title9_rb1);
        list_alyn1_title9.add(alyn1_title9_rb2);
        list_alyn1_title9.add(alyn1_title9_rb3);
        list_alyn1_title9.add(alyn1_title9_rb4);

        alyn1_title10_rb1 = (RadioButton) findViewById(R.id.alyn1_title10_rb1);
        alyn1_title10_rb2 = (RadioButton) findViewById(R.id.alyn1_title10_rb2);
        alyn1_title10_rb3 = (RadioButton) findViewById(R.id.alyn1_title10_rb3);
        alyn1_title10_rb4 = (RadioButton) findViewById(R.id.alyn1_title10_rb4);
        list_alyn1_title10.add(alyn1_title10_rb1);
        list_alyn1_title10.add(alyn1_title10_rb2);
        list_alyn1_title10.add(alyn1_title10_rb3);
        list_alyn1_title10.add(alyn1_title10_rb4);

        alyn1_title11_rb1 = (RadioButton) findViewById(R.id.alyn1_title11_rb1);
        alyn1_title11_rb2 = (RadioButton) findViewById(R.id.alyn1_title11_rb2);
        alyn1_title11_rb3 = (RadioButton) findViewById(R.id.alyn1_title11_rb3);
        alyn1_title11_rb4 = (RadioButton) findViewById(R.id.alyn1_title11_rb4);
        list_alyn1_title11.add(alyn1_title11_rb1);
        list_alyn1_title11.add(alyn1_title11_rb2);
        list_alyn1_title11.add(alyn1_title11_rb3);
        list_alyn1_title11.add(alyn1_title11_rb4);

        alyn1_title12_rb1 = (RadioButton) findViewById(R.id.alyn1_title12_rb1);
        alyn1_title12_rb2 = (RadioButton) findViewById(R.id.alyn1_title12_rb2);
        alyn1_title12_rb3 = (RadioButton) findViewById(R.id.alyn1_title12_rb3);
        alyn1_title12_rb4 = (RadioButton) findViewById(R.id.alyn1_title12_rb4);
        list_alyn1_title12.add(alyn1_title12_rb1);
        list_alyn1_title12.add(alyn1_title12_rb2);
        list_alyn1_title12.add(alyn1_title12_rb3);
        list_alyn1_title12.add(alyn1_title12_rb4);

        alyn1_title13_rb1 = (RadioButton) findViewById(R.id.alyn1_title13_rb1);
        alyn1_title13_rb2 = (RadioButton) findViewById(R.id.alyn1_title13_rb2);
        alyn1_title13_rb3 = (RadioButton) findViewById(R.id.alyn1_title13_rb3);
        alyn1_title13_rb4 = (RadioButton) findViewById(R.id.alyn1_title13_rb4);
        list_alyn1_title13.add(alyn1_title13_rb1);
        list_alyn1_title13.add(alyn1_title13_rb2);
        list_alyn1_title13.add(alyn1_title13_rb3);
        list_alyn1_title13.add(alyn1_title13_rb4);

        alyn1_total_scores_bt = (Button) findViewById(R.id.alyn1_total_scores_bt);
        alyn1_total_scores_bt.setOnClickListener(this);
        alyn1_total_scores_tv = (TextView) findViewById(R.id.alyn1_total_scores_tv);

        alyn1_total_scores_centesmal_bt = (Button) findViewById(R.id.alyn1_total_scores_centesmal_bt);
        alyn1_total_scores_centesmal_bt.setOnClickListener(this);
        alyn1_total_scores_centesmal_tv = (TextView) findViewById(R.id.alyn1_total_scores_centesmal_tv);

        if(isAdd == false){
            setData();
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tittle_back://返回
                this.finish();
                break;
            case R.id.patient_save://保存
                saveAlyn();
                break;
            case R.id.patient_edit://编辑

                break;
            case R.id.alyn1_date_tv://评定日期
                pwTime.showAtLocation(alyn1_date_tv, Gravity.BOTTOM, 0, 0, new Date());
                break;
            case R.id.alyn1_total_scores_bt://显示总分
                counts = counts1 + counts2 + counts3 + counts4 + counts5 + counts6 + counts7 + counts8 + counts9 + counts10 +
                        counts11 + counts12 + counts13;
                alyn1_total_scores_tv.setText(counts + "");
                break;
            case R.id.alyn1_total_scores_centesmal_bt://显示百分制得分
                counts = counts1 + counts2 + counts3 + counts4 + counts5 + counts6 + counts7 + counts8 + counts9 + counts10 +
                        counts11 + counts12 + counts13;
                int i = counts * 100 / 52;
                alyn1_total_scores_centesmal_tv.setText(i + "");
        }

    }

    /**
     * 新建数据保存
     */
    private void saveAlyn() {
        TableAlynInfo1 info = null;
        if (dataIsEmpty()) {

        } else {
            counts = counts1 + counts2 + counts3 + counts4 + counts5 + counts6 + counts7 + counts8 + counts9 + counts10 +
                    counts11 + counts12 + counts13;
            String uuid = UUID.randomUUID().toString();
            info = new TableAlynInfo1();
            info.setFlag("new");
            info.setAlyn_uuid(uuid);
            info.setDate(alyn1_date_tv.getText().toString());
            info.setInstructions(spinner_value);
            info.setUser_name("admin");
            info.setScores(counts);
            info.setScore_t(counts * 100 / 52);
            L.d(counts + "");
            info.setAnswer1(answer1);
            info.setAnswer2(answer2);
            info.setAnswer3(answer3);
            info.setAnswer4(answer4);
            info.setAnswer5(answer5);
            info.setAnswer6(answer6);
            info.setAnswer7(answer7);
            info.setAnswer8(answer8);
            info.setAnswer9(answer9);
            info.setAnswer10(answer10);
            info.setAnswer11(answer11);
            info.setAnswer12(answer12);
            info.setAnswer13(answer13);

            TableAlyn1Manager manager = new TableAlyn1Manager(this);
            manager.openDataBase();
            if(isAdd == false){
                info.setAlyn_uuid(uuid_s);
                manager.updateOne(uuid_s,info);
            }else {
                manager.insertOne(info);
            }
            manager.closeDataBase();

            this.finish();
        }

    }

    /**
     * 编辑界面给每个radiogroup赋值
     * @param list
     * @param s
     */
    private void setRadioButtonData(List<RadioButton> list, String s){
        for(int i=0;i<list.size();i++){
            if(s.equals(list.get(i).getText())){
                list.get(i).setChecked(true);
            }
        }
    }

    /**
     * 给所有控件赋值
     */
    private void setData(){

        TableAlyn1Manager manager = new TableAlyn1Manager(this);
        TableAlynInfo1 info = new TableAlynInfo1();
        manager.openDataBase();
        info = manager.queryOne(instruction, "admin");
        manager.closeDataBase();
        uuid_s = info.getAlyn_uuid();
        for (int i = 0; i < list_s.size(); i++) {
            if ((list_s.get(i)).equals(info.getInstructions())) {
                alyn1_instructions.setSelection(i);
            }
        }
        alyn1_date_tv.setText(info.getDate());
        alyn1_total_scores_tv.setText(info.getScores()+"");
        alyn1_total_scores_centesmal_tv.setText(info.getScore_t() + "");

        setRadioButtonData(list_alyn1_title1, info.getAnswer1());
        setRadioButtonData(list_alyn1_title2,info.getAnswer2());
        setRadioButtonData(list_alyn1_title3,info.getAnswer3());
        setRadioButtonData(list_alyn1_title4,info.getAnswer4());
        setRadioButtonData(list_alyn1_title5,info.getAnswer5());
        setRadioButtonData(list_alyn1_title6,info.getAnswer6());
        setRadioButtonData(list_alyn1_title7,info.getAnswer7());
        setRadioButtonData(list_alyn1_title8,info.getAnswer8());
        setRadioButtonData(list_alyn1_title9,info.getAnswer9());
        setRadioButtonData(list_alyn1_title10,info.getAnswer10());
        setRadioButtonData(list_alyn1_title11,info.getAnswer11());
        setRadioButtonData(list_alyn1_title12,info.getAnswer12());
        setRadioButtonData(list_alyn1_title13,info.getAnswer13());
    }

    /**
     * 判断RadioButton是否有选择，没做出选择返回值false
     *
     * @param tds
     * @return
     */
    boolean radioButtonIsChecked(List<RadioButton> tds) {
        boolean flag = false;
        for (RadioButton cbx : tds) {
            if (cbx.isChecked()) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 判断是否有没有完整填写数据
     *
     * @return
     */
    boolean dataIsEmpty() {
        if (TextUtils.isEmpty(alyn1_date_tv.getText().toString())) {
            MyToast("请选择评定日期...");
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title1) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title2) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title3) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title4) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title5) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title6) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title7) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title8) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title9) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title10) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title11) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title12) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title13) == false) {
            MyToast(msg);
            return true;
        } else {
            return false;
        }
    }


    /**
     * 定制提示语
     *
     * @param str
     */
    void MyToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取系统当前时间
     *
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }


}
