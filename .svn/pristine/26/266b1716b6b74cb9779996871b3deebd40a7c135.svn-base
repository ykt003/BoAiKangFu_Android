package me.zhangls.rilintech.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.greenrobot.event.EventBus;
import me.zhangls.rilintech.R;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.fragment.PatientInfoFragment;
import me.zhangls.rilintech.model.FlowChartInfo;
import me.zhangls.rilintech.model.Get_URL;
import me.zhangls.rilintech.model.NetWorkEvent;
import me.zhangls.rilintech.model.PatientInfo;
import me.zhangls.rilintech.utils.HttpUtils;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.NetUtils;
import me.zhangls.rilintech.utils.SystemBarTintManager;

/**
 * Created by zsn on 15/11/30.
 */
public class TableFlowChart extends Activity implements View.OnClickListener {
    //基本信息
    private TextView basic_information;
    //评价量表
    private TextView evaluation_scale;
    //目标设定
    private TextView target_set;
    //康复计划
    private TextView recovery_plan;
    //治疗记录
    private TextView treatment_record;
    //疗效评估
    private TextView efficacy_evaluation;
    //回顾总结
    private TextView review_summary;
    //电子病例
    private TextView electronic_case;
    //实验室
    private TextView laboratory;
    //影像
    private TextView image;
    //当前登录的用户ID
    private int user_auth_id;
    //获取的待解析的字符串
    private String value;
    //解析之后的数据集合
    private ArrayList<FlowChartInfo> flowChartInfoList = new ArrayList<>();
    //康复评价会
    private ImageView rehabilitation_evaluation;
    private ArrayList<HashMap<String, Integer[]>> nameAndIdList = null;
    //患者实体类
    private PatientInfo patientInfo;
    private TextView name, sex, age, linchuangzhenduan, department, expenses_resource;
    private MybroadCastReceiver mybroadCastReceiver;
    private BroadcastReceiver netStateReceiver;
    private MaterialDialog noNetWorkDialog;
    //从网络拿到的所有的数据源
    private PatientInfo patientInfo_parseNetData;
    static boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            // 创建状态栏的管理实例
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // 激活状态栏设置
            tintManager.setStatusBarTintEnabled(true);
            // 激活导航栏设置
            //tintManager.setNavigationBarTintEnabled(true);
            // 设置一个颜色给系统栏
            tintManager.setTintColor(Color.parseColor("#990000FF"));
            // 设置一个样式背景给导航栏
            //tintManager.setNavigationBarTintResource(R.drawable.actionbar_background);
            // 设置一个状态栏资源
            // tintManager.setStatusBarTintDrawable(MyDrawable);
            //tintManager.setStatusBarTintResource(R.drawable.actionbar_background);
            tintManager.setStatusBarTintColor(0xff56abe4);
        }
        setContentView(R.layout.activity_flow_chart);
        EventBus.getDefault().register(this);
        IntentFilter myIntentFilter = new IntentFilter();
        mybroadCastReceiver = new MybroadCastReceiver();
        //广播过滤器对象
        myIntentFilter.addAction(PatientInfoFragment.ACTION_INTENT_PATIENTINFO_ADD);
        //动态注册广播
        registerReceiver(mybroadCastReceiver, myIntentFilter);
        checkNetWork();

        initView();
        initListener();
        getSharedPreferencesData();
    }

    private void initData() {
        name.setText(patientInfo_parseNetData.getmName());
        sex.setText(patientInfo_parseNetData.getmSex());
        if(patientInfo_parseNetData.getmAge()==-1){
            age.setText("");
        }else{
            age.setText(patientInfo_parseNetData.getmAge() + "");
        }
        department.setText(patientInfo_parseNetData.getmP_keshi());
        expenses_resource.setText(patientInfo_parseNetData.getmP_jfly());
        linchuangzhenduan.setText(patientInfo_parseNetData.getmP_zhenduan());
    }

    /**
     * 获取当前登录用户和当前患者的ID
     */
    private void getSharedPreferencesData() {

        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        user_auth_id = preferences.getInt("use_id", 2);
    }

    /**
     * 联网，获取流程图的数据
     *
     * @return
     */
    private ArrayList<FlowChartInfo> initFlowChartInfoData() {

        patientInfo = (PatientInfo) getIntent().getSerializableExtra("patient_info_add");
        patientInfo_parseNetData = loadDataByNetworkType();
        String param = "patient_info_id=" + patientInfo.getId()
                + "&user_auth_id=" + user_auth_id
                + "&token=" + NetUrlAddress.token;
        value = Get_URL.getData(NetUrlAddress.GET_FLOW_CHART_DATA_URL, param);
        //解析数据
        try {
            L.d("uu", "value====" + value);
            flowChartInfoList = parseJsonStr(value);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return flowChartInfoList;
    }

    /**
     * 解析服务器传递的流程图数据
     *
     * @param value
     * @return
     * @throws JSONException
     */
    private ArrayList<FlowChartInfo> parseJsonStr(String value) throws JSONException {
        ArrayList<FlowChartInfo> flowChartInfo = new ArrayList<>();
        FlowChartInfo chatInfo = null;
        JSONObject jsonObject = new JSONObject(value);
        JSONArray jsonArr = jsonObject.getJSONArray("list");
        for (int i = 0; i < jsonArr.length(); i++) {
            chatInfo = new FlowChartInfo();
            String name = jsonArr.getJSONObject(i).getString("name");
            JSONArray idArr = jsonArr.getJSONObject(i).getJSONArray("id");
            Integer[] ids = new Integer[idArr.length()];
            for (int j = 0; j < idArr.length(); j++) {
                int id = Integer.parseInt(idArr.get(j).toString());
                ids[j] = id;
            }
            int flag = jsonArr.getJSONObject(i).getInt("flag");
            chatInfo.setFlag(flag);
            chatInfo.setName(name);
            chatInfo.setId(ids);
            flowChartInfo.add(chatInfo);
        }
        return flowChartInfo;
    }


    private void initListener() {
        basic_information.setOnClickListener(this);
        evaluation_scale.setOnClickListener(this);
        target_set.setOnClickListener(this);
        recovery_plan.setOnClickListener(this);
        treatment_record.setOnClickListener(this);
        efficacy_evaluation.setOnClickListener(this);
        review_summary.setOnClickListener(this);
        electronic_case.setOnClickListener(this);
        laboratory.setOnClickListener(this);
        image.setOnClickListener(this);
        rehabilitation_evaluation.setOnClickListener(this);
    }

    private void initView() {

        name = (TextView) findViewById(R.id.name);
        sex = (TextView) findViewById(R.id.sex);
        age = (TextView) findViewById(R.id.age);
        linchuangzhenduan = (TextView) findViewById(R.id.linchuangzhenduan);
        department = (TextView) findViewById(R.id.department);
        expenses_resource = (TextView) findViewById(R.id.expenses_resource);

        basic_information = (TextView) findViewById(R.id.basic_information);
        evaluation_scale = (TextView) findViewById(R.id.evaluation_scale);
        target_set = (TextView) findViewById(R.id.target_set);
        recovery_plan = (TextView) findViewById(R.id.recovery_plan);
        treatment_record = (TextView) findViewById(R.id.treatment_record);
        efficacy_evaluation = (TextView) findViewById(R.id.efficacy_evaluation);
        review_summary = (TextView) findViewById(R.id.review_summary);
        electronic_case = (TextView) findViewById(R.id.electronic_case);
        laboratory = (TextView) findViewById(R.id.laboratory);
        image = (TextView) findViewById(R.id.image);
        rehabilitation_evaluation = (ImageView) findViewById(R.id.rehabilitation_evaluation);
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(this, TableListViewShowActivity.class);
        Integer[] evaluation_scale_id = new Integer[2];
        Integer[] target_set_id = new Integer[1];
        Integer[] recovery_plan_id = new Integer[1];
        Integer[] treatment_record_id = new Integer[1];
        Integer[] efficacy_evaluation_id = new Integer[1];
        Integer[] review_summary_id = new Integer[1];
        Integer[] rehabilitation_evaluation_id = new Integer[1];
//        Integer[] electronic_case_id= new Integer[1];
//        Integer[] laboratory_id= new Integer[1];
//        Integer[] image_id= new Integer[1];
        L.d("uu", "nameAndIdList=" + nameAndIdList);
        if (null == nameAndIdList) {
            unregisterReceiver(netStateReceiver);
            checkNetWork();
            return;
        } else {
            for (int i = 0; i < nameAndIdList.size(); i++) {
//                if(nameAndIdList.get(i).containsKey("基本信息")){
//                    basic_information_id = nameAndIdList.get(i).get("基本信息");
//                }

                if (nameAndIdList.get(i).containsKey("评价量表")) {
                    evaluation_scale_id = nameAndIdList.get(i).get("评价量表");
                }
                if (nameAndIdList.get(i).containsKey("目标设定")) {
                    target_set_id = nameAndIdList.get(i).get("目标设定");
                }
                if (nameAndIdList.get(i).containsKey("康复计划")) {
                    recovery_plan_id = nameAndIdList.get(i).get("康复计划");
                }
                if (nameAndIdList.get(i).containsKey("治疗记录")) {
                    treatment_record_id = nameAndIdList.get(i).get("治疗记录");
                }
                if (nameAndIdList.get(i).containsKey("疗效评估")) {
                    efficacy_evaluation_id = nameAndIdList.get(i).get("疗效评估");
                }
                if (nameAndIdList.get(i).containsKey("回顾总结")) {
                    review_summary_id = nameAndIdList.get(i).get("回顾总结");
                }
                if (nameAndIdList.get(i).containsKey("康复评价会")) {
                    rehabilitation_evaluation_id = nameAndIdList.get(i).get("康复评价会");
                }
//                if( nameAndIdList.get(i).containsKey("电子病例")){
//                    electronic_case_id=nameAndIdList.get(i).get("电子病例");
//                }
//                if( nameAndIdList.get(i).containsKey("实验室")){
//                    laboratory_id=nameAndIdList.get(i).get("实验室");
//                }
//                if( nameAndIdList.get(i).containsKey("影像")) {
//                    image_id=nameAndIdList.get(i).get("影像");
//                }
            }

        }
        switch (view.getId()) {
            case R.id.basic_information:
                if ("null".equals(nameAndIdList)) {
                    unregisterReceiver(netStateReceiver);
                    checkNetWork();
                    return;
                } else {
                    open(patientInfo_parseNetData);
                }

                break;
            case R.id.evaluation_scale:
                if ("null".equals(nameAndIdList)) {
                    unregisterReceiver(netStateReceiver);
                    checkNetWork();
                    return;
                } else {
                    intent.putExtra("menuName", "评价量表");
                    intent.putExtra("id", evaluation_scale_id);
                    startActivity(intent);
                }

                break;
            case R.id.target_set:
                if ("null".equals(nameAndIdList)) {
                    unregisterReceiver(netStateReceiver);
                    checkNetWork();
                    return;
                } else {
                    intent.putExtra("menuName", "目标设定");
                    intent.putExtra("id", target_set_id);
                    startActivity(intent);
                }
                break;
            case R.id.recovery_plan:
                if ("null".equals(nameAndIdList)) {
                    unregisterReceiver(netStateReceiver);
                    checkNetWork();
                    return;
                } else {
                    intent.putExtra("menuName", "康复计划");
                    intent.putExtra("id", recovery_plan_id);
                    startActivity(intent);
                }
                break;
            case R.id.treatment_record:
                if ("null".equals(nameAndIdList)) {
                    unregisterReceiver(netStateReceiver);
                    checkNetWork();
                    return;
                } else {
                    intent.putExtra("menuName", "治疗记录");
                    intent.putExtra("id", treatment_record_id);
                    startActivity(intent);
                }
                break;
//            case R.id.efficacy_evaluation:
//                if ("null".equals(nameAndIdList)) {
//                    checkNetWork();
//                    return;
//                } else {
//                    intent.putExtra("menuName", "疗效评估");
//                    intent.putExtra("id", efficacy_evaluation_id);
//                    startActivity(intent);
//                }
//                break;
            case R.id.review_summary:
                if ("null".equals(nameAndIdList)) {
                    unregisterReceiver(netStateReceiver);
                    checkNetWork();
                    return;
                } else {
                    intent.putExtra("menuName", "回顾总结");
                    intent.putExtra("id", review_summary_id);
                    startActivity(intent);
                }
                break;

            case R.id.rehabilitation_evaluation:
                if ("null".equals(nameAndIdList)) {
                    unregisterReceiver(netStateReceiver);
                    checkNetWork();
                    return;
                } else {
                    intent.putExtra("menuName", "康复评价会");
                    intent.putExtra("id", rehabilitation_evaluation_id);
                    startActivity(intent);
                }
                break;
//            case R.id.electronic_case:
//                intent.putExtra("menuName","目标设定");
//                intent.putExtra("id",target_set_id);
//                break;
//            case R.id.laboratory:
//                intent.putExtra("menuName","目标设定");
//                intent.putExtra("id",target_set_id);
//                break;
//            case R.id.image:
//                intent.putExtra("menuName","目标设定");
//                intent.putExtra("id",target_set_id);
//                break;

        }
    }

    private class MyTask extends AsyncTask<String, String, ArrayList<FlowChartInfo>> {

        @Override
        protected ArrayList<FlowChartInfo> doInBackground(String... strings) {

            return initFlowChartInfoData();
        }

        @Override
        protected void onPostExecute(ArrayList<FlowChartInfo> list) {
            super.onPostExecute(list);
            if (list.size() > 0) {
                //初始化title内容
                initData();
                //防止乱序，再次封装
                ArrayList<HashMap<String, Integer>> flagList = new ArrayList<>();
                nameAndIdList = new ArrayList<>();
                HashMap<String, Integer> flagMap = null;
                HashMap<String, Integer[]> nameAndIdMap = null;

                for (int i = 0; i < list.size(); i++) {
                    flagMap = new HashMap<>();
                    nameAndIdMap = new HashMap<>();
                    int flag = list.get(i).getFlag();
                    String name = list.get(i).getName();
                    Integer[] id = list.get(i).getId();
                    flagMap.put(name, flag);
                    nameAndIdMap.put(name, id);
                    flagList.add(flagMap);
                    nameAndIdList.add(nameAndIdMap);
                }

                ArrayList<Integer> listsForChangeBackGround = null;
                //int  basic_information_flag=-1;
                int evaluation_scale_flag = -1;
                int target_set_flag = -1;
                int recovery_plan_flag = -1;
                int treatment_record_flag = -1;
                int efficacy_evaluation_flag = -1;
                int review_summary_flag = -1;
                int rehabilitation_evaluation_flag = -1;
                int electronic_case_flag = -1;
                int laboratory_flag = -1;
                int image_flag = -1;
                for (int j = 0; j < flagList.size(); j++) {
                    listsForChangeBackGround = new ArrayList<>();
//                    if( flagList.get(j).containsKey("基本信息")){
//                        basic_information_flag=flagList.get(j).get("基本信息");
//                    }
                    if (flagList.get(j).containsKey("评价量表")) {
                        evaluation_scale_flag = flagList.get(j).get("评价量表");
                    }
                    if (flagList.get(j).containsKey("目标设定")) {
                        target_set_flag = flagList.get(j).get("目标设定");
                    }
                    if (flagList.get(j).containsKey("康复计划")) {
                        recovery_plan_flag = flagList.get(j).get("康复计划");
                    }
                    if (flagList.get(j).containsKey("治疗记录")) {
                        treatment_record_flag = flagList.get(j).get("治疗记录");
                    }
                    if (flagList.get(j).containsKey("疗效评估")) {
                        efficacy_evaluation_flag = flagList.get(j).get("疗效评估");
                    }
                    if (flagList.get(j).containsKey("回顾总结")) {
                        review_summary_flag = flagList.get(j).get("回顾总结");
                    }
                    if (flagList.get(j).containsKey("康复评价会")) {
                        rehabilitation_evaluation_flag = flagList.get(j).get("康复评价会");
                    }
//                    if( flagList.get(j).containsKey("电子病例")){
//                        electronic_case_flag=flagList.get(j).get("电子病例");
//                    }
//                    if( flagList.get(j).containsKey("实验室")){
//                        laboratory_flag=flagList.get(j).get("实验室");
//                    }
//                    if( flagList.get(j).containsKey("影像")){
//                        image_flag=flagList.get(j).get("影像");
//                    }

                    //listsForChangeBackGround.add(0,basic_information_flag);
                    listsForChangeBackGround.add(0, evaluation_scale_flag);
                    listsForChangeBackGround.add(1, target_set_flag);
                    listsForChangeBackGround.add(2, recovery_plan_flag);
                    listsForChangeBackGround.add(3, treatment_record_flag);
                    listsForChangeBackGround.add(4, efficacy_evaluation_flag);
                    listsForChangeBackGround.add(5, review_summary_flag);
                    listsForChangeBackGround.add(6, rehabilitation_evaluation_flag);
//                    listsForChangeBackGround.add(7,electronic_case_flag);
//                    listsForChangeBackGround.add(8,laboratory_flag);
//                    listsForChangeBackGround.add(9,image_flag);
                }

                changeButtonBackgroundImage(listsForChangeBackGround);
            }
        }
    }

    /**
     * 改变按钮背景颜色
     */
    private void changeButtonBackgroundImage(ArrayList<Integer> listsForChangeBackGround) {
//        if(listsForChangeBackGround.get(0)==1){
//            basic_information.setBackgroundResource(R.drawable.anniu_dark);
//        }else if(listsForChangeBackGround.get(0)==0){
//            basic_information.setBackgroundResource(R.drawable.anniu_light);
//        }

        if (listsForChangeBackGround.get(0) == 1) {
            evaluation_scale.setBackgroundResource(R.drawable.anniu_dark);
        } else if (listsForChangeBackGround.get(0) == 0) {
            evaluation_scale.setBackgroundResource(R.drawable.anniu_light);
        }

        if (listsForChangeBackGround.get(1) == 1) {
            target_set.setBackgroundResource(R.drawable.anniu_dark);
        } else if (listsForChangeBackGround.get(1) == 0) {
            target_set.setBackgroundResource(R.drawable.anniu_light);
        }

        if (listsForChangeBackGround.get(2) == 1) {
            recovery_plan.setBackgroundResource(R.drawable.anniu_dark);
        } else if (listsForChangeBackGround.get(2) == 0) {
            recovery_plan.setBackgroundResource(R.drawable.anniu_light);
        }

        if (listsForChangeBackGround.get(3) == 1) {
            treatment_record.setBackgroundResource(R.drawable.anniu_dark);
        } else if (listsForChangeBackGround.get(3) == 0) {
            treatment_record.setBackgroundResource(R.drawable.anniu_light);
        }

        if (listsForChangeBackGround.get(4) == 1) {
            efficacy_evaluation.setBackgroundResource(R.drawable.anniu_dark);
        } else if (listsForChangeBackGround.get(4) == 0) {
            efficacy_evaluation.setBackgroundResource(R.drawable.anniu_light);
        }

        if (listsForChangeBackGround.get(5) == 1) {
            review_summary.setBackgroundResource(R.drawable.tuoyuan_dark);
        } else if (listsForChangeBackGround.get(5) == 0) {
            review_summary.setBackgroundResource(R.drawable.tuoyuan_light);
        }

        if (listsForChangeBackGround.get(6) == 1) {
            rehabilitation_evaluation.setImageResource(R.drawable.kangfupingdinghui_dark);
        } else if (listsForChangeBackGround.get(6) == 0) {
            rehabilitation_evaluation.setImageResource(R.drawable.kangfupingdinghui_light);
        }

//        if(listsForChangeBackGround.get(7)==1){
//            electronic_case.setBackgroundResource(R.drawable.anniu_dark);
//        }else if(listsForChangeBackGround.get(7)==0){
//            electronic_case.setBackgroundResource(R.drawable.anniu_light);
//        }
//
//        if(listsForChangeBackGround.get(8)==1){
//            laboratory.setBackgroundResource(R.drawable.anniu_dark);
//        }else if(listsForChangeBackGround.get(8)==0){
//            laboratory.setBackgroundResource(R.drawable.anniu_light);
//        }
//
//        if(listsForChangeBackGround.get(9)==1){
//            image.setBackgroundResource(R.drawable.anniu_dark);
//        }else if(listsForChangeBackGround.get(9)==0){
//            image.setBackgroundResource(R.drawable.anniu_light);
//        }
    }

    /**
     * 打开患者添加aty
     *
     * @param patientInfo
     */
    private void open(PatientInfo patientInfo) {
        Intent intent = new Intent(this, PatientInfoAddActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("patient_info_add", patientInfo);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 广播接收者，实现相应的事件的处理
     */

    private class MybroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(PatientInfoFragment.ACTION_INTENT_PATIENTINFO_ADD)) {
                new MyTask().execute();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active=false;
        unregisterReceiver(mybroadCastReceiver);
        unregisterReceiver(netStateReceiver);
        EventBus.getDefault().unregister(this);
    }


    /**
     * 检查网络状态
     */
    private void checkNetWork() {
        netStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    if (NetUtils.isConnected(TableFlowChart.this)) {
                        EventBus.getDefault().post(new NetWorkEvent(NetWorkEvent.AVAILABLE));
                    } else {
                        EventBus.getDefault().post(new NetWorkEvent(NetWorkEvent.UNAVAILABLE));
                    }
                }
            }
        };
        //注册
        registerReceiver(netStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStart() {
        super.onStart();
        active=true;
        new MyTask().execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        active=false;
    }

    public void onEvent(NetWorkEvent event) {
        if (event.getType() == NetWorkEvent.UNAVAILABLE) {
            if (noNetWorkDialog == null) {
                noNetWorkDialog = new MaterialDialog.Builder(TableFlowChart.this)
                        .title("无网络连接")
                        .content("去开启网络?")
                        .positiveText("是")
                        .negativeText("否")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                dialog.dismiss();
                                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(intent);
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                dialog.dismiss();
                            }
                        }).cancelable(false)
                        .build();
            }
            if(active){
                if (!noNetWorkDialog.isShowing()) {
                    noNetWorkDialog.show();
                }
            }
        }
    }

    /**
     * 联网，获取单个人的数据
     */
    private PatientInfo loadDataByNetworkType() {

        String URL_PATIENT_INFO_ADD = NetUrlAddress.ipAndPort + "/patient_infos/show/" + patientInfo.getId() + ".json?" +
                "&&user_auth_id=" + String.valueOf(user_auth_id) + "&&token=" + NetUrlAddress.token;

        String result = HttpUtils.doPost(URL_PATIENT_INFO_ADD, "");
        try {
            patientInfo_parseNetData = parse(new JSONObject(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return patientInfo_parseNetData;
    }

    /**
     * 解析单个人的数据信息
     *
     * @param jsonObject
     * @return
     */
    private PatientInfo parse(JSONObject jsonObject) {

        PatientInfo patientInfo = new PatientInfo();
        patientInfo.setId(jsonObject.optInt("id"));
        patientInfo.setmPatient_uuid(jsonObject.optString("patient_uuid"));
        patientInfo.setmP_bah(jsonObject.optInt("p_bah"));
        patientInfo.setmName(jsonObject.optString("name"));
        patientInfo.setmSex(jsonObject.optString("sex"));
        patientInfo.setmAge(jsonObject.optInt("age"));
        patientInfo.setmBirthday(jsonObject.optString("birthday"));
        patientInfo.setBirth_locate(jsonObject.optString("birth_locate"));
        patientInfo.setmProvince_id(jsonObject.optString("province_id"));
        patientInfo.setmState_id(jsonObject.optString("state_id"));
        patientInfo.setmCity_id(jsonObject.optString("city_id"));
        patientInfo.setmNation(jsonObject.optString("nation"));
        patientInfo.setmZhiwei(jsonObject.optString("zhiwei"));
        patientInfo.setmWh_chd(jsonObject.optString("wh_chd"));
        patientInfo.setmHy_zhk(jsonObject.optString("hy_zhk"));
        patientInfo.setmP_keshi(jsonObject.optString("p_keshi"));
        patientInfo.setmP_jfly(jsonObject.optString("p_jfly"));
        patientInfo.setmFirst_releate_person(jsonObject.optString("first_releate_person"));
        patientInfo.setmTel(jsonObject.optString("tel"));
        patientInfo.setmHeight(jsonObject.optString("height"));
        patientInfo.setmWeight(jsonObject.optInt("weight"));
        patientInfo.setmP_bmi(jsonObject.optInt("p_bmi"));
        patientInfo.setmP_sqks(jsonObject.optString("p_sqks"));
        patientInfo.setmP_zhenduan(jsonObject.optString("p_zhenduan"));
        patientInfo.setmLczd_icd(jsonObject.optString("lczd_icd"));
        patientInfo.setmP_bbbw(jsonObject.optString("p_bbbw"));
        patientInfo.setmBbbw_icd(jsonObject.optString("bbbw_icd"));
        patientInfo.setmP_startdate(jsonObject.optString("p_startdate"));
        patientInfo.setmP_bingcheng(jsonObject.optString("p_bingcheng"));
        patientInfo.setmP_gnza(jsonObject.optString("p_gnza"));
        patientInfo.setmP_fbjg(jsonObject.optString("p_fbjg"));
        patientInfo.setmP_zljg(jsonObject.optString("p_zljg"));
        patientInfo.setmP_kfjg(jsonObject.optString("p_kfjg"));
        patientInfo.setmP_xy(jsonObject.optInt("p_xy"));
        patientInfo.setmShu_z_ya(jsonObject.optInt("shu_z_ya"));
        patientInfo.setmP_xl(jsonObject.optInt("p_xl"));
        patientInfo.setmP_bxl(jsonObject.optInt("p_bxl"));
        patientInfo.setmP_szbxl(jsonObject.optInt("p_szbxl"));
        patientInfo.setmP_mb(jsonObject.optInt("p_mb"));
        patientInfo.setmP_xt(jsonObject.optInt("p_xt"));
        patientInfo.setmZl_xm(jsonObject.optString("zl_xm"));
        patientInfo.setmZl_zt(jsonObject.optString("zl_zt"));
        patientInfo.setmSj_ap(jsonObject.optString("sj_ap"));
        patientInfo.setmZhliao_bw(jsonObject.optString("zhliao_bw"));
        patientInfo.setmExecutor(jsonObject.optString("executor"));
        patientInfo.setmLr_time(jsonObject.optString("lr_time"));
        return patientInfo;
    }
}
