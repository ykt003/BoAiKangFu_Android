package me.zhangls.rilintech.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.umeng.update.UmengUpdateAgent;
import com.zhangls.residemenu.ResideMenuInfo;
import com.zhangls.residemenu.ResideMenuItem;

import de.greenrobot.event.EventBus;
import me.zhangls.rilintech.R;
import me.zhangls.rilintech.fragment.FragmentFactory;
import me.zhangls.rilintech.model.NetWorkEvent;
import me.zhangls.rilintech.utils.NetUtils;
import me.zhangls.rilintech.utils.ShowToast;
import me.zhangls.rilintech.utils.SystemBarTintManager;

/**
 * 主界面
 */
public class MainActivity extends Activity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {

    public BroadcastReceiver netStateReceiver;
    public MaterialDialog noNetWorkDialog;
    public static boolean active = false;
    /**
     * 侧滑菜单menu
     */
    // private static ResideMenu resideMenu;
    /**
     * FragmentManager管理者
     */
    private FragmentManager fragmentManager;
    /**
     *侧滑菜单是否关闭
     */
    //public static boolean is_closed = false;
    /**
     * 菜单的item
     */
    private ResideMenuItem itemHuiyuan;
    private ResideMenuItem itemQianbao;
    private ResideMenuItem itemZhuangban;
    private ResideMenuItem itemShoucang;
    private ResideMenuItem itemXiangce;
    private ResideMenuItem itemFile;
    private ResideMenuInfo info;
    /**
     * 退出时间
     */
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 创建状态栏的管理实例
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // 激活状态栏设置
            tintManager.setStatusBarTintEnabled(true);
            // 设置一个颜色给系统栏
            tintManager.setTintColor(Color.parseColor("#990000FF"));
            tintManager.setStatusBarTintColor(0xff56abe4);
        }

        EventBus.getDefault().register(this);
        checkNetWork();

        UmengUpdateAgent.setUpdateCheckConfig(false);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);

        setContentView(R.layout.activity);
        // 删除窗口背景，解决Android Activity切换时出现白屏问题
        getWindow().setBackgroundDrawable(null);

        // setUpMenu();

        //   setListener();
      /*  //根据id值跳转到不同的fragment
        int id = getIntent().getIntExtra("fragid",-1);
        if(id>0){
            if(id==R.id.rb_mylist){
                FragmentTransaction transaction = fragmentManager.beginTransaction();
               Fragment _Fragment=FragmentFactory.getInstanceByIndex(id);
                transaction.replace(R.id.content, _Fragment);
                //添加至回退栈
                transaction.addToBackStack(null);
                transaction.commit();

            }
        }*/

        fragmentManager = getFragmentManager();
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        radioGroup.setOnCheckedChangeListener(this);
        //默认选中第一个fragment
        findViewById(R.id.rb_attention).performClick();
    }

    /**
     * 设置各种监听
     */
    private void setListener() {

        //resideMenu.addMenuInfo(info);

        itemHuiyuan.setOnClickListener(this);
        itemQianbao.setOnClickListener(this);
        itemZhuangban.setOnClickListener(this);
        itemShoucang.setOnClickListener(this);
        itemXiangce.setOnClickListener(this);
        itemFile.setOnClickListener(this);

        info.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        active=true;

    }

    @Override
    protected void onStop() {
        super.onStop();
        active=false;
    }

    /**
     * 检查网络状态
     */
    public void checkNetWork() {
        netStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    if (NetUtils.isConnected(MainActivity.this)) {
                        EventBus.getDefault().post(new NetWorkEvent(NetWorkEvent.AVAILABLE));
                    } else {
                        EventBus.getDefault().post(new NetWorkEvent(NetWorkEvent.UNAVAILABLE));
                    }
                }
            }
        };
        //注册
        registerReceiver(netStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }


    public void onEvent(NetWorkEvent event) {
        if (event.getType() == NetWorkEvent.UNAVAILABLE) {
            if (noNetWorkDialog == null) {
                noNetWorkDialog = new MaterialDialog.Builder(MainActivity.this)
                        .title("无网络连接")
                        .content("去开启网络?")
                        .positiveText("是")
                        .negativeText("否")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                //dialog.dismiss();
                                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(intent);
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                //dialog.dismiss();
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
    /**
     * 创建侧滑菜单
     */
    /*private void setUpMenu() {

        resideMenu=new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        //resideMenu.setMenuListener(new menuListener());
        resideMenu.setScaleValue(0.6f);
        //禁止使用右侧菜单
        resideMenu.setDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        //禁止使用左侧菜单
        resideMenu.setDirectionDisable(ResideMenu.DIRECTION_LEFT);
        // create menu items;
        itemHuiyuan = new ResideMenuItem(this, R.drawable.ic_launcher, "开通会员");
        itemQianbao = new ResideMenuItem(this, R.drawable.ic_launcher, "QQ钱包");
        itemZhuangban = new ResideMenuItem(this, R.drawable.ic_launcher, "个性装扮");
        itemShoucang = new ResideMenuItem(this, R.drawable.ic_launcher, "我的收藏");
        itemXiangce = new ResideMenuItem(this, R.drawable.ic_launcher, "我的相册");
        itemFile = new ResideMenuItem(this, R.drawable.ic_launcher, "我的文件");

        resideMenu.addMenuItem(itemHuiyuan, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemQianbao, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemZhuangban, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemShoucang, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemXiangce, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemFile, ResideMenu.DIRECTION_LEFT);

        //给用户赋值
        info = new ResideMenuInfo(this, R.drawable.icon_profile, "开心小萝卜头",
                "12 级");


    }*/

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        //resideMenu.clearIgnoredViewList();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //实例化需要的fragment
        Fragment fragment = FragmentFactory.getInstanceByIndex(checkedId);
        transaction.replace(R.id.content, fragment);
        transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        //添加至回退栈
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * 用来分发TouchEvent
     * @param
     * @return
     */
    /*@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }*/

    @Override
    public void onClick(View view) {
        if (view == itemHuiyuan) {
            Intent intent = new Intent();
            intent.putExtra("flog", "会员");
            intent.setClass(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
        } else if (view == itemQianbao) {
            Intent intent = new Intent();
            intent.putExtra("flog", "钱包");
            intent.setClass(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
        } else if (view == itemZhuangban) {
            Intent intent = new Intent();
            intent.putExtra("flog", "装扮");
            intent.setClass(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
        } else if (view == itemShoucang) {
            Intent intent = new Intent();
            intent.putExtra("flog", "收藏");
            intent.setClass(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
        } else if (view == itemXiangce) {
            Intent intent = new Intent();
            intent.putExtra("flog", "相册");
            intent.setClass(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
        } else if (view == itemFile) {
            Intent intent = new Intent();
            intent.putExtra("flog", "文件");
            intent.setClass(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
        } else if (view == info) {
            Intent intent = new Intent();
            intent.putExtra("flog", "个人信息");
            intent.setClass(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
        }

    }

    /**
     * 获取侧滑菜单对象
     * @return
     */
   /* public static ResideMenu getResideMenu(){
        return  resideMenu;
    }*/

    /**
     *  监听手机上的BACK键
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            // 判断菜单是否关闭
            // if (is_closed) {
            // 判断两次点击的时间间隔（默认设置为2秒）
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ShowToast.Long("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
                System.gc();
            }
           /* } else {
                resideMenu.closeMenu();
            }*/
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active=false;
        unregisterReceiver(netStateReceiver);
        EventBus.getDefault().unregister(this);
    }
}
