package me.zhangls.rilintech.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import me.zhangls.rilintech.R;

/**
 * Created by YANG on 15/11/23.
 */
public class TableNormalParentIndexActivity extends BaseActivity {

    //返回键
    public ImageView mBack;
    //添加键
    public ImageView mAdd;
    //标题
    public TextView mTitle;
    //二级标题
    public TextView mSecondTitle1;
    public TextView mSecondTitle2;
    public TextView mSecondTitle3;
    //主体展示
    public ListView mListView;
    //刷新
    public SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_normal_index);

        initView();
    }


    private void initView(){

        mBack = (ImageView) findViewById(R.id.layout).findViewById(R.id.title_back);
        mAdd = (ImageView) findViewById(R.id.layout).findViewById(R.id.title_add);
        mTitle = (TextView) findViewById(R.id.layout).findViewById(R.id.title);
        mSecondTitle1 = (TextView) findViewById(R.id.include_layout).findViewById(R.id.title1);
        mSecondTitle2 = (TextView) findViewById(R.id.include_layout).findViewById(R.id.title2);
        mSecondTitle3 = (TextView) findViewById(R.id.include_layout).findViewById(R.id.title3);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mListView = (ListView) findViewById(R.id.lv);
    }
}
