package me.zhangls.rilintech.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.fragment.FeedbackFragment;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import java.util.ArrayList;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.activity.AboutUsActivity;
import me.zhangls.rilintech.activity.FeedBackActivity;
import me.zhangls.rilintech.activity.LoginActivity;
import me.zhangls.rilintech.activity.PasswordChangeActivity;
import me.zhangls.rilintech.adapter.SettingAdapter;

/**
 * Created by YANG on 15/12/7.
 */
public class SettingFragment extends Fragment {
    private Context context;
    /**
     * ListView
     */
    private ListView mListView;
    /**
     * 加载器
     */
    private SettingAdapter mAdapter;
    /**
     * 版本号
     */
    private int versionCode;
    /**
     * 版本名
     */
    private String versionName;
    /**
     * 左侧数据内容
     */
    private ArrayList<String> list_left;
    /**
     * 右侧数据内容
     */
    private ArrayList<String> list_right;
    /**
     * 登录用户信息
     */
    private SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity();

        View view = inflater.inflate(R.layout.fragment_setting, null);

        initView(view);
        initData();
        initAdapter();
        setListener();

        return view;
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    private void initView(View view) {

        mListView = (ListView) view.findViewById(R.id.list_view_setting);

    }

    /**
     * 数据初始化
     */
    private void initData() {

        list_left = new ArrayList<>();
        list_left.add(getResources().getString(R.string.version_code));
        list_left.add(getResources().getString(R.string.about_us));
        list_left.add(getResources().getString(R.string.feedback));
        list_left.add(getResources().getString(R.string.changepassword));
        list_left.add(getResources().getString(R.string.logout));

        versionName = getVersionName(context);
        versionCode = getVersionCode(context);

        list_right = new ArrayList<>();
        list_right.add("当前版本为" + versionName);
        list_right.add("");
        list_right.add("");
        list_right.add("");
        list_right.add("");

    }

    /**
     * 获取加载器
     */
    private void initAdapter() {
        mAdapter = new SettingAdapter(context, list_left, list_right);
        mListView.setAdapter(mAdapter);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        mListView.setOnItemClickListener(new MyOnItemClickListener());
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (position == 0) {//检查更新
                UmengUpdateAgent.setUpdateCheckConfig(false);
                UmengUpdateAgent.setUpdateOnlyWifi(false);
                UmengUpdateAgent.setUpdateAutoPopup(false);
                UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
                    @Override
                    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                        switch (updateStatus) {
                            case UpdateStatus.Yes: // has update
                                UmengUpdateAgent.showUpdateDialog(context, updateInfo);
                                break;
                            case UpdateStatus.No: // has no update
                                Toast.makeText(context, "当前已经是最新版本", Toast.LENGTH_SHORT).show();
                                break;
                            case UpdateStatus.NoneWifi: // none wifi
                                Toast.makeText(context, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
                                break;
                            case UpdateStatus.Timeout: // time out
                                Toast.makeText(context, "超时", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
                UmengUpdateAgent.forceUpdate(context);

            } else if (position == 1) {//关于我们
                Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent);

            } else if (position == 2) {//意见反馈

                Intent intent = new Intent(getActivity(), FeedBackActivity.class);
                String ids = new FeedbackAgent(getActivity()).getDefaultConversation().getId();
                intent.putExtra(FeedbackFragment.BUNDLE_KEY_CONVERSATION_ID, ids);
                startActivity(intent);

                /*FeedbackAgent agent = new FeedbackAgent(context);
                agent.startFeedbackActivity();*/
            } else if (position == 3) {//修改密码
                Intent intent = new Intent(getActivity(), PasswordChangeActivity.class);
                startActivity(intent);

            } else if (position == 4) {//退出登录
                preferences = getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("user_name","");
                editor.putString("password", "");
                editor.commit();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

                getActivity().finish();

            } else {
                Toast.makeText(context, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 获取版本名
     *
     * @param context
     * @return
     */
    private String getVersionName(Context context) {
        String name = "";

        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);

            name = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    private int getVersionCode(Context context) {
        int code = 0;

        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);

            code = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return code;
    }

}
