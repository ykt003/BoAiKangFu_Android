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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.adapter.SpinnerBaseAdapter;
import me.zhangls.rilintech.adapter.TableNervousSystemInfoAdapter;
import me.zhangls.rilintech.application.MyApplication;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.TableNervousSystemModel;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;

/**
 * Created by YANG on 15/11/27.
 */
public class TableNervousSystemInfoActivity extends BaseActivity implements View.OnClickListener{
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
    private TableNervousSystemModel info = null;
    //是否新建
    private Boolean isAdd = true;
    //返回键
    private ImageView key_back;
    //保存键
    private ImageView key_save;
    //评定日期
    private TextView date;
    //评定人
    private EditText author;
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
    //ListView
    private ListView listView;
    //adapter
    private TableNervousSystemInfoAdapter adapter_listView;
    //项目
    private ArrayList<String> list_projects = new ArrayList<>();
    //部位
    private ArrayList<String> list_parts = new ArrayList<>();
    //左侧主动分数
    private ArrayList<String> list_left_init_scores = new ArrayList<>();
    //右侧主动分数
    private ArrayList<String> list_right_init_scores = new ArrayList<>();
    //adapter的返回值map
    private HashMap<Integer, String> map_init_lefts;
    private HashMap<Integer, String> map_init_rights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_nervous_system_info);

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
     * 获取到从indexActivity传过来的数据
     */
    private void getMyIntent() {

        isAdd = getIntent().getBooleanExtra("isAdd", true);
        if (isAdd) {
            list_instructions_cloud = getIntent().getStringArrayListExtra("instructions");
            list_projects = getIntent().getStringArrayListExtra("list_projects");
            list_parts = getIntent().getStringArrayListExtra("list_parts");

        } else {
            info = getIntent().getParcelableExtra("info");
            list_instructions_cloud = getIntent().getStringArrayListExtra("instructions");
            list_projects = getIntent().getStringArrayListExtra("list_projects");
            list_parts = getIntent().getStringArrayListExtra("list_parts");
        }
    }

    private void initView() {

        instructions_map = new HashMap<>();
        instructions_map.put(getString(R.string.evaluation0), 0);
        instructions_map.put(getString(R.string.evaluation1), 1);
        instructions_map.put(getString(R.string.evaluation2), 2);
        instructions_map.put(getString(R.string.evaluation3), 3);
        instructions_map.put(getString(R.string.evaluation4), 4);
        instructions_map.put(getString(R.string.evaluation5), 5);
        instructions_map.put(getString(R.string.evaluation6), 6);

        key_back = (ImageView) findViewById(R.id.tittle_back);
        key_back.setOnClickListener(this);
        key_save = (ImageView) findViewById(R.id.title_save);
        key_save.setOnClickListener(this);
        progressBar = (GoogleProgressBar) findViewById(R.id.google_progress);
        progressBar.setVisibility(View.GONE);

        author = (EditText) findViewById(R.id.author);

        date = (TextView) findViewById(R.id.date);
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
        adapter = new SpinnerBaseAdapter(this, android.R.layout.simple_spinner_item,
                spinner_instructions, list_instructions_new);
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


        listView = (ListView) findViewById(R.id.rom_list_item);


        setData();
    }

    /**
     * 给所有控件赋值
     */
    private void setData() {

        if (info != null) {

            author.setText(info.getMaker());

            date.setText(info.getDate().substring(0, 10) + " " + info.getDate().substring(11, 16));

            for (int i = 0; i < list_instructions_new.size(); i++) {
                if ((list_instructions_new.get(i)).equals(info.getInstructions())) {
                    spinner_instructions.setSelection(i);
                }
            }
        }

        if (info != null) {
            for (String s1 : info.getLeft_init_score().split(",")) {
                list_left_init_scores.add(s1);
            }
            for (String s3 : info.getRight_init_score().split(",")) {
                list_right_init_scores.add(s3);
            }

        }

        adapter_listView = new TableNervousSystemInfoAdapter(this, list_projects, list_parts,
                list_left_init_scores, list_right_init_scores);
        listView.setAdapter(adapter_listView);
        //adapter_listView.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tittle_back:
                this.finish();
                break;
            case R.id.title_save:
                if (dataIsEmpty()) {

                } else {
                    progressBar.setVisibility(View.VISIBLE);

                    map_init_lefts = TableNervousSystemInfoAdapter.getMap().get(0);
                    map_init_rights = TableNervousSystemInfoAdapter.getMap().get(1);

                    String left = "";
                    for (Map.Entry<Integer,String> entry : map_init_lefts.entrySet()){
                        left += entry.getValue() + ",";
                    }
                    String right = "";
                    for (Map.Entry<Integer,String> entry : map_init_rights.entrySet()){
                        right += entry.getValue() + ",";
                    }

                    param = "patient_info_id=" + patient_info_id
                            + "&user_auth_id=" + user_auth_id
                            + "&menu_lib_id=" + 213
                            + "&nerve_sys_eval[evaluation_person]=" + author.getText()
                            + "&nerve_sys_eval[evaluation_time]=" + date.getText()
                            + "&nerve_sys_eval[evaluation_time_note]=" + spinner_value
                            + "&nerve_sys_eval[left]=" + left
                            + "&nerve_sys_eval[right]=" + right

                            + "&token=" + NetUrlAddress.token;


                    new MyThread().start();
                }
                break;
            case R.id.date:

                pwTime.showAtLocation(date, Gravity.BOTTOM, 0, 0, new Date());
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
            Looper.prepare();
            try {
                if (isAdd) {
                    create_code = TableNervousSystemModel.doPost(NetUrlAddress.Nse_create_url, param);
                } else {
                    String url = NetUrlAddress.Nse_update_url.replaceAll("aaa", info.getRecord_id() + "");
                    update_code = TableNervousSystemModel.doPost(url, param + "&nerve_sys_eval[id]=" + info.getRecord_id());
                }
                if (create_code == 200) {
                    TableNervousSystemInfoActivity.this.finish();
                    ShowToast.Short(getString(R.string.msg_save_success));
                } else if (update_code == 200) {
                    TableNervousSystemInfoActivity.this.finish();
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
     * 判断是否有没有完整填写数据
     *
     * @return
     */
    boolean dataIsEmpty() {
        if (TextUtils.isEmpty(date.getText())) {
            ShowToast.Short(getString(R.string.msg_date));
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是不是null值
     *
     * @param s
     * @return
     */
    String isNull(Object s) {
        if (s == null) {
            return "";
        } else {
            return s.toString();
        }
    }
}
