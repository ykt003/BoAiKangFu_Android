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
import me.zhangls.rilintech.model.TreadmillTraininginWaterInfo;
import me.zhangls.rilintech.net.HttpUtil;
import me.zhangls.rilintech.utils.HttpUtils;
import me.zhangls.rilintech.utils.NetUtils;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;
import me.zhangls.rilintech.view.matchview.MatchTextView;

/**
 * Created by zsn on 15/9/10.
 */
public class TableTreadmillTrainingInWaterIndexActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
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
    private ArrayList<TreadmillTraininginWaterInfo> mTreadmillTraininginWaterInfoList = new ArrayList<>();
    private ArrayList<TreadmillTraininginWaterInfo> mTreadmillTraininginWaterInfoListCache = new ArrayList<>();
    private MatchTextView no_content;
    private MatchTextView error;
    //广播接收者对象
    private MybroadCastReceiver mybroadCastReceiver;
    //广播的标志字段
    public static final String ACTION_INTENT_TREADMILL_TRAINING_IN_WATER_ADD = "me.zhangls.rilintech.activity.tableTreadmillTrainingInWaterIndexActivity";
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_table_treadmill_training_in_water);


        //广播过滤器对象
        IntentFilter myIntentFilter = new IntentFilter();
        mybroadCastReceiver = new MybroadCastReceiver();
        myIntentFilter.addAction(ACTION_INTENT_TREADMILL_TRAINING_IN_WATER_ADD);
        //动态注册广播
        registerReceiver(mybroadCastReceiver, myIntentFilter);

        initView();

        initData();

        initListener();
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
            mTreadmillTraininginWaterInfoListCache = getCacheDatas(jsonArray);
            mTreadmillTraininginWaterInfoList.clear();
            mTreadmillTraininginWaterInfoList.addAll(mTreadmillTraininginWaterInfoListCache);
            mListView.setAdapter(mAdapter);
        }
    }

    private ArrayList<TreadmillTraininginWaterInfo> getCacheDatas(JSONArray jsonArr) {
        mTreadmillTraininginWaterInfoListCache = TreadmillTraininginWaterInfo.parseCache(jsonArr);
        return mTreadmillTraininginWaterInfoListCache;
    }

    private void initListener() {
        mBack.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        mListView.setOnItemClickListener(this);
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                // PatientInfo patientInfo = mPatientInfoListAdpter.get(position);
                TreadmillTraininginWaterInfo treadmillTraininginWaterInfo = mTreadmillTraininginWaterInfoList.get(position);
                switch (index) {
                    case 0:
                        // open
                        open(treadmillTraininginWaterInfo);
                        break;
                    case 1:

                        if (NetUtils.isConnected(TableTreadmillTrainingInWaterIndexActivity.this)) {
                            // delete
                            delete(treadmillTraininginWaterInfo);
                            mTreadmillTraininginWaterInfoList.remove(position);
                            mAdapter.notifyDataSetChanged();
                            mListView.setAdapter(mAdapter);
                        } else {
                            mTreadmillTraininginWaterInfoListCache.remove(position);
                            mTreadmillTraininginWaterInfoList.clear();
                            mTreadmillTraininginWaterInfoList.addAll(mTreadmillTraininginWaterInfoListCache);
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

    private void initView() {
        no_content = (MatchTextView) findViewById(R.id.tv_no_thing);
        error = (MatchTextView) findViewById(R.id.tv_error);

        mBack = (ImageView) findViewById(R.id.training_in_water_back);
        mAdd = (ImageView) findViewById(R.id.training_in_water_add);
        //listview
        mListView = (SwipeMenuListView) findViewById(R.id.training_in_water_list_item);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.training_in_water_back:
                this.finish();
                break;
            case R.id.training_in_water_add:
                this.startActivity(new Intent(TableTreadmillTrainingInWaterIndexActivity.this, TableTreadmillTrainingInWaterAddActivity.class));
                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final TreadmillTraininginWaterInfo waterInfo = mTreadmillTraininginWaterInfoList.get(position);
       // open(waterInfo);
        AlertDialog.Builder builder = new AlertDialog.Builder(TableTreadmillTrainingInWaterIndexActivity.this);
        View view1 = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        TextView title = (TextView) view1.findViewById(R.id.title);
        final TextView delete = (TextView) view1.findViewById(R.id.delete);
        TextView open = (TextView) view1.findViewById(R.id.add);
        TextView edit = (TextView) view1.findViewById(R.id.edit);
        edit.setVisibility(View.GONE);
        title.setText("更多操作");
        open.setText("打开");
        dialog = builder.show();
        dialog.setCancelable(true);
        dialog.getWindow().setContentView(view1);

        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                delete(waterInfo);
                dialog.dismiss();
                mTreadmillTraininginWaterInfoList.remove(position);
                mListView.setAdapter(mAdapter);
            }
        });

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(waterInfo);
                dialog.dismiss();
            }
        });
    }

    private void open(TreadmillTraininginWaterInfo treadmillTraininginWaterInfo) {
        Intent intent = new Intent(TableTreadmillTrainingInWaterIndexActivity.this, TableTreadmillTrainingInWaterAddActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("training_in_water_add", treadmillTraininginWaterInfo);
        intent.putExtras(bundle);
        TableTreadmillTrainingInWaterIndexActivity.this.startActivity(intent);
    }

    /**
     * 删除某一个item
     *
     * @param _treadmillTraininginWaterInfo
     */
    private void delete(TreadmillTraininginWaterInfo _treadmillTraininginWaterInfo) {

        SharedPreferences preferences = TableTreadmillTrainingInWaterIndexActivity.this.getSharedPreferences("user", MODE_PRIVATE);
        int use_id = preferences.getInt("use_id", -1);
        SharedPreferences sharedPreferences = getSharedPreferences("patientinfo", MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", -1);

        Log.i("zsn22", "发请求");
        Log.i("zsn22","_treadmillTraininginWaterInfo.getId()="+_treadmillTraininginWaterInfo.getId());
        String URL_PATIRNT_DELETE = NetUrlAddress.ipAndPort + "/under_water_threadmills/destroy/"+ _treadmillTraininginWaterInfo.getId() + ".json?token="+NetUrlAddress.token+"&&patient_info_id="+id+"&&user_auth_id="+ String.valueOf(use_id);

        try {
            HttpUtils.doPostAsyn(URL_PATIRNT_DELETE, null, new HttpUtils.CallBack() {
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
            if (action.equals(ACTION_INTENT_TREADMILL_TRAINING_IN_WATER_ADD)) {
                i++;
                Log.i("log", "执行的次数" + i);
                mTreadmillTraininginWaterInfoList.clear();
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

        private TreadmillTraininginWaterInfo mTreadmillTraininginWaterInfo;

        @Override
        public int getCount() {
            return mTreadmillTraininginWaterInfoList.size();
        }

        @Override
        public Object getItem(int i) {
            return mTreadmillTraininginWaterInfoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View _view, ViewGroup viewGroup) {
            Holder _holder = null;
            if (_view == null) {
                _view = LayoutInflater.from(TableTreadmillTrainingInWaterIndexActivity.this).inflate(
                        R.layout.item_common_use_info_list, null);
                _holder = new Holder();
                _holder.tv1 = (TextView) _view.findViewById(R.id.tv1);
                _holder.tv2 = (TextView) _view.findViewById(R.id.tv2);
                _holder.tv3 = (TextView) _view.findViewById(R.id.tv3);
                _view.setTag(_holder);
            } else {
                _holder = (Holder) _view.getTag();
            }


            mTreadmillTraininginWaterInfo = (TreadmillTraininginWaterInfo) mTreadmillTraininginWaterInfoList.get(position);
            if (mTreadmillTraininginWaterInfo.getDate_time() .equals("null") ) {
                _holder.tv1.setText("");
            } else {
                _holder.tv1.setText(mTreadmillTraininginWaterInfo.getDate_time());
            }
            if(mTreadmillTraininginWaterInfo.getTime_of_therapy()==-1){
                _holder.tv2.setText("");
            }else{
                _holder.tv2.setText(mTreadmillTraininginWaterInfo.getTime_of_therapy()+"");
            }

            if(mTreadmillTraininginWaterInfo.getSpeed()==-1.0){
                _holder.tv3.setText("");
            }else{
                _holder.tv3.setText(mTreadmillTraininginWaterInfo.getSpeed() + "");
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

            String URL_TRAINING_IN_WATER_INFOS = NetUrlAddress.ipAndPort + "/under_water_threadmills/index/1.json?" +
                    "user_auth_id=" + String.valueOf(use_id) + "&&token="+NetUrlAddress.token+"&&patient_info_id=" + String.valueOf(id);
            if (NetUtils.isConnected(TableTreadmillTrainingInWaterIndexActivity.this)) {
                if (!isCachedData) {
                    HttpUtil.get(URL_TRAINING_IN_WATER_INFOS, null, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            google_progress.setVisibility(View.GONE);
                            mLoadFinisCallBack.loadFinish(null);
                            if (mSwipeRefreshLayout.isRefreshing()) {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }

                            if (page == 1) {
                                mTreadmillTraininginWaterInfoList.clear();
                                //清空缓存
                                mCache.remove("JsonArray");
                            }

                            ArrayList<TreadmillTraininginWaterInfo> parseInfoList = TreadmillTraininginWaterInfo.parse(response);
                            mTreadmillTraininginWaterInfoList.addAll(parseInfoList);
                            //去重
                            mTreadmillTraininginWaterInfoList= (ArrayList<TreadmillTraininginWaterInfo>) removeDuplicate(mTreadmillTraininginWaterInfoList);

                            if(page==1){
                                if (mTreadmillTraininginWaterInfoList.size() > 0) {
                                    no_content.setVisibility(View.GONE);
                                    //给listview设置adapter
                                    mListView.setAdapter(mAdapter);
                                } else {
                                    no_content.setVisibility(View.VISIBLE);
                                    mListView.setAdapter(mAdapter);
                                }
                            }else{
                                if (mTreadmillTraininginWaterInfoList.size() > 0) {
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
                    mTreadmillTraininginWaterInfoListCache = getCacheDatas(jsonArray);
                    mTreadmillTraininginWaterInfoList.clear();
                    mTreadmillTraininginWaterInfoList.addAll(mTreadmillTraininginWaterInfoListCache);
                    notifyDataSetChanged();

                }
            } else {
                google_progress.setVisibility(View.GONE);
                mLoadFinisCallBack.loadFinish(null);
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                if (page == 1) {
                    mTreadmillTraininginWaterInfoList.clear();
                    ShowToast.Short(ToastMsg.LOAD_NO_NETWORK);
                }
                //取出缓存
                JSONArray jsonArray = mCache.getAsJSONArray("JsonArray");
                if (jsonArray != null) {
                    mTreadmillTraininginWaterInfoListCache = getCacheDatas(jsonArray);
                    mTreadmillTraininginWaterInfoList.clear();
                    mTreadmillTraininginWaterInfoList.addAll(mTreadmillTraininginWaterInfoListCache);
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
