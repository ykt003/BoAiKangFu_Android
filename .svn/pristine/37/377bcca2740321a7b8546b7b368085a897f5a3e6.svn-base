package me.zhangls.rilintech.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import me.zhangls.rilintech.R;

/**
 * Created by YANG on 15/11/23.
 */
public class TableNormalParentInfoActivity extends BaseActivity {


    //返回键
    public ImageView mBack;
    //添加键
    public ImageView mSave;
    //标题
    public TextView mTitle;
    //主体展示
    public ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table_normal_info);

        initView();
    }
    
    private void initView(){

        mBack = (ImageView) findViewById(R.id.activity_footer).findViewById(R.id.tittle_back);
        mSave = (ImageView) findViewById(R.id.activity_footer).findViewById(R.id.patient_save);
        mTitle = (TextView) findViewById(R.id.activity_footer).findViewById(R.id.title_name);

        mListView = (ListView) findViewById(R.id.listview);

    }
}
