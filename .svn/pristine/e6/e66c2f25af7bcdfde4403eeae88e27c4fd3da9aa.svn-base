package me.zhangls.rilintech.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import me.zhangls.rilintech.model.TableRomInfo;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;

/**
 * Created by YANG on 15/9/23.
 */
public class TableRomInfoActivity extends BaseActivity implements View.OnClickListener {

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
    //正在编辑的对象
    private TableRomInfo info = null;
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
    //所有的选项
    private EditText left_init_1, left_init_2, left_init_3, left_init_4, left_init_5, left_init_6, left_init_7, left_init_8,
            left_init_9, left_init_10, left_init_11, left_init_12, left_init_13, left_init_14, left_init_15, left_init_16,
            left_init_17, left_init_18, left_init_19, left_init_20, left_init_21, left_init_22, left_init_23,
            left_init_24, left_init_25, left_init_26, left_init_27, left_init_28, left_init_29, left_init_30,
            left_init_31, left_init_32, left_init_33, left_init_34, left_init_35, left_init_36, left_init_37,
            left_init_38, left_init_39, left_init_40, left_init_41, left_init_42, left_init_43, left_init_44,
            left_init_45, left_init_46, left_init_47, left_init_48, left_init_49, left_init_50, left_init_51,
            left_init_52, left_init_53, left_init_54, left_init_55, left_init_56, left_init_57, left_init_58,
            left_init_59, left_init_60, left_init_61, left_init_62, left_init_63, left_init_64, left_init_65,
            left_init_66, left_init_67, left_init_68, left_init_69, left_init_70;
    private EditText left_passive_1, left_passive_2, left_passive_3, left_passive_4, left_passive_5,
            left_passive_6, left_passive_7, left_passive_8, left_passive_9, left_passive_10, left_passive_11,
            left_passive_12, left_passive_13, left_passive_14, left_passive_15, left_passive_16, left_passive_17,
            left_passive_18, left_passive_19, left_passive_20, left_passive_21, left_passive_22, left_passive_23,
            left_passive_24, left_passive_25, left_passive_26, left_passive_27, left_passive_28, left_passive_29,
            left_passive_30, left_passive_31, left_passive_32, left_passive_33, left_passive_34, left_passive_35,
            left_passive_36, left_passive_37, left_passive_38, left_passive_39, left_passive_40, left_passive_41,
            left_passive_42, left_passive_43, left_passive_44, left_passive_45, left_passive_46, left_passive_47,
            left_passive_48, left_passive_49, left_passive_50, left_passive_51, left_passive_52, left_passive_53,
            left_passive_54, left_passive_55, left_passive_56, left_passive_57, left_passive_58, left_passive_59,
            left_passive_60, left_passive_61, left_passive_62, left_passive_63, left_passive_64, left_passive_65,
            left_passive_66, left_passive_67, left_passive_68, left_passive_69, left_passive_70;
    private EditText right_init_1, right_init_2, right_init_3, right_init_4, right_init_5, right_init_6, right_init_7, right_init_8,
            right_init_9, right_init_10, right_init_11, right_init_12, right_init_13, right_init_14, right_init_15, right_init_16,
            right_init_17, right_init_18, right_init_19, right_init_20, right_init_21, right_init_22, right_init_23,
            right_init_24, right_init_25, right_init_26, right_init_27, right_init_28, right_init_29, right_init_30,
            right_init_31, right_init_32, right_init_33, right_init_34, right_init_35, right_init_36, right_init_37,
            right_init_38, right_init_39, right_init_40, right_init_41, right_init_42, right_init_43, right_init_44,
            right_init_45, right_init_46, right_init_47, right_init_48, right_init_49, right_init_50, right_init_51,
            right_init_52, right_init_53, right_init_54, right_init_55, right_init_56, right_init_57, right_init_58,
            right_init_59, right_init_60, right_init_61, right_init_62, right_init_63, right_init_64, right_init_65,
            right_init_66, right_init_67, right_init_68, right_init_69, right_init_70;
    private EditText right_passive_1, right_passive_2, right_passive_3, right_passive_4, right_passive_5, right_passive_6, right_passive_7, right_passive_8,
            right_passive_9, right_passive_10, right_passive_11, right_passive_12, right_passive_13, right_passive_14, right_passive_15, right_passive_16,
            right_passive_17, right_passive_18, right_passive_19, right_passive_20, right_passive_21, right_passive_22, right_passive_23,
            right_passive_24, right_passive_25, right_passive_26, right_passive_27, right_passive_28, right_passive_29, right_passive_30,
            right_passive_31, right_passive_32, right_passive_33, right_passive_34, right_passive_35, right_passive_36, right_passive_37,
            right_passive_38, right_passive_39, right_passive_40, right_passive_41, right_passive_42, right_passive_43, right_passive_44,
            right_passive_45, right_passive_46, right_passive_47, right_passive_48, right_passive_49, right_passive_50, right_passive_51,
            right_passive_52, right_passive_53, right_passive_54, right_passive_55, right_passive_56, right_passive_57, right_passive_58,
            right_passive_59, right_passive_60, right_passive_61, right_passive_62, right_passive_63, right_passive_64, right_passive_65,
            right_passive_66, right_passive_67, right_passive_68, right_passive_69, right_passive_70;

    //所有的选项集
    private List<EditText> list_left_init = new ArrayList<>();
    private List<EditText> list_left_passive = new ArrayList<>();
    private List<EditText> list_right_init = new ArrayList<>();
    private List<EditText> list_right_passive = new ArrayList<>();

    /**
     * 判断当前Activity界面是否加载完成
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            new InitUIView().execute();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_rom_info);

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
        //initView();

    }

    /**
     * 异步加载界面
     */
    class InitUIView extends AsyncTask<Void,Integer,Boolean>{
        @Override
        protected Boolean doInBackground(Void... params) {
            return true;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean){
                LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
                View view = getLayoutInflater().inflate(R.layout.activity_table_rom_info_include, null);
                layout.addView(view);
                initView();
                progressBar.setVisibility(View.GONE);
            }
        }
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

        instructions_map = new HashMap<>();
        instructions_map.put(getString(R.string.evaluation0), 0);
        instructions_map.put(getString(R.string.evaluation1), 1);
        instructions_map.put(getString(R.string.evaluation2), 2);
        instructions_map.put(getString(R.string.evaluation3), 3);
        instructions_map.put(getString(R.string.evaluation4), 4);
        instructions_map.put(getString(R.string.evaluation5), 5);
        instructions_map.put(getString(R.string.evaluation6), 6);

        key_back = (ImageView) findViewById(R.id.tittle_back);
        key_back.setOnClickListener(this);
        key_save = (ImageView) findViewById(R.id.patient_save);
        key_save.setOnClickListener(this);
        progressBar = (GoogleProgressBar) findViewById(R.id.google_progress);
        //progressBar.setVisibility(View.GONE);

        date = (TextView) findViewById(R.id.date);
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

        left_init_1 = (EditText) findViewById(R.id.left_init_et1);
        left_init_2 = (EditText) findViewById(R.id.left_init_et2);
        left_init_3 = (EditText) findViewById(R.id.left_init_et3);
        left_init_4 = (EditText) findViewById(R.id.left_init_et4);
        left_init_5 = (EditText) findViewById(R.id.left_init_et5);
        left_init_6 = (EditText) findViewById(R.id.left_init_et6);
        left_init_7 = (EditText) findViewById(R.id.left_init_et7);
        left_init_8 = (EditText) findViewById(R.id.left_init_et8);
        left_init_9 = (EditText) findViewById(R.id.left_init_et9);
        left_init_10 = (EditText) findViewById(R.id.left_init_et10);
        left_init_11 = (EditText) findViewById(R.id.left_init_et11);
        left_init_12 = (EditText) findViewById(R.id.left_init_et12);
        left_init_13 = (EditText) findViewById(R.id.left_init_et13);
        left_init_14 = (EditText) findViewById(R.id.left_init_et14);
        left_init_15 = (EditText) findViewById(R.id.left_init_et15);
        left_init_16 = (EditText) findViewById(R.id.left_init_et16);
        left_init_17 = (EditText) findViewById(R.id.left_init_et17);
        left_init_18 = (EditText) findViewById(R.id.left_init_et18);
        left_init_19 = (EditText) findViewById(R.id.left_init_et19);
        left_init_20 = (EditText) findViewById(R.id.left_init_et20);
        left_init_21 = (EditText) findViewById(R.id.left_init_et21);
        left_init_22 = (EditText) findViewById(R.id.left_init_et22);
        left_init_23 = (EditText) findViewById(R.id.left_init_et23);
        left_init_24 = (EditText) findViewById(R.id.left_init_et24);
        left_init_25 = (EditText) findViewById(R.id.left_init_et25);
        left_init_26 = (EditText) findViewById(R.id.left_init_et26);
        left_init_27 = (EditText) findViewById(R.id.left_init_et27);
        left_init_28 = (EditText) findViewById(R.id.left_init_et28);
        left_init_29 = (EditText) findViewById(R.id.left_init_et29);
        left_init_30 = (EditText) findViewById(R.id.left_init_et30);
        left_init_31 = (EditText) findViewById(R.id.left_init_et31);
        left_init_32 = (EditText) findViewById(R.id.left_init_et32);
        left_init_33 = (EditText) findViewById(R.id.left_init_et33);
        left_init_34 = (EditText) findViewById(R.id.left_init_et34);
        left_init_35 = (EditText) findViewById(R.id.left_init_et35);
        left_init_36 = (EditText) findViewById(R.id.left_init_et36);
        left_init_37 = (EditText) findViewById(R.id.left_init_et37);
        left_init_38 = (EditText) findViewById(R.id.left_init_et38);
        left_init_39 = (EditText) findViewById(R.id.left_init_et39);
        left_init_40 = (EditText) findViewById(R.id.left_init_et40);
        left_init_41 = (EditText) findViewById(R.id.left_init_et41);
        left_init_42 = (EditText) findViewById(R.id.left_init_et42);
        left_init_43 = (EditText) findViewById(R.id.left_init_et43);
        left_init_44 = (EditText) findViewById(R.id.left_init_et44);
        left_init_45 = (EditText) findViewById(R.id.left_init_et45);
        left_init_46 = (EditText) findViewById(R.id.left_init_et46);
        left_init_47 = (EditText) findViewById(R.id.left_init_et47);
        left_init_48 = (EditText) findViewById(R.id.left_init_et48);
        left_init_49 = (EditText) findViewById(R.id.left_init_et49);
        left_init_50 = (EditText) findViewById(R.id.left_init_et50);
        left_init_51 = (EditText) findViewById(R.id.left_init_et51);
        left_init_52 = (EditText) findViewById(R.id.left_init_et52);
        left_init_53 = (EditText) findViewById(R.id.left_init_et53);
        left_init_54 = (EditText) findViewById(R.id.left_init_et54);
        left_init_55 = (EditText) findViewById(R.id.left_init_et55);
        left_init_56 = (EditText) findViewById(R.id.left_init_et56);
        left_init_57 = (EditText) findViewById(R.id.left_init_et57);
        left_init_58 = (EditText) findViewById(R.id.left_init_et58);
        left_init_59 = (EditText) findViewById(R.id.left_init_et59);
        left_init_60 = (EditText) findViewById(R.id.left_init_et60);
        left_init_61 = (EditText) findViewById(R.id.left_init_et61);
        left_init_62 = (EditText) findViewById(R.id.left_init_et62);
        left_init_63 = (EditText) findViewById(R.id.left_init_et63);
        left_init_64 = (EditText) findViewById(R.id.left_init_et64);
        left_init_65 = (EditText) findViewById(R.id.left_init_et65);
        left_init_66 = (EditText) findViewById(R.id.left_init_et66);
        left_init_67 = (EditText) findViewById(R.id.left_init_et67);
        left_init_68 = (EditText) findViewById(R.id.left_init_et68);
        left_init_69 = (EditText) findViewById(R.id.left_init_et69);
        left_init_70 = (EditText) findViewById(R.id.left_init_et70);

        list_left_init.add(left_init_1);
        list_left_init.add(left_init_2);
        list_left_init.add(left_init_3);
        list_left_init.add(left_init_4);
        list_left_init.add(left_init_5);
        list_left_init.add(left_init_6);
        list_left_init.add(left_init_7);
        list_left_init.add(left_init_8);
        list_left_init.add(left_init_9);
        list_left_init.add(left_init_10);
        list_left_init.add(left_init_11);
        list_left_init.add(left_init_12);
        list_left_init.add(left_init_13);
        list_left_init.add(left_init_14);
        list_left_init.add(left_init_15);
        list_left_init.add(left_init_16);
        list_left_init.add(left_init_17);
        list_left_init.add(left_init_18);
        list_left_init.add(left_init_19);
        list_left_init.add(left_init_20);
        list_left_init.add(left_init_21);
        list_left_init.add(left_init_22);
        list_left_init.add(left_init_23);
        list_left_init.add(left_init_24);
        list_left_init.add(left_init_25);
        list_left_init.add(left_init_26);
        list_left_init.add(left_init_27);
        list_left_init.add(left_init_28);
        list_left_init.add(left_init_29);
        list_left_init.add(left_init_30);
        list_left_init.add(left_init_31);
        list_left_init.add(left_init_32);
        list_left_init.add(left_init_33);
        list_left_init.add(left_init_34);
        list_left_init.add(left_init_35);
        list_left_init.add(left_init_36);
        list_left_init.add(left_init_37);
        list_left_init.add(left_init_38);
        list_left_init.add(left_init_39);
        list_left_init.add(left_init_40);
        list_left_init.add(left_init_41);
        list_left_init.add(left_init_42);
        list_left_init.add(left_init_43);
        list_left_init.add(left_init_44);
        list_left_init.add(left_init_45);
        list_left_init.add(left_init_46);
        list_left_init.add(left_init_47);
        list_left_init.add(left_init_48);
        list_left_init.add(left_init_49);
        list_left_init.add(left_init_50);
        list_left_init.add(left_init_51);
        list_left_init.add(left_init_52);
        list_left_init.add(left_init_53);
        list_left_init.add(left_init_54);
        list_left_init.add(left_init_55);
        list_left_init.add(left_init_56);
        list_left_init.add(left_init_57);
        list_left_init.add(left_init_58);
        list_left_init.add(left_init_59);
        list_left_init.add(left_init_60);
        list_left_init.add(left_init_61);
        list_left_init.add(left_init_62);
        list_left_init.add(left_init_63);
        list_left_init.add(left_init_64);
        list_left_init.add(left_init_65);
        list_left_init.add(left_init_66);
        list_left_init.add(left_init_67);
        list_left_init.add(left_init_68);
        list_left_init.add(left_init_69);
        list_left_init.add(left_init_70);

        left_passive_1 = (EditText) findViewById(R.id.left_passive_et1);
        left_passive_2 = (EditText) findViewById(R.id.left_passive_et2);
        left_passive_3 = (EditText) findViewById(R.id.left_passive_et3);
        left_passive_4 = (EditText) findViewById(R.id.left_passive_et4);
        left_passive_5 = (EditText) findViewById(R.id.left_passive_et5);
        left_passive_6 = (EditText) findViewById(R.id.left_passive_et6);
        left_passive_7 = (EditText) findViewById(R.id.left_passive_et7);
        left_passive_8 = (EditText) findViewById(R.id.left_passive_et8);
        left_passive_9 = (EditText) findViewById(R.id.left_passive_et9);
        left_passive_10 = (EditText) findViewById(R.id.left_passive_et10);
        left_passive_11 = (EditText) findViewById(R.id.left_passive_et11);
        left_passive_12 = (EditText) findViewById(R.id.left_passive_et12);
        left_passive_13 = (EditText) findViewById(R.id.left_passive_et13);
        left_passive_14 = (EditText) findViewById(R.id.left_passive_et14);
        left_passive_15 = (EditText) findViewById(R.id.left_passive_et15);
        left_passive_16 = (EditText) findViewById(R.id.left_passive_et16);
        left_passive_17 = (EditText) findViewById(R.id.left_passive_et17);
        left_passive_18 = (EditText) findViewById(R.id.left_passive_et18);
        left_passive_19 = (EditText) findViewById(R.id.left_passive_et19);
        left_passive_20 = (EditText) findViewById(R.id.left_passive_et20);
        left_passive_21 = (EditText) findViewById(R.id.left_passive_et21);
        left_passive_22 = (EditText) findViewById(R.id.left_passive_et22);
        left_passive_23 = (EditText) findViewById(R.id.left_passive_et23);
        left_passive_24 = (EditText) findViewById(R.id.left_passive_et24);
        left_passive_25 = (EditText) findViewById(R.id.left_passive_et25);
        left_passive_26 = (EditText) findViewById(R.id.left_passive_et26);
        left_passive_27 = (EditText) findViewById(R.id.left_passive_et27);
        left_passive_28 = (EditText) findViewById(R.id.left_passive_et28);
        left_passive_29 = (EditText) findViewById(R.id.left_passive_et29);
        left_passive_30 = (EditText) findViewById(R.id.left_passive_et30);
        left_passive_31 = (EditText) findViewById(R.id.left_passive_et31);
        left_passive_32 = (EditText) findViewById(R.id.left_passive_et32);
        left_passive_33 = (EditText) findViewById(R.id.left_passive_et33);
        left_passive_34 = (EditText) findViewById(R.id.left_passive_et34);
        left_passive_35 = (EditText) findViewById(R.id.left_passive_et35);
        left_passive_36 = (EditText) findViewById(R.id.left_passive_et36);
        left_passive_37 = (EditText) findViewById(R.id.left_passive_et37);
        left_passive_38 = (EditText) findViewById(R.id.left_passive_et38);
        left_passive_39 = (EditText) findViewById(R.id.left_passive_et39);
        left_passive_40 = (EditText) findViewById(R.id.left_passive_et40);
        left_passive_41 = (EditText) findViewById(R.id.left_passive_et41);
        left_passive_42 = (EditText) findViewById(R.id.left_passive_et42);
        left_passive_43 = (EditText) findViewById(R.id.left_passive_et43);
        left_passive_44 = (EditText) findViewById(R.id.left_passive_et44);
        left_passive_45 = (EditText) findViewById(R.id.left_passive_et45);
        left_passive_46 = (EditText) findViewById(R.id.left_passive_et46);
        left_passive_47 = (EditText) findViewById(R.id.left_passive_et47);
        left_passive_48 = (EditText) findViewById(R.id.left_passive_et48);
        left_passive_49 = (EditText) findViewById(R.id.left_passive_et49);
        left_passive_50 = (EditText) findViewById(R.id.left_passive_et50);
        left_passive_51 = (EditText) findViewById(R.id.left_passive_et51);
        left_passive_52 = (EditText) findViewById(R.id.left_passive_et52);
        left_passive_53 = (EditText) findViewById(R.id.left_passive_et53);
        left_passive_54 = (EditText) findViewById(R.id.left_passive_et54);
        left_passive_55 = (EditText) findViewById(R.id.left_passive_et55);
        left_passive_56 = (EditText) findViewById(R.id.left_passive_et56);
        left_passive_57 = (EditText) findViewById(R.id.left_passive_et57);
        left_passive_58 = (EditText) findViewById(R.id.left_passive_et58);
        left_passive_59 = (EditText) findViewById(R.id.left_passive_et59);
        left_passive_60 = (EditText) findViewById(R.id.left_passive_et60);
        left_passive_61 = (EditText) findViewById(R.id.left_passive_et61);
        left_passive_62 = (EditText) findViewById(R.id.left_passive_et62);
        left_passive_63 = (EditText) findViewById(R.id.left_passive_et63);
        left_passive_64 = (EditText) findViewById(R.id.left_passive_et64);
        left_passive_65 = (EditText) findViewById(R.id.left_passive_et65);
        left_passive_66 = (EditText) findViewById(R.id.left_passive_et66);
        left_passive_67 = (EditText) findViewById(R.id.left_passive_et67);
        left_passive_68 = (EditText) findViewById(R.id.left_passive_et68);
        left_passive_69 = (EditText) findViewById(R.id.left_passive_et69);
        left_passive_70 = (EditText) findViewById(R.id.left_passive_et70);


        list_left_passive.add(left_passive_1);
        list_left_passive.add(left_passive_2);
        list_left_passive.add(left_passive_3);
        list_left_passive.add(left_passive_4);
        list_left_passive.add(left_passive_5);
        list_left_passive.add(left_passive_6);
        list_left_passive.add(left_passive_7);
        list_left_passive.add(left_passive_8);
        list_left_passive.add(left_passive_9);
        list_left_passive.add(left_passive_10);
        list_left_passive.add(left_passive_11);
        list_left_passive.add(left_passive_12);
        list_left_passive.add(left_passive_13);
        list_left_passive.add(left_passive_14);
        list_left_passive.add(left_passive_15);
        list_left_passive.add(left_passive_16);
        list_left_passive.add(left_passive_17);
        list_left_passive.add(left_passive_18);
        list_left_passive.add(left_passive_19);
        list_left_passive.add(left_passive_20);
        list_left_passive.add(left_passive_21);
        list_left_passive.add(left_passive_22);
        list_left_passive.add(left_passive_23);
        list_left_passive.add(left_passive_24);
        list_left_passive.add(left_passive_25);
        list_left_passive.add(left_passive_26);
        list_left_passive.add(left_passive_27);
        list_left_passive.add(left_passive_28);
        list_left_passive.add(left_passive_29);
        list_left_passive.add(left_passive_30);
        list_left_passive.add(left_passive_31);
        list_left_passive.add(left_passive_32);
        list_left_passive.add(left_passive_33);
        list_left_passive.add(left_passive_34);
        list_left_passive.add(left_passive_35);
        list_left_passive.add(left_passive_36);
        list_left_passive.add(left_passive_37);
        list_left_passive.add(left_passive_38);
        list_left_passive.add(left_passive_39);
        list_left_passive.add(left_passive_40);
        list_left_passive.add(left_passive_41);
        list_left_passive.add(left_passive_42);
        list_left_passive.add(left_passive_43);
        list_left_passive.add(left_passive_44);
        list_left_passive.add(left_passive_45);
        list_left_passive.add(left_passive_46);
        list_left_passive.add(left_passive_47);
        list_left_passive.add(left_passive_48);
        list_left_passive.add(left_passive_49);
        list_left_passive.add(left_passive_50);
        list_left_passive.add(left_passive_51);
        list_left_passive.add(left_passive_52);
        list_left_passive.add(left_passive_53);
        list_left_passive.add(left_passive_54);
        list_left_passive.add(left_passive_55);
        list_left_passive.add(left_passive_56);
        list_left_passive.add(left_passive_57);
        list_left_passive.add(left_passive_58);
        list_left_passive.add(left_passive_59);
        list_left_passive.add(left_passive_60);
        list_left_passive.add(left_passive_61);
        list_left_passive.add(left_passive_62);
        list_left_passive.add(left_passive_63);
        list_left_passive.add(left_passive_64);
        list_left_passive.add(left_passive_65);
        list_left_passive.add(left_passive_66);
        list_left_passive.add(left_passive_67);
        list_left_passive.add(left_passive_68);
        list_left_passive.add(left_passive_69);
        list_left_passive.add(left_passive_70);

        right_init_1 = (EditText) findViewById(R.id.right_init_et1);
        right_init_2 = (EditText) findViewById(R.id.right_init_et2);
        right_init_3 = (EditText) findViewById(R.id.right_init_et3);
        right_init_4 = (EditText) findViewById(R.id.right_init_et4);
        right_init_5 = (EditText) findViewById(R.id.right_init_et5);
        right_init_6 = (EditText) findViewById(R.id.right_init_et6);
        right_init_7 = (EditText) findViewById(R.id.right_init_et7);
        right_init_8 = (EditText) findViewById(R.id.right_init_et8);
        right_init_9 = (EditText) findViewById(R.id.right_init_et9);
        right_init_10 = (EditText) findViewById(R.id.right_init_et10);
        right_init_11 = (EditText) findViewById(R.id.right_init_et11);
        right_init_12 = (EditText) findViewById(R.id.right_init_et12);
        right_init_13 = (EditText) findViewById(R.id.right_init_et13);
        right_init_14 = (EditText) findViewById(R.id.right_init_et14);
        right_init_15 = (EditText) findViewById(R.id.right_init_et15);
        right_init_16 = (EditText) findViewById(R.id.right_init_et16);
        right_init_17 = (EditText) findViewById(R.id.right_init_et17);
        right_init_18 = (EditText) findViewById(R.id.right_init_et18);
        right_init_19 = (EditText) findViewById(R.id.right_init_et19);
        right_init_20 = (EditText) findViewById(R.id.right_init_et20);
        right_init_21 = (EditText) findViewById(R.id.right_init_et21);
        right_init_22 = (EditText) findViewById(R.id.right_init_et22);
        right_init_23 = (EditText) findViewById(R.id.right_init_et23);
        right_init_24 = (EditText) findViewById(R.id.right_init_et24);
        right_init_25 = (EditText) findViewById(R.id.right_init_et25);
        right_init_26 = (EditText) findViewById(R.id.right_init_et26);
        right_init_27 = (EditText) findViewById(R.id.right_init_et27);
        right_init_28 = (EditText) findViewById(R.id.right_init_et28);
        right_init_29 = (EditText) findViewById(R.id.right_init_et29);
        right_init_30 = (EditText) findViewById(R.id.right_init_et30);
        right_init_31 = (EditText) findViewById(R.id.right_init_et31);
        right_init_32 = (EditText) findViewById(R.id.right_init_et32);
        right_init_33 = (EditText) findViewById(R.id.right_init_et33);
        right_init_34 = (EditText) findViewById(R.id.right_init_et34);
        right_init_35 = (EditText) findViewById(R.id.right_init_et35);
        right_init_36 = (EditText) findViewById(R.id.right_init_et36);
        right_init_37 = (EditText) findViewById(R.id.right_init_et37);
        right_init_38 = (EditText) findViewById(R.id.right_init_et38);
        right_init_39 = (EditText) findViewById(R.id.right_init_et39);
        right_init_40 = (EditText) findViewById(R.id.right_init_et40);
        right_init_41 = (EditText) findViewById(R.id.right_init_et41);
        right_init_42 = (EditText) findViewById(R.id.right_init_et42);
        right_init_43 = (EditText) findViewById(R.id.right_init_et43);
        right_init_44 = (EditText) findViewById(R.id.right_init_et44);
        right_init_45 = (EditText) findViewById(R.id.right_init_et45);
        right_init_46 = (EditText) findViewById(R.id.right_init_et46);
        right_init_47 = (EditText) findViewById(R.id.right_init_et47);
        right_init_48 = (EditText) findViewById(R.id.right_init_et48);
        right_init_49 = (EditText) findViewById(R.id.right_init_et49);
        right_init_50 = (EditText) findViewById(R.id.right_init_et50);
        right_init_51 = (EditText) findViewById(R.id.right_init_et51);
        right_init_52 = (EditText) findViewById(R.id.right_init_et52);
        right_init_53 = (EditText) findViewById(R.id.right_init_et53);
        right_init_54 = (EditText) findViewById(R.id.right_init_et54);
        right_init_55 = (EditText) findViewById(R.id.right_init_et55);
        right_init_56 = (EditText) findViewById(R.id.right_init_et56);
        right_init_57 = (EditText) findViewById(R.id.right_init_et57);
        right_init_58 = (EditText) findViewById(R.id.right_init_et58);
        right_init_59 = (EditText) findViewById(R.id.right_init_et59);
        right_init_60 = (EditText) findViewById(R.id.right_init_et60);
        right_init_61 = (EditText) findViewById(R.id.right_init_et61);
        right_init_62 = (EditText) findViewById(R.id.right_init_et62);
        right_init_63 = (EditText) findViewById(R.id.right_init_et63);
        right_init_64 = (EditText) findViewById(R.id.right_init_et64);
        right_init_65 = (EditText) findViewById(R.id.right_init_et65);
        right_init_66 = (EditText) findViewById(R.id.right_init_et66);
        right_init_67 = (EditText) findViewById(R.id.right_init_et67);
        right_init_68 = (EditText) findViewById(R.id.right_init_et68);
        right_init_69 = (EditText) findViewById(R.id.right_init_et69);
        right_init_70 = (EditText) findViewById(R.id.right_init_et70);


        list_right_init.add(right_init_1);
        list_right_init.add(right_init_2);
        list_right_init.add(right_init_3);
        list_right_init.add(right_init_4);
        list_right_init.add(right_init_5);
        list_right_init.add(right_init_6);
        list_right_init.add(right_init_7);
        list_right_init.add(right_init_8);
        list_right_init.add(right_init_9);
        list_right_init.add(right_init_10);
        list_right_init.add(right_init_11);
        list_right_init.add(right_init_12);
        list_right_init.add(right_init_13);
        list_right_init.add(right_init_14);
        list_right_init.add(right_init_15);
        list_right_init.add(right_init_16);
        list_right_init.add(right_init_17);
        list_right_init.add(right_init_18);
        list_right_init.add(right_init_19);
        list_right_init.add(right_init_20);
        list_right_init.add(right_init_21);
        list_right_init.add(right_init_22);
        list_right_init.add(right_init_23);
        list_right_init.add(right_init_24);
        list_right_init.add(right_init_25);
        list_right_init.add(right_init_26);
        list_right_init.add(right_init_27);
        list_right_init.add(right_init_28);
        list_right_init.add(right_init_29);
        list_right_init.add(right_init_30);
        list_right_init.add(right_init_31);
        list_right_init.add(right_init_32);
        list_right_init.add(right_init_33);
        list_right_init.add(right_init_34);
        list_right_init.add(right_init_35);
        list_right_init.add(right_init_36);
        list_right_init.add(right_init_37);
        list_right_init.add(right_init_38);
        list_right_init.add(right_init_39);
        list_right_init.add(right_init_40);
        list_right_init.add(right_init_41);
        list_right_init.add(right_init_42);
        list_right_init.add(right_init_43);
        list_right_init.add(right_init_44);
        list_right_init.add(right_init_45);
        list_right_init.add(right_init_46);
        list_right_init.add(right_init_47);
        list_right_init.add(right_init_48);
        list_right_init.add(right_init_49);
        list_right_init.add(right_init_50);
        list_right_init.add(right_init_51);
        list_right_init.add(right_init_52);
        list_right_init.add(right_init_53);
        list_right_init.add(right_init_54);
        list_right_init.add(right_init_55);
        list_right_init.add(right_init_56);
        list_right_init.add(right_init_57);
        list_right_init.add(right_init_58);
        list_right_init.add(right_init_59);
        list_right_init.add(right_init_60);
        list_right_init.add(right_init_61);
        list_right_init.add(right_init_62);
        list_right_init.add(right_init_63);
        list_right_init.add(right_init_64);
        list_right_init.add(right_init_65);
        list_right_init.add(right_init_66);
        list_right_init.add(right_init_67);
        list_right_init.add(right_init_68);
        list_right_init.add(right_init_69);
        list_right_init.add(right_init_70);

        right_passive_1 = (EditText) findViewById(R.id.right_passive_et1);
        right_passive_2 = (EditText) findViewById(R.id.right_passive_et2);
        right_passive_3 = (EditText) findViewById(R.id.right_passive_et3);
        right_passive_4 = (EditText) findViewById(R.id.right_passive_et4);
        right_passive_5 = (EditText) findViewById(R.id.right_passive_et5);
        right_passive_6 = (EditText) findViewById(R.id.right_passive_et6);
        right_passive_7 = (EditText) findViewById(R.id.right_passive_et7);
        right_passive_8 = (EditText) findViewById(R.id.right_passive_et8);
        right_passive_9 = (EditText) findViewById(R.id.right_passive_et9);
        right_passive_10 = (EditText) findViewById(R.id.right_passive_et10);
        right_passive_11 = (EditText) findViewById(R.id.right_passive_et11);
        right_passive_12 = (EditText) findViewById(R.id.right_passive_et12);
        right_passive_13 = (EditText) findViewById(R.id.right_passive_et13);
        right_passive_14 = (EditText) findViewById(R.id.right_passive_et14);
        right_passive_15 = (EditText) findViewById(R.id.right_passive_et15);
        right_passive_16 = (EditText) findViewById(R.id.right_passive_et16);
        right_passive_17 = (EditText) findViewById(R.id.right_passive_et17);
        right_passive_18 = (EditText) findViewById(R.id.right_passive_et18);
        right_passive_19 = (EditText) findViewById(R.id.right_passive_et19);
        right_passive_20 = (EditText) findViewById(R.id.right_passive_et20);
        right_passive_21 = (EditText) findViewById(R.id.right_passive_et21);
        right_passive_22 = (EditText) findViewById(R.id.right_passive_et22);
        right_passive_23 = (EditText) findViewById(R.id.right_passive_et23);
        right_passive_24 = (EditText) findViewById(R.id.right_passive_et24);
        right_passive_25 = (EditText) findViewById(R.id.right_passive_et25);
        right_passive_26 = (EditText) findViewById(R.id.right_passive_et26);
        right_passive_27 = (EditText) findViewById(R.id.right_passive_et27);
        right_passive_28 = (EditText) findViewById(R.id.right_passive_et28);
        right_passive_29 = (EditText) findViewById(R.id.right_passive_et29);
        right_passive_30 = (EditText) findViewById(R.id.right_passive_et30);
        right_passive_31 = (EditText) findViewById(R.id.right_passive_et31);
        right_passive_32 = (EditText) findViewById(R.id.right_passive_et32);
        right_passive_33 = (EditText) findViewById(R.id.right_passive_et33);
        right_passive_34 = (EditText) findViewById(R.id.right_passive_et34);
        right_passive_35 = (EditText) findViewById(R.id.right_passive_et35);
        right_passive_36 = (EditText) findViewById(R.id.right_passive_et36);
        right_passive_37 = (EditText) findViewById(R.id.right_passive_et37);
        right_passive_38 = (EditText) findViewById(R.id.right_passive_et38);
        right_passive_39 = (EditText) findViewById(R.id.right_passive_et39);
        right_passive_40 = (EditText) findViewById(R.id.right_passive_et40);
        right_passive_41 = (EditText) findViewById(R.id.right_passive_et41);
        right_passive_42 = (EditText) findViewById(R.id.right_passive_et42);
        right_passive_43 = (EditText) findViewById(R.id.right_passive_et43);
        right_passive_44 = (EditText) findViewById(R.id.right_passive_et44);
        right_passive_45 = (EditText) findViewById(R.id.right_passive_et45);
        right_passive_46 = (EditText) findViewById(R.id.right_passive_et46);
        right_passive_47 = (EditText) findViewById(R.id.right_passive_et47);
        right_passive_48 = (EditText) findViewById(R.id.right_passive_et48);
        right_passive_49 = (EditText) findViewById(R.id.right_passive_et49);
        right_passive_50 = (EditText) findViewById(R.id.right_passive_et50);
        right_passive_51 = (EditText) findViewById(R.id.right_passive_et51);
        right_passive_52 = (EditText) findViewById(R.id.right_passive_et52);
        right_passive_53 = (EditText) findViewById(R.id.right_passive_et53);
        right_passive_54 = (EditText) findViewById(R.id.right_passive_et54);
        right_passive_55 = (EditText) findViewById(R.id.right_passive_et55);
        right_passive_56 = (EditText) findViewById(R.id.right_passive_et56);
        right_passive_57 = (EditText) findViewById(R.id.right_passive_et57);
        right_passive_58 = (EditText) findViewById(R.id.right_passive_et58);
        right_passive_59 = (EditText) findViewById(R.id.right_passive_et59);
        right_passive_60 = (EditText) findViewById(R.id.right_passive_et60);
        right_passive_61 = (EditText) findViewById(R.id.right_passive_et61);
        right_passive_62 = (EditText) findViewById(R.id.right_passive_et62);
        right_passive_63 = (EditText) findViewById(R.id.right_passive_et63);
        right_passive_64 = (EditText) findViewById(R.id.right_passive_et64);
        right_passive_65 = (EditText) findViewById(R.id.right_passive_et65);
        right_passive_66 = (EditText) findViewById(R.id.right_passive_et66);
        right_passive_67 = (EditText) findViewById(R.id.right_passive_et67);
        right_passive_68 = (EditText) findViewById(R.id.right_passive_et68);
        right_passive_69 = (EditText) findViewById(R.id.right_passive_et69);
        right_passive_70 = (EditText) findViewById(R.id.right_passive_et70);


        list_right_passive.add(right_passive_1);
        list_right_passive.add(right_passive_2);
        list_right_passive.add(right_passive_3);
        list_right_passive.add(right_passive_4);
        list_right_passive.add(right_passive_5);
        list_right_passive.add(right_passive_6);
        list_right_passive.add(right_passive_7);
        list_right_passive.add(right_passive_8);
        list_right_passive.add(right_passive_9);
        list_right_passive.add(right_passive_10);
        list_right_passive.add(right_passive_11);
        list_right_passive.add(right_passive_12);
        list_right_passive.add(right_passive_13);
        list_right_passive.add(right_passive_14);
        list_right_passive.add(right_passive_15);
        list_right_passive.add(right_passive_16);
        list_right_passive.add(right_passive_17);
        list_right_passive.add(right_passive_18);
        list_right_passive.add(right_passive_19);
        list_right_passive.add(right_passive_20);
        list_right_passive.add(right_passive_21);
        list_right_passive.add(right_passive_22);
        list_right_passive.add(right_passive_23);
        list_right_passive.add(right_passive_24);
        list_right_passive.add(right_passive_25);
        list_right_passive.add(right_passive_26);
        list_right_passive.add(right_passive_27);
        list_right_passive.add(right_passive_28);
        list_right_passive.add(right_passive_29);
        list_right_passive.add(right_passive_30);
        list_right_passive.add(right_passive_31);
        list_right_passive.add(right_passive_32);
        list_right_passive.add(right_passive_33);
        list_right_passive.add(right_passive_34);
        list_right_passive.add(right_passive_35);
        list_right_passive.add(right_passive_36);
        list_right_passive.add(right_passive_37);
        list_right_passive.add(right_passive_38);
        list_right_passive.add(right_passive_39);
        list_right_passive.add(right_passive_40);
        list_right_passive.add(right_passive_41);
        list_right_passive.add(right_passive_42);
        list_right_passive.add(right_passive_43);
        list_right_passive.add(right_passive_44);
        list_right_passive.add(right_passive_45);
        list_right_passive.add(right_passive_46);
        list_right_passive.add(right_passive_47);
        list_right_passive.add(right_passive_48);
        list_right_passive.add(right_passive_49);
        list_right_passive.add(right_passive_50);
        list_right_passive.add(right_passive_51);
        list_right_passive.add(right_passive_52);
        list_right_passive.add(right_passive_53);
        list_right_passive.add(right_passive_54);
        list_right_passive.add(right_passive_55);
        list_right_passive.add(right_passive_56);
        list_right_passive.add(right_passive_57);
        list_right_passive.add(right_passive_58);
        list_right_passive.add(right_passive_59);
        list_right_passive.add(right_passive_60);
        list_right_passive.add(right_passive_61);
        list_right_passive.add(right_passive_62);
        list_right_passive.add(right_passive_63);
        list_right_passive.add(right_passive_64);
        list_right_passive.add(right_passive_65);
        list_right_passive.add(right_passive_66);
        list_right_passive.add(right_passive_67);
        list_right_passive.add(right_passive_68);
        list_right_passive.add(right_passive_69);
        list_right_passive.add(right_passive_70);

        if (isAdd == false) {
            if (info != null) {
                setData();
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
            for (int i = 0; i < info.getLeft_init_score().split(",").length; i++) {
                list_left_init.get(i).setText(info.getLeft_init_score().split(",")[i]);
            }
            for (int i = 0; i < info.getLeft_passive_score().split(",").length; i++) {
                list_left_passive.get(i).setText(info.getLeft_passive_score().split(",")[i]);
            }
            for (int i = 0; i < info.getRight_init_score().split(",").length; i++) {
                list_right_init.get(i).setText(info.getRight_init_score().split(",")[i]);
            }
            for (int i = 0; i < info.getRight_passive_score().split(",").length; i++) {
                list_right_passive.get(i).setText(info.getRight_passive_score().split(",")[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tittle_back:
                this.finish();
                break;
            case R.id.patient_save:
                if (dataIsEmpty()) {

                } else {
                    progressBar.setVisibility(View.VISIBLE);

                    param = "patient_info_id=" + patient_info_id
                            + "&user_auth_id=" + user_auth_id
                            + "&menu_id=" + 92
                            + "&evaluation[evaluation_time]=" + date.getText()
                            + "&evaluation[evaluation_time_note]=" + instructions_map.get(spinner_value)
                            + "&c=" + 70

                            + "&left_zd0=" + left_init_1.getText()
                            + "&left_zd1=" + left_init_2.getText()
                            + "&left_zd2=" + left_init_3.getText()
                            + "&left_zd3=" + left_init_4.getText()
                            + "&left_zd4=" + left_init_5.getText()
                            + "&left_zd5=" + left_init_6.getText()
                            + "&left_zd6=" + left_init_7.getText()
                            + "&left_zd7=" + left_init_8.getText()
                            + "&left_zd8=" + left_init_9.getText()
                            + "&left_zd9=" + left_init_10.getText()
                            + "&left_zd10=" + left_init_11.getText()
                            + "&left_zd11=" + left_init_12.getText()
                            + "&left_zd12=" + left_init_13.getText()
                            + "&left_zd13=" + left_init_14.getText()
                            + "&left_zd14=" + left_init_15.getText()
                            + "&left_zd15=" + left_init_16.getText()
                            + "&left_zd16=" + left_init_17.getText()
                            + "&left_zd17=" + left_init_18.getText()
                            + "&left_zd18=" + left_init_19.getText()
                            + "&left_zd19=" + left_init_20.getText()
                            + "&left_zd20=" + left_init_21.getText()
                            + "&left_zd21=" + left_init_22.getText()
                            + "&left_zd22=" + left_init_23.getText()
                            + "&left_zd23=" + left_init_24.getText()
                            + "&left_zd24=" + left_init_25.getText()
                            + "&left_zd25=" + left_init_26.getText()
                            + "&left_zd26=" + left_init_27.getText()
                            + "&left_zd27=" + left_init_28.getText()
                            + "&left_zd28=" + left_init_29.getText()
                            + "&left_zd29=" + left_init_30.getText()
                            + "&left_zd30=" + left_init_31.getText()
                            + "&left_zd31=" + left_init_32.getText()
                            + "&left_zd32=" + left_init_33.getText()
                            + "&left_zd33=" + left_init_34.getText()
                            + "&left_zd34=" + left_init_35.getText()
                            + "&left_zd35=" + left_init_36.getText()
                            + "&left_zd36=" + left_init_37.getText()
                            + "&left_zd37=" + left_init_38.getText()
                            + "&left_zd38=" + left_init_39.getText()
                            + "&left_zd39=" + left_init_40.getText()
                            + "&left_zd40=" + left_init_41.getText()
                            + "&left_zd41=" + left_init_42.getText()
                            + "&left_zd42=" + left_init_43.getText()
                            + "&left_zd43=" + left_init_44.getText()
                            + "&left_zd44=" + left_init_45.getText()
                            + "&left_zd45=" + left_init_46.getText()
                            + "&left_zd46=" + left_init_47.getText()
                            + "&left_zd47=" + left_init_48.getText()
                            + "&left_zd48=" + left_init_49.getText()
                            + "&left_zd49=" + left_init_50.getText()
                            + "&left_zd50=" + left_init_51.getText()
                            + "&left_zd51=" + left_init_52.getText()
                            + "&left_zd52=" + left_init_53.getText()
                            + "&left_zd53=" + left_init_54.getText()
                            + "&left_zd54=" + left_init_55.getText()
                            + "&left_zd55=" + left_init_56.getText()
                            + "&left_zd56=" + left_init_57.getText()
                            + "&left_zd57=" + left_init_58.getText()
                            + "&left_zd58=" + left_init_59.getText()
                            + "&left_zd59=" + left_init_60.getText()
                            + "&left_zd60=" + left_init_61.getText()
                            + "&left_zd61=" + left_init_62.getText()
                            + "&left_zd62=" + left_init_63.getText()
                            + "&left_zd63=" + left_init_64.getText()
                            + "&left_zd64=" + left_init_65.getText()
                            + "&left_zd65=" + left_init_66.getText()
                            + "&left_zd66=" + left_init_67.getText()
                            + "&left_zd67=" + left_init_68.getText()
                            + "&left_zd68=" + left_init_69.getText()
                            + "&left_zd69=" + left_init_70.getText()

                            + "&left_bd0=" + left_passive_1.getText()
                            + "&left_bd1=" + left_passive_2.getText()
                            + "&left_bd2=" + left_passive_3.getText()
                            + "&left_bd3=" + left_passive_4.getText()
                            + "&left_bd4=" + left_passive_5.getText()
                            + "&left_bd5=" + left_passive_6.getText()
                            + "&left_bd6=" + left_passive_7.getText()
                            + "&left_bd7=" + left_passive_8.getText()
                            + "&left_bd8=" + left_passive_9.getText()
                            + "&left_bd9=" + left_passive_10.getText()
                            + "&left_bd10=" + left_passive_11.getText()
                            + "&left_bd11=" + left_passive_12.getText()
                            + "&left_bd12=" + left_passive_13.getText()
                            + "&left_bd13=" + left_passive_14.getText()
                            + "&left_bd14=" + left_passive_15.getText()
                            + "&left_bd15=" + left_passive_16.getText()
                            + "&left_bd16=" + left_passive_17.getText()
                            + "&left_bd17=" + left_passive_18.getText()
                            + "&left_bd18=" + left_passive_19.getText()
                            + "&left_bd19=" + left_passive_20.getText()
                            + "&left_bd20=" + left_passive_21.getText()
                            + "&left_bd21=" + left_passive_22.getText()
                            + "&left_bd22=" + left_passive_23.getText()
                            + "&left_bd23=" + left_passive_24.getText()
                            + "&left_bd24=" + left_passive_25.getText()
                            + "&left_bd25=" + left_passive_26.getText()
                            + "&left_bd26=" + left_passive_27.getText()
                            + "&left_bd27=" + left_passive_28.getText()
                            + "&left_bd28=" + left_passive_29.getText()
                            + "&left_bd29=" + left_passive_30.getText()
                            + "&left_bd30=" + left_passive_31.getText()
                            + "&left_bd31=" + left_passive_32.getText()
                            + "&left_bd32=" + left_passive_33.getText()
                            + "&left_bd33=" + left_passive_34.getText()
                            + "&left_bd34=" + left_passive_35.getText()
                            + "&left_bd35=" + left_passive_36.getText()
                            + "&left_bd36=" + left_passive_37.getText()
                            + "&left_bd37=" + left_passive_38.getText()
                            + "&left_bd38=" + left_passive_39.getText()
                            + "&left_bd39=" + left_passive_40.getText()
                            + "&left_bd40=" + left_passive_41.getText()
                            + "&left_bd41=" + left_passive_42.getText()
                            + "&left_bd42=" + left_passive_43.getText()
                            + "&left_bd43=" + left_passive_44.getText()
                            + "&left_bd44=" + left_passive_45.getText()
                            + "&left_bd45=" + left_passive_46.getText()
                            + "&left_bd46=" + left_passive_47.getText()
                            + "&left_bd47=" + left_passive_48.getText()
                            + "&left_bd48=" + left_passive_49.getText()
                            + "&left_bd49=" + left_passive_50.getText()
                            + "&left_bd50=" + left_passive_51.getText()
                            + "&left_bd51=" + left_passive_52.getText()
                            + "&left_bd52=" + left_passive_53.getText()
                            + "&left_bd53=" + left_passive_54.getText()
                            + "&left_bd54=" + left_passive_55.getText()
                            + "&left_bd55=" + left_passive_56.getText()
                            + "&left_bd56=" + left_passive_57.getText()
                            + "&left_bd57=" + left_passive_58.getText()
                            + "&left_bd58=" + left_passive_59.getText()
                            + "&left_bd59=" + left_passive_60.getText()
                            + "&left_bd60=" + left_passive_61.getText()
                            + "&left_bd61=" + left_passive_62.getText()
                            + "&left_bd62=" + left_passive_63.getText()
                            + "&left_bd63=" + left_passive_64.getText()
                            + "&left_bd64=" + left_passive_65.getText()
                            + "&left_bd65=" + left_passive_66.getText()
                            + "&left_bd66=" + left_passive_67.getText()
                            + "&left_bd67=" + left_passive_68.getText()
                            + "&left_bd68=" + left_passive_69.getText()
                            + "&left_bd69=" + left_passive_70.getText()


                            + "&right_zd0=" + right_init_1.getText()
                            + "&right_zd1=" + right_init_2.getText()
                            + "&right_zd2=" + right_init_3.getText()
                            + "&right_zd3=" + right_init_4.getText()
                            + "&right_zd4=" + right_init_5.getText()
                            + "&right_zd5=" + right_init_6.getText()
                            + "&right_zd6=" + right_init_7.getText()
                            + "&right_zd7=" + right_init_8.getText()
                            + "&right_zd8=" + right_init_9.getText()
                            + "&right_zd9=" + right_init_10.getText()
                            + "&right_zd10=" + right_init_11.getText()
                            + "&right_zd11=" + right_init_12.getText()
                            + "&right_zd12=" + right_init_13.getText()
                            + "&right_zd13=" + right_init_14.getText()
                            + "&right_zd14=" + right_init_15.getText()
                            + "&right_zd15=" + right_init_16.getText()
                            + "&right_zd16=" + right_init_17.getText()
                            + "&right_zd17=" + right_init_18.getText()
                            + "&right_zd18=" + right_init_19.getText()
                            + "&right_zd19=" + right_init_20.getText()
                            + "&right_zd20=" + right_init_21.getText()
                            + "&right_zd21=" + right_init_22.getText()
                            + "&right_zd22=" + right_init_23.getText()
                            + "&right_zd23=" + right_init_24.getText()
                            + "&right_zd24=" + right_init_25.getText()
                            + "&right_zd25=" + right_init_26.getText()
                            + "&right_zd26=" + right_init_27.getText()
                            + "&right_zd27=" + right_init_28.getText()
                            + "&right_zd28=" + right_init_29.getText()
                            + "&right_zd29=" + right_init_30.getText()
                            + "&right_zd30=" + right_init_31.getText()
                            + "&right_zd31=" + right_init_32.getText()
                            + "&right_zd32=" + right_init_33.getText()
                            + "&right_zd33=" + right_init_34.getText()
                            + "&right_zd34=" + right_init_35.getText()
                            + "&right_zd35=" + right_init_36.getText()
                            + "&right_zd36=" + right_init_37.getText()
                            + "&right_zd37=" + right_init_38.getText()
                            + "&right_zd38=" + right_init_39.getText()
                            + "&right_zd39=" + right_init_40.getText()
                            + "&right_zd40=" + right_init_41.getText()
                            + "&right_zd41=" + right_init_42.getText()
                            + "&right_zd42=" + right_init_43.getText()
                            + "&right_zd43=" + right_init_44.getText()
                            + "&right_zd44=" + right_init_45.getText()
                            + "&right_zd45=" + right_init_46.getText()
                            + "&right_zd46=" + right_init_47.getText()
                            + "&right_zd47=" + right_init_48.getText()
                            + "&right_zd48=" + right_init_49.getText()
                            + "&right_zd49=" + right_init_50.getText()
                            + "&right_zd50=" + right_init_51.getText()
                            + "&right_zd51=" + right_init_52.getText()
                            + "&right_zd52=" + right_init_53.getText()
                            + "&right_zd53=" + right_init_54.getText()
                            + "&right_zd54=" + right_init_55.getText()
                            + "&right_zd55=" + right_init_56.getText()
                            + "&right_zd56=" + right_init_57.getText()
                            + "&right_zd57=" + right_init_58.getText()
                            + "&right_zd58=" + right_init_59.getText()
                            + "&right_zd59=" + right_init_60.getText()
                            + "&right_zd60=" + right_init_61.getText()
                            + "&right_zd61=" + right_init_62.getText()
                            + "&right_zd62=" + right_init_63.getText()
                            + "&right_zd63=" + right_init_64.getText()
                            + "&right_zd64=" + right_init_65.getText()
                            + "&right_zd65=" + right_init_66.getText()
                            + "&right_zd66=" + right_init_67.getText()
                            + "&right_zd67=" + right_init_68.getText()
                            + "&right_zd68=" + right_init_69.getText()
                            + "&right_zd69=" + right_init_70.getText()


                            + "&right_bd0=" + right_passive_1.getText()
                            + "&right_bd1=" + right_passive_2.getText()
                            + "&right_bd2=" + right_passive_3.getText()
                            + "&right_bd3=" + right_passive_4.getText()
                            + "&right_bd4=" + right_passive_5.getText()
                            + "&right_bd5=" + right_passive_6.getText()
                            + "&right_bd6=" + right_passive_7.getText()
                            + "&right_bd7=" + right_passive_8.getText()
                            + "&right_bd8=" + right_passive_9.getText()
                            + "&right_bd9=" + right_passive_10.getText()
                            + "&right_bd10=" + right_passive_11.getText()
                            + "&right_bd11=" + right_passive_12.getText()
                            + "&right_bd12=" + right_passive_13.getText()
                            + "&right_bd13=" + right_passive_14.getText()
                            + "&right_bd14=" + right_passive_15.getText()
                            + "&right_bd15=" + right_passive_16.getText()
                            + "&right_bd16=" + right_passive_17.getText()
                            + "&right_bd17=" + right_passive_18.getText()
                            + "&right_bd18=" + right_passive_19.getText()
                            + "&right_bd19=" + right_passive_20.getText()
                            + "&right_bd20=" + right_passive_21.getText()
                            + "&right_bd21=" + right_passive_22.getText()
                            + "&right_bd22=" + right_passive_23.getText()
                            + "&right_bd23=" + right_passive_24.getText()
                            + "&right_bd24=" + right_passive_25.getText()
                            + "&right_bd25=" + right_passive_26.getText()
                            + "&right_bd26=" + right_passive_27.getText()
                            + "&right_bd27=" + right_passive_28.getText()
                            + "&right_bd28=" + right_passive_29.getText()
                            + "&right_bd29=" + right_passive_30.getText()
                            + "&right_bd30=" + right_passive_31.getText()
                            + "&right_bd31=" + right_passive_32.getText()
                            + "&right_bd32=" + right_passive_33.getText()
                            + "&right_bd33=" + right_passive_34.getText()
                            + "&right_bd34=" + right_passive_35.getText()
                            + "&right_bd35=" + right_passive_36.getText()
                            + "&right_bd36=" + right_passive_37.getText()
                            + "&right_bd37=" + right_passive_38.getText()
                            + "&right_bd38=" + right_passive_39.getText()
                            + "&right_bd39=" + right_passive_40.getText()
                            + "&right_bd40=" + right_passive_41.getText()
                            + "&right_bd41=" + right_passive_42.getText()
                            + "&right_bd42=" + right_passive_43.getText()
                            + "&right_bd43=" + right_passive_44.getText()
                            + "&right_bd44=" + right_passive_45.getText()
                            + "&right_bd45=" + right_passive_46.getText()
                            + "&right_bd46=" + right_passive_47.getText()
                            + "&right_bd47=" + right_passive_48.getText()
                            + "&right_bd48=" + right_passive_49.getText()
                            + "&right_bd49=" + right_passive_50.getText()
                            + "&right_bd50=" + right_passive_51.getText()
                            + "&right_bd51=" + right_passive_52.getText()
                            + "&right_bd52=" + right_passive_53.getText()
                            + "&right_bd53=" + right_passive_54.getText()
                            + "&right_bd54=" + right_passive_55.getText()
                            + "&right_bd55=" + right_passive_56.getText()
                            + "&right_bd56=" + right_passive_57.getText()
                            + "&right_bd57=" + right_passive_58.getText()
                            + "&right_bd58=" + right_passive_59.getText()
                            + "&right_bd59=" + right_passive_60.getText()
                            + "&right_bd60=" + right_passive_61.getText()
                            + "&right_bd61=" + right_passive_62.getText()
                            + "&right_bd62=" + right_passive_63.getText()
                            + "&right_bd63=" + right_passive_64.getText()
                            + "&right_bd64=" + right_passive_65.getText()
                            + "&right_bd65=" + right_passive_66.getText()
                            + "&right_bd66=" + right_passive_67.getText()
                            + "&right_bd67=" + right_passive_68.getText()
                            + "&right_bd68=" + right_passive_69.getText()
                            + "&right_bd69=" + right_passive_70.getText()


                            + "&token=" + NetUrlAddress.token;


                    new MyThread().start();
                }
                break;
            case R.id.date:
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
                    create_code = TableRomInfo.doPost(NetUrlAddress.Rom_create_url, param);
                } else {
                    String url = NetUrlAddress.Rom_update_url.replaceAll("aaa", info.getRecord_id() + "");
                    update_code = TableRomInfo.doPost(url, param);
                }
                if (create_code == 200) {
                    TableRomInfoActivity.this.finish();
                    ShowToast.Short(getString(R.string.msg_save_success));
                } else if (update_code == 200) {
                    TableRomInfoActivity.this.finish();
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
     * 判断是否有没有完整填写数据
     *
     * @return
     */
    boolean dataIsEmpty() {
        if (TextUtils.isEmpty(date.getText())) {
            ShowToast.Short(getString(R.string.msg_date));
            return true;
        } else {
            return false;
        }
    }
}
