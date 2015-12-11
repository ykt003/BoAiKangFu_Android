package me.zhangls.rilintech.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.utils.HttpUtils;
import me.zhangls.rilintech.utils.NetUtils;
import me.zhangls.rilintech.utils.ShowToast;

/**
 * Created by rilintech on 15/8/5.
 */
public class WelcomeActivity extends Activity {

    private SharedPreferences preferences;
    private String user_name;
    private String user_password;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        getUserAndPassword();
        Handler handler = new Handler();
        handler.postDelayed(new Loading(), 3000);

    }

    private void getUserAndPassword() {
        preferences = getSharedPreferences("user", MODE_PRIVATE);
        user_id = preferences.getInt("use_id", 2);
        user_name = preferences.getString("user_name", "");
        user_password = preferences.getString("password", "");
    }


    class Loading implements Runnable {
        @Override
        public void run() {

            new MyAsyncTask().execute();
        }
    }

    /**
     * 后台联网更新密码
     */
    private class MyAsyncTask extends AsyncTask<String, String, Integer> {


        String param = "user[user_id]=" + user_name
                + "&user[user_pass]=" + user_password
                + "&token=" + NetUrlAddress.token;

        @Override
        protected Integer doInBackground(String... params) {
            HashMap<Integer, Integer> result = null;
            if (NetUtils.isConnected(WelcomeActivity.this)) {//是否联网
                result = HttpUtils.checkUser(NetUrlAddress.login_url, param);
            }else {
                result.put(1,-1);
            }

            return result.get(1);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            if (-1 == integer) {
                ShowToast.Short("未连接网络");
                startActivity(new Intent(getApplication(), LoginActivity.class));
            } else if (200 == integer) {
                startActivity(new Intent(getApplication(), MainActivity.class));
            } else {
                ShowToast.Short("用户名或者密码不正确");
                startActivity(new Intent(getApplication(), LoginActivity.class));
            }
            WelcomeActivity.this.finish();
        }
    }
}
