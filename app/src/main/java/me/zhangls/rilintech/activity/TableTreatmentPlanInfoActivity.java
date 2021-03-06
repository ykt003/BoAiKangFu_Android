package me.zhangls.rilintech.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.application.MyApplication;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.TableTreatmentPlanInfo;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;

/**
 * Created by YANG on 15/9/12.
 */
public class TableTreatmentPlanInfoActivity extends BaseActivity implements View.OnClickListener {
    //返回键
    private ImageView back;
    //保存键
    private ImageView save;
    //缓冲球
    private GoogleProgressBar progressBar;
    //正在编辑的对象
    private TableTreatmentPlanInfo info = null;
    //是否新建
    private Boolean isAdd = true;
    //次数
    private EditText counts;
    //干预目的
    private EditText purpose;
    //项目名称
    private EditText project_name;
    //剂量
    private EditText dose;
    //频率
    private EditText frequency;
    //强度
    private EditText intensity;
    //持续时间
    private EditText duration;
    //备注
    private EditText remarker;
    //设定者
    private EditText makers;
    //设定时间
    private TextView set_time;
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
    //添加干预目的
    private ImageView add_purpose;
    //删除
    private ImageView delete_purpose;
    //新加的干预目的值
    private String new_purpose = "";
    //给每个新添加的目的做标记
    private int tag = 0;
    //存放tag标记的EditText
    private ArrayList<Integer> list_tag = new ArrayList<>();
    //干预目的布局
    private LinearLayout ll_purpose_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table_treatment_plan_info);

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

        isAdd = getIntent().getBooleanExtra("isAdd", true);
        if (isAdd) {
            new_count = getIntent().getIntExtra("counts", 0);
        } else {
            info = (TableTreatmentPlanInfo) getIntent().getParcelableExtra("info");
        }
    }


    private void initView() {
        back = (ImageView) findViewById(R.id.title_back);
        back.setOnClickListener(this);
        save = (ImageView) findViewById(R.id.title_save);
        save.setOnClickListener(this);
        progressBar = (GoogleProgressBar) findViewById(R.id.google_progress);
        progressBar.setVisibility(View.GONE);

        add_purpose = (ImageView) findViewById(R.id.image_add_layout);
        add_purpose.setOnClickListener(this);

        ll_purpose_layout = (LinearLayout) findViewById(R.id.et_tp_purpose_layout);


        counts = (EditText) findViewById(R.id.et_tp_counts);
        counts.setText(new_count + 1 + "");
        purpose = (EditText) findViewById(R.id.et_tp_purpose);
        project_name = (EditText) findViewById(R.id.et_tp_project_name);
        dose = (EditText) findViewById(R.id.et_tp_dose);
        frequency = (EditText) findViewById(R.id.et_tp_frequency);
        intensity = (EditText) findViewById(R.id.et_tp_intensity);
        duration = (EditText) findViewById(R.id.et_tp_duration);
        remarker = (EditText) findViewById(R.id.et_tp_remarker);
        makers = (EditText) findViewById(R.id.et_tp_maker);
        set_time = (TextView) findViewById(R.id.et_tp_set_date);
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

        if (isAdd == false) {
            if (info != null) {
                setData();
            }
        }
    }

    /**
     * isAdd = false，给控件赋值
     */
    private void setData() {

        counts.setText(info.getCounts() + "");
        counts.setEnabled(false);
        purpose.setText(info.getPurpose().split(",")[0]);

        ArrayList<String> list = new ArrayList<>();
        for (String s:info.getPurpose().split(",")){
            list.add(s);
        }
        //去掉已经赋值给第一个的干预目的
        list.remove(0);
        for(String s:list){
            add_layout_set_data(s);
        }

        project_name.setText(info.getProject_name());
        dose.setText(info.getDose());
        frequency.setText(info.getFrequency());
        intensity.setText(info.getIntensity());
        duration.setText(info.getDuration());
        remarker.setText(info.getRemarker());
        makers.setText(info.getMakers());
        set_time.setText(info.getSet_time());
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
                }
                if (TextUtils.isEmpty(purpose.getText())) {
                    ShowToast.Short("请填写干预目的");
                }
                if (TextUtils.isEmpty(makers.getText())) {
                    ShowToast.Short("请填写设定者");
                }
                if (TextUtils.isEmpty(set_time.getText())) {
                    ShowToast.Short("请选择设定日期");
                } else {

                    progressBar.setVisibility(View.VISIBLE);

                    SharedPreferences sharedPreferences = getSharedPreferences("patientinfo", Activity.MODE_PRIVATE);
                    int id = sharedPreferences.getInt("id", -1);

                    SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                    int user_id = preferences.getInt("use_id", 2);

                    for(int i:list_tag){
                        EditText text = (EditText)ll_purpose_layout.findViewWithTag(i).findViewById(R.id.et_tp_purpose);
                        new_purpose += text.getText().toString()+",";
                    }

                    param = "patient_info_id=" + id
                            + "&user_auth_id=" + user_id
                            + "&treatment_plan[cishu]=" + counts.getText()
                            + "&treatment_plan[trp_gymd]=" + purpose.getText()+","+new_purpose
                            + "&treatment_plan[trp_xmmc]=" + project_name.getText()
                            + "&treatment_plan[trp_jl]=" + dose.getText()
                            + "&treatment_plan[trp_pl]=" + frequency.getText()
                            + "&treatment_plan[trp_qd]=" + intensity.getText()
                            + "&treatment_plan[trp_cxdate]=" + duration.getText()
                            + "&treatment_plan[trp_bzh]=" + remarker.getText()
                            + "&treatment_plan[executor]=" + makers.getText()
                            + "&treatment_plan[zhid_time]=" + set_time.getText()

                            + "&token=" + NetUrlAddress.token;

                    new MyThread().start();
                }
                break;
            case R.id.et_tp_set_date:
                pwTime.showAtLocation(set_time, Gravity.BOTTOM, 0, 0, new Date());
                break;
            case R.id.image_add_layout:
                add_layout_set_data("");

                break;


            default:
                break;
        }

    }

    /**
     * 添加新的干预目标
     * @param s
     */
    private void add_layout_set_data(String s){
        tag += 1;
        list_tag.add(tag);

        final View view = LayoutInflater.from(this).inflate(R.layout.include_treatment_plan,null);
        delete_purpose = (ImageView) view.findViewById(R.id.image_delete_layout);
        view.setTag(tag);
        EditText text = (EditText) view.findViewById(R.id.et_tp_purpose);
        text.setText(s);
        ll_purpose_layout.addView(view);

        delete_purpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_purpose_layout.removeView(view);

                for (int i=0;i<list_tag.size();i++){
                    if (list_tag.get(i)==view.getTag()){
                        list_tag.remove(i);
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
                    create_code = TableTreatmentPlanInfo.doPost(NetUrlAddress.tp_create_url, param);
                } else {
                    String url = NetUrlAddress.tp_update_url.replaceAll("aaa", info.getRecord_id() + "");
                    update_code = TableTreatmentPlanInfo.doPost(url, param);
                }
                if (create_code == 200 || update_code == 200) {
                    TableTreatmentPlanInfoActivity.this.finish();
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
}
