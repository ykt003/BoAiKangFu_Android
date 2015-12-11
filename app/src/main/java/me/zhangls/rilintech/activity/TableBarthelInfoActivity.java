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
import android.widget.Button;
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
import me.zhangls.rilintech.model.TableBarthelInfo;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;

/**
 * Created by YANG on 15/9/16.
 */
public class TableBarthelInfoActivity extends BaseActivity implements View.OnClickListener {

    //radiogroup
    private RadioGroup rg1, rg2, rg3, rg4, rg5, rg6, rg7, rg8, rg9, rg10;
    //计算总分
    private Button total_scores_bt;
    //总分
    private TextView total_scores_tv;
    //选中的答案
    private String answer1, answer2, answer3, answer4, answer5, answer6, answer7, answer8, answer9, answer10;
    //计分器
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
    //评定说明当前选中的值
    private String spinner_value;
    //spinner加载器
    private ArrayAdapter<String> adapter;
    //评定说明的选项
    private String[] arr_instructions = new String[]{"初评", "中评Ⅰ", "中评Ⅱ", "中评Ⅲ", "中评Ⅳ", "中评Ⅴ", "末评"};
    //评定说明int
    private Map<String,Integer> instructions_map;
    //去除已经存在的评定说明后的spinner数据
    private List<String> list_instructions_new = new ArrayList<>();
    //评定说明
    private Spinner spinner_instructions;
    //时间选择器
    private TimePopupWindow pwTime;
    private String msg = "请检查是否填写完整...";
    //正在编辑的对象
    private TableBarthelInfo info = null;
    //是否新建
    private Boolean isAdd = true;
    //返回键
    private ImageView key_back;
    //保存键
    private ImageView key_save;
    //编辑键
    private ImageView key_edit;
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
    //所有的选项
    private RadioButton title1_rb1, title1_rb2, title1_rb3, title1_rb4;
    private RadioButton title2_rb1, title2_rb2;
    private RadioButton title3_rb1, title3_rb2;
    private RadioButton title4_rb1, title4_rb2, title4_rb3;
    private RadioButton title5_rb1, title5_rb2, title5_rb3;
    private RadioButton title6_rb1, title6_rb2, title6_rb3;
    private RadioButton title7_rb1, title7_rb2, title7_rb3;
    private RadioButton title8_rb1, title8_rb2, title8_rb3, title8_rb4;
    private RadioButton title9_rb1, title9_rb2, title9_rb3, title9_rb4;
    private RadioButton title10_rb1, title10_rb2, title10_rb3;
    //所有的选项集
    private List<RadioButton> list_barthel_title1 = new ArrayList<>();
    private List<RadioButton> list_barthel_title2 = new ArrayList<>();
    private List<RadioButton> list_barthel_title3 = new ArrayList<>();
    private List<RadioButton> list_barthel_title4 = new ArrayList<>();
    private List<RadioButton> list_barthel_title5 = new ArrayList<>();
    private List<RadioButton> list_barthel_title6 = new ArrayList<>();
    private List<RadioButton> list_barthel_title7 = new ArrayList<>();
    private List<RadioButton> list_barthel_title8 = new ArrayList<>();
    private List<RadioButton> list_barthel_title9 = new ArrayList<>();
    private List<RadioButton> list_barthel_title10 = new ArrayList<>();

    private Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_barthel_info);

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
                    case 3:
                        counts = counts1 + counts2 + counts3 + counts4 + counts5 + counts6
                                + counts7 + counts8 + counts9 + counts10;
                        total_scores_tv.setText(counts + "");
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

        for(String s:arr_instructions){
            list_instructions_new.add(s);
        }
        for(int i=0;i<list_instructions_cloud.size();i++){
            if(list_instructions_new.contains(list_instructions_cloud.get(i))){
                list_instructions_new.remove(list_instructions_cloud.get(i));
            }
        }
        if(isAdd == false){//如果是编辑界面，需要把当前的评定说明加回去
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
            info = (TableBarthelInfo) getIntent().getParcelableExtra("info");
            list_instructions_cloud = getIntent().getStringArrayListExtra("instructions");
            L.d("size", list_instructions_cloud.size() + "");
        }
    }


    private void initView() {

        instructions_map = new HashMap<>();
        instructions_map.put("初评",0);
        instructions_map.put("中评Ⅰ",1);
        instructions_map.put("末评",2);
        instructions_map.put("中评Ⅱ",3);
        instructions_map.put("中评Ⅲ",4);
        instructions_map.put("中评Ⅳ",5);
        instructions_map.put("中评Ⅴ",6);

        key_back = (ImageView) findViewById(R.id.tittle_back);
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
        adapter = new SpinnerBaseAdapter(this,android.R.layout.simple_spinner_item,spinner_instructions,list_instructions_new);
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
        //spinner
        rg1 = (RadioGroup) findViewById(R.id.barthel_title1_rg);
        rg2 = (RadioGroup) findViewById(R.id.barthel_title2_rg);
        rg3 = (RadioGroup) findViewById(R.id.barthel_title3_rg);
        rg4 = (RadioGroup) findViewById(R.id.barthel_title4_rg);
        rg5 = (RadioGroup) findViewById(R.id.barthel_title5_rg);
        rg6 = (RadioGroup) findViewById(R.id.barthel_title6_rg);
        rg7 = (RadioGroup) findViewById(R.id.barthel_title7_rg);
        rg8 = (RadioGroup) findViewById(R.id.barthel_title8_rg);
        rg9 = (RadioGroup) findViewById(R.id.barthel_title9_rg);
        rg10 = (RadioGroup) findViewById(R.id.barthel_title10_rg);
        setListenerOnRadioGroup();

        //计算总分按钮
        total_scores_bt = (Button) findViewById(R.id.barthel_total_scores_bt);
        total_scores_bt.setOnClickListener(this);
        total_scores_tv = (TextView) findViewById(R.id.barthel_total_scores_tv);

        title1_rb1 = (RadioButton) findViewById(R.id.barthel_title1_rb1);
        title1_rb2 = (RadioButton) findViewById(R.id.barthel_title1_rb2);
        title1_rb3 = (RadioButton) findViewById(R.id.barthel_title1_rb3);
        title1_rb4 = (RadioButton) findViewById(R.id.barthel_title1_rb4);
        list_barthel_title1.add(title1_rb1);
        list_barthel_title1.add(title1_rb2);
        list_barthel_title1.add(title1_rb3);
        list_barthel_title1.add(title1_rb4);

        title2_rb1 = (RadioButton) findViewById(R.id.barthel_title2_rb1);
        title2_rb2 = (RadioButton) findViewById(R.id.barthel_title2_rb2);
        list_barthel_title2.add(title2_rb1);
        list_barthel_title2.add(title2_rb2);

        title3_rb1 = (RadioButton) findViewById(R.id.barthel_title3_rb1);
        title3_rb2 = (RadioButton) findViewById(R.id.barthel_title3_rb2);
        list_barthel_title3.add(title3_rb1);
        list_barthel_title3.add(title3_rb2);

        title4_rb1 = (RadioButton) findViewById(R.id.barthel_title4_rb1);
        title4_rb2 = (RadioButton) findViewById(R.id.barthel_title4_rb2);
        title4_rb3 = (RadioButton) findViewById(R.id.barthel_title4_rb3);
        list_barthel_title4.add(title4_rb1);
        list_barthel_title4.add(title4_rb2);
        list_barthel_title4.add(title4_rb3);

        title5_rb1 = (RadioButton) findViewById(R.id.barthel_title5_rb1);
        title5_rb2 = (RadioButton) findViewById(R.id.barthel_title5_rb2);
        title5_rb3 = (RadioButton) findViewById(R.id.barthel_title5_rb3);
        list_barthel_title5.add(title5_rb1);
        list_barthel_title5.add(title5_rb2);
        list_barthel_title5.add(title5_rb3);

        title6_rb1 = (RadioButton) findViewById(R.id.barthel_title6_rb1);
        title6_rb2 = (RadioButton) findViewById(R.id.barthel_title6_rb2);
        title6_rb3 = (RadioButton) findViewById(R.id.barthel_title6_rb3);
        list_barthel_title6.add(title6_rb1);
        list_barthel_title6.add(title6_rb2);
        list_barthel_title6.add(title6_rb3);

        title7_rb1 = (RadioButton) findViewById(R.id.barthel_title7_rb1);
        title7_rb2 = (RadioButton) findViewById(R.id.barthel_title7_rb2);
        title7_rb3 = (RadioButton) findViewById(R.id.barthel_title7_rb3);
        list_barthel_title7.add(title7_rb1);
        list_barthel_title7.add(title7_rb2);
        list_barthel_title7.add(title7_rb3);

        title8_rb1 = (RadioButton) findViewById(R.id.barthel_title8_rb1);
        title8_rb2 = (RadioButton) findViewById(R.id.barthel_title8_rb2);
        title8_rb3 = (RadioButton) findViewById(R.id.barthel_title8_rb3);
        title8_rb4 = (RadioButton) findViewById(R.id.barthel_title8_rb4);
        list_barthel_title8.add(title8_rb1);
        list_barthel_title8.add(title8_rb2);
        list_barthel_title8.add(title8_rb3);
        list_barthel_title8.add(title8_rb4);

        title9_rb1 = (RadioButton) findViewById(R.id.barthel_title9_rb1);
        title9_rb2 = (RadioButton) findViewById(R.id.barthel_title9_rb2);
        title9_rb3 = (RadioButton) findViewById(R.id.barthel_title9_rb3);
        title9_rb4 = (RadioButton) findViewById(R.id.barthel_title9_rb4);
        list_barthel_title9.add(title9_rb1);
        list_barthel_title9.add(title9_rb2);
        list_barthel_title9.add(title9_rb3);
        list_barthel_title9.add(title9_rb4);

        title10_rb1 = (RadioButton) findViewById(R.id.barthel_title10_rb1);
        title10_rb2 = (RadioButton) findViewById(R.id.barthel_title10_rb2);
        title10_rb3 = (RadioButton) findViewById(R.id.barthel_title10_rb3);
        list_barthel_title10.add(title10_rb1);
        list_barthel_title10.add(title10_rb2);
        list_barthel_title10.add(title10_rb3);

        if (isAdd == false) {
            if (info != null) {
                setData();
            }
        }
    }

    /**
     * 监听radiogroup
     */
    private void setListenerOnRadioGroup(){

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableBarthelInfoActivity.this.findViewById(group.getCheckedRadioButtonId());
                answer1 = s.getText().toString();
                counts1 = Integer.parseInt(answer1.substring(0, 2).trim());
                message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }
        });
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableBarthelInfoActivity.this.findViewById(group.getCheckedRadioButtonId());
                answer2 = s.getText().toString();
                counts2 = Integer.parseInt(answer2.substring(0, 2).trim());
                //发消息，动态改变总分显示
                message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }
        });
        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableBarthelInfoActivity.this.findViewById(group.getCheckedRadioButtonId());
                answer3 = s.getText().toString();
                counts3 = Integer.parseInt(answer3.substring(0, 2).trim());
                message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }
        });
        rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableBarthelInfoActivity.this.findViewById(group.getCheckedRadioButtonId());
                answer4 = s.getText().toString();
                counts4 = Integer.parseInt(answer4.substring(0, 2).trim());
                message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }
        });
        rg5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableBarthelInfoActivity.this.findViewById(group.getCheckedRadioButtonId());
                answer5 = s.getText().toString();
                counts5 = Integer.parseInt(answer5.substring(0, 2).trim());
                message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }
        });
        rg6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableBarthelInfoActivity.this.findViewById(group.getCheckedRadioButtonId());
                answer6 = s.getText().toString();
                counts6 = Integer.parseInt(answer6.substring(0, 2).trim());
                message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }
        });
        rg7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableBarthelInfoActivity.this.findViewById(group.getCheckedRadioButtonId());
                answer7 = s.getText().toString();
                counts7 = Integer.parseInt(answer7.substring(0, 2).trim());
                message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }
        });
        rg8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableBarthelInfoActivity.this.findViewById(group.getCheckedRadioButtonId());
                answer8 = s.getText().toString();
                counts8 = Integer.parseInt(answer8.substring(0, 2).trim());
                message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }
        });
        rg9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableBarthelInfoActivity.this.findViewById(group.getCheckedRadioButtonId());
                answer9 = s.getText().toString();
                counts9 = Integer.parseInt(answer9.substring(0, 2).trim());
                message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }
        });
        rg10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton s = (RadioButton) TableBarthelInfoActivity.this.findViewById(group.getCheckedRadioButtonId());
                answer10 = s.getText().toString();
                counts10 = Integer.parseInt(answer10.substring(0, 2).trim());
                message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }
        });


    }

    /**
     * 编辑界面给每个radiogroup赋值
     * @param list
     * @param s
     */
    private void setRadioButtonData(List<RadioButton> list, int s){
        for(int i=0;i<list.size();i++){
            if(s == Integer.parseInt((list.get(i).getText()).toString().substring(0, 2).trim())){
                list.get(i).setChecked(true);
            }
        }
    }

    /**
     * 给所有控件赋值
     */
    private void setData(){

        date.setText(info.getDate().substring(0, 10) + " " + info.getDate().substring(11, 16));

        for (int i = 0; i < list_instructions_new.size(); i++) {
            if ((list_instructions_new.get(i)).equals(info.getInstructions())) {
                spinner_instructions.setSelection(i);
            }
        }
        setRadioButtonData(list_barthel_title1, info.getScores().get(0));
        setRadioButtonData(list_barthel_title2, info.getScores().get(1));
        setRadioButtonData(list_barthel_title3, info.getScores().get(2));
        setRadioButtonData(list_barthel_title4, info.getScores().get(3));
        setRadioButtonData(list_barthel_title5, info.getScores().get(4));
        setRadioButtonData(list_barthel_title6, info.getScores().get(5));
        setRadioButtonData(list_barthel_title7, info.getScores().get(6));
        setRadioButtonData(list_barthel_title8, info.getScores().get(7));
        setRadioButtonData(list_barthel_title9, info.getScores().get(8));
        setRadioButtonData(list_barthel_title10, info.getScores().get(9));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tittle_back:
                this.finish();
                break;
            case R.id.patient_save:
                if (dataIsEmpty()) {

                }else {
                    progressBar.setVisibility(View.VISIBLE);

                    param = "patient_info_id=" + patient_info_id
                            + "&user_auth_id=" + user_auth_id
                            + "&menu_id=" + 29
                            //+ "&evaluation[sum]=" + "has_sum"
                            + "&evaluation[grade]=" + "has_grade"
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

                            + "&note[0]=" + ""
                            + "&note[1]=" + ""
                            + "&note[2]=" + ""
                            + "&note[3]=" + ""
                            + "&note[4]=" + ""
                            + "&note[5]=" + ""
                            + "&note[6]=" + ""
                            + "&note[7]=" + ""
                            + "&note[8]=" + ""
                            + "&note[9]=" + ""

                            + "&catalog[0]=" + ""
                            + "&catalog[1]=" + ""
                            + "&catalog[2]=" + ""
                            + "&catalog[3]=" + ""
                            + "&catalog[4]=" + ""
                            + "&catalog[5]=" + ""
                            + "&catalog[6]=" + ""
                            + "&catalog[7]=" + ""
                            + "&catalog[8]=" + ""
                            + "&catalog[9]=" + ""

                            + "&explain[0]=" + "1.进食"
                            + "&explain[1]=" + "2.洗澡"
                            + "&explain[2]=" + "3.修饰"
                            + "&explain[3]=" + "4.穿衣"
                            + "&explain[4]=" + "5.控制大便"
                            + "&explain[5]=" + "6.控制小便"
                            + "&explain[6]=" + "7.用厕所"
                            + "&explain[7]=" + "8.床椅转移"
                            + "&explain[8]=" + "9.平地走45米"
                            + "&explain[9]=" + "10.上下楼梯"

                            + "&token=" + NetUrlAddress.token;

                    if (isAdd == false){
                        param += "&evaluation[sum_id]=" + info.getRecord_id().get(10)
                                + "&evaluation[grade_id]=" + info.getRecord_id().get(11)
                                + "&evaluation[groupid]=" + instructions_map.get(spinner_value)
                                + "&evaluation[sum]=" + "sum"
                                + "&r_id[0]=" + info.getRecord_id().get(0)
                                + "&r_id[1]=" + info.getRecord_id().get(1)
                                + "&r_id[2]=" + info.getRecord_id().get(2)
                                + "&r_id[3]=" + info.getRecord_id().get(3)
                                + "&r_id[4]=" + info.getRecord_id().get(4)
                                + "&r_id[5]=" + info.getRecord_id().get(5)
                                + "&r_id[6]=" + info.getRecord_id().get(6)
                                + "&r_id[7]=" + info.getRecord_id().get(7)
                                + "&r_id[8]=" + info.getRecord_id().get(8)
                                + "&r_id[9]=" + info.getRecord_id().get(9);
                    }else{
                        param += "&evaluation[sum]=" + "has_sum";
                    }

                    new MyThread().start();
                }
                break;
            case R.id.date_tv:
                pwTime.showAtLocation(date, Gravity.BOTTOM, 0, 0, new Date());
                break;
            case R.id.barthel_total_scores_bt:
                counts = counts1 + counts2 + counts3 + counts4 + counts5 + counts6
                        + counts7 + counts8 + counts9 + counts10;
                total_scores_tv.setText(counts + "");
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
                    create_code = TableBarthelInfo.doPost(NetUrlAddress.Barthel_create_url, param);
                } else {
                    update_code = TableBarthelInfo.doPost(NetUrlAddress.Barthel_update_url, param);
                }
                if (create_code == 200 || update_code == 200) {
                    TableBarthelInfoActivity.this.finish();
                    ShowToast.Short("操作成功...");
                } else if (create_code == 100 || create_code == 500) {
                    ShowToast.Short("保存失败...");
                } else if (update_code == 100 || create_code == 500) {
                    ShowToast.Short("更新失败...");
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
            ShowToast.Short("请选择评定日期...");
            return true;
        } else if (radioButtonIsChecked(list_barthel_title1) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_barthel_title2) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_barthel_title3) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_barthel_title4) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_barthel_title5) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_barthel_title6) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_barthel_title7) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_barthel_title8) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_barthel_title9) == false) {
            ShowToast.Short(msg);
            return true;
        } else if (radioButtonIsChecked(list_barthel_title10) == false) {
            ShowToast.Short(msg);
            return true;
        }  else {
            return false;
        }
    }

}