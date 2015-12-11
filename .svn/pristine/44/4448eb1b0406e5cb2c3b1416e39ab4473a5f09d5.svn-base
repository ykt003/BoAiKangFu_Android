package me.zhangls.rilintech.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.zhangls.rilintech.R;


/**
 * Created by admin on 13-11-23.
 */
public abstract class BaseFragment extends Fragment {
   // protected ActionBar mActionBar;
  // private BroadcastReceiver netStateReceiver;
   // public MaterialDialog noNetWorkDialog;
  //  static boolean active = false;
    //public boolean isNetAvailable=false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       // EventBus.getDefault().register(this);

//        mActionBar = ((AppCompatActivity) activity).getSupportActionBar();
//        Log.i("TAG","mActionBar="+mActionBar);
//        if(activity instanceof MainActivity){
//            mActionBar.getCustomView().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onActionBarClick();
//                }
//            });
 //       }
    }

//    protected void onActionBarClick() {
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, null);
        TextView textView = (TextView) view.findViewById(R.id.txt_content);
        textView.setText(getContent());
        return view;
    }
    /**
     * 由子类实现
     *
     * @return
     */
    public abstract String getContent();
    /**
     * 网络请求
     * @param
     */
//    protected void executeRequest(Request request){
//        RequestManager.addRequest(request, this);
//    }
//    protected Response.ErrorListener errorListener(){
//        return  new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                ShowToast.Long(error.getMessage());
//            }
//        };
//    }
//
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
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        active=false;
//        getActivity().unregisterReceiver(netStateReceiver);
//        EventBus.getDefault().unregister(this);
//    }
//    /**
//     * 检查网络状态
//     */
//    public void checkNetWork() {
//        netStateReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
//                    if (NetUtils.isConnected(getActivity())) {
//                        isNetAvailable=true;
//                        EventBus.getDefault().post(new NetWorkEvent(NetWorkEvent.AVAILABLE));
//                    } else {
//                        isNetAvailable=false;
//                        EventBus.getDefault().post(new NetWorkEvent(NetWorkEvent.UNAVAILABLE));
//                    }
//                }
//            }
//        };
//        //注册
//        getActivity().registerReceiver(netStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//    }
//
//
//    public void onEvent(NetWorkEvent event) {
//        if (event.getType() == NetWorkEvent.UNAVAILABLE) {
//            if (noNetWorkDialog == null) {
//                noNetWorkDialog = new MaterialDialog.Builder(getActivity())
//                        .title("无网络连接")
//                        .content("去开启网络?")
//                        .positiveText("是")
//                        .negativeText("否")
//                        .callback(new MaterialDialog.ButtonCallback() {
//                            @Override
//                            public void onPositive(MaterialDialog dialog) {
//                                dialog.dismiss();
//                                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
//                                startActivity(intent);
//                            }
//
//                            @Override
//                            public void onNegative(MaterialDialog dialog) {
//                                dialog.dismiss();
//                            }
//                        }).cancelable(false)
//                        .build();
//            }
//            if(active){
//                if (!noNetWorkDialog.isShowing()) {
//                    noNetWorkDialog.show();
//                    active=false;
//                }
//            }
//        }
//    }
}
