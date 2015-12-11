package me.zhangls.rilintech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.adapter.TableIndexAdapter;
import me.zhangls.rilintech.manager.MedicalHistoryRecordManager;
import me.zhangls.rilintech.model.Get_URL;
import me.zhangls.rilintech.model.MedicalHistoryRecord;
import me.zhangls.rilintech.model.Model_one;
import me.zhangls.rilintech.utils.L;

/**
 * Created by rilintech on 15/8/4.
 */
public class TableIndexActivity extends BaseActivity implements AbsListView.OnScrollListener {
    /**
     * 返回按钮
     */
    private ImageView mBack;

    /**
     * 添加按钮
     */
    private ImageView mAdd;
    /**
     * 是否点击了添加按钮
     */
    public static boolean isAdd = true;
    /**
     * ListView
     */
    private ListView mListView;
    /**
     * 病史记录集合
     */
    private List<MedicalHistoryRecord> list_mhr = null;
    /**
     * 放入适配器中的数据
     */
    private List<MedicalHistoryRecord> mMHRListAdpter = new ArrayList<>();
    /**
     * 病史记录管理
     */
    private MedicalHistoryRecordManager manager = null;
    private MedicalHistoryRecord mhr = null;
    /**
     * ListView底部View
     */
    private View mMoreView;
    /**
     * 进度条
     */
    private ProgressBar mProgressBar;
    /**
     * 设置一个最大的数据条数，超过即不再加载
     */
    private int mMaxDateNum;
    /**
     * 最后可见条目的索引
     */
    private int lastVisibleIndex;
    private TableIndexAdapter mAdapter;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_index);

        getAllMedicalHistoryRecords();
        initView();

    }

    /**
     * 获取所有的病史记录，赋值给list_mhr
     *
     * @return list_mhr
     */
    private List<MedicalHistoryRecord> getAllMedicalHistoryRecords() {
        manager = new MedicalHistoryRecordManager(this);
        manager.openDataBase();
        list_mhr = manager.queryAllMedicalHistoryRecords();
        manager.closeDataBase();
        return list_mhr;
    }

    private void initView() {

        // 实例化底部布局
        mMoreView = getLayoutInflater().inflate(R.layout.activity_progress, null);
        //增加的布局的progressBar
        mProgressBar = (ProgressBar) mMoreView.findViewById(R.id.pg);

        mBack = (ImageView) findViewById(R.id.medical_history_record_back);
        mAdd = (ImageView) findViewById(R.id.patient_add);
        mListView = (ListView) findViewById(R.id.tables_infos_list_item);
        // 绑定监听器
        mListView.setOnScrollListener(this);
        mMaxDateNum = list_mhr.size();
        mHandler = new Handler();
        mAdapter = new TableIndexAdapter(TableIndexActivity.this, mMHRListAdpter);
        // 加上底部View，注意要放在setAdapter方法前
        mListView.addFooterView(mMoreView);
        //先给mPatientInfoListAdpater方数据
        loadMoreDate();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isAdd = false;
                manager = new MedicalHistoryRecordManager(TableIndexActivity.this);
                manager.openDataBase();
                mhr = list_mhr.get(i);
                String mhr_uuid = mhr.getMhr_uuid();
                Intent intent = new Intent(TableIndexActivity.this, MedicalHistoryRecordActivity.class);
                intent.putExtra("isAdd", isAdd);
                intent.putExtra("mhr_uuid", mhr_uuid);
                startActivity(intent);
                //TableIndexActivity.this.finish();

            }
        });
    }

    private void loadMoreDate() {
        int count = mAdapter.getCount();
        if (count + 10 < mMaxDateNum) {
            // 每次加载5条
            for (int i = count; i < count + 10; i++) {
                mMHRListAdpter.add(list_mhr.get(i));
            }
        } else {
            // 数据已经不足5条
            for (int i = count; i < mMaxDateNum; i++) {
                mMHRListAdpter.add(list_mhr.get(i));
            }
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        // 滑到底部后自动加载，判断listview已经停止滚动并且最后可视的条目等于adapter的条目
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                && lastVisibleIndex == mAdapter.getCount()) {
            //当滑到底部时自动加载
            mProgressBar.setVisibility(View.VISIBLE);

            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    loadMoreDate();
                    mProgressBar.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();
                }

            }, 1000);

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // 计算最后可见条目的索引
        lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;

        // 所有的条目已经和最大条数相等，则移除底部的View
        if (totalItemCount == mMaxDateNum + 1) {
            mListView.removeFooterView(mMoreView);
            Toast.makeText(getApplicationContext(), "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 返回按钮
     *
     * @param view
     */
    public void back(View view) {
        this.finish();
    }

    /**
     * 添加按钮
     *
     * @param view
     */
    public void add(View view) {
        isAdd = true;
        Intent intent = new Intent(this, MedicalHistoryRecordActivity.class);
        intent.putExtra("isAdd", isAdd);
        startActivity(intent);
        //this.finish();
    }


}
