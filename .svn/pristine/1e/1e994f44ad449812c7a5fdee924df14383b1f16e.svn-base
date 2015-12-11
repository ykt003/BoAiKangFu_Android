package me.zhangls.rilintech.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.model.Model_one;

/**
 * Created by zsn on 15/11/11.
 */
public class TableParentIndexActivity extends BaseActivity {
    public FrameLayout fl_empty;
    //题目
    public TextView title;
    //评定日期
    public TextView date;
    //评定人
    public EditText maker;
//    //总分
//    public TextView total_scores;
   //评定说明
   public Spinner instructions;
    //listview
    public ListView listView;
    //添加 返回
    public ImageView add,back;
    //刷新
    public SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_bpq_index);
        //初始化控件
        initView();
    }

    private void initView() {
        fl_empty = (FrameLayout) findViewById(R.id.fl_empty);
        RelativeLayout layout_rl = (RelativeLayout) findViewById(R.id.layout);
        LinearLayout layout_ll=(LinearLayout)findViewById(R.id.activity_sub_title_3);
        title = (TextView) layout_rl.findViewById(R.id.title);
        add=(ImageView)layout_rl.findViewById(R.id.title_add);
        back=(ImageView)layout_rl.findViewById(R.id.title_back);

        date=(TextView)layout_ll.findViewById(R.id.checked_date_tv);
        instructions=(Spinner)findViewById(R.id.medical_history_relator_edit);
        maker=(EditText)findViewById(R.id.checked_person_tv);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        listView = (ListView) findViewById(R.id.listview);
    }

    public List<Model_one> initData(){
        return null;
    }

}
