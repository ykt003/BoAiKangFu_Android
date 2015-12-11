package me.zhangls.rilintech.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import me.zhangls.rilintech.R;
import me.zhangls.rilintech.adapter.TableInfoNormalAdapter;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.NormalTableFinalModel;
import me.zhangls.rilintech.net.HttpUtil;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.ShowToast;

/**
 * Created by YANG on 15/11/23.
 */
public class TableNormalChildInfoActivity extends TableNormalParentInfoActivity implements View.OnClickListener{

    private boolean isAdd;
    //当前登录的用户ID
    private int user_auth_id;
    //患者ID
    private int patient_info_id;
    //model集合
    private NormalTableFinalModel finalModel;
    //menu_lib_id
    private String menu_lib_id;
    //新建响应码
    private int createCode;
    //更新响应码
    private int updateCode;
    //MyHandler
    private MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //启动消息接收
        handler = new MyHandler();
        //获取当前登录用户和当前患者的ID,和页面的model集合
        getSharedPreferencesData();
        //初始化控件
        initView();
        //添加监听
        initSetOnClick();

    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 100:
                    ShowToast.Short("操作失败");
                    break;
                case 200:
                    ShowToast.Short("操作成功");
                    setResult(TableNormalChildIndexActivity.RESULT_YES);
                    TableNormalChildInfoActivity.this.finish();
                    break;
                case 500:
                    ShowToast.Short("操作失败");
                    break;
            }
        }
    }

    private void getSharedPreferencesData() {
        SharedPreferences sharedPreferences = getSharedPreferences("patientinfo", Activity.MODE_PRIVATE);
        patient_info_id = sharedPreferences.getInt("id", -1);

        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        user_auth_id = preferences.getInt("use_id", 2);

        Bundle bundle = getIntent().getExtras();
        menu_lib_id = bundle.getString("menu_lib_id");
        isAdd = bundle.getBoolean("isAdd");
        finalModel = bundle.getParcelable("finalModel");

    }

    private void initView() {
        if (isAdd){
            mTitle.setText("添加");
        }else {
            mTitle.setText("编辑");
        }

        mListView.setAdapter(new TableInfoNormalAdapter(this, finalModel, isAdd));

    }

    private void initSetOnClick() {
        mSave.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tittle_back:
                this.finish();
                break;
            case R.id.patient_save:

                new MyThread().start();

                break;
        }
    }

    /**
     * 提交数据至服务器
     */
    private class MyThread extends Thread {
        @Override
        public void run() {
            super.run();

            String param = "patient_info_id=" + patient_info_id
                    + "&user_auth_id=" + user_auth_id
                    + "&menu_lib_id=" + menu_lib_id
                    + getValue()
                    + "&"+finalModel.getModel_name()+"["+ "id"+"]=" + finalModel.getId()
                    + "&token=" + NetUrlAddress.token;

            Message message = new Message();
            if (isAdd) {
                createCode = NormalTableFinalModel.doPost(NetUrlAddress.CreateNormalTableData, param);
                message.what = createCode;
            } else {
                updateCode =NormalTableFinalModel.doPost(NetUrlAddress.UpdateNormalTableData, param);
                message.what = updateCode;
            }
            handler.sendMessage(message);
        }
    }

    /**
     * 获取字段值
     * @return
     */
    private String getValue(){
        finalModel = TableInfoNormalAdapter.getFinalModelValue();
        String value = "";
        String modelName = finalModel.getModel_name();
        for (int i=0;i<finalModel.getList_model().size();i++){
            value += "&"+modelName+"["+finalModel.getList_model().get(i).getName()+"]="+
                    finalModel.getList_model().get(i).getMiddleText();
        }
        return value;
    }


}
