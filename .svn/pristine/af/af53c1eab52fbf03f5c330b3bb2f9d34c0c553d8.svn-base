package me.zhangls.rilintech.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang.math.NumberUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.DischargeSummarizeInfo;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;
import me.zhangls.rilintech.utils.HttpUtils;

/**
 * Created by rilintech on 15/9/11.
 */
public class DischargeSummaryAddActivity extends BaseActivity implements View.OnClickListener {
    //时间选择器 格式：2011-02-15
    private TimePopupWindow pwTime1,pwTime2,pwTime3;
    private TextView mStart_time, mEnd_time, mZj_time;
    private EditText mCishu, mTimes, mStart_score, mEnd_score,
            mAnalysis, mProgress, mWeak, mSummarize, mGuidance, mRemark, mZjze;
    private ImageView mBack, mEdit, mSave;
    private DischargeSummarizeInfo dischargeSummarizeInfo;
    private boolean flag = false;
    private String _mCishu,_mTimes,_mStart_score,_mEnd_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_discharge_summary);
        initView();
        initData();
        initListener();
        //时间选择
        dateChoose1(mStart_time);
        dateChoose2(mEnd_time);
        dateChoose3(mZj_time);
    }

    private void initView() {

        mBack = (ImageView) findViewById(R.id.title_back);
        mEdit = (ImageView) findViewById(R.id.title_edit);
        mSave = (ImageView) findViewById(R.id.title_save);

        mStart_time = (TextView) findViewById(R.id.start_time_edit);
        mEnd_time = (TextView) findViewById(R.id.end_time_edit);
        mZj_time = (TextView) findViewById(R.id.zj_time_edit);

        mCishu = (EditText) findViewById(R.id.cishu_edit);
        mTimes = (EditText) findViewById(R.id.times_edit);
        mStart_score = (EditText) findViewById(R.id.start_score_edit);
        mEnd_score = (EditText) findViewById(R.id.end_score_edit);
        mAnalysis = (EditText) findViewById(R.id.analysis_edit);
        mProgress = (EditText) findViewById(R.id.progress_edit);
        mWeak = (EditText) findViewById(R.id.weak_edit);
        mSummarize = (EditText) findViewById(R.id.summarize_edit);
        mGuidance = (EditText) findViewById(R.id.guidance_edit);
        mRemark = (EditText) findViewById(R.id.remark_edit);
        mZjze = (EditText) findViewById(R.id.zjze_edit);

    }

    private void initData() {
        Intent intent = getIntent();
        dischargeSummarizeInfo = (DischargeSummarizeInfo) intent.getSerializableExtra("discharge_summary_add");
        if (dischargeSummarizeInfo != null) {
            loadDischargeSummarizeInfoData();
            setEable(false);
        }
    }

    private void setEable(boolean flag) {
        mStart_time.setEnabled(flag);
        mEnd_time.setEnabled(flag);
        mZj_time.setEnabled(flag);
        mCishu.setEnabled(flag);
        mTimes.setEnabled(flag);
        mStart_score.setEnabled(flag);
        mEnd_score.setEnabled(flag);
        mAnalysis.setEnabled(flag);
        mProgress.setEnabled(flag);
        mWeak.setEnabled(flag);
        mSummarize.setEnabled(flag);
        mGuidance.setEnabled(flag);
        mRemark.setEnabled(flag);
        mZjze.setEnabled(flag);
    }

    private void loadDischargeSummarizeInfoData() {
        //更改图标，编程编辑图标
        mEdit.setVisibility(View.VISIBLE);
        mSave.setVisibility(View.GONE);

        if (dischargeSummarizeInfo.getCishu() == -1) {
            mCishu.setText("");
        } else {
            mCishu.setText(dischargeSummarizeInfo.getCishu()+ "");
        }

        if (dischargeSummarizeInfo.getTimes() == -1) {
            mTimes.setText("");
        } else {
            mTimes.setText(dischargeSummarizeInfo.getTimes()+ "");
        }

        if (dischargeSummarizeInfo.getStart_score() == -1) {
            mStart_score.setText("");
        } else {
            mStart_score.setText(dischargeSummarizeInfo.getStart_score()+ "");
        }

        if (dischargeSummarizeInfo.getEnd_score() == -1) {
            mEnd_score.setText("");
        } else {
            mEnd_score.setText(dischargeSummarizeInfo.getEnd_score()+ "");
        }
        mStart_time.setText(dischargeSummarizeInfo.getStart_time());
        mEnd_time.setText(dischargeSummarizeInfo.getEnd_time());
        mZj_time.setText(dischargeSummarizeInfo.getZj_time());
        mAnalysis.setText(dischargeSummarizeInfo.getAnalysis());
        mProgress.setText(dischargeSummarizeInfo.getProgress());
        mWeak.setText(dischargeSummarizeInfo.getWeak());
        mSummarize.setText(dischargeSummarizeInfo.getSummarize());
        mGuidance.setText(dischargeSummarizeInfo.getGuidance());
        mRemark.setText(dischargeSummarizeInfo.getRemark());
        mZjze.setText(dischargeSummarizeInfo.getZjze());

    }

    private void initListener() {
        mBack.setOnClickListener(this);
        mEdit.setOnClickListener(this);
        mSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                dischargeSummarizeInfoSave();
                break;
        }
    }
    /**
     * 保存数据
     */
    private void dischargeSummarizeInfoSave() {
        _mCishu=mCishu.getText().toString().trim();
        _mTimes=mTimes.getText().toString().trim();
        _mStart_score=mStart_score.getText().toString().trim();
        _mEnd_score=mEnd_score.getText().toString().trim();
        _mCishu=inputStyle(_mCishu, "次数只能为数字");
        _mTimes=inputStyle(_mTimes, "治疗次数只能为数字");
        _mStart_score=inputStyle(_mStart_score, "初评得分只能为数字");
        _mEnd_score=inputStyle(_mEnd_score,"末评得分只能为数字");

        if (Integer.parseInt(_mCishu) == -2 || Integer.parseInt(_mTimes) == -2 ||
                Integer.parseInt(_mStart_score) == -2 || Integer.parseInt(_mEnd_score) == -2
                ) {
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

    private void setData() {

        DischargeSummarizeInfo dischargeSummarizeInfo=new DischargeSummarizeInfo();
        dischargeSummarizeInfo.setStart_time(mStart_time.getText().toString().trim());
        dischargeSummarizeInfo.setZjze(mZjze.getText().toString().trim());
        dischargeSummarizeInfo.setZj_time(mZj_time.getText().toString().trim());
        dischargeSummarizeInfo.setWeak(mWeak.getText().toString().trim());
        dischargeSummarizeInfo.setTimes(Integer.parseInt(_mTimes));
        dischargeSummarizeInfo.setAnalysis(mAnalysis.getText().toString().trim());
        dischargeSummarizeInfo.setCishu(Integer.parseInt(_mCishu));
        dischargeSummarizeInfo.setEnd_score(Integer.parseInt(_mEnd_score));
        dischargeSummarizeInfo.setEnd_time(mEnd_time.getText().toString().trim());
        dischargeSummarizeInfo.setGuidance(mGuidance.getText().toString().trim());
        dischargeSummarizeInfo.setProgress(mProgress.getText().toString().trim());
        dischargeSummarizeInfo.setRemark(mRemark.getText().toString().trim());
        dischargeSummarizeInfo.setSummarize(mSummarize.getText().toString().trim());
        dischargeSummarizeInfo.setStart_score(Integer.parseInt(_mStart_score));
        saveData(dischargeSummarizeInfo);
    }

    private void saveData(DischargeSummarizeInfo _dischargeSummarizeInfo) {
        if (flag) {
            try {
                SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                int use_id = preferences.getInt("use_id", -1);
                SharedPreferences sharedPreferences = getSharedPreferences("patientinfo", MODE_PRIVATE);
                int id = sharedPreferences.getInt("id", -1);

                Log.i("dischargesummary", "发请求");
                String URL_DISCHARGE_SUMMARY_UPDATE = NetUrlAddress.ipAndPort + "/discharge_summarizes/update/"
                        + dischargeSummarizeInfo.getId() + ".json?token="+NetUrlAddress.token+"&&user_auth_id=" + use_id + "&&patient_info_id=" + id;
                String params = "discharge_summarize[cishu]=" + _dischargeSummarizeInfo.getCishu()
                        + "&discharge_summarize[start_time]=" +_dischargeSummarizeInfo.getStart_time()
                        + "&discharge_summarize[end_time]=" +_dischargeSummarizeInfo.getEnd_time()
                        + "&discharge_summarize[times]=" +_dischargeSummarizeInfo.getTimes()
                        + "&discharge_summarize[start_score]=" +_dischargeSummarizeInfo.getStart_score()
                        + "&discharge_summarize[end_score]=" +_dischargeSummarizeInfo.getEnd_score()
                        + "&discharge_summarize[analysis]=" +_dischargeSummarizeInfo.getAnalysis()
                        + "&discharge_summarize[progress]=" +_dischargeSummarizeInfo.getProgress()
                        + "&discharge_summarize[weak]=" +_dischargeSummarizeInfo.getWeak()
                        + "&discharge_summarize[summarize]=" +_dischargeSummarizeInfo.getSummarize()
                        + "&discharge_summarize[guidance]=" +_dischargeSummarizeInfo.getGuidance()
                        + "&discharge_summarize[remark]=" +_dischargeSummarizeInfo.getRemark()
                        + "&discharge_summarize[zj_time]=" +_dischargeSummarizeInfo.getZj_time()
                        + "&discharge_summarize[zjze]=" +_dischargeSummarizeInfo.getZjze()
                        + "&token=" + NetUrlAddress.token;


                HttpUtils.doPostAsyn(URL_DISCHARGE_SUMMARY_UPDATE, params, new HttpUtils.CallBack() {
                    @Override
                    public void onRequestComplete(String result) {
                        Log.i("dischargesummary", "result=" + result);
                        if(result.equals("success")){
                            //发广播
                            Intent intent = new Intent(DischargeSummaryIndexActivity.ACTION_INTENT_DISCHARGESUMMARY_ADD);
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
                // /under_water_threadmills/create/1.json?patient_info_id=85&&user_auth_id=1
                SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                int use_id = preferences.getInt("use_id", -1);
                SharedPreferences sharedPreferences = getSharedPreferences("patientinfo", MODE_PRIVATE);
                int id = sharedPreferences.getInt("id", -1);

                String URL_DISCHARGE_SUMMARY_ADD = NetUrlAddress.ipAndPort + "/discharge_summarizes/create/1.json?" +
                        "token="+NetUrlAddress.token+"&&user_auth_id=" + String.valueOf(use_id) + "&&patient_info_id=" + id;

                String params = "discharge_summarize[cishu]=" + _dischargeSummarizeInfo.getCishu()
                        + "&discharge_summarize[start_time]=" +_dischargeSummarizeInfo.getStart_time()
                        + "&discharge_summarize[end_time]=" +_dischargeSummarizeInfo.getEnd_time()
                        + "&discharge_summarize[times]=" +_dischargeSummarizeInfo.getTimes()
                        + "&discharge_summarize[start_score]=" +_dischargeSummarizeInfo.getStart_score()
                        + "&discharge_summarize[end_score]=" +_dischargeSummarizeInfo.getEnd_score()
                        + "&discharge_summarize[analysis]=" +_dischargeSummarizeInfo.getAnalysis()
                        + "&discharge_summarize[progress]=" +_dischargeSummarizeInfo.getProgress()
                        + "&discharge_summarize[weak]=" +_dischargeSummarizeInfo.getWeak()
                        + "&discharge_summarize[summarize]=" +_dischargeSummarizeInfo.getSummarize()
                        + "&discharge_summarize[guidance]=" +_dischargeSummarizeInfo.getGuidance()
                        + "&discharge_summarize[remark]=" +_dischargeSummarizeInfo.getRemark()
                        + "&discharge_summarize[zj_time]=" +_dischargeSummarizeInfo.getZj_time()
                        + "&discharge_summarize[zjze]=" +_dischargeSummarizeInfo.getZjze()
                        + "&token=" + NetUrlAddress.token;

                HttpUtils.doPostAsyn(URL_DISCHARGE_SUMMARY_ADD, params, new HttpUtils.CallBack() {
                    @Override
                    public void onRequestComplete(String result) {
                        Log.i("dischargesummary", "result=" + result);
                        // ShowToast.Long(result);
                        if(result.equals("success")){
                            //发广播
                            Intent intent = new Intent(DischargeSummaryIndexActivity.ACTION_INTENT_DISCHARGESUMMARY_ADD);
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
     * 时间弹框
     */
    private void dateChoose1(final TextView text) {
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
    } private void dateChoose2(final TextView text) {
        //时间选择器
        pwTime2 = new TimePopupWindow(this, TimePopupWindow.Type.YEAR_MONTH_DAY);
        //时间选择后回调
        pwTime2.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                text.setText(getDate(date));
            }
        });
        //弹出时间选择器
        text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pwTime2.showAtLocation(text, Gravity.BOTTOM, 0, 0, new Date());
            }
        });

        //设置循环
        pwTime2.setCyclic(true);
    }
    private void dateChoose3(final TextView text) {
        //时间选择器
        pwTime3 = new TimePopupWindow(this, TimePopupWindow.Type.YEAR_MONTH_DAY);
        //时间选择后回调
        pwTime3.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                text.setText(getDate(date));
            }
        });
        //弹出时间选择器
        text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pwTime3.showAtLocation(text, Gravity.BOTTOM, 0, 0, new Date());
            }
        });

        //设置循环
        pwTime3.setCyclic(true);
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
