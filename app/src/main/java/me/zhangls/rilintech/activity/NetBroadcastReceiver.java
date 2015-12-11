package me.zhangls.rilintech.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.ArrayList;
import me.zhangls.rilintech.application.MyApplication;
import me.zhangls.rilintech.utils.NetUtils;

/**
 * Created by YANG on 15/9/7.
 */
public class NetBroadcastReceiver extends BroadcastReceiver {
    public static ArrayList<netEventHandler> mListeners = new ArrayList<netEventHandler>();
    public static String NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(NET_CHANGE_ACTION)) {
            MyApplication.mNetWorkState = NetUtils.getNetworkState(context);
            if (mListeners.size() > 0)// 通知接口完成加载
                for (netEventHandler handler : mListeners) {
                    handler.onNetChange();
                }
        }
    }

    public static abstract interface netEventHandler {

        public abstract void onNetChange();
    }
}