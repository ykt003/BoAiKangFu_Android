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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.zhangls.rilintech.R;
import me.zhangls.rilintech.adapter.SpinnerBaseAdapter;
import me.zhangls.rilintech.adapter.TableRomAdapter;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.TableAshworthInfo;
import me.zhangls.rilintech.model.TableFmaInfo;
import me.zhangls.rilintech.model.TableRomInfo;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.NetUtils;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;

/**
 * Created by YANG on 15/9/30.
 */
public class TableFmaIndexActivity extends BaseActivity implements View.OnClickListener{
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
    //临床意义
    private TextView evaluation_content;
    //主（数据显示）布局
    private LinearLayout main_linear_layout;
    //具体数据展示控件
    private TextView up_1_1;
    private TextView up_1_2;
    private TextView up_2_1;
    private TextView up_2_2;
    private TextView up_2_3;
    private TextView up_2_4;
    private TextView up_2_5;
    private TextView up_2_6;
    private TextView up_3_1;
    private TextView up_3_2;
    private TextView up_3_3;
    private TextView up_4_1;
    private TextView up_4_2;
    private TextView up_4_3;
    private TextView up_5_1;
    private TextView up_5_2;
    private TextView up_5_3;
    private TextView up_6_1;
    private TextView up_7_1;
    private TextView up_7_2;
    private TextView up_7_3;
    private TextView up_7_4;
    private TextView up_7_5;
    private TextView up_8_1;
    private TextView up_8_2;
    private TextView up_8_3;
    private TextView up_8_4;
    private TextView up_8_5;
    private TextView up_8_6;
    private TextView up_8_7;
    private TextView up_9_1;
    private TextView up_9_2;
    private TextView up_9_3;
    private TextView down_1_1;
    private TextView down_1_2;
    private TextView down_2_1;
    private TextView down_2_2;
    private TextView down_2_3;
    private TextView down_2_4;
    private TextView down_2_5;
    private TextView down_2_6;
    private TextView down_2_7;
    private TextView down_3_1;
    private TextView down_3_2;
    private TextView down_4_1;
    private TextView down_4_2;
    private TextView down_5_1;
    private TextView down_6_1;
    private TextView down_6_2;
    private TextView down_6_3;
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
    private TableFmaInfo info = null;
    //另起线程
    private MyThread myThread;
    //用来接受拿到数据后发来的消息
    private static Handler handler;
    //缓冲球
    private GoogleProgressBar google_progress;
    //返回的值转化成String字符串
    private String value;
    //封装返回数据的实例
    private ArrayList<TableFmaInfo> list_infos = new ArrayList<>();
    //评定说明
    private Map<String, Integer> instructions_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_fma_index);
        //获取当前登录用户和当前患者的ID
        getSharedPreferencesData();
        //初始化控件
        initView();

        //显示缓冲球
        google_progress.setVisibility(View.VISIBLE);
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
        title.setText(getString(R.string.fma_title));
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
        evaluation_content = (TextView) findViewById(R.id.clinical);
        main_linear_layout = (LinearLayout) findViewById(R.id.main_linear_layout);
        main_linear_layout.setVisibility(View.INVISIBLE);
        main_linear_layout.setOnClickListener(this);

        instructions = (Spinner) findViewById(R.id.spinner_edit);
        adapter_spinner = new SpinnerBaseAdapter(this, android.R.layout.simple_spinner_item, instructions, list_instructions);
        adapter_spinner.setDropDownViewResource(R.layout.item_spinner_layout);
        instructions.setOnItemSelectedListener(new MyOnItemSelectedListener());
        instructions.setAdapter(adapter_spinner);

        up_1_1 = (TextView) findViewById(R.id.fma_up_title_1_1);
        up_1_2 = (TextView) findViewById(R.id.fma_up_title_1_2);
        up_2_1 = (TextView) findViewById(R.id.fma_up_title_2_1);
        up_2_2 = (TextView) findViewById(R.id.fma_up_title_2_2);
        up_2_3 = (TextView) findViewById(R.id.fma_up_title_2_3);
        up_2_4 = (TextView) findViewById(R.id.fma_up_title_2_4);
        up_2_5 = (TextView) findViewById(R.id.fma_up_title_2_5);
        up_2_6 = (TextView) findViewById(R.id.fma_up_title_2_6);
        up_3_1 = (TextView) findViewById(R.id.fma_up_title_3_1);
        up_3_2 = (TextView) findViewById(R.id.fma_up_title_3_2);
        up_3_3 = (TextView) findViewById(R.id.fma_up_title_3_3);
        up_4_1 = (TextView) findViewById(R.id.fma_up_title_4_1);
        up_4_2 = (TextView) findViewById(R.id.fma_up_title_4_2);
        up_4_3 = (TextView) findViewById(R.id.fma_up_title_4_3);
        up_5_1 = (TextView) findViewById(R.id.fma_up_title_5_1);
        up_5_2 = (TextView) findViewById(R.id.fma_up_title_5_2);
        up_5_3 = (TextView) findViewById(R.id.fma_up_title_5_3);
        up_6_1 = (TextView) findViewById(R.id.fma_up_title_6_1);
        up_7_1 = (TextView) findViewById(R.id.fma_up_title_7_1);
        up_7_2 = (TextView) findViewById(R.id.fma_up_title_7_2);
        up_7_3 = (TextView) findViewById(R.id.fma_up_title_7_3);
        up_7_4 = (TextView) findViewById(R.id.fma_up_title_7_4);
        up_7_5 = (TextView) findViewById(R.id.fma_up_title_7_5);
        up_8_1 = (TextView) findViewById(R.id.fma_up_title_8_1);
        up_8_2 = (TextView) findViewById(R.id.fma_up_title_8_2);
        up_8_3 = (TextView) findViewById(R.id.fma_up_title_8_3);
        up_8_4 = (TextView) findViewById(R.id.fma_up_title_8_4);
        up_8_5 = (TextView) findViewById(R.id.fma_up_title_8_5);
        up_8_6 = (TextView) findViewById(R.id.fma_up_title_8_6);
        up_8_7 = (TextView) findViewById(R.id.fma_up_title_8_7);
        up_9_1 = (TextView) findViewById(R.id.fma_up_title_9_1);
        up_9_2 = (TextView) findViewById(R.id.fma_up_title_9_2);
        up_9_3 = (TextView) findViewById(R.id.fma_up_title_9_3);
        down_1_1 = (TextView) findViewById(R.id.fma_down_title_1_1);
        down_1_2 = (TextView) findViewById(R.id.fma_down_title_1_2);
        down_2_1 = (TextView) findViewById(R.id.fma_down_title_2_1_1);
        down_2_2 = (TextView) findViewById(R.id.fma_down_title_2_1_2);
        down_2_3 = (TextView) findViewById(R.id.fma_down_title_2_1_3);
        down_2_4 = (TextView) findViewById(R.id.fma_down_title_2_2_1);
        down_2_5 = (TextView) findViewById(R.id.fma_down_title_2_2_2);
        down_2_6 = (TextView) findViewById(R.id.fma_down_title_2_2_3);
        down_2_7 = (TextView) findViewById(R.id.fma_down_title_2_2_4);
        down_3_1 = (TextView) findViewById(R.id.fma_down_title_3_1);
        down_3_2 = (TextView) findViewById(R.id.fma_down_title_3_2);
        down_4_1 = (TextView) findViewById(R.id.fma_down_title_4_1);
        down_4_2 = (TextView) findViewById(R.id.fma_down_title_4_2);
        down_5_1 = (TextView) findViewById(R.id.fma_down_title_5_1);
        down_6_1 = (TextView) findViewById(R.id.fma_down_title_6_1);
        down_6_2 = (TextView) findViewById(R.id.fma_down_title_6_2);
        down_6_3 = (TextView) findViewById(R.id.fma_down_title_6_3);

        list_textView = new ArrayList<>();
        list_textView.add(up_1_1);
        list_textView.add(up_1_2);
        list_textView.add(up_2_1);
        list_textView.add(up_2_2);
        list_textView.add(up_2_3);
        list_textView.add(up_2_4);
        list_textView.add(up_2_5);
        list_textView.add(up_2_6);
        list_textView.add(up_3_1);
        list_textView.add(up_3_2);
        list_textView.add(up_3_3);
        list_textView.add(up_4_1);
        list_textView.add(up_4_2);
        list_textView.add(up_4_3);
        list_textView.add(up_5_1);
        list_textView.add(up_5_2);
        list_textView.add(up_5_3);
        list_textView.add(up_6_1);
        list_textView.add(up_7_1);
        list_textView.add(up_7_2);
        list_textView.add(up_7_3);
        list_textView.add(up_7_4);
        list_textView.add(up_7_5);
        list_textView.add(up_8_1);
        list_textView.add(up_8_2);
        list_textView.add(up_8_3);
        list_textView.add(up_8_4);
        list_textView.add(up_8_5);
        list_textView.add(up_8_6);
        list_textView.add(up_8_7);
        list_textView.add(up_9_1);
        list_textView.add(up_9_2);
        list_textView.add(up_9_3);
        list_textView.add(down_1_1);
        list_textView.add(down_1_2);
        list_textView.add(down_2_1);
        list_textView.add(down_2_2);
        list_textView.add(down_2_3);
        list_textView.add(down_2_4);
        list_textView.add(down_2_5);
        list_textView.add(down_2_6);
        list_textView.add(down_2_7);
        list_textView.add(down_3_1);
        list_textView.add(down_3_2);
        list_textView.add(down_4_1);
        list_textView.add(down_4_2);
        list_textView.add(down_5_1);
        list_textView.add(down_6_1);
        list_textView.add(down_6_2);
        list_textView.add(down_6_3);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_linear_layout:
                AlertDialog.Builder builder = new AlertDialog.Builder(TableFmaIndexActivity.this);
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
                                + "&menu_id=" + 33
                                + "&token=" + NetUrlAddress.token;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                String url = NetUrlAddress.Fma_delete_url.replaceAll("aaa",info.getRecord_id()+"");
                                delete_code = TableFmaInfo.doPost(url, param);
                                if (delete_code == 200) {
                                    ShowToast.Short(getString(R.string.msg_delete_success));
                                    //回滚到顶端
                                    ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
                                    scrollView.scrollTo(0,0);
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
                        intent = new Intent(TableFmaIndexActivity.this, TableFmaInfoActivity.class);
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
                    + "&menu_id=" + 33
                    + "&user_auth_id=" + user_auth_id
                    + "&token=" + NetUrlAddress.token;

            try {
                value = TableFmaInfo.getData(NetUrlAddress.Fma_getData_url, param);
                L.d("text", value);
                JSONArray jsonArray = new JSONArray(value);
                list_infos = TableFmaInfo.parseCache(jsonArray);
                //得到返回的数据，发消息通知刷新View
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
                L.d("text", getLocalClassName() + "出错了");
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
     * 获取返回的数据并展示,通知listview刷新
     */
    private void getData() {

        //有网络的情况下
        if (NetUtils.getNetworkState(this) != NetUtils.NETWORN_NONE) {

            //每次点击spinner都要重置barthelinfo实例为null,并清空数据
            info = null;
            date.setText("");
            maker.setText("");
            evaluation_content.setText("");
            for (TextView v:list_textView) {
                v.setText("");
            }
            //隐藏listView
            main_linear_layout.setVisibility(View.INVISIBLE);

            //重新赋值前要清空
            list_instructions_cloud.clear();
            //返回的数据size()>0说明不为空
            if (list_infos.size() > 0) {
                for (TableFmaInfo m : list_infos) {
                    list_instructions_cloud.add(m.getInstructions());
                    //找出与当前spinner显示相匹配的量表数据
                    if (instructions_value.equals(m.getInstructions())) {
                        info = m;
                    }
                }
            }

            //如果返回数据不为空则赋值给显示控件
            if (null != info) {
                if (null != info.getDate()) {
                    date.setText(info.getDate().substring(0, 10) + " " + info.getDate().substring(11, 16));
                }
                maker.setText(info.getMaker());
                evaluation_content.setText(info.getEvaluation_content() + "  " + info.getEvaluation_catalog());
                for (int i=0;i<list_textView.size();i++){
                    list_textView.get(i).setText(info.getList_value().get(i));
                }

                main_linear_layout.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 添加按键
     *
     * @param view
     */
    public void add(View view) {
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
            intent = new Intent(this, TableFmaInfoActivity.class);
            intent.putExtra("isAdd", true);
            intent.putStringArrayListExtra("instructions", list_instructions_cloud);

            startActivity(intent);
        } else {
            Toast.makeText(TableFmaIndexActivity.this, getString(R.string.msg_data_full), Toast.LENGTH_SHORT).show();
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
