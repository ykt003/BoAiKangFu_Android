package me.zhangls.rilintech.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.utils.L;
import me.zhangls.rilintech.utils.ShowToast;

/**
 * Created by YANG on 15/12/8.
 */
public class PasswordChangeActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 超时时间
     */
    private static final int TIMEOUT_IN_MILLIONS = 5000;
    /**
     * 返回键
     */
    private ImageView back_image_view;
    /**
     * 原密码
     */
    private EditText oldPassword;
    /**
     * 新密码
     */
    private EditText newPassword;
    /**
     * 确认密码
     */
    private EditText confirmPassword;
    /**
     * 保存密码
     */
    private TextView savePassword;
    private SharedPreferences preferences;
    /**
     * 已经保存的用户名
     */
    private String user_name;
    /**
     * 已经保存的用户密码
     */
    private String user_password;
    /**
     * 已经保存的用户ID
     */
    private int user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSharedPreferencesData();

        initView();

    }

    /**
     * 获取当前登录用户和当前患者的ID
     */
    private void getSharedPreferencesData() {

        preferences = getSharedPreferences("user", MODE_PRIVATE);
        user_id = preferences.getInt("use_id", 2);
        user_name = preferences.getString("user_name", "");
        user_password = preferences.getString("password", "");

    }

    /**
     * 初始化控件
     */
    private void initView() {

        back_image_view = (ImageView) findViewById(R.id.back);
        back_image_view.setOnClickListener(this);
        oldPassword = (EditText) findViewById(R.id.old_password);
        newPassword = (EditText) findViewById(R.id.new_password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        savePassword = (TextView) findViewById(R.id.save);
        savePassword.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.save:
                if (comparePassword()) {
                    new MyAsyncTask().execute();
                } else {

                }
                break;
        }
    }

    /**
     * 比较新旧密码
     */
    private boolean comparePassword() {
        String oldPass = oldPassword.getText().toString();
        String newPass = newPassword.getText().toString();
        String confirmPass = confirmPassword.getText().toString();

        if (oldPass.equals(user_password)) {
            if (newPass.equals(confirmPass)) {
                return true;
            } else {
                ShowToast.Short("两次输入密码不一致");
                return false;
            }
        } else {
            ShowToast.Short("原密码错误");
            return false;
        }


    }

    /**
     * 后台联网更新密码
     */
    private class MyAsyncTask extends AsyncTask<String, String, Integer> {

        String errorMessage = "网络连接出错";
        String errorcode = "-1";

        String param = "auth_id=" + user_id
                + "&password[old]=" + oldPassword.getText()
                + "&password[new]=" + newPassword.getText()
                + "&token=" + NetUrlAddress.token;

        @Override
        protected Integer doInBackground(String... params) {

            String response = doPost(NetUrlAddress.UpdatePassword, param);

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject object = jsonObject.getJSONObject("msg");
                errorMessage = object.getString("errorMessage");
                errorcode = object.getString("errorcode");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return Integer.parseInt(errorcode);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            if (integer == -1) {
                ShowToast.Short(errorMessage);
            } else if (integer == 200) {
                ShowToast.Short(errorMessage);
                setSharedPreferencesData();
                PasswordChangeActivity.this.finish();
            } else if (integer == 500) {
                ShowToast.Short(errorMessage);
            } else if (integer == 100) {
                ShowToast.Short("密码修改出错");
            }
        }
    }


    public static String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        HttpURLConnection conn = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            conn.disconnect();
        }

        return result;
    }

    /**
     * 设置用户信息为空
     */
    private void setSharedPreferencesData() {
        preferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //editor.putInt("use_id", -1);
        //editor.putString("user_name", "");
        editor.putString("password", newPassword.getText().toString());

        editor.commit();
    }

}
