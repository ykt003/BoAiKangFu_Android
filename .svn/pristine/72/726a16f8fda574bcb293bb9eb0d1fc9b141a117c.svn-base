package me.zhangls.rilintech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.apache.commons.lang.math.NumberUtils;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.fragment.MedicalRecordFragment;
import me.zhangls.rilintech.manager.MedicalRecordManager;
import me.zhangls.rilintech.model.MedicalRecord;

/**
 * Created by zsn on 15/8/4.
 */
public class MedicalRecordAddActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "edicalRecordAddActivity";
    private ImageView back, save,edit;
    private   EditText hospitalRoomRecord,hospitalChiefComplaint,hospitalClinicalDiagnosis,
            hospitalDepartment,hospitalPastHistory,hospitalRecoveryDiagnosis;
    //private static final String ACTION_INTENT_MEDICALRECORD_ADD = "me.zhangls.rilintech.activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_medical_record);

        initView();

        initData();

        initListener();
    }

    /**
     *初始化
     */
    private void initView(){
        hospitalRoomRecord = (EditText) findViewById(R.id.hospital_room_record_edit);
        hospitalChiefComplaint = (EditText) findViewById(R.id.hospital_chief_complaint_edit);
        hospitalClinicalDiagnosis = (EditText) findViewById(R.id.hospital_clinical_diagnosis_edit);
        hospitalDepartment = (EditText) findViewById(R.id.hospital_department_edit);
        hospitalPastHistory = (EditText) findViewById(R.id.hospital_past_history_edit);
        hospitalRecoveryDiagnosis = (EditText) findViewById(R.id.hospital_recovery_diagnosis_edit);

        save=(ImageView)findViewById(R.id.save_medical);
        edit=(ImageView)findViewById(R.id.edit_medical);
        back=(ImageView)findViewById(R.id.back_medical);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        //通过intent拿到传来的数据，默认为-1
        int _hospitalRoomRecord = getIntent().getIntExtra("roomRecord", -1);
        String _hospitalChiefComplaint = getIntent().getStringExtra("hospitalChiefComplaint");
        String _hospitalClinicalDiagnosis = getIntent().getStringExtra("hospitalClinicalDiagnosis");
        String _hospitalDepartment = getIntent().getStringExtra("hospitalDepartment");
        String _hospitalPastHistory = getIntent().getStringExtra("hospitalPastHistory");
        String _hospitalRecoveryDiagnosis = getIntent().getStringExtra("hospitalRecoveryDiagnosis");

        if( _hospitalRoomRecord!=-1){
            hospitalRoomRecord.setText(_hospitalRoomRecord+"");
            hospitalChiefComplaint.setText(_hospitalChiefComplaint);
            hospitalClinicalDiagnosis.setText(_hospitalClinicalDiagnosis);
            hospitalDepartment.setText(_hospitalDepartment);
            hospitalPastHistory.setText(_hospitalPastHistory);
            hospitalRecoveryDiagnosis.setText(_hospitalRecoveryDiagnosis);
            return;
        }
    }

    /**
     * 各种控件的监听
     */
    private void initListener() {
        save.setOnClickListener(this);
        edit.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //返回
            case R.id.back_medical:
                finish();
                break;
            //再编辑
            case R.id.edit_medical:

                break;
            //保存
            case R.id.save_medical:
                final String _roomRecord=hospitalRoomRecord.getText().toString().trim();
                if(_roomRecord.length()==0||_roomRecord==null||"".equals(_roomRecord)){
                    showMsg("病房号不能为空");
                    return;
                }
                //输入的不能为汉字，符号
                if(!NumberUtils.isNumber(_roomRecord)){
                    showMsg("病房号只能为数字");
                    return;
                }
                //工作线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MedicalRecord _medicalRecord=new MedicalRecord();

                        _medicalRecord.setmHospitalRoomRecord(Integer.parseInt(_roomRecord));
                        _medicalRecord.setmHospitalRecoveryDiagnosis(hospitalRecoveryDiagnosis.getText().toString().trim());
                        _medicalRecord.setmHospitalPastHistory(hospitalPastHistory.getText().toString().trim());
                        _medicalRecord.setmHospitalDepartment(hospitalDepartment.getText().toString().trim());
                        _medicalRecord.setmHospitalChiefComplaint(hospitalChiefComplaint.getText().toString().trim());
                        _medicalRecord.setmHospitalClinicalDiagnosis(hospitalClinicalDiagnosis.getText().toString().trim());
                        //添加到sqlite数据库
                        MedicalRecordManager _medicalRecordManager=new MedicalRecordManager(MedicalRecordAddActivity.this);
                        _medicalRecordManager.openDataBase();
                        _medicalRecordManager.addMedicalRecordInfo(_medicalRecord);
                        _medicalRecordManager.closeDataBase();

                        //发广播
                        Intent intent = new Intent(MedicalRecordFragment.ACTION_INTENT_MEDICALRECORD_ADD);
                        sendBroadcast(intent);
                        finish();
                    }
                }).start();

                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播
        // unregisterReceiver(mybroadCastReceiver);
    }


}
