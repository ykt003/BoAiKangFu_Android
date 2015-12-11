package me.zhangls.rilintech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.adapter.SpinnerBaseAdapter;
import me.zhangls.rilintech.adapter.TableInfoRadioGroupAdapter;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.Model_one;
import me.zhangls.rilintech.model.Model_three;
import me.zhangls.rilintech.model.Model_two;
import me.zhangls.rilintech.net.HttpUtil;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.ShowToast;

/**
 * Created by zsn on 15/11/11.
 */
public class TableChildInfoActivity extends TableParentInfoActivity {

    //添加时，传递过来的评定说明集合
    private ArrayList<String> lists_note;
    //添加时，传递过来的所有数据集合
    private ArrayList<Model_one> list_infos;
    //是否是添加
    public static boolean isAdd = true;
    //选中的评定说明,日期和评定人
    private String instructions_value, instructions_date, instructions_person;
    private List<String> delList = new ArrayList<>();
    //从index界面，传递过来的配置
    private int patient_info_id;
    private int user_auth_id;
    private int position;
    private int main_position;
    private String menu_lib_id;
    //拼接参数
    private String param;
    //新建响应码
    private int create_code = 0;
    //更新响应码
    private int update_code = 0;
    //handler
    private Handler handler;
    //添加时间
    private String time;
    private String value_str;
    //广播的标志字段
    public static final String ACTION_INTENT_TABLE_ADD = "me.zhangls.rilintech.activity.tableChildInfoActivity.add";
    public static final String ACTION_INTENT_TABLE_EDIT = "me.zhangls.rilintech.activity.tableChildInfoActivity.edit";
    //头标题
    private String indexTextName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //获取传递的特殊数据
        Bundle bundle = getIntent().getExtras();
        lists_note = bundle.getStringArrayList("lists_note");

        list_infos = bundle.getParcelableArrayList("list_infos");

        instructions_value = bundle.getString("instructions_value");
        instructions_date = bundle.getString("date");
        instructions_person = bundle.getString("maker");

        patient_info_id = bundle.getInt("patient_info_id");
        user_auth_id = bundle.getInt("user_auth_id");
        position = bundle.getInt("position");
        main_position = bundle.getInt("main_position");
        menu_lib_id = bundle.getString("menu_lib_id");

        indexTextName=bundle.getString("indexTextName");

        initData();
        initListener();

        //网络返回数据后得到通知刷新View
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        //progressBar.setVisibility(View.GONE);
                        if (200==create_code ) {
                            //发广播
                            Intent intent = new Intent(TableChildInfoActivity.ACTION_INTENT_TABLE_ADD);
                            intent.putExtra("spinner_value",spinner_value);
                            L.d("aa","sos--create-----spinner_value-------------------"+spinner_value);
                            sendBroadcast(intent);
                            finish();
                            ShowToast.Short(getString(R.string.msg_save_success));
                        } else if (200==update_code){
                            //发广播
                            Intent intent1 = new Intent(TableChildInfoActivity.ACTION_INTENT_TABLE_EDIT);
                            intent1.putExtra("spinner_value",spinner_value);
                            sendBroadcast(intent1);
                            finish();
                            ShowToast.Short(getString(R.string.msg_update_success));
                        } else if (100==create_code || 500==create_code) {
                            ShowToast.Short(getString(R.string.msg_save_fail));
                        } else if (100==update_code|| 500==create_code) {
                            ShowToast.Short(getString(R.string.msg_update_fail));
                        }
                        break;
                }
            }
        };
    }

    private void initListener() {

        key_back.setOnClickListener(this);
        //保存,上传至服务器
        key_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p = person.getText().toString();
                time = date_tv.getText().toString();

                ArrayList<Model_three> list_model_three_include_sum = TableInfoRadioGroupAdapter.getlist_modle_three_include_sum();
                Model_one model_one = TableInfoRadioGroupAdapter.getlist_modle_one(spinner_value);
                ArrayList<Model_one> model_one_list = TableInfoRadioGroupAdapter.getlist_modle_one_list();
                //判断数据是否填写完整
                if (dataIsEmpty(p, time, list_model_three_include_sum)) {
                    ShowToast.Short(getString(R.string.msg_isEmpty_warning));
                } else {

                    if("编辑".equals(title_name.getText().toString())){
                       value_str=getJsonDataEdit(model_one_list, model_one);
                    }
                    if("添加".equals(title_name.getText().toString())){
                        value_str=getJsonDataAdd(list_model_three_include_sum, model_one_list);
                    }

                    new MyThread().start();

                }
            }
        });
        date_tv.setOnClickListener(this);
        pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                date_tv.setText(getTime(date));
            }
        });
        pwTime.setCyclic(true);
    }

    /**
     * 获取返回数据
     */
    class MyThread extends Thread {

        public void run() {
            try {
                String url="&&user_auth_id="+user_auth_id+"&&patient_info_id="+patient_info_id+"&&menu_lib_id="+menu_lib_id;

                L.d("aa","value_str="+value_str);
                if ("添加".equals(title_name.getText().toString())) {
                    create_code = HttpUtil.doPost(NetUrlAddress.Common_create_url+url, value_str);
                } else {
                    L.d("aa","value_str_edit="+value_str);
                    update_code =HttpUtil.doPost(NetUrlAddress.Common_update_url+url, value_str);
                }

                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);

            } catch (Exception e) {
                L.d("aa", "MyThread出错了");
                e.printStackTrace();
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }
    }

//    private void writeData(String jsonData) {
//        try {
//            BufferedReader reader=new BufferedReader(new StringReader(jsonData));
//            BufferedWriter writer=new BufferedWriter(new FileWriter(saveFile));
//            int len=0;
//            char[] buffer=new char[1024];
//            while((len=reader.read(buffer))!=-1){
//                writer.write(buffer, 0, len);
//            }
//            writer.flush();
//            writer.close();
//            reader.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    /**
     * 编辑时，拼接json
     * @param list_model_one
     * @return
     */
    private String getJsonDataEdit(ArrayList<Model_one> list_model_one, Model_one model_one) {

        JSONArray arr_three =null;
        JSONArray arr_two=null;
        JSONObject jsonObject_model_one=null;
        try {
            arr_two = new JSONArray();
            for (int m = 0; m < list_model_one.get(0).getHeader_questions().size(); m++) {
                arr_three= new JSONArray();
                for (int n = 0; n < list_model_one.get(0).getHeader_questions().get(m).getQuestions().size(); n++) {
                    JSONObject jsonObject_three = new JSONObject();
                    Model_three model_three = model_one.getHeader_questions().get(m).getQuestions().get(n);
                    jsonObject_three.put("question", model_three.getQuestion());
                    jsonObject_three.put("value", model_three.getValue());
                    jsonObject_three.put("in_sum", model_three.isIn_sum());
                    jsonObject_three.put("id", model_three.getId());
                    arr_three.put(jsonObject_three);
                }
                    JSONObject jsonObject_model_two = new JSONObject();
                    Model_two model_two = model_one.getHeader_questions().get(m);
                    jsonObject_model_two.put("header", model_two.getHeader());
                    jsonObject_model_two.put("questions", arr_three);
                    arr_two.put(jsonObject_model_two);
            }

            jsonObject_model_one  = new JSONObject();
            jsonObject_model_one.put("evaluation_person", person.getText().toString());
            jsonObject_model_one.put("evaluation_time_note", spinner_value);
            jsonObject_model_one.put("evaluation_time", date_tv.getText().toString());
            jsonObject_model_one.put("has_sum", model_one.isHas_sum());
            jsonObject_model_one.put("has_grade", model_one.isHas_grade());

            String groupid = getGroupId();
            jsonObject_model_one.put("groupid", groupid);
            jsonObject_model_one.put("header_questions", arr_two);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject_model_one.toString();
    }

    /**
     * 添加时获取groupid
     */
    private String getGroupId() {
        String groupid="-1";
        if("初评".equals(spinner_value)){
            groupid="1";
        }else if("中评Ⅰ".equals(spinner_value)){
            groupid="2";
        }else if("末评".equals(spinner_value)){
            groupid="3";
        }else if("中评Ⅱ".equals(spinner_value)){
            groupid="4";
        }else if("中评Ⅲ".equals(spinner_value)){
            groupid="5";
        }else if("中评Ⅳ".equals(spinner_value)){
            groupid="6";
        }else if("中评Ⅴ".equals(spinner_value)){
            groupid="7";
        }
        return groupid;
    }

    /**
     * 添加时，拼接json
     * @param list_model_three
     * @param list_model_one
     * @return
     */
    private String getJsonDataAdd(List<Model_three> list_model_three, List<Model_one> list_model_one) {
        JSONArray arr_three =null;
        JSONObject jsonObject_model_one=null;
        JSONArray arr_two=null;
        try {
            arr_two = new JSONArray();
            for(int m=0;m<list_model_one.get(0).getHeader_questions().size();m++){

              arr_three= new JSONArray();
              for (int n = 0; n < list_model_one.get(0).getHeader_questions().get(m).getQuestions().size(); n++) {
                  JSONObject jsonObject_three = new JSONObject();

                  Model_three model_three = list_model_one.get(0).getHeader_questions().get(m).getQuestions().get(n);
                  jsonObject_three.put("question", model_three.getQuestion());
                  jsonObject_three.put("value", model_three.getValue());

                  jsonObject_three.put("in_sum", model_three.isIn_sum());
                  arr_three.put(jsonObject_three);
              }
              JSONObject jsonObject_model_two = new JSONObject();
              Model_two model_two = list_model_one.get(0).getHeader_questions().get(m);
              jsonObject_model_two.put("header", model_two.getHeader());
              jsonObject_model_two.put("questions", arr_three);
              arr_two.put(jsonObject_model_two);

            }

            Model_one model_one=list_model_one.get(0);
            jsonObject_model_one  = new JSONObject();
           // arr_one=new JSONArray();

            jsonObject_model_one.put("evaluation_person", person.getText().toString());
            jsonObject_model_one.put("evaluation_time_note", spinner_value);
            jsonObject_model_one.put("evaluation_time", date_tv.getText().toString());
            jsonObject_model_one.put("has_sum", model_one.isHas_sum());
            jsonObject_model_one.put("has_grade", model_one.isHas_grade());

            String groupid = getGroupId();
            jsonObject_model_one.put("groupid", groupid);
            jsonObject_model_one.put("header_questions", arr_two);

            //params.put("model",jsonObject_model_one);
         // arr_one.put(jsonObject_model_one);
          // jsonData = new JSONStringer().object().key("data").value(arr_one).endObject().toString();

          //  L.d("aa", "arr_one=" + arr_one.toString());
            L.d("aa","jsonObject_model_one="+jsonObject_model_one.toString());
           // L.d("aa", "  jsonData=" + jsonData);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject_model_one.toString();
    }

    @Override
    public void initData() {
        //标题
        if (isAdd) {
            title_name.setText("添加");
            //spinner中的数据
            for (String s : arr_instructions) {
                list_s.add(s);
                delList.add(s);
            }
            for (String note : lists_note) {
                if (list_s.contains(note)) {
                    list_s.remove(note);
                    delList.remove(note);
                }
            }
        } else {
            title_name.setText("编辑");
            instructions.setClickable(isAdd);
            isAdd = true;
            delList.addAll(list_s);
            delList.clear();
            delList.add(instructions_value);
            //初始化编辑的数据
            initEditData();
        }


        SpinnerBaseAdapter adapter = new SpinnerBaseAdapter(this, android.R.layout.simple_spinner_item, instructions, delList);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        instructions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_value = delList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        instructions.setAdapter(adapter);

        if ("添加".equals(title_name.getText().toString().trim())) {
            //添加
            //listview 初始化
            list_view.setAdapter(new TableInfoRadioGroupAdapter(TableChildInfoActivity.this, list_infos));
        } else if ("编辑".equals(title_name.getText().toString().trim())) {
            //编辑
            list_view.setAdapter(new TableInfoRadioGroupAdapter(TableChildInfoActivity.this, list_infos, instructions_value));
        }
    }

    /**
     * 编辑时显示的数据
     */
    private void initEditData() {
        person.setText(instructions_person);
        date_tv.setText(instructions_date);
    }

    /**
     * 获取系统当前时间
     *
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    /**
     * 判断是否数据填写完整
     *
     * @param person
     * @param date
     * @param list_model_three
     * @return
     */
    private boolean dataIsEmpty(String person, String date, List<Model_three> list_model_three) {
        if (TextUtils.isEmpty(person) || TextUtils.isEmpty(date) || radioButtonIsEmpty(list_model_three)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * radiobutton是否全部选中
     *
     * @param list_model_three
     * @return
     */
    private boolean radioButtonIsEmpty(List<Model_three> list_model_three) {
        for (Model_three model_three : list_model_three) {
            String value = model_three.getValue();
            if (value.equals("-1000")) {
                return true;
            }
        }
        return false;
    }

}
