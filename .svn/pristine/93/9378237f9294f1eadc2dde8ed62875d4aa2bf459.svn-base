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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import me.zhangls.rilintech.model.TableGmfmInfo;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;

/**
 * Created by YANG on 15/10/12.
 */
public class TableGmfmInfoActivity extends BaseActivity implements View.OnClickListener {

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
            rg43, rg44, rg45, rg46, rg47, rg48, rg49, rg50, rg51, rg52, rg53, rg54, rg55, rg56,
            rg57, rg58, rg59, rg60, rg61, rg62, rg63, rg64, rg65, rg66, rg67, rg68, rg69, rg70,
            rg71, rg72, rg73, rg74, rg75, rg76, rg77, rg78, rg79, rg80, rg81, rg82, rg83, rg84,
            rg85, rg86, rg87, rg88;
    //集合RadioGroup
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
    private TableGmfmInfo info = null;
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
    //分数
    private TextView score_a, score_b, score_c, score_d, score_e, score_all;
    //总分
    private int all = 0;
    //A部分得分
    private int a = 0;
    //B部分得分
    private int b = 0;
    //C部分得分
    private int c = 0;
    //D部分得分
    private int d = 0;
    //E部分得分
    private int e = 0;

    /**
     * 判断当前Activity界面是否加载完成
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_gmfm_info);

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

    private void initView() {
        title = (TextView) findViewById(R.id.layout).findViewById(R.id.title_content);
        title.setText(getString(R.string.gmfm_title));
        back = (ImageView) findViewById(R.id.layout).findViewById(R.id.title_back);
        back.setOnClickListener(this);
        save = (ImageView) findViewById(R.id.layout).findViewById(R.id.title_save);
        save.setOnClickListener(this);

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

        score_a = (TextView) findViewById(R.id.total_scores_a_tv);
        score_b = (TextView) findViewById(R.id.total_scores_b_tv);
        score_c = (TextView) findViewById(R.id.total_scores_c_tv);
        score_d = (TextView) findViewById(R.id.total_scores_d_tv);
        score_e = (TextView) findViewById(R.id.total_scores_e_tv);
        score_all = (TextView) findViewById(R.id.total_scores_all_tv);

        rg1 = (RadioGroup) findViewById(R.id.gmfm_title_1_rg);
        rg2 = (RadioGroup) findViewById(R.id.gmfm_title_2_rg);
        rg3 = (RadioGroup) findViewById(R.id.gmfm_title_3_rg);
        rg4 = (RadioGroup) findViewById(R.id.gmfm_title_4_rg);
        rg5 = (RadioGroup) findViewById(R.id.gmfm_title_5_rg);
        rg6 = (RadioGroup) findViewById(R.id.gmfm_title_6_rg);
        rg7 = (RadioGroup) findViewById(R.id.gmfm_title_7_rg);
        rg8 = (RadioGroup) findViewById(R.id.gmfm_title_8_rg);
        rg9 = (RadioGroup) findViewById(R.id.gmfm_title_9_rg);
        rg10 = (RadioGroup) findViewById(R.id.gmfm_title_10_rg);
        rg11 = (RadioGroup) findViewById(R.id.gmfm_title_11_rg);
        rg12 = (RadioGroup) findViewById(R.id.gmfm_title_12_rg);
        rg13 = (RadioGroup) findViewById(R.id.gmfm_title_13_rg);
        rg14 = (RadioGroup) findViewById(R.id.gmfm_title_14_rg);
        rg15 = (RadioGroup) findViewById(R.id.gmfm_title_15_rg);
        rg16 = (RadioGroup) findViewById(R.id.gmfm_title_16_rg);
        rg17 = (RadioGroup) findViewById(R.id.gmfm_title_17_rg);
        rg18 = (RadioGroup) findViewById(R.id.gmfm_title_18_rg);
        rg19 = (RadioGroup) findViewById(R.id.gmfm_title_19_rg);
        rg20 = (RadioGroup) findViewById(R.id.gmfm_title_20_rg);
        rg21 = (RadioGroup) findViewById(R.id.gmfm_title_21_rg);
        rg22 = (RadioGroup) findViewById(R.id.gmfm_title_22_rg);
        rg23 = (RadioGroup) findViewById(R.id.gmfm_title_23_rg);
        rg24 = (RadioGroup) findViewById(R.id.gmfm_title_24_rg);
        rg25 = (RadioGroup) findViewById(R.id.gmfm_title_25_rg);
        rg26 = (RadioGroup) findViewById(R.id.gmfm_title_26_rg);
        rg27 = (RadioGroup) findViewById(R.id.gmfm_title_27_rg);
        rg28 = (RadioGroup) findViewById(R.id.gmfm_title_28_rg);
        rg29 = (RadioGroup) findViewById(R.id.gmfm_title_29_rg);
        rg30 = (RadioGroup) findViewById(R.id.gmfm_title_30_rg);
        rg31 = (RadioGroup) findViewById(R.id.gmfm_title_31_rg);
        rg32 = (RadioGroup) findViewById(R.id.gmfm_title_32_rg);
        rg33 = (RadioGroup) findViewById(R.id.gmfm_title_33_rg);
        rg34 = (RadioGroup) findViewById(R.id.gmfm_title_34_rg);
        rg35 = (RadioGroup) findViewById(R.id.gmfm_title_35_rg);
        rg36 = (RadioGroup) findViewById(R.id.gmfm_title_36_rg);
        rg37 = (RadioGroup) findViewById(R.id.gmfm_title_37_rg);
        rg38 = (RadioGroup) findViewById(R.id.gmfm_title_38_rg);
        rg39 = (RadioGroup) findViewById(R.id.gmfm_title_39_rg);
        rg40 = (RadioGroup) findViewById(R.id.gmfm_title_40_rg);
        rg41 = (RadioGroup) findViewById(R.id.gmfm_title_41_rg);
        rg42 = (RadioGroup) findViewById(R.id.gmfm_title_42_rg);
        rg43 = (RadioGroup) findViewById(R.id.gmfm_title_43_rg);
        rg44 = (RadioGroup) findViewById(R.id.gmfm_title_44_rg);
        rg45 = (RadioGroup) findViewById(R.id.gmfm_title_45_rg);
        rg46 = (RadioGroup) findViewById(R.id.gmfm_title_46_rg);
        rg47 = (RadioGroup) findViewById(R.id.gmfm_title_47_rg);
        rg48 = (RadioGroup) findViewById(R.id.gmfm_title_48_rg);
        rg49 = (RadioGroup) findViewById(R.id.gmfm_title_49_rg);
        rg50 = (RadioGroup) findViewById(R.id.gmfm_title_50_rg);
        rg51 = (RadioGroup) findViewById(R.id.gmfm_title_51_rg);
        rg52 = (RadioGroup) findViewById(R.id.gmfm_title_52_rg);
        rg53 = (RadioGroup) findViewById(R.id.gmfm_title_53_rg);
        rg54 = (RadioGroup) findViewById(R.id.gmfm_title_54_rg);
        rg55 = (RadioGroup) findViewById(R.id.gmfm_title_55_rg);
        rg56 = (RadioGroup) findViewById(R.id.gmfm_title_56_rg);
        rg57 = (RadioGroup) findViewById(R.id.gmfm_title_57_rg);
        rg58 = (RadioGroup) findViewById(R.id.gmfm_title_58_rg);
        rg59 = (RadioGroup) findViewById(R.id.gmfm_title_59_rg);
        rg60 = (RadioGroup) findViewById(R.id.gmfm_title_60_rg);
        rg61 = (RadioGroup) findViewById(R.id.gmfm_title_61_rg);
        rg62 = (RadioGroup) findViewById(R.id.gmfm_title_62_rg);
        rg63 = (RadioGroup) findViewById(R.id.gmfm_title_63_rg);
        rg64 = (RadioGroup) findViewById(R.id.gmfm_title_64_rg);
        rg65 = (RadioGroup) findViewById(R.id.gmfm_title_65_rg);
        rg66 = (RadioGroup) findViewById(R.id.gmfm_title_66_rg);
        rg67 = (RadioGroup) findViewById(R.id.gmfm_title_67_rg);
        rg68 = (RadioGroup) findViewById(R.id.gmfm_title_68_rg);
        rg69 = (RadioGroup) findViewById(R.id.gmfm_title_69_rg);
        rg70 = (RadioGroup) findViewById(R.id.gmfm_title_70_rg);
        rg71 = (RadioGroup) findViewById(R.id.gmfm_title_71_rg);
        rg72 = (RadioGroup) findViewById(R.id.gmfm_title_72_rg);
        rg73 = (RadioGroup) findViewById(R.id.gmfm_title_73_rg);
        rg74 = (RadioGroup) findViewById(R.id.gmfm_title_74_rg);
        rg75 = (RadioGroup) findViewById(R.id.gmfm_title_75_rg);
        rg76 = (RadioGroup) findViewById(R.id.gmfm_title_76_rg);
        rg77 = (RadioGroup) findViewById(R.id.gmfm_title_77_rg);
        rg78 = (RadioGroup) findViewById(R.id.gmfm_title_78_rg);
        rg79 = (RadioGroup) findViewById(R.id.gmfm_title_79_rg);
        rg80 = (RadioGroup) findViewById(R.id.gmfm_title_80_rg);
        rg81 = (RadioGroup) findViewById(R.id.gmfm_title_81_rg);
        rg82 = (RadioGroup) findViewById(R.id.gmfm_title_82_rg);
        rg83 = (RadioGroup) findViewById(R.id.gmfm_title_83_rg);
        rg84 = (RadioGroup) findViewById(R.id.gmfm_title_84_rg);
        rg85 = (RadioGroup) findViewById(R.id.gmfm_title_85_rg);
        rg86 = (RadioGroup) findViewById(R.id.gmfm_title_86_rg);
        rg87 = (RadioGroup) findViewById(R.id.gmfm_title_87_rg);
        rg88 = (RadioGroup) findViewById(R.id.gmfm_title_88_rg);

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
        list_radioGroup.add(rg51);
        list_radioGroup.add(rg52);
        list_radioGroup.add(rg53);
        list_radioGroup.add(rg54);
        list_radioGroup.add(rg55);
        list_radioGroup.add(rg56);
        list_radioGroup.add(rg57);
        list_radioGroup.add(rg58);
        list_radioGroup.add(rg59);
        list_radioGroup.add(rg60);
        list_radioGroup.add(rg61);
        list_radioGroup.add(rg62);
        list_radioGroup.add(rg63);
        list_radioGroup.add(rg64);
        list_radioGroup.add(rg65);
        list_radioGroup.add(rg66);
        list_radioGroup.add(rg67);
        list_radioGroup.add(rg68);
        list_radioGroup.add(rg69);
        list_radioGroup.add(rg70);
        list_radioGroup.add(rg71);
        list_radioGroup.add(rg72);
        list_radioGroup.add(rg73);
        list_radioGroup.add(rg74);
        list_radioGroup.add(rg75);
        list_radioGroup.add(rg76);
        list_radioGroup.add(rg77);
        list_radioGroup.add(rg78);
        list_radioGroup.add(rg79);
        list_radioGroup.add(rg80);
        list_radioGroup.add(rg81);
        list_radioGroup.add(rg82);
        list_radioGroup.add(rg83);
        list_radioGroup.add(rg84);
        list_radioGroup.add(rg85);
        list_radioGroup.add(rg86);
        list_radioGroup.add(rg87);
        list_radioGroup.add(rg88);

        //循环RadioGroup集合，监听每个radioGroup；
        for (RadioGroup rg : list_radioGroup) {
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    //全部归零
                    all = 0;
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                    //再次遍历RadioGroup集合，计算每部分的总分
                    for (int i = 0; i < list_radioGroup.size(); i++) {
                        all += myGetScore(findViewById(list_radioGroup.get(i).getCheckedRadioButtonId()));
                        if (i < 17) {
                            a += myGetScore(findViewById(list_radioGroup.get(i).getCheckedRadioButtonId()));
                        }
                        if (17 <= i && i < 37) {
                            b += myGetScore(findViewById(list_radioGroup.get(i).getCheckedRadioButtonId()));
                        }
                        if (37 <= i && i < 51) {
                            c += myGetScore(findViewById(list_radioGroup.get(i).getCheckedRadioButtonId()));
                        }
                        if (51 <= i && i < 64) {
                            d += myGetScore(findViewById(list_radioGroup.get(i).getCheckedRadioButtonId()));
                        }
                        if (64 <= i && i < 88) {
                            e += myGetScore(findViewById(list_radioGroup.get(i).getCheckedRadioButtonId()));
                        }
                    }
                    score_all.setText(all + "");
                    score_a.setText(a + "");
                    score_b.setText(b + "");
                    score_c.setText(c + "");
                    score_d.setText(d + "");
                    score_e.setText(e + "");

                }
            });
        }

        if (isAdd == false) {
            if (info != null) {
                setData();
            }
        }
    }

    /**
     * 获取tag值即分数
     * 判断RadioGroup否选中结果
     * 没有选中则保存值为0
     *
     * @param view
     * @return
     */
    int myGetScore(View view) {
        int s = 0;
        try {
            if (null == view) {

            } else {
                s = Integer.parseInt(view.getTag().toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            L.d("Fma", "radiobutton没有选中");
        }
        return s;
    }


    /**
     * 给所有控件赋值
     */
    private void setData() {

        try {
            date.setText(info.getDate().substring(0, 10) + " " + info.getDate().substring(11, 16));
        } catch (Exception e) {
            L.d("gmfm", info.getInstructions() + "表没有添加时间");
            date.setText("");
        }

        for (int i = 0; i < list_instructions_new.size(); i++) {
            if ((list_instructions_new.get(i)).equals(info.getInstructions())) {
                spinner_instructions.setSelection(i);
            }
        }
        //遍历所有的RadioGroup
        for (int j = 0; j < list_radioGroup.size(); j++) {
            ///遍历这个RadioGroup下所有的View
            for (int i = 0; i < list_radioGroup.get(j).getChildCount(); i++) {
                //得到当前遍历到得View
                View view = list_radioGroup.get(j).getChildAt(i);
                //如果View是RadioGroup
                if (view instanceof RadioButton) {
                    //并且它的Tag值与给定的分值相等
                    if (view.getTag().equals(info.getList_scores().get(j).toString())) {
                        //则选中这个View
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
                            + "&menu_id=" + 172
                            + "&evaluation[evaluation_time]=" + date.getText()
                            + "&evaluation[evaluation_time_note]=" + instructions_map.get(spinner_value)

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
                            + "&in_sum[12]=" + "t"
                            + "&in_sum[13]=" + "t"
                            + "&in_sum[14]=" + "t"
                            + "&in_sum[15]=" + "t"
                            + "&in_sum[16]=" + "t"
                            + "&in_sum[17]=" + "t"
                            + "&in_sum[18]=" + "t"
                            + "&in_sum[19]=" + "t"
                            + "&in_sum[20]=" + "t"
                            + "&in_sum[21]=" + "t"
                            + "&in_sum[22]=" + "t"
                            + "&in_sum[23]=" + "t"
                            + "&in_sum[24]=" + "t"
                            + "&in_sum[25]=" + "t"
                            + "&in_sum[26]=" + "t"
                            + "&in_sum[27]=" + "t"
                            + "&in_sum[28]=" + "t"
                            + "&in_sum[29]=" + "t"
                            + "&in_sum[30]=" + "t"
                            + "&in_sum[31]=" + "t"
                            + "&in_sum[32]=" + "t"
                            + "&in_sum[33]=" + "t"
                            + "&in_sum[34]=" + "t"
                            + "&in_sum[35]=" + "t"
                            + "&in_sum[36]=" + "t"
                            + "&in_sum[37]=" + "t"
                            + "&in_sum[38]=" + "t"
                            + "&in_sum[39]=" + "t"
                            + "&in_sum[40]=" + "t"
                            + "&in_sum[41]=" + "t"
                            + "&in_sum[42]=" + "t"
                            + "&in_sum[43]=" + "t"
                            + "&in_sum[44]=" + "t"
                            + "&in_sum[45]=" + "t"
                            + "&in_sum[46]=" + "t"
                            + "&in_sum[47]=" + "t"
                            + "&in_sum[48]=" + "t"
                            + "&in_sum[49]=" + "t"
                            + "&in_sum[50]=" + "t"
                            + "&in_sum[51]=" + "t"
                            + "&in_sum[52]=" + "t"
                            + "&in_sum[53]=" + "t"
                            + "&in_sum[54]=" + "t"
                            + "&in_sum[55]=" + "t"
                            + "&in_sum[56]=" + "t"
                            + "&in_sum[57]=" + "t"
                            + "&in_sum[58]=" + "t"
                            + "&in_sum[59]=" + "t"
                            + "&in_sum[60]=" + "t"
                            + "&in_sum[61]=" + "t"
                            + "&in_sum[62]=" + "t"
                            + "&in_sum[63]=" + "t"
                            + "&in_sum[64]=" + "t"
                            + "&in_sum[65]=" + "t"
                            + "&in_sum[66]=" + "t"
                            + "&in_sum[67]=" + "t"
                            + "&in_sum[68]=" + "t"
                            + "&in_sum[69]=" + "t"
                            + "&in_sum[70]=" + "t"
                            + "&in_sum[71]=" + "t"
                            + "&in_sum[72]=" + "t"
                            + "&in_sum[73]=" + "t"
                            + "&in_sum[74]=" + "t"
                            + "&in_sum[75]=" + "t"
                            + "&in_sum[76]=" + "t"
                            + "&in_sum[77]=" + "t"
                            + "&in_sum[78]=" + "t"
                            + "&in_sum[79]=" + "t"
                            + "&in_sum[80]=" + "t"
                            + "&in_sum[81]=" + "t"
                            + "&in_sum[82]=" + "t"
                            + "&in_sum[83]=" + "t"
                            + "&in_sum[84]=" + "t"
                            + "&in_sum[85]=" + "t"
                            + "&in_sum[86]=" + "t"
                            + "&in_sum[87]=" + "t"


                            + "&note[0]=" + "A:卧位&翻身"
                            + "&note[1]=" + "A:卧位&翻身"
                            + "&note[2]=" + "A:卧位&翻身"
                            + "&note[3]=" + "A:卧位&翻身"
                            + "&note[4]=" + "A:卧位&翻身"
                            + "&note[5]=" + "A:卧位&翻身"
                            + "&note[6]=" + "A:卧位&翻身"
                            + "&note[7]=" + "A:卧位&翻身"
                            + "&note[8]=" + "A:卧位&翻身"
                            + "&note[9]=" + "A:卧位&翻身"
                            + "&note[10]=" + "A:卧位&翻身"
                            + "&note[11]=" + "A:卧位&翻身"
                            + "&note[12]=" + "A:卧位&翻身"
                            + "&note[13]=" + "A:卧位&翻身"
                            + "&note[14]=" + "A:卧位&翻身"
                            + "&note[15]=" + "A:卧位&翻身"
                            + "&note[16]=" + "A:卧位&翻身"
                            + "&note[17]=" + "B:坐位"
                            + "&note[18]=" + "B:坐位"
                            + "&note[19]=" + "B:坐位"
                            + "&note[20]=" + "B:坐位"
                            + "&note[21]=" + "B:坐位"
                            + "&note[22]=" + "B:坐位"
                            + "&note[23]=" + "B:坐位"
                            + "&note[24]=" + "B:坐位"
                            + "&note[25]=" + "B:坐位"
                            + "&note[26]=" + "B:坐位"
                            + "&note[27]=" + "B:坐位"
                            + "&note[28]=" + "B:坐位"
                            + "&note[29]=" + "B:坐位"
                            + "&note[30]=" + "B:坐位"
                            + "&note[31]=" + "B:坐位"
                            + "&note[32]=" + "B:坐位"
                            + "&note[33]=" + "B:坐位"
                            + "&note[34]=" + "B:坐位"
                            + "&note[35]=" + "B:坐位"
                            + "&note[36]=" + "B:坐位"
                            + "&note[37]=" + "C:爬行&跪位"
                            + "&note[38]=" + "C:爬行&跪位"
                            + "&note[39]=" + "C:爬行&跪位"
                            + "&note[40]=" + "C:爬行&跪位"
                            + "&note[41]=" + "C:爬行&跪位"
                            + "&note[42]=" + "C:爬行&跪位"
                            + "&note[43]=" + "C:爬行&跪位"
                            + "&note[44]=" + "C:爬行&跪位"
                            + "&note[45]=" + "C:爬行&跪位"
                            + "&note[46]=" + "C:爬行&跪位"
                            + "&note[47]=" + "C:爬行&跪位"
                            + "&note[48]=" + "C:爬行&跪位"
                            + "&note[49]=" + "C:爬行&跪位"
                            + "&note[50]=" + "C:爬行&跪位"
                            + "&note[51]=" + "D:站立"
                            + "&note[52]=" + "D:站立"
                            + "&note[53]=" + "D:站立"
                            + "&note[54]=" + "D:站立"
                            + "&note[55]=" + "D:站立"
                            + "&note[56]=" + "D:站立"
                            + "&note[57]=" + "D:站立"
                            + "&note[58]=" + "D:站立"
                            + "&note[59]=" + "D:站立"
                            + "&note[60]=" + "D:站立"
                            + "&note[61]=" + "D:站立"
                            + "&note[62]=" + "D:站立"
                            + "&note[63]=" + "D:站立"
                            + "&note[64]=" + "E:行走、跑步&跳跃"
                            + "&note[65]=" + "E:行走、跑步&跳跃"
                            + "&note[66]=" + "E:行走、跑步&跳跃"
                            + "&note[67]=" + "E:行走、跑步&跳跃"
                            + "&note[68]=" + "E:行走、跑步&跳跃"
                            + "&note[69]=" + "E:行走、跑步&跳跃"
                            + "&note[70]=" + "E:行走、跑步&跳跃"
                            + "&note[71]=" + "E:行走、跑步&跳跃"
                            + "&note[72]=" + "E:行走、跑步&跳跃"
                            + "&note[73]=" + "E:行走、跑步&跳跃"
                            + "&note[74]=" + "E:行走、跑步&跳跃"
                            + "&note[75]=" + "E:行走、跑步&跳跃"
                            + "&note[76]=" + "E:行走、跑步&跳跃"
                            + "&note[77]=" + "E:行走、跑步&跳跃"
                            + "&note[78]=" + "E:行走、跑步&跳跃"
                            + "&note[79]=" + "E:行走、跑步&跳跃"
                            + "&note[80]=" + "E:行走、跑步&跳跃"
                            + "&note[81]=" + "E:行走、跑步&跳跃"
                            + "&note[82]=" + "E:行走、跑步&跳跃"
                            + "&note[83]=" + "E:行走、跑步&跳跃"
                            + "&note[84]=" + "E:行走、跑步&跳跃"
                            + "&note[85]=" + "E:行走、跑步&跳跃"
                            + "&note[86]=" + "E:行走、跑步&跳跃"
                            + "&note[87]=" + "E:行走、跑步&跳跃"

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
                            + "&catalog[10]=" + ""
                            + "&catalog[11]=" + ""
                            + "&catalog[12]=" + ""
                            + "&catalog[13]=" + ""
                            + "&catalog[14]=" + ""
                            + "&catalog[15]=" + ""
                            + "&catalog[16]=" + ""
                            + "&catalog[17]=" + ""
                            + "&catalog[18]=" + ""
                            + "&catalog[19]=" + ""
                            + "&catalog[20]=" + ""
                            + "&catalog[21]=" + ""
                            + "&catalog[22]=" + ""
                            + "&catalog[23]=" + ""
                            + "&catalog[24]=" + ""
                            + "&catalog[25]=" + ""
                            + "&catalog[26]=" + ""
                            + "&catalog[27]=" + ""
                            + "&catalog[28]=" + ""
                            + "&catalog[29]=" + ""
                            + "&catalog[30]=" + ""
                            + "&catalog[31]=" + ""
                            + "&catalog[32]=" + ""
                            + "&catalog[33]=" + ""
                            + "&catalog[34]=" + ""
                            + "&catalog[35]=" + ""
                            + "&catalog[36]=" + ""
                            + "&catalog[37]=" + ""
                            + "&catalog[38]=" + ""
                            + "&catalog[39]=" + ""
                            + "&catalog[40]=" + ""
                            + "&catalog[41]=" + ""
                            + "&catalog[42]=" + ""
                            + "&catalog[43]=" + ""
                            + "&catalog[44]=" + ""
                            + "&catalog[45]=" + ""
                            + "&catalog[46]=" + ""
                            + "&catalog[47]=" + ""
                            + "&catalog[48]=" + ""
                            + "&catalog[49]=" + ""
                            + "&catalog[50]=" + ""
                            + "&catalog[51]=" + ""
                            + "&catalog[52]=" + ""
                            + "&catalog[53]=" + ""
                            + "&catalog[54]=" + ""
                            + "&catalog[55]=" + ""
                            + "&catalog[56]=" + ""
                            + "&catalog[57]=" + ""
                            + "&catalog[58]=" + ""
                            + "&catalog[59]=" + ""
                            + "&catalog[60]=" + ""
                            + "&catalog[61]=" + ""
                            + "&catalog[62]=" + ""
                            + "&catalog[63]=" + ""
                            + "&catalog[64]=" + ""
                            + "&catalog[65]=" + ""
                            + "&catalog[66]=" + ""
                            + "&catalog[67]=" + ""
                            + "&catalog[68]=" + ""
                            + "&catalog[69]=" + ""
                            + "&catalog[70]=" + ""
                            + "&catalog[71]=" + ""
                            + "&catalog[72]=" + ""
                            + "&catalog[73]=" + ""
                            + "&catalog[74]=" + ""
                            + "&catalog[75]=" + ""
                            + "&catalog[76]=" + ""
                            + "&catalog[77]=" + ""
                            + "&catalog[78]=" + ""
                            + "&catalog[79]=" + ""
                            + "&catalog[80]=" + ""
                            + "&catalog[81]=" + ""
                            + "&catalog[82]=" + ""
                            + "&catalog[83]=" + ""
                            + "&catalog[84]=" + ""
                            + "&catalog[85]=" + ""
                            + "&catalog[86]=" + ""
                            + "&catalog[87]=" + ""

                            + "&explain[0]=" + getString(R.string.gmfm_title_1)
                            + "&explain[1]=" + getString(R.string.gmfm_title_2)
                            + "&explain[2]=" + getString(R.string.gmfm_title_3)
                            + "&explain[3]=" + getString(R.string.gmfm_title_4)
                            + "&explain[4]=" + getString(R.string.gmfm_title_5)
                            + "&explain[5]=" + getString(R.string.gmfm_title_6)
                            + "&explain[6]=" + getString(R.string.gmfm_title_7)
                            + "&explain[7]=" + getString(R.string.gmfm_title_8)
                            + "&explain[8]=" + getString(R.string.gmfm_title_9)
                            + "&explain[9]=" + getString(R.string.gmfm_title_10)
                            + "&explain[10]=" + getString(R.string.gmfm_title_11)
                            + "&explain[11]=" + getString(R.string.gmfm_title_12)
                            + "&explain[12]=" + getString(R.string.gmfm_title_13)
                            + "&explain[13]=" + getString(R.string.gmfm_title_14)
                            + "&explain[14]=" + getString(R.string.gmfm_title_15)
                            + "&explain[15]=" + getString(R.string.gmfm_title_16)
                            + "&explain[16]=" + getString(R.string.gmfm_title_17)
                            + "&explain[17]=" + getString(R.string.gmfm_title_18)
                            + "&explain[18]=" + getString(R.string.gmfm_title_19)
                            + "&explain[19]=" + getString(R.string.gmfm_title_20)
                            + "&explain[20]=" + getString(R.string.gmfm_title_21)
                            + "&explain[21]=" + getString(R.string.gmfm_title_22)
                            + "&explain[22]=" + getString(R.string.gmfm_title_23)
                            + "&explain[23]=" + getString(R.string.gmfm_title_24)
                            + "&explain[24]=" + getString(R.string.gmfm_title_25)
                            + "&explain[25]=" + getString(R.string.gmfm_title_26)
                            + "&explain[26]=" + getString(R.string.gmfm_title_27)
                            + "&explain[27]=" + getString(R.string.gmfm_title_28)
                            + "&explain[28]=" + getString(R.string.gmfm_title_29)
                            + "&explain[29]=" + getString(R.string.gmfm_title_30)
                            + "&explain[30]=" + getString(R.string.gmfm_title_31)
                            + "&explain[31]=" + getString(R.string.gmfm_title_32)
                            + "&explain[32]=" + getString(R.string.gmfm_title_33)
                            + "&explain[33]=" + getString(R.string.gmfm_title_34)
                            + "&explain[34]=" + getString(R.string.gmfm_title_35)
                            + "&explain[35]=" + getString(R.string.gmfm_title_36)
                            + "&explain[36]=" + getString(R.string.gmfm_title_37)
                            + "&explain[37]=" + getString(R.string.gmfm_title_38)
                            + "&explain[38]=" + getString(R.string.gmfm_title_39)
                            + "&explain[39]=" + getString(R.string.gmfm_title_40)
                            + "&explain[40]=" + getString(R.string.gmfm_title_41)
                            + "&explain[41]=" + getString(R.string.gmfm_title_42)
                            + "&explain[42]=" + getString(R.string.gmfm_title_43)
                            + "&explain[43]=" + getString(R.string.gmfm_title_44)
                            + "&explain[44]=" + getString(R.string.gmfm_title_45)
                            + "&explain[45]=" + getString(R.string.gmfm_title_46)
                            + "&explain[46]=" + getString(R.string.gmfm_title_47)
                            + "&explain[47]=" + getString(R.string.gmfm_title_48)
                            + "&explain[48]=" + getString(R.string.gmfm_title_49)
                            + "&explain[49]=" + getString(R.string.gmfm_title_50)
                            + "&explain[50]=" + getString(R.string.gmfm_title_51)
                            + "&explain[51]=" + getString(R.string.gmfm_title_52)
                            + "&explain[52]=" + getString(R.string.gmfm_title_53)
                            + "&explain[53]=" + getString(R.string.gmfm_title_54)
                            + "&explain[54]=" + getString(R.string.gmfm_title_55)
                            + "&explain[55]=" + getString(R.string.gmfm_title_56)
                            + "&explain[56]=" + getString(R.string.gmfm_title_57)
                            + "&explain[57]=" + getString(R.string.gmfm_title_58)
                            + "&explain[58]=" + getString(R.string.gmfm_title_59)
                            + "&explain[59]=" + getString(R.string.gmfm_title_60)
                            + "&explain[60]=" + getString(R.string.gmfm_title_61)
                            + "&explain[61]=" + getString(R.string.gmfm_title_62)
                            + "&explain[62]=" + getString(R.string.gmfm_title_63)
                            + "&explain[63]=" + getString(R.string.gmfm_title_64)
                            + "&explain[64]=" + getString(R.string.gmfm_title_65)
                            + "&explain[65]=" + getString(R.string.gmfm_title_66)
                            + "&explain[66]=" + getString(R.string.gmfm_title_67)
                            + "&explain[67]=" + getString(R.string.gmfm_title_68)
                            + "&explain[68]=" + getString(R.string.gmfm_title_69)
                            + "&explain[69]=" + getString(R.string.gmfm_title_70)
                            + "&explain[70]=" + getString(R.string.gmfm_title_71)
                            + "&explain[71]=" + getString(R.string.gmfm_title_72)
                            + "&explain[72]=" + getString(R.string.gmfm_title_73)
                            + "&explain[73]=" + getString(R.string.gmfm_title_74)
                            + "&explain[74]=" + getString(R.string.gmfm_title_75)
                            + "&explain[75]=" + getString(R.string.gmfm_title_76)
                            + "&explain[76]=" + getString(R.string.gmfm_title_77)
                            + "&explain[77]=" + getString(R.string.gmfm_title_78)
                            + "&explain[78]=" + getString(R.string.gmfm_title_79)
                            + "&explain[79]=" + getString(R.string.gmfm_title_80)
                            + "&explain[80]=" + getString(R.string.gmfm_title_81)
                            + "&explain[81]=" + getString(R.string.gmfm_title_82)
                            + "&explain[82]=" + getString(R.string.gmfm_title_83)
                            + "&explain[83]=" + getString(R.string.gmfm_title_84)
                            + "&explain[84]=" + getString(R.string.gmfm_title_85)
                            + "&explain[85]=" + getString(R.string.gmfm_title_86)
                            + "&explain[86]=" + getString(R.string.gmfm_title_87)
                            + "&explain[87]=" + getString(R.string.gmfm_title_88)

                            + "&sum[0]=" + myGetTag(findViewById(rg1.getCheckedRadioButtonId()))
                            + "&sum[1]=" + myGetTag(findViewById(rg2.getCheckedRadioButtonId()))
                            + "&sum[2]=" + myGetTag(findViewById(rg3.getCheckedRadioButtonId()))
                            + "&sum[3]=" + myGetTag(findViewById(rg4.getCheckedRadioButtonId()))
                            + "&sum[4]=" + myGetTag(findViewById(rg5.getCheckedRadioButtonId()))
                            + "&sum[5]=" + myGetTag(findViewById(rg6.getCheckedRadioButtonId()))
                            + "&sum[6]=" + myGetTag(findViewById(rg7.getCheckedRadioButtonId()))
                            + "&sum[7]=" + myGetTag(findViewById(rg8.getCheckedRadioButtonId()))
                            + "&sum[8]=" + myGetTag(findViewById(rg9.getCheckedRadioButtonId()))
                            + "&sum[9]=" + myGetTag(findViewById(rg10.getCheckedRadioButtonId()))
                            + "&sum[10]=" + myGetTag(findViewById(rg11.getCheckedRadioButtonId()))
                            + "&sum[11]=" + myGetTag(findViewById(rg12.getCheckedRadioButtonId()))
                            + "&sum[12]=" + myGetTag(findViewById(rg13.getCheckedRadioButtonId()))
                            + "&sum[13]=" + myGetTag(findViewById(rg14.getCheckedRadioButtonId()))
                            + "&sum[14]=" + myGetTag(findViewById(rg15.getCheckedRadioButtonId()))
                            + "&sum[15]=" + myGetTag(findViewById(rg16.getCheckedRadioButtonId()))
                            + "&sum[16]=" + myGetTag(findViewById(rg17.getCheckedRadioButtonId()))
                            + "&sum[17]=" + myGetTag(findViewById(rg18.getCheckedRadioButtonId()))
                            + "&sum[18]=" + myGetTag(findViewById(rg19.getCheckedRadioButtonId()))
                            + "&sum[19]=" + myGetTag(findViewById(rg20.getCheckedRadioButtonId()))
                            + "&sum[20]=" + myGetTag(findViewById(rg21.getCheckedRadioButtonId()))
                            + "&sum[21]=" + myGetTag(findViewById(rg22.getCheckedRadioButtonId()))
                            + "&sum[22]=" + myGetTag(findViewById(rg23.getCheckedRadioButtonId()))
                            + "&sum[23]=" + myGetTag(findViewById(rg24.getCheckedRadioButtonId()))
                            + "&sum[24]=" + myGetTag(findViewById(rg25.getCheckedRadioButtonId()))
                            + "&sum[25]=" + myGetTag(findViewById(rg26.getCheckedRadioButtonId()))
                            + "&sum[26]=" + myGetTag(findViewById(rg27.getCheckedRadioButtonId()))
                            + "&sum[27]=" + myGetTag(findViewById(rg28.getCheckedRadioButtonId()))
                            + "&sum[28]=" + myGetTag(findViewById(rg29.getCheckedRadioButtonId()))
                            + "&sum[29]=" + myGetTag(findViewById(rg30.getCheckedRadioButtonId()))
                            + "&sum[30]=" + myGetTag(findViewById(rg31.getCheckedRadioButtonId()))
                            + "&sum[31]=" + myGetTag(findViewById(rg32.getCheckedRadioButtonId()))
                            + "&sum[32]=" + myGetTag(findViewById(rg33.getCheckedRadioButtonId()))
                            + "&sum[33]=" + myGetTag(findViewById(rg34.getCheckedRadioButtonId()))
                            + "&sum[34]=" + myGetTag(findViewById(rg35.getCheckedRadioButtonId()))
                            + "&sum[35]=" + myGetTag(findViewById(rg36.getCheckedRadioButtonId()))
                            + "&sum[36]=" + myGetTag(findViewById(rg37.getCheckedRadioButtonId()))
                            + "&sum[37]=" + myGetTag(findViewById(rg38.getCheckedRadioButtonId()))
                            + "&sum[38]=" + myGetTag(findViewById(rg39.getCheckedRadioButtonId()))
                            + "&sum[39]=" + myGetTag(findViewById(rg40.getCheckedRadioButtonId()))
                            + "&sum[40]=" + myGetTag(findViewById(rg41.getCheckedRadioButtonId()))
                            + "&sum[41]=" + myGetTag(findViewById(rg42.getCheckedRadioButtonId()))
                            + "&sum[42]=" + myGetTag(findViewById(rg43.getCheckedRadioButtonId()))
                            + "&sum[43]=" + myGetTag(findViewById(rg44.getCheckedRadioButtonId()))
                            + "&sum[44]=" + myGetTag(findViewById(rg45.getCheckedRadioButtonId()))
                            + "&sum[45]=" + myGetTag(findViewById(rg46.getCheckedRadioButtonId()))
                            + "&sum[46]=" + myGetTag(findViewById(rg47.getCheckedRadioButtonId()))
                            + "&sum[47]=" + myGetTag(findViewById(rg48.getCheckedRadioButtonId()))
                            + "&sum[48]=" + myGetTag(findViewById(rg49.getCheckedRadioButtonId()))
                            + "&sum[49]=" + myGetTag(findViewById(rg50.getCheckedRadioButtonId()))
                            + "&sum[50]=" + myGetTag(findViewById(rg51.getCheckedRadioButtonId()))
                            + "&sum[51]=" + myGetTag(findViewById(rg52.getCheckedRadioButtonId()))
                            + "&sum[52]=" + myGetTag(findViewById(rg53.getCheckedRadioButtonId()))
                            + "&sum[53]=" + myGetTag(findViewById(rg54.getCheckedRadioButtonId()))
                            + "&sum[54]=" + myGetTag(findViewById(rg55.getCheckedRadioButtonId()))
                            + "&sum[55]=" + myGetTag(findViewById(rg56.getCheckedRadioButtonId()))
                            + "&sum[56]=" + myGetTag(findViewById(rg57.getCheckedRadioButtonId()))
                            + "&sum[57]=" + myGetTag(findViewById(rg58.getCheckedRadioButtonId()))
                            + "&sum[58]=" + myGetTag(findViewById(rg59.getCheckedRadioButtonId()))
                            + "&sum[59]=" + myGetTag(findViewById(rg60.getCheckedRadioButtonId()))
                            + "&sum[60]=" + myGetTag(findViewById(rg61.getCheckedRadioButtonId()))
                            + "&sum[61]=" + myGetTag(findViewById(rg62.getCheckedRadioButtonId()))
                            + "&sum[62]=" + myGetTag(findViewById(rg63.getCheckedRadioButtonId()))
                            + "&sum[63]=" + myGetTag(findViewById(rg64.getCheckedRadioButtonId()))
                            + "&sum[64]=" + myGetTag(findViewById(rg65.getCheckedRadioButtonId()))
                            + "&sum[65]=" + myGetTag(findViewById(rg66.getCheckedRadioButtonId()))
                            + "&sum[66]=" + myGetTag(findViewById(rg67.getCheckedRadioButtonId()))
                            + "&sum[67]=" + myGetTag(findViewById(rg68.getCheckedRadioButtonId()))
                            + "&sum[68]=" + myGetTag(findViewById(rg69.getCheckedRadioButtonId()))
                            + "&sum[69]=" + myGetTag(findViewById(rg70.getCheckedRadioButtonId()))
                            + "&sum[70]=" + myGetTag(findViewById(rg71.getCheckedRadioButtonId()))
                            + "&sum[71]=" + myGetTag(findViewById(rg72.getCheckedRadioButtonId()))
                            + "&sum[72]=" + myGetTag(findViewById(rg73.getCheckedRadioButtonId()))
                            + "&sum[73]=" + myGetTag(findViewById(rg74.getCheckedRadioButtonId()))
                            + "&sum[74]=" + myGetTag(findViewById(rg75.getCheckedRadioButtonId()))
                            + "&sum[75]=" + myGetTag(findViewById(rg76.getCheckedRadioButtonId()))
                            + "&sum[76]=" + myGetTag(findViewById(rg77.getCheckedRadioButtonId()))
                            + "&sum[77]=" + myGetTag(findViewById(rg78.getCheckedRadioButtonId()))
                            + "&sum[78]=" + myGetTag(findViewById(rg79.getCheckedRadioButtonId()))
                            + "&sum[79]=" + myGetTag(findViewById(rg80.getCheckedRadioButtonId()))
                            + "&sum[80]=" + myGetTag(findViewById(rg81.getCheckedRadioButtonId()))
                            + "&sum[81]=" + myGetTag(findViewById(rg82.getCheckedRadioButtonId()))
                            + "&sum[82]=" + myGetTag(findViewById(rg83.getCheckedRadioButtonId()))
                            + "&sum[83]=" + myGetTag(findViewById(rg84.getCheckedRadioButtonId()))
                            + "&sum[84]=" + myGetTag(findViewById(rg85.getCheckedRadioButtonId()))
                            + "&sum[85]=" + myGetTag(findViewById(rg86.getCheckedRadioButtonId()))
                            + "&sum[86]=" + myGetTag(findViewById(rg87.getCheckedRadioButtonId()))
                            + "&sum[87]=" + myGetTag(findViewById(rg88.getCheckedRadioButtonId()))

                            + "&sumA=" + "22"
                            + "&sumB=" + "33"
                            + "&sumc=" + "44"
                            + "&sumD=" + "55"
                            + "&sumE=" + "66";
                    if (isAdd == false) {
                        param += "&evaluation[groupid]=" + instructions_map.get(spinner_value)
                                + "&evaluation[sum]=" + "sum"
                                + "&evaluation[sum_idA]=" + info.getList_record_id().get(89)
                                + "&evaluation[sum_idB]=" + info.getList_record_id().get(90)
                                + "&evaluation[sum_idC]=" + info.getList_record_id().get(91)
                                + "&evaluation[sum_idD]=" + info.getList_record_id().get(92)
                                + "&evaluation[sum_idE]=" + info.getList_record_id().get(93)
                                + "&evaluation[sum_id]=" + info.getList_record_id().get(88)

                                + "&r_id[0]=" + info.getList_record_id().get(0)
                                + "&r_id[1]=" + info.getList_record_id().get(1)
                                + "&r_id[2]=" + info.getList_record_id().get(2)
                                + "&r_id[3]=" + info.getList_record_id().get(3)
                                + "&r_id[4]=" + info.getList_record_id().get(4)
                                + "&r_id[5]=" + info.getList_record_id().get(5)
                                + "&r_id[6]=" + info.getList_record_id().get(6)
                                + "&r_id[7]=" + info.getList_record_id().get(7)
                                + "&r_id[8]=" + info.getList_record_id().get(8)
                                + "&r_id[9]=" + info.getList_record_id().get(9)
                                + "&r_id[10]=" + info.getList_record_id().get(10)
                                + "&r_id[11]=" + info.getList_record_id().get(11)
                                + "&r_id[12]=" + info.getList_record_id().get(12)
                                + "&r_id[13]=" + info.getList_record_id().get(13)
                                + "&r_id[14]=" + info.getList_record_id().get(14)
                                + "&r_id[15]=" + info.getList_record_id().get(15)
                                + "&r_id[16]=" + info.getList_record_id().get(16)
                                + "&r_id[17]=" + info.getList_record_id().get(17)
                                + "&r_id[18]=" + info.getList_record_id().get(18)
                                + "&r_id[19]=" + info.getList_record_id().get(19)
                                + "&r_id[20]=" + info.getList_record_id().get(20)
                                + "&r_id[21]=" + info.getList_record_id().get(21)
                                + "&r_id[22]=" + info.getList_record_id().get(22)
                                + "&r_id[23]=" + info.getList_record_id().get(23)
                                + "&r_id[24]=" + info.getList_record_id().get(24)
                                + "&r_id[25]=" + info.getList_record_id().get(25)
                                + "&r_id[26]=" + info.getList_record_id().get(26)
                                + "&r_id[27]=" + info.getList_record_id().get(27)
                                + "&r_id[28]=" + info.getList_record_id().get(28)
                                + "&r_id[29]=" + info.getList_record_id().get(29)
                                + "&r_id[30]=" + info.getList_record_id().get(30)
                                + "&r_id[31]=" + info.getList_record_id().get(31)
                                + "&r_id[32]=" + info.getList_record_id().get(32)
                                + "&r_id[33]=" + info.getList_record_id().get(33)
                                + "&r_id[34]=" + info.getList_record_id().get(34)
                                + "&r_id[35]=" + info.getList_record_id().get(35)
                                + "&r_id[36]=" + info.getList_record_id().get(36)
                                + "&r_id[37]=" + info.getList_record_id().get(37)
                                + "&r_id[38]=" + info.getList_record_id().get(38)
                                + "&r_id[39]=" + info.getList_record_id().get(39)
                                + "&r_id[40]=" + info.getList_record_id().get(40)
                                + "&r_id[41]=" + info.getList_record_id().get(41)
                                + "&r_id[42]=" + info.getList_record_id().get(42)
                                + "&r_id[43]=" + info.getList_record_id().get(43)
                                + "&r_id[44]=" + info.getList_record_id().get(44)
                                + "&r_id[45]=" + info.getList_record_id().get(45)
                                + "&r_id[46]=" + info.getList_record_id().get(46)
                                + "&r_id[47]=" + info.getList_record_id().get(47)
                                + "&r_id[48]=" + info.getList_record_id().get(48)
                                + "&r_id[49]=" + info.getList_record_id().get(49)
                                + "&r_id[50]=" + info.getList_record_id().get(50)
                                + "&r_id[51]=" + info.getList_record_id().get(51)
                                + "&r_id[52]=" + info.getList_record_id().get(52)
                                + "&r_id[53]=" + info.getList_record_id().get(53)
                                + "&r_id[54]=" + info.getList_record_id().get(54)
                                + "&r_id[55]=" + info.getList_record_id().get(55)
                                + "&r_id[56]=" + info.getList_record_id().get(56)
                                + "&r_id[57]=" + info.getList_record_id().get(57)
                                + "&r_id[58]=" + info.getList_record_id().get(58)
                                + "&r_id[59]=" + info.getList_record_id().get(59)
                                + "&r_id[60]=" + info.getList_record_id().get(60)
                                + "&r_id[61]=" + info.getList_record_id().get(61)
                                + "&r_id[62]=" + info.getList_record_id().get(62)
                                + "&r_id[63]=" + info.getList_record_id().get(63)
                                + "&r_id[64]=" + info.getList_record_id().get(64)
                                + "&r_id[65]=" + info.getList_record_id().get(65)
                                + "&r_id[66]=" + info.getList_record_id().get(66)
                                + "&r_id[67]=" + info.getList_record_id().get(67)
                                + "&r_id[68]=" + info.getList_record_id().get(68)
                                + "&r_id[69]=" + info.getList_record_id().get(69)
                                + "&r_id[70]=" + info.getList_record_id().get(70)
                                + "&r_id[71]=" + info.getList_record_id().get(71)
                                + "&r_id[72]=" + info.getList_record_id().get(72)
                                + "&r_id[73]=" + info.getList_record_id().get(73)
                                + "&r_id[74]=" + info.getList_record_id().get(74)
                                + "&r_id[75]=" + info.getList_record_id().get(75)
                                + "&r_id[76]=" + info.getList_record_id().get(76)
                                + "&r_id[77]=" + info.getList_record_id().get(77)
                                + "&r_id[78]=" + info.getList_record_id().get(78)
                                + "&r_id[79]=" + info.getList_record_id().get(79)
                                + "&r_id[80]=" + info.getList_record_id().get(80)
                                + "&r_id[81]=" + info.getList_record_id().get(81)
                                + "&r_id[82]=" + info.getList_record_id().get(82)
                                + "&r_id[83]=" + info.getList_record_id().get(83)
                                + "&r_id[84]=" + info.getList_record_id().get(84)
                                + "&r_id[85]=" + info.getList_record_id().get(85)
                                + "&r_id[86]=" + info.getList_record_id().get(86)
                                + "&r_id[87]=" + info.getList_record_id().get(87);

                    } else {
                        param += "&evaluation[sum]=" + "has_sum";

                    }
                    param += "&token=" + NetUrlAddress.token;


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
     * 判断radiogroup是否选中结果
     * 如果选中了就获取tag得值(分数)
     *
     * @param view
     * @return
     */
    String myGetTag(View view) {
        String s = "";
        try {
            if (null == view) {

            } else {
                s = view.getTag().toString();
            }
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
                    create_code = TableGmfmInfo.doPost(NetUrlAddress.Gmfm_create_url, param);
                } else {
                    update_code = TableGmfmInfo.doPost(NetUrlAddress.Gmfm_update_url, param);
                }
                if (create_code == 200) {
                    TableGmfmInfoActivity.this.finish();
                    ShowToast.Short(getString(R.string.msg_save_success));
                } else if (update_code == 200) {
                    TableGmfmInfoActivity.this.finish();
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
                L.d("gmfm", "MyThread出错了");
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
     * @return
     */
    boolean radioButtonIsEmpty() {
        boolean flag = true;
        try {
            for (RadioGroup rg : list_radioGroup) {
                if (findViewById(rg.getCheckedRadioButtonId()) == null) {
                    flag = true;
                } else {
                    flag = false;
                }
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
        if (TextUtils.isEmpty(date.getText().toString()) || radioButtonIsEmpty()) {
            ShowToast.Short(getString(R.string.msg_isEmpty_warning));
            return true;
        } else {
            return false;
        }
    }
}
