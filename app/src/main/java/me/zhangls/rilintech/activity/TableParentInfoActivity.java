package me.zhangls.rilintech.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.pickertimelib.TimePopupWindow;

/**
 * Created by zsn on 15/11/11.
 */
public class TableParentInfoActivity extends BaseActivity implements View.OnClickListener{
    /**
     * 返回键
     */
    public ImageView key_back;
    /**
     * 保存键
     */
    public ImageView key_save;
    /**
     * 编辑键
     */
    public ImageView key_edit;
    /**
     * 评定日期
     */
    public TextView date_tv;
    /**
     * 时间选择器
     */
    public TimePopupWindow pwTime;
    /**
     * 评定说明
     */
    public Spinner instructions;
    /**
     * 评定说明的选项
     */
    public String[] arr_instructions = new String[]{"初评", "中评Ⅰ", "中评Ⅱ", "中评Ⅲ", "中评Ⅳ", "中评Ⅴ", "末评"};
    /**
     * 最终的spinner数据
     */
    public List<String> list_s = new ArrayList<>();
    /**
     * 评定人
     */
    public EditText person;
    /**
     * listview
     */
    public ListView list_view;
    /**
     * spinner数据
     */
    public String spinner_value;
    /**
     * 标题名称
     */
    public TextView title_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_bpq_info);
        //各种初始化
        initView();
    }

    public void initData() {

    }

    private void initView() {
        RelativeLayout activity_footer_rl = (RelativeLayout) findViewById(R.id.activity_footer);
        key_back = (ImageView) activity_footer_rl.findViewById(R.id.tittle_back);
        key_save = (ImageView) activity_footer_rl.findViewById(R.id.patient_save);
        key_edit = (ImageView) activity_footer_rl.findViewById(R.id.patient_edit);
        title_name=(TextView)activity_footer_rl.findViewById(R.id.title_name);
        LinearLayout activity_sub_title_ll = (LinearLayout) findViewById(R.id.activity_sub_title_3);
        date_tv = (TextView)activity_sub_title_ll.findViewById(R.id.checked_date_tv);
        //时间选择器
        pwTime = new TimePopupWindow(this, TimePopupWindow.Type.YEAR_MONTH_DAY);
        //评定说明
        instructions = (Spinner) activity_sub_title_ll.findViewById(R.id.medical_history_relator_edit);
        person=(EditText)activity_sub_title_ll.findViewById(R.id.checked_person_tv);
        list_view=(ListView)findViewById(R.id.listview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tittle_back://返回
                this.finish();
                break;

            case R.id.checked_date_tv://评定日期
                pwTime.showAtLocation(date_tv, Gravity.BOTTOM, 0, 0, new Date());
                break;
        }

    }
}
