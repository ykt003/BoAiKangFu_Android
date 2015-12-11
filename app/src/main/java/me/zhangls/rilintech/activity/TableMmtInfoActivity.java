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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.adapter.SpinnerBaseAdapter;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.TableMmtInfo;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;

/**
 * Created by YANG on 15/9/6.
 */
public class TableMmtInfoActivity extends BaseActivity implements View.OnClickListener {

    //新建响应码
    private int create_code = 0;
    //更新响应码
    private int update_code = 0;

    //用来接受拿到数据后发来的消息
    private static Handler handler;
    //缓冲球
    private GoogleProgressBar google_progress;
    /**
     * 记录表的id
     */
    private int record_id;
    /**
     * 拼接参数
     */
    private String param;
    /**
     * 根据当前选中的值判断出第几次评定
     */
    private int instructions_index;
    /**
     * 评定说明当前选中值
     */
    private String instructions_value;
    /**
     * 传过来得时间
     */
    private String date_intent;
    /**
     * 当前记录表的左侧分数
     */
    private ArrayList<String> list_lefts = new ArrayList<>();
    /**
     * 当前记录表的右侧分数
     */
    private ArrayList<String> list_rights = new ArrayList<>();
    /**
     * 当前编辑的记录表
     */
    private String instruction;
    /**
     * 返回的服务器已经存在的记录表
     */
    private ArrayList<String> list_instructions_cloud = new ArrayList<>();
    /**
     * 是否新建
     */
    private boolean isAdd;
    /**
     * 时间选择器
     */
    private TimePopupWindow pwTime;
    /**
     * 保存键
     */
    private ImageView save;
    /**
     * 返回键
     */
    private ImageView back;
    /**
     * spinner的值
     */
    private ArrayList<String> list = new ArrayList<>();
    /**
     * 评定说明的值
     */
    private ArrayList<String> list_instructions = new ArrayList<>();
    /**
     * spinner加载器
     */
    private SpinnerBaseAdapter adapter;
    /**
     * 评定说明
     */
    private Spinner instructions;
    /**
     * 评定时间
     */
    private TextView date;
    /**
     * 左侧得分
     */
    private Spinner mmt_left_1, mmt_left_2, mmt_left_3, mmt_left_4, mmt_left_5, mmt_left_6, mmt_left_7, mmt_left_8,
            mmt_left_9, mmt_left_10, mmt_left_11, mmt_left_12, mmt_left_13, mmt_left_14, mmt_left_15,
            mmt_left_16, mmt_left_17, mmt_left_18, mmt_left_19, mmt_left_20, mmt_left_21, mmt_left_22,
            mmt_left_23, mmt_left_24, mmt_left_25, mmt_left_26, mmt_left_27, mmt_left_28, mmt_left_29,
            mmt_left_30, mmt_left_31, mmt_left_32, mmt_left_33, mmt_left_34, mmt_left_35, mmt_left_36,
            mmt_left_37, mmt_left_38, mmt_left_39, mmt_left_40, mmt_left_41, mmt_left_42, mmt_left_43,
            mmt_left_44, mmt_left_45, mmt_left_46, mmt_left_47, mmt_left_48, mmt_left_49, mmt_left_50,
            mmt_left_51, mmt_left_52, mmt_left_53, mmt_left_54, mmt_left_55, mmt_left_56;

    /**
     * 左侧得分
     */
    private Spinner mmt_right_1, mmt_right_2, mmt_right_3, mmt_right_4, mmt_right_5, mmt_right_6, mmt_right_7, mmt_right_8,
            mmt_right_9, mmt_right_10, mmt_right_11, mmt_right_12, mmt_right_13, mmt_right_14, mmt_right_15,
            mmt_right_16, mmt_right_17, mmt_right_18, mmt_right_19, mmt_right_20, mmt_right_21, mmt_right_22,
            mmt_right_23, mmt_right_24, mmt_right_25, mmt_right_26, mmt_right_27, mmt_right_28, mmt_right_29,
            mmt_right_30, mmt_right_31, mmt_right_32, mmt_right_33, mmt_right_34, mmt_right_35, mmt_right_36,
            mmt_right_37, mmt_right_38, mmt_right_39, mmt_right_40, mmt_right_41, mmt_right_42, mmt_right_43,
            mmt_right_44, mmt_right_45, mmt_right_46, mmt_right_47, mmt_right_48, mmt_right_49, mmt_right_50,
            mmt_right_51, mmt_right_52, mmt_right_53, mmt_right_54, mmt_right_55, mmt_right_56;

    /**
     * 左侧分值
     */
    private String mmt_left_value1, mmt_left_value2, mmt_left_value3, mmt_left_value4, mmt_left_value5, mmt_left_value6,
            mmt_left_value7, mmt_left_value8, mmt_left_value9, mmt_left_value10, mmt_left_value11, mmt_left_value12,
            mmt_left_value13, mmt_left_value14, mmt_left_value15, mmt_left_value16, mmt_left_value22, mmt_left_value17,
            mmt_left_value18, mmt_left_value19, mmt_left_value20, mmt_left_value21, mmt_left_value23, mmt_left_value24,
            mmt_left_value25, mmt_left_value26, mmt_left_value27, mmt_left_value28, mmt_left_value29, mmt_left_value30,
            mmt_left_value31, mmt_left_value32, mmt_left_value33, mmt_left_value34, mmt_left_value35, mmt_left_value36,
            mmt_left_value37, mmt_left_value38, mmt_left_value39, mmt_left_value40, mmt_left_value41, mmt_left_value42,
            mmt_left_value43, mmt_left_value44, mmt_left_value45, mmt_left_value46, mmt_left_value47, mmt_left_value48,
            mmt_left_value49, mmt_left_value50, mmt_left_value51, mmt_left_value52, mmt_left_value53, mmt_left_value54,
            mmt_left_value55, mmt_left_value56;

    private String mmt_right_value1, mmt_right_value2, mmt_right_value3, mmt_right_value4, mmt_right_value5,
            mmt_right_value6, mmt_right_value7, mmt_right_value8, mmt_right_value9, mmt_right_value10,
            mmt_right_value11, mmt_right_value12, mmt_right_value13, mmt_right_value14, mmt_right_value15,
            mmt_right_value16, mmt_right_value17, mmt_right_value18, mmt_right_value19, mmt_right_value20,
            mmt_right_value21, mmt_right_value22, mmt_right_value23, mmt_right_value24, mmt_right_value25,
            mmt_right_value26, mmt_right_value27, mmt_right_value28, mmt_right_value29, mmt_right_value30,
            mmt_right_value31, mmt_right_value32, mmt_right_value33, mmt_right_value34, mmt_right_value35,
            mmt_right_value36, mmt_right_value37, mmt_right_value38, mmt_right_value39, mmt_right_value40,
            mmt_right_value41, mmt_right_value42, mmt_right_value43, mmt_right_value44, mmt_right_value45,
            mmt_right_value46, mmt_right_value47, mmt_right_value48, mmt_right_value49, mmt_right_value50,
            mmt_right_value51, mmt_right_value52, mmt_right_value53, mmt_right_value54, mmt_right_value55,
            mmt_right_value56;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_mmt_info);

        queryInstructions();

        initView();


        //网络返回数据后得到通知刷新View
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        google_progress.setVisibility(View.GONE);
                        break;
                }
            }
        };

    }

    /**
     * 去除掉已经有的评定说明
     */
    public void queryInstructions() {

        list_instructions.add("初评");
        list_instructions.add("中评Ⅰ");
        list_instructions.add("中评Ⅱ");
        list_instructions.add("中评Ⅲ");
        list_instructions.add("中评Ⅳ");
        list_instructions.add("中评Ⅴ");
        list_instructions.add("末评");

        Intent intent = getIntent();
        isAdd = intent.getBooleanExtra("isAdd", true);
        record_id = intent.getIntExtra("record_id", 0);
        date_intent = intent.getStringExtra("date");
        list_instructions_cloud = intent.getStringArrayListExtra("instructions");
        list_lefts = intent.getStringArrayListExtra("lefts");
        list_rights = intent.getStringArrayListExtra("rights");
        instruction = intent.getStringExtra("instruction");

        for (int i = 0; i < list_instructions_cloud.size(); i++) {
            if (list_instructions.contains(list_instructions_cloud.get(i))) {
                list_instructions.remove(list_instructions_cloud.get(i));
            }
        }
        if (isAdd == false) {//如果是编辑界面，需要把当前的评定说明加回去
            list_instructions.add(0, instruction);
        }

    }


    private void initView() {

        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        google_progress = (GoogleProgressBar) findViewById(R.id.google_progress);
        google_progress.setVisibility(View.GONE);

        back = (ImageView) findViewById(R.id.tittle_back);
        back.setOnClickListener(this);
        save = (ImageView) findViewById(R.id.patient_save);
        save.setOnClickListener(this);
        instructions = (Spinner) findViewById(R.id.medical_history_relator_edit);
        date = (TextView) findViewById(R.id.alyn1_date_tv);
        date.setOnClickListener(this);
        //时间选择器
        pwTime = new TimePopupWindow(this, TimePopupWindow.Type.YEAR_MONTH_DAY);
        pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date d) {
                date.setText(getTime(d));
            }
        });
        pwTime.setCyclic(true);

        mmt_left_1 = (Spinner) findViewById(R.id.mmt_left_spinner1);
        mmt_left_2 = (Spinner) findViewById(R.id.mmt_left_spinner2);
        mmt_left_3 = (Spinner) findViewById(R.id.mmt_left_spinner3);
        mmt_left_4 = (Spinner) findViewById(R.id.mmt_left_spinner4);
        mmt_left_5 = (Spinner) findViewById(R.id.mmt_left_spinner5);
        mmt_left_6 = (Spinner) findViewById(R.id.mmt_left_spinner6);
        mmt_left_7 = (Spinner) findViewById(R.id.mmt_left_spinner7);
        mmt_left_8 = (Spinner) findViewById(R.id.mmt_left_spinner8);
        mmt_left_9 = (Spinner) findViewById(R.id.mmt_left_spinner9);
        mmt_left_10 = (Spinner) findViewById(R.id.mmt_left_spinner10);
        mmt_left_11 = (Spinner) findViewById(R.id.mmt_left_spinner11);
        mmt_left_12 = (Spinner) findViewById(R.id.mmt_left_spinner12);
        mmt_left_13 = (Spinner) findViewById(R.id.mmt_left_spinner13);
        mmt_left_14 = (Spinner) findViewById(R.id.mmt_left_spinner14);
        mmt_left_15 = (Spinner) findViewById(R.id.mmt_left_spinner15);
        mmt_left_16 = (Spinner) findViewById(R.id.mmt_left_spinner16);
        mmt_left_17 = (Spinner) findViewById(R.id.mmt_left_spinner17);
        mmt_left_18 = (Spinner) findViewById(R.id.mmt_left_spinner18);
        mmt_left_19 = (Spinner) findViewById(R.id.mmt_left_spinner19);
        mmt_left_20 = (Spinner) findViewById(R.id.mmt_left_spinner20);
        mmt_left_21 = (Spinner) findViewById(R.id.mmt_left_spinner21);
        mmt_left_22 = (Spinner) findViewById(R.id.mmt_left_spinner22);
        mmt_left_23 = (Spinner) findViewById(R.id.mmt_left_spinner23);
        mmt_left_24 = (Spinner) findViewById(R.id.mmt_left_spinner24);
        mmt_left_25 = (Spinner) findViewById(R.id.mmt_left_spinner25);
        mmt_left_26 = (Spinner) findViewById(R.id.mmt_left_spinner26);
        mmt_left_27 = (Spinner) findViewById(R.id.mmt_left_spinner27);
        mmt_left_28 = (Spinner) findViewById(R.id.mmt_left_spinner28);
        mmt_left_29 = (Spinner) findViewById(R.id.mmt_left_spinner29);
        mmt_left_30 = (Spinner) findViewById(R.id.mmt_left_spinner30);
        mmt_left_31 = (Spinner) findViewById(R.id.mmt_left_spinner31);
        mmt_left_32 = (Spinner) findViewById(R.id.mmt_left_spinner32);
        mmt_left_33 = (Spinner) findViewById(R.id.mmt_left_spinner33);
        mmt_left_34 = (Spinner) findViewById(R.id.mmt_left_spinner34);
        mmt_left_35 = (Spinner) findViewById(R.id.mmt_left_spinner35);
        mmt_left_36 = (Spinner) findViewById(R.id.mmt_left_spinner36);
        mmt_left_37 = (Spinner) findViewById(R.id.mmt_left_spinner37);
        mmt_left_38 = (Spinner) findViewById(R.id.mmt_left_spinner38);
        mmt_left_39 = (Spinner) findViewById(R.id.mmt_left_spinner39);
        mmt_left_40 = (Spinner) findViewById(R.id.mmt_left_spinner40);
        mmt_left_41 = (Spinner) findViewById(R.id.mmt_left_spinner41);
        mmt_left_42 = (Spinner) findViewById(R.id.mmt_left_spinner42);
        mmt_left_43 = (Spinner) findViewById(R.id.mmt_left_spinner43);
        mmt_left_44 = (Spinner) findViewById(R.id.mmt_left_spinner44);
        mmt_left_45 = (Spinner) findViewById(R.id.mmt_left_spinner45);
        mmt_left_46 = (Spinner) findViewById(R.id.mmt_left_spinner46);
        mmt_left_47 = (Spinner) findViewById(R.id.mmt_left_spinner47);
        mmt_left_48 = (Spinner) findViewById(R.id.mmt_left_spinner48);
        mmt_left_49 = (Spinner) findViewById(R.id.mmt_left_spinner49);
        mmt_left_50 = (Spinner) findViewById(R.id.mmt_left_spinner50);
        mmt_left_51 = (Spinner) findViewById(R.id.mmt_left_spinner51);
        mmt_left_52 = (Spinner) findViewById(R.id.mmt_left_spinner52);
        mmt_left_53 = (Spinner) findViewById(R.id.mmt_left_spinner53);
        mmt_left_54 = (Spinner) findViewById(R.id.mmt_left_spinner54);
        mmt_left_55 = (Spinner) findViewById(R.id.mmt_left_spinner55);
        mmt_left_56 = (Spinner) findViewById(R.id.mmt_left_spinner56);

        mmt_right_1 = (Spinner) findViewById(R.id.mmt_right_spinner1);
        mmt_right_2 = (Spinner) findViewById(R.id.mmt_right_spinner2);
        mmt_right_3 = (Spinner) findViewById(R.id.mmt_right_spinner3);
        mmt_right_4 = (Spinner) findViewById(R.id.mmt_right_spinner4);
        mmt_right_5 = (Spinner) findViewById(R.id.mmt_right_spinner5);
        mmt_right_6 = (Spinner) findViewById(R.id.mmt_right_spinner6);
        mmt_right_7 = (Spinner) findViewById(R.id.mmt_right_spinner7);
        mmt_right_8 = (Spinner) findViewById(R.id.mmt_right_spinner8);
        mmt_right_9 = (Spinner) findViewById(R.id.mmt_right_spinner9);
        mmt_right_10 = (Spinner) findViewById(R.id.mmt_right_spinner10);
        mmt_right_11 = (Spinner) findViewById(R.id.mmt_right_spinner11);
        mmt_right_12 = (Spinner) findViewById(R.id.mmt_right_spinner12);
        mmt_right_13 = (Spinner) findViewById(R.id.mmt_right_spinner13);
        mmt_right_14 = (Spinner) findViewById(R.id.mmt_right_spinner14);
        mmt_right_15 = (Spinner) findViewById(R.id.mmt_right_spinner15);
        mmt_right_16 = (Spinner) findViewById(R.id.mmt_right_spinner16);
        mmt_right_17 = (Spinner) findViewById(R.id.mmt_right_spinner17);
        mmt_right_18 = (Spinner) findViewById(R.id.mmt_right_spinner18);
        mmt_right_19 = (Spinner) findViewById(R.id.mmt_right_spinner19);
        mmt_right_20 = (Spinner) findViewById(R.id.mmt_right_spinner20);
        mmt_right_21 = (Spinner) findViewById(R.id.mmt_right_spinner21);
        mmt_right_22 = (Spinner) findViewById(R.id.mmt_right_spinner22);
        mmt_right_23 = (Spinner) findViewById(R.id.mmt_right_spinner23);
        mmt_right_24 = (Spinner) findViewById(R.id.mmt_right_spinner24);
        mmt_right_25 = (Spinner) findViewById(R.id.mmt_right_spinner25);
        mmt_right_26 = (Spinner) findViewById(R.id.mmt_right_spinner26);
        mmt_right_27 = (Spinner) findViewById(R.id.mmt_right_spinner27);
        mmt_right_28 = (Spinner) findViewById(R.id.mmt_right_spinner28);
        mmt_right_29 = (Spinner) findViewById(R.id.mmt_right_spinner29);
        mmt_right_30 = (Spinner) findViewById(R.id.mmt_right_spinner30);
        mmt_right_31 = (Spinner) findViewById(R.id.mmt_right_spinner31);
        mmt_right_32 = (Spinner) findViewById(R.id.mmt_right_spinner32);
        mmt_right_33 = (Spinner) findViewById(R.id.mmt_right_spinner33);
        mmt_right_34 = (Spinner) findViewById(R.id.mmt_right_spinner34);
        mmt_right_35 = (Spinner) findViewById(R.id.mmt_right_spinner35);
        mmt_right_36 = (Spinner) findViewById(R.id.mmt_right_spinner36);
        mmt_right_37 = (Spinner) findViewById(R.id.mmt_right_spinner37);
        mmt_right_38 = (Spinner) findViewById(R.id.mmt_right_spinner38);
        mmt_right_39 = (Spinner) findViewById(R.id.mmt_right_spinner39);
        mmt_right_40 = (Spinner) findViewById(R.id.mmt_right_spinner40);
        mmt_right_41 = (Spinner) findViewById(R.id.mmt_right_spinner41);
        mmt_right_42 = (Spinner) findViewById(R.id.mmt_right_spinner42);
        mmt_right_43 = (Spinner) findViewById(R.id.mmt_right_spinner43);
        mmt_right_44 = (Spinner) findViewById(R.id.mmt_right_spinner44);
        mmt_right_45 = (Spinner) findViewById(R.id.mmt_right_spinner45);
        mmt_right_46 = (Spinner) findViewById(R.id.mmt_right_spinner46);
        mmt_right_47 = (Spinner) findViewById(R.id.mmt_right_spinner47);
        mmt_right_48 = (Spinner) findViewById(R.id.mmt_right_spinner48);
        mmt_right_49 = (Spinner) findViewById(R.id.mmt_right_spinner49);
        mmt_right_50 = (Spinner) findViewById(R.id.mmt_right_spinner50);
        mmt_right_51 = (Spinner) findViewById(R.id.mmt_right_spinner51);
        mmt_right_52 = (Spinner) findViewById(R.id.mmt_right_spinner52);
        mmt_right_53 = (Spinner) findViewById(R.id.mmt_right_spinner53);
        mmt_right_54 = (Spinner) findViewById(R.id.mmt_right_spinner54);
        mmt_right_55 = (Spinner) findViewById(R.id.mmt_right_spinner55);
        mmt_right_56 = (Spinner) findViewById(R.id.mmt_right_spinner56);

        setMyAdapter();

        if (isAdd == false) {
            instructions.setClickable(false);
            putData();
        }
    }

    /**
     * 给所有控件赋值
     */
    private void putData() {

        date.setText(date_intent);
        setSpinnerData(list_instructions, instruction, instructions);

        setSpinnerData(list, list_lefts.get(0), mmt_left_1);
        setSpinnerData(list, list_lefts.get(1), mmt_left_2);
        setSpinnerData(list, list_lefts.get(2), mmt_left_3);
        setSpinnerData(list, list_lefts.get(3), mmt_left_4);
        setSpinnerData(list, list_lefts.get(4), mmt_left_5);
        setSpinnerData(list, list_lefts.get(5), mmt_left_6);
        setSpinnerData(list, list_lefts.get(6), mmt_left_7);
        setSpinnerData(list, list_lefts.get(7), mmt_left_8);
        setSpinnerData(list, list_lefts.get(8), mmt_left_9);
        setSpinnerData(list, list_lefts.get(9), mmt_left_10);
        setSpinnerData(list, list_lefts.get(10), mmt_left_11);
        setSpinnerData(list, list_lefts.get(11), mmt_left_12);
        setSpinnerData(list, list_lefts.get(12), mmt_left_13);
        setSpinnerData(list, list_lefts.get(13), mmt_left_14);
        setSpinnerData(list, list_lefts.get(14), mmt_left_15);
        setSpinnerData(list, list_lefts.get(15), mmt_left_16);
        setSpinnerData(list, list_lefts.get(16), mmt_left_17);
        setSpinnerData(list, list_lefts.get(17), mmt_left_18);
        setSpinnerData(list, list_lefts.get(18), mmt_left_19);
        setSpinnerData(list, list_lefts.get(19), mmt_left_20);
        setSpinnerData(list, list_lefts.get(20), mmt_left_21);
        setSpinnerData(list, list_lefts.get(21), mmt_left_22);
        setSpinnerData(list, list_lefts.get(22), mmt_left_23);
        setSpinnerData(list, list_lefts.get(23), mmt_left_24);
        setSpinnerData(list, list_lefts.get(24), mmt_left_25);
        setSpinnerData(list, list_lefts.get(25), mmt_left_26);
        setSpinnerData(list, list_lefts.get(26), mmt_left_27);
        setSpinnerData(list, list_lefts.get(27), mmt_left_28);
        setSpinnerData(list, list_lefts.get(28), mmt_left_29);
        setSpinnerData(list, list_lefts.get(29), mmt_left_30);
        setSpinnerData(list, list_lefts.get(30), mmt_left_31);
        setSpinnerData(list, list_lefts.get(31), mmt_left_32);
        setSpinnerData(list, list_lefts.get(32), mmt_left_33);
        setSpinnerData(list, list_lefts.get(33), mmt_left_34);
        setSpinnerData(list, list_lefts.get(34), mmt_left_35);
        setSpinnerData(list, list_lefts.get(35), mmt_left_36);
        setSpinnerData(list, list_lefts.get(36), mmt_left_37);
        setSpinnerData(list, list_lefts.get(37), mmt_left_38);
        setSpinnerData(list, list_lefts.get(38), mmt_left_39);
        setSpinnerData(list, list_lefts.get(39), mmt_left_40);
        setSpinnerData(list, list_lefts.get(40), mmt_left_41);
        setSpinnerData(list, list_lefts.get(41), mmt_left_42);
        setSpinnerData(list, list_lefts.get(42), mmt_left_43);
        setSpinnerData(list, list_lefts.get(43), mmt_left_44);
        setSpinnerData(list, list_lefts.get(44), mmt_left_45);
        setSpinnerData(list, list_lefts.get(45), mmt_left_46);
        setSpinnerData(list, list_lefts.get(46), mmt_left_47);
        setSpinnerData(list, list_lefts.get(47), mmt_left_48);
        setSpinnerData(list, list_lefts.get(48), mmt_left_49);
        setSpinnerData(list, list_lefts.get(49), mmt_left_50);
        setSpinnerData(list, list_lefts.get(50), mmt_left_51);
        setSpinnerData(list, list_lefts.get(51), mmt_left_52);
        setSpinnerData(list, list_lefts.get(52), mmt_left_53);
        setSpinnerData(list, list_lefts.get(53), mmt_left_54);
        setSpinnerData(list, list_lefts.get(54), mmt_left_55);
        setSpinnerData(list, list_lefts.get(55), mmt_left_56);


        setSpinnerData(list, list_rights.get(0), mmt_right_1);
        setSpinnerData(list, list_rights.get(1), mmt_right_2);
        setSpinnerData(list, list_rights.get(2), mmt_right_3);
        setSpinnerData(list, list_rights.get(3), mmt_right_4);
        setSpinnerData(list, list_rights.get(4), mmt_right_5);
        setSpinnerData(list, list_rights.get(5), mmt_right_6);
        setSpinnerData(list, list_rights.get(6), mmt_right_7);
        setSpinnerData(list, list_rights.get(7), mmt_right_8);
        setSpinnerData(list, list_rights.get(8), mmt_right_9);
        setSpinnerData(list, list_rights.get(9), mmt_right_10);
        setSpinnerData(list, list_rights.get(10), mmt_right_11);
        setSpinnerData(list, list_rights.get(11), mmt_right_12);
        setSpinnerData(list, list_rights.get(12), mmt_right_13);
        setSpinnerData(list, list_rights.get(13), mmt_right_14);
        setSpinnerData(list, list_rights.get(14), mmt_right_15);
        setSpinnerData(list, list_rights.get(15), mmt_right_16);
        setSpinnerData(list, list_rights.get(16), mmt_right_17);
        setSpinnerData(list, list_rights.get(17), mmt_right_18);
        setSpinnerData(list, list_rights.get(18), mmt_right_19);
        setSpinnerData(list, list_rights.get(19), mmt_right_20);
        setSpinnerData(list, list_rights.get(20), mmt_right_21);
        setSpinnerData(list, list_rights.get(21), mmt_right_22);
        setSpinnerData(list, list_rights.get(22), mmt_right_23);
        setSpinnerData(list, list_rights.get(23), mmt_right_24);
        setSpinnerData(list, list_rights.get(24), mmt_right_25);
        setSpinnerData(list, list_rights.get(25), mmt_right_26);
        setSpinnerData(list, list_rights.get(26), mmt_right_27);
        setSpinnerData(list, list_rights.get(27), mmt_right_28);
        setSpinnerData(list, list_rights.get(28), mmt_right_29);
        setSpinnerData(list, list_rights.get(29), mmt_right_30);
        setSpinnerData(list, list_rights.get(30), mmt_right_31);
        setSpinnerData(list, list_rights.get(31), mmt_right_32);
        setSpinnerData(list, list_rights.get(32), mmt_right_33);
        setSpinnerData(list, list_rights.get(33), mmt_right_34);
        setSpinnerData(list, list_rights.get(34), mmt_right_35);
        setSpinnerData(list, list_rights.get(35), mmt_right_36);
        setSpinnerData(list, list_rights.get(36), mmt_right_37);
        setSpinnerData(list, list_rights.get(37), mmt_right_38);
        setSpinnerData(list, list_rights.get(38), mmt_right_39);
        setSpinnerData(list, list_rights.get(39), mmt_right_40);
        setSpinnerData(list, list_rights.get(40), mmt_right_41);
        setSpinnerData(list, list_rights.get(41), mmt_right_42);
        setSpinnerData(list, list_rights.get(42), mmt_right_43);
        setSpinnerData(list, list_rights.get(43), mmt_right_44);
        setSpinnerData(list, list_rights.get(44), mmt_right_45);
        setSpinnerData(list, list_rights.get(45), mmt_right_46);
        setSpinnerData(list, list_rights.get(46), mmt_right_47);
        setSpinnerData(list, list_rights.get(47), mmt_right_48);
        setSpinnerData(list, list_rights.get(48), mmt_right_49);
        setSpinnerData(list, list_rights.get(49), mmt_right_50);
        setSpinnerData(list, list_rights.get(50), mmt_right_51);
        setSpinnerData(list, list_rights.get(51), mmt_right_52);
        setSpinnerData(list, list_rights.get(52), mmt_right_53);
        setSpinnerData(list, list_rights.get(53), mmt_right_54);
        setSpinnerData(list, list_rights.get(54), mmt_right_55);
        setSpinnerData(list, list_rights.get(55), mmt_right_56);

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


    /**
     * 给每个spinner做监听
     */
    private void setMyAdapter() {
        adapter = new SpinnerBaseAdapter(this, android.R.layout.simple_spinner_item, instructions, list_instructions);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        instructions.setOnItemSelectedListener(new MyOnItemSelectedListener());
        instructions.setAdapter(adapter);

        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_1, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_1.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_1.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_2, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_2.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_2.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_3, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_3.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_3.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_4, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_4.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_4.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_5, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_5.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_5.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_6, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_6.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_6.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_7, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_7.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_7.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_8, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_8.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_8.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_9, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_9.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_9.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_10, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_10.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_10.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_11, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_11.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_11.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_12, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_12.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_12.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_13, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_13.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_13.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_14, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_14.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_14.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_15, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_15.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_15.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_16, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_16.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_16.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_17, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_17.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_17.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_18, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_18.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_18.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_19, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_19.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_19.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_20, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_20.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_20.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_21, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_21.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_21.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_22, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_22.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_22.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_23, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_23.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_23.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_24, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_24.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_24.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_25, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_25.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_25.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_26, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_26.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_26.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_27, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_27.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_27.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_28, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_28.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_28.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_29, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_29.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_29.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_30, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_30.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_30.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_31, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_31.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_31.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_32, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_32.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_32.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_33, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_33.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_33.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_34, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_34.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_34.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_35, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_35.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_35.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_36, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_36.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_36.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_37, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_37.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_37.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_38, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_38.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_38.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_39, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_39.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_39.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_40, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_40.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_40.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_41, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_41.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_41.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_42, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_42.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_42.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_43, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_43.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_43.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_44, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_44.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_44.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_45, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_45.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_45.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_46, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_46.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_46.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_47, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_47.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_47.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_48, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_48.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_48.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_49, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_49.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_49.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_50, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_50.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_50.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_51, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_51.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_51.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_52, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_52.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_52.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_53, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_53.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_53.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_54, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_54.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_54.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_55, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_55.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_55.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_left_56, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_left_56.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_left_56.setAdapter(adapter);

        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_1, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_1.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_1.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_2, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_2.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_2.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_3, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_3.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_3.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_4, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_4.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_4.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_5, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_5.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_5.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_6, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_6.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_6.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_7, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_7.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_7.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_8, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_8.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_8.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_9, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_9.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_9.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_10, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_10.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_10.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_11, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_11.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_11.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_12, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_12.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_12.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_13, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_13.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_13.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_14, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_14.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_14.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_15, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_15.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_15.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_16, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_16.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_16.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_17, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_17.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_17.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_18, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_18.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_18.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_19, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_19.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_19.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_20, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_20.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_20.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_21, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_21.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_21.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_22, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_22.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_22.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_23, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_23.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_23.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_24, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_24.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_24.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_25, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_25.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_25.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_26, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_26.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_26.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_27, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_27.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_27.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_28, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_28.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_28.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_29, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_29.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_29.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_30, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_30.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_30.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_31, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_31.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_31.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_32, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_32.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_32.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_33, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_33.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_33.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_34, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_34.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_34.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_35, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_35.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_35.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_36, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_36.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_36.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_37, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_37.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_37.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_38, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_38.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_38.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_39, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_39.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_39.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_40, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_40.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_40.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_41, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_41.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_41.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_42, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_42.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_42.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_43, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_43.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_43.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_44, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_44.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_44.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_45, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_45.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_45.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_46, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_46.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_46.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_47, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_47.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_47.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_48, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_48.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_48.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_49, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_49.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_49.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_50, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_50.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_50.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_51, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_51.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_51.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_52, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_52.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_52.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_53, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_53.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_53.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_54, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_54.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_54.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_55, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_55.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_55.setAdapter(adapter);
        adapter = new SpinnerBaseAdapter(this, R.layout.simple_spinner_item, mmt_right_56, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        mmt_right_56.setOnItemSelectedListener(new MyOnItemSelectedListener());
        mmt_right_56.setAdapter(adapter);
    }

    /**
     * 监听 Spinner点选
     */
    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (parent == instructions) {
                instructions_value = list_instructions.get(position);
                if ("初评".equals(instructions_value)) {
                    instructions_index = 0;
                } else if ("中评Ⅰ".equals(instructions_value)) {
                    instructions_index = 1;
                } else if ("中评Ⅱ".equals(instructions_value)) {
                    instructions_index = 3;
                } else if ("中评Ⅲ".equals(instructions_value)) {
                    instructions_index = 4;
                } else if ("中评Ⅳ".equals(instructions_value)) {
                    instructions_index = 5;
                } else if ("中评Ⅴ".equals(instructions_value)) {
                    instructions_index = 6;
                } else if ("末评".equals(instructions_value)) {
                    instructions_index = 2;
                }
            }

            if (parent == mmt_left_1) {
                mmt_left_value1 = list.get(position);
            }
            if (parent == mmt_left_2) {
                mmt_left_value2 = list.get(position);
            }
            if (parent == mmt_left_3) {
                mmt_left_value3 = list.get(position);
            }
            if (parent == mmt_left_4) {
                mmt_left_value4 = list.get(position);
            }
            if (parent == mmt_left_5) {
                mmt_left_value5 = list.get(position);
            }
            if (parent == mmt_left_6) {
                mmt_left_value6 = list.get(position);
            }
            if (parent == mmt_left_7) {
                mmt_left_value7 = list.get(position);
            }
            if (parent == mmt_left_8) {
                mmt_left_value8 = list.get(position);
            }
            if (parent == mmt_left_9) {
                mmt_left_value9 = list.get(position);
            }
            if (parent == mmt_left_10) {
                mmt_left_value10 = list.get(position);
            }
            if (parent == mmt_left_11) {
                mmt_left_value11 = list.get(position);
            }
            if (parent == mmt_left_12) {
                mmt_left_value12 = list.get(position);
            }
            if (parent == mmt_left_13) {
                mmt_left_value13 = list.get(position);
            }
            if (parent == mmt_left_14) {
                mmt_left_value14 = list.get(position);
            }
            if (parent == mmt_left_15) {
                mmt_left_value15 = list.get(position);
            }
            if (parent == mmt_left_16) {
                mmt_left_value16 = list.get(position);
            }
            if (parent == mmt_left_17) {
                mmt_left_value17 = list.get(position);
            }
            if (parent == mmt_left_18) {
                mmt_left_value18 = list.get(position);
            }
            if (parent == mmt_left_19) {
                mmt_left_value19 = list.get(position);
            }
            if (parent == mmt_left_20) {
                mmt_left_value20 = list.get(position);
            }
            if (parent == mmt_left_21) {
                mmt_left_value21 = list.get(position);
            }
            if (parent == mmt_left_22) {
                mmt_left_value22 = list.get(position);
            }
            if (parent == mmt_left_23) {
                mmt_left_value23 = list.get(position);
            }
            if (parent == mmt_left_24) {
                mmt_left_value24 = list.get(position);
            }
            if (parent == mmt_left_25) {
                mmt_left_value25 = list.get(position);
            }
            if (parent == mmt_left_26) {
                mmt_left_value26 = list.get(position);
            }
            if (parent == mmt_left_27) {
                mmt_left_value27 = list.get(position);
            }
            if (parent == mmt_left_28) {
                mmt_left_value28 = list.get(position);
            }
            if (parent == mmt_left_29) {
                mmt_left_value29 = list.get(position);
            }
            if (parent == mmt_left_30) {
                mmt_left_value30 = list.get(position);
            }
            if (parent == mmt_left_31) {
                mmt_left_value31 = list.get(position);
            }
            if (parent == mmt_left_32) {
                mmt_left_value32 = list.get(position);
            }
            if (parent == mmt_left_33) {
                mmt_left_value33 = list.get(position);
            }
            if (parent == mmt_left_34) {
                mmt_left_value34 = list.get(position);
            }
            if (parent == mmt_left_35) {
                mmt_left_value35 = list.get(position);
            }
            if (parent == mmt_left_36) {
                mmt_left_value36 = list.get(position);
            }
            if (parent == mmt_left_37) {
                mmt_left_value37 = list.get(position);
            }
            if (parent == mmt_left_38) {
                mmt_left_value38 = list.get(position);
            }
            if (parent == mmt_left_39) {
                mmt_left_value39 = list.get(position);
            }
            if (parent == mmt_left_40) {
                mmt_left_value40 = list.get(position);
            }
            if (parent == mmt_left_41) {
                mmt_left_value41 = list.get(position);
            }
            if (parent == mmt_left_42) {
                mmt_left_value42 = list.get(position);
            }
            if (parent == mmt_left_43) {
                mmt_left_value43 = list.get(position);
            }
            if (parent == mmt_left_44) {
                mmt_left_value44 = list.get(position);
            }
            if (parent == mmt_left_45) {
                mmt_left_value45 = list.get(position);
            }
            if (parent == mmt_left_46) {
                mmt_left_value46 = list.get(position);
            }
            if (parent == mmt_left_47) {
                mmt_left_value47 = list.get(position);
            }
            if (parent == mmt_left_48) {
                mmt_left_value48 = list.get(position);
            }
            if (parent == mmt_left_49) {
                mmt_left_value49 = list.get(position);
            }
            if (parent == mmt_left_50) {
                mmt_left_value50 = list.get(position);
            }
            if (parent == mmt_left_51) {
                mmt_left_value51 = list.get(position);
            }
            if (parent == mmt_left_52) {
                mmt_left_value52 = list.get(position);
            }
            if (parent == mmt_left_53) {
                mmt_left_value53 = list.get(position);
            }
            if (parent == mmt_left_54) {
                mmt_left_value54 = list.get(position);
            }
            if (parent == mmt_left_55) {
                mmt_left_value55 = list.get(position);
            }
            if (parent == mmt_left_56) {
                mmt_left_value56 = list.get(position);
            }
            if (parent == mmt_right_1) {
                mmt_right_value1 = list.get(position);
            }
            if (parent == mmt_right_2) {
                mmt_right_value2 = list.get(position);
            }
            if (parent == mmt_right_3) {
                mmt_right_value3 = list.get(position);
            }
            if (parent == mmt_right_4) {
                mmt_right_value4 = list.get(position);
            }
            if (parent == mmt_right_5) {
                mmt_right_value5 = list.get(position);
            }
            if (parent == mmt_right_6) {
                mmt_right_value6 = list.get(position);
            }
            if (parent == mmt_right_7) {
                mmt_right_value7 = list.get(position);
            }
            if (parent == mmt_right_8) {
                mmt_right_value8 = list.get(position);
            }
            if (parent == mmt_right_9) {
                mmt_right_value9 = list.get(position);
            }
            if (parent == mmt_right_10) {
                mmt_right_value10 = list.get(position);
            }
            if (parent == mmt_right_11) {
                mmt_right_value11 = list.get(position);
            }
            if (parent == mmt_right_12) {
                mmt_right_value12 = list.get(position);
            }
            if (parent == mmt_right_13) {
                mmt_right_value13 = list.get(position);
            }
            if (parent == mmt_right_14) {
                mmt_right_value14 = list.get(position);
            }
            if (parent == mmt_right_15) {
                mmt_right_value15 = list.get(position);
            }
            if (parent == mmt_right_16) {
                mmt_right_value16 = list.get(position);
            }
            if (parent == mmt_right_17) {
                mmt_right_value17 = list.get(position);
            }
            if (parent == mmt_right_18) {
                mmt_right_value18 = list.get(position);
            }
            if (parent == mmt_right_19) {
                mmt_right_value19 = list.get(position);
            }
            if (parent == mmt_right_20) {
                mmt_right_value20 = list.get(position);
            }
            if (parent == mmt_right_21) {
                mmt_right_value21 = list.get(position);
            }
            if (parent == mmt_right_22) {
                mmt_right_value22 = list.get(position);
            }
            if (parent == mmt_right_23) {
                mmt_right_value23 = list.get(position);
            }
            if (parent == mmt_right_24) {
                mmt_right_value24 = list.get(position);
            }
            if (parent == mmt_right_25) {
                mmt_right_value25 = list.get(position);
            }
            if (parent == mmt_right_26) {
                mmt_right_value26 = list.get(position);
            }
            if (parent == mmt_right_27) {
                mmt_right_value27 = list.get(position);
            }
            if (parent == mmt_right_28) {
                mmt_right_value28 = list.get(position);
            }
            if (parent == mmt_right_29) {
                mmt_right_value29 = list.get(position);
            }
            if (parent == mmt_right_30) {
                mmt_right_value30 = list.get(position);
            }
            if (parent == mmt_right_31) {
                mmt_right_value31 = list.get(position);
            }
            if (parent == mmt_right_32) {
                mmt_right_value32 = list.get(position);
            }
            if (parent == mmt_right_33) {
                mmt_right_value33 = list.get(position);
            }
            if (parent == mmt_right_34) {
                mmt_right_value34 = list.get(position);
            }
            if (parent == mmt_right_35) {
                mmt_right_value35 = list.get(position);
            }
            if (parent == mmt_right_36) {
                mmt_right_value36 = list.get(position);
            }
            if (parent == mmt_right_37) {
                mmt_right_value37 = list.get(position);
            }
            if (parent == mmt_right_38) {
                mmt_right_value38 = list.get(position);
            }
            if (parent == mmt_right_39) {
                mmt_right_value39 = list.get(position);
            }
            if (parent == mmt_right_40) {
                mmt_right_value40 = list.get(position);
            }
            if (parent == mmt_right_41) {
                mmt_right_value41 = list.get(position);
            }
            if (parent == mmt_right_42) {
                mmt_right_value42 = list.get(position);
            }
            if (parent == mmt_right_43) {
                mmt_right_value43 = list.get(position);
            }
            if (parent == mmt_right_44) {
                mmt_right_value44 = list.get(position);
            }
            if (parent == mmt_right_45) {
                mmt_right_value45 = list.get(position);
            }
            if (parent == mmt_right_46) {
                mmt_right_value46 = list.get(position);
            }
            if (parent == mmt_right_47) {
                mmt_right_value47 = list.get(position);
            }
            if (parent == mmt_right_48) {
                mmt_right_value48 = list.get(position);
            }
            if (parent == mmt_right_49) {
                mmt_right_value49 = list.get(position);
            }
            if (parent == mmt_right_50) {
                mmt_right_value50 = list.get(position);
            }
            if (parent == mmt_right_51) {
                mmt_right_value51 = list.get(position);
            }
            if (parent == mmt_right_52) {
                mmt_right_value52 = list.get(position);
            }
            if (parent == mmt_right_53) {
                mmt_right_value53 = list.get(position);
            }
            if (parent == mmt_right_54) {
                mmt_right_value54 = list.get(position);
            }
            if (parent == mmt_right_55) {
                mmt_right_value55 = list.get(position);
            }
            if (parent == mmt_right_56) {
                mmt_right_value56 = list.get(position);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alyn1_date_tv:
                pwTime.showAtLocation(date, Gravity.BOTTOM, 0, 0, new Date());
                break;
            case R.id.tittle_back:
                this.finish();

                break;
            case R.id.patient_save:
                if (TextUtils.isEmpty(date.getText())) {
                    ShowToast.Short("请选择评定日期");
                } else {

                    google_progress.setVisibility(View.VISIBLE);

                    SharedPreferences sharedPreferences = getSharedPreferences("patientinfo", Activity.MODE_PRIVATE);
                    int id = sharedPreferences.getInt("id", -1);

                    SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                    int user_id = preferences.getInt("use_id", 2);

                    param = "evaluation[evaluation_time]=" + date.getText()
                            + "&evaluation[evaluation_time_note]=" + instructions_index //95
                            + "&patient_info_id=" + id
                            + "&user_auth_id=" + user_id
                            + "&c=" + 56
                            + "&left_zd0=" + mmt_left_value1
                            + "&left_zd1=" + mmt_left_value2
                            + "&left_zd2=" + mmt_left_value3
                            + "&left_zd3=" + mmt_left_value4
                            + "&left_zd4=" + mmt_left_value5
                            + "&left_zd5=" + mmt_left_value6
                            + "&left_zd6=" + mmt_left_value7
                            + "&left_zd7=" + mmt_left_value8
                            + "&left_zd8=" + mmt_left_value9
                            + "&left_zd9=" + mmt_left_value10
                            + "&left_zd10=" + mmt_left_value11
                            + "&left_zd11=" + mmt_left_value12
                            + "&left_zd12=" + mmt_left_value13
                            + "&left_zd13=" + mmt_left_value14
                            + "&left_zd14=" + mmt_left_value15
                            + "&left_zd15=" + mmt_left_value16
                            + "&left_zd16=" + mmt_left_value17
                            + "&left_zd17=" + mmt_left_value18
                            + "&left_zd18=" + mmt_left_value19
                            + "&left_zd19=" + mmt_left_value20
                            + "&left_zd20=" + mmt_left_value21
                            + "&left_zd21=" + mmt_left_value22
                            + "&left_zd22=" + mmt_left_value23
                            + "&left_zd23=" + mmt_left_value24
                            + "&left_zd24=" + mmt_left_value25
                            + "&left_zd25=" + mmt_left_value26
                            + "&left_zd26=" + mmt_left_value27
                            + "&left_zd27=" + mmt_left_value28
                            + "&left_zd28=" + mmt_left_value29
                            + "&left_zd29=" + mmt_left_value30
                            + "&left_zd30=" + mmt_left_value31
                            + "&left_zd31=" + mmt_left_value32
                            + "&left_zd32=" + mmt_left_value33
                            + "&left_zd33=" + mmt_left_value34
                            + "&left_zd34=" + mmt_left_value35
                            + "&left_zd35=" + mmt_left_value36
                            + "&left_zd36=" + mmt_left_value37
                            + "&left_zd37=" + mmt_left_value38
                            + "&left_zd38=" + mmt_left_value39
                            + "&left_zd39=" + mmt_left_value40
                            + "&left_zd40=" + mmt_left_value41
                            + "&left_zd41=" + mmt_left_value42
                            + "&left_zd42=" + mmt_left_value43
                            + "&left_zd43=" + mmt_left_value44
                            + "&left_zd44=" + mmt_left_value45
                            + "&left_zd45=" + mmt_left_value46
                            + "&left_zd46=" + mmt_left_value47
                            + "&left_zd47=" + mmt_left_value48
                            + "&left_zd48=" + mmt_left_value49
                            + "&left_zd49=" + mmt_left_value50
                            + "&left_zd50=" + mmt_left_value51
                            + "&left_zd51=" + mmt_left_value52
                            + "&left_zd52=" + mmt_left_value53
                            + "&left_zd53=" + mmt_left_value54
                            + "&left_zd54=" + mmt_left_value55
                            + "&left_zd55=" + mmt_left_value56

                            + "&right_zd0=" + mmt_right_value1
                            + "&right_zd1=" + mmt_right_value2
                            + "&right_zd2=" + mmt_right_value3
                            + "&right_zd3=" + mmt_right_value4
                            + "&right_zd4=" + mmt_right_value5
                            + "&right_zd5=" + mmt_right_value6
                            + "&right_zd6=" + mmt_right_value7
                            + "&right_zd7=" + mmt_right_value8
                            + "&right_zd8=" + mmt_right_value9
                            + "&right_zd9=" + mmt_right_value10
                            + "&right_zd10=" + mmt_right_value11
                            + "&right_zd11=" + mmt_right_value12
                            + "&right_zd12=" + mmt_right_value13
                            + "&right_zd13=" + mmt_right_value14
                            + "&right_zd14=" + mmt_right_value15
                            + "&right_zd15=" + mmt_right_value16
                            + "&right_zd16=" + mmt_right_value17
                            + "&right_zd17=" + mmt_right_value18
                            + "&right_zd18=" + mmt_right_value19
                            + "&right_zd19=" + mmt_right_value20
                            + "&right_zd20=" + mmt_right_value21
                            + "&right_zd21=" + mmt_right_value22
                            + "&right_zd22=" + mmt_right_value23
                            + "&right_zd23=" + mmt_right_value24
                            + "&right_zd24=" + mmt_right_value25
                            + "&right_zd25=" + mmt_right_value26
                            + "&right_zd26=" + mmt_right_value27
                            + "&right_zd27=" + mmt_right_value28
                            + "&right_zd28=" + mmt_right_value29
                            + "&right_zd29=" + mmt_right_value30
                            + "&right_zd30=" + mmt_right_value31
                            + "&right_zd31=" + mmt_right_value32
                            + "&right_zd32=" + mmt_right_value33
                            + "&right_zd33=" + mmt_right_value34
                            + "&right_zd34=" + mmt_right_value35
                            + "&right_zd35=" + mmt_right_value36
                            + "&right_zd36=" + mmt_right_value37
                            + "&right_zd37=" + mmt_right_value38
                            + "&right_zd38=" + mmt_right_value39
                            + "&right_zd39=" + mmt_right_value40
                            + "&right_zd40=" + mmt_right_value41
                            + "&right_zd41=" + mmt_right_value42
                            + "&right_zd42=" + mmt_right_value43
                            + "&right_zd43=" + mmt_right_value44
                            + "&right_zd44=" + mmt_right_value45
                            + "&right_zd45=" + mmt_right_value46
                            + "&right_zd46=" + mmt_right_value47
                            + "&right_zd47=" + mmt_right_value48
                            + "&right_zd48=" + mmt_right_value49
                            + "&right_zd49=" + mmt_right_value50
                            + "&right_zd50=" + mmt_right_value51
                            + "&right_zd51=" + mmt_right_value52
                            + "&right_zd52=" + mmt_right_value53
                            + "&right_zd53=" + mmt_right_value54
                            + "&right_zd54=" + mmt_right_value55
                            + "&right_zd55=" + mmt_right_value56

                            + "&token=" + NetUrlAddress.token;

                    new MyThread().start();
                }
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
                    create_code = TableMmtInfo.create(NetUrlAddress.create_url, param);
                } else {
                    String rul = NetUrlAddress.update_url.replaceAll("468",record_id+"");
                    update_code = TableMmtInfo.update(rul, param);
                }

                if (create_code == 200 || update_code == 200) {
                    TableMmtInfoActivity.this.finish();
                    ShowToast.Short("操作成功...");
                } else if (create_code == 100) {
                    ShowToast.Short("保存失败...");
                } else if (update_code == 100) {
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
