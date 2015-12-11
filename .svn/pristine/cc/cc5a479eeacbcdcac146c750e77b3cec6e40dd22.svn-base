package me.zhangls.rilintech.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.adapter.TableTreatmentPlanAdapter;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.TableRehabilitationGoalInfo;
import me.zhangls.rilintech.model.TableTreatmentPlanInfo;
import me.zhangls.rilintech.utils.HttpUtils;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;

/**
 * Created by YANG on 15/9/12.
 */
public class TableTreatmentPlanIndexActivity extends BaseActivity {

    private Intent intent;
    //缓冲球
    private GoogleProgressBar progressBar;
    //listView
    private ListView mLsitView;
    //刷新布局
    private SwipeRefreshLayout swipeRefreshLayout;
    //网络变化选择框
    private AlertDialog dialog;
    //当前登录的用户ID
    private int user_auth_id;
    //患者ID
    private int patient_info_id;
    //返回的值转化成String字符串
    private String value;
    //封装返回数据的实例
    private ArrayList<TableTreatmentPlanInfo> list_infos = new ArrayList<>();
    //选中的item
    private TableRehabilitationGoalInfo info = null;
    //另起线程
    private MyThread myThread;
    //用来接受拿到数据后发来的消息
    private static Handler handler;
    //次数集合
    private List<String> list_counts = new ArrayList<>();
    //项目名称集合
    private ArrayList<String> list_purposes = new ArrayList<>();
    //持续时间集合
    private ArrayList<String> list_project_names = new ArrayList<>();
    //ListView加载器
    private TableTreatmentPlanAdapter adapter;
    //删除响应码
    private int delete_code = 0;
    //网络变化广播接收
    private NetBroadcastReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table_treatment_plan_index);

        getUserIDAndPatientIDBySharedPreferences();

        initView();

        myThread = new MyThread();
        myThread.start();

        //网络返回数据后得到通知刷新View
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                switch (msg.what) {
                    case 1:
                        progressBar.setVisibility(View.GONE);
                        setData();
                        break;
                    case 2:
                        progressBar.setVisibility(View.GONE);
                        break;
                }
            }
        };
    }

    /**
     * 获取当前的登录用户ID和当前选中的患者ID
     */
    private void getUserIDAndPatientIDBySharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("patientinfo", Activity.MODE_PRIVATE);
        patient_info_id = sharedPreferences.getInt("id", -1);

        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        user_auth_id = preferences.getInt("use_id", 2);
    }

    private void initView() {

        progressBar = (GoogleProgressBar) findViewById(R.id.google_progress);
        progressBar.setVisibility(View.GONE);
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

        mLsitView = (ListView) findViewById(R.id.lv_treatment_plan);
        mLsitView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(TableTreatmentPlanIndexActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.dialog_layout, null);

                TextView delete = (TextView) view1.findViewById(R.id.delete);
                TextView add = (TextView) view1.findViewById(R.id.add);
                TextView edit = (TextView) view1.findViewById(R.id.edit);
                TextView title = (TextView) view1.findViewById(R.id.title);
                title.setText("康复目标 第" + list_infos.get(position).getCounts() + "次");
                SpannableStringBuilder builder1 = new SpannableStringBuilder(title.getText().toString());
                ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
                builder1.setSpan(redSpan, 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder1.setSpan(new TextAppearanceSpan(null, 0, 60, null, null), 6, 7, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                title.setText(builder1);


                dialog = builder.show();
                dialog.getWindow().setContentView(view1);
                //删除
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressBar.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                        final String url = NetUrlAddress.tp_delete_url.replaceAll("aaa", list_infos.get(position).getRecord_id() + "");
                        final String param = "patient_info_id=" + patient_info_id
                                + "&user_auth_id=" + user_auth_id
                                + "&token=" + NetUrlAddress.token;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                delete_code = TableTreatmentPlanInfo.doPost(url, param);
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
                        intent = new Intent(TableTreatmentPlanIndexActivity.this, TableTreatmentPlanInfoActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putParcelable("info", list_infos.get(position));
                        mBundle.putBoolean("isAdd", false);
                        intent.putExtras(mBundle);
                        startActivity(intent);
                    }
                });
            }
        });

    }

    /**
     * 获得数据后重新赋值给ListView
     */
    private void setData() {

        list_counts.clear();
        list_purposes.clear();
        list_project_names.clear();
        //返回的数据size()>0说明不为空
        if (list_infos.size() > 0) {
            for (int i = 0; i < list_infos.size(); i++) {
                list_counts.add(list_infos.get(i).getCounts() + "");
                list_purposes.add(list_infos.get(i).getPurpose());
                list_project_names.add(list_infos.get(i).getProject_name());
            }
        }
        adapter = new TableTreatmentPlanAdapter(this, list_counts, list_purposes, list_project_names);
        mLsitView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    /**
     * 获取返回数据
     */
    class MyThread extends Thread {

        public void run() {

            String param = "patient_info_id=" + patient_info_id
                    + "&menu_id=" + 160
                    + "&user_auth_id=" + user_auth_id
                    + "&token=" + NetUrlAddress.token;

            try {
                value = HttpUtils.doPost(NetUrlAddress.tp_getData_url, param);
                L.d("re", value);
                JSONArray jsonArray = new JSONArray(value);
                list_infos = TableTreatmentPlanInfo.parseData(jsonArray);
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
     * 添加按钮
     *
     * @param view
     */
    public void add(View view) {
        intent = new Intent(this, TableTreatmentPlanInfoActivity.class);
        intent.putExtra("counts", list_infos.size());
        intent.putExtra("isAdd", true);
        startActivity(intent);
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
        progressBar.setVisibility(View.VISIBLE);
        myThread = new MyThread();
        myThread.start();
    }

}
