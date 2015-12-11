package me.zhangls.rilintech.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zhangls.swipemenulistview.SwipeMenu;
import com.zhangls.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import me.zhangls.rilintech.R;
import me.zhangls.rilintech.activity.MedicalRecordAddActivity;
import me.zhangls.rilintech.adapter.TableItemAdapter;
import me.zhangls.rilintech.manager.MedicalRecordManager;
import me.zhangls.rilintech.model.MedicalRecord;
import me.zhangls.rilintech.model.NetWorkEvent;
import me.zhangls.rilintech.utils.GetSwipeMenuCreator;
import me.zhangls.rilintech.utils.NetUtils;


/**
 * Created by zsn on 2015/8/4.
 */
public class MedicalRecordFragment extends Fragment implements AbsListView.OnScrollListener, View.OnClickListener {
    //fragment内的view
    private View mView;
    //左边滑动出现的删除和打开的view
    private SwipeMenuListView mListView;
    //用于刷新的list集合
    private List<MedicalRecord> mMedicalRecordsListAdapter=new ArrayList<MedicalRecord>();
    //从数据库拿到的所有的数据源
    private List<MedicalRecord> mMedicalRecordsList= new ArrayList<MedicalRecord>();
    //listview上面标题栏的三个item
    public TableItemAdapter mAdapter;
    //进度条
    private ProgressBar mProgressBar;
    // 设置一个最大的数据条数，超过即不再加载
    private int mMaxDateNum;
    // 最后可见条目的索引
    private int lastVisibleIndex;
    // ListView底部View
    private View mMoreView;
    //handler
    private Handler mHandler;
    //添加住院按钮
    private ImageView mImageView;
    //侧滑菜单menu
    //private ResideMenu mResideMenu;
    //标题栏左边的头像
    private ImageView mLeftMenu;
    //广播的标志字段
    public static final String ACTION_INTENT_MEDICALRECORD_ADD = "me.zhangls.rilintech.activity";
    //广播接收者对象
    private MybroadCastReceiver  mybroadCastReceiver;
    private BroadcastReceiver netStateReceiver;
    private MaterialDialog noNetWorkDialog;
    private static boolean active = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_hospital_info, null);

       // EventBus.getDefault().register(this);
        //广播过滤器对象
        IntentFilter myIntentFilter=new IntentFilter();
        mybroadCastReceiver=new MybroadCastReceiver();
        myIntentFilter.addAction(ACTION_INTENT_MEDICALRECORD_ADD);
        //动态注册广播
        getActivity().registerReceiver(mybroadCastReceiver, myIntentFilter);

        initView();
        initData();
        initListener();
        //checkNetWork();
        return mView;
    }


    /**
     * 各种初始化
     */
    private void initView() {
        //复用xml
        TextView _tv = (TextView) mView.findViewById(R.id.title_common_use);
        _tv.setText("就诊列表");
        TextView _tv1= (TextView) mView.findViewById(R.id.item_common_use_1);
        _tv1.setText("病房号");
        TextView _tv2= (TextView) mView.findViewById(R.id.item_common_use_2);
        _tv2.setText("科室");
        TextView _tv3= (TextView) mView.findViewById(R.id.item_common_use_3);
        _tv3.setText("康复诊断");
        //添加按钮对象
        mImageView = (ImageView)mView.findViewById(R.id.patient_add);
        //获取mainAty的侧滑菜单对象
        //mResideMenu= MainActivity.getResideMenu();
        //标题栏左边的头像
        mLeftMenu = (ImageView) mView.findViewById(R.id.title_bar_left_menu);
        //listView对象
        mListView = (SwipeMenuListView) mView.findViewById(R.id.patient_infos_list_item);
        //listView中向左滑动，初始化SwipeMenu菜单
        GetSwipeMenuCreator creator=new GetSwipeMenuCreator(getActivity());
        mListView.setMenuCreator(creator);
        // 实例化底部布局
        mMoreView = getActivity().getLayoutInflater().inflate(R.layout.activity_progress, null);
        //增加的布局的progressBar
        mProgressBar = (ProgressBar) mMoreView.findViewById(R.id.pg);

    }

    /**
     * 加载数据
     */
    private void initData() {
        //获取数据源
        MedicalRecordManager _mMedicalRecordManager=new MedicalRecordManager(getActivity());
        _mMedicalRecordManager.openDataBase();
        mMedicalRecordsList= _mMedicalRecordManager.medicalRecords();
        _mMedicalRecordManager.closeDataBase();

        //数据源集合的最大值
        mMaxDateNum = mMedicalRecordsList.size();
        mHandler = new Handler();
        // 加上底部View，注意要放在setAdapter方法前
        mListView.addFooterView(mMoreView);
        //标记
        int tag=1;
        //初始化adapter
        mAdapter = new TableItemAdapter(getActivity(), mMedicalRecordsListAdapter,tag);
        //加载更多
        loadMoreDate();
        //给listview设置adapter
        mListView.setAdapter(mAdapter);

    }

    /**
     * 设置各种监听
     */
    private void initListener() {
        mImageView.setOnClickListener(this);
       // mResideMenu.setMenuListener(new menuListener());
       /* mLeftMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mResideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });*/
        mListView.setOnScrollListener(this);
        mListView.setOnItemClickListener(new ListViewOnItemClickListener());
        //给listview设置监听
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                MedicalRecord item = mMedicalRecordsListAdapter.get(position);
                switch (index) {
                    //打开
                    case 0:
                        open(item);
                        break;
                    //删除
                    case 1:
                        //	delete(item);
                        mMedicalRecordsListAdapter.remove(position);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });

        //listView设置滑动过程中的监听
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

        // listview长按的监听
        mListView.setOnItemLongClickListener(new SwipeMenuListView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(getActivity(), position + " long click", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }
    /**
     * 点击标题栏头像的事件监听
     */
    /*private class menuListener implements  ResideMenu.OnMenuListener{

        @Override
        public void openMenu() {
            MainActivity.is_closed=false;
            mLeftMenu.setVisibility(View.GONE);
        }

        @Override
        public void closeMenu() {
            MainActivity.is_closed=true;
            mLeftMenu.setVisibility(View.VISIBLE);

        }
    }*/
    /**
     * listview加载更多
     */
    private void loadMoreDate() {
        int count = mAdapter.getCount();
        if (count + 5 < mMaxDateNum) {
            // 每次加载5条
            for (int i = count; i < count + 5; i++) {
                mMedicalRecordsListAdapter.add(mMedicalRecordsList.get(i));
            }
        } else {
            // 数据已经不足5条
            for (int i = count; i < mMaxDateNum; i++) {
                mMedicalRecordsListAdapter.add(mMedicalRecordsList.get(i));
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

            }, 2000);

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
            //Toast.makeText(getActivity().getApplicationContext(), "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * listview中item的监听事件
     */
    private class ListViewOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            MedicalRecord _medicalRecord = mMedicalRecordsListAdapter.get(position);
            open(_medicalRecord);
        }
    }
    /**
     * 通过listview中item打开aty
     * @param _medicalRecord
     */
    private void open(MedicalRecord _medicalRecord) {

        int _roomRecord=_medicalRecord.getmHospitalRoomRecord();
        String  _hospitalChiefComplaint=_medicalRecord.getmHospitalChiefComplaint();
        String  _hospitalClinicalDiagnosis = _medicalRecord.getmHospitalClinicalDiagnosis();
        String _hospitalDepartment= _medicalRecord.getmHospitalDepartment();
        String  _hospitalPastHistory=_medicalRecord.getmHospitalPastHistory();
        String _hospitalRecoveryDiagnosis=_medicalRecord.getmHospitalRecoveryDiagnosis();

        Intent _Intent=new Intent(getActivity(),MedicalRecordAddActivity.class);
        _Intent.putExtra("roomRecord",_roomRecord);
        _Intent.putExtra("hospitalChiefComplaint",_hospitalChiefComplaint);
        _Intent.putExtra("hospitalClinicalDiagnosis",_hospitalClinicalDiagnosis);
        _Intent.putExtra("hospitalDepartment",_hospitalDepartment);
        _Intent.putExtra("hospitalPastHistory",_hospitalPastHistory);
        _Intent.putExtra("hospitalRecoveryDiagnosis",_hospitalRecoveryDiagnosis);

        startActivity(_Intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.patient_add:
                openActivity(MedicalRecordAddActivity.class);
                break;
            default:
                break;
        }
    }
    /**
     * 启动aty
     */
    protected void openActivity(Class<?>pClass) {
        Intent _Intent=new Intent();
        _Intent.setClass(getActivity(), pClass);
        startActivity(_Intent);
    }

    /**
     * 广播接收者，实现相应的事件的处理
     */
    private class MybroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action= intent.getAction();
            if(action.equals(ACTION_INTENT_MEDICALRECORD_ADD)){
                //加载数据
                initData();
            }
        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        active=true;
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        active=false;
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销广播
        getActivity().unregisterReceiver(mybroadCastReceiver);
//        active=false;
//        getActivity().unregisterReceiver(netStateReceiver);
//        EventBus.getDefault().unregister(this);
    }
    /**
     * 检查网络状态
     */
    public void checkNetWork() {
        netStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    if (NetUtils.isConnected(getActivity())) {
                        EventBus.getDefault().post(new NetWorkEvent(NetWorkEvent.AVAILABLE));
                    } else {
                        EventBus.getDefault().post(new NetWorkEvent(NetWorkEvent.UNAVAILABLE));
                    }
                }
            }
        };
        //注册
        getActivity().registerReceiver(netStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }


    public void onEvent(NetWorkEvent event) {
        if (event.getType() == NetWorkEvent.UNAVAILABLE) {
            if (noNetWorkDialog == null) {
                noNetWorkDialog = new MaterialDialog.Builder(getActivity())
                        .title("无网络连接")
                        .content("去开启网络?")
                        .positiveText("是")
                        .negativeText("否")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                dialog.dismiss();
                                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(intent);
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                dialog.dismiss();
                            }
                        }).cancelable(false)
                        .build();
            }
            if(active){
                if (!noNetWorkDialog.isShowing()) {
                    noNetWorkDialog.show();
                }
            }
        }
    }
}
