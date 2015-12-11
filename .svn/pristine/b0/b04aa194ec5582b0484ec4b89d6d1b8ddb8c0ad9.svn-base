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
import java.util.List;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.adapter.SpinnerBaseAdapter;
import me.zhangls.rilintech.adapter.TableMmtAdapter;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.TableMmtInfo;
import me.zhangls.rilintech.utils.HttpUtils;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.NetUtils;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;

/**
 * Created by YANG on 15/9/6.
 */
public class TableMmtIndexActivity extends BaseActivity{

    //当前登录的用户ID
    private int user_auth_id;
    //患者ID
    private int patient_info_id;
    //删除响应码
    private int delete_code = 0;
    /**
     * 返回的已存在记录量表集合
     */
    private ArrayList<String> list_instructions_cloud = new ArrayList<>();

    private Intent intent;
    /**
     * 评定日期
     */
    private TextView date;
    /**
     * 评定说明
     */
    private Spinner instructions;
    /**
     * spinner加载器
     */
    private SpinnerBaseAdapter adapter_spinner;

    private TableMmtAdapter adapter_listView;

    private ListView listView;
    //下拉刷新
    private SwipeRefreshLayout swipeRefreshLayout;
    /**
     * 评定说明的值
     */
    private ArrayList<String> list_instructions = new ArrayList<>();

    /**
     * 评定说明当前选中值
     */
    private String instructions_value = "初评";
    //dialog
    private AlertDialog dialog = null;
    //网络变化广播接收
    private NetBroadcastReceiver receiver;
    /**
     * 部位
     */
    private List<String> list_parts = new ArrayList<>();
    /**
     * 动作
     */
    private ArrayList<String> list_actions = new ArrayList<>();
    /**
     * 左侧分数
     */
    private ArrayList<String> list_lefts = new ArrayList<>();
    /**
     * 右侧分数
     */
    private ArrayList<String> list_rights = new ArrayList<>();
    /**
     * 当前显示的检查记录表
     */
    private TableMmtInfo tableMmtInfo = null;
    //另起线程
    private MyThread myThread;
    //用来接受拿到数据后发来的消息
    private static Handler handler;
    //缓冲球
    private GoogleProgressBar google_progress;
    //返回的值转化成String字符串
    private String value;
    //封装返回数据的实力
    private ArrayList<TableMmtInfo> list_TableMmtInfos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table_mmt_index);

        SharedPreferences sharedPreferences = getSharedPreferences("patientinfo", Activity.MODE_PRIVATE);
        patient_info_id = sharedPreferences.getInt("id", -1);

        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        user_auth_id = preferences.getInt("use_id", 2);

        initView();

        try {
            google_progress.setVisibility(View.VISIBLE);
            myThread = new MyThread();
            myThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void initView() {
        list_instructions.add("初评");
        list_instructions.add("中评Ⅰ");
        list_instructions.add("中评Ⅱ");
        list_instructions.add("中评Ⅲ");
        list_instructions.add("中评Ⅳ");
        list_instructions.add("中评Ⅴ");
        list_instructions.add("末评");

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
        instructions = (Spinner) findViewById(R.id.instructions);
        adapter_spinner = new SpinnerBaseAdapter(this, android.R.layout.simple_spinner_item, instructions, list_instructions);
        adapter_spinner.setDropDownViewResource(R.layout.item_spinner_layout);
        instructions.setOnItemSelectedListener(new MyOnItemSelectedListener());
        instructions.setAdapter(adapter_spinner);

        listView = (ListView) findViewById(R.id.mmt_list_item);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TableMmtIndexActivity.this);
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
                        final String url = NetUrlAddress.delete_url.replaceAll("468", tableMmtInfo.getRecord_id() + "");
                        final String param = "patient_info_id=" + patient_info_id  //104
                                + "&user_auth_id=" + user_auth_id   //2
                                + "&token=" + NetUrlAddress.token;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                delete_code = TableMmtInfo.delete(url, param);
                                if (delete_code == 200) {
                                    ShowToast.Short("删除成功...");

                                    //刷新列表，重新获取数据
                                    myThread = new MyThread();
                                    myThread.start();
                                    Message message = new Message();
                                    message.what = 2;
                                    handler.sendMessage(message);

                                } else {
                                    ShowToast.Short("删除失败...");
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
                        intent = new Intent(TableMmtIndexActivity.this, TableMmtInfoActivity.class);
                        intent.putExtra("isAdd", false);
                        intent.putExtra("record_id", tableMmtInfo.getRecord_id());
                        intent.putExtra("date", tableMmtInfo.getDate().substring(0, 10) + " " + tableMmtInfo.getDate().substring(11, 16));
                        intent.putStringArrayListExtra("instructions", list_instructions_cloud);
                        intent.putStringArrayListExtra("lefts", list_lefts);
                        intent.putStringArrayListExtra("rights", list_rights);
                        intent.putExtra("instruction", instructions_value);
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

            String param = "patient_info_id=" + patient_info_id  //104
                    + "&menu_id=" + 95
                    + "&user_auth_id=" + user_auth_id   //2
                    + "&token=" + NetUrlAddress.token;

            try {
                value = HttpUtils.doPost(NetUrlAddress.getData_url, param);
                L.d("text", value);
                JSONObject jsonObject = new JSONObject(value);
                list_TableMmtInfos = TableMmtInfo.parseCache(jsonObject);
                //得到返回的数据，发消息通知刷新View
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            } catch (Exception e) {

                L.d("text", "出错了");
                Looper.prepare();
                ShowToast.Short("获取数据失败...");
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
                Looper.loop();
                e.printStackTrace();
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

            //每次点击spinner都要重置mmtTableInfo实例为null,并清空数据
            tableMmtInfo = null;
            date.setText("");
            list_parts.clear();
            list_actions.clear();
            list_lefts.clear();
            list_rights.clear();
            //隐藏listView
            listView.setVisibility(View.INVISIBLE);

            list_parts.add(getString(R.string.mmt_part1));
            list_parts.add(getString(R.string.mmt_part1));
            list_parts.add(getString(R.string.mmt_part2));
            list_parts.add(getString(R.string.mmt_part2));
            list_parts.add(getString(R.string.mmt_part2));
            list_parts.add(getString(R.string.mmt_part3));
            list_parts.add(getString(R.string.mmt_part3));
            list_parts.add(getString(R.string.mmt_part3));
            list_parts.add(getString(R.string.mmt_part3));
            list_parts.add(getString(R.string.mmt_part4));
            list_parts.add(getString(R.string.mmt_part4));
            list_parts.add(getString(R.string.mmt_part4));
            list_parts.add(getString(R.string.mmt_part4));
            list_parts.add(getString(R.string.mmt_part4));
            list_parts.add(getString(R.string.mmt_part4));
            list_parts.add(getString(R.string.mmt_part4));
            list_parts.add(getString(R.string.mmt_part5));
            list_parts.add(getString(R.string.mmt_part5));
            list_parts.add(getString(R.string.mmt_part6));
            list_parts.add(getString(R.string.mmt_part6));
            list_parts.add(getString(R.string.mmt_part7));
            list_parts.add(getString(R.string.mmt_part7));
            list_parts.add(getString(R.string.mmt_part8));
            list_parts.add(getString(R.string.mmt_part8));
            list_parts.add(getString(R.string.mmt_part8));
            list_parts.add(getString(R.string.mmt_part8));
            list_parts.add(getString(R.string.mmt_part8));
            list_parts.add(getString(R.string.mmt_part8));
            list_parts.add(getString(R.string.mmt_part8));
            list_parts.add(getString(R.string.mmt_part9));
            list_parts.add(getString(R.string.mmt_part9));
            list_parts.add(getString(R.string.mmt_part9));
            list_parts.add(getString(R.string.mmt_part9));
            list_parts.add(getString(R.string.mmt_part9));
            list_parts.add(getString(R.string.mmt_part9));
            list_parts.add(getString(R.string.mmt_part9));
            list_parts.add(getString(R.string.mmt_part10));
            list_parts.add(getString(R.string.mmt_part10));
            list_parts.add(getString(R.string.mmt_part10));
            list_parts.add(getString(R.string.mmt_part10));
            list_parts.add(getString(R.string.mmt_part10));
            list_parts.add(getString(R.string.mmt_part10));
            list_parts.add(getString(R.string.mmt_part11));
            list_parts.add(getString(R.string.mmt_part11));
            list_parts.add(getString(R.string.mmt_part12));
            list_parts.add(getString(R.string.mmt_part12));
            list_parts.add(getString(R.string.mmt_part12));
            list_parts.add(getString(R.string.mmt_part12));
            list_parts.add(getString(R.string.mmt_part13));
            list_parts.add(getString(R.string.mmt_part13));
            list_parts.add(getString(R.string.mmt_part13));
            list_parts.add(getString(R.string.mmt_part13));
            list_parts.add(getString(R.string.mmt_part14));
            list_parts.add(getString(R.string.mmt_part14));
            list_parts.add(getString(R.string.mmt_part14));
            list_parts.add(getString(R.string.mmt_part14));

            list_actions.add(getString(R.string.mmt_action1));
            list_actions.add(getString(R.string.mmt_action2));
            list_actions.add(getString(R.string.mmt_action3));
            list_actions.add(getString(R.string.mmt_action4));
            list_actions.add(getString(R.string.mmt_action5));
            list_actions.add(getString(R.string.mmt_action6));
            list_actions.add(getString(R.string.mmt_action7));
            list_actions.add(getString(R.string.mmt_action8));
            list_actions.add(getString(R.string.mmt_action9));
            list_actions.add(getString(R.string.mmt_action10));
            list_actions.add(getString(R.string.mmt_action11));
            list_actions.add(getString(R.string.mmt_action12));
            list_actions.add(getString(R.string.mmt_action13));
            list_actions.add(getString(R.string.mmt_action14));
            list_actions.add(getString(R.string.mmt_action15));
            list_actions.add(getString(R.string.mmt_action16));
            list_actions.add(getString(R.string.mmt_action17));
            list_actions.add(getString(R.string.mmt_action18));
            list_actions.add(getString(R.string.mmt_action19));
            list_actions.add(getString(R.string.mmt_action20));
            list_actions.add(getString(R.string.mmt_action21));
            list_actions.add(getString(R.string.mmt_action22));
            list_actions.add(getString(R.string.mmt_action23));
            list_actions.add(getString(R.string.mmt_action24));
            list_actions.add(getString(R.string.mmt_action25));
            list_actions.add(getString(R.string.mmt_action26));
            list_actions.add(getString(R.string.mmt_action27));
            list_actions.add(getString(R.string.mmt_action28));
            list_actions.add(getString(R.string.mmt_action29));
            list_actions.add(getString(R.string.mmt_action30));
            list_actions.add(getString(R.string.mmt_action31));
            list_actions.add(getString(R.string.mmt_action32));
            list_actions.add(getString(R.string.mmt_action33));
            list_actions.add(getString(R.string.mmt_action34));
            list_actions.add(getString(R.string.mmt_action35));
            list_actions.add(getString(R.string.mmt_action36));
            list_actions.add(getString(R.string.mmt_action37));
            list_actions.add(getString(R.string.mmt_action38));
            list_actions.add(getString(R.string.mmt_action39));
            list_actions.add(getString(R.string.mmt_action40));
            list_actions.add(getString(R.string.mmt_action41));
            list_actions.add(getString(R.string.mmt_action42));
            list_actions.add(getString(R.string.mmt_action43));
            list_actions.add(getString(R.string.mmt_action44));
            list_actions.add(getString(R.string.mmt_action45));
            list_actions.add(getString(R.string.mmt_action46));
            list_actions.add(getString(R.string.mmt_action47));
            list_actions.add(getString(R.string.mmt_action48));
            list_actions.add(getString(R.string.mmt_action49));
            list_actions.add(getString(R.string.mmt_action50));
            list_actions.add(getString(R.string.mmt_action51));
            list_actions.add(getString(R.string.mmt_action52));
            list_actions.add(getString(R.string.mmt_action53));
            list_actions.add(getString(R.string.mmt_action54));
            list_actions.add(getString(R.string.mmt_action55));
            list_actions.add(getString(R.string.mmt_action56));

            //重新赋值前要清空
            list_instructions_cloud.clear();
            //返回的数据size()>0说明不为空
            if (list_TableMmtInfos.size() > 0) {
                for (TableMmtInfo m : list_TableMmtInfos) {
                    list_instructions_cloud.add(m.getInstructions());
                    L.d("ttt", m.getInstructions());
                    //找出与当前spinner显示相匹配的量表数据
                    if (instructions_value.equals(m.getInstructions())) {
                        tableMmtInfo = m;
                    }
                }
            }
            //如果返回数据不为空则给两个list赋值
            if (tableMmtInfo != null) {
                String str = tableMmtInfo.getLeft();
                for (String s : str.split(",")) {
                    list_lefts.add(s);
                }

                String str1 = tableMmtInfo.getRight();
                for (String s : str1.split(",")) {
                    list_rights.add(s);
                }

                if (null != tableMmtInfo.getDate()) {
                    date.setText(tableMmtInfo.getDate().substring(0, 10) + " " + tableMmtInfo.getDate().substring(11, 16));
                } else {
                    date.setText("");
                }
                listView.setVisibility(View.VISIBLE);

            }
            adapter_listView = new TableMmtAdapter(this, list_parts, list_actions, list_lefts, list_rights);
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
        if (list_TableMmtInfos.size() > 0) {

            for (int i = 0; i < list_instructions_cloud.size(); i++) {
                if (list.contains(list_instructions_cloud.get(i))) {
                    list.remove(list_instructions_cloud.get(i));
                }
            }
        }

        if (list.size() > 0) {

            intent = new Intent(this, TableMmtInfoActivity.class);
            intent.putExtra("isAdd", true);
            intent.putStringArrayListExtra("instructions", list_instructions_cloud);
            intent.putStringArrayListExtra("lefts", list_lefts);
            intent.putStringArrayListExtra("rights", list_rights);
            intent.putExtra("instruction", instructions_value);

            startActivity(intent);
        } else {
            Toast.makeText(TableMmtIndexActivity.this, "已满..", Toast.LENGTH_SHORT).show();
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
