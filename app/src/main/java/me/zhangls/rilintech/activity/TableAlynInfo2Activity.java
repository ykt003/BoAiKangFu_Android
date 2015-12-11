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
import me.zhangls.rilintech.manager.TableAlyn2Manager;
import me.zhangls.rilintech.model.TableAlynInfo2;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;


/**
 * Created by YANG on 15/8/22.
 */

/**
 * Alyn 水中适应性评定详细信息类
 */
public class TableAlynInfo2Activity extends BaseActivity implements View.OnClickListener {


    /**
     * 除去已经存在表的数据后得spinner数据
     */
    private List<String>list_s = new ArrayList<>();
    /**
     *
     */
    private RadioGroup rg1,rg2,rg3,rg4,rg5,rg6,rg7,rg8,rg9,rg10,rg11,rg12,rg13,rg14,
            rg15,rg16,rg17,rg18,rg19,rg20,rg21,rg22,rg23,rg24,rg25,rg26,rg27;
    /**
     * UUID
     */
    private String uuid_s;
    /**
     * 是否新建
     */
    private boolean isAdd;
    /**
     * SharedPreferences里保存的值
     */
    private String instruction;
    /**
     * 计算总分
     */
    private Button alyn2_total_scores_bt, alyn2_total_scores_centesmal_bt;
    /**
     * 总分
     */
    private TextView alyn2_total_scores_tv, alyn2_total_scores_centesmal_tv;
    /**
     * 选中的答案
     */
    private String answer1, answer2, answer3, answer4, answer5, answer6, answer7, answer8, answer9, answer10,
            answer11, answer13, answer12, answer14, answer15, answer16, answer17, answer18, answer19, answer20,
            answer21, answer22, answer23, answer24, answer25, answer26, answer27;
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
    private int counts14 = 0;
    private int counts15 = 0;
    private int counts16 = 0;
    private int counts17 = 0;
    private int counts18 = 0;
    private int counts19 = 0;
    private int counts20 = 0;
    private int counts21 = 0;
    private int counts22 = 0;
    private int counts23 = 0;
    private int counts24 = 0;
    private int counts25 = 0;
    private int counts26 = 0;
    private int counts27 = 0;
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
    private Spinner alyn2_instructions;
    /**
     * 时间选择器
     */
    private TimePopupWindow pwTime;
    private String msg = "请检查是否填写完整...";
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
    private TextView alyn2_date_tv;
    /**
     * 所有的选项
     */
    private RadioButton alyn1_title1_rb1, alyn1_title1_rb2, alyn1_title1_rb3, alyn1_title1_rb4;
    private RadioButton alyn1_title2_rb1, alyn1_title2_rb2, alyn1_title2_rb3, alyn1_title2_rb4, alyn1_title2_rb5;
    private RadioButton alyn1_title3_rb1, alyn1_title3_rb2, alyn1_title3_rb3, alyn1_title3_rb4, alyn1_title3_rb5;
    private RadioButton alyn1_title4_rb1, alyn1_title4_rb2, alyn1_title4_rb3, alyn1_title4_rb4, alyn1_title4_rb5;
    private RadioButton alyn1_title5_rb1, alyn1_title5_rb2, alyn1_title5_rb3, alyn1_title5_rb4, alyn1_title5_rb5;
    private RadioButton alyn1_title6_rb1, alyn1_title6_rb2, alyn1_title6_rb3, alyn1_title6_rb4, alyn1_title6_rb5;
    private RadioButton alyn1_title7_rb1, alyn1_title7_rb2, alyn1_title7_rb3, alyn1_title7_rb4, alyn1_title7_rb5;
    private RadioButton alyn1_title8_rb1, alyn1_title8_rb2, alyn1_title8_rb3, alyn1_title8_rb4, alyn1_title8_rb5;
    private RadioButton alyn1_title9_rb1, alyn1_title9_rb2, alyn1_title9_rb3, alyn1_title9_rb4, alyn1_title9_rb5;
    private RadioButton alyn1_title10_rb1, alyn1_title10_rb2, alyn1_title10_rb3, alyn1_title10_rb4, alyn1_title10_rb5;
    private RadioButton alyn1_title11_rb1, alyn1_title11_rb2, alyn1_title11_rb3, alyn1_title11_rb4, alyn1_title11_rb5;
    private RadioButton alyn1_title12_rb1, alyn1_title12_rb2, alyn1_title12_rb3, alyn1_title12_rb4, alyn1_title12_rb5;
    private RadioButton alyn1_title13_rb1, alyn1_title13_rb2, alyn1_title13_rb3, alyn1_title13_rb4, alyn1_title13_rb5;
    private RadioButton alyn1_title14_rb1, alyn1_title14_rb2, alyn1_title14_rb3, alyn1_title14_rb4, alyn1_title14_rb5;
    private RadioButton alyn1_title15_rb1, alyn1_title15_rb2, alyn1_title15_rb3, alyn1_title15_rb4, alyn1_title15_rb5;
    private RadioButton alyn1_title16_rb1, alyn1_title16_rb2, alyn1_title16_rb3, alyn1_title16_rb4, alyn1_title16_rb5;
    private RadioButton alyn1_title17_rb1, alyn1_title17_rb2, alyn1_title17_rb3, alyn1_title17_rb4, alyn1_title17_rb5;
    private RadioButton alyn1_title18_rb1, alyn1_title18_rb2, alyn1_title18_rb3, alyn1_title18_rb4, alyn1_title18_rb5;
    private RadioButton alyn1_title19_rb1, alyn1_title19_rb2, alyn1_title19_rb3, alyn1_title19_rb4, alyn1_title19_rb5;
    private RadioButton alyn1_title20_rb1, alyn1_title20_rb2, alyn1_title20_rb3, alyn1_title20_rb4, alyn1_title20_rb5;
    private RadioButton alyn1_title21_rb1, alyn1_title21_rb2, alyn1_title21_rb3, alyn1_title21_rb4, alyn1_title21_rb5;
    private RadioButton alyn1_title22_rb1, alyn1_title22_rb2, alyn1_title22_rb3, alyn1_title22_rb4, alyn1_title22_rb5;
    private RadioButton alyn1_title23_rb1, alyn1_title23_rb2, alyn1_title23_rb3, alyn1_title23_rb4, alyn1_title23_rb5;
    private RadioButton alyn1_title24_rb1, alyn1_title24_rb2, alyn1_title24_rb3, alyn1_title24_rb4, alyn1_title24_rb5;
    private RadioButton alyn1_title25_rb1, alyn1_title25_rb2, alyn1_title25_rb3, alyn1_title25_rb4, alyn1_title25_rb5;
    private RadioButton alyn1_title26_rb1, alyn1_title26_rb2, alyn1_title26_rb3, alyn1_title26_rb4, alyn1_title26_rb5;
    private RadioButton alyn1_title27_rb1, alyn1_title27_rb2, alyn1_title27_rb3, alyn1_title27_rb4, alyn1_title27_rb5;
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
    private List<RadioButton> list_alyn1_title14 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title15 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title16 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title17 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title18 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title19 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title20 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title21 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title22 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title23 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title24 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title25 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title26 = new ArrayList<RadioButton>();
    private List<RadioButton> list_alyn1_title27 = new ArrayList<RadioButton>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("instructions", Context.MODE_PRIVATE);
        instruction = sharedPreferences.getString("instruction", "");

        Intent intent = getIntent();
        isAdd = intent.getBooleanExtra("isAdd", true);

        setContentView(R.layout.activity_table_alyn_info2);

        queryInstructions();
        initView();

    }
    /**
     * 去除掉已经有的评定说明
     */
    public void queryInstructions() {
        TableAlyn2Manager manager = new TableAlyn2Manager(this);
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

        rg1 = (RadioGroup) findViewById(R.id.alyn2_title1_rg);
        rg2 = (RadioGroup) findViewById(R.id.alyn2_title2_rg);
        rg3 = (RadioGroup) findViewById(R.id.alyn2_title3_rg);
        rg4 = (RadioGroup) findViewById(R.id.alyn2_title4_rg);
        rg5 = (RadioGroup) findViewById(R.id.alyn2_title5_rg);
        rg6 = (RadioGroup) findViewById(R.id.alyn2_title6_rg);
        rg7 = (RadioGroup) findViewById(R.id.alyn2_title7_rg);
        rg8 = (RadioGroup) findViewById(R.id.alyn2_title8_rg);
        rg9 = (RadioGroup) findViewById(R.id.alyn2_title9_rg);
        rg10 = (RadioGroup) findViewById(R.id.alyn2_title10_rg);
        rg11 = (RadioGroup) findViewById(R.id.alyn2_title11_rg);
        rg12 = (RadioGroup) findViewById(R.id.alyn2_title12_rg);
        rg13 = (RadioGroup) findViewById(R.id.alyn2_title13_rg);
        rg14 = (RadioGroup) findViewById(R.id.alyn2_title14_rg);
        rg15 = (RadioGroup) findViewById(R.id.alyn2_title15_rg);
        rg16 = (RadioGroup) findViewById(R.id.alyn2_title16_rg);
        rg17 = (RadioGroup) findViewById(R.id.alyn2_title17_rg);
        rg18 = (RadioGroup) findViewById(R.id.alyn2_title18_rg);
        rg19 = (RadioGroup) findViewById(R.id.alyn2_title19_rg);
        rg20 = (RadioGroup) findViewById(R.id.alyn2_title20_rg);
        rg21 = (RadioGroup) findViewById(R.id.alyn2_title21_rg);
        rg22 = (RadioGroup) findViewById(R.id.alyn2_title22_rg);
        rg23 = (RadioGroup) findViewById(R.id.alyn2_title23_rg);
        rg24 = (RadioGroup) findViewById(R.id.alyn2_title24_rg);
        rg25 = (RadioGroup) findViewById(R.id.alyn2_title25_rg);
        rg26 = (RadioGroup) findViewById(R.id.alyn2_title26_rg);
        rg27 = (RadioGroup) findViewById(R.id.alyn2_title27_rg);

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer1 = s.getText().toString();
                counts1 = checkedX(answer1.substring(0, 1));
            }
        });
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer2 = s.getText().toString();
                counts2 = checkedX(answer2.substring(0, 1));
            }
        });
        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer3 = s.getText().toString();
                counts3 = checkedX(answer3.substring(0, 1));
            }
        });
        rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer4 = s.getText().toString();
                counts4 = checkedX(answer4.substring(0, 1));
            }
        });
        rg5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer5 = s.getText().toString();
                counts5 = checkedX(answer5.substring(0, 1));
            }
        });
        rg6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer6 = s.getText().toString();
                counts6 = checkedX(answer6.substring(0, 1));
            }
        });
        rg7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer7 = s.getText().toString();
                counts7 = checkedX(answer7.substring(0, 1));
            }
        });
        rg8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer8 = s.getText().toString();
                counts8 = checkedX(answer8.substring(0, 1));
            }
        });
        rg9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer9 = s.getText().toString();
                counts9 = checkedX(answer9.substring(0, 1));
            }
        });
        rg10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer10 = s.getText().toString();
                counts10 = checkedX(answer10.substring(0, 1));
            }
        });
        rg11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer11 = s.getText().toString();
                counts11 = checkedX(answer11.substring(0, 1));
            }
        });
        rg12.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer12 = s.getText().toString();
                counts12 = checkedX(answer12.substring(0, 1));
            }
        });
        rg13.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer13 = s.getText().toString();
                counts13 = checkedX(answer13.substring(0, 1));
            }
        });
        rg14.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer14 = s.getText().toString();
                counts14 = checkedX(answer14.substring(0, 1));
            }
        });
        rg15.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer15 = s.getText().toString();
                counts15 = checkedX(answer15.substring(0, 1));
            }
        });
        rg16.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer16 = s.getText().toString();
                counts16 = checkedX(answer16.substring(0, 1));
            }
        });
        rg17.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer17 = s.getText().toString();
                counts17 = checkedX(answer17.substring(0, 1));
            }
        });
        rg18.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer18 = s.getText().toString();
                counts18 = checkedX(answer18.substring(0, 1));
            }
        });
        rg19.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer19 = s.getText().toString();
                counts19 = checkedX(answer19.substring(0, 1));
            }
        });
        rg20.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer20 = s.getText().toString();
                counts20 = checkedX(answer20.substring(0, 1));
            }
        });
        rg21.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer21 = s.getText().toString();
                counts21 = checkedX(answer21.substring(0, 1));
            }
        });
        rg22.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer22 = s.getText().toString();
                counts22 = checkedX(answer22.substring(0, 1));
            }
        });
        rg23.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer23 = s.getText().toString();
                counts23 = checkedX(answer23.substring(0, 1));
            }
        });
        rg24.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer24 = s.getText().toString();
                counts24 = checkedX(answer24.substring(0, 1));
            }
        });
        rg25.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer25 = s.getText().toString();
                counts25 = checkedX(answer25.substring(0, 1));
            }
        });
        rg26.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer26 = s.getText().toString();
                counts26 = checkedX(answer26.substring(0, 1));
            }
        });
        rg27.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableAlynInfo2Activity.this.findViewById(group.getCheckedRadioButtonId());
                answer27 = s.getText().toString();
                counts27 = checkedX(answer27.substring(0, 1));
            }
        });


        key_back = (ImageView) findViewById(R.id.tittle_back);
        key_back.setOnClickListener(this);
        key_save = (ImageView) findViewById(R.id.patient_save);
        key_save.setOnClickListener(this);
        key_edit = (ImageView) findViewById(R.id.patient_edit);
        key_edit.setOnClickListener(this);

        alyn2_date_tv = (TextView) findViewById(R.id.alyn2_date_tv);
        alyn2_date_tv.setOnClickListener(this);
        //时间选择器
        pwTime = new TimePopupWindow(this, TimePopupWindow.Type.YEAR_MONTH_DAY);
        pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                alyn2_date_tv.setText(getTime(date));
            }
        });
        pwTime.setCyclic(true);

        //评定说明
        alyn2_instructions = (Spinner) findViewById(R.id.medical_history_relator_edit);
        adapter = new SpinnerBaseAdapter(this,android.R.layout.simple_spinner_item,alyn2_instructions,list_s);
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr_instructions);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alyn2_instructions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //spinner_value = arr_instructions[position];
                spinner_value = list_s.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        alyn2_instructions.setAdapter(adapter);


        alyn1_title1_rb1 = (RadioButton) findViewById(R.id.alyn2_title1_rb1);
        alyn1_title1_rb2 = (RadioButton) findViewById(R.id.alyn2_title1_rb2);
        alyn1_title1_rb3 = (RadioButton) findViewById(R.id.alyn2_title1_rb3);
        alyn1_title1_rb4 = (RadioButton) findViewById(R.id.alyn2_title1_rb4);
        list_alyn1_title1.add(alyn1_title1_rb1);
        list_alyn1_title1.add(alyn1_title1_rb2);
        list_alyn1_title1.add(alyn1_title1_rb3);
        list_alyn1_title1.add(alyn1_title1_rb4);

        alyn1_title2_rb1 = (RadioButton) findViewById(R.id.alyn2_title2_rb1);
        alyn1_title2_rb2 = (RadioButton) findViewById(R.id.alyn2_title2_rb2);
        alyn1_title2_rb3 = (RadioButton) findViewById(R.id.alyn2_title2_rb3);
        alyn1_title2_rb4 = (RadioButton) findViewById(R.id.alyn2_title2_rb4);
        alyn1_title2_rb5 = (RadioButton) findViewById(R.id.alyn2_title2_rb5);
        list_alyn1_title2.add(alyn1_title2_rb1);
        list_alyn1_title2.add(alyn1_title2_rb2);
        list_alyn1_title2.add(alyn1_title2_rb3);
        list_alyn1_title2.add(alyn1_title2_rb4);
        list_alyn1_title2.add(alyn1_title2_rb5);

        alyn1_title3_rb1 = (RadioButton) findViewById(R.id.alyn2_title3_rb1);
        alyn1_title3_rb2 = (RadioButton) findViewById(R.id.alyn2_title3_rb2);
        alyn1_title3_rb3 = (RadioButton) findViewById(R.id.alyn2_title3_rb3);
        alyn1_title3_rb4 = (RadioButton) findViewById(R.id.alyn2_title3_rb4);
        alyn1_title3_rb5 = (RadioButton) findViewById(R.id.alyn2_title3_rb5);
        list_alyn1_title3.add(alyn1_title3_rb1);
        list_alyn1_title3.add(alyn1_title3_rb2);
        list_alyn1_title3.add(alyn1_title3_rb3);
        list_alyn1_title3.add(alyn1_title3_rb4);
        list_alyn1_title3.add(alyn1_title3_rb5);

        alyn1_title4_rb1 = (RadioButton) findViewById(R.id.alyn2_title4_rb1);
        alyn1_title4_rb2 = (RadioButton) findViewById(R.id.alyn2_title4_rb2);
        alyn1_title4_rb3 = (RadioButton) findViewById(R.id.alyn2_title4_rb3);
        alyn1_title4_rb4 = (RadioButton) findViewById(R.id.alyn2_title4_rb4);
        alyn1_title4_rb5 = (RadioButton) findViewById(R.id.alyn2_title4_rb5);
        list_alyn1_title4.add(alyn1_title4_rb1);
        list_alyn1_title4.add(alyn1_title4_rb2);
        list_alyn1_title4.add(alyn1_title4_rb3);
        list_alyn1_title4.add(alyn1_title4_rb4);
        list_alyn1_title4.add(alyn1_title4_rb5);

        alyn1_title5_rb1 = (RadioButton) findViewById(R.id.alyn2_title5_rb1);
        alyn1_title5_rb2 = (RadioButton) findViewById(R.id.alyn2_title5_rb2);
        alyn1_title5_rb3 = (RadioButton) findViewById(R.id.alyn2_title5_rb3);
        alyn1_title5_rb4 = (RadioButton) findViewById(R.id.alyn2_title5_rb4);
        alyn1_title5_rb5 = (RadioButton) findViewById(R.id.alyn2_title5_rb5);
        list_alyn1_title5.add(alyn1_title5_rb1);
        list_alyn1_title5.add(alyn1_title5_rb2);
        list_alyn1_title5.add(alyn1_title5_rb3);
        list_alyn1_title5.add(alyn1_title5_rb4);
        list_alyn1_title5.add(alyn1_title5_rb5);

        alyn1_title6_rb1 = (RadioButton) findViewById(R.id.alyn2_title6_rb1);
        alyn1_title6_rb2 = (RadioButton) findViewById(R.id.alyn2_title6_rb2);
        alyn1_title6_rb3 = (RadioButton) findViewById(R.id.alyn2_title6_rb3);
        alyn1_title6_rb4 = (RadioButton) findViewById(R.id.alyn2_title6_rb4);
        alyn1_title6_rb5 = (RadioButton) findViewById(R.id.alyn2_title6_rb5);
        list_alyn1_title6.add(alyn1_title6_rb1);
        list_alyn1_title6.add(alyn1_title6_rb2);
        list_alyn1_title6.add(alyn1_title6_rb3);
        list_alyn1_title6.add(alyn1_title6_rb4);
        list_alyn1_title6.add(alyn1_title6_rb5);

        alyn1_title7_rb1 = (RadioButton) findViewById(R.id.alyn2_title7_rb1);
        alyn1_title7_rb2 = (RadioButton) findViewById(R.id.alyn2_title7_rb2);
        alyn1_title7_rb3 = (RadioButton) findViewById(R.id.alyn2_title7_rb3);
        alyn1_title7_rb4 = (RadioButton) findViewById(R.id.alyn2_title7_rb4);
        alyn1_title7_rb5 = (RadioButton) findViewById(R.id.alyn2_title7_rb5);
        list_alyn1_title7.add(alyn1_title7_rb1);
        list_alyn1_title7.add(alyn1_title7_rb2);
        list_alyn1_title7.add(alyn1_title7_rb3);
        list_alyn1_title7.add(alyn1_title7_rb4);
        list_alyn1_title7.add(alyn1_title7_rb5);

        alyn1_title8_rb1 = (RadioButton) findViewById(R.id.alyn2_title8_rb1);
        alyn1_title8_rb2 = (RadioButton) findViewById(R.id.alyn2_title8_rb2);
        alyn1_title8_rb3 = (RadioButton) findViewById(R.id.alyn2_title8_rb3);
        alyn1_title8_rb4 = (RadioButton) findViewById(R.id.alyn2_title8_rb4);
        alyn1_title8_rb5 = (RadioButton) findViewById(R.id.alyn2_title8_rb5);
        list_alyn1_title8.add(alyn1_title8_rb1);
        list_alyn1_title8.add(alyn1_title8_rb2);
        list_alyn1_title8.add(alyn1_title8_rb3);
        list_alyn1_title8.add(alyn1_title8_rb4);
        list_alyn1_title8.add(alyn1_title8_rb5);

        alyn1_title9_rb1 = (RadioButton) findViewById(R.id.alyn2_title9_rb1);
        alyn1_title9_rb2 = (RadioButton) findViewById(R.id.alyn2_title9_rb2);
        alyn1_title9_rb3 = (RadioButton) findViewById(R.id.alyn2_title9_rb3);
        alyn1_title9_rb4 = (RadioButton) findViewById(R.id.alyn2_title9_rb4);
        alyn1_title9_rb5 = (RadioButton) findViewById(R.id.alyn2_title9_rb5);
        list_alyn1_title9.add(alyn1_title9_rb1);
        list_alyn1_title9.add(alyn1_title9_rb2);
        list_alyn1_title9.add(alyn1_title9_rb3);
        list_alyn1_title9.add(alyn1_title9_rb4);
        list_alyn1_title9.add(alyn1_title9_rb5);

        alyn1_title10_rb1 = (RadioButton) findViewById(R.id.alyn2_title10_rb1);
        alyn1_title10_rb2 = (RadioButton) findViewById(R.id.alyn2_title10_rb2);
        alyn1_title10_rb3 = (RadioButton) findViewById(R.id.alyn2_title10_rb3);
        alyn1_title10_rb4 = (RadioButton) findViewById(R.id.alyn2_title10_rb4);
        alyn1_title10_rb5 = (RadioButton) findViewById(R.id.alyn2_title10_rb5);
        list_alyn1_title10.add(alyn1_title10_rb1);
        list_alyn1_title10.add(alyn1_title10_rb2);
        list_alyn1_title10.add(alyn1_title10_rb3);
        list_alyn1_title10.add(alyn1_title10_rb4);
        list_alyn1_title10.add(alyn1_title10_rb5);

        alyn1_title11_rb1 = (RadioButton) findViewById(R.id.alyn2_title11_rb1);
        alyn1_title11_rb2 = (RadioButton) findViewById(R.id.alyn2_title11_rb2);
        alyn1_title11_rb3 = (RadioButton) findViewById(R.id.alyn2_title11_rb3);
        alyn1_title11_rb4 = (RadioButton) findViewById(R.id.alyn2_title11_rb4);
        alyn1_title11_rb5 = (RadioButton) findViewById(R.id.alyn2_title11_rb5);
        list_alyn1_title11.add(alyn1_title11_rb1);
        list_alyn1_title11.add(alyn1_title11_rb2);
        list_alyn1_title11.add(alyn1_title11_rb3);
        list_alyn1_title11.add(alyn1_title11_rb4);
        list_alyn1_title11.add(alyn1_title11_rb5);

        alyn1_title12_rb1 = (RadioButton) findViewById(R.id.alyn2_title12_rb1);
        alyn1_title12_rb2 = (RadioButton) findViewById(R.id.alyn2_title12_rb2);
        alyn1_title12_rb3 = (RadioButton) findViewById(R.id.alyn2_title12_rb3);
        alyn1_title12_rb4 = (RadioButton) findViewById(R.id.alyn2_title12_rb4);
        alyn1_title12_rb5 = (RadioButton) findViewById(R.id.alyn2_title12_rb5);
        list_alyn1_title12.add(alyn1_title12_rb1);
        list_alyn1_title12.add(alyn1_title12_rb2);
        list_alyn1_title12.add(alyn1_title12_rb3);
        list_alyn1_title12.add(alyn1_title12_rb4);
        list_alyn1_title12.add(alyn1_title12_rb5);

        alyn1_title13_rb1 = (RadioButton) findViewById(R.id.alyn2_title13_rb1);
        alyn1_title13_rb2 = (RadioButton) findViewById(R.id.alyn2_title13_rb2);
        alyn1_title13_rb3 = (RadioButton) findViewById(R.id.alyn2_title13_rb3);
        alyn1_title13_rb4 = (RadioButton) findViewById(R.id.alyn2_title13_rb4);
        alyn1_title13_rb5 = (RadioButton) findViewById(R.id.alyn2_title13_rb5);
        list_alyn1_title13.add(alyn1_title13_rb1);
        list_alyn1_title13.add(alyn1_title13_rb2);
        list_alyn1_title13.add(alyn1_title13_rb3);
        list_alyn1_title13.add(alyn1_title13_rb4);
        list_alyn1_title13.add(alyn1_title13_rb5);

        alyn1_title13_rb1 = (RadioButton) findViewById(R.id.alyn2_title13_rb1);
        alyn1_title13_rb2 = (RadioButton) findViewById(R.id.alyn2_title13_rb2);
        alyn1_title13_rb3 = (RadioButton) findViewById(R.id.alyn2_title13_rb3);
        alyn1_title13_rb4 = (RadioButton) findViewById(R.id.alyn2_title13_rb4);
        alyn1_title13_rb5 = (RadioButton) findViewById(R.id.alyn2_title13_rb5);
        list_alyn1_title13.add(alyn1_title13_rb1);
        list_alyn1_title13.add(alyn1_title13_rb2);
        list_alyn1_title13.add(alyn1_title13_rb3);
        list_alyn1_title13.add(alyn1_title13_rb4);
        list_alyn1_title13.add(alyn1_title13_rb5);

        alyn1_title14_rb1 = (RadioButton) findViewById(R.id.alyn2_title14_rb1);
        alyn1_title14_rb2 = (RadioButton) findViewById(R.id.alyn2_title14_rb2);
        alyn1_title14_rb3 = (RadioButton) findViewById(R.id.alyn2_title14_rb3);
        alyn1_title14_rb4 = (RadioButton) findViewById(R.id.alyn2_title14_rb4);
        alyn1_title14_rb5 = (RadioButton) findViewById(R.id.alyn2_title14_rb5);
        list_alyn1_title14.add(alyn1_title14_rb1);
        list_alyn1_title14.add(alyn1_title14_rb2);
        list_alyn1_title14.add(alyn1_title14_rb3);
        list_alyn1_title14.add(alyn1_title14_rb4);
        list_alyn1_title14.add(alyn1_title14_rb5);

        alyn1_title15_rb1 = (RadioButton) findViewById(R.id.alyn2_title15_rb1);
        alyn1_title15_rb2 = (RadioButton) findViewById(R.id.alyn2_title15_rb2);
        alyn1_title15_rb3 = (RadioButton) findViewById(R.id.alyn2_title15_rb3);
        alyn1_title15_rb4 = (RadioButton) findViewById(R.id.alyn2_title15_rb4);
        alyn1_title15_rb5 = (RadioButton) findViewById(R.id.alyn2_title15_rb5);
        list_alyn1_title15.add(alyn1_title15_rb1);
        list_alyn1_title15.add(alyn1_title15_rb2);
        list_alyn1_title15.add(alyn1_title15_rb3);
        list_alyn1_title15.add(alyn1_title15_rb4);
        list_alyn1_title15.add(alyn1_title15_rb5);

        alyn1_title16_rb1 = (RadioButton) findViewById(R.id.alyn2_title16_rb1);
        alyn1_title16_rb2 = (RadioButton) findViewById(R.id.alyn2_title16_rb2);
        alyn1_title16_rb3 = (RadioButton) findViewById(R.id.alyn2_title16_rb3);
        alyn1_title16_rb4 = (RadioButton) findViewById(R.id.alyn2_title16_rb4);
        alyn1_title16_rb5 = (RadioButton) findViewById(R.id.alyn2_title16_rb5);
        list_alyn1_title16.add(alyn1_title16_rb1);
        list_alyn1_title16.add(alyn1_title16_rb2);
        list_alyn1_title16.add(alyn1_title16_rb3);
        list_alyn1_title16.add(alyn1_title16_rb4);
        list_alyn1_title16.add(alyn1_title16_rb5);

        alyn1_title17_rb1 = (RadioButton) findViewById(R.id.alyn2_title17_rb1);
        alyn1_title17_rb2 = (RadioButton) findViewById(R.id.alyn2_title17_rb2);
        alyn1_title17_rb3 = (RadioButton) findViewById(R.id.alyn2_title17_rb3);
        alyn1_title17_rb4 = (RadioButton) findViewById(R.id.alyn2_title17_rb4);
        alyn1_title17_rb5 = (RadioButton) findViewById(R.id.alyn2_title17_rb5);
        list_alyn1_title17.add(alyn1_title17_rb1);
        list_alyn1_title17.add(alyn1_title17_rb2);
        list_alyn1_title17.add(alyn1_title17_rb3);
        list_alyn1_title17.add(alyn1_title17_rb4);
        list_alyn1_title17.add(alyn1_title17_rb5);

        alyn1_title18_rb1 = (RadioButton) findViewById(R.id.alyn2_title18_rb1);
        alyn1_title18_rb2 = (RadioButton) findViewById(R.id.alyn2_title18_rb2);
        alyn1_title18_rb3 = (RadioButton) findViewById(R.id.alyn2_title18_rb3);
        alyn1_title18_rb4 = (RadioButton) findViewById(R.id.alyn2_title18_rb4);
        alyn1_title18_rb5 = (RadioButton) findViewById(R.id.alyn2_title18_rb5);
        list_alyn1_title18.add(alyn1_title18_rb1);
        list_alyn1_title18.add(alyn1_title18_rb2);
        list_alyn1_title18.add(alyn1_title18_rb3);
        list_alyn1_title18.add(alyn1_title18_rb4);
        list_alyn1_title18.add(alyn1_title18_rb5);

        alyn1_title19_rb1 = (RadioButton) findViewById(R.id.alyn2_title19_rb1);
        alyn1_title19_rb2 = (RadioButton) findViewById(R.id.alyn2_title19_rb2);
        alyn1_title19_rb3 = (RadioButton) findViewById(R.id.alyn2_title19_rb3);
        alyn1_title19_rb4 = (RadioButton) findViewById(R.id.alyn2_title19_rb4);
        alyn1_title19_rb5 = (RadioButton) findViewById(R.id.alyn2_title19_rb5);
        list_alyn1_title19.add(alyn1_title19_rb1);
        list_alyn1_title19.add(alyn1_title19_rb2);
        list_alyn1_title19.add(alyn1_title19_rb3);
        list_alyn1_title19.add(alyn1_title19_rb4);
        list_alyn1_title19.add(alyn1_title19_rb5);

        alyn1_title20_rb1 = (RadioButton) findViewById(R.id.alyn2_title20_rb1);
        alyn1_title20_rb2 = (RadioButton) findViewById(R.id.alyn2_title20_rb2);
        alyn1_title20_rb3 = (RadioButton) findViewById(R.id.alyn2_title20_rb3);
        alyn1_title20_rb4 = (RadioButton) findViewById(R.id.alyn2_title20_rb4);
        alyn1_title20_rb5 = (RadioButton) findViewById(R.id.alyn2_title20_rb5);
        list_alyn1_title20.add(alyn1_title20_rb1);
        list_alyn1_title20.add(alyn1_title20_rb2);
        list_alyn1_title20.add(alyn1_title20_rb3);
        list_alyn1_title20.add(alyn1_title20_rb4);
        list_alyn1_title20.add(alyn1_title20_rb5);

        alyn1_title21_rb1 = (RadioButton) findViewById(R.id.alyn2_title21_rb1);
        alyn1_title21_rb2 = (RadioButton) findViewById(R.id.alyn2_title21_rb2);
        alyn1_title21_rb3 = (RadioButton) findViewById(R.id.alyn2_title21_rb3);
        alyn1_title21_rb4 = (RadioButton) findViewById(R.id.alyn2_title21_rb4);
        alyn1_title21_rb5 = (RadioButton) findViewById(R.id.alyn2_title21_rb5);
        list_alyn1_title21.add(alyn1_title21_rb1);
        list_alyn1_title21.add(alyn1_title21_rb2);
        list_alyn1_title21.add(alyn1_title21_rb3);
        list_alyn1_title21.add(alyn1_title21_rb4);
        list_alyn1_title21.add(alyn1_title21_rb5);

        alyn1_title22_rb1 = (RadioButton) findViewById(R.id.alyn2_title22_rb1);
        alyn1_title22_rb2 = (RadioButton) findViewById(R.id.alyn2_title22_rb2);
        alyn1_title22_rb3 = (RadioButton) findViewById(R.id.alyn2_title22_rb3);
        alyn1_title22_rb4 = (RadioButton) findViewById(R.id.alyn2_title22_rb4);
        alyn1_title22_rb5 = (RadioButton) findViewById(R.id.alyn2_title22_rb5);
        list_alyn1_title22.add(alyn1_title22_rb1);
        list_alyn1_title22.add(alyn1_title22_rb2);
        list_alyn1_title22.add(alyn1_title22_rb3);
        list_alyn1_title22.add(alyn1_title22_rb4);
        list_alyn1_title22.add(alyn1_title22_rb5);

        alyn1_title23_rb1 = (RadioButton) findViewById(R.id.alyn2_title23_rb1);
        alyn1_title23_rb2 = (RadioButton) findViewById(R.id.alyn2_title23_rb2);
        alyn1_title23_rb3 = (RadioButton) findViewById(R.id.alyn2_title23_rb3);
        alyn1_title23_rb4 = (RadioButton) findViewById(R.id.alyn2_title23_rb4);
        alyn1_title23_rb5 = (RadioButton) findViewById(R.id.alyn2_title23_rb5);
        list_alyn1_title23.add(alyn1_title23_rb1);
        list_alyn1_title23.add(alyn1_title23_rb2);
        list_alyn1_title23.add(alyn1_title23_rb3);
        list_alyn1_title23.add(alyn1_title23_rb4);
        list_alyn1_title23.add(alyn1_title23_rb5);

        alyn1_title24_rb1 = (RadioButton) findViewById(R.id.alyn2_title24_rb1);
        alyn1_title24_rb2 = (RadioButton) findViewById(R.id.alyn2_title24_rb2);
        alyn1_title24_rb3 = (RadioButton) findViewById(R.id.alyn2_title24_rb3);
        alyn1_title24_rb4 = (RadioButton) findViewById(R.id.alyn2_title24_rb4);
        alyn1_title24_rb5 = (RadioButton) findViewById(R.id.alyn2_title24_rb5);
        list_alyn1_title24.add(alyn1_title24_rb1);
        list_alyn1_title24.add(alyn1_title24_rb2);
        list_alyn1_title24.add(alyn1_title24_rb3);
        list_alyn1_title24.add(alyn1_title24_rb4);
        list_alyn1_title24.add(alyn1_title24_rb5);

        alyn1_title25_rb1 = (RadioButton) findViewById(R.id.alyn2_title25_rb1);
        alyn1_title25_rb2 = (RadioButton) findViewById(R.id.alyn2_title25_rb2);
        alyn1_title25_rb3 = (RadioButton) findViewById(R.id.alyn2_title25_rb3);
        alyn1_title25_rb4 = (RadioButton) findViewById(R.id.alyn2_title25_rb4);
        alyn1_title25_rb5 = (RadioButton) findViewById(R.id.alyn2_title25_rb5);
        list_alyn1_title25.add(alyn1_title25_rb1);
        list_alyn1_title25.add(alyn1_title25_rb2);
        list_alyn1_title25.add(alyn1_title25_rb3);
        list_alyn1_title25.add(alyn1_title25_rb4);
        list_alyn1_title25.add(alyn1_title25_rb5);

        alyn1_title26_rb1 = (RadioButton) findViewById(R.id.alyn2_title26_rb1);
        alyn1_title26_rb2 = (RadioButton) findViewById(R.id.alyn2_title26_rb2);
        alyn1_title26_rb3 = (RadioButton) findViewById(R.id.alyn2_title26_rb3);
        alyn1_title26_rb4 = (RadioButton) findViewById(R.id.alyn2_title26_rb4);
        alyn1_title26_rb5 = (RadioButton) findViewById(R.id.alyn2_title26_rb5);
        list_alyn1_title26.add(alyn1_title26_rb1);
        list_alyn1_title26.add(alyn1_title26_rb2);
        list_alyn1_title26.add(alyn1_title26_rb3);
        list_alyn1_title26.add(alyn1_title26_rb4);
        list_alyn1_title26.add(alyn1_title26_rb5);

        alyn1_title27_rb1 = (RadioButton) findViewById(R.id.alyn2_title27_rb1);
        alyn1_title27_rb2 = (RadioButton) findViewById(R.id.alyn2_title27_rb2);
        alyn1_title27_rb3 = (RadioButton) findViewById(R.id.alyn2_title27_rb3);
        alyn1_title27_rb4 = (RadioButton) findViewById(R.id.alyn2_title27_rb4);
        alyn1_title27_rb5 = (RadioButton) findViewById(R.id.alyn2_title27_rb5);
        list_alyn1_title27.add(alyn1_title27_rb1);
        list_alyn1_title27.add(alyn1_title27_rb2);
        list_alyn1_title27.add(alyn1_title27_rb3);
        list_alyn1_title27.add(alyn1_title27_rb4);
        list_alyn1_title27.add(alyn1_title27_rb5);

        alyn2_total_scores_bt = (Button) findViewById(R.id.alyn2_total_scores_bt);
        alyn2_total_scores_bt.setOnClickListener(this);
        alyn2_total_scores_tv = (TextView) findViewById(R.id.alyn2_total_scores_tv);

        alyn2_total_scores_centesmal_bt = (Button) findViewById(R.id.alyn2_total_scores_centesmal_bt);
        alyn2_total_scores_centesmal_bt.setOnClickListener(this);
        alyn2_total_scores_centesmal_tv = (TextView) findViewById(R.id.alyn2_total_scores_centesmal_tv);


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
            case R.id.alyn2_date_tv://评定日期
                pwTime.showAtLocation(alyn2_date_tv, Gravity.BOTTOM, 0, 0, new Date());
                break;
            case R.id.alyn2_total_scores_bt://显示总分
                counts = counts1 + counts2 + counts3 + counts4 + counts5 + counts6 + counts7 + counts8 + counts9 + counts10 +
                        counts11 + counts12 + counts13 + counts14 + counts15 + counts16 + counts17 + counts18 + counts19
                        + counts20 + counts21 + counts22 + counts23 + counts24 + counts25 + counts26 + counts27;
                alyn2_total_scores_tv.setText(counts + "");
                break;
            case R.id.alyn2_total_scores_centesmal_bt://显示百分制得分
                counts = counts1 + counts2 + counts3 + counts4 + counts5 + counts6 + counts7 + counts8 + counts9 + counts10 +
                        counts11 + counts12 + counts13 + counts14 + counts15 + counts16 + counts17 + counts18 + counts19
                        + counts20 + counts21 + counts22 + counts23 + counts24 + counts25 + counts26 + counts27;
                int i = counts * 100 / 81;
                alyn2_total_scores_centesmal_tv.setText(i + "");
        }

    }

    /**
     * 新建数据保存
     */
    private void saveAlyn() {
        TableAlynInfo2 info = null;
        if (dataIsEmpty()) {

        } else {
            counts = counts1 + counts2 + counts3 + counts4 + counts5 + counts6 + counts7 + counts8 + counts9 + counts10 +
                    counts11 + counts12 + counts13 + counts14 + counts15 + counts16 + counts17 + counts18 + counts19
                    + counts20 + counts21 + counts22 + counts23 + counts24 + counts25 + counts26 + counts27;
            String uuid = UUID.randomUUID().toString();
            info = new TableAlynInfo2();
            info.setFlag("new");
            info.setAlyn_uuid(uuid);
            info.setDate(alyn2_date_tv.getText().toString());
            info.setInstructions(spinner_value);
            info.setUser_name("admin");
            info.setScores(counts);
            info.setScore_t(counts * 100 / 81);
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
            info.setAnswer14(answer14);
            info.setAnswer15(answer15);
            info.setAnswer16(answer16);
            info.setAnswer17(answer17);
            info.setAnswer18(answer18);
            info.setAnswer19(answer19);
            info.setAnswer20(answer20);
            info.setAnswer21(answer21);
            info.setAnswer22(answer22);
            info.setAnswer23(answer23);
            info.setAnswer24(answer24);
            info.setAnswer25(answer25);
            info.setAnswer26(answer26);
            info.setAnswer27(answer27);

            TableAlyn2Manager manager = new TableAlyn2Manager(this);
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

        TableAlyn2Manager manager = new TableAlyn2Manager(this);
        TableAlynInfo2 info = new TableAlynInfo2();
        manager.openDataBase();
        info = manager.queryOne(instruction, "admin");
        manager.closeDataBase();
        uuid_s = info.getAlyn_uuid();
        for (int i = 0; i < list_s.size(); i++) {
            if ((list_s.get(i)).equals(info.getInstructions())) {
                alyn2_instructions.setSelection(i);
            }
        }
        alyn2_date_tv.setText(info.getDate());
        alyn2_total_scores_tv.setText(info.getScores()+"");
        alyn2_total_scores_centesmal_tv.setText(info.getScore_t() + "");

        setRadioButtonData(list_alyn1_title1, info.getAnswer1());
        setRadioButtonData(list_alyn1_title2, info.getAnswer2());
        setRadioButtonData(list_alyn1_title3, info.getAnswer3());
        setRadioButtonData(list_alyn1_title4, info.getAnswer4());
        setRadioButtonData(list_alyn1_title5, info.getAnswer5());
        setRadioButtonData(list_alyn1_title6, info.getAnswer6());
        setRadioButtonData(list_alyn1_title7, info.getAnswer7());
        setRadioButtonData(list_alyn1_title8, info.getAnswer8());
        setRadioButtonData(list_alyn1_title9, info.getAnswer9());
        setRadioButtonData(list_alyn1_title10, info.getAnswer10());
        setRadioButtonData(list_alyn1_title11, info.getAnswer11());
        setRadioButtonData(list_alyn1_title12, info.getAnswer12());
        setRadioButtonData(list_alyn1_title13, info.getAnswer13());
        setRadioButtonData(list_alyn1_title14, info.getAnswer14());
        setRadioButtonData(list_alyn1_title15,info.getAnswer15());
        setRadioButtonData(list_alyn1_title16,info.getAnswer16());
        setRadioButtonData(list_alyn1_title17,info.getAnswer17());
        setRadioButtonData(list_alyn1_title18,info.getAnswer18());
        setRadioButtonData(list_alyn1_title19,info.getAnswer19());
        setRadioButtonData(list_alyn1_title20,info.getAnswer20());
        setRadioButtonData(list_alyn1_title21,info.getAnswer21());
        setRadioButtonData(list_alyn1_title22,info.getAnswer22());
        setRadioButtonData(list_alyn1_title23,info.getAnswer23());
        setRadioButtonData(list_alyn1_title24,info.getAnswer24());
        setRadioButtonData(list_alyn1_title25,info.getAnswer25());
        setRadioButtonData(list_alyn1_title26,info.getAnswer26());
        setRadioButtonData(list_alyn1_title27,info.getAnswer27());
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
        if (TextUtils.isEmpty(alyn2_date_tv.getText().toString())) {
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
        } else if (radioButtonIsChecked(list_alyn1_title14) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title15) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title16) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title17) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title18) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title19) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title20) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title21) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title22) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title23) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title24) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title25) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title26) == false) {
            MyToast(msg);
            return true;
        } else if (radioButtonIsChecked(list_alyn1_title27) == false) {
            MyToast(msg);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否截取值是”X“
     * @param s
     * @return
     */
    private int checkedX(String s){
        int i = 0;
        if(s.equals("X")){
            i = 0;
        }else{
            i = Integer.parseInt(s);
        }
        return i;
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
