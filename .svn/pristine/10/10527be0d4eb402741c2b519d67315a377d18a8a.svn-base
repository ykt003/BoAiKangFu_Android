package me.zhangls.rilintech.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zhangls.sortlistview.SortListActivity;

import org.apache.commons.lang.math.NumberUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.TreadmillTraininginWaterInfo;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;
import me.zhangls.rilintech.utils.HttpUtils;

/**
 * Created by rilintech on 15/9/9.
 */
public class TableTreadmillTrainingInWaterAddActivity extends BaseActivity implements View.OnClickListener {
    //时间选择器 格式：2011-02-15
    private TimePopupWindow pwTime1;
    private TextView mDate_time, mSpeed_evaluation, mTherapeutic_effect;
    private EditText mTime_of_therapy, mSpeed, mActual_distance, mDepth_of_water, mWater_temperature,
            mGait_guidance, mVideo_record, mSoap_record, mRemark, mExecutor;
    private RadioGroup mVortex;
    private RadioButton mVortex_open, mVortex_close;
    private ImageView mBack, mEdit, mSave;
    private final String[] mSpeed_evaluation_Items = new String[]{"较慢", "适中", "较快"};
    private boolean flag = false;
    private TreadmillTraininginWaterInfo treadmillTraininginWaterInfo = null;
    private String _time_of_therapy, _speed, _actual_distance, _depth_of_water, _water_temperature;
    private static final int REQUEST_CODE_THERAPEUTIC_EFFECT_EDIT=1020;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_treadmill_training_in_water);
        initView();
        initData();
        initListener();
        //时间选择
        dateChoose(mDate_time);
    }

    private void initView() {


        mBack = (ImageView) findViewById(R.id.title_back);
        mEdit = (ImageView) findViewById(R.id.title_edit);
        mSave = (ImageView) findViewById(R.id.title_save);

        mDate_time = (TextView) findViewById(R.id.trainning_in_water_date_edit);
        mTime_of_therapy = (EditText) findViewById(R.id.treat_date_edit);
        mSpeed = (EditText) findViewById(R.id.set_speed_edit);
        mActual_distance = (EditText) findViewById(R.id.actual_distance_edit);
        mSpeed_evaluation = (TextView) findViewById(R.id.speed_evaluation_edit);
        mDepth_of_water = (EditText) findViewById(R.id.water_deep_edit);
        mWater_temperature = (EditText) findViewById(R.id.water_temperature_edit);
        mVortex = (RadioGroup) findViewById(R.id.trainning_in_water_radiogroup);
        mVortex_open = (RadioButton) findViewById(R.id.trainning_in_water_radiobutton_open);
        mVortex_close = (RadioButton) findViewById(R.id.trainning_in_water_radiobutton_close);
        mGait_guidance = (EditText) findViewById(R.id.gait_guidance_edit);
        mVideo_record = (EditText) findViewById(R.id.video_recording_edit);
        mTherapeutic_effect = (TextView) findViewById(R.id.therapeutic_effect_edit);
        mSoap_record = (EditText) findViewById(R.id.sozp_record_edit);
        mRemark = (EditText) findViewById(R.id.remarksd_edit);
        mExecutor = (EditText) findViewById(R.id.executive_edit);
    }

    private void initData() {
        //training_in_water_add
        Intent intent = getIntent();
        treadmillTraininginWaterInfo = (TreadmillTraininginWaterInfo) intent.getSerializableExtra("training_in_water_add");
        if (treadmillTraininginWaterInfo != null) {
            loadTreadmillTraininginWaterInfoData();
            setEable(false);
        }

    }

    private void loadTreadmillTraininginWaterInfoData() {
        //更改图标，编程编辑图标
        mEdit.setVisibility(View.VISIBLE);
        mSave.setVisibility(View.GONE);

        if (treadmillTraininginWaterInfo.getTime_of_therapy() == -1) {
            mTime_of_therapy.setText("");
        } else {
            mTime_of_therapy.setText(treadmillTraininginWaterInfo.getTime_of_therapy() + "");
        }

        if (treadmillTraininginWaterInfo.getSpeed() == -1) {
            mSpeed.setText("");
        } else {
            mSpeed.setText(treadmillTraininginWaterInfo.getSpeed() + "");
        }

        if (treadmillTraininginWaterInfo.getActual_distance() == -1) {
            mActual_distance.setText("");
        } else {
            mActual_distance.setText(treadmillTraininginWaterInfo.getActual_distance() + "");
        }

        if (treadmillTraininginWaterInfo.getDepth_of_water() == -1) {
            mDepth_of_water.setText("");
        } else {
            mDepth_of_water.setText(treadmillTraininginWaterInfo.getDepth_of_water() + "");
        }

        if (treadmillTraininginWaterInfo.getWater_temperature() == -1) {
            mWater_temperature.setText("");
        } else {
            mWater_temperature.setText(treadmillTraininginWaterInfo.getWater_temperature() + "");
        }

        if (treadmillTraininginWaterInfo.getDate_time().equals("null")) {
            mDate_time.setText("");
        } else {
            mDate_time.setText(treadmillTraininginWaterInfo.getDate_time());
        }

        mSpeed_evaluation.setText(treadmillTraininginWaterInfo.getSpeed_evaluation());
        mGait_guidance.setText(treadmillTraininginWaterInfo.getGait_guidance());
        mVideo_record.setText(treadmillTraininginWaterInfo.getVideo_record());
        mTherapeutic_effect.setText(treadmillTraininginWaterInfo.getTherapeutic_effect());
        mSoap_record.setText(treadmillTraininginWaterInfo.getSoap_record());
        mRemark.setText(treadmillTraininginWaterInfo.getRemark());
        mExecutor.setText(treadmillTraininginWaterInfo.getExecutor());

        if (treadmillTraininginWaterInfo.getVortex()!= null) {
            if (treadmillTraininginWaterInfo.getVortex().equals(mVortex_open.getText().toString().trim())) {
                mVortex_open.setChecked(true);
            }
            if (treadmillTraininginWaterInfo.getVortex().equals(mVortex_close.getText().toString().trim())) {
                mVortex_close.setChecked(true);
            }
        }
    }

    private void initListener() {
        mBack.setOnClickListener(this);
        mEdit.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mTherapeutic_effect.setOnClickListener(this);
        mSpeed_evaluation.setOnClickListener(this);
    }

    /**
     * 设置不可点击
     */
    private void setEable(boolean flag) {

        mDate_time.setEnabled(flag);
        mTime_of_therapy.setEnabled(flag);
        mSpeed.setEnabled(flag);
        mActual_distance.setEnabled(flag);
        mSpeed_evaluation.setEnabled(flag);
        mDepth_of_water.setEnabled(flag);
        mWater_temperature.setEnabled(flag);
        mGait_guidance.setEnabled(flag);
        mVideo_record.setEnabled(flag);
        mTherapeutic_effect.setEnabled(flag);
        mSoap_record.setEnabled(flag);
        mRemark.setEnabled(flag);
        mExecutor.setEnabled(flag);

        mVortex.setClickable(flag);
        mVortex_open.setClickable(flag);
        mVortex_close.setClickable(flag);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.speed_evaluation_edit:
                this.choose(mSpeed_evaluation, mSpeed_evaluation_Items);
                break;
            case R.id.title_back:
                this.finish();
                break;
            case R.id.title_edit:
                //编辑
                flag = true;
                setEable(flag);
                //更改图标，编辑图标
                mEdit.setVisibility(View.GONE);
                mSave.setVisibility(View.VISIBLE);
                break;
            case R.id.title_save:
                //保存
                treadmillTraininginWaterInfoSave();
                break;
            case R.id.therapeutic_effect_edit:
                Intent _Intent_2 = new Intent(this, SortListActivity.class);
                _Intent_2.putExtra("icf",2);
                startActivityForResult(_Intent_2, REQUEST_CODE_THERAPEUTIC_EFFECT_EDIT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_THERAPEUTIC_EFFECT_EDIT:
                if (resultCode == RESULT_OK) {
                    String result = data.getExtras().getString("result");
                    mTherapeutic_effect.setText(result);
                }
                break;
        }
    }
    /**
     * 保存数据
     */
    private void treadmillTraininginWaterInfoSave() {
        _time_of_therapy = mTime_of_therapy.getText().toString().trim();
        _speed = mSpeed.getText().toString().trim();
        _actual_distance = mActual_distance.getText().toString().trim();
        _depth_of_water = mDepth_of_water.getText().toString().trim();
        _water_temperature = mWater_temperature.getText().toString().trim();

        _time_of_therapy = inputStyle(_time_of_therapy, "治疗时间只能为数字");
        _speed = inputStyle(_speed, "设定速度只能为数字");
        _actual_distance = inputStyle(_actual_distance, "实际距离只能为数字");
        _depth_of_water = inputStyle(_depth_of_water, "水深（负重比）只能为数字");
        _water_temperature = inputStyle(_water_temperature, "水温只能为数字");

        if (Integer.parseInt(_time_of_therapy) == -2 || Double.parseDouble(_speed) == -2 ||
                Double.parseDouble(_actual_distance) == -2 || Double.parseDouble(_depth_of_water) == -2
                || Double.parseDouble(_water_temperature) == -2) {
            return;
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    setData();
                }
            }).start();
        }
    }

    /**
     * 给实体类赋值
     */
    private void setData() {
        TreadmillTraininginWaterInfo treadmillTraininginWaterInfo = new TreadmillTraininginWaterInfo();
        treadmillTraininginWaterInfo.setTime_of_therapy(Integer.parseInt(_time_of_therapy));
        treadmillTraininginWaterInfo.setSpeed(Double.parseDouble(_speed));
        treadmillTraininginWaterInfo.setActual_distance(Double.parseDouble(_actual_distance));
        treadmillTraininginWaterInfo.setDepth_of_water(Double.parseDouble(_depth_of_water));
        treadmillTraininginWaterInfo.setWater_temperature(Double.parseDouble(_water_temperature));

        if(mVortex_open.isChecked()){
            treadmillTraininginWaterInfo.setVortex(mVortex_open.getText().toString().trim());
        }else if(mVortex_close.isChecked()){
            treadmillTraininginWaterInfo.setVortex(mVortex_close.getText().toString().trim());
        }

        treadmillTraininginWaterInfo.setVideo_record(mVideo_record.getText().toString().trim());
        treadmillTraininginWaterInfo.setDate_time(mDate_time.getText().toString().trim());
        treadmillTraininginWaterInfo.setExecutor(mExecutor.getText().toString().trim());
        treadmillTraininginWaterInfo.setRemark(mRemark.getText().toString().trim());
        treadmillTraininginWaterInfo.setGait_guidance(mGait_guidance.getText().toString().trim());
        treadmillTraininginWaterInfo.setSoap_record(mSoap_record.getText().toString().trim());
        treadmillTraininginWaterInfo.setSpeed_evaluation(mSpeed_evaluation.getText().toString().trim());
        treadmillTraininginWaterInfo.setTherapeutic_effect(mTherapeutic_effect.getText().toString().trim());

        saveData(treadmillTraininginWaterInfo);

    }

    /**
     * 向服务器添加数据
     *
     * @param _treadmillTraininginWaterInfo
     */
    private void saveData(TreadmillTraininginWaterInfo _treadmillTraininginWaterInfo) {

        //localhost:8080/under_water_threadmill s/update/85.json? user_auth_id=1&& patient_info_id=85
        if (flag) {
            try {
                SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                int use_id = preferences.getInt("use_id", -1);
                SharedPreferences sharedPreferences = getSharedPreferences("patientinfo", MODE_PRIVATE);
                int id = sharedPreferences.getInt("id", -1);

                Log.i("training", "发请求");
                String URL_TREADMILL_TRAINING_IN_WATER_UPDATE = NetUrlAddress.ipAndPort + "/under_water_threadmills/update/"
                        + treadmillTraininginWaterInfo.getId() + ".json?token="+NetUrlAddress.token+"&&user_auth_id=" + use_id + "&&patient_info_id=" + id;
                String params = "under_water_threadmill[date_time]=" + _treadmillTraininginWaterInfo.getDate_time()
                        + "&under_water_threadmill[time_of_therapy]=" + _treadmillTraininginWaterInfo.getTime_of_therapy()
                        + "&under_water_threadmill[speed]=" + _treadmillTraininginWaterInfo.getSpeed()
                        + "&under_water_threadmill[actual_distance]=" + _treadmillTraininginWaterInfo.getActual_distance()
                        + "&under_water_threadmill[speed_evaluation]=" + _treadmillTraininginWaterInfo.getSpeed_evaluation()
                        + "&under_water_threadmill[depth_of_water]=" + _treadmillTraininginWaterInfo.getDepth_of_water()
                        + "&under_water_threadmill[water_temperature]=" + _treadmillTraininginWaterInfo.getWater_temperature()
                        + "&under_water_threadmill[vortex]=" + _treadmillTraininginWaterInfo.getVortex()
                        + "&under_water_threadmill[gait_guidance]=" + _treadmillTraininginWaterInfo.getGait_guidance()
                        + "&under_water_threadmill[video_record]=" + _treadmillTraininginWaterInfo.getVideo_record()
                        + "&under_water_threadmill[therapeutic_effect]=" + _treadmillTraininginWaterInfo.getTherapeutic_effect()
                        + "&under_water_threadmill[soap_record]=" + _treadmillTraininginWaterInfo.getSoap_record()
                        + "&under_water_threadmill[remark]=" + _treadmillTraininginWaterInfo.getRemark()
                        + "&under_water_threadmill[executor]=" + _treadmillTraininginWaterInfo.getExecutor()
                        + "&token=" + NetUrlAddress.token;


                HttpUtils.doPostAsyn(URL_TREADMILL_TRAINING_IN_WATER_UPDATE, params, new HttpUtils.CallBack() {
                    @Override
                    public void onRequestComplete(String result) {
                        Log.i("training", "result1=" + result);
                        if(result.equals("success")){
                            //发广播
                            Intent intent = new Intent(TableTreadmillTrainingInWaterIndexActivity.ACTION_INTENT_TREADMILL_TRAINING_IN_WATER_ADD);
                            sendBroadcast(intent);
                            finish();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                int use_id = preferences.getInt("use_id", -1);
                SharedPreferences sharedPreferences = getSharedPreferences("patientinfo", MODE_PRIVATE);
                int id = sharedPreferences.getInt("id", -1);

                String URL_TREADMILL_TRAINING_IN_WATER_ADD = NetUrlAddress.ipAndPort + "/under_water_threadmills/create/1.json?" +
                        "token="+NetUrlAddress.token+"&&user_auth_id=" + String.valueOf(use_id) + "&&patient_info_id=" + id;

                String params = "under_water_threadmill[date_time]=" + _treadmillTraininginWaterInfo.getDate_time()
                        + "&under_water_threadmill[time_of_therapy]=" + _treadmillTraininginWaterInfo.getTime_of_therapy()
                        + "&under_water_threadmill[speed]=" + _treadmillTraininginWaterInfo.getSpeed()
                        + "&under_water_threadmill[actual_distance]=" + _treadmillTraininginWaterInfo.getActual_distance()
                        + "&under_water_threadmill[speed_evaluation]=" + _treadmillTraininginWaterInfo.getSpeed_evaluation()
                        + "&under_water_threadmill[depth_of_water]=" + _treadmillTraininginWaterInfo.getDepth_of_water()
                        + "&under_water_threadmill[water_temperature]=" +_treadmillTraininginWaterInfo.getWater_temperature()
                        + "&under_water_threadmill[vortex]=" + _treadmillTraininginWaterInfo.getVortex()
                        + "&under_water_threadmill[gait_guidance]=" + _treadmillTraininginWaterInfo.getGait_guidance()
                        + "&under_water_threadmill[video_record]=" + _treadmillTraininginWaterInfo.getVideo_record()
                        + "&under_water_threadmill[therapeutic_effect]=" + _treadmillTraininginWaterInfo.getTherapeutic_effect()
                        + "&under_water_threadmill[soap_record]=" + _treadmillTraininginWaterInfo.getSoap_record()
                        + "&under_water_threadmill[remark]=" + _treadmillTraininginWaterInfo.getRemark()
                        + "&under_water_threadmill[executor]=" + _treadmillTraininginWaterInfo.getExecutor()
                        + "&token=" + NetUrlAddress.token;

                HttpUtils.doPostAsyn(URL_TREADMILL_TRAINING_IN_WATER_ADD, params, new HttpUtils.CallBack() {
                    @Override
                    public void onRequestComplete(String result) {
                        Log.i("training", "result2=" + result);
                        if(result.equals("success")){
                            //发广播
                            Intent intent = new Intent(TableTreadmillTrainingInWaterIndexActivity.ACTION_INTENT_TREADMILL_TRAINING_IN_WATER_ADD);
                            sendBroadcast(intent);
                            finish();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



    /**
     * 对输入的空值和字符串判断，保证输入类型为数字类型
     */
    private String inputStyle(String str, String msg) {
        if ("".equals(str) || str.length() == 0 || str == null) {
            str = "-1";
        }
        //输入的不能为汉字，符号
        else if (!NumberUtils.isNumber(str)) {
            showMsg(msg);
            str = "-2";
        }
        return str;
    }

    /**
     * 动态dialog
     *
     * @param textView
     * @param items
     */
    public void choose(final TextView textView, final String[] items) {

        // 创建builder
        AlertDialog.Builder builder = new AlertDialog.Builder(
                TableTreadmillTrainingInWaterAddActivity.this);

        builder.setCancelable(false) // 不响应back按钮
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(PatientInfoAddActivity.this,
//                                "选择了" + items[which], Toast.LENGTH_SHORT)
//                                .show();
                        textView.setText(items[which]);
                    }
                });
        // 创建Dialog对象
        AlertDialog dlg = builder.create();
        dlg.show();
    }

    /**
     * 时间弹框
     */
    private void dateChoose(final TextView text) {
        //时间选择器
        pwTime1 = new TimePopupWindow(this, TimePopupWindow.Type.YEAR_MONTH_DAY);
        //时间选择后回调
        pwTime1.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                text.setText(getDate(date));
            }
        });
        //弹出时间选择器
        text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pwTime1.showAtLocation(text, Gravity.BOTTOM, 0, 0, new Date());
            }
        });

        //设置循环
        pwTime1.setCyclic(true);
    }

    /**
     * 获取格式时间: yyyy-MM-dd
     *
     * @param date
     * @return
     */
    private String getDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
