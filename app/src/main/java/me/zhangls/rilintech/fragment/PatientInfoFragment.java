package me.zhangls.rilintech.fragment;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.zhangls.swipemenulistview.SwipeMenuListView;

import org.apache.http.Header;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.activity.PatientInfoAddActivity;
import me.zhangls.rilintech.activity.TableFlowChart;
import me.zhangls.rilintech.cache.ACache;
import me.zhangls.rilintech.callback.LoadFinishCallBack;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.constants.ToastMsg;
import me.zhangls.rilintech.manager.PatientInfoManager;
import me.zhangls.rilintech.model.PatientInfo;
import me.zhangls.rilintech.net.HttpUtil;
import me.zhangls.rilintech.utils.HttpUtils;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.NetUtils;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.view.googleprogressbar.GoogleProgressBar;
import me.zhangls.rilintech.view.matchview.MatchTextView;

/**
 * Created by admin on 13-11-23.
 */
public class PatientInfoFragment extends BaseFragment implements View.OnClickListener{
    //fragment内的view
    private View mView;
    //左边滑动出现的删除和打开的view
    // private ScrollListviewDelete mListView;
    private SwipeMenuListView mListView;
    //private ZListView mListView;
    //从网络拿到的所有的数据源
    private List<PatientInfo> mPatientInfoList = new ArrayList<PatientInfo>();
    //缓存所有的数据源
    private List<PatientInfo> mPatientInfoListCache = new ArrayList<PatientInfo>();

    public TableItemAdapter mAdapter;
    //进度条
    private ProgressBar mProgressBar;
    // 设置一个最大的数据条数，超过即不再加载
    private int mMaxDateNum;
    // 最后可见条目的索引
    private int lastVisibleIndex;
    // ListView底部View
    private View mMoreView;
    //添加患者按钮
    private ImageView mImageView;
    //标题栏左边的头像
    private ImageView mLeftMenu;
    //侧滑菜单menu
    //private ResideMenu mResideMenu;
    //数据库管理对象
    private PatientInfoManager patientInfoManager;
    //广播接收者对象
    private MybroadCastReceiver mybroadCastReceiver;
    //广播的标志字段
    public static final String ACTION_INTENT_PATIENTINFO_ADD = "me.zhangls.rilintech.fragment.PatientInfoFragment";
    // private AutoLoadRecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private GoogleProgressBar google_progress;
    private LoadFinishCallBack mLoadFinisCallBack;
    private int page;
    //private PatientInfoAdapter mAdapter;
    //缓存
    private ACache mCache;
    //是否添加了缓存
    private boolean isCachedData;

    private MatchTextView no_content;
    private MatchTextView error;
    private AlertDialog dialog;
    //当前登录的用户ID
    private int user_auth_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_patient_info, null);

        //广播过滤器对象
        IntentFilter myIntentFilter = new IntentFilter();
        mybroadCastReceiver = new MybroadCastReceiver();
        myIntentFilter.addAction(ACTION_INTENT_PATIENTINFO_ADD);
        //动态注册广播
        getActivity().registerReceiver(mybroadCastReceiver, myIntentFilter);
        getSharedPreferencesData();
        initView();
        initData();
        initListener();
        return mView;
    }

   /* @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new PatientInfoAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.loadFirst();
    }*/
    /**
     * 获取当前登录用户和当前患者的ID
     */
    private void getSharedPreferencesData() {

        SharedPreferences preferences = getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
        user_auth_id = preferences.getInt("use_id", 2);
    }

    @Override
    public String getContent() {
        return null;
    }


    /**
     * 初始化控件
     */
    private void initView() {

        no_content = (MatchTextView) mView.findViewById(R.id.tv_no_thing);
        error = (MatchTextView) mView.findViewById(R.id.tv_error);
        //添加按钮对象
        mImageView = (ImageView) mView.findViewById(R.id.patient_add);
        //获取mainAty的侧滑菜单对象
        // mResideMenu= MainActivity.getResideMenu();
        //标题栏左边的头像
        mLeftMenu = (ImageView) mView.findViewById(R.id.title_bar_left_menu);
        //listview
        //mListView = (ScrollListviewDelete) mView.findViewById(R.id.patient_infos_list_item);
        mListView = (SwipeMenuListView) mView.findViewById(R.id.patient_infos_list_item);
        //mListView = (ZListView) mView.findViewById(R.id.patient_infos_list_item);
        // mRecyclerView=(AutoLoadRecyclerView)mView.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipeRefreshLayout);
        google_progress = (GoogleProgressBar) mView.findViewById(R.id.google_progress);

        // mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //mLoadFinisCallBack=mRecyclerView;
       mLoadFinisCallBack = mListView;

        mListView.setLoadMoreListener(new SwipeMenuListView.onLoadMoreListener() {

            @Override
            public void loadMore() {

                if(mSwipeRefreshLayout.isRefreshing()){
                    return;
                } else{
                    isCachedData = false;
                    mAdapter.loadNextPage();
                }
            }
        });

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

        // mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //listView中向左滑动，初始化SwipeMenu菜单
//         GetSwipeMenuCreator creator = new GetSwipeMenuCreator(getActivity());
//          mListView.setMenuCreator(creator);


        //mListView.setPullLoadEnable(true);
        // 实例化底部布局
        // mMoreView = getActivity().getLayoutInflater().inflate(R.layout.activity_progress, null);
        //增加的布局的progressBar
        //  mProgressBar = (ProgressBar) mMoreView.findViewById(R.id.pg);

    }

//  private void geneDatas() {
//        //获取数据源
//        patientInfoManager = new PatientInfoManager(getActivity());
//        patientInfoManager.openDataBase();
//       // mPatientInfoListJDBC = patientInfoManager.patientInfos();
//        patientInfoManager.closeDataBase();
//    }

    private List<PatientInfo> getCacheDatas(JSONArray jsonArr) {
        mPatientInfoListCache = PatientInfo.parseCache(jsonArr);
        return mPatientInfoListCache;
    }

    /**
     * 加载view和数据
     */
    private void initData() {

//        value = Get_URL.getData("http://192.168.0.115:2222/app_pat_profile/profile_index/1.json?token=9ba2a51a1458421c245bace706ec159faf88247327aa2f607a1c8dcdb7f33673119bf62e32bd29723df40542944a5c2de8e50c089e61cee3f242fe4cab343b80&patient_info_id=152%20&user_auth_id=2","");
//        L.d("uu", "value====" + value);
       /* //数据源集合的最大值
        mMaxDateNum = mPatientInfoList.size();
        mHandler = new Handler();
        // 加上底部View，注意要放在setAdapter方法前
        mListView.addFooterView(mMoreView);*/

        //初始化adapter
      /*  if (mPatientInfoList.size() >= 1) {
            for (int i = 0; i < mPatientInfoList.size(); i++) {
                PatientInfo _p = mPatientInfoList.get(i);
                mPateintInfoListAdpter.add(i, _p);

            }
            for (int k = 0; k < mPateintInfoListAdpter.size() - 1; k++) {
                for (int j = mPateintInfoListAdpter.size() - 1; j > k; j--) {
                    if (mPateintInfoListAdpter.get(j).getmP_bah() == (mPateintInfoListAdpter.get(k).getmP_bah())) {
                        mPateintInfoListAdpter.remove(j);
                    }
                }

                //mPateintInfoListAdpter=removeDuplicate(mPateintInfoListAdpter);
                Log.i("log", "mPateintInfoListAdpter1=" + mPateintInfoListAdpter.size());
            }
        }*/
        mCache = ACache.get(getActivity());
        mAdapter = new TableItemAdapter();
        if (NetUtils.isConnected(getActivity())) {
            isCachedData = false;
            mAdapter.loadFirst();

        } else {
            google_progress.setVisibility(View.GONE);
            JSONArray jsonArray = mCache.getAsJSONArray("JsonArray");
            mPatientInfoListCache = getCacheDatas(jsonArray);
            mPatientInfoList.clear();
            mPatientInfoList.addAll(mPatientInfoListCache);
            mListView.setAdapter(mAdapter);
        }

        //  Log.i("log", "mPateintInfoListAdpter2=" + mPatientInfoListAdpter.size());
        //mAdapter = new TableItemAdapter(getActivity(), mPatientInfoListAdpter, tag);
        //标记
        //int tag = 0;
        //mAdapter = new TableItemAdapter(getActivity(), mPatientInfoList, tag);

        //mPatientInfoList.clear();
        // mAdapter = new TableItemAdapter();
        //mAdapter.loadFirst();
        //加载更多
        //loadMoreDate();
        //给listview设置adapter
        //mListView.setAdapter(mAdapter);
        //mListView.setAdapter(new ListViewAdapter(getActivity(), mPatientInfoList, tag));

        /* mListView.setXListViewListener(new IXListViewListener() {
             @Override
             public void onRefresh() {
                 mHandler.postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         mPatientInfoList.clear();
                         geneDatas();
                         // mAdapter.notifyDataSetChanged();
                         mAdapter = new TableItemAdapter(getActivity(), mPatientInfoList, 0);
                         mListView.setAdapter(mAdapter);
                         onLoad();
                     }
                 }, 2000);
             }

             @Override
             public void onLoadMore() {
                 mHandler.postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         geneDatas();
                         mAdapter.notifyDataSetChanged();
                         onLoad();
                     }
                 }, 2000);
             }
         });*/
        //mHandler = new Handler();
    }

    /*private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
      //mListView.setRefreshTime("刚刚");
    }*/

    /**
     * 设置各种监听
     */
    private void initListener() {
        mImageView.setOnClickListener(this);
        // mResideMenu.setMenuListener(new menuListener());
//        mLeftMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mResideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
//            }
//        });
        //mListView.setOnScrollListener(this);
       mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
               L.d("oo","position="+position);
               open(mPatientInfoList.get(position));
           }
       });

       /* mListView.setXListViewListener(new ZListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListView.stopRefresh();
                        //mPatientInfoList.clear();
                        //geneDatas();
                       // mListView.setAdapter(new ListViewAdapter(getActivity(), mPatientInfoList, 0));
                        onLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListView.stopLoadMore();
                       // geneDatas();
                       // mListView.setAdapter(new ListViewAdapter(getActivity(), mPatientInfoList, 0));
                        //onLoad();
                    }
                }, 1000);
            }
        });
        mListView.setPullLoadEnable(true);*/



      /*  mListView.setOnMenuItemClickListener(new ZListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                PatientInfo patientInfo = mPatientInfoList.get(position);
                switch (index) {
                    case 0:
                        // open
                        open(patientInfo);
                        break;
                    case 1:
                        // delete
                        delete(patientInfo);
                        //mPatientInfoListAdpter.remove(position);
                        mPatientInfoList.remove(position);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });*/
//        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
//                // PatientInfo patientInfo = mPatientInfoListAdpter.get(position);
//                PatientInfo patientInfo = mPatientInfoList.get(position);
//                switch (index) {
////                    case 0:
////                        // open
////                        open(patientInfo);
////                        break;
//                    case 0:
//
//                        if (NetUtils.isConnected(getActivity())) {
//                            L.d("oo","patientInfo.getid="+patientInfo.getId());
//                            // delete
//                            delete(patientInfo);
//                            mPatientInfoList.remove(position);
//                            mAdapter.notifyDataSetChanged();
//                            mListView.setAdapter(mAdapter);
//                        } else {
//                            mPatientInfoListCache.remove(position);
//                            mPatientInfoList.clear();
//                            mPatientInfoList.addAll(mPatientInfoListCache);
//                            mAdapter.notifyDataSetChanged();
//                            mListView.setAdapter(mAdapter);
//                        }
//
//                        break;
//                }
//            }
//        });
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
                                           final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = getActivity().getLayoutInflater().inflate(R.layout.dialog_layout, null);
                TextView title = (TextView) view1.findViewById(R.id.title);
                final TextView delete = (TextView) view1.findViewById(R.id.delete);
                TextView cancel = (TextView) view1.findViewById(R.id.add);
                TextView edit = (TextView) view1.findViewById(R.id.edit);
                edit.setVisibility(View.GONE);
                title.setText("是否删除");
                cancel.setText("取消");
                delete.setText("确定");
                dialog = builder.show();
                dialog.setCancelable(true);
                dialog.getWindow().setContentView(view1);

                delete.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        delete(mPatientInfoList.get(position));
                        dialog.dismiss();
                        mPatientInfoList.remove(position);
                        mListView.setAdapter(mAdapter);
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                return true;
            }
        });
    }

  /*  @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPatientInfoList.clear();
                geneDatas();
                // mAdapter.notifyDataSetChanged();
                mAdapter = new TableItemAdapter(getActivity(), mPatientInfoList, 0);
                mListView.setAdapter(mAdapter);
                onLoad();
            }
        }, 2000);
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                geneDatas();
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }*/

    /**
     * 侧滑菜单的监听
     */
   /* private class menuListener implements  ResideMenu.OnMenuListener{

        @Override
        public void openMenu() {
            MainActivity.is_closed=false;
            mLeftMenu.setVisibility(View.GONE);
        }

        @Override
        public void closeMenu() {
            MainActivity.is_closed=true;
           // mLeftMenu.setVisibility(View.VISIBLE);
            mLeftMenu.setVisibility(View.GONE);

        }
    }*/

//    @Override
//    protected void onActionBarClick() {
//        if(mRecyclerView!=null&&mAdapter.mPatientInfoList.size()>0){
//            mRecyclerView.scrollToPosition(0);
//        }
//    }

    /**
     * 加载更多
     */
   /* private void loadMoreDate() {
        int count = mAdapter.getCount();
        if (count + 5 < mMaxDateNum) {
            // 每次加载5条
            for (int i = count; i < count + 5; i++) {
                mPatientInfoListAdpter.add(mPatientInfoList.get(i));
            }
        } else {
            // 数据已经不足5条
            for (int i = count; i < mMaxDateNum; i++) {
                mPatientInfoListAdpter.add(mPatientInfoList.get(i));
            }
        }

    }*/


   /* @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        // 滑到底部后自动加载，判断listview已经停止滚动并且最后可视的条目等于adapter的条目
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                && lastVisibleIndex == mAdapter.getCount()) {
            //当滑到底部时自动加载
            mProgressBar.setVisibility(View.VISIBLE);

            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                   // loadMoreDate();
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
           // Toast.makeText(getActivity().getApplicationContext(), "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
        }
    }*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.patient_add:
                getActivity().startActivity(new Intent(getActivity(), PatientInfoAddActivity.class));
                break;
            default:
                break;
        }
    }
//    /**
//     * listview中item的监听事件
//     */
//    @Override
//    public void onItemClick(int position) {
//        Log.d("oo","position="+position);
//        final PatientInfo patientInfo = mPatientInfoList.get(position);
//
//        Intent intent=new Intent(getActivity(), TableFlowChart.class);
//
//        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("patientinfo",getActivity().MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("id",patientInfo.getId());
//        editor.commit();
//
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("patient_info_add", patientInfo);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }


    /**
     * 打开患者添加aty
     *
     * @param patientInfo
     */
    private void open(PatientInfo patientInfo) {

        Intent intent=new Intent(getActivity(), TableFlowChart.class);

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("patientinfo",getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id",patientInfo.getId());
        editor.commit();

        Bundle bundle = new Bundle();
        bundle.putSerializable("patient_info_add", patientInfo);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    /**
     * 删除某一个item
     *
     * @param patientInfo
     */
    private void delete(PatientInfo patientInfo) {

        Log.i("ZSN", "发请求");
        String URL_PATIRNT_DELETE = NetUrlAddress.ipAndPort + "/patient_infos" +
                "/destroy/" + patientInfo.getId() + ".json?token="+
                NetUrlAddress.token+"&&user_auth_id=" + String.valueOf(user_auth_id) + "&&menu_id=15";

        try {
            HttpUtils.doPostAsyn(URL_PATIRNT_DELETE, null, new HttpUtils.CallBack() {
                @Override
                public void onRequestComplete(String result) {
                    Log.i("ZSN", "result=" + result);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   /* @Override
    public void onResume() {
        super.onResume();
        mPatientInfoList.clear();
        //加载数据
        initData();
    }*/

    /**
     * 广播接收者，实现相应的事件的处理
     */
    private int i = 0;

    private class MybroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_INTENT_PATIENTINFO_ADD)) {
                i++;
                Log.i("uu", "执行的次数" + i);
                mPatientInfoList.clear();
                google_progress.setVisibility(View.VISIBLE);
                initData();
            }
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

    /**
     * 患者详细适配器
     */
    /*public class PatientInfoAdapter extends RecyclerView.Adapter<ViewHolder>{
        private int page;
        private int lastPosition = -1;
        private List<PatientInfo> mPatientInfoList;
        public PatientInfoAdapter(){
            mPatientInfoList=new ArrayList<PatientInfo>();
        }
        private void setAnimation(View viewToAnimate, int position) {
            if (position > lastPosition) {
                Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R
                        .anim.item_bottom_in);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }
        //在给item加上动画之后，慢速滑动的时候是没有问题的，但要快速滑动就会出现卡屏现象
        //问题就出在动画设置完之后，没有及时清除动画
        @Override
        public void onViewDetachedFromWindow(ViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            holder.ll_content.clearAnimation();
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_use_info_list,parent,false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final PatientInfo patientInfo=mPatientInfoList.get(position);
            if(patientInfo.getmP_bah()==-1){
                holder.tv1.setText("");
            }else{
                holder.tv1.setText(""+patientInfo.getmP_bah());
            }
            holder.tv2.setText(patientInfo.getmName());
            holder.tv3.setText(patientInfo.getmSex());


            holder.ll_content.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    open(patientInfo);
                }
            });
            setAnimation(holder.ll_content, position);
        }




        @Override
        public int getItemCount() {
            return mPatientInfoList.size();
        }
        private void loadFirst(){
            page=1;
            loadDataByNetworkType();
        }

        public void loadNextPage() {
            page++;
            loadDataByNetworkType();
        }

        private void loadDataByNetworkType() {

            if(NetUtils.isConnected(getActivity())) {
                executeRequest(new Request4PatientInfo(PatientInfo.getUrlPatientInfos(page), new Response.Listener<ArrayList<PatientInfo>>() {
                    @Override
                    public void onResponse(ArrayList<PatientInfo> response) {
                        google_progress.setVisibility(View.GONE);
                        mLoadFinisCallBack.loadFinish(null);
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }

                        if(page==1){
                            mPatientInfoList.clear();
                            //清空缓存
                        }
                        mAdapter.mPatientInfoList.addAll(response);
                        notifyDataSetChanged();
                        //添加入缓存

                    }
                },new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("TAG",error.getMessage(),error);
                        ShowToast.Short(ToastMsg.LOAD_FAILED);
                        google_progress.setVisibility(View.GONE);
                        mLoadFinisCallBack.loadFinish(null);
                        if(mSwipeRefreshLayout.isRefreshing()){
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }));
            }else{
                google_progress.setVisibility(View.GONE);
                mLoadFinisCallBack.loadFinish(null);
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                if (page == 1) {
                    mPatientInfoList.clear();
                    ShowToast.Short(ToastMsg.LOAD_NO_NETWORK);
                }

               //添加缓存
                //notifyDataSetChanged();
            }
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv1;
        private TextView tv2;
        private TextView tv3;
        private LinearLayout ll_content;
        public ViewHolder(View contentView) {
            super(contentView);
            tv1= (TextView) contentView.findViewById(R.id.tv1);
            tv2= (TextView) contentView.findViewById(R.id.tv2);
            tv3= (TextView) contentView.findViewById(R.id.tv3);
            ll_content = (LinearLayout) contentView.findViewById(R.id.ll_content);
        }



    }*/
    public class TableItemAdapter extends BaseAdapter {
       // public  ListItemDelete itemDelete = null;
        private PatientInfo mPatientInfo;
        @Override
        public int getCount() {
            return mPatientInfoList== null ? 0 : mPatientInfoList.size();
        }

        @Override
        public Object getItem(int i) {
            return mPatientInfoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView( final int position, View _view, ViewGroup viewGroup) {
            Holder _holder = null;
            if (_view == null) {
//                _view = LayoutInflater.from(getActivity()).inflate(
//                        R.layout.item_delete, null);
                _view = LayoutInflater.from(getActivity()).inflate(
                        R.layout.item_common_use_info_list, null);
                _holder = new Holder();
                _holder.tv1 = (TextView) _view.findViewById(R.id.tv1);
                _holder.tv2 = (TextView) _view.findViewById(R.id.tv2);
                _holder.tv3 = (TextView) _view.findViewById(R.id.tv3);
                //_holder.btnDelete = (Button) _view.findViewById(R.id.btnDelete);

                _view.setTag(_holder);
            } else {
                _holder = (Holder) _view.getTag();
            }


            mPatientInfo = (PatientInfo) mPatientInfoList.get(position);
            Log.i("log", "PatientInfo=" + ((PatientInfo) mPatientInfoList.get(0)).getmP_bah());
            if (mPatientInfo.getmP_bah() == -1) {
                _holder.tv1.setText("");
            } else {
                _holder.tv1.setText("" + mPatientInfo.getmP_bah());
            }
            _holder.tv2.setText(mPatientInfo.getmName());
            _holder.tv3.setText(mPatientInfo.getmSex());
//            _holder.btnDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    delete(mPatientInfo);
//                    mPatientInfoList.remove(position);
//                    mListView.setAdapter(mAdapter);
//                }
//            });

            return _view;

        }
        private Toast mToast;
        public void showInfo(String text) {
            if (mToast == null) {
                mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
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
            SharedPreferences preferences = getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
            int use_id = preferences.getInt("use_id", -1);
            String URL_PATIENT_INFO_ADD = NetUrlAddress.ipAndPort + "/patient_infos/index/1.json?per_page=10&&" +
                    "user_auth_id=" + String.valueOf(use_id) + "&&token="+NetUrlAddress.token+"&&page=" + page + "&&menu_id=15";
            if (NetUtils.isConnected(getActivity())) {
                if (!isCachedData) {
                   HttpUtil.get(URL_PATIENT_INFO_ADD, null, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            google_progress.setVisibility(View.GONE);
                            mLoadFinisCallBack.loadFinish(null);
                            if (mSwipeRefreshLayout.isRefreshing()) {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }

                            if (page == 1) {
                                mPatientInfoList.clear();
                                //清空缓存
                                mCache.remove("JsonArray");

                            }

                            ArrayList<PatientInfo> parseInfoList = PatientInfo.parse(response);
                            mPatientInfoList.addAll(parseInfoList);
                            if(page ==1){
                                if (mPatientInfoList.size() == 0) {
                                    no_content.setVisibility(View.VISIBLE);
                                    mListView.setAdapter(mAdapter);
                                } else {
                                    no_content.setVisibility(View.GONE);
                                    mListView.setAdapter(mAdapter);
                                }
                            }else{
                                if (mPatientInfoList.size() == 0) {
                                    no_content.setVisibility(View.VISIBLE);
                                    mAdapter.notifyDataSetChanged();

                                } else {
                                    no_content.setVisibility(View.GONE);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                            if (mPatientInfoList.size() > 0) {
                                //默认第一项的id
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("patientinfo", getActivity().MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("id", mPatientInfoList.get(0).getId());
                                editor.commit();
                            }

                            //添加入缓存
                            Log.i("response", "response=" + response);
                            mCache.put("JsonArray", response);
                            isCachedData = true;
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.i("TAG", throwable.getMessage(), throwable);
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
                    mPatientInfoListCache = getCacheDatas(jsonArray);
                    mPatientInfoList.clear();
                    mPatientInfoList.addAll(mPatientInfoListCache);
                    notifyDataSetChanged();
                }
            } else {
                google_progress.setVisibility(View.GONE);
                mLoadFinisCallBack.loadFinish(null);
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                if (page == 1) {
                    mPatientInfoList.clear();
                    ShowToast.Short(ToastMsg.LOAD_NO_NETWORK);
                }
                //取出缓存
                JSONArray jsonArray = mCache.getAsJSONArray("JsonArray");
                if (jsonArray != null) {
                    mPatientInfoListCache = getCacheDatas(jsonArray);
                    mPatientInfoList.clear();
                    mPatientInfoList.addAll(mPatientInfoListCache);
                    notifyDataSetChanged();
                }
            }
        }

        private class Holder {
            TextView tv1, tv2, tv3;
            //Button btnDelete;
        }
    }

    /**
     * 通过url拿到服务器json数据
     *
     * @param url
     * @return
     */
    private String loadJson(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销广播
        getActivity().unregisterReceiver(mybroadCastReceiver);
    }
}
