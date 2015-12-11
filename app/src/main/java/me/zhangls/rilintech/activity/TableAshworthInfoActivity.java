package me.zhangls.rilintech.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.zhangls.rilintech.R;
import me.zhangls.rilintech.adapter.SpinnerBaseAdapter;
import me.zhangls.rilintech.application.MyApplication;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.TableAshworthInfo;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;

/**
 * Created by YANG on 15/9/22.
 */
public class TableAshworthInfoActivity extends BaseActivity implements View.OnClickListener {

    //radiogroup
    private RadioGroup rg1, rg2, rg3, rg4, rg5, rg6, rg7, rg8, rg9, rg10, rg11, rg12;
    //评定说明当前选中的值
    private String spinner_value;
    //spinner加载器
    private ArrayAdapter<String> adapter;
    //评定说明的选项
    private ArrayList<String> list_instructions;
    //评定说明int
    private Map<String, Integer> instructions_map;
    //去除已经存在的评定说明后的spinner数据
    private List<String> list_instructions_new = new ArrayList<>();
    //评定说明
    private Spinner spinner_instructions;
    //时间选择器
    private TimePopupWindow pwTime;
    private String msg = "请检查是否填写完整...";
    //正在编辑的对象
    private TableAshworthInfo info = null;
    //是否新建
    private Boolean isAdd = true;
    //返回键
    private ImageView key_back;
    //保存键
    private ImageView key_save;
    //评定日期
    private TextView date;
    //缓冲球
    private GoogleProgressBar progressBar;
    //当前服务器已经存在的评定集合
    private ArrayList<String> list_instructions_cloud = new ArrayList<>();
    //handler
    private Handler handler;
    //新建响应码
    private int create_code = 0;
    //更新响应码
    private int update_code = 0;
    //拼接参数
    private String param;
    //当前登录用户
    private int user_auth_id;
    //当前选中的患者
    private int patient_info_id;
    //选中的答案
    private String answer1, answer2, answer3, answer4, answer5, answer6, answer7, answer8,
            answer9, answer10, answer11, answer12;
    //计分器
    private String counts, counts1, counts2, counts3, counts4, counts5, counts6,
            counts7, counts8, counts9, counts10, counts11, counts12;
    //评分集合
    private ArrayList<String> list_counts;
    //所有的选项
    private RadioButton title1_rb1, title1_rb2, title1_rb3, title1_rb4, title1_rb5, title1_rb6;
    private RadioButton title2_rb1, title2_rb2, title2_rb3, title2_rb4, title2_rb5, title2_rb6;
    private RadioButton title3_rb1, title3_rb2, title3_rb3, title3_rb4, title3_rb5, title3_rb6;
    private RadioButton title4_rb1, title4_rb2, title4_rb3, title4_rb4, title4_rb5, title4_rb6;
    private RadioButton title5_rb1, title5_rb2, title5_rb3, title5_rb4, title5_rb5, title5_rb6;
    private RadioButton title6_rb1, title6_rb2, title6_rb3, title6_rb4, title6_rb5, title6_rb6;
    private RadioButton title7_rb1, title7_rb2, title7_rb3, title7_rb4, title7_rb5, title7_rb6;
    private RadioButton title8_rb1, title8_rb2, title8_rb3, title8_rb4, title8_rb5, title8_rb6;
    private RadioButton title9_rb1, title9_rb2, title9_rb3, title9_rb4, title9_rb5, title9_rb6;
    private RadioButton title10_rb1, title10_rb2, title10_rb3, title10_rb4, title10_rb5, title10_rb6;
    private RadioButton title11_rb1, title11_rb2, title11_rb3, title11_rb4, title11_rb5, title11_rb6;
    private RadioButton title12_rb1, title12_rb2, title12_rb3, title12_rb4, title12_rb5, title12_rb6;
    //所有的选项集
    private List<RadioButton> list_title1 = new ArrayList<>();
    private List<RadioButton> list_title2 = new ArrayList<>();
    private List<RadioButton> list_title3 = new ArrayList<>();
    private List<RadioButton> list_title4 = new ArrayList<>();
    private List<RadioButton> list_title5 = new ArrayList<>();
    private List<RadioButton> list_title6 = new ArrayList<>();
    private List<RadioButton> list_title7 = new ArrayList<>();
    private List<RadioButton> list_title8 = new ArrayList<>();
    private List<RadioButton> list_title9 = new ArrayList<>();
    private List<RadioButton> list_title10 = new ArrayList<>();
    private List<RadioButton> list_title11 = new ArrayList<>();
    private List<RadioButton> list_title12 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table_ashworth_info);

        getMyIntent();

        queryInstructions();


        getSharedPreferencesData();

        //网络返回数据后得到通知刷新View
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        progressBar.setVisibility(View.GONE);
                        break;

                }
            }
        };
        initView();

    }

    /**
     * 获取当前登录用户和当前患者的ID
     */
    private void getSharedPreferencesData() {
        SharedPreferences sharedPreferences = getSharedPreferences("patientinfo", Activity.MODE_PRIVATE);
        patient_info_id = sharedPreferences.getInt("id", -1);

        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        user_auth_id = preferences.getInt("use_id", 2);
    }

    /**
     * 去除掉已经有的评定说明
     */
    public void queryInstructions() {
        list_instructions = new ArrayList<>();
        list_instructions.add(getString(R.string.evaluation0));
        list_instructions.add(getString(R.string.evaluation1));
        list_instructions.add(getString(R.string.evaluation2));
        list_instructions.add(getString(R.string.evaluation3));
        list_instructions.add(getString(R.string.evaluation4));
        list_instructions.add(getString(R.string.evaluation5));
        list_instructions.add(getString(R.string.evaluation6));

        list_instructions_new.addAll(list_instructions);
        for (int i = 0; i < list_instructions_cloud.size(); i++) {
            if (list_instructions_new.contains(list_instructions_cloud.get(i))) {
                list_instructions_new.remove(list_instructions_cloud.get(i));
            }
        }
        if (isAdd == false) {//如果是编辑界面，需要把当前的评定说明加回去
            list_instructions_new.add(0, info.getInstructions());
        }
    }

    /**
     * 获取到从indexActivity传过来的数据
     */
    private void getMyIntent() {

        isAdd = getIntent().getBooleanExtra("isAdd", true);
        if (isAdd) {
            list_instructions_cloud = getIntent().getStringArrayListExtra("instructions");

        } else {
            info = getIntent().getParcelableExtra("info");
            list_instructions_cloud = getIntent().getStringArrayListExtra("instructions");
        }
    }

    private void initView() {

        list_counts = new ArrayList<>();
        list_counts.add("0.0");
        list_counts.add("1.0");
        list_counts.add("2.0");
        list_counts.add("3.0");
        list_counts.add("4.0");
        list_counts.add("5.0");

        instructions_map = new HashMap<>();
        instructions_map.put(getString(R.string.evaluation0), 0);
        instructions_map.put(getString(R.string.evaluation1), 1);
        instructions_map.put(getString(R.string.evaluation2), 2);
        instructions_map.put(getString(R.string.evaluation3), 3);
        instructions_map.put(getString(R.string.evaluation4), 4);
        instructions_map.put(getString(R.string.evaluation5), 5);
        instructions_map.put(getString(R.string.evaluation6), 6);

        key_back = (ImageView) findViewById(R.id.title_back);
        key_back.setOnClickListener(this);
        key_save = (ImageView) findViewById(R.id.patient_save);
        key_save.setOnClickListener(this);
        progressBar = (GoogleProgressBar) findViewById(R.id.google_progress);
        progressBar.setVisibility(View.GONE);

        date = (TextView) findViewById(R.id.date_tv);
        date.setOnClickListener(this);
        //时间选择器
        pwTime = new TimePopupWindow(this, TimePopupWindow.Type.YEAR_MONTH_DAY);
        pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date d) {
                date.setText(MyApplication.getTime(d));
            }
        });
        pwTime.setCyclic(true);
        //评定说明
        spinner_instructions = (Spinner) findViewById(R.id.spinner_edit);
        adapter = new SpinnerBaseAdapter(this, android.R.layout.simple_spinner_item, spinner_instructions, list_instructions_new);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        spinner_instructions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_value = list_instructions_new.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_instructions.setAdapter(adapter);

        rg1 = (RadioGroup) findViewById(R.id.title1_rg);
        rg2 = (RadioGroup) findViewById(R.id.title2_rg);
        rg3 = (RadioGroup) findViewById(R.id.title3_rg);
        rg4 = (RadioGroup) findViewById(R.id.title4_rg);
        rg5 = (RadioGroup) findViewById(R.id.title5_rg);
        rg6 = (RadioGroup) findViewById(R.id.title6_rg);
        rg7 = (RadioGroup) findViewById(R.id.title7_rg);
        rg8 = (RadioGroup) findViewById(R.id.title8_rg);
        rg9 = (RadioGroup) findViewById(R.id.title9_rg);
        rg10 = (RadioGroup) findViewById(R.id.title10_rg);
        rg11 = (RadioGroup) findViewById(R.id.title11_rg);
        rg12 = (RadioGroup) findViewById(R.id.title12_rg);
        //监听每个RadioGroup
        setListenerOnRadioGroup();

        title1_rb1 = (RadioButton) findViewById(R.id.title1_rb1);
        title1_rb2 = (RadioButton) findViewById(R.id.title1_rb2);
        title1_rb3 = (RadioButton) findViewById(R.id.title1_rb3);
        title1_rb4 = (RadioButton) findViewById(R.id.title1_rb4);
        title1_rb5 = (RadioButton) findViewById(R.id.title1_rb5);
        title1_rb6 = (RadioButton) findViewById(R.id.title1_rb6);
        list_title1.add(title1_rb1);
        list_title1.add(title1_rb2);
        list_title1.add(title1_rb3);
        list_title1.add(title1_rb4);
        list_title1.add(title1_rb5);
        list_title1.add(title1_rb6);

        title2_rb1 = (RadioButton) findViewById(R.id.title2_rb1);
        title2_rb2 = (RadioButton) findViewById(R.id.title2_rb2);
        title2_rb3 = (RadioButton) findViewById(R.id.title2_rb3);
        title2_rb4 = (RadioButton) findViewById(R.id.title2_rb4);
        title2_rb5 = (RadioButton) findViewById(R.id.title2_rb5);
        title2_rb6 = (RadioButton) findViewById(R.id.title2_rb6);
        list_title2.add(title2_rb1);
        list_title2.add(title2_rb2);
        list_title2.add(title2_rb3);
        list_title2.add(title2_rb4);
        list_title2.add(title2_rb5);
        list_title2.add(title2_rb6);

        title3_rb1 = (RadioButton) findViewById(R.id.title3_rb1);
        title3_rb2 = (RadioButton) findViewById(R.id.title3_rb2);
        title3_rb3 = (RadioButton) findViewById(R.id.title3_rb3);
        title3_rb4 = (RadioButton) findViewById(R.id.title3_rb4);
        title3_rb5 = (RadioButton) findViewById(R.id.title3_rb5);
        title3_rb6 = (RadioButton) findViewById(R.id.title3_rb6);
        list_title3.add(title3_rb1);
        list_title3.add(title3_rb2);
        list_title3.add(title3_rb3);
        list_title3.add(title3_rb4);
        list_title3.add(title3_rb5);
        list_title3.add(title3_rb6);

        title4_rb1 = (RadioButton) findViewById(R.id.title4_rb1);
        title4_rb2 = (RadioButton) findViewById(R.id.title4_rb2);
        title4_rb3 = (RadioButton) findViewById(R.id.title4_rb3);
        title4_rb4 = (RadioButton) findViewById(R.id.title4_rb4);
        title4_rb5 = (RadioButton) findViewById(R.id.title4_rb5);
        title4_rb6 = (RadioButton) findViewById(R.id.title4_rb6);
        list_title4.add(title4_rb1);
        list_title4.add(title4_rb2);
        list_title4.add(title4_rb3);
        list_title4.add(title4_rb4);
        list_title4.add(title4_rb5);
        list_title4.add(title4_rb6);

        title5_rb1 = (RadioButton) findViewById(R.id.title5_rb1);
        title5_rb2 = (RadioButton) findViewById(R.id.title5_rb2);
        title5_rb3 = (RadioButton) findViewById(R.id.title5_rb3);
        title5_rb4 = (RadioButton) findViewById(R.id.title5_rb4);
        title5_rb5 = (RadioButton) findViewById(R.id.title5_rb5);
        title5_rb6 = (RadioButton) findViewById(R.id.title5_rb6);
        list_title5.add(title5_rb1);
        list_title5.add(title5_rb2);
        list_title5.add(title5_rb3);
        list_title5.add(title5_rb4);
        list_title5.add(title5_rb5);
        list_title5.add(title5_rb6);

        title6_rb1 = (RadioButton) findViewById(R.id.title6_rb1);
        title6_rb2 = (RadioButton) findViewById(R.id.title6_rb2);
        title6_rb3 = (RadioButton) findViewById(R.id.title6_rb3);
        title6_rb4 = (RadioButton) findViewById(R.id.title6_rb4);
        title6_rb5 = (RadioButton) findViewById(R.id.title6_rb5);
        title6_rb6 = (RadioButton) findViewById(R.id.title6_rb6);
        list_title6.add(title6_rb1);
        list_title6.add(title6_rb2);
        list_title6.add(title6_rb3);
        list_title6.add(title6_rb4);
        list_title6.add(title6_rb5);
        list_title6.add(title6_rb6);

        title7_rb1 = (RadioButton) findViewById(R.id.title7_rb1);
        title7_rb2 = (RadioButton) findViewById(R.id.title7_rb2);
        title7_rb3 = (RadioButton) findViewById(R.id.title7_rb3);
        title7_rb4 = (RadioButton) findViewById(R.id.title7_rb4);
        title7_rb5 = (RadioButton) findViewById(R.id.title7_rb5);
        title7_rb6 = (RadioButton) findViewById(R.id.title7_rb6);
        list_title7.add(title7_rb1);
        list_title7.add(title7_rb2);
        list_title7.add(title7_rb3);
        list_title7.add(title7_rb4);
        list_title7.add(title7_rb5);
        list_title7.add(title7_rb6);

        title8_rb1 = (RadioButton) findViewById(R.id.title8_rb1);
        title8_rb2 = (RadioButton) findViewById(R.id.title8_rb2);
        title8_rb3 = (RadioButton) findViewById(R.id.title8_rb3);
        title8_rb4 = (RadioButton) findViewById(R.id.title8_rb4);
        title8_rb5 = (RadioButton) findViewById(R.id.title8_rb5);
        title8_rb6 = (RadioButton) findViewById(R.id.title8_rb6);
        list_title8.add(title8_rb1);
        list_title8.add(title8_rb2);
        list_title8.add(title8_rb3);
        list_title8.add(title8_rb4);
        list_title8.add(title8_rb5);
        list_title8.add(title8_rb6);

        title9_rb1 = (RadioButton) findViewById(R.id.title9_rb1);
        title9_rb2 = (RadioButton) findViewById(R.id.title9_rb2);
        title9_rb3 = (RadioButton) findViewById(R.id.title9_rb3);
        title9_rb4 = (RadioButton) findViewById(R.id.title9_rb4);
        title9_rb5 = (RadioButton) findViewById(R.id.title9_rb5);
        title9_rb6 = (RadioButton) findViewById(R.id.title9_rb6);
        list_title9.add(title9_rb1);
        list_title9.add(title9_rb2);
        list_title9.add(title9_rb3);
        list_title9.add(title9_rb4);
        list_title9.add(title9_rb5);
        list_title9.add(title9_rb6);

        title10_rb1 = (RadioButton) findViewById(R.id.title10_rb1);
        title10_rb2 = (RadioButton) findViewById(R.id.title10_rb2);
        title10_rb3 = (RadioButton) findViewById(R.id.title10_rb3);
        title10_rb4 = (RadioButton) findViewById(R.id.title10_rb4);
        title10_rb5 = (RadioButton) findViewById(R.id.title10_rb5);
        title10_rb6 = (RadioButton) findViewById(R.id.title10_rb6);
        list_title10.add(title10_rb1);
        list_title10.add(title10_rb2);
        list_title10.add(title10_rb3);
        list_title10.add(title10_rb4);
        list_title10.add(title10_rb5);
        list_title10.add(title10_rb6);

        title11_rb1 = (RadioButton) findViewById(R.id.title11_rb1);
        title11_rb2 = (RadioButton) findViewById(R.id.title11_rb2);
        title11_rb3 = (RadioButton) findViewById(R.id.title11_rb3);
        title11_rb4 = (RadioButton) findViewById(R.id.title11_rb4);
        title11_rb5 = (RadioButton) findViewById(R.id.title11_rb5);
        title11_rb6 = (RadioButton) findViewById(R.id.title11_rb6);
        list_title11.add(title11_rb1);
        list_title11.add(title11_rb2);
        list_title11.add(title11_rb3);
        list_title11.add(title11_rb4);
        list_title11.add(title11_rb5);
        list_title11.add(title11_rb6);

        title12_rb1 = (RadioButton) findViewById(R.id.title12_rb1);
        title12_rb2 = (RadioButton) findViewById(R.id.title12_rb2);
        title12_rb3 = (RadioButton) findViewById(R.id.title12_rb3);
        title12_rb4 = (RadioButton) findViewById(R.id.title12_rb4);
        title12_rb5 = (RadioButton) findViewById(R.id.title12_rb5);
        title12_rb6 = (RadioButton) findViewById(R.id.title12_rb6);
        list_title12.add(title12_rb1);
        list_title12.add(title12_rb2);
        list_title12.add(title12_rb3);
        list_title12.add(title12_rb4);
        list_title12.add(title12_rb5);
        list_title12.add(title12_rb6);

        if (isAdd == false) {
            if (info != null) {
                setData();
            }
        }
    }

    /**
     * 监听radiogroup
     */
    private void setListenerOnRadioGroup() {

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < list_title1.size(); i++) {
                    if (checkedId == list_title1.get(i).getId()) {
                        counts1 = list_counts.get(i);
                    }
                }
            }
        });
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < list_title2.size(); i++) {
                    if (checkedId == list_title2.get(i).getId()) {
                        counts2 = list_counts.get(i);
                    }
                }
            }
        });
        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < list_title3.size(); i++) {
                    if (checkedId == list_title3.get(i).getId()) {
                        counts3 = list_counts.get(i);
                    }
                }
            }
        });
        rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < list_title4.size(); i++) {
                    if (checkedId == list_title4.get(i).getId()) {
                        counts4 = list_counts.get(i);
                    }
                }
            }
        });
        rg5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < list_title5.size(); i++) {
                    if (checkedId == list_title5.get(i).getId()) {
                        counts5 = list_counts.get(i);
                    }
                }
            }
        });
        rg6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < list_title6.size(); i++) {
                    if (checkedId == list_title6.get(i).getId()) {
                        counts6 = list_counts.get(i);
                    }
                }
            }
        });
        rg7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < list_title7.size(); i++) {
                    if (checkedId == list_title7.get(i).getId()) {
                        counts7 = list_counts.get(i);
                    }
                }
            }
        });
        rg8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < list_title8.size(); i++) {
                    if (checkedId == list_title8.get(i).getId()) {
                        counts8 = list_counts.get(i);
                    }
                }
            }
        });
        rg9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < list_title9.size(); i++) {
                    if (checkedId == list_title9.get(i).getId()) {
                        counts9 = list_counts.get(i);
                    }
                }
            }
        });
        rg10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < list_title10.size(); i++) {
                    if (checkedId == list_title10.get(i).getId()) {
                        counts10 = list_counts.get(i);
                    }
                }
            }
        });
        rg11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < list_title11.size(); i++) {
                    if (checkedId == list_title11.get(i).getId()) {
                        counts11 = list_counts.get(i);
                    }
                }
            }
        });
        rg12.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < list_title12.size(); i++) {
                    if (checkedId == list_title12.get(i).getId()) {
                        counts12 = list_counts.get(i);
                    }
                }
            }
        });
    }

    /**
     * 编辑界面给每个radiogroup赋值
     *
     * @param list
     * @param s
     */
    private void setRadioButtonData(List<RadioButton> list, String s) {
        for (int i = 0; i < list_counts.size(); i++) {
            if (s.equals(list_counts.get(i))) {
                list.get(i).setChecked(true);
            }
        }
    }

    /**
     * 给所有控件赋值
     */
    private void setData() {

        date.setText(info.getDate().substring(0, 10) + " " + info.getDate().substring(11, 16));

        for (int i = 0; i < list_instructions_new.size(); i++) {
            if ((list_instructions_new.get(i)).equals(info.getInstructions())) {
                spinner_instructions.setSelection(i);
            }
        }
        try {
            setRadioButtonData(list_title1, info.getLeft_scores().get(0));
            setRadioButtonData(list_title2, info.getLeft_scores().get(1));
            setRadioButtonData(list_title3, info.getLeft_scores().get(2));
            setRadioButtonData(list_title4, info.getLeft_scores().get(3));
            setRadioButtonData(list_title5, info.getLeft_scores().get(4));
            setRadioButtonData(list_title6, info.getLeft_scores().get(5));
            setRadioButtonData(list_title7, info.getRight_scores().get(0));
            setRadioButtonData(list_title8, info.getRight_scores().get(1));
            setRadioButtonData(list_title9, info.getRight_scores().get(2));
            setRadioButtonData(list_title10, info.getRight_scores().get(3));
            setRadioButtonData(list_title11, info.getRight_scores().get(4));
            setRadioButtonData(list_title12, info.getRight_scores().get(5));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                this.finish();
                break;
            case R.id.patient_save:
                if (dataIsEmpty()) {

                } else {
                    progressBar.setVisibility(View.VISIBLE);

                    param = "patient_info_id=" + patient_info_id
                            + "&user_auth_id=" + user_auth_id
                            + "&menu_id=" + 38
                            + "&evaluation[evaluation_time]=" + date.getText()
                            + "&evaluation[evaluation_time_note]=" + instructions_map.get(spinner_value)

                            + "&sum[0]=" + counts1
                            + "&sum[1]=" + counts2
                            + "&sum[2]=" + counts3
                            + "&sum[3]=" + counts4
                            + "&sum[4]=" + counts5
                            + "&sum[5]=" + counts6
                            + "&sum[6]=" + counts7
                            + "&sum[7]=" + counts8
                            + "&sum[8]=" + counts9
                            + "&sum[9]=" + counts10
                            + "&sum[10]=" + counts11
                            + "&sum[11]=" + counts12

                            + "&in_sum[0]=" + "t"
                            + "&in_sum[1]=" + "t"
                            + "&in_sum[2]=" + "t"
                            + "&in_sum[3]=" + "t"
                            + "&in_sum[4]=" + "t"
                            + "&in_sum[5]=" + "t"
                            + "&in_sum[6]=" + "t"
                            + "&in_sum[7]=" + "t"
                            + "&in_sum[8]=" + "t"
                            + "&in_sum[9]=" + "t"
                            + "&in_sum[10]=" + "t"
                            + "&in_sum[11]=" + "t"

                            + "&note[0]=" + "左侧"
                            + "&note[1]=" + "左侧"
                            + "&note[2]=" + "左侧"
                            + "&note[3]=" + "左侧"
                            + "&note[4]=" + "左侧"
                            + "&note[5]=" + "左侧"
                            + "&note[6]=" + "右侧"
                            + "&note[7]=" + "右侧"
                            + "&note[8]=" + "右侧"
                            + "&note[9]=" + "右侧"
                            + "&note[10]=" + "右侧"
                            + "&note[11]=" + "右侧"

                            + "&catalog[0]=" + "部位"
                            + "&catalog[1]=" + "部位"
                            + "&catalog[2]=" + "部位"
                            + "&catalog[3]=" + "部位"
                            + "&catalog[4]=" + "部位"
                            + "&catalog[5]=" + "部位"
                            + "&catalog[6]=" + "部位"
                            + "&catalog[7]=" + "部位"
                            + "&catalog[8]=" + "部位"
                            + "&catalog[9]=" + "部位"
                            + "&catalog[10]=" + "部位"
                            + "&catalog[11]=" + "部位"

                            + "&explain[0]=" + "上肢"
                            + "&explain[1]=" + "下肢"
                            + "&explain[2]=" + "手"
                            + "&explain[3]=" + "颈部"
                            + "&explain[4]=" + "躯干"
                            + "&explain[5]=" + "其他"
                            + "&explain[6]=" + "上肢"
                            + "&explain[7]=" + "下肢"
                            + "&explain[8]=" + "手"
                            + "&explain[9]=" + "颈部"
                            + "&explain[10]=" + "躯干"
                            + "&explain[11]=" + "其他"

                            + "&token=" + NetUrlAddress.token;

                    if (isAdd == false) {
                        param += "&evaluation[groupid]=" + instructions_map.get(spinner_value)
                                + "&evaluation[grade]=" + "has_grade"
                                + "&r_id[0]=" + info.getRecord_id().get(0)
                                + "&r_id[1]=" + info.getRecord_id().get(1)
                                + "&r_id[2]=" + info.getRecord_id().get(2)
                                + "&r_id[3]=" + info.getRecord_id().get(3)
                                + "&r_id[4]=" + info.getRecord_id().get(4)
                                + "&r_id[5]=" + info.getRecord_id().get(5)
                                + "&r_id[6]=" + info.getRecord_id().get(6)
                                + "&r_id[7]=" + info.getRecord_id().get(7)
                                + "&r_id[8]=" + info.getRecord_id().get(8)
                                + "&r_id[9]=" + info.getRecord_id().get(9)
                                + "&r_id[10]=" + info.getRecord_id().get(10)
                                + "&r_id[11]=" + info.getRecord_id().get(11);
                    }
                    new MyThread().start();
                }
                break;
            case R.id.date_tv:
                pwTime.showAtLocation(date, Gravity.BOTTOM, 0, 0, new Date());
                break;

            default:
                break;
        }

    }

    /**
     * 获取返回数据
     */
    class MyThread extends Thread {

        public void run() {
            Looper.prepare();
            try {
                if (isAdd) {
                    create_code = TableAshworthInfo.doPost(NetUrlAddress.Ashworth_create_url, param);
                } else {
                    update_code = TableAshworthInfo.doPost(NetUrlAddress.Ashworth_update_url, param);
                }
                if (create_code == 200) {
                    TableAshworthInfoActivity.this.finish();
                    ShowToast.Short(getString(R.string.msg_save_success));
                } else if (update_code == 200) {
                    TableAshworthInfoActivity.this.finish();
                    ShowToast.Short(getString(R.string.msg_update_success));
                } else if (create_code == 100 || create_code == 500) {
                    ShowToast.Short(getString(R.string.msg_save_fail));
                } else if (update_code == 100 || create_code == 500) {
                    ShowToast.Short(getString(R.string.msg_update_fail));
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);

            } catch (Exception e) {
                L.d("text", "出错了");
                e.printStackTrace();
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
            Looper.loop();
        }
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
        if (TextUtils.isEmpty(date.getText().toString())) {
            ShowToast.Short(getString(R.string.msg_date));
            return true;
        } else if (radioButtonIsChecked(list_title1) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_title2) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_title3) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_title4) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_title5) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_title6) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_title7) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_title8) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_title9) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_title10) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_title11) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_title12) == false) {
            ShowToast.Short(msg);
            return true;
        } else {
            return false;
        }
    }

}