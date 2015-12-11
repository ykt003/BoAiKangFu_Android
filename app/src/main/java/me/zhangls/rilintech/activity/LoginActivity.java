package me.zhangls.rilintech.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;

import java.util.HashMap;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.MenuLibModel;
import me.zhangls.rilintech.utils.HttpUtils;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.NetUtils;
import me.zhangls.rilintech.utils.ShowToast;

/**
 * Created by YANG on 15/9/1.
 */
public class LoginActivity extends Activity {

    //密码
    private EditText password;
    //可见
    private ImageView visible;
    //不可见
    private ImageView invisible;
    //intent
    private Intent intent;
    //SharedPreferences
    private SharedPreferences preferences;
    //用户名
    private EditText user_name;
    //是否为空
    private boolean isEmpty = false;
    //dialog
    private AlertDialog dialog;
    //参数
    private String param;
    //返回的登录数据
    private HashMap<Integer, Integer> result;
    //菜单数据
    private String value;
    //将菜单数据保存成功返回的响应码
    private int resultCode;
    private Handler handler;
    //第一次登录
    private Boolean isFirstIn = false;

    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_FAILD = 0;
    private MaterialDialog noNetWorkDialog;
//    private Handler handler2=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case 10086:
//
//
//                    break;
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_login);

        initView();

        //网络返回数据后得到通知刷新View
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case RESULT_SUCCESS:
                        SharedPreferences pref = getSharedPreferences("FirstIn", 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("isFirstIn", false);
                        editor.commit();

                        dialog.dismiss();
                        intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                        break;
                    case RESULT_FAILD:
                        dialog.dismiss();
                        ShowToast.Short("登录失败，请重新登录");
                        break;
                    case 10086:
                        if (noNetWorkDialog == null) {
                            noNetWorkDialog = new MaterialDialog.Builder(LoginActivity.this)
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
                        if (!noNetWorkDialog.isShowing()) {
                            noNetWorkDialog.show();
                        }

                        break;
                }
            }
        };


    }

    private void initView() {

        SharedPreferences pref = getSharedPreferences("FirstIn", 0);
        //取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        isFirstIn = pref.getBoolean("isFirstIn", true);

        resultCode = RESULT_FAILD;
        password = (EditText) findViewById(R.id.password);
        visible = (ImageView) findViewById(R.id.visible);
        invisible = (ImageView) findViewById(R.id.invisible);
        user_name = (EditText) findViewById(R.id.user_name);

        preferences = getSharedPreferences("user", MODE_PRIVATE);
        String username = preferences.getString("user_name", "");
        String pass_word = preferences.getString("password", "");

        user_name.setText(username);
        password.setText(pass_word);
    }


    /**
     * 保存用户资料
     *
     * @param user_name
     * @param password
     */
    private void saveUser(String user_name, String password, int use_id) {
        preferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_name", user_name);
        editor.putString("password", password);
        editor.putInt("use_id", use_id);
        editor.commit();
    }

    /**
     * 判断用户名密码是否为空
     *
     * @return
     */
    private boolean isEmpty() {
        if (TextUtils.isEmpty(user_name.getText()) && TextUtils.isEmpty(password.getText())) {
            isEmpty = true;
            ShowToast.Short(getString(R.string.user_waring));
        } else if (TextUtils.isEmpty(user_name.getText())) {
            isEmpty = true;
            ShowToast.Short(getString(R.string.user_waring));
        } else if (TextUtils.isEmpty(password.getText())) {
            isEmpty = true;
            ShowToast.Short(getString(R.string.password_waring));
        } else if (TextUtils.isEmpty(user_name.getText()) == false && TextUtils.isEmpty(password.getText()) == false) {
            isEmpty = false;
        }
        return isEmpty;
    }

    /**
     * 密码可见
     *
     * @param view
     */
    public void visible(View view) {
        password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        password.postInvalidate();
        invisible.setVisibility(View.GONE);
        visible.setVisibility(View.VISIBLE);

    }

    /**
     * 密码不可见
     *
     * @param view
     */
    public void invisible(View view) {
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        password.postInvalidate();
        invisible.setVisibility(View.VISIBLE);
        visible.setVisibility(View.GONE);
    }

    /**
     * 登录
     *
     * @param view
     */
    public void login(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        View view1 = getLayoutInflater().inflate(R.layout.dialog_layout, null);

        TextView title = (TextView) view1.findViewById(R.id.title);
        title.setText("登录中...");
        LinearLayout ll = (LinearLayout) view1.findViewById(R.id.ll);
        ll.setVisibility(View.GONE);

        builder.setCancelable(false);
        dialog = builder.show();
        dialog.getWindow().setContentView(view1);

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                if (isEmpty() == true) {//用户名或密码为空
                    dialog.dismiss();
                } else {

                    param = "user[user_id]=" + user_name.getText()
                            + "&user[user_pass]=" + password.getText()
                            + "&token=" + NetUrlAddress.token;

                    if (NetUtils.isConnected(LoginActivity.this)) {//是否联网
                        result = HttpUtils.checkUser(NetUrlAddress.login_url, param);
                        Log.i("TAG", "result.get(1)=" + result);
                        if (200 == result.get(1)) {
                            //登录成功保存用户名密码
                            saveUser(user_name.getText().toString(), password.getText().toString(), result.get(2));
                            if (isFirstIn) {
                                new MyThread().start();
                            }else {
                                dialog.dismiss();
                                intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                LoginActivity.this.finish();
                            }

                        } else if (404 == result.get(1)) {
                            dialog.dismiss();
                            ShowToast.Short("该用户id不存在");
                        } else if (500 == result.get(1)) {
                            dialog.dismiss();
                            ShowToast.Short("用户名或者密码不正确");
                        } else if (100 == result.get(1)) {
                            dialog.dismiss();
                            ShowToast.Short("联网超时...");
                        }

                    } else {

                        dialog.dismiss();

                        Message message=new Message();
                        message.what=10086;
                        handler.sendMessage(message);

//                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                        View view1 = getLayoutInflater().inflate(R.layout.dialog_layout, null);
//
//                        TextView title = (TextView) view1.findViewById(R.id.title);
//                        title.setText("没有网络连接，是否前往设置？");
//                        TextView delete = (TextView) view1.findViewById(R.id.delete);
//                        delete.setVisibility(View.GONE);
//                        TextView line = (TextView) view1.findViewById(R.id.line1);
//                        line.setVisibility(View.GONE);
//                        TextView add = (TextView) view1.findViewById(R.id.add);
//                        add.setText("确认");
//                        TextView edit = (TextView) view1.findViewById(R.id.edit);
//                        edit.setText("取消");
//                        builder.setCancelable(false);
//                        dialog = builder.show();
//                        dialog.getWindow().setContentView(view1);
//
//                        //新建
//                        add.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                                NetUtils.openSetting(LoginActivity.this);
//                            }
//                        });
//                        //编辑
//                        edit.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
                    }
                }
                Looper.loop();
            }
        }.start();
    }


    /**
     * 获取返回数据,菜单
     */
    class MyThread extends Thread {

        public void run() {

            String param = "user_auth_id=" + result.get(2)
                    + "&token=" + NetUrlAddress.token;

            try {
                value = MenuLibModel.getData(NetUrlAddress.MENU_LIB_URL, param);
                L.d("menu", value);
                if (value != null) {
                    JSONArray jsonArray = new JSONArray(value);
                    MenuLibModel.saveToSql(jsonArray, LoginActivity.this);
                }
                resultCode = RESULT_SUCCESS;
                //得到返回的数据，发消息通知跳转
                Message message = new Message();
                message.what = resultCode;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
                resultCode = RESULT_FAILD;
                L.d("text", getLocalClassName() + "解析JSON出错");
                Message message = new Message();
                message.what = resultCode;
                handler.sendMessage(message);
            }
        }
    }
}
