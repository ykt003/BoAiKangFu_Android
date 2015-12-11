package me.zhangls.rilintech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.adapter.SpinnerBaseAdapter;
import me.zhangls.rilintech.adapter.TableInfoMcGillAdapter;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.Model_one;
import me.zhangls.rilintech.model.Model_three;
import me.zhangls.rilintech.model.Model_two;
import me.zhangls.rilintech.net.HttpUtil;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.ShowToast;

/**
 * Created by zsn on 15/11/25.
 */
public class TableMcGillInfoActivity extends TableMcGillParentInfoActivity {

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
    public static final String ACTION_INTENT_TABLE_McGill_ADD = "me.zhangls.rilintech.activity.tableMcGillInfoActivity.add";
    public static final String ACTION_INTENT_TABLE_McGill_EDIT = "me.zhangls.rilintech.activity.tableMcGillInfoActivity.edit";
    //头标题
    private String indexTextName;

    //给每个新添加的目的做标记
    private int tag = 0;
    //存放tag标记的EditText
    private ArrayList<Integer> list_tag = new ArrayList<>();
    //删除
    private ImageView delete_purpose;
    //拼接多个edittext的值
    private String new_purpose = "";
    //加号（加载的 布局数量）
    private int addCount=0;


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

        indexTextName = bundle.getString("indexTextName");

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
                        if (200 == create_code) {
                            //发广播
                            Intent intent = new Intent(TableMcGillInfoActivity.ACTION_INTENT_TABLE_McGill_ADD);
                            intent.putExtra("spinner_value", spinner_value);
                            L.d("aa", "sos--create-----spinner_value-------------------" + spinner_value);
                            sendBroadcast(intent);
                            finish();
                            ShowToast.Short(getString(R.string.msg_save_success));
                        } else if (200 == update_code) {
                            //发广播
                            Intent intent1 = new Intent(TableMcGillInfoActivity.ACTION_INTENT_TABLE_McGill_EDIT);
                            intent1.putExtra("spinner_value", spinner_value);
                            L.d("aa", "sos--update----spinner_value-------------------" + spinner_value);
                            sendBroadcast(intent1);
                            finish();
                            ShowToast.Short(getString(R.string.msg_update_success));
                        } else if (100 == create_code || 500 == create_code) {
                            ShowToast.Short(getString(R.string.msg_save_fail));
                        } else if (100 == update_code || 500 == create_code) {
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
                //拿到第七题所有的edit的值
                for (int i : list_tag) {
                    EditText text = (EditText) textview_edittext_img_ll.findViewWithTag(i).findViewById(R.id.et_common_purpose);
                    if (text.length() > 0) {
                        new_purpose += text.getText().toString() + ",";
                    }
                }
                L.d("cc", "new_purpose=" + new_purpose);

                String p = person.getText().toString();
                time = date_tv.getText().toString();

                ArrayList<Model_three> list_model_three_include_sum = TableInfoMcGillAdapter.getlist_modle_three_include_sum();
                Model_one model_one = TableInfoMcGillAdapter.getlist_modle_one(spinner_value);
                ArrayList<Model_one> model_one_list = TableInfoMcGillAdapter.getlist_modle_one_list();


                //判断数据是否填写完整
                if (dataIsEmpty(p, time, list_model_three_include_sum)) {
                    ShowToast.Short(getString(R.string.msg_isEmpty_warning));
                } else {
                    if ("编辑".equals(title_name.getText().toString())) {
                        //图片设置固定坐标
                        //sos..............
                        model_one.getHeader_questions().get(0).getQuestions().get(1)
                                .setValue("-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000");

                        if(checkMultipleEditTextIsEmptyOrNot(model_one)){
                            showMsg("请检查是否填写完整");
                            return;
                        }else{
                            value_str = getJsonDataEdit(model_one_list, model_one);
                            L.d("cc", "value_str-edit" + value_str);
                        }

//                        param = "&sum[0]=" +list_model_three_include_sum.get(0).getValue()
//                                //坐标
//                                //坐标
//                                + "&sum[2]=" +list_model_three_include_sum.get(2).getValue()
//                                + "&sum[3]=" +list_model_three_include_sum.get(3).getValue()
//                                + "&sum[4]=" +list_model_three_include_sum.get(4).getValue()
//                                + "&sum[5]=" +list_model_three_include_sum.get(5).getValue()
//                                + "&sum[6]=" +et_value_two.getText().toString()+","+new_purpose.substring(0,new_purpose.length()-1)
//                                + "&sum[7]=" +list_model_three_include_sum.get(7).getValue()
//                                + "&sum[8]=" +list_model_three_include_sum.get(9).getValue()
//                                + "&sum[9]=" +list_model_three_include_sum.get(10).getValue()
//                                + "&sum[10]=" +list_model_three_include_sum.get(11).getValue()
//                                + "&sum[11]=" +list_model_three_include_sum.get(12).getValue()
//                                + "&sum[12]=" +list_model_three_include_sum.get(13).getValue()
//                                + "&sum[13]=" +list_model_three_include_sum.get(14).getValue()
//                                + "&sum[14]=" +list_model_three_include_sum.get(15).getValue()
//                                + "&sum[15]=" +list_model_three_include_sum.get(15).getValue()
//                                + "&sum[16]=" +list_model_three_include_sum.get(17).getValue()
//                                + "&token=" + NetUrlAddress.token;

                        //L.d("ss","param——edit="+param);


                    }
                    if ("添加".equals(title_name.getText().toString())) {
                        //图片设置固定坐标
                        //sos..............
                        model_one_list.get(0).getHeader_questions().get(0).getQuestions().get(1)
                                .setValue("-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000");

                        if(checkMultipleEditTextIsEmptyOrNot(model_one_list)){
                            showMsg("请检查是否填写完整");
                            return;
                        }else{
                            value_str = getJsonDataAdd(list_model_three_include_sum, model_one_list);
                            L.d("cc", "value_str-add" + value_str);
                        }



//                        param = "&sum[0]=" +list_model_three_include_sum.get(0).getValue()
//                                //147|105|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000
//                                + "&sum[1]="+"107|100|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000"
//                                + "&sum[2]=" +list_model_three_include_sum.get(2).getValue()
//                                + "&sum[3]=" +list_model_three_include_sum.get(3).getValue()
//                                + "&sum[4]=" +list_model_three_include_sum.get(4).getValue()
//                                + "&sum[5]=" +list_model_three_include_sum.get(5).getValue()
//                                + "&sum[6]=" +et_value_two.getText().toString()+","+new_purpose.substring(0,new_purpose.length()-1)
//                                + "&sum[7]=" +list_model_three_include_sum.get(7).getValue()
//                                + "&sum[8]=" +list_model_three_include_sum.get(9).getValue()
//                                + "&sum[9]=" +list_model_three_include_sum.get(10).getValue()
//                                + "&sum[10]=" +list_model_three_include_sum.get(11).getValue()
//                                + "&sum[11]=" +list_model_three_include_sum.get(12).getValue()
//                                + "&sum[12]=" +list_model_three_include_sum.get(13).getValue()
//                                + "&sum[13]=" +list_model_three_include_sum.get(14).getValue()
//                                + "&sum[14]=" +list_model_three_include_sum.get(15).getValue()
//                                + "&sum[15]=" +list_model_three_include_sum.get(15).getValue()
//                                + "&sum[16]=" +list_model_three_include_sum.get(17).getValue()
//                                + "&token=" + NetUrlAddress.token;
//
//                        L.d("ss","param--add="+param);
                    }
                    L.d("cc","value_str="+value_str.length());
                    L.d("cc","value_str="+value_str.toString());

                        new MyThread().start();
                }
            }
        });

        //加号的监听
        image_add_layout_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(addCount<2){
                    add_layout_set_data("");
                    addCount++;
                }else{
                    showMsg("添加超过限制");
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
     * 添加时，检查第七题的多选edit，是否没填写完整
     *
     * @param model_one_list
     */
    private boolean checkMultipleEditTextIsEmptyOrNot(ArrayList<Model_one> model_one_list) {
        if (et_value_two.getText().toString().equals("")) {
            return true;
        } else if (new_purpose.length() <= 0) {
            model_one_list.get(0).getHeader_questions().get(0).getQuestions().get(6)
                    .setValue(et_value_two.getText().toString());
        } else {
            model_one_list.get(0).getHeader_questions().get(0).getQuestions().get(6)
                    .setValue(et_value_two.getText().toString() + "," + new_purpose.substring(0, new_purpose.length() - 1));
        }
        return false;
    }
    /**
     * 编辑时，检查第七题的多选edit，是否没填写完整
     */
    private boolean checkMultipleEditTextIsEmptyOrNot(Model_one model_one) {
        if (et_value_two.getText().toString().equals("")) {
            return true;
        } else if (new_purpose.length() <= 0) {
            model_one.getHeader_questions().get(0).getQuestions().get(6)
                    .setValue(et_value_two.getText().toString());
        } else {
            model_one.getHeader_questions().get(0).getQuestions().get(6)
                    .setValue(et_value_two.getText().toString() + "," + new_purpose.substring(0, new_purpose.length() - 1));
        }
        return false;
    }

    /**
     * 获取返回数据
     */
    class MyThread extends Thread {

        public void run() {
            // Looper.prepare();
            try {
                //patientInfo.getId()+".json?token="+NetUrlAddress.token+"&&user_auth_id="+String.valueOf(use_id)+"&&menu_id=15";
//                String jsonData = new JSONStringer().object().key("list").
//                        value(value_str).endObject().toString();

                String url = "&&user_auth_id=" + user_auth_id + "&&patient_info_id=" + patient_info_id + "&&menu_lib_id=" + menu_lib_id;

                L.d("aa", "param_str=" + param);
                if ("添加".equals(title_name.getText().toString())) {
                    create_code = HttpUtil.doPost(NetUrlAddress.Common_create_url + url, value_str);
                    // create_code = HttpUtil.doPost2(NetUrlAddress.Common_create_url + url, param);
                } else {
                    L.d("aa", "param_edit=" + param);
                    update_code = HttpUtil.doPost(NetUrlAddress.Common_update_url + url, value_str);
                    //update_code = HttpUtil.doPost2(NetUrlAddress.Common_update_url + url, param);
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
            //Looper.loop();
        }
    }


    /**
     * 添加新的布局
     *
     * @param s
     */
    private void add_layout_set_data(String s) {
        tag += 1;
        list_tag.add(tag);

        final View view = LayoutInflater.from(this).inflate(R.layout.include_listview_linearlayout, null);
        delete_purpose = (ImageView) view.findViewById(R.id.image_delete);
        view.setTag(tag);
        EditText text = (EditText) view.findViewById(R.id.et_common_purpose);
        text.setText(s);
        textview_edittext_img_ll.addView(view);
        delete_purpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCount--;
                textview_edittext_img_ll.removeView(view);

                for (int i = 0; i < list_tag.size(); i++) {
                    if (list_tag.get(i) == view.getTag()) {
                        list_tag.remove(i);
                    }
                }
            }
        });
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
//
//            e.printStackTrace();
//        }
//    }

    /**
     * 编辑时，拼接json
     *
     * @param list_model_one
     * @return
     */
    private String getJsonDataEdit(ArrayList<Model_one> list_model_one, Model_one model_one) {

        JSONArray arr_three = null;
        JSONArray arr_two = null;
        JSONObject jsonObject_model_one = null;
        try {
            arr_two = new JSONArray();
            for (int m = 0; m < list_model_one.get(0).getHeader_questions().size(); m++) {
                arr_three = new JSONArray();
                for (int n = 0; n < list_model_one.get(0).getHeader_questions().get(m).getQuestions().size(); n++) {
                    JSONObject jsonObject_three = new JSONObject();
                    Model_three model_three = model_one.getHeader_questions().get(m).getQuestions().get(n);
                    jsonObject_three.put("question", model_three.getQuestion());
                    jsonObject_three.put("value", model_three.getValue());
                    jsonObject_three.put("in_sum", model_three.isIn_sum());
                    L.d("aa", "model_three.getId()=" + model_three.getId());
                    jsonObject_three.put("id", model_three.getId());
                    arr_three.put(jsonObject_three);
                }
                JSONObject jsonObject_model_two = new JSONObject();
                Model_two model_two = model_one.getHeader_questions().get(m);
                jsonObject_model_two.put("header", model_two.getHeader());
                jsonObject_model_two.put("questions", arr_three);
                arr_two.put(jsonObject_model_two);
            }

            jsonObject_model_one = new JSONObject();
            jsonObject_model_one.put("evaluation_person", person.getText().toString());
            jsonObject_model_one.put("evaluation_time_note", spinner_value);
            jsonObject_model_one.put("evaluation_time", date_tv.getText().toString());
            jsonObject_model_one.put("has_sum", model_one.isHas_sum());
            jsonObject_model_one.put("has_grade", model_one.isHas_grade());

            String groupid = getGroupId();
            jsonObject_model_one.put("groupid", groupid);
            jsonObject_model_one.put("header_questions", arr_two);

            L.d("aa", "  object_edit=" + jsonObject_model_one);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject_model_one.toString();
    }

    /**
     * 添加时获取groupid
     */
    private String getGroupId() {
        String groupid = "-1";
        if ("初评".equals(spinner_value)) {
            groupid = "1";
        } else if ("中评Ⅰ".equals(spinner_value)) {
            groupid = "2";
        } else if ("末评".equals(spinner_value)) {
            groupid = "3";
        } else if ("中评Ⅱ".equals(spinner_value)) {
            groupid = "4";
        } else if ("中评Ⅲ".equals(spinner_value)) {
            groupid = "5";
        } else if ("中评Ⅳ".equals(spinner_value)) {
            groupid = "6";
        } else if ("中评Ⅴ".equals(spinner_value)) {
            groupid = "7";
        }
        return groupid;
    }

    /**
     * 添加时，拼接json
     *
     * @param list_model_three
     * @param list_model_one
     * @return
     */
    private String getJsonDataAdd(List<Model_three> list_model_three, List<Model_one> list_model_one) {
        JSONArray arr_three = null;
        JSONObject jsonObject_model_one = null;
        JSONArray arr_two = null;
        try {
            arr_two = new JSONArray();
            for (int m = 0; m < list_model_one.get(0).getHeader_questions().size(); m++) {

                arr_three = new JSONArray();
                for (int n = 0; n < list_model_one.get(0).getHeader_questions().get(m).getQuestions().size(); n++) {
                    JSONObject jsonObject_three = new JSONObject();

                    Model_three model_three = list_model_one.get(0).getHeader_questions().get(m).getQuestions().get(n);
                    jsonObject_three.put("question", model_three.getQuestion());
                    jsonObject_three.put("value", model_three.getValue());

                    L.d("aa", "model_three.isIn_sum()==" + model_three.isIn_sum());
                    jsonObject_three.put("in_sum", model_three.isIn_sum());
                    arr_three.put(jsonObject_three);
                }
                JSONObject jsonObject_model_two = new JSONObject();
                Model_two model_two = list_model_one.get(0).getHeader_questions().get(m);
                jsonObject_model_two.put("header", model_two.getHeader());
                jsonObject_model_two.put("questions", arr_three);
                arr_two.put(jsonObject_model_two);

            }

            Model_one model_one = list_model_one.get(0);
            jsonObject_model_one = new JSONObject();
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
            L.d("aa", "jsonObject_model_one=" + jsonObject_model_one.toString());
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
            tv_value_two.setText(list_infos.get(0).getHeader_questions().get(0).getQuestions().get(6).getQuestion());
            list_view.setAdapter(new TableInfoMcGillAdapter(TableMcGillInfoActivity.this, list_infos));
        } else if ("编辑".equals(title_name.getText().toString().trim())) {
            //编辑
            L.d("ss", "list_infos===" + list_infos);
            L.d("ss", "list_infos.size()===" + list_infos.size());
            tv_value_two.setText(list_infos.get(0).getHeader_questions().get(0).getQuestions().get(6).getQuestion());
            list_view.setAdapter(new TableInfoMcGillAdapter(TableMcGillInfoActivity.this, list_infos, instructions_value));
        }
    }

    /**
     * 编辑时显示的数据
     */
    private void initEditData() {
        person.setText(instructions_person);
        date_tv.setText(instructions_date);

        Model_one model_one = getlist_modle_one(instructions_value);

        et_value_two.setText(model_one.getHeader_questions().get(0)
                .getQuestions().get(6).getValue().split(",")[0]);
        L.d("ss", "list_infos.get(0).getHeader_questions().get(0).getQuestions().get(1)." +
                "getValue()=" + list_infos.get(0).getHeader_questions().get(0).
                getQuestions().get(1).getValue());
        //147|105|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000|-1000
//        GetTextImage get = new GetTextImage(this, X, Y, i);
//        setContentView(get);
        String[] image_XY = list_infos.get(0).getHeader_questions().get(0).
                getQuestions().get(1).getValue().split("\\|");

        int t = 0;
        int count = 0;
        for (int i = 0; i < image_XY.length; i++) {
            if (image_XY[i].equals("-1000")) {
                count++;
                t = 20 - count;
            }
        }
        L.d("ss", "t=" + t);
        ArrayList<String> x_list = new ArrayList<>();
        ArrayList<String> y_list = new ArrayList<>();

        for (int j = 0; j < t; j++) {
            L.d("ss", "image_XY[j]=" + image_XY[j]);
            L.d("ss", "image_XY[j+1]=" + image_XY[j + 1]);
            x_list.add(image_XY[j]);
            y_list.add(image_XY[j + 1]);
            j += 1;
        }

        L.d("ss", "y_list=" + y_list.size());
        L.d("ss", "x_list=" + x_list.size());

        String X = image_XY[0];
        String Y = image_XY[1];
        L.d("ss", "X=" + X);
        L.d("ss", "Y=" + Y);


//        for(int m=0;m<x_list.size();m++){
//
//            GetTextImage get = new GetTextImage(this,  Float.parseFloat(x_list.get(m)),  Float.parseFloat(y_list.get(m)), 0);
//            Resources res = getResources();
//            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.mcgill).copy(Bitmap.Config.ARGB_8888, true);
//            get.draw(new Canvas(bmp));
//            mc_gill_img.setImageBitmap(bmp);
//        }

        //编辑时，给第七题的edit赋值
        ArrayList<String> list = new ArrayList<>();
        for (String s : model_one.getHeader_questions().get(0)
                .getQuestions().get(6).getValue().split(",")) {
            list.add(s);
        }
        //去掉已经赋值给第一个的值
        list.remove(0);
        for (String s : list) {
            add_layout_set_data(s);
        }
    }

    /**
     * 获取特定评级的list数据
     * @param spinner_value
     * @return
     */
    public Model_one getlist_modle_one(String spinner_value) {
        L.d("aa", "spinner_value==" + spinner_value);
        Model_one list_one = null;
        for (int position = 0; position < list_infos.size(); position++) {
            if (spinner_value.equals(list_infos.get(position).getEvaluation_time_note())) {
                list_one = list_infos.get(position);
            }
        }
        return list_one;
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
    private boolean dataIsEmpty(String person, String date, ArrayList<Model_three> list_model_three) {
        if (TextUtils.isEmpty(person) || TextUtils.isEmpty(date) || radioButtonIsEmpty(list_model_three)) {
            L.d("ss", "radioButtonIsEmpty(list_model_three)=" + radioButtonIsEmpty(list_model_three));
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
    private boolean radioButtonIsEmpty(ArrayList<Model_three> list_model_three) {
        L.d("ss", "list_model_three.get(1).getValue()=" + list_model_three.get(1).getValue());
        L.d("ss", "list_model_three.get(6).getValue()=" + list_model_three.get(6).getValue());
        list_model_three.get(1).setValue("-100");
        list_model_three.get(6).setValue("-100");
        L.d("ss", "list_model_three.size()=" + list_model_three.size());
        for (Model_three model_three : list_model_three) {

            String value = model_three.getValue();
            L.d("aa", "value=" + value);
            if (value.equals("-1000")) {
                return true;
            }
        }
        return false;
    }
}