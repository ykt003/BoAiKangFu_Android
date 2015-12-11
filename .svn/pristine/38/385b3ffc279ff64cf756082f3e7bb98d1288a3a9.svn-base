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
import me.zhangls.rilintech.model.TableFmaInfo;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;

/**
 * Created by YANG on 15/9/30.
 */
public class TableFmaInfoActivity extends BaseActivity implements View.OnClickListener {

    //标题
    private TextView title;
    //返回按键
    private ImageView back;
    //保存按键
    private ImageView save;
    //radiogroup
    private RadioGroup rg1, rg2, rg3, rg4, rg5, rg6, rg7, rg8, rg9, rg10, rg11, rg12, rg13, rg14,
            rg15, rg16, rg17, rg18, rg19, rg20, rg21, rg22, rg23, rg24, rg25, rg26, rg27, rg28,
            rg29, rg30, rg31, rg32, rg33, rg34, rg35, rg36, rg37, rg38, rg39, rg40, rg41, rg42,
            rg43, rg44, rg45, rg46, rg47, rg48, rg49, rg50;
    //
    private ArrayList<RadioGroup> list_radioGroup;
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
    private TableFmaInfo info = null;
    //是否新建
    private Boolean isAdd = true;
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
    //评分集合
    private ArrayList<String> list_counts1, list_counts2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_fma_info);
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
     * 初始化控件
     */
    private void initView() {
        title = (TextView) findViewById(R.id.layout).findViewById(R.id.title_content);
        title.setText(getString(R.string.fma_title));
        back = (ImageView) findViewById(R.id.layout).findViewById(R.id.title_back);
        back.setOnClickListener(this);
        save = (ImageView) findViewById(R.id.layout).findViewById(R.id.title_save);
        save.setOnClickListener(this);

        list_counts1 = new ArrayList<>();
        list_counts1.add("0");
        list_counts1.add("1");
        list_counts1.add("2");
        list_counts2 = new ArrayList<>();
        list_counts2.add("0");
        list_counts2.add("2");


        instructions_map = new HashMap<>();
        instructions_map.put(getString(R.string.evaluation0), 0);
        instructions_map.put(getString(R.string.evaluation1), 1);
        instructions_map.put(getString(R.string.evaluation2), 2);
        instructions_map.put(getString(R.string.evaluation3), 3);
        instructions_map.put(getString(R.string.evaluation4), 4);
        instructions_map.put(getString(R.string.evaluation5), 5);
        instructions_map.put(getString(R.string.evaluation6), 6);

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

        rg1 = (RadioGroup) findViewById(R.id.fma_up_answer_1_1_rg);
        rg2 = (RadioGroup) findViewById(R.id.fma_up_answer_1_2_rg);
        rg3 = (RadioGroup) findViewById(R.id.fma_up_answer_2_1_rg);
        rg4 = (RadioGroup) findViewById(R.id.fma_up_answer_2_2_rg);
        rg5 = (RadioGroup) findViewById(R.id.fma_up_answer_2_3_rg);
        rg6 = (RadioGroup) findViewById(R.id.fma_up_answer_2_4_rg);
        rg7 = (RadioGroup) findViewById(R.id.fma_up_answer_2_5_rg);
        rg8 = (RadioGroup) findViewById(R.id.fma_up_answer_2_6_rg);
        rg9 = (RadioGroup) findViewById(R.id.fma_up_answer_3_1_rg);
        rg10 = (RadioGroup) findViewById(R.id.fma_up_answer_3_2_rg);
        rg11 = (RadioGroup) findViewById(R.id.fma_up_answer_3_3_rg);
        rg12 = (RadioGroup) findViewById(R.id.fma_up_answer_4_1_rg);
        rg13 = (RadioGroup) findViewById(R.id.fma_up_answer_4_2_rg);
        rg14 = (RadioGroup) findViewById(R.id.fma_up_answer_4_3_rg);
        rg15 = (RadioGroup) findViewById(R.id.fma_up_answer_5_1_rg);
        rg16 = (RadioGroup) findViewById(R.id.fma_up_answer_5_2_rg);
        rg17 = (RadioGroup) findViewById(R.id.fma_up_answer_5_3_rg);
        rg18 = (RadioGroup) findViewById(R.id.fma_up_answer_6_1_rg);
        rg19 = (RadioGroup) findViewById(R.id.fma_up_answer_7_1_rg);
        rg20 = (RadioGroup) findViewById(R.id.fma_up_answer_7_2_rg);
        rg21 = (RadioGroup) findViewById(R.id.fma_up_answer_7_3_rg);
        rg22 = (RadioGroup) findViewById(R.id.fma_up_answer_7_4_rg);
        rg23 = (RadioGroup) findViewById(R.id.fma_up_answer_7_5_rg);
        rg24 = (RadioGroup) findViewById(R.id.fma_up_answer_8_1_rg);
        rg25 = (RadioGroup) findViewById(R.id.fma_up_answer_8_2_rg);
        rg26 = (RadioGroup) findViewById(R.id.fma_up_answer_8_3_rg);
        rg27 = (RadioGroup) findViewById(R.id.fma_up_answer_8_4_rg);
        rg28 = (RadioGroup) findViewById(R.id.fma_up_answer_8_5_rg);
        rg29 = (RadioGroup) findViewById(R.id.fma_up_answer_8_6_rg);
        rg30 = (RadioGroup) findViewById(R.id.fma_up_answer_8_7_rg);
        rg31 = (RadioGroup) findViewById(R.id.fma_up_answer_9_1_rg);
        rg32 = (RadioGroup) findViewById(R.id.fma_up_answer_9_2_rg);
        rg33 = (RadioGroup) findViewById(R.id.fma_up_answer_9_3_rg);
        rg34 = (RadioGroup) findViewById(R.id.fma_down_title_1_1_rg);
        rg35 = (RadioGroup) findViewById(R.id.fma_down_title_1_2_rg);
        rg36 = (RadioGroup) findViewById(R.id.fma_down_title_2_1_rg);
        rg37 = (RadioGroup) findViewById(R.id.fma_down_title_2_2_rg);
        rg38 = (RadioGroup) findViewById(R.id.fma_down_title_2_3_rg);
        rg39 = (RadioGroup) findViewById(R.id.fma_down_title_2_4_rg);
        rg40 = (RadioGroup) findViewById(R.id.fma_down_title_2_5_rg);
        rg41 = (RadioGroup) findViewById(R.id.fma_down_title_2_6_rg);
        rg42 = (RadioGroup) findViewById(R.id.fma_down_title_2_7_rg);
        rg43 = (RadioGroup) findViewById(R.id.fma_down_title_3_1_rg);
        rg44 = (RadioGroup) findViewById(R.id.fma_down_title_3_2_rg);
        rg45 = (RadioGroup) findViewById(R.id.fma_down_title_4_1_rg);
        rg46 = (RadioGroup) findViewById(R.id.fma_down_title_4_2_rg);
        rg47 = (RadioGroup) findViewById(R.id.fma_down_title_5_1_rg);
        rg48 = (RadioGroup) findViewById(R.id.fma_down_title_6_1_rg);
        rg49 = (RadioGroup) findViewById(R.id.fma_down_title_6_2_rg);
        rg50 = (RadioGroup) findViewById(R.id.fma_down_title_6_3_rg);

        list_radioGroup = new ArrayList<>();
        list_radioGroup.add(rg1);
        list_radioGroup.add(rg2);
        list_radioGroup.add(rg3);
        list_radioGroup.add(rg4);
        list_radioGroup.add(rg5);
        list_radioGroup.add(rg6);
        list_radioGroup.add(rg7);
        list_radioGroup.add(rg8);
        list_radioGroup.add(rg9);
        list_radioGroup.add(rg10);
        list_radioGroup.add(rg11);
        list_radioGroup.add(rg12);
        list_radioGroup.add(rg13);
        list_radioGroup.add(rg14);
        list_radioGroup.add(rg15);
        list_radioGroup.add(rg16);
        list_radioGroup.add(rg17);
        list_radioGroup.add(rg18);
        list_radioGroup.add(rg19);
        list_radioGroup.add(rg20);
        list_radioGroup.add(rg21);
        list_radioGroup.add(rg22);
        list_radioGroup.add(rg23);
        list_radioGroup.add(rg24);
        list_radioGroup.add(rg25);
        list_radioGroup.add(rg26);
        list_radioGroup.add(rg27);
        list_radioGroup.add(rg28);
        list_radioGroup.add(rg29);
        list_radioGroup.add(rg30);
        list_radioGroup.add(rg31);
        list_radioGroup.add(rg32);
        list_radioGroup.add(rg33);
        list_radioGroup.add(rg34);
        list_radioGroup.add(rg35);
        list_radioGroup.add(rg36);
        list_radioGroup.add(rg37);
        list_radioGroup.add(rg38);
        list_radioGroup.add(rg39);
        list_radioGroup.add(rg40);
        list_radioGroup.add(rg41);
        list_radioGroup.add(rg42);
        list_radioGroup.add(rg43);
        list_radioGroup.add(rg44);
        list_radioGroup.add(rg45);
        list_radioGroup.add(rg46);
        list_radioGroup.add(rg47);
        list_radioGroup.add(rg48);
        list_radioGroup.add(rg49);
        list_radioGroup.add(rg50);


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
        for (int j = 0; j < list_radioGroup.size(); j++) {
            for (int i = 0; i < list_radioGroup.get(j).getChildCount(); i++) {
                View view = list_radioGroup.get(j).getChildAt(i);
                if (view instanceof RadioButton) {
                    if (view.getTag().equals(info.getList_value().get(j))) {
                        ((RadioButton) view).setChecked(true);
                    }
                }
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

                if (dataIsEmpty()) {

                } else {
                    progressBar.setVisibility(View.VISIBLE);

                    param = "patient_info_id=" + patient_info_id
                            + "&user_auth_id=" + user_auth_id
                            + "&menu_id=" + 33
                            + "&evaluation[evaluation_time]=" + date.getText()
                            + "&evaluation[evaluation_time_note]=" + instructions_map.get(spinner_value)

                            + "&patient_fugl_meyer_evaluation[sz_getjfs]=" + myGetTag(findViewById(rg1.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_gstjfs]=" + myGetTag(findViewById(rg2.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_jgjst]=" + myGetTag(findViewById(rg3.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_jgjhs]=" + myGetTag(findViewById(rg4.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_wz]=" + myGetTag(findViewById(rg5.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_wx]=" + myGetTag(findViewById(rg6.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_zgjqq]=" + myGetTag(findViewById(rg7.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_qbxh]=" + myGetTag(findViewById(rg8.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_jgjns]=" + myGetTag(findViewById(rg9.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_zgjsz]=" + myGetTag(findViewById(rg10.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_qbxq]=" + myGetTag(findViewById(rg11.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_scyz]=" + myGetTag(findViewById(rg12.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_jgjqq]=" + myGetTag(findViewById(rg13.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_qbxqxh]=" + myGetTag(findViewById(rg14.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_fl_jgjwz]=" + myGetTag(findViewById(rg15.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_fl_jgjqq]=" + myGetTag(findViewById(rg16.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_fl_zjgjqq]=" + myGetTag(findViewById(rg17.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_zcfs_getjfs]=" + myGetTag(findViewById(rg18.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_w_swbs]=" + myGetTag(findViewById(rg19.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_w_swqq]=" + myGetTag(findViewById(rg20.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_w_swgjbs]=" + myGetTag(findViewById(rg21.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_w_swgjqq]=" + myGetTag(findViewById(rg22.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_w_hzyd]=" + myGetTag(findViewById(rg23.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_s_szlhqq]=" + myGetTag(findViewById(rg24.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_s_szlhsz]=" + myGetTag(findViewById(rg25.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_s_gzzw]=" + myGetTag(findViewById(rg26.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_s_cn]=" + myGetTag(findViewById(rg27.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_s_dn]=" + myGetTag(findViewById(rg28.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_s_yzzzw]=" + myGetTag(findViewById(rg29.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_s_qxzw]=" + myGetTag(findViewById(rg30.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_zc]=" + myGetTag(findViewById(rg31.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_bjbl]=" + myGetTag(findViewById(rg32.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[sz_sd]=" + myGetTag(findViewById(rg33.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[xz_yww_gjfs]=" + myGetTag(findViewById(rg34.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[xz_yww_xjfs]=" + myGetTag(findViewById(rg35.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[xz_yww_kgjqq]=" + myGetTag(findViewById(rg36.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[xz_yww_xgjqq]=" + myGetTag(findViewById(rg37.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[xz_yww_hgjbq]=" + myGetTag(findViewById(rg38.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[xz_yww_kgjsz]=" + myGetTag(findViewById(rg39.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[xz_yww_kgjns]=" + myGetTag(findViewById(rg40.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[xz_yww_xgjsz]=" + myGetTag(findViewById(rg41.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[xz_yww_hgjtq]=" + myGetTag(findViewById(rg42.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[xz_zw_xgjqq]=" + myGetTag(findViewById(rg43.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[xz_zw_hbq]=" + myGetTag(findViewById(rg44.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[xz_zlw_xgjqq]=" + myGetTag(findViewById(rg45.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[xz_zlw_hbq]=" + myGetTag(findViewById(rg46.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[xz_zw_xbqj]=" + myGetTag(findViewById(rg47.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[xz_yww_zc]=" + myGetTag(findViewById(rg48.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[xz_yww_bjbl]=" + myGetTag(findViewById(rg49.getCheckedRadioButtonId()))
                            + "&patient_fugl_meyer_evaluation[xz_yww_sd]=" + myGetTag(findViewById(rg50.getCheckedRadioButtonId()))

                            + "&token=" + NetUrlAddress.token;


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
     * 获取tag值即分数
     * 判断radiogroup是否选中结果
     *
     * @param view
     * @return
     */
    String myGetTag(View view) {
        String s = "";
        try {
            s = view.getTag().toString();
        } catch (Exception e) {
            e.printStackTrace();
            L.d("Fma", "radiobutton没有选中");
        }
        return s;
    }

    /**
     * 获取返回数据
     */
    class MyThread extends Thread {

        public void run() {
            Looper.prepare();
            try {
                if (isAdd) {
                    create_code = TableFmaInfo.doPost(NetUrlAddress.Fma_create_url, param);
                } else {
                    String url = NetUrlAddress.Fma_update_url.replaceAll("aaa", info.getRecord_id() + "");
                    update_code = TableFmaInfo.doPost(url, param);
                }
                if (create_code == 200) {
                    TableFmaInfoActivity.this.finish();
                    ShowToast.Short(getString(R.string.msg_save_success));
                } else if (update_code == 200) {
                    TableFmaInfoActivity.this.finish();
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
     * @param radioGroup
     * @return
     */
    boolean radioButtonIsChecked(RadioGroup radioGroup) {
        boolean flag = false;
        try {
            if (findViewById(radioGroup.getCheckedRadioButtonId()) == null) {
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
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
        } else if (radioButtonIsChecked(rg1)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg2)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg3)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg4)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg5)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg6)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg7)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg8)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg9)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg10)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg11)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg12)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg13)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg14)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg15)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg16)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg17)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg18)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg19)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg20)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg21)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg22)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg23)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg24)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg25)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg26)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg27)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg28)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg29)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg30)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg31)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg32)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg33)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg34)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg35)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg36)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg37)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg38)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg39)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg40)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg41)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg42)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg43)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg44)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg45)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg46)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg47)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg48)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg49)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else if (radioButtonIsChecked(rg50)) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else {
            return false;
        }
    }
}
