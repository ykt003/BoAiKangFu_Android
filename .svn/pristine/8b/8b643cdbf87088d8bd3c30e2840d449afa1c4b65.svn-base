package me.zhangls.rilintech.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.adapter.SpinnerBaseAdapter;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.TableFimInfo;
import me.zhangls.rilintech.model.TableFmaInfo;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.NetUtils;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;

/**
 * Created by YANG on 15/10/15.
 */
public class TableFimIndexActivity extends BaseActivity implements View.OnClickListener{

    private Intent intent;
    //题目
    private TextView title;
    //当前登录的用户ID
    private int user_auth_id;
    //患者ID
    private int patient_info_id;
    //删除响应码
    private int delete_code = 0;
    //返回的已存在记录量表集合
    private ArrayList<String> list_instructions_cloud = new ArrayList<>();
    //评定日期
    private TextView date;
    //评定人
    private TextView maker;
    //评定说明
    private Spinner instructions;
    //总分
    private TextView total_score;
    //运动功能评分
    private TextView part1_score;
    //认知功能评分
    private TextView part2_score;
    //主（数据显示）布局
    private LinearLayout main_linear_layout;
    //具体数据展示控件
    private TextView score1;
    private TextView score2;
    private TextView score3;
    private TextView score4;
    private TextView score5;
    private TextView score6;
    private TextView score7;
    private TextView score8;
    private TextView score9;
    private TextView score10;
    private TextView score11;
    private TextView score12;
    private TextView score13;
    private TextView score14;
    private TextView score15;
    private TextView score16;
    private TextView score17;
    private TextView score18;
    //集合
    private List<TextView> list_textView;
    //spinner加载器
    private SpinnerBaseAdapter adapter_spinner;
    //下拉刷新
    private SwipeRefreshLayout swipeRefreshLayout;
    //评定说明的值
    private ArrayList<String> list_instructions = new ArrayList<>();
    //评定说明当前选中值
    private String instructions_value;
    //dialog
    private AlertDialog dialog = null;
    //当前显示的检查记录表
    private TableFimInfo info = null;
    //另起线程
    private MyThread myThread;
    //用来接受拿到数据后发来的消息
    private static Handler handler;
    //缓冲球
    private GoogleProgressBar google_progress;
    //返回的值转化成String字符串
    private String value;
    //封装返回数据的实例
    private ArrayList<TableFimInfo> list_infos = new ArrayList<>();
    //评定说明
    private Map<String, Integer> instructions_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_fim_index);

        //获取当前登录用户和当前患者的ID
        getSharedPreferencesData();
        //初始化控件
        initView();
        //另起线程向服务器请求数据
        myThread = new MyThread();
        myThread.start();
        //网络返回数据后得到通知刷新View
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //停止刷新
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                switch (msg.what) {
                    case 1:

                        google_progress.setVisibility(View.GONE);
                        getData();
                        break;
                    case 2:
                        google_progress.setVisibility(View.GONE);
                        break;
                }
            }
        };

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

    private void initView() {

        title = (TextView) findViewById(R.id.layout).findViewById(R.id.title);
        title.setText(getString(R.string.fim_title));
        //放数据
        instructions_map = new HashMap<>();
        instructions_map.put(getString(R.string.evaluation0), 0);
        instructions_map.put(getString(R.string.evaluation1), 1);
        instructions_map.put(getString(R.string.evaluation2), 2);
        instructions_map.put(getString(R.string.evaluation3), 3);
        instructions_map.put(getString(R.string.evaluation4), 4);
        instructions_map.put(getString(R.string.evaluation5), 5);
        instructions_map.put(getString(R.string.evaluation6), 6);

        list_instructions.add(getString(R.string.evaluation0));
        list_instructions.add(getString(R.string.evaluation1));
        list_instructions.add(getString(R.string.evaluation2));
        list_instructions.add(getString(R.string.evaluation3));
        list_instructions.add(getString(R.string.evaluation4));
        list_instructions.add(getString(R.string.evaluation5));
        list_instructions.add(getString(R.string.evaluation6));

        instructions_value = list_instructions.get(0);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new MyThread().start();
            }
        });

        google_progress = (GoogleProgressBar) findViewById(R.id.google_progress);
        google_progress.setVisibility(View.GONE);

        date = (TextView) findViewById(R.id.date);
        maker = (TextView) findViewById(R.id.maker);
        total_score = (TextView) findViewById(R.id.fim_total_score);
        part1_score = (TextView) findViewById(R.id.fim_part1_score);
        part2_score = (TextView) findViewById(R.id.fim_part2_score);
        main_linear_layout = (LinearLayout) findViewById(R.id.main_linear_layout);
        main_linear_layout.setVisibility(View.INVISIBLE);
        main_linear_layout.setOnClickListener(this);

        instructions = (Spinner) findViewById(R.id.spinner_edit);
        adapter_spinner = new SpinnerBaseAdapter(this, android.R.layout.simple_spinner_item, instructions, list_instructions);
        adapter_spinner.setDropDownViewResource(R.layout.item_spinner_layout);
        instructions.setOnItemSelectedListener(new MyOnItemSelectedListener());
        instructions.setAdapter(adapter_spinner);

        score1 = (TextView) findViewById(R.id.fim_answer1);
        score2 = (TextView) findViewById(R.id.fim_answer2);
        score3 = (TextView) findViewById(R.id.fim_answer3);
        score4 = (TextView) findViewById(R.id.fim_answer4);
        score5 = (TextView) findViewById(R.id.fim_answer5);
        score6 = (TextView) findViewById(R.id.fim_answer6);
        score7 = (TextView) findViewById(R.id.fim_answer7);
        score8 = (TextView) findViewById(R.id.fim_answer8);
        score9 = (TextView) findViewById(R.id.fim_answer9);
        score10 = (TextView) findViewById(R.id.fim_answer10);
        score11 = (TextView) findViewById(R.id.fim_answer11);
        score12 = (TextView) findViewById(R.id.fim_answer12);
        score13 = (TextView) findViewById(R.id.fim_answer13);
        score14 = (TextView) findViewById(R.id.fim_answer14);
        score15 = (TextView) findViewById(R.id.fim_answer15);
        score16 = (TextView) findViewById(R.id.fim_answer16);
        score17 = (TextView) findViewById(R.id.fim_answer17);
        score18 = (TextView) findViewById(R.id.fim_answer18);

        list_textView = new ArrayList<>();
        list_textView.add(score1);
        list_textView.add(score2);
        list_textView.add(score3);
        list_textView.add(score4);
        list_textView.add(score5);
        list_textView.add(score6);
        list_textView.add(score7);
        list_textView.add(score8);
        list_textView.add(score9);
        list_textView.add(score10);
        list_textView.add(score11);
        list_textView.add(score12);
        list_textView.add(score13);
        list_textView.add(score14);
        list_textView.add(score15);
        list_textView.add(score16);
        list_textView.add(score17);
        list_textView.add(score18);
        list_textView.add(part1_score);
        list_textView.add(part2_score);
        list_textView.add(total_score);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_linear_layout:
                AlertDialog.Builder builder = new AlertDialog.Builder(TableFimIndexActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_layout, null);

                TextView delete = (TextView) view.findViewById(R.id.delete);
                TextView add = (TextView) view.findViewById(R.id.add);
                TextView edit = (TextView) view.findViewById(R.id.edit);

                dialog = builder.show();
                dialog.getWindow().setContentView(view);
                //删除
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        google_progress.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                        final String param = "patient_info_id=" + patient_info_id
                                + "&user_auth_id=" + user_auth_id
                                + "&menu_id=" + 35
                                + "&groupid=" + (instructions_map.get(instructions_value) + 1)
                                + "&token=" + NetUrlAddress.token;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                delete_code = TableFmaInfo.doPost(NetUrlAddress.Fim_delete_url, param);
                                if (delete_code == 200) {

                                    //删除后回滚到顶端
                                    ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
                                    scrollView.scrollTo(0,0);

                                    ShowToast.Short(getString(R.string.msg_delete_success));
                                    //刷新列表，重新获取数据
                                    myThread = new MyThread();
                                    myThread.start();
                                    Message message = new Message();
                                    message.what = 2;
                                    handler.sendMessage(message);
                                } else {
                                    ShowToast.Short(getString(R.string.msg_delete_fail));
                                    Message message = new Message();
                                    message.what = 2;
                                    handler.sendMessage(message);
                                }
                                Looper.loop();
                            }
                        }).start();
                    }
                });
                //新建
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        add(v);
                    }
                });
                //编辑
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        intent = new Intent(TableFimIndexActivity.this, TableFimInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("info",info);
                        bundle.putBoolean("isAdd", false);
                        bundle.putStringArrayList("instructions", list_instructions_cloud);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
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

            String param = "patient_info_id=" + patient_info_id
                    + "&menu_id=" + 35
                    + "&user_auth_id=" + user_auth_id
                    + "&token=" + NetUrlAddress.token;

            try {
                value = TableFimInfo.getData(NetUrlAddress.Fim_getData_url, param);
                L.d("text", value);
                JSONObject jsonObject = new JSONObject(value);
                list_infos = TableFimInfo.parseCache(jsonObject);
                //得到返回的数据，发消息通知刷新View
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
                L.d("text", getLocalClassName() + "解析JSON出错");
                Looper.prepare();
                ShowToast.Short(getString(R.string.msg_getdate_fail));
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
                Looper.loop();
            }
        }
    }

    /**
     * 监听 Spinner点选
     */
    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            instructions_value = list_instructions.get(position);
            L.d("text", instructions_value);
            //重新赋值
            getData();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /**
     * 获取返回的数据并展示,通知刷新
     */
    private void getData() {

        //有网络的情况下
        if (NetUtils.getNetworkState(this) != NetUtils.NETWORN_NONE) {

            //每次点击spinner都要重置barthelinfo实例为null,并清空数据
            info = null;
            date.setText("");
            maker.setText("");
            total_score.setText("");
            part1_score.setText("");
            part2_score.setText("");
            for (TextView v:list_textView) {
                v.setText("");
            }
            //隐藏listView
            main_linear_layout.setVisibility(View.INVISIBLE);

            //重新赋值前要清空
            list_instructions_cloud.clear();
            //返回的数据size()>0说明不为空
            if (list_infos.size() > 0) {
                for (TableFimInfo m : list_infos) {
                    list_instructions_cloud.add(m.getInstructions());
                    //找出与当前spinner显示相匹配的量表数据
                    if (instructions_value.equals(m.getInstructions())) {
                        info = m;
                    }
                }
            }

            //如果返回数据不为空则赋值给显示控件
            if (null != info) {
                //判断日期是否为空
                if (null != info.getDate()) {
                    date.setText(info.getDate().substring(0, 10) + " " + info.getDate().substring(11, 16));
                }
                maker.setText(info.getMaker());
                //判断数据长度是否与控件个数相等，如果不相等List.get(i)的时候会报错
                if (info.getList_score().size() == list_textView.size()) {
                    for (int i = 0; i < list_textView.size(); i++) {
                        list_textView.get(i).setText(info.getList_score().get(i)+"");
                    }
                }
                main_linear_layout.setVisibility(View.VISIBLE);
            }
        }
    }

    public void add (View view){
        ArrayList<String> list = new ArrayList<>();
        list.addAll(list_instructions);
        if (list_infos.size() > 0) {
            for (int i = 0; i < list_instructions_cloud.size(); i++) {
                if (list.contains(list_instructions_cloud.get(i))) {
                    list.remove(list_instructions_cloud.get(i));
                }
            }
        }
        if (list.size() > 0) {
            google_progress.setVisibility(View.VISIBLE);
            intent = new Intent(this, TableFimInfoActivity.class);
            intent.putExtra("isAdd", true);
            intent.putStringArrayListExtra("instructions", list_instructions_cloud);

            startActivity(intent);
        } else {
            Toast.makeText(TableFimIndexActivity.this, getString(R.string.msg_data_full), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 返回按键
     *
     * @param view
     */
    public void back(View view) {
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        google_progress.setVisibility(View.VISIBLE);
        myThread = new MyThread();
        myThread.start();
    }
}
