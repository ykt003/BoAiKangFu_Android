package me.zhangls.rilintech.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.zhangls.sortlistview.SortListActivity;

import java.util.ArrayList;
import java.util.Date;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.adapter.SpinnerBaseAdapter;
import me.zhangls.rilintech.application.MyApplication;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.TableRehabilitationGoalInfo;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;

/**
 * Created by YANG on 15/9/10.
 */
public class TableRehabilitationGoalInfoActivity extends BaseActivity implements View.OnClickListener {
    //返回键
    private ImageView back;
    //保存键
    private ImageView save;
    //缓冲球
    private GoogleProgressBar progressBar;
    //正在编辑的对象
    private TableRehabilitationGoalInfo info = null;
    //是否新建
    private Boolean isAdd = true;
    //次数
    private EditText counts;
    //主要问题
    private EditText main_problems;
    //次要问题
    private EditText minor_problems;
    //近期目标
    private TextView short_goals;
    //目标状态
    private Spinner state_goals_short;
    //中期目标
    private TextView medium_goals;
    //目标状态
    private Spinner state_goals_medium;
    //远期目标
    private TextView long_goals;
    //目标状态
    private Spinner state_goals_long;
    //家属预期
    private EditText family_expectations;
    //患者预期
    private EditText patient_expectations;
    //设定者
    private EditText makers;
    //设定时间
    private TextView set_time;
    //spinner的值集合
    private ArrayList<String> list_spinner_value = new ArrayList<>();
    //目标完成情况
    private String short_value, mediun_value, long_value;
    //spinner加载器
    private SpinnerBaseAdapter adapter;
    //时间选择器
    private TimePopupWindow pwTime;
    //拼接参数
    private String param;
    //新建响应码
    private int create_code = 0;
    //更新响应码
    private int update_code = 0;
    //用来接受拿到数据后发来的消息
    private static Handler handler;
    //第几次+1
    private int new_count;
    //添加近期目标
    private ImageView image_add_short_goals;
    //添加中期目标
    private ImageView image_add_medium_goals;
    //添加远期目标
    private ImageView image_add_long_goals;
    //近期目标布局
    private LinearLayout short_layout;
    //中期目标布局
    private LinearLayout medium_layout;
    //远期目标布局
    private LinearLayout long_layout;
    //用来标记新加的目标及状态
    private int tag_short = 0,
            tag_medium = 0,
            tag_long = 0,
            tag_short_state = 0,
            tag_medium_state = 0,
            tag_long_state = 0;
    //用来临时存储tag值
    private ArrayList<Integer> list_tag_short = new ArrayList<>();
    private ArrayList<Integer> list_tag_medium = new ArrayList<>();
    private ArrayList<Integer> list_tag_long = new ArrayList<>();
    private ArrayList<Integer> list_tag_short_state = new ArrayList<>();
    private ArrayList<Integer> list_tag_medium_state = new ArrayList<>();
    private ArrayList<Integer> list_tag_long_state = new ArrayList<>();

    //新加目标的个字段值
    private String short_values,
            medium_values,
            long_values,
            state_short_values,
            state_medium_values,
            state_long_values;
    //拼音检索响应码
    private static final int REQUEST_CODE_SHORT = 1111;
    private static final int REQUEST_CODE_MEDIUM = 2222;
    private static final int REQUEST_CODE_LONG = 3333;
    private static final int REQUEST_CODE_SHORT_NEW = 4444;
    private static final int REQUEST_CODE_MEDIUM_NEW = 5555;
    private static final int REQUEST_CODE_LONG_NEW = 6666;
    //当前点击View的tag
    private static int tag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table_rehabilitation_goal_info);

        getMyIntent();

        initView();

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

    }

    /**
     * 获取到从indexActivity传过来的数据
     */
    private void getMyIntent() {

        list_spinner_value.add("未达成");
        list_spinner_value.add("进行中");
        list_spinner_value.add("已达成");
        isAdd = getIntent().getBooleanExtra("isAdd", true);
        if (isAdd) {
            new_count = getIntent().getIntExtra("counts", 0);
        } else {
            info = (TableRehabilitationGoalInfo) getIntent().getParcelableExtra("info");
        }
    }


    private void initView() {
        back = (ImageView) findViewById(R.id.title_back);
        back.setOnClickListener(this);
        save = (ImageView) findViewById(R.id.title_save);
        save.setOnClickListener(this);
        progressBar = (GoogleProgressBar) findViewById(R.id.google_progress);
        progressBar.setVisibility(View.GONE);

        image_add_short_goals = (ImageView) findViewById(R.id.image_add_short_goals);
        image_add_short_goals.setOnClickListener(this);
        image_add_medium_goals = (ImageView) findViewById(R.id.image_add_medium_goals);
        image_add_medium_goals.setOnClickListener(this);
        image_add_long_goals = (ImageView) findViewById(R.id.image_add_long_goals);
        image_add_long_goals.setOnClickListener(this);

        short_layout = (LinearLayout) findViewById(R.id.add_short_goals_layout);
        medium_layout = (LinearLayout) findViewById(R.id.add_medium_goals_layout);
        long_layout = (LinearLayout) findViewById(R.id.add_long_goals_layout);

        counts = (EditText) findViewById(R.id.et_rt_counts);
        counts.setText(new_count + 1 + "");
        main_problems = (EditText) findViewById(R.id.et_rt_main_problems);
        minor_problems = (EditText) findViewById(R.id.et_rt_minor_problems);
        short_goals = (TextView) findViewById(R.id.et_rt_short_goals);
        short_goals.setOnClickListener(this);
        medium_goals = (TextView) findViewById(R.id.et_rt_medium_goals);
        medium_goals.setOnClickListener(this);
        long_goals = (TextView) findViewById(R.id.et_rt_long_goals);
        long_goals.setOnClickListener(this);
        state_goals_short = (Spinner) findViewById(R.id.spinner_short_rt_state_goals);
        state_goals_medium = (Spinner) findViewById(R.id.spinner_medium_rt_state_goals);
        state_goals_long = (Spinner) findViewById(R.id.spinner_long_rt_state_goals);
        family_expectations = (EditText) findViewById(R.id.et_rt_family_expectations);
        patient_expectations = (EditText) findViewById(R.id.et_rt_patient_expectations);
        makers = (EditText) findViewById(R.id.et_rt_setter);
        set_time = (TextView) findViewById(R.id.et_rt_set_date);
        set_time.setOnClickListener(this);

        //时间选择器
        pwTime = new TimePopupWindow(this, TimePopupWindow.Type.YEAR_MONTH_DAY);
        pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date d) {
                set_time.setText(MyApplication.getTime(d));
            }
        });
        pwTime.setCyclic(true);

        setAdapter();

        if (isAdd == false) {
            if (info != null) {
                setData();
            }
        }
    }

    private void setAdapter() {
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, state_goals_short, list_spinner_value);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        state_goals_short.setOnItemSelectedListener(new MyOnItemSelectedListener());
        state_goals_short.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, state_goals_medium, list_spinner_value);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        state_goals_medium.setOnItemSelectedListener(new MyOnItemSelectedListener());
        state_goals_medium.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, state_goals_long, list_spinner_value);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        state_goals_long.setOnItemSelectedListener(new MyOnItemSelectedListener());
        state_goals_long.setAdapter(adapter);
    }

    /**
     * isAdd = false，给控件赋值
     */
    private void setData() {

        counts.setText(info.getCounts() + "");
        counts.setEnabled(false);
        main_problems.setText(info.getMain_problems());
        minor_problems.setText(info.getMinor_problems());
        short_goals.setText(info.getShort_goals().split(",")[0]);
        medium_goals.setText(info.getMedium_goals().split(",")[0]);
        long_goals.setText(info.getLong_goals().split(",")[0]);
        setSpinnerData(list_spinner_value, info.getState_goals_short().split(",")[0], state_goals_short);
        setSpinnerData(list_spinner_value, info.getState_goals_medium().split(",")[0], state_goals_medium);
        setSpinnerData(list_spinner_value, info.getState_goals_long().split(",")[0], state_goals_long);

        //近期目标
        ArrayList<String> list_short = new ArrayList<>();
        for (String s : info.getShort_goals().split(",")) {
            list_short.add(s);
        }
        list_short.remove(0);

        ArrayList<String> list_short_state = new ArrayList<>();
        for (String s : info.getState_goals_short().split(",")) {
            list_short_state.add(s);
        }
        list_short_state.remove(0);

        for (int i = 0; i < list_short.size(); i++) {
            add_short_goals(list_short.get(i), list_short_state.get(i));
        }
        //中期目标
        ArrayList<String> list_medium = new ArrayList<>();
        for (String s : info.getMedium_goals().split(",")) {
            list_medium.add(s);
        }
        list_medium.remove(0);

        ArrayList<String> list_medium_state = new ArrayList<>();
        for (String s : info.getState_goals_medium().split(",")) {
            list_medium_state.add(s);
        }
        list_medium_state.remove(0);

        for (int i = 0; i < list_medium.size(); i++) {
            add_medium_goals(list_medium.get(i), list_medium_state.get(i));
        }
        //远期目标
        ArrayList<String> list_long = new ArrayList<>();
        for (String s : info.getLong_goals().split(",")) {
            list_long.add(s);
        }
        list_long.remove(0);

        ArrayList<String> list_long_state = new ArrayList<>();
        for (String s : info.getState_goals_long().split(",")) {
            list_long_state.add(s);
        }
        list_long_state.remove(0);

        for (int i = 0; i < list_long.size(); i++) {
            add_long_goals(list_long.get(i), list_long_state.get(i));
        }


        family_expectations.setText(info.getFamily_expectations());
        patient_expectations.setText(info.getPatient_expectations());
        makers.setText(info.getMakers());
        set_time.setText(info.getSet_time());


    }

    /**
     * 监听 Spinner点选
     */
    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (parent == state_goals_short) {
                short_value = list_spinner_value.get(position);
            }
            if (parent == state_goals_medium) {
                mediun_value = list_spinner_value.get(position);
            }
            if (parent == state_goals_long) {
                long_value = list_spinner_value.get(position);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /**
     * 编辑界面给每个spinner赋值
     *
     * @param list
     * @param value
     * @param spinner
     */
    private void setSpinnerData(ArrayList<String> list, String value, Spinner spinner) {
        for (int i = 0; i < list.size(); i++) {
            if ((list.get(i)).equals(value)) {
                spinner.setSelection(i, true);
                L.d("text", value);
                L.d("text", list.size() + "");

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                this.finish();
                break;
            case R.id.title_save:
                if (TextUtils.isEmpty(counts.getText())) {
                    ShowToast.Short("请填写制定次数");
                    break;
                }
                if (TextUtils.isEmpty(main_problems.getText())) {
                    ShowToast.Short("请填写主要问题");
                    break;
                }
                if (TextUtils.isEmpty(makers.getText())) {
                    ShowToast.Short("请填写设定者");
                    break;
                }
                if (TextUtils.isEmpty(set_time.getText())) {
                    ShowToast.Short("请选择设定日期");
                    break;
                } else {

                    progressBar.setVisibility(View.VISIBLE);

                    SharedPreferences sharedPreferences = getSharedPreferences("patientinfo", Activity.MODE_PRIVATE);
                    int id = sharedPreferences.getInt("id", -1);

                    SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                    int user_id = preferences.getInt("use_id", 2);

                    short_values = short_goals.getText().toString();
                    medium_values = medium_goals.getText().toString();
                    long_values = long_goals.getText().toString();
                    state_short_values = short_value;
                    state_medium_values = mediun_value;
                    state_long_values = long_value;

                    if (list_tag_short.size() > 0) {
                        for (int i : list_tag_short) {
                            TextView text = (TextView) short_layout.findViewWithTag(i).findViewById(R.id.et_rt_short_goals);
                            short_values += "," + isNull(text.getText().toString());
                        }
                    }
                    if (list_tag_medium.size() > 0) {
                        for (int i : list_tag_medium) {
                            TextView text = (TextView) medium_layout.findViewWithTag(i).findViewById(R.id.et_rt_medium_goals);
                            medium_values += "," + isNull(text.getText().toString());
                        }
                    }
                    if (list_tag_long.size() > 0) {
                        for (int i : list_tag_long) {
                            TextView text = (TextView) long_layout.findViewWithTag(i).findViewById(R.id.et_rt_long_goals);
                            long_values += "," + isNull(text.getText().toString());
                        }
                    }
                    if (list_tag_short_state.size() > 0) {
                        for (int i : list_tag_short_state) {
                            Spinner spinner = (Spinner) short_layout.findViewWithTag(i).findViewById(R.id.spinner_short_rt_state_goals);
                            state_short_values += "," + spinner.getSelectedItem().toString();
                        }
                    }
                    if (list_tag_medium_state.size() > 0) {
                        for (int i : list_tag_medium_state) {
                            Spinner spinner = (Spinner) medium_layout.findViewWithTag(i).findViewById(R.id.spinner_medium_rt_state_goals);
                            state_medium_values += "," + spinner.getSelectedItem().toString();
                        }
                    }
                    if (list_tag_long_state.size() > 0) {
                        for (int i : list_tag_long_state) {
                            Spinner spinner = (Spinner) long_layout.findViewWithTag(i).findViewById(R.id.spinner_long_rt_state_goals);
                            state_long_values += "," + spinner.getSelectedItem().toString();
                        }
                    }


                    param = "patient_info_id=" + id
                            + "&user_auth_id=" + user_id
                            + "&recovered_goal[cishu]=" + counts.getText()
                            + "&recovered_goal[re_mainq]=" + main_problems.getText()
                            + "&recovered_goal[re_secondq]=" + minor_problems.getText()

                            + "&recovered_goal[re_near_tm]=" + isNull(short_values) + ","
                            + "&recovered_goal[re_mid_tm]=" + isNull(medium_values) + ","
                            + "&recovered_goal[re_long_tm]=" + isNull(long_values) + ","
                            + "&recovered_goal[target_statey]=" + state_long_values + ","
                            + "&recovered_goal[target_statez]=" + state_medium_values + ","
                            + "&recovered_goal[target_state]=" + state_short_values + ","

                            + "&recovered_goal[re_home_yq]=" + family_expectations.getText()
                            + "&recovered_goal[re_patien_yq]=" + patient_expectations.getText()
                            + "&recovered_goal[executor]=" + makers.getText()
                            + "&recovered_goal[zhid_time]=" + set_time.getText().toString()

                            + "&token=" + NetUrlAddress.token;

                    new MyThread().start();
                }
                break;
            case R.id.et_rt_set_date:
                pwTime.showAtLocation(set_time, Gravity.BOTTOM, 0, 0, new Date());
                break;

            case R.id.image_add_short_goals:
                add_short_goals("", "");
                break;
            case R.id.image_add_medium_goals:
                add_medium_goals("", "");
                break;
            case R.id.image_add_long_goals:
                add_long_goals("", "");
                break;

            case R.id.et_rt_short_goals://近期目标
                Intent intent1 = new Intent(this, SortListActivity.class);
                intent1.putExtra("icf", 2);
                startActivityForResult(intent1, REQUEST_CODE_SHORT);

                break;
            case R.id.et_rt_medium_goals://中期目标
                Intent intent2 = new Intent(this, SortListActivity.class);
                intent2.putExtra("icf", 2);
                startActivityForResult(intent2, REQUEST_CODE_MEDIUM);

                break;
            case R.id.et_rt_long_goals://远期目标
                Intent intent3 = new Intent(this, SortListActivity.class);
                intent3.putExtra("icf", 2);
                startActivityForResult(intent3, REQUEST_CODE_LONG);

                break;

            default:
                break;
        }

    }

    /**
     * 添加近期目标
     *
     * @param text
     * @param spinner_value
     */
    private void add_short_goals(String text, String spinner_value) {
        tag_short += 1;
        tag_short_state += 1;
        list_tag_short.add(tag_short);
        list_tag_short_state.add(tag_short_state);

        final View view_short = LayoutInflater.from(this).inflate(R.layout.include_rehabilitation_goal_short, null);
        view_short.setTag(tag_short);
        short_layout.addView(view_short);

        final TextView editText = (TextView) view_short.findViewById(R.id.et_rt_short_goals);
        editText.setText(text);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TableRehabilitationGoalInfoActivity.this, SortListActivity.class);
                intent.putExtra("icf", 2);
                startActivityForResult(intent, REQUEST_CODE_SHORT_NEW);
                tag = (int)view_short.getTag();
            }
        });

        Spinner spinner = (Spinner) view_short.findViewById(R.id.spinner_short_rt_state_goals);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, spinner, list_spinner_value);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        //spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        spinner.setAdapter(adapter);

        setSpinnerData(list_spinner_value, spinner_value, spinner);

        ImageView delete_short = (ImageView) view_short.findViewById(R.id.delete_short);
        delete_short.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                short_layout.removeView(view_short);
                for (int i = 0; i < list_tag_short.size(); i++) {
                    if (list_tag_short.get(i) == view_short.getTag()) {
                        list_tag_short.remove(i);
                    }
                    if (list_tag_short_state.get(i) == view_short.getTag()) {
                        list_tag_short_state.remove(i);
                    }
                }
            }
        });
    }

    /**
     * 添加中期目标
     *
     * @param text
     * @param spinner_value
     */
    private void add_medium_goals(String text, String spinner_value) {
        tag_medium += 1;
        tag_medium_state += 1;
        list_tag_medium.add(tag_medium);
        list_tag_medium_state.add(tag_medium_state);

        final View view_medium = LayoutInflater.from(this).inflate(R.layout.include_rehabilitation_goal_medium, null);
        view_medium.setTag(tag_medium);
        medium_layout.addView(view_medium);

        TextView editText = (TextView) view_medium.findViewById(R.id.et_rt_medium_goals);
        editText.setText(text);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TableRehabilitationGoalInfoActivity.this, SortListActivity.class);
                intent.putExtra("icf", 2);
                startActivityForResult(intent, REQUEST_CODE_MEDIUM_NEW);
                tag = (int)view_medium.getTag();
            }
        });

        Spinner spinner = (Spinner) view_medium.findViewById(R.id.spinner_medium_rt_state_goals);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, spinner, list_spinner_value);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        //spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        spinner.setAdapter(adapter);

        setSpinnerData(list_spinner_value, spinner_value, spinner);

        ImageView delete_medium = (ImageView) view_medium.findViewById(R.id.delete_medium);
        delete_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medium_layout.removeView(view_medium);
                for (int i = 0; i < list_tag_medium.size(); i++) {
                    if (list_tag_medium.get(i) == view_medium.getTag()) {
                        list_tag_medium.remove(i);
                    }
                    if (list_tag_medium_state.get(i) == view_medium.getTag()) {
                        list_tag_medium_state.remove(i);
                    }
                }
            }
        });
    }

    /**
     * 添加远期目标
     *
     * @param text
     * @param spinner_value
     */
    private void add_long_goals(String text, String spinner_value) {
        tag_long += 1;
        tag_long_state += 1;
        list_tag_long.add(tag_long);
        list_tag_long_state.add(tag_long_state);

        final View view_long = LayoutInflater.from(this).inflate(R.layout.include_rehabilitation_goal_long, null);
        view_long.setTag(tag_long);
        long_layout.addView(view_long);

        TextView editText = (TextView) view_long.findViewById(R.id.et_rt_long_goals);
        editText.setText(text);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TableRehabilitationGoalInfoActivity.this, SortListActivity.class);
                intent.putExtra("icf", 2);
                startActivityForResult(intent, REQUEST_CODE_LONG_NEW);
                tag = (int)view_long.getTag();
            }
        });

        Spinner spinner = (Spinner) view_long.findViewById(R.id.spinner_long_rt_state_goals);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, spinner, list_spinner_value);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        //spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        spinner.setAdapter(adapter);

        setSpinnerData(list_spinner_value, spinner_value, spinner);

        ImageView delete_long = (ImageView) view_long.findViewById(R.id.delete_long);
        delete_long.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long_layout.removeView(view_long);
                for (int i = 0; i < list_tag_long.size(); i++) {
                    if (list_tag_long.get(i) == view_long.getTag()) {
                        list_tag_long.remove(i);
                    }
                    if (list_tag_long_state.get(i) == view_long.getTag()) {
                        list_tag_long_state.remove(i);
                    }
                }
            }
        });
    }

    /**
     * 获取返回数据
     */
    class MyThread extends Thread {

        public void run() {
            Looper.prepare();
            try {
                if (isAdd) {
                    create_code = TableRehabilitationGoalInfo.doPost(NetUrlAddress.re_create_url, param);
                } else {
                    String url = NetUrlAddress.re_update_url.replaceAll("aaa", info.getRecord_id() + "");
                    update_code = TableRehabilitationGoalInfo.doPost(url, param);
                }
                if (create_code == 200 || update_code == 200) {
                    TableRehabilitationGoalInfoActivity.this.finish();
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

    //判断是否为空字符串
    private String isNull(String s) {
        if (s.equals("")) {
            s = " ";
        }
        return s;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_SHORT:
                if (resultCode == RESULT_OK) {
                    String result_1 = data.getExtras().getString("result");
                    short_goals.setText(result_1);
                }
                break;
            case REQUEST_CODE_MEDIUM:
                if (resultCode == RESULT_OK) {
                    String result_2 = data.getExtras().getString("result");
                    medium_goals.setText(result_2);
                }
                break;
            case REQUEST_CODE_LONG:
                if (resultCode == RESULT_OK) {
                    String result_3 = data.getExtras().getString("result");
                    long_goals.setText(result_3);
                }
                break;
            case REQUEST_CODE_SHORT_NEW:
                if (resultCode == RESULT_OK) {
                    String result_4 = data.getExtras().getString("result");
                    TextView view = (TextView) short_layout.findViewWithTag(tag).findViewById(R.id.et_rt_short_goals);
                    view.setText(result_4);
                }
                break;
            case REQUEST_CODE_MEDIUM_NEW:
                if (resultCode == RESULT_OK) {
                    String result_5 = data.getExtras().getString("result");
                    TextView view = (TextView) medium_layout.findViewWithTag(tag).findViewById(R.id.et_rt_medium_goals);
                    view.setText(result_5);
                }
                break;
            case REQUEST_CODE_LONG_NEW:
                if (resultCode == RESULT_OK) {
                    String result_6 = data.getExtras().getString("result");
                    TextView view = (TextView) long_layout.findViewWithTag(tag).findViewById(R.id.et_rt_long_goals);
                    view.setText(result_6);
                }
                break;
        }
    }
}
