package me.zhangls.rilintech.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import me.zhangls.rilintech.utils.L;

/**
 * Created by YANG on 15/12/1.
 */
public class TableListViewShowModel implements Parcelable {

    private String menuLibId;
    private String name;
    private int flag;

    public String getMenuLibId() {
        return menuLibId;
    }

    public void setMenuLibId(String menuLibId) {
        this.menuLibId = menuLibId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }


    public static final Creator<TableListViewShowModel> CREATOR = new Creator<TableListViewShowModel>() {
        @Override
        public TableListViewShowModel createFromParcel(Parcel in) {

            TableListViewShowModel model = new TableListViewShowModel();

            model.menuLibId = in.readString();
            model.name = in.readString();
            model.flag = in.readInt();

            return model;
        }

        @Override
        public TableListViewShowModel[] newArray(int size) {
            return new TableListViewShowModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(menuLibId);
        dest.writeString(name);
        dest.writeInt(flag);
    }


    /**
     * 获取网络数据
     *
     * @param url
     * @param param
     * @return
     */
    public static String getData(String url, String param) {

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
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

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
     * 解析数据JSON
     *
     * @param strResult
     * @return
     */
    public static ArrayList<TableListViewShowModel> parseJson(String strResult) {

        ArrayList<TableListViewShowModel> list_model = null;
        TableListViewShowModel model = null;
        int resultCode;
        String resultMessage = "";

        try {
            JSONObject jsonObject = new JSONObject(strResult);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            JSONObject object = jsonObject.getJSONObject("msg");

            resultCode = object.getInt("errorcode");
            resultMessage = object.getString("errorMessage");

            if (200 == resultCode) {
                list_model = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    model = new TableListViewShowModel();

                    model.setMenuLibId(jsonArray.getJSONObject(i).getString("id"));
                    model.setName(jsonArray.getJSONObject(i).getString("name"));
                    model.setFlag(jsonArray.getJSONObject(i).getInt("flag"));

                    list_model.add(model);
                }
            }else {
                L.d("yy", "ParseJSON Error");
            }

        } catch (Exception e) {
            L.d("yy", resultMessage);
            e.printStackTrace();
        }

        return list_model;
    }

}
