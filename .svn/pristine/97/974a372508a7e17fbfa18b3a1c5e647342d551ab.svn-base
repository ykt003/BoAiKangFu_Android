package me.zhangls.rilintech.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.zhangls.swipemenulistview.SwipeMenu;
import com.zhangls.swipemenulistview.SwipeMenuListView;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.cache.ACache;
import me.zhangls.rilintech.callback.LoadFinishCallBack;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.constants.ToastMsg;
import me.zhangls.rilintech.model.DischargeSummarizeInfo;
import me.zhangls.rilintech.net.HttpUtil;
import me.zhangls.rilintech.utils.HttpUtils;
import me.zhangls.rilintech.utils.NetUtils;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;
import me.zhangls.rilintech.view.matchview.MatchTextView;

/**
 * Created by rilintech on 15/9/11.
 */
public class DischargeSummaryIndexActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageView mBack;
    private ImageView mAdd;
    private SwipeMenuListView mListView;
    private GoogleProgressBar google_progress;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LoadFinishCallBack mLoadFinisCallBack;
    private ACache mCache;
    public TableItemAdapter mAdapter;
    private int page;
    //是否添加了缓存
    private boolean isCachedData;
    //广播接收者对象
    private MybroadCastReceiver mybroadCastReceiver;
    //广播的标志字段
    public static final String ACTION_INTENT_DISCHARGESUMMARY_ADD = "me.zhangls.rilintech.activity.dischargeSummaryIndexActivity";
    private AlertDialog dialog;
    private MatchTextView no_content;
    private MatchTextView error;
    private ArrayList<DischargeSummarizeInfo> mDischargeSummarizeInfoList = new ArrayList<>();
    private ArrayList<DischargeSummarizeInfo> mDischargeSummarizeInfoListCache = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_table_discharge_summary);
        //广播过滤器对象
        IntentFilter myIntentFilter = new IntentFilter();
        mybroadCastReceiver = new MybroadCastReceiver();
        myIntentFilter.addAction(ACTION_INTENT_DISCHARGESUMMARY_ADD);
        //动态注册广播
        registerReceiver(mybroadCastReceiver, myIntentFilter);

        initView();

        initData();

        initListener();
    }

    private void initView() {
        no_content = (MatchTextView) findViewById(R.id.tv_no_thing);
        error = (MatchTextView) findViewById(R.id.tv_error);

        mBack = (ImageView) findViewById(R.id.discharge_summary_back);
        mAdd = (ImageView) findViewById(R.id.discharge_summary_add);
        //listview
        mListView = (SwipeMenuListView) findViewById(R.id.discharge_summary_list_item);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        google_progress = (GoogleProgressBar) findViewById(R.id.google_progress);
        mLoadFinisCallBack = mListView;

//        mListView.setLoadMoreListener(new SwipeMenuListView.onLoadMoreListener() {
//
//            @Override
//            public void loadMore() {
//                isCachedData = false;
//                mAdapter.loadNextPage();
//            }
//        });

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isCachedData = false;
                 mAdapter.loadFirst();

            }
        });
        //listView中向左滑动，初始化SwipeMenu菜单
        //  GetSwipeMenuCreator creator = new GetSwipeMenuCreator(this);
        // mListView.setMenuCreator(creator);
    }

    private void initData() {
        mCache = ACache.get(this);
        mAdapter = new TableItemAdapter();
        if (NetUtils.isConnected(this)) {
            isCachedData = false;
            mAdapter.loadFirst();
        } else {
            google_progress.setVisibility(View.GONE);
            JSONArray jsonArray = mCache.getAsJSONArray("JsonArray");
            mDischargeSummarizeInfoListCache = getCacheDatas(jsonArray);
            mDischargeSummarizeInfoList.clear();
            mDischargeSummarizeInfoList.addAll(mDischargeSummarizeInfoListCache);
            mListView.setAdapter(mAdapter);

        }
    }

    private ArrayList<DischargeSummarizeInfo> getCacheDatas(JSONArray jsonArr) {
        mDischargeSummarizeInfoListCache = DischargeSummarizeInfo.parseCache(jsonArr);
        return mDischargeSummarizeInfoListCache;
    }

    private void initListener() {
        mBack.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        mListView.setOnItemClickListener(this);
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                // PatientInfo patientInfo = mPatientInfoListAdpter.get(position);
               DischargeSummarizeInfo dischargeSummarizeInfo = mDischargeSummarizeInfoList.get(position);
                switch (index) {
                    case 0:
                        // open
                        open(dischargeSummarizeInfo);
                        break;
                    case 1:

                        if (NetUtils.isConnected(DischargeSummaryIndexActivity.this)) {
                            // delete
                            delete(dischargeSummarizeInfo);
                            mDischargeSummarizeInfoList.remove(position);
                            mAdapter.notifyDataSetChanged();
                            mListView.setAdapter(mAdapter);
                        } else {
                            mDischargeSummarizeInfoListCache.remove(position);
                            mDischargeSummarizeInfoList.clear();
                            mDischargeSummarizeInfoList.addAll(mDischargeSummarizeInfoListCache);
                            mAdapter.notifyDataSetChanged();
                            mListView.setAdapter(mAdapter);
                        }

                        break;
                }
            }
        });
        // set SwipeListener
        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start

                System.out.println("11111111");

            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end

                System.out.println("222222");
            }
        });

        // other setting
        // listView.setCloseInterpolator(new BounceInterpolator());
        // test item long click
        mListView.setOnItemLongClickListener(new SwipeMenuListView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.discharge_summary_back:
                this.finish();
                break;
            case R.id.discharge_summary_add:
                this.startActivity(new Intent(DischargeSummaryIndexActivity.this, DischargeSummaryAddActivity.class));
                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final DischargeSummarizeInfo summarizeInfo = mDischargeSummarizeInfoList.get(position);
        // open(waterInfo);
        AlertDialog.Builder builder = new AlertDialog.Builder(DischargeSummaryIndexActivity.this);
        View view1 = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        TextView title = (TextView) view1.findViewById(R.id.title);
        final TextView delete = (TextView) view1.findViewById(R.id.delete);
        TextView open = (TextView) view1.findViewById(R.id.add);
        TextView edit = (TextView) view1.findViewById(R.id.edit);
        TextView line = (TextView) view1.findViewById(R.id.line2);
        line.setVisibility(View.GONE);
        edit.setVisibility(View.GONE);
        title.setText("更多操作");
        open.setText("打开");
        dialog = builder.show();
        dialog.setCancelable(true);
        dialog.getWindow().setContentView(view1);

        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                delete(summarizeInfo);
                dialog.dismiss();
                mDischargeSummarizeInfoList.remove(position);
                mListView.setAdapter(mAdapter);
            }
        });

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(summarizeInfo);
                dialog.dismiss();
            }
        });
    }

    private void open(DischargeSummarizeInfo dischargeSummarizeInfo) {
        Intent intent = new Intent(DischargeSummaryIndexActivity.this, DischargeSummaryAddActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("discharge_summary_add", dischargeSummarizeInfo);
        intent.putExtras(bundle);
        DischargeSummaryIndexActivity.this.startActivity(intent);
    }

    /**
     * 删除某一个item
     *
     * @param _dischargeSummarizeInfo
     */
    private void delete(DischargeSummarizeInfo  _dischargeSummarizeInfo) {

        SharedPreferences preferences =DischargeSummaryIndexActivity.this.getSharedPreferences("user", MODE_PRIVATE);
        int use_id = preferences.getInt("use_id", -1);
        SharedPreferences sharedPreferences = getSharedPreferences("patientinfo", MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", -1);

        Log.i("zsn22", "发请求");
        String URL_DISCHARGE_SUMMARY_DELETE = NetUrlAddress.ipAndPort + "/discharge_summarizes/destroy/" + _dischargeSummarizeInfo.getId() + ".json?token="+NetUrlAddress.token+"&&patient_info_id=" + id + "&&user_auth_id=" + String.valueOf(use_id);

        try {
            HttpUtils.doPostAsyn(URL_DISCHARGE_SUMMARY_DELETE, null, new HttpUtils.CallBack() {
                @Override
                public void onRequestComplete(String result) {
                    Log.i("zsn", "result=" + result);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 广播接收者，实现相应的事件的处理
     */
    private int i = 0;

    private class MybroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_INTENT_DISCHARGESUMMARY_ADD)) {
                i++;
                Log.i("log", "执行的次数" + i);
                mDischargeSummarizeInfoList.clear();
                google_progress.setVisibility(View.VISIBLE);
                initData();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播
        unregisterReceiver(mybroadCastReceiver);
    }

    public class TableItemAdapter extends BaseAdapter {

        private DischargeSummarizeInfo mDischargeSummarizeInfo;

        @Override
        public int getCount() {
            return mDischargeSummarizeInfoList.size();
        }

        @Override
        public Object getItem(int i) {
            return mDischargeSummarizeInfoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View _view, ViewGroup viewGroup) {
            Holder _holder = null;
            if (_view == null) {
                _view = LayoutInflater.from(DischargeSummaryIndexActivity.this).inflate(
                        R.layout.item_common_use_info_list, null);
                _holder = new Holder();
                _holder.tv1 = (TextView) _view.findViewById(R.id.tv1);
                _holder.tv2 = (TextView) _view.findViewById(R.id.tv2);
                _holder.tv3 = (TextView) _view.findViewById(R.id.tv3);
                _view.setTag(_holder);
            } else {
                _holder = (Holder) _view.getTag();
            }


            mDischargeSummarizeInfo = (DischargeSummarizeInfo) mDischargeSummarizeInfoList.get(position);
            if (mDischargeSummarizeInfo.getCishu()==-1) {
                _holder.tv1.setText("");
            } else {
                _holder.tv1.setText(mDischargeSummarizeInfo.getCishu()+"");
            }
            if (mDischargeSummarizeInfo.getStart_time().equals("null")) {
                _holder.tv2.setText("");
            } else {
                _holder.tv2.setText(mDischargeSummarizeInfo.getStart_time());
            }

            if (mDischargeSummarizeInfo.getEnd_time().equals("null")) {
                _holder.tv3.setText("");
            } else {
                _holder.tv3.setText(mDischargeSummarizeInfo.getEnd_time());
            }
            return _view;

        }

        private void loadFirst() {
            page = 1;
            loadDataByNetworkType();
        }

        public void loadNextPage() {
            page++;
            loadDataByNetworkType();
        }

        /**
         * 获取网络数据
         */
        private void loadDataByNetworkType() {

            SharedPreferences sharedPreferences = getSharedPreferences("patientinfo", MODE_PRIVATE);
            int id = sharedPreferences.getInt("id", -1);
            SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
            int use_id = preferences.getInt("use_id", -1);

            Log.i("response", "id=" + id);
            Log.i("response", "page=" + page);
            Log.i("response", "use_id=" + use_id);

            String URL_DISCHARGE_SUMMARY_INFOS = NetUrlAddress.ipAndPort + "/discharge_summarizes/index/1.json?" +
                    "user_auth_id=" + String.valueOf(use_id) + "&&token="+NetUrlAddress.token+"&&patient_info_id=" + String.valueOf(id);
            if (NetUtils.isConnected(DischargeSummaryIndexActivity.this)) {
                if (!isCachedData) {
                    HttpUtil.get(URL_DISCHARGE_SUMMARY_INFOS, null, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            google_progress.setVisibility(View.GONE);
                            mLoadFinisCallBack.loadFinish(null);
                            if (mSwipeRefreshLayout.isRefreshing()) {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }

                            if (page == 1) {
                                mDischargeSummarizeInfoList.clear();
                                //清空缓存
                                mCache.remove("JsonArray");
                            }

                            ArrayList<DischargeSummarizeInfo> parseInfoList = DischargeSummarizeInfo.parse(response);
                            mDischargeSummarizeInfoList.addAll(parseInfoList);
                            //去重
                            mDischargeSummarizeInfoList = (ArrayList<DischargeSummarizeInfo>) removeDuplicate(mDischargeSummarizeInfoList);
                            if(page==1){
                                if (mDischargeSummarizeInfoList.size() > 0) {
                                    no_content.setVisibility(View.GONE);
                                    //给listview设置adapter
                                    mListView.setAdapter(mAdapter);
                                } else {
                                    no_content.setVisibility(View.VISIBLE);
                                    mListView.setAdapter(mAdapter);
                                }
                            }else{
                                if (mDischargeSummarizeInfoList.size() > 0) {
                                    no_content.setVisibility(View.GONE);
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    no_content.setVisibility(View.VISIBLE);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }


//                            if (mTreadmillTraininginWaterInfoList.size() > 0) {
//                                //默认第一项的id
//                                SharedPreferences sharedPreferences = TableTreadmillTrainingInWaterIndexActivity.this.getSharedPreferences("patientinfo", getActivity().MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                editor.putInt("id", mTreadmillTraininginWaterInfoList.get(0).getId());
//                                editor.commit();
//                            }
                            //添加入缓存
                            mCache.put("JsonArray", response);
                            isCachedData = true;
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.i("zsn", throwable.getMessage(), throwable);
                            ShowToast.Short(ToastMsg.LOAD_FAILED);
                            google_progress.setVisibility(View.GONE);
                            mLoadFinisCallBack.loadFinish(null);
                            if (mSwipeRefreshLayout.isRefreshing()) {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    });
                } else {
                    google_progress.setVisibility(View.GONE);
                    //取出缓存
                    JSONArray jsonArray = mCache.getAsJSONArray("JsonArray");
                    mDischargeSummarizeInfoListCache = getCacheDatas(jsonArray);
                    mDischargeSummarizeInfoList.clear();
                    mDischargeSummarizeInfoList.addAll(mDischargeSummarizeInfoListCache);
                    notifyDataSetChanged();

                }
            } else {
                google_progress.setVisibility(View.GONE);
                mLoadFinisCallBack.loadFinish(null);
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                if (page == 1) {
                    mDischargeSummarizeInfoList.clear();
                    ShowToast.Short(ToastMsg.LOAD_NO_NETWORK);
                }
                //取出缓存
                JSONArray jsonArray = mCache.getAsJSONArray("JsonArray");
                if (jsonArray != null) {
                    mDischargeSummarizeInfoListCache = getCacheDatas(jsonArray);
                    mDischargeSummarizeInfoList.clear();
                    mDischargeSummarizeInfoList.addAll(mDischargeSummarizeInfoListCache);
                    notifyDataSetChanged();
                }
            }
        }

        private class Holder {
            TextView tv1, tv2, tv3;
        }
    }

    // 去重
    public List removeDuplicate(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }
}
