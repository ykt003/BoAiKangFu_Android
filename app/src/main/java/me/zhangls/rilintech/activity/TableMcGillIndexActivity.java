package me.zhangls.rilintech.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.adapter.SpinnerBaseAdapter;
import me.zhangls.rilintech.adapter.TableIndexRadioGroupAdapter;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.Get_URL;
import me.zhangls.rilintech.model.Model_one;
import me.zhangls.rilintech.model.TableFragmentListViewDate;
import me.zhangls.rilintech.net.HttpUtil;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.ShowToast;

/**
 * Created by zsn on 15/11/11.
 */
public class TableMcGillIndexActivity extends TableParentIndexActivity {
    //子控件位置
    private int pos;
    //父控件位置
    private int main_position;
    //标题名字
    private String indexTextName;
    private Handler handler;
    //当前登录的用户ID
    private int user_auth_id;
    //患者ID
    private int patient_info_id;
    //menu_lib_id
    private String menu_lib_id;
    //返回的json-->字符串
    private String value = null;
    //返回的model
    private ArrayList<Model_one> list_infos = null;
    //另起线程拿数据
    private MyThread myThread;
    //异步任务
    private Task task;

    //评定说明集合
    private ArrayList<String> lists_note = new ArrayList<>();
    //评定人集合
    private ArrayList<String> lists_person = new ArrayList<>();
    //评定时间集合
    private ArrayList<String> lists_time = new ArrayList<>();
    //spinner适配器
    private SpinnerBaseAdapter adapter_spinner;
    //选中的spinner值
    private String instructions_value;
    //警告对话框
    private AlertDialog dialog;
    //广播接收者对象
    private MybroadCastReceiver mybroadCastReceiver;
    private boolean isDelete;
    //删除码
    private int delete_code;
    public static final String ACTION_INTENT_TABLE_McGill_DELETE = "me.zhangls.rilintech.activity.tableMcGillIndexActivity.delete";
    //popwindow是否显示
    private boolean isPopShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSharedPreferencesData();
        //广播过滤器对象
        IntentFilter myIntentFilter = new IntentFilter();
        mybroadCastReceiver = new MybroadCastReceiver();
        myIntentFilter.addAction(TableMcGillInfoActivity.ACTION_INTENT_TABLE_McGill_ADD);
        myIntentFilter.addAction(TableMcGillInfoActivity.ACTION_INTENT_TABLE_McGill_EDIT);
        myIntentFilter.addAction(ACTION_INTENT_TABLE_McGill_DELETE);
        //动态注册广播
        this.registerReceiver(mybroadCastReceiver, myIntentFilter);

        task = new Task();
        task.execute();


        //网络返回数据后得到通知刷新View
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1006:
                        //progressBar.setVisibility(View.GONE);
                        if (200 == delete_code) {
                            //发广播
                            Intent intent = new Intent(ACTION_INTENT_TABLE_McGill_DELETE);
                            sendBroadcast(intent);
                            ShowToast.Short(getString(R.string.msg_delete_success));
                        } else if (500 == delete_code) {
                            finish();
                            ShowToast.Short(getString(R.string.msg_delete_fail));
                        }
                        break;

                    case 1001:
                        setListView();
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

        menu_lib_id = getIntent().getStringExtra("menu_lib_id");

        pos = getIntent().getIntExtra("position", 0);
        main_position = getIntent().getIntExtra("main_position", 0);

        //instructions_value=getIntent().getStringExtra("spinner_value");
        indexTextName = getIntent().getStringExtra("indexTextName");

    }

    /**
     * 获取返回数据
     */
    class MyThread extends Thread {

        public void run() {
            if (isDelete) {
                //删除某一个item
                deleteSthItem();
                isDelete = false;
            } else {
                setListView();
            }
        }
    }


    /**
     * 设置标题
     */
    private void setIndexTextName() {
        TableFragmentListViewDate tableFragmentListViewDate = new TableFragmentListViewDate(this);

        if (main_position == 0 && pos == 0) {

        } else {
            indexTextName = tableFragmentListViewDate.getList_more_text().get(main_position).get(pos);
        }

        title.setText(indexTextName);
    }

    private void initListener() {

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(lists_note.size()>=7){
                    showMsg("评定已全部完成");
                    return;
                }

                Intent intent = new Intent(TableMcGillIndexActivity.this, TableMcGillInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("lists_note", lists_note);
                L.d("ss", "333=" + list_infos.get(0).getHeader_questions().get(0).getQuestions().get(0).isIn_sum());
                bundle.putParcelableArrayList("list_infos", list_infos);
                bundle.putInt("patient_info_id", patient_info_id);
                bundle.putInt("user_auth_id", user_auth_id);
                bundle.putString("menu_lib_id", menu_lib_id);
                bundle.putInt("position", pos);
                bundle.putInt("main_position", main_position);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //当不存在数据时，listview等其他控件不可点击
       // L.d("aa","lists_note.get(0)="+lists_note.get(0));
        if ("".equals(lists_note.get(0)) || "null".equals(lists_note.get(0))) {
            isPopShow=false;
            listView.setClickable(false);
            instructions.setClickable(false);
            maker.setEnabled(false);
            date.setEnabled(false);

        } else {
            isPopShow=true;
            listView.setClickable(true);
            listView.setEnabled(true);
            instructions.setClickable(true);
            maker.setEnabled(false);
            date.setEnabled(false);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(TableMcGillIndexActivity.this);
                    View view1 = getLayoutInflater().inflate(R.layout.dialog_layout, null);

                    TextView delete = (TextView) view1.findViewById(R.id.delete);
                    TextView add = (TextView) view1.findViewById(R.id.add);
                    TextView edit = (TextView) view1.findViewById(R.id.edit);
                    if (isPopShow) {
                        dialog = builder.show();
                        dialog.getWindow().setContentView(view1);
                    }
                    //编辑
                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(TableMcGillIndexActivity.this, TableMcGillInfoActivity.class);
                            Bundle bundle = new Bundle();
                            TableMcGillInfoActivity.isAdd = false;
                            bundle.putStringArrayList("lists_note", lists_note);
                            bundle.putParcelableArrayList("list_infos", list_infos);
                            bundle.putString("instructions_value", instructions_value);
                            bundle.putString("date", date.getText().toString());
                            bundle.putString("maker", maker.getText().toString());
                            bundle.putInt("patient_info_id", patient_info_id);
                            bundle.putInt("user_auth_id", user_auth_id);
                            bundle.putString("menu_lib_id", menu_lib_id);
                            bundle.putInt("position ", pos);
                            bundle.putInt("main_position ", main_position);
                            bundle.putString("indexTextName", indexTextName);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    //添加
                    add.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();
                            if (lists_note.size() >= 7) {
                                showMsg("评定已全部完成");
                                return;
                            }

                            Intent intent = new Intent(TableMcGillIndexActivity.this, TableMcGillInfoActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("lists_note", lists_note);
                            L.d("ss", "222=" + list_infos.get(0).getHeader_questions().get(0).getQuestions().get(0).isIn_sum());
                            bundle.putParcelableArrayList("list_infos", list_infos);

                            bundle.putInt("patient_info_id", patient_info_id);
                            bundle.putInt("user_auth_id", user_auth_id);
                            bundle.putString("menu_lib_id", menu_lib_id);
                            bundle.putInt("position ", pos);
                            bundle.putInt("main_position ", main_position);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    //删除
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            isDelete = true;
                            new MyThread().start();

                        }
                    });
                }
            });

            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    lists_note.clear();
                    lists_person.clear();
                    lists_time.clear();

                    task = new Task();
                    task.execute();
                }
            });
        }
    }

    /**
     * 删除某个item
     */
    private void deleteSthItem() {
        L.d("aa", "instructions_value=" + instructions_value);
        String groupid = getGroupId(instructions_value);

        String url = "&&groupid=" + groupid + "&&patient_info_id=" +
                patient_info_id + "&&menu_lib_id=" + menu_lib_id+"&&user_auth_id="+user_auth_id;
        delete_code = HttpUtil.doPost(NetUrlAddress.Common_destroy_url + url, "");

        Message message = new Message();
        message.what = 1006;
        handler.sendMessage(message);
    }

    /**
     * 添加时获取groupid
     */
    private String getGroupId(String instructions_v) {
        String groupid = "-1";
        if ("初评".equals(instructions_v)) {
            groupid = "1";
        } else if ("中评Ⅰ".equals(instructions_v)) {
            groupid = "2";
        } else if ("末评".equals(instructions_v)) {
            groupid = "3";
        } else if ("中评Ⅱ".equals(instructions_v)) {
            groupid = "4";
        } else if ("中评Ⅲ".equals(instructions_v)) {
            groupid = "5";
        } else if ("中评Ⅳ".equals(instructions_v)) {
            groupid = "6";
        } else if ("中评Ⅴ".equals(instructions_v)) {
            groupid = "7";
        }
        return groupid;
    }

    /**
     * 从网络端请求数据
     *
     * @return
     */
    @Override
    public List<Model_one> initData() {

        String param = "patient_info_id=" + patient_info_id
                + "&menu_lib_id=" + menu_lib_id
                + "&user_auth_id=" + user_auth_id
                + "&token=" + NetUrlAddress.token;

        L.d("patient_info_id=" + patient_info_id + "menu_lib_id=" + menu_lib_id + "user_auth_id=" + user_auth_id);

        try {

            value = Get_URL.getData(NetUrlAddress.GET_TABLE_DATA_URL, param);
            L.d("ii","value="+value);
            list_infos = Get_URL.setupViews(value);

            for (int i = 0; i < list_infos.size(); i++) {

                String person = list_infos.get(i).getEvaluation_person();
                String time = list_infos.get(i).getEvaluation_time();
                String note = list_infos.get(i).getEvaluation_time_note();

                lists_note.add(note);
                lists_person.add(person);
                lists_time.add(time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_infos;

    }

    /**
     * ListView赋值
     */
    private void setListView() {
        if (list_infos != null) {
            listView.setAdapter(new TableIndexRadioGroupAdapter(this, list_infos, instructions_value));
        } else {
            L.d("yy", "解析失败，返回数据==" + value);
        }
    }

    private class Task extends AsyncTask<String, String, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            list_infos = (ArrayList<Model_one>) initData();
            if (list_infos == null) {
                return 0;

            } else {
                return list_infos.size();
            }
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            if (0 != s) {
                //获取标题名称
                setIndexTextName();
                //显示评定说明、日期和评定人
                setNoteAndTimeAndPerson();
                //显示listview
                setListView();
                //监听器
                initListener();

                Message message = new Message();
                message.what = 1001;
                handler.sendMessage(message);

            } else {
                fl_empty.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 设置评定说明、日期和评定人
     */
    private void setNoteAndTimeAndPerson() {
        L.d("aa", "lists_note=" + lists_note.size());
        int index=0;
        String time_value = null;
        String person_value = null;
        L.d("aa","lists_person.size()="+lists_person.size());
        L.d("aa","lists_person="+lists_person.get(0).toString());
        //查找到当前spinner值的位置
        if (lists_person.size() > 0) {
            if(lists_note.contains(instructions_value)){

                index = lists_note.indexOf(instructions_value);
                instructions_value = lists_note.get(index);
                time_value = lists_time.get(index);
                person_value = lists_person.get(index);

            }

            L.d("aa", "time_value==" + time_value);

            if ("" == time_value || time_value == null || time_value.length() <= 0) {
                date.setText("");

            } else if(time_value.length()>=15) {
                date.setText(time_value.substring(0, 10) + " " + time_value.substring(11, 16));
            }else{
                date.setText(time_value);
            }

            maker.setText(person_value);

            L.d("aa","maker.getText().toString()="+maker.getText().toString());
            if(maker.getText().toString().equals("")){
                listView.setClickable(false);
                //listView.setEnabled(false);
                isPopShow=false;
            }

            adapter_spinner = new SpinnerBaseAdapter(TableMcGillIndexActivity.this, android.R.layout.simple_spinner_item, instructions, lists_note);
            adapter_spinner.setDropDownViewResource(R.layout.item_spinner_layout);
            instructions.setOnItemSelectedListener(new MyOnItemSelectedListener());
            instructions.setAdapter(adapter_spinner);

            instructions.setSelection(index, true);
        }else {
            date.setText("");
            listView.setClickable(false);
            //listView.setEnabled(false);
            isPopShow=false;
        }
    }

    /**
     * 监听 Spinner点选
     */
    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (lists_note.size() > 0) {
                instructions_value = lists_note.get(position);

                if ("" == lists_time.get(position) || lists_time.get(position) == null || lists_time.get(position).length() <= 0) {

                }else if(lists_time.get(position).length()>=15) {
                    date.setText(lists_time.get(position).substring(0, 10) + " " + lists_time.get(position).substring(11, 16));
                }else{
                    date.setText(lists_time.get(position));
                }
                maker.setText(lists_person.get(position));
            }
            setListView();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /**
     * 广播接收者，实现相应的事件的处理
     */

    private class MybroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String spinner_value = intent.getStringExtra("spinner_value");
            L.d("aa", "broadcast-----action ======" + action);
            if (TableMcGillInfoActivity.ACTION_INTENT_TABLE_McGill_ADD.equals(action)) {
                lists_note.clear();
                lists_person.clear();
                lists_time.clear();

                instructions_value = spinner_value;
                task = new Task();
                task.execute();

                //google_progress.setVisibility(View.VISIBLE);

            } else if (TableMcGillInfoActivity.ACTION_INTENT_TABLE_McGill_EDIT.equals(action)) {
                L.d("aa", "edit---ok");
                lists_note.clear();
                lists_person.clear();
                lists_time.clear();

                instructions_value = spinner_value;
                L.d("aa", "instructions_value=" + instructions_value);
                task = new Task();
                task.execute();

            } else if (ACTION_INTENT_TABLE_McGill_DELETE.equals(action)) {
                lists_note.clear();
                lists_person.clear();
                lists_time.clear();

                L.d("aa", "action=" + action);
                task = new Task();
                task.execute();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mybroadCastReceiver);
    }
}
