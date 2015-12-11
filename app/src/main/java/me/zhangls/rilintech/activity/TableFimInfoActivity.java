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
import me.zhangls.rilintech.model.TableFimInfo;
import me.zhangls.rilintech.model.TableGmfmInfo;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;

/**
 * Created by YANG on 15/10/15.
 */
public class TableFimInfoActivity extends BaseActivity implements View.OnClickListener{

    //标题
    private TextView title;
    //返回按键
    private ImageView back;
    //保存按键
    private ImageView save;
    //radiogroup
    private RadioGroup rg1, rg2, rg3, rg4, rg5, rg6, rg7, rg8, rg9, rg10, rg11, rg12, rg13, rg14,
            rg15, rg16, rg17, rg18;
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
    private TableFimInfo info = null;
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
    private TextView score_a, score_b, score_all;
    //总分
    private int all = 0;
    //A部分得分
    private int a = 0;
    //B部分得分
    private int b = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_fim_info);

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
        title.setText(getString(R.string.fim_title));
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

        score_a = (TextView) findViewById(R.id.fim_part1_score);
        score_b = (TextView) findViewById(R.id.fim_part2_score);
        score_all = (TextView) findViewById(R.id.fim_total_score);

        rg1 = (RadioGroup) findViewById(R.id.fim_question1_rg);
        rg2 = (RadioGroup) findViewById(R.id.fim_question2_rg);
        rg3 = (RadioGroup) findViewById(R.id.fim_question3_rg);
        rg4 = (RadioGroup) findViewById(R.id.fim_question4_rg);
        rg5 = (RadioGroup) findViewById(R.id.fim_question5_rg);
        rg6 = (RadioGroup) findViewById(R.id.fim_question6_rg);
        rg7 = (RadioGroup) findViewById(R.id.fim_question7_rg);
        rg8 = (RadioGroup) findViewById(R.id.fim_question8_rg);
        rg9 = (RadioGroup) findViewById(R.id.fim_question9_rg);
        rg10 = (RadioGroup) findViewById(R.id.fim_question10_rg);
        rg11 = (RadioGroup) findViewById(R.id.fim_question11_rg);
        rg12 = (RadioGroup) findViewById(R.id.fim_question12_rg);
        rg13 = (RadioGroup) findViewById(R.id.fim_question13_rg);
        rg14 = (RadioGroup) findViewById(R.id.fim_question14_rg);
        rg15 = (RadioGroup) findViewById(R.id.fim_question15_rg);
        rg16 = (RadioGroup) findViewById(R.id.fim_question16_rg);
        rg17 = (RadioGroup) findViewById(R.id.fim_question17_rg);
        rg18 = (RadioGroup) findViewById(R.id.fim_question18_rg);


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


        //循环RadioGroup集合，监听每个radioGroup；
        for (RadioGroup rg : list_radioGroup) {
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    //全部归零
                    all = 0;
                    a = 0;
                    b = 0;
                    //再次遍历RadioGroup集合，计算每部分的总分
                    for (int i = 0; i < list_radioGroup.size(); i++) {
                        all += myGetScore(findViewById(list_radioGroup.get(i).getCheckedRadioButtonId()));
                        if (i < 13) {
                            a += myGetScore(findViewById(list_radioGroup.get(i).getCheckedRadioButtonId()));
                        }
                        if (13 <= i && i < 18) {
                            b += myGetScore(findViewById(list_radioGroup.get(i).getCheckedRadioButtonId()));
                        }
                    }
                    score_all.setText(all + "");
                    score_a.setText(a + "");
                    score_b.setText(b + "");
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
            L.d("Fim", "radiobutton没有选中");
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
                    if (view.getTag().equals(info.getList_score().get(j).toString())) {
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
                            + "&menu_id=" + 35
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
                            + "&in_sum[18]=" + "f"
                            + "&in_sum[19]=" + "f"

                            + "&note[0]=" + "运动功能<br/>Ⅰ 自理能力"
                            + "&note[1]=" + "运动功能<br/>Ⅰ 自理能力"
                            + "&note[2]=" + "运动功能<br/>Ⅰ 自理能力"
                            + "&note[3]=" + "运动功能<br/>Ⅰ 自理能力"
                            + "&note[4]=" + "运动功能<br/>Ⅰ 自理能力"
                            + "&note[5]=" + "运动功能<br/>Ⅰ 自理能力"
                            + "&note[6]=" + "Ⅱ 括约肌控制"
                            + "&note[7]=" + "Ⅱ 括约肌控制"
                            + "&note[8]=" + "Ⅱ 括约肌控制"
                            + "&note[9]=" + "Ⅲ 转移"
                            + "&note[10]=" + "Ⅲ 转移"
                            + "&note[11]=" + "Ⅳ 行走"
                            + "&note[12]=" + "Ⅳ 行走"
                            + "&note[13]=" + "认知功能<br>Ⅴ 交流"
                            + "&note[14]=" + "认知功能<br>Ⅴ 交流"
                            + "&note[15]=" + "Ⅵ 社会认知"
                            + "&note[16]=" + "Ⅵ 社会认知"
                            + "&note[17]=" + "Ⅵ 社会认知"
                            + "&note[18]=" + "结论"
                            + "&note[19]=" + "结论"

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

                            + "&explain[0]=" + getString(R.string.fim_question1)
                            + "&explain[1]=" + getString(R.string.fim_question2)
                            + "&explain[2]=" + getString(R.string.fim_question3)
                            + "&explain[3]=" + getString(R.string.fim_question4)
                            + "&explain[4]=" + getString(R.string.fim_question5)
                            + "&explain[5]=" + getString(R.string.fim_question6)
                            + "&explain[6]=" + getString(R.string.fim_question7)
                            + "&explain[7]=" + getString(R.string.fim_question8)
                            + "&explain[8]=" + getString(R.string.fim_question9)
                            + "&explain[9]=" + getString(R.string.fim_question10)
                            + "&explain[10]=" + getString(R.string.fim_question11)
                            + "&explain[11]=" + getString(R.string.fim_question12)
                            + "&explain[12]=" + getString(R.string.fim_question13)
                            + "&explain[13]=" + getString(R.string.fim_question14)
                            + "&explain[14]=" + getString(R.string.fim_question15)
                            + "&explain[15]=" + getString(R.string.fim_question16)
                            + "&explain[16]=" + getString(R.string.fim_question17)
                            + "&explain[17]=" + getString(R.string.fim_question18)
                            + "&explain[18]=" + getString(R.string.fim_part1_score)
                            + "&explain[19]=" + getString(R.string.fim_part2_score)

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
                            + "&sum[18]=" + score_a.getText()
                            + "&sum[19]=" + score_b.getText();

                    if (isAdd == false) {
                        param += "&evaluation[groupid]=" + instructions_map.get(spinner_value)
                                + "&evaluation[sum]=" + "sum"
                                + "&evaluation[sum_id]=" + info.getList_record_id().get(20)

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
                                + "&r_id[19]=" + info.getList_record_id().get(19);

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
                    create_code = TableFimInfo.doPost(NetUrlAddress.Fim_create_url, param);
                } else {
                    update_code = TableFimInfo.doPost(NetUrlAddress.Fim_update_url, param);
                }
                if (create_code == 200) {
                    TableFimInfoActivity.this.finish();
                    ShowToast.Short(getString(R.string.msg_save_success));
                } else if (update_code == 200) {
                    TableFimInfoActivity.this.finish();
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
                L.d("Fim", "MyThread出错");
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
