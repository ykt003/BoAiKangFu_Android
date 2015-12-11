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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.zhangls.rilintech.R;
import me.zhangls.rilintech.adapter.SpinnerBaseAdapter;
import me.zhangls.rilintech.adapter.TableAshworthAdapter;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.TableAshworthInfo;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.NetUtils;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;

/**
 * Created by YANG on 15/9/22.
 */
public class TableAshworthIndexActivity extends BaseActivity {

    //当前登录的用户ID
    private int user_auth_id;
    //患者ID
    private int patient_info_id;
    //删除响应码
    private int delete_code = 0;
    //返回的已存在记录量表集合
    private ArrayList<String> list_instructions_cloud = new ArrayList<>();
    //Intent
    private Intent intent;
    //评定日期
    private TextView date;
    //评定人
    private TextView maker;
    //评定说明
    private Spinner instructions;
    //spinner加载器
    private SpinnerBaseAdapter adapter_spinner;
    //listView加载器
    private TableAshworthAdapter adapter_listView;
    //listview
    private ListView listView;
    //下拉刷新
    private SwipeRefreshLayout swipeRefreshLayout;
    //评定说明的值
    private ArrayList<String> list_instructions = new ArrayList<>();
    //评定说明当前选中值
    private String instructions_value;
    //dialog
    private AlertDialog dialog = null;
    //部位
    private List<String> list_parts = new ArrayList<>();
    //左侧分数
    private ArrayList<String> list_left_scores = new ArrayList<>();
    //右侧分数
    private ArrayList<String> list_right_scores = new ArrayList<>();
    //当前显示的检查记录表
    private TableAshworthInfo info = null;
    //另起线程
    private MyThread myThread;
    //用来接受拿到数据后发来的消息
    private static Handler handler;
    //缓冲球
    private GoogleProgressBar google_progress;
    //返回的值转化成String字符串
    private String value;
    //封装返回数据的实例
    private ArrayList<TableAshworthInfo> list_infos = new ArrayList<>();
    //评定说明
    private Map<String, Integer> instructions_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_ashworth_index);

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

    /**
     * 初始化控件
     */
    private void initView() {

        //instructions_value = getString(R.string.evaluation0);

        instructions_map = new HashMap<>();
        instructions_map.put(getString(R.string.evaluation0), 0);
        instructions_map.put(getString(R.string.evaluation1), 1);
        instructions_map.put(getString(R.string.evaluation2), 2);
        instructions_map.put(getString(R.string.evaluation3), 3);
        instructions_map.put(getString(R.string.evaluation4), 4);
        instructions_map.put(getString(R.string.evaluation5), 5);
        instructions_map.put(getString(R.string.evaluation6), 6);

        //放数据
        list_instructions.add(getString(R.string.evaluation0));
        list_instructions.add(getString(R.string.evaluation1));
        list_instructions.add(getString(R.string.evaluation2));
        list_instructions.add(getString(R.string.evaluation3));
        list_instructions.add(getString(R.string.evaluation4));
        list_instructions.add(getString(R.string.evaluation5));
        list_instructions.add(getString(R.string.evaluation6));

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
        instructions = (Spinner) findViewById(R.id.instructions);
        adapter_spinner = new SpinnerBaseAdapter(this, android.R.layout.simple_spinner_item, instructions, list_instructions);
        adapter_spinner.setDropDownViewResource(R.layout.item_spinner_layout);
        instructions.setOnItemSelectedListener(new MyOnItemSelectedListener());
        instructions.setAdapter(adapter_spinner);

        listView = (ListView) findViewById(R.id.mas_list_item);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TableAshworthIndexActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.dialog_layout, null);

                TextView delete = (TextView) view1.findViewById(R.id.delete);
                TextView add = (TextView) view1.findViewById(R.id.add);
                TextView edit = (TextView) view1.findViewById(R.id.edit);

                dialog = builder.show();
                dialog.getWindow().setContentView(view1);
                //删除
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        google_progress.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                        final String param = "patient_info_id=" + patient_info_id
                                + "&user_auth_id=" + user_auth_id
                                + "&menu_id=" + 38
                                + "&groupid=" + (instructions_map.get(instructions_value) + 1)
                                + "&token=" + NetUrlAddress.token;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                delete_code = TableAshworthInfo.doPost(NetUrlAddress.Ashworth_delete_url, param);
                                if (delete_code == 200) {
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
                        add(view);
                    }
                });
                //编辑
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        intent = new Intent(TableAshworthIndexActivity.this, TableAshworthInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("info",info);
                        bundle.putBoolean("isAdd", false);
                        bundle.putStringArrayList("instructions", list_instructions_cloud);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        });

    }

    /**
     * 获取返回数据
     */
    class MyThread extends Thread {

        public void run() {

            String param = "patient_info_id=" + patient_info_id
                    + "&menu_id=" + 38
                    + "&user_auth_id=" + user_auth_id
                    + "&token=" + NetUrlAddress.token;

            try {
                value = TableAshworthInfo.getData(NetUrlAddress.Ashworth_getData_url, param);
                L.d("text", value);
                JSONObject jsonObject = new JSONObject(value);
                list_infos = TableAshworthInfo.parseCache(jsonObject);
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
            list_left_scores.clear();
            list_right_scores.clear();
            list_parts.clear();
            //隐藏listView
            listView.setVisibility(View.INVISIBLE);

            //重新赋值前要清空
            list_instructions_cloud.clear();
            //返回的数据size()>0说明不为空
            if (list_infos.size() > 0) {
                for (TableAshworthInfo m : list_infos) {
                    list_instructions_cloud.add(m.getInstructions());
                    //找出与当前spinner显示相匹配的量表数据
                    if (instructions_value.equals(m.getInstructions())) {
                        info = m;
                        L.d("text", "info===" + info.toString());
                    }
                }
            }

            //如果返回数据不为空则给两个list赋值
            if (info != null) {
                list_left_scores.addAll(info.getLeft_scores());
                list_right_scores.addAll(info.getRight_scores());
                //去重赋值
                for (String s : info.getParts()) {
                    if (!list_parts.contains(s)) {
                        list_parts.add(s);
                    }
                }
                if (null != info.getDate()) {
                    date.setText(info.getDate().substring(0, 10) + " " + info.getDate().substring(11, 16));
                    maker.setText(info.getMaker());
                } else {

                }
                listView.setVisibility(View.VISIBLE);
            }
            adapter_listView = new TableAshworthAdapter(this, list_parts, list_left_scores, list_right_scores);
            listView.setAdapter(adapter_listView);
            adapter_listView.notifyDataSetChanged();

        }
    }

    /**
     * 添加
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
            intent = new Intent(this, TableAshworthInfoActivity.class);
            intent.putExtra("isAdd", true);
            intent.putStringArrayListExtra("instructions", list_instructions_cloud);

            startActivity(intent);
        } else {
            Toast.makeText(TableAshworthIndexActivity.this, getString(R.string.msg_data_full), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 返回按钮
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
