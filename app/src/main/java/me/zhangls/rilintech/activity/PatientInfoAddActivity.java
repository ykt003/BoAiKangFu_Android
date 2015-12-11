package me.zhangls.rilintech.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhangls.shengshiqu.wheel.widget.OnWheelChangedListener;
import com.zhangls.shengshiqu.wheel.widget.WheelView;
import com.zhangls.shengshiqu.wheel.widget.adapters.ArrayWheelAdapter;
import com.zhangls.sortlistview.SortListActivity;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.constants.ToastMsg;
import me.zhangls.rilintech.fragment.PatientInfoFragment;
import me.zhangls.rilintech.model.PatientInfo;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow.Type;
import me.zhangls.rilintech.utils.DateTools;
import me.zhangls.rilintech.utils.HttpUtils;

/**
 * Created by zsn on 15/8/4.
 */
public class PatientInfoAddActivity extends BaseActivity implements View.OnClickListener, OnWheelChangedListener {
    private ImageView icf_tongbu_image;
    private ImageView icd_tongbu_image;
    private ImageView mBack, mSave, mEdit;
    private PatientInfo patientInfo = null;
    private String patient_uuid = null;
    private String _bah, _age, _tel, _height, _weight, _pXY, _shuZya, _pXl, _pBxl, _pSzbxl, _pMb, _pXt, _bmi;

    private EditText mNameEdit, mAgeEdit, mBahEdit, mZhiweiEdit, mFirst_releate_personEdit, mTelEdit, mHeightEdit,
            mWeightEdit, mP_zhenduanEdit, mP_bbbwEdit, mP_gnzaEdit, mP_fbjgEdit, mP_zljgEdit,
            mP_kfjgEdit, mP_xyEdit, mShu_z_yaEdit, mP_xlEdit, mP_bxlEdit, mP_szbxlEdit, mP_mbEdit, mP_xtEdit, mZhliao_bwEdit,
            mExecutorEdit;
    private TextView mSexText, mNationText, mBrithdayText, mProvince_idText, mWh_chdText, mHy_zhkText,
            mP_keshiText, mP_jflyText, mP_bmiText, mP_sqksText, mP_startdateText, mP_bingchengText, mZl_xmText,
            mZl_ztText, mSj_apText, mLr_timeText, mLczd_icdEdit, mBbbw_icdEdit;
    private final String[] mSexItems = new String[]{"男", "女"};
    private final String[] mNationItems = new String[]{"汉族", "壮族", "回族", "满族", "苗族", "蒙古族"};
    private final String[] mWh_chdItems = new String[]{"小学", "初中", "中专", "高中", "大专", "高中", "大专", "本科", "硕士", "博士", "其他"};
    private final String[] mHy_zhkItems = new String[]{"未婚", "已婚", "离异", "丧偶", "其他"};
    private final String[] mP_keshiItems = new String[]{"眼科", "妇科", "骨科"};
    private final String[] mP_jflyItems = new String[]{"城镇居民基本医疗保险", "城镇职工基本医疗保险", "新型农村合作医疗", "贫困救助", "商业医疗保险", "全公费", "全自费", "其他社会保险", "其他"};
    private final String[] mZl_xmItems = new String[]{"化疗", "放疗", "CT", "MR"};
    private final String[] mZl_ztItems = new String[]{"治疗中", "附加费"};
    private final String[] mSj_apItems = new String[]{"第一疗程"};

    //省市级联
    // private ArrayList<String> options1Items = new ArrayList<String>();
    // private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    // private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    //private OptionsPopupWindow pwOptions;


    //时间选择器 格式：2011-02-15
    private TimePopupWindow pwTime1;
    private TimePopupWindow pwTime2;
    //时间选择器 格式：2012-04-25 09：45
    private TimePopupWindow tpwTime;
    //判断插入还是更新数据库
    private boolean flag = false;

    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;

    private RelativeLayout relativeLayout_patient_add_1;
    private RelativeLayout relativeLayout_patient_add_2;
    private View relativeLayout_patient_add_3;
    private Button mBtnCancel;
    private Button mBtnSummit;
    private ScrollView mPatient_scroll_view;
    private boolean isProvinceChecked;
    private static final int REQUEST_CODE_1 = 2211;
    private static final int REQUEST_CODE_2 = 2212;
    private static final int REQUEST_CODE_3 = 2213;
    private static final int REQUEST_CODE_4 = 2214;
    //页码
    private int page;
    //获取时间段的天数
    private long bingcheng = 0;
    private long specificStartdateMilliseconds = 0;
    private int user_auth_id;

    private ArrayList<PatientInfo> mPatientInfoList;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1006) {
                showMsg("发病日期不能大于当前日期");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_patient_info);

        getSharedPreferencesData();
        initView();

        initData();

        initListener();
        //时间选择
        dateChoose1(mBrithdayText);
        dateChoose2(mP_startdateText);
        timeChoose(mLr_timeText);
        //城市级联选择
        setUpcityChooseData();

    }

    /**
     * 获取当前登录用户和当前患者的ID
     */
    private void getSharedPreferencesData() {

        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        user_auth_id = preferences.getInt("use_id", 2);
    }

    /**
     * 初始化view
     */
    private void initView() {

        mPatient_scroll_view = (ScrollView) findViewById(R.id.patient_scrollview);

        relativeLayout_patient_add_1 = (RelativeLayout) findViewById(R.id.patient_add_rl1);
        relativeLayout_patient_add_2 = (RelativeLayout) findViewById(R.id.patient_add_rl2);
        relativeLayout_patient_add_3 = (View) findViewById(R.id.patient_add_rl3);

        mBtnCancel = (Button) findViewById(R.id.btnCancel);
        mBtnSummit = (Button) findViewById(R.id.btnSubmit);

        mViewProvince = (WheelView) findViewById(R.id.options1);
        mViewCity = (WheelView) findViewById(R.id.options2);
        mViewDistrict = (WheelView) findViewById(R.id.options3);

        mBack = (ImageView) findViewById(R.id.tittle_back);
        mSave = (ImageView) findViewById(R.id.tittle_save);
        mEdit = (ImageView) findViewById(R.id.tittle_edit);

        mBahEdit = (EditText) findViewById(R.id.hospital_record_edit);
        mNameEdit = (EditText) findViewById(R.id.patient_name_edit);
        mAgeEdit = (EditText) findViewById(R.id.patient_age_edit);
        mZhiweiEdit = (EditText) findViewById(R.id.patient_zhiwei_edit);
        mFirst_releate_personEdit = (EditText) findViewById(R.id.patient_first_releate_person_edit);
        mTelEdit = (EditText) findViewById(R.id.patient_tel_edit);
        mHeightEdit = (EditText) findViewById(R.id.patient_height_edit);
        mWeightEdit = (EditText) findViewById(R.id.patient_weight_edit);
        mP_zhenduanEdit = (EditText) findViewById(R.id.patient_p_zhenduan_edit);
        mLczd_icdEdit = (TextView) findViewById(R.id.patient_lczd_icd_edit);
        mP_bbbwEdit = (EditText) findViewById(R.id.patient_p_bbbw_edit);
        mBbbw_icdEdit = (TextView) findViewById(R.id.patient_bbbw_icd_edit);
        mP_gnzaEdit = (EditText) findViewById(R.id.patient_p_gnza_edit);
        mP_fbjgEdit = (EditText) findViewById(R.id.patient_p_fbjg_edit);
        mP_zljgEdit = (EditText) findViewById(R.id.patient_p_zljg_edit);
        mP_kfjgEdit = (EditText) findViewById(R.id.patient_p_kfjg_edit);
        mP_xyEdit = (EditText) findViewById(R.id.patient_p_xy_edit);
        mShu_z_yaEdit = (EditText) findViewById(R.id.patient_shu_z_ya_edit);
        mP_xlEdit = (EditText) findViewById(R.id.patient_p_xl_edit);
        mP_bxlEdit = (EditText) findViewById(R.id.patient_p_bxl_edit);
        mP_szbxlEdit = (EditText) findViewById(R.id.patient_p_szbxl_edit);
        mP_mbEdit = (EditText) findViewById(R.id.patient_p_mb_edit);
        mP_xtEdit = (EditText) findViewById(R.id.patient_p_xt_edit);
        mZhliao_bwEdit = (EditText) findViewById(R.id.patient_zhliao_bw_edit);
        mExecutorEdit = (EditText) findViewById(R.id.patient_executor_edit);


        mSexText = (TextView) findViewById(R.id.patient_sex_edit);
        mNationText = (TextView) findViewById(R.id.patient_nation_edit);
        mBrithdayText = (TextView) findViewById(R.id.patient_birthday_edit);
        mProvince_idText = (TextView) findViewById(R.id.patient_province_id_edit);
        mWh_chdText = (TextView) findViewById(R.id.patient_wh_chd_edit);
        mHy_zhkText = (TextView) findViewById(R.id.patient_hy_zhk_edit);
        mP_keshiText = (TextView) findViewById(R.id.patient_p_keshi_edit);
        mP_jflyText = (TextView) findViewById(R.id.patient_p_jfly_edit);
        mP_bmiText = (TextView) findViewById(R.id.patient_p_bmi_edit);
        mP_sqksText = (TextView) findViewById(R.id.patient_p_sqks_edit);
        mP_startdateText = (TextView) findViewById(R.id.patient_p_startdate_edit);
        mP_bingchengText = (TextView) findViewById(R.id.patient_p_bingcheng_edit);
        mZl_xmText = (TextView) findViewById(R.id.patient_zl_xm_edit);
        mZl_ztText = (TextView) findViewById(R.id.patient_zl_zt_edit);
        mSj_apText = (TextView) findViewById(R.id.patient_sj_ap_edit);
        mLr_timeText = (TextView) findViewById(R.id.patient_lr_time_edit);


        icd_tongbu_image = (ImageView) findViewById(R.id.icd_tongbu_image);
        icf_tongbu_image = (ImageView) findViewById(R.id.icf_tongbu_image);
    }


    /**
     * 初始化数据
     */
    private void initData() {


        Intent intent = getIntent();
        patientInfo = (PatientInfo) intent.getSerializableExtra("patient_info_add");

        if (patientInfo != null) {
            patientInfo = loadDataByNetworkType();
            loadPatientInfoData();
            //loadFirst();
            setEable(false);
        }
    }

    /**
     * 设置三级联动数据
     */
    private void setUpcityChooseData() {
        //点击弹出城市选择器
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(PatientInfoAddActivity.this, mProvinceDatas));
        // 设置可见条目数量
        //mViewProvince.setVisibleItems(7);
        //mViewCity.setVisibleItems(7);
        //mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();

    }

    /**
     * 各种控件的监听
     */
    private void initListener() {

        // mHeightEdit.setOnClickListener(this);
        // mWeightEdit.setOnClickListener(this);

        mBbbw_icdEdit.setOnClickListener(this);
        mLczd_icdEdit.setOnClickListener(this);

        mBtnCancel.setOnClickListener(this);
        mBtnSummit.setOnClickListener(this);

        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);

        mBack.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mEdit.setOnClickListener(this);

        mSexText.setOnClickListener(this);
        mNationText.setOnClickListener(this);
        mWh_chdText.setOnClickListener(this);
        mHy_zhkText.setOnClickListener(this);
        mP_keshiText.setOnClickListener(this);
        mP_jflyText.setOnClickListener(this);
        mZl_xmText.setOnClickListener(this);
        mZl_ztText.setOnClickListener(this);
        mSj_apText.setOnClickListener(this);
        mProvince_idText.setOnClickListener(this);

        icd_tongbu_image.setOnClickListener(this);
        icf_tongbu_image.setOnClickListener(this);

    }

    /**
     * 设置scrollview能否上下滑动
     */

    private class patientOnTouchEvent implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (isProvinceChecked) {
                return false;
            }
            return true;
        }
    }

    /**
     * 设置不可点击
     */
    private void setEable(boolean flag) {

        mSexText.setEnabled(flag);
        mNationText.setEnabled(flag);
        mBrithdayText.setEnabled(flag);
        mProvince_idText.setEnabled(flag);
        mWh_chdText.setEnabled(flag);
        mHy_zhkText.setEnabled(flag);
        mP_keshiText.setEnabled(flag);
        mP_jflyText.setEnabled(flag);
        mP_bmiText.setEnabled(flag);
        mP_sqksText.setEnabled(flag);
        mP_startdateText.setEnabled(flag);
        mP_bingchengText.setEnabled(flag);
        mZl_xmText.setEnabled(flag);
        mZl_ztText.setEnabled(flag);
        mSj_apText.setEnabled(flag);
        mLr_timeText.setEnabled(flag);

        mBahEdit.setEnabled(flag);
        mNameEdit.setEnabled(flag);
        mAgeEdit.setEnabled(flag);
        mZhiweiEdit.setEnabled(flag);
        mFirst_releate_personEdit.setEnabled(flag);
        mTelEdit.setEnabled(flag);
        mHeightEdit.setEnabled(flag);
        mWeightEdit.setEnabled(flag);
        mP_zhenduanEdit.setEnabled(flag);
        mLczd_icdEdit.setEnabled(flag);
        mP_bbbwEdit.setEnabled(flag);
        mBbbw_icdEdit.setEnabled(flag);
        mP_gnzaEdit.setEnabled(flag);
        mP_fbjgEdit.setEnabled(flag);
        mP_zljgEdit.setEnabled(flag);
        mP_kfjgEdit.setEnabled(flag);
        mP_xyEdit.setEnabled(flag);
        mShu_z_yaEdit.setEnabled(flag);
        mP_xlEdit.setEnabled(flag);
        mP_bxlEdit.setEnabled(flag);
        mP_szbxlEdit.setEnabled(flag);
        mP_mbEdit.setEnabled(flag);
        mP_xtEdit.setEnabled(flag);
        mZhliao_bwEdit.setEnabled(flag);
        mExecutorEdit.setEnabled(flag);
    }

    /**
     * 页面赋值
     */
    private void loadPatientInfoData() {
        //更改图标，编程编辑图标
        mEdit.setVisibility(View.VISIBLE);
        mSave.setVisibility(View.GONE);
        //加载患者数据
        if (patientInfo.getmP_bah() == -1) {
            mBahEdit.setText("");
        } else {
            mBahEdit.setText("" + patientInfo.getmP_bah());
        }

        mNameEdit.setText(patientInfo.getmName());
        if (patientInfo.getmAge() == -1) {
            mAgeEdit.setText("");
        } else {
            mAgeEdit.setText("" + patientInfo.getmAge());
        }
        mZhiweiEdit.setText(patientInfo.getmZhiwei());
        mFirst_releate_personEdit.setText(patientInfo.getmFirst_releate_person());
//        if (patientInfo.getmTel() == -1) {
//            mTelEdit.setText("");
//        } else {
//            mTelEdit.setText("" + patientInfo.getmTel());
//        }
        mTelEdit.setText(patientInfo.getmTel());
//        if (patientInfo.getmHeight() == -1) {
//            mHeightEdit.setText("");
//        } else {
//            mHeightEdit.setText(""+ patientInfo.getmHeight()*100);
//        }
        //_height.substring(0,_height.indexOf("."));
        String h = String.valueOf(Double.parseDouble(patientInfo.getmHeight()) * 100);
        Log.d("33","hjh="+h);
        String h1 = h.substring(0, h.indexOf("."));
        Log.d("33","h1="+h1);
        mHeightEdit.setText(h1);
        if (patientInfo.getmWeight() == -1) {
            mWeightEdit.setText("");
        } else {
            mWeightEdit.setText("" + patientInfo.getmWeight());
        }
        mP_zhenduanEdit.setText(patientInfo.getmP_zhenduan());
        mLczd_icdEdit.setText(patientInfo.getmLczd_icd());
        mP_bbbwEdit.setText(patientInfo.getmP_bbbw());
        mBbbw_icdEdit.setText(patientInfo.getmBbbw_icd());
        mP_gnzaEdit.setText(patientInfo.getmP_gnza());
        mP_fbjgEdit.setText(patientInfo.getmP_fbjg());
        mP_zljgEdit.setText(patientInfo.getmP_zljg());
        mP_kfjgEdit.setText(patientInfo.getmP_kfjg());
        if (patientInfo.getmP_xy() == -1) {
            mP_xyEdit.setText("");
        } else {
            mP_xyEdit.setText("" + patientInfo.getmP_xy());
        }
        if (patientInfo.getmShu_z_ya() == -1) {
            mShu_z_yaEdit.setText("");
        } else {
            mShu_z_yaEdit.setText("" + patientInfo.getmShu_z_ya());
        }
        if (patientInfo.getmP_xl() == -1) {
            mP_xlEdit.setText("");
        } else {
            mP_xlEdit.setText("" + patientInfo.getmP_xl());
        }
        if (patientInfo.getmP_bxl() == -1) {
            mP_bxlEdit.setText("");
        } else {
            mP_bxlEdit.setText("" + patientInfo.getmP_bxl());
        }
        if (patientInfo.getmP_szbxl() == -1) {
            mP_szbxlEdit.setText("");
        } else {
            mP_szbxlEdit.setText("" + patientInfo.getmP_szbxl());
        }
        if (patientInfo.getmP_mb() == -1) {
            mP_mbEdit.setText("");
        } else {
            mP_mbEdit.setText("" + patientInfo.getmP_mb());
        }

        if (patientInfo.getmP_xt() == -1) {
            mP_xtEdit.setText("");
        } else {
            mP_xtEdit.setText("" + patientInfo.getmP_xt());
        }

        mZhliao_bwEdit.setText(patientInfo.getmZhliao_bw());
        mExecutorEdit.setText(patientInfo.getmExecutor());

        mSexText.setText(patientInfo.getmSex());
        mNationText.setText(patientInfo.getmNation());
        if (patientInfo.getmBirthday().equals("null")) {
            mBrithdayText.setText("");
        } else {
            mBrithdayText.setText(patientInfo.getmBirthday());
        }

        mProvince_idText.setText(patientInfo.getBirth_locate());
        mWh_chdText.setText(patientInfo.getmWh_chd());
        mHy_zhkText.setText(patientInfo.getmHy_zhk());
        mP_keshiText.setText(patientInfo.getmP_keshi());
        mP_jflyText.setText(patientInfo.getmP_jfly());
        if (patientInfo.getmP_bmi() <= 0) {
            mP_bmiText.setText("");
        } else {
            mP_bmiText.setText(patientInfo.getmP_bmi() + "");
        }
        mP_sqksText.setText(patientInfo.getmP_sqks());
        if (patientInfo.getmP_startdate().equals("null")) {
            mP_startdateText.setText("");
        } else {
            mP_startdateText.setText(patientInfo.getmP_startdate());
        }


        Log.i("log", "病程＝＝" + patientInfo.getmP_bingcheng());
        if (patientInfo.getmP_bingcheng() == "32760天") {
            mP_bingchengText.setText("");
        } else if (patientInfo.getmP_bingcheng().equals("null")) {

        } else {
            mP_bingchengText.setText(patientInfo.getmP_bingcheng());
        }

        mZl_xmText.setText(patientInfo.getmZl_xm());
        mZl_ztText.setText(patientInfo.getmZl_zt());
        mSj_apText.setText(patientInfo.getmSj_ap());
        if (patientInfo.getmLr_time().equals("null")) {
            mLr_timeText.setText("");
        } else {
            mLr_timeText.setText(patientInfo.getmLr_time());
        }
        mLczd_icdEdit.setText(patientInfo.getmLczd_icd());
        mBbbw_icdEdit.setText(patientInfo.getmBbbw_icd());
        mP_sqksText.setText(patientInfo.getmP_sqks());

    }

    /**
     * 保存患者
     */
    public void patientInfoSave() {

        // patientInfoManager.openDataBase();
        //edit
        _bah = mBahEdit.getText().toString().trim();
        _age = mAgeEdit.getText().toString().trim();
        _tel = mTelEdit.getText().toString().trim();
        _height = mHeightEdit.getText().toString().trim();
        _weight = mWeightEdit.getText().toString().trim();
        _pXY = mP_xyEdit.getText().toString().trim();
        _shuZya = mShu_z_yaEdit.getText().toString().trim();
        _pXl = mP_xlEdit.getText().toString().trim();
        _pBxl = mP_bxlEdit.getText().toString().trim();
        _pSzbxl = mP_szbxlEdit.getText().toString().trim();
        _pMb = mP_mbEdit.getText().toString().trim();
        _pXt = mP_xtEdit.getText().toString().trim();
        _bmi = mP_bmiText.getText().toString().trim();

        _bah = inputStyle(_bah, "病案号只能为数字");
        _age = inputStyle(_age, "年龄只能为数字");
        _weight = inputStyle(_weight, "体重只能为数字");
        _pXY = inputStyle(_pXY, "收缩压只能为数字");
        _shuZya = inputStyle(_shuZya, "舒张压只能为数字");
        _pXl = inputStyle(_pXl, "心率只能为数字");
        _pBxl = inputStyle(_pBxl, "靶心率只能为数字");
        _pSzbxl = inputStyle(_pSzbxl, "水中靶心率只能为数字");
        _pMb = inputStyle(_pMb, "脉搏只能为数字");
        _pXt = inputStyle(_pXt, "血糖只能为数字");
        _bmi = inputStyle(_bmi, "BMI只能为数字");

        Log.d("33","_bah="+_bah);
        Log.d("33","mNameEdit.getText().toString()="+mNameEdit.getText().toString());
        Log.d("33","mLczd_icdEdit.getText().toString()="+mLczd_icdEdit.getText().toString());

        if(_bah.equals("-1")){
            showMsg(ToastMsg.BAH_NO_DATA);
        }else if(mNameEdit.getText().toString().equals("")){
            showMsg(ToastMsg.NAME_NO_DATA);
        }else if(mLczd_icdEdit.getText().toString().equals("")){
            showMsg(ToastMsg.LCZD_NO_DATA);
        }
        if (Integer.parseInt(_bah) == -2 || Integer.parseInt(_age) == -2
                || Integer.parseInt(_weight) == -2 || Integer.parseInt(_pXY) == -2 ||
                Integer.parseInt(_shuZya) == -2 || Integer.parseInt(_pXl) == -2 || Integer.parseInt(_pBxl) == -2 ||
                Integer.parseInt(_pSzbxl) == -2 || Integer.parseInt(_pMb) == -2 || Integer.parseInt(_pXt) == -2 ||
                Double.parseDouble(_bmi) == -2) {
            return;
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        setData();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * 给实体类赋值
     */
    private void setData() {

        String sqksText = "";
        PatientInfo _PatientInfo = new PatientInfo();

        _PatientInfo.setmName(mNameEdit.getText().toString().trim());
        _PatientInfo.setmP_bah(Integer.parseInt(_bah));
        _PatientInfo.setmAge(Integer.parseInt(_age));
        _PatientInfo.setmZhiwei(mZhiweiEdit.getText().toString().trim());
        _PatientInfo.setmFirst_releate_person(mFirst_releate_personEdit.getText().toString().trim());
        _PatientInfo.setmTel(_tel);
        String h;
        if (_height.equals("") || _weight.equals("")) {
            return;
        } else {
            if(_height.contains(".")){
               h = _height.substring(0, _height.indexOf("."));
            }else{
                h=_height;
            }

            Log.d("33","h=="+h);
            int height = Integer.parseInt(h);
            int weight = Integer.parseInt(_weight);

            double bmi_score = 0;
            if (height > 0 && weight > 0) {
                bmi_score = 10000 * weight / (Math.pow(height, 2));
                //double bmi_score = 10000*weight / (height*height);
                if (bmi_score < 18.5 && bmi_score > 0) {
                    sqksText = "偏瘦";
                } else if (bmi_score >= 18.5 && bmi_score <= 23.9) {
                    sqksText = "正常";
                } else if (bmi_score >= 24) {
                    sqksText = "偏胖";
                } else {
                    sqksText = "";
                }
            }
            _PatientInfo.setmHeight(String.valueOf(height / 100.0));
            _PatientInfo.setmWeight(weight);
            _PatientInfo.setmP_bmi(bmi_score);
        }

        _PatientInfo.setmP_sqks(sqksText);

        _PatientInfo.setmP_zhenduan(mP_zhenduanEdit.getText().toString().trim());
        _PatientInfo.setmLczd_icd(mLczd_icdEdit.getText().toString().trim());
        _PatientInfo.setmBbbw_icd(mBbbw_icdEdit.getText().toString().trim());
        _PatientInfo.setmP_bbbw(mP_bbbwEdit.getText().toString().trim());
        _PatientInfo.setmP_gnza(mP_gnzaEdit.getText().toString().trim());
        _PatientInfo.setmP_fbjg(mP_fbjgEdit.getText().toString().trim());
        _PatientInfo.setmP_zljg(mP_zljgEdit.getText().toString().trim());
        _PatientInfo.setmP_kfjg(mP_kfjgEdit.getText().toString().trim());

        _PatientInfo.setmP_xy(Integer.parseInt(_pXY));
        _PatientInfo.setmShu_z_ya(Integer.parseInt(_shuZya));
        _PatientInfo.setmP_xl(Integer.parseInt(_pXl));
        _PatientInfo.setmP_bxl(Integer.parseInt(_pBxl));
        _PatientInfo.setmP_szbxl(Integer.parseInt(_pSzbxl));
        _PatientInfo.setmP_mb(Integer.parseInt(_pMb));
        _PatientInfo.setmP_xt(Integer.parseInt(_pXt));
        _PatientInfo.setmZhliao_bw(mZhliao_bwEdit.getText().toString().trim());
        _PatientInfo.setmExecutor(mExecutorEdit.getText().toString().trim());

        //text
        _PatientInfo.setmSex(mSexText.getText().toString().trim());
        _PatientInfo.setmNation(mNationText.getText().toString().trim());
        _PatientInfo.setmBirthday(mBrithdayText.getText().toString().trim());

        _PatientInfo.setBirth_locate(mProvince_idText.getText().toString().trim());
        //省市区
        _PatientInfo.setmProvince_id(mCurrentProviceId);
        _PatientInfo.setmState_id(mCurrentCityId);
        _PatientInfo.setmCity_id(mCurrentDistrictId);

        _PatientInfo.setmWh_chd(mWh_chdText.getText().toString().trim());
        _PatientInfo.setmHy_zhk(mHy_zhkText.getText().toString().trim());
        _PatientInfo.setmP_keshi(mP_keshiText.getText().toString().trim());
        _PatientInfo.setmP_jfly(mP_jflyText.getText().toString().trim());

        _PatientInfo.setmZl_xm(mZl_xmText.getText().toString().trim());
        _PatientInfo.setmZl_zt(mZl_ztText.getText().toString().trim());
        _PatientInfo.setmSj_ap(mSj_apText.getText().toString().trim());
        _PatientInfo.setmLr_time(mLr_timeText.getText().toString().trim());

        //得到当前时间的时间
        String nowDate = getDate(new Date());
        //特定的输入时间
        String startDate = mP_startdateText.getText().toString().trim();
        // 将日期转换为毫秒数进行比较
        long specificNowDateMilliseconds = specificTimeFormat(nowDate);
        System.out.println("nowDate=" + nowDate);
        System.out.println("startdate=" + startDate);
        //startdate=null
        if (startDate.length() > 0 && startDate != null && startDate != "" && !startDate.equals("null")) {
            specificStartdateMilliseconds = specificTimeFormat(startDate);
            if (specificNowDateMilliseconds >= specificStartdateMilliseconds) {
                //获取时间段的天数
                bingcheng = DateTools.getIntervalDays(startDate, nowDate);
                String newNowDate = nowDate.replace('-', '/');
                String newstartDate = startDate.replace('-', '/');
                //时间段相差
                String diffValue = DurationFormatUtils.formatPeriod(new Date(newstartDate).getTime(), new Date(newNowDate).getTime(), "y-M-d");
                String[] yearMonthDayValueArr = diffValue.split("-");
                String year = yearMonthDayValueArr[0];
                String month = yearMonthDayValueArr[1];
                String day = yearMonthDayValueArr[2];
                //_PatientInfo.setmP_bingcheng(bingcheng + "天");
                StringBuilder builder = new StringBuilder();
                builder.append(year).append("年").append(month).append("月").append(day).append("天");
                _PatientInfo.setmP_bingcheng(builder.toString());

                _PatientInfo.setmP_startdate(startDate);
                saveData(_PatientInfo);

            } else {
                bingcheng = 32760;
                _PatientInfo.setmP_startdate(startDate);
                _PatientInfo.setmP_bingcheng(bingcheng + "天");
                Message msg = new Message();
                msg.what = 1006;
                mhandler.sendMessage(msg);
                return;
            }
        } else {
            _PatientInfo.setmP_startdate("");
            saveData(_PatientInfo);
        }
    }

    /**
     * 编辑和插入时保存的操作
     *
     * @param _PatientInfo
     */
    private void saveData(PatientInfo _PatientInfo) {

        if (flag) {
            Log.d("33","_bah="+_bah);
            Log.d("33","mNameEdit.getText().toString()="+mNameEdit.getText().toString());
            Log.d("33","mLczd_icdEdit.getText().toString()="+mLczd_icdEdit.getText().toString());
            if(_bah.equals("")){
                showMsg(ToastMsg.BAH_NO_DATA);
            }else if(mNameEdit.getText().toString().equals("")){
                showMsg(ToastMsg.NAME_NO_DATA);
            }else if(mLczd_icdEdit.getText().toString().equals("")){
                showMsg(ToastMsg.LCZD_NO_DATA);
            }else{
                try {
                    //更新
                    // _PatientInfo.setmPatient_uuid(patientInfo.getmPatient_uuid());
                    // patientInfoManager.updatePatientInfo(_PatientInfo);

                    Log.i("ZSN", "发请求");
                    String URL_PATIRNT_UPDATE = NetUrlAddress.ipAndPort + "/patient_infos" +
                            "/update/" + patientInfo.getId() + ".json?token=" + NetUrlAddress.token + "&&user_auth_id=" + String.valueOf(user_auth_id) + "&&menu_id=15";

                    String params = "patient_info[p_bah]=" + _PatientInfo.getmP_bah()
                            + "&patient_info[name]=" + _PatientInfo.getmName()
                            + "&patient_info[sex]=" + _PatientInfo.getmSex()
                            + "&patient_info[age]=" + _PatientInfo.getmAge()
                            + "&patient_info[birthday]=" + _PatientInfo.getmBirthday()
                            + "&patient_info[birth_locate]=" + _PatientInfo.getBirth_locate()
                            + "&patient_info[province_id]=" + _PatientInfo.getmProvince_id()
                            + "&patient_info[state_id]=" + _PatientInfo.getmState_id()
                            + "&patient_info[city_id]=" + _PatientInfo.getmCity_id()
                            + "&patient_info[nation]=" + _PatientInfo.getmNation()
                            + "&patient_info[zhiwei]=" + _PatientInfo.getmZhiwei()
                            + "&patient_info[wh_chd]=" + _PatientInfo.getmWh_chd()
                            + "&patient_info[hy_zhk]=" + _PatientInfo.getmHy_zhk()
                            + "&patient_info[p_keshi]=" + _PatientInfo.getmP_keshi()
                            + "&patient_info[p_jfly]=" + _PatientInfo.getmP_jfly()
                            + "&patient_info[first_releate_person]=" + _PatientInfo.getmFirst_releate_person()
                            + "&patient_info[tel]=" + _PatientInfo.getmTel()
                            + "&patient_info[height]=" + _PatientInfo.getmHeight()
                            + "&patient_info[weight]=" + _PatientInfo.getmWeight()
                            + "&patient_info[p_bmi]=" + _PatientInfo.getmP_bmi()
                            + "&patient_info[p_sqks]=" + _PatientInfo.getmP_sqks()
                            + "&patient_info[p_zhenduan]=" + _PatientInfo.getmP_zhenduan()
                            + "&patient_info[lczd_icd]=" + _PatientInfo.getmLczd_icd()
                            + "&patient_info[p_bbbw]=" + _PatientInfo.getmP_bbbw()
                            + "&patient_info[bbbw_icd]=" + _PatientInfo.getmBbbw_icd()
                            + "&patient_info[p_startdate]=" + _PatientInfo.getmP_startdate()
                            + "&patient_info[p_bingcheng]=" + _PatientInfo.getmP_bingcheng()
                            + "&patient_info[p_gnza]=" + _PatientInfo.getmP_gnza()
                            + "&patient_info[p_fbjg]=" + _PatientInfo.getmP_fbjg()
                            + "&patient_info[p_zljg]=" + _PatientInfo.getmP_zljg()
                            + "&patient_info[p_kfjg]=" + _PatientInfo.getmP_kfjg()
                            + "&patient_info[p_xy]=" + _PatientInfo.getmP_xy()
                            + "&patient_info[shu_z_ya]=" + _PatientInfo.getmShu_z_ya()
                            + "&patient_info[p_xl]=" + _PatientInfo.getmP_xl()
                            + "&patient_info[p_bxl]=" + _PatientInfo.getmP_bxl()
                            + "&patient_info[p_szbxl]=" + _PatientInfo.getmP_szbxl()
                            + "&patient_info[p_mb]=" + _PatientInfo.getmP_mb()
                            + "&patient_info[p_xt]=" + _PatientInfo.getmP_xt()
                            + "&patient_info[zl_xm]=" + _PatientInfo.getmZl_xm()
                            + "&patient_info[zl_zt]=" + _PatientInfo.getmZl_zt()
                            + "&patient_info[sj_ap]=" + _PatientInfo.getmSj_ap()
                            + "&patient_info[zhliao_bw]=" + _PatientInfo.getmZhliao_bw()
                            + "&patient_info[executor]=" + _PatientInfo.getmExecutor()
                            + "&patient_info[lr_time]=" + _PatientInfo.getmLr_time()
                            + "&token=" + NetUrlAddress.token;

                    HttpUtils.doPostAsyn(URL_PATIRNT_UPDATE, params, new HttpUtils.CallBack() {
                        @Override
                        public void onRequestComplete(String result) {
                            Log.i("zsn", "result1=" + result);
                            if (result.equals("success")) {
                                //发广播
                                Intent intent = new Intent(PatientInfoFragment.ACTION_INTENT_PATIENTINFO_ADD);
                                sendBroadcast(intent);
                                finish();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            //添加
            // String uuid = getPatient_uuid().trim();
            //_PatientInfo.setmPatient_uuid(uuid);
            Log.d("33","_bah="+_bah);
            Log.d("33","mNameEdit.getText().toString()="+mNameEdit.getText().toString());
            Log.d("33","mLczd_icdEdit.getText().toString()="+mLczd_icdEdit.getText().toString());
            if(_bah.equals("")){
                showMsg(ToastMsg.BAH_NO_DATA);
            }else if(mNameEdit.getText().toString().equals("")){
                showMsg(ToastMsg.NAME_NO_DATA);
            }else if(mLczd_icdEdit.getText().toString().equals("")){
                showMsg(ToastMsg.LCZD_NO_DATA);
            }else{
                try {
                    String URL_PATIRNT_ADD = NetUrlAddress.ipAndPort + "/patient_infos" +
                            "/create/1.json?token=" + NetUrlAddress.token + "&&user_auth_id=" + String.valueOf(user_auth_id) + "&&menu_id=15";

                    String params = "patient_info[p_bah]=" + _PatientInfo.getmP_bah()
                            + "&patient_info[name]=" + _PatientInfo.getmName()
                            + "&patient_info[sex]=" + _PatientInfo.getmSex()
                            + "&patient_info[age]=" + _PatientInfo.getmAge()
                            + "&patient_info[birthday]=" + _PatientInfo.getmBirthday()
                            + "&patient_info[birth_locate]=" + _PatientInfo.getBirth_locate()
                            + "&patient_info[province_id]=" + _PatientInfo.getmProvince_id()
                            + "&patient_info[state_id]=" + _PatientInfo.getmState_id()
                            + "&patient_info[city_id]=" + _PatientInfo.getmCity_id()
                            + "&patient_info[nation]=" + _PatientInfo.getmNation()
                            + "&patient_info[zhiwei]=" + _PatientInfo.getmZhiwei()
                            + "&patient_info[wh_chd]=" + _PatientInfo.getmWh_chd()
                            + "&patient_info[hy_zhk]=" + _PatientInfo.getmHy_zhk()
                            + "&patient_info[p_keshi]=" + _PatientInfo.getmP_keshi()
                            + "&patient_info[p_jfly]=" + _PatientInfo.getmP_jfly()
                            + "&patient_info[first_releate_person]=" + _PatientInfo.getmFirst_releate_person()
                            + "&patient_info[tel]=" + _PatientInfo.getmTel()
                            + "&patient_info[height]=" + _PatientInfo.getmHeight()
                            + "&patient_info[weight]=" + _PatientInfo.getmWeight()
                            + "&patient_info[p_bmi]=" + _PatientInfo.getmP_bmi()
                            + "&patient_info[p_sqks]=" + _PatientInfo.getmP_sqks()
                            + "&patient_info[p_zhenduan]=" + _PatientInfo.getmP_zhenduan()
                            + "&patient_info[lczd_icd]=" + _PatientInfo.getmLczd_icd()
                            + "&patient_info[p_bbbw]=" + _PatientInfo.getmP_bbbw()
                            + "&patient_info[bbbw_icd]=" + _PatientInfo.getmBbbw_icd()
                            + "&patient_info[p_startdate]=" + _PatientInfo.getmP_startdate()
                            + "&patient_info[p_bingcheng]=" + _PatientInfo.getmP_bingcheng()
                            + "&patient_info[p_gnza]=" + _PatientInfo.getmP_gnza()
                            + "&patient_info[p_fbjg]=" + _PatientInfo.getmP_fbjg()
                            + "&patient_info[p_zljg]=" + _PatientInfo.getmP_zljg()
                            + "&patient_info[p_kfjg]=" + _PatientInfo.getmP_kfjg()
                            + "&patient_info[p_xy]=" + _PatientInfo.getmP_xy()
                            + "&patient_info[shu_z_ya]=" + _PatientInfo.getmShu_z_ya()
                            + "&patient_info[p_xl]=" + _PatientInfo.getmP_xl()
                            + "&patient_info[p_bxl]=" + _PatientInfo.getmP_bxl()
                            + "&patient_info[p_szbxl]=" + _PatientInfo.getmP_szbxl()
                            + "&patient_info[p_mb]=" + _PatientInfo.getmP_mb()
                            + "&patient_info[p_xt]=" + _PatientInfo.getmP_xt()
                            + "&patient_info[zl_xm]=" + _PatientInfo.getmZl_xm()
                            + "&patient_info[zl_zt]=" + _PatientInfo.getmZl_zt()
                            + "&patient_info[sj_ap]=" + _PatientInfo.getmSj_ap()
                            + "&patient_info[zhliao_bw]=" + _PatientInfo.getmZhliao_bw()
                            + "&patient_info[executor]=" + _PatientInfo.getmExecutor()
                            + "&patient_info[lr_time]=" + _PatientInfo.getmLr_time()
                            + "&token=" + NetUrlAddress.token;

                    HttpUtils.doPostAsyn(URL_PATIRNT_ADD, params, new HttpUtils.CallBack() {
                        @Override
                        public void onRequestComplete(String result) {
                            Log.i("ZSN", "result2=" + result);
                            if (result.equals("success")) {
                                //发广播
                                Intent intent = new Intent(PatientInfoFragment.ACTION_INTENT_PATIENTINFO_ADD);
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
                PatientInfoAddActivity.this);

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
    private void dateChoose1(final TextView text) {
        //时间选择器
        pwTime1 = new TimePopupWindow(this, Type.YEAR_MONTH_DAY);
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

    private void dateChoose2(final TextView text) {
        //时间选择器
        pwTime2 = new TimePopupWindow(this, Type.YEAR_MONTH_DAY);
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

    private void timeChoose(final TextView tv) {
        //时间选择器对象
        tpwTime = new TimePopupWindow(this, Type.ALL);
        //设置选择监听
        tpwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                tv.setText(getTime(date));
            }
        });
        //弹出时间对话栏
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tpwTime.showAtLocation(tv, Gravity.BOTTOM, 0, 0, new Date());
            }
        });
        //设置循环
        tpwTime.setCyclic(true);

    }

    /**
     * 获取uuid
     *
     * @return patient_uuid
     */
    private String getPatient_uuid() {
        return UUID.randomUUID().toString();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tittle_back:
                finish();
                break;
            case R.id.tittle_edit:
                //编辑
                flag = true;
                setEable(flag);
                //更改图标，编辑图标
                mEdit.setVisibility(View.GONE);
                mSave.setVisibility(View.VISIBLE);
                break;
            case R.id.tittle_save:
                //保存
                patientInfoSave();

                break;
            case R.id.patient_sex_edit:
                this.choose(mSexText, mSexItems);
                break;
            case R.id.patient_nation_edit:
                this.choose(mNationText, mNationItems);
                break;
            case R.id.patient_wh_chd_edit:
                this.choose(mWh_chdText, mWh_chdItems);
                break;
            case R.id.patient_hy_zhk_edit:
                this.choose(mHy_zhkText, mHy_zhkItems);
                break;
            case R.id.patient_p_keshi_edit:
                this.choose(mP_keshiText, mP_keshiItems);
                break;
            case R.id.patient_p_jfly_edit:
                this.choose(mP_jflyText, mP_jflyItems);
                break;
            case R.id.patient_zl_xm_edit:
                this.choose(mZl_xmText, mZl_xmItems);
                break;
            case R.id.patient_zl_zt_edit:
                this.choose(mZl_ztText, mZl_ztItems);
                break;
            case R.id.patient_sj_ap_edit:
                this.choose(mSj_apText, mSj_apItems);
                break;
            case R.id.patient_province_id_edit:
                isProvinceChecked = false;
                mPatient_scroll_view.scrollTo(0, 0);
                mPatient_scroll_view.setOnTouchListener(new patientOnTouchEvent());

                relativeLayout_patient_add_1.setVisibility(View.VISIBLE);
                relativeLayout_patient_add_2.setVisibility(View.VISIBLE);
                relativeLayout_patient_add_3.setVisibility(View.VISIBLE);

                break;
            case R.id.btnCancel:
                isProvinceChecked = true;
                relativeLayout_patient_add_1.setVisibility(View.GONE);
                relativeLayout_patient_add_2.setVisibility(View.GONE);
                relativeLayout_patient_add_3.setVisibility(View.GONE);
                break;
            case R.id.btnSubmit:
                isProvinceChecked = true;
                mProvince_idText.setText(mCurrentProviceName + mCurrentCityName + mCurrentDistrictName);
                relativeLayout_patient_add_1.setVisibility(View.GONE);
                relativeLayout_patient_add_2.setVisibility(View.GONE);
                relativeLayout_patient_add_3.setVisibility(View.GONE);
                break;

            case R.id.patient_lczd_icd_edit:
                if (SortListActivity.isICDLoaded) {
                    Intent _Intent_1 = new Intent(this, SortListActivity.class);
                    _Intent_1.putExtra("flag", 1);
                    startActivityForResult(_Intent_1, REQUEST_CODE_1);
                } else {
                    Toast.makeText(this, "第一次要先同步icd字典表数据", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.patient_bbbw_icd_edit:
                if (SortListActivity.isICFLoaded) {
                    Intent _Intent_2 = new Intent(this, SortListActivity.class);
                    _Intent_2.putExtra("flag", 2);
                    startActivityForResult(_Intent_2, REQUEST_CODE_2);
                } else {
                    Toast.makeText(this, "第一次要先同步icf字典表数据", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.icd_tongbu_image:
                Intent _Intent_3 = new Intent(this, SortListActivity.class);
                _Intent_3.putExtra("flag", 3);
                startActivityForResult(_Intent_3, REQUEST_CODE_3);
                break;

            case R.id.icf_tongbu_image:
                Intent _Intent_4 = new Intent(this, SortListActivity.class);
                _Intent_4.putExtra("flag", 4);
                startActivityForResult(_Intent_4, REQUEST_CODE_4);
                break;

            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("33", "requestCode=" + requestCode);
        switch (requestCode) {
            case REQUEST_CODE_1:
                if (resultCode == RESULT_OK) {
                    String result_1 = data.getExtras().getString("result");
                    mLczd_icdEdit.setText(result_1);
                }
                break;
            case REQUEST_CODE_2:
                if (resultCode == RESULT_OK) {
                    String result_2 = data.getExtras().getString("result");
                    mBbbw_icdEdit.setText(result_2);
                }
                break;
            case REQUEST_CODE_3:
                if (resultCode == RESULT_OK) {
                    String result_3 = data.getExtras().getString("result");
                    Log.d("33", "result_3==" + result_3);
                    mLczd_icdEdit.setText(result_3);
                }
                break;
            case REQUEST_CODE_4:
                if (resultCode == RESULT_OK) {
                    String result_4 = data.getExtras().getString("result");
                    Log.d("33", "result_4==" + result_4);
                    mBbbw_icdEdit.setText(result_4);
                }
                break;
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentDistrictId = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {

        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        mCurrentCityId = mCitisDatasIdsMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
        mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();

        mCurrentProviceName = mProvinceDatas[pCurrent];
        mCurrentProviceId = mProvinceDatasIds[pCurrent];

        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
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

    /**
     * 获取格式时间: yyyy-MM-dd HH:MM
     *
     * @param date
     * @return
     */
    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    /**
     * 将特定的毫秒数转为时间格式
     *
     * @param time
     * @return
     */
    private String formatSpecificTime(long time) {
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String retStrFormatNowDate = sdFormatter.format(time);
        return retStrFormatNowDate;
    }

    /**
     * 得到当前的时间字符串
     *
     * @return
     */
    private String refFormatNowDate() {
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String retStrFormatNowDate = sdFormatter.format(nowTime);
        return retStrFormatNowDate;
    }

    /**
     * 自1970年1月1日0时起的毫秒数
     *
     * @return
     */
    private long nowTime() {
        return System.currentTimeMillis();
    }

    /**
     * 将特定格式的时间转为豪秒数
     *
     * @param specificTime
     * @return
     */
    private long specificTimeFormat(String specificTime) {

        Date date = null;
        if (!specificTime.equals("null") && specificTime != null) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                date = simpleDateFormat.parse(specificTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date.getTime();
        }

        return -1;
    }

    /**
     * 联网，获取单个人的数据
     */
    private PatientInfo loadDataByNetworkType() {

        String URL_PATIENT_INFO_ADD = NetUrlAddress.ipAndPort + "/patient_infos/show/" + patientInfo.getId() + ".json?" +
                "&&user_auth_id=" + String.valueOf(user_auth_id) + "&&token=" + NetUrlAddress.token;

        String result = HttpUtils.doPost(URL_PATIENT_INFO_ADD, "");
        try {
            patientInfo = parse(new JSONObject(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return patientInfo;
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
