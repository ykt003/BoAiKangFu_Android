package me.zhangls.rilintech.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;
import me.zhangls.rilintech.manager.MedicalHistoryRecordManager;
import me.zhangls.rilintech.model.MedicalHistoryRecord;
import me.zhangls.rilintech.utils.UuidFactory;

/**
 * Created by rilintech on 15/8/4.
 */
public class MedicalHistoryRecordActivity extends BaseActivity implements View.OnClickListener{

    /**
     * 返回按钮
     */
    private ImageView mBack;
    /**
     * 编辑按钮
     */
    private ImageView mEdit;
    /**
     * 保存按钮
     */
    private ImageView mSave;
    /**
     * 是否为添加
     */
    private static boolean isAdd = true;
    /**
     * 病例采集日期
     */
    private TextView mMedicalHistoryDate;
    /**
     * 病史陈述者
     */
    private EditText mMedicalHistoryRelator;
    /**
     * 病例可靠性
     */
    private EditText mMedicalHistoryReliable;
    /**
     * 主诉
     */
    private EditText mMainSuit;
    /**
     * 病残史
     */
    private EditText mDiseaseHistory;
    /**
     * 既往史
     */
    private EditText mPastHistory;
    /**
     * 病史记录管理
     */
    private MedicalHistoryRecordManager manager = null;
    /**
     * 病史记录
     */
    private MedicalHistoryRecord medicalHistoryRecord = null;
    /**
     * 病史记录UUID
     */
    private String mhr_uuid;
    /**
     * 时间选择器
     */
    private TimePopupWindow pwTime;

    private TextView jingshen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history_record_info);
        //取到isAdd的值，判断此界面是添加还是详细
        isAdd = getIntent().getBooleanExtra("isAdd", true);
        //得到传过来的uuid
        if(isAdd == false) {
            mhr_uuid = getIntent().getStringExtra("mhr_uuid");
        }

        initView();
    }

    private void initView() {

        //时间选择器
        pwTime = new TimePopupWindow(this, TimePopupWindow.Type.YEAR_MONTH_DAY);
        pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                mMedicalHistoryDate.setText(getTime(date));
            }
        });
        pwTime.setCyclic(true);
        //头标上得返回，编辑保存控件
        mBack = (ImageView) findViewById(R.id.tittle_back);
        mSave = (ImageView) findViewById(R.id.patient_save);
        mEdit = (ImageView) findViewById(R.id.patient_edit);
        mBack.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mEdit.setOnClickListener(this);
        //详细字段
        jingshen = (TextView) findViewById(R.id.jingshen_tv);
        jingshen.setOnClickListener(this);
        mMedicalHistoryDate = (TextView) findViewById(R.id.medical_history_record_date_tv);
        mMedicalHistoryDate.setOnClickListener(this);
        mMedicalHistoryRelator = (EditText) findViewById(R.id.medical_history_relator_edit);
        mMedicalHistoryReliable = (EditText) findViewById(R.id.medical_history_reliable_edit);
        mMainSuit = (EditText) findViewById(R.id.main_suit_edit);
        mDiseaseHistory = (EditText) findViewById(R.id.disease_history_edit);
        mPastHistory = (EditText) findViewById(R.id.past_history_edit);


        //判断isAdd的值来显示后隐藏右上角的按钮和其他所有控件是否可点击
        if(isAdd){
            mEdit.setVisibility(View.GONE);
            mSave.setVisibility(View.VISIBLE);
            setAllBtnEnable(true);

        }else{
            mEdit.setVisibility(View.VISIBLE);
            mSave.setVisibility(View.GONE);
            setAllBtnEnable(false);
            setAllWidgetDate(mhr_uuid);
        }
    }

    /**
     * 给所有的控件赋值
     * @param mhr_uuid
     */
    private void setAllWidgetDate(String mhr_uuid){
        manager = new MedicalHistoryRecordManager(this);
        manager.openDataBase();
        medicalHistoryRecord = manager.queryMedicalHistoryRecord(mhr_uuid);
        mMedicalHistoryDate.setText(medicalHistoryRecord.getMedical_history_date());
        mMedicalHistoryRelator.setText(medicalHistoryRecord.getMedical_history_relator());
        mMedicalHistoryReliable.setText(medicalHistoryRecord.getMedical_history_reliable());
        mMainSuit.setText(medicalHistoryRecord.getMain_suit());
        mDiseaseHistory.setText(medicalHistoryRecord.getDisease_history());
        mPastHistory.setText(medicalHistoryRecord.getPast_history());
        manager.closeDataBase();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tittle_back:
                Intent intent_back = new Intent(this, TableIndexActivity.class);
                startActivity(intent_back);
                this.finish();
                break;
            case R.id.patient_edit:
                //点击了编辑按钮，设置所有的控件可编辑状态为true
                mEdit.setVisibility(View.GONE);
                mSave.setVisibility(View.VISIBLE);
                setAllBtnEnable(true);

                break;
            case R.id.patient_save:

                saveMedicalHistoryRecord();
                setAllBtnEnable(false);
                Intent intent_save = new Intent(this, TableIndexActivity.class);
                startActivity(intent_save);
                this.finish();
                break;
            case R.id.medical_history_record_date_tv:
                pwTime.showAtLocation(mMedicalHistoryDate, Gravity.BOTTOM, 0,0,new Date());
                break;
            case R.id.jingshen_tv:
                final String[]mItems = {"淡漠","正常","兴奋","烦躁","抑郁","幻觉","幻听","幻视"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("列表选择框");
                builder.setItems(mItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        jingshen.setText(mItems[i]);
                    }
                });
                builder.create().show();
        }

    }

    /**
     * 保存
     */
    private void saveMedicalHistoryRecord(){
        manager = new MedicalHistoryRecordManager(this);
        manager.openDataBase();
        medicalHistoryRecord = new MedicalHistoryRecord();

        medicalHistoryRecord.setMedical_history_date(mMedicalHistoryDate.getText().toString());
        medicalHistoryRecord.setMedical_history_relator(mMedicalHistoryRelator.getText().toString());
        medicalHistoryRecord.setMedical_history_reliable(mMedicalHistoryReliable.getText().toString());
        medicalHistoryRecord.setMain_suit(mMainSuit.getText().toString());
        medicalHistoryRecord.setDisease_history(mDiseaseHistory.getText().toString());
        medicalHistoryRecord.setPast_history(mPastHistory.getText().toString());
        if(isAdd){
            //添加
            medicalHistoryRecord.setMhr_uuid(UuidFactory.getUuid());
            manager.addMedicalHistoryRecord(medicalHistoryRecord);
        }else{
            //更新
            manager.updateMedicalHistoryRecords(medicalHistoryRecord, mhr_uuid);
        }
        manager.closeDataBase();
    }

    /**
     * 设置所有控件是否可编辑
     * @param b
     */
    private void setAllBtnEnable(boolean b){

        mMedicalHistoryDate.setEnabled(b);
        mMedicalHistoryRelator.setEnabled(b);
        mMedicalHistoryReliable.setEnabled(b);
        mMainSuit.setEnabled(b);
        mDiseaseHistory.setEnabled(b);
        mPastHistory.setEnabled(b);

    }

    public static String getTime(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    /**
     * 销毁activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    /**]
     * 监听返回按钮
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       if(keyCode == KeyEvent.KEYCODE_BACK){
           Intent intent = new Intent(this, TableIndexActivity.class);
           startActivity(intent);
           this.finish();
        }
        return true;
    }

}
