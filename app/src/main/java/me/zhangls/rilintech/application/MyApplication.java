package me.zhangls.rilintech.application;

import android.app.Application;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.zhangls.rilintech.utils.CrashHandler;
import me.zhangls.rilintech.utils.NetUtils;

/**
 * Updated by zsn on 15/9/1.
 */
public class MyApplication extends Application {

    public static boolean isDebug = false;// 是否需要打印bug
    private static Context mContext;
    private static MyApplication instance;
    public static final int TIMEOUT_IN_MILLIONS = 5000;

    //网络状态码
    public static int mNetWorkState;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext=this;
        instance=this;

        initData();

        //运行异常捕获类
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

    }
    public static Context getContext(){
        return mContext;
    }

    public static MyApplication getInstance(){
        return instance;
    }
    public void initData() {
        isDebug = true;
        mNetWorkState = NetUtils.getNetworkState(this);
    }
    /**
     * 获取系统当前时间
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}
