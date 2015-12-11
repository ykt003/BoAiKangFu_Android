package me.zhangls.rilintech.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import me.zhangls.rilintech.utils.L;

/**
 * Created by YANG on 15/11/25.
 */
public class NormalTableFinalModel implements Parcelable, Serializable {

    private String id;
    private String isEmpty;
    private String model_name;
    private ArrayList<NormalTableModel> list_model;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(isEmpty);
        dest.writeString(model_name);
        dest.writeList(list_model);
    }


    public static final Creator<NormalTableFinalModel> CREATOR = new Creator<NormalTableFinalModel>() {
        @Override
        public NormalTableFinalModel createFromParcel(Parcel in) {

            NormalTableFinalModel model = new NormalTableFinalModel();
            model.id = in.readString();
            model.isEmpty = in.readString();
            model.model_name = in.readString();
            model.list_model = in.readArrayList(NormalTableModel.class.getClassLoader());

            return model;
        }

        @Override
        public NormalTableFinalModel[] newArray(int size) {
            return new NormalTableFinalModel[size];
        }
    };


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(String isEmpty) {
        this.isEmpty = isEmpty;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String table_name) {
        this.model_name = table_name;
    }

    public ArrayList<NormalTableModel> getList_model() {
        return list_model;
    }

    public void setList_model(ArrayList<NormalTableModel> list_model) {
        this.list_model = list_model;
    }


    /**
     * delete/update/create操作
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static int doPost(String url, String param) {

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        int r = 0;
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

            r = conn.getResponseCode();

        } catch (Exception e) {
            e.printStackTrace();
            r = 100;
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
        return r;
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
    public static ArrayList<NormalTableFinalModel> parseJson(String strResult, ArrayList<NormalTableModel> list_info) {

        ArrayList<NormalTableModel> list_model = null;
        ArrayList<NormalTableFinalModel> list_final_model = null;
        NormalTableFinalModel model = null;

        try {
            JSONObject jsonObject = new JSONObject(strResult);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            list_final_model = new ArrayList<>();
            if (!jsonArray.toString().equals("[]")) {
                for (int i = jsonArray.length()-1; i >= 0; i--) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    list_model = new ArrayList<>();

                    try {
                        list_model = deepCopy(list_info);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    for (int j = 0; j < list_model.size(); j++) {
                        list_model.get(j).setMiddleText(object.getString(list_model.get(j).getName()));
                    }
                    model = new NormalTableFinalModel();
                    model.setIsEmpty("no");
                    model.setModel_name(jsonObject.getString("model_name"));
                    model.setId(jsonArray.getJSONObject(i).getString("id"));
                    model.setList_model(list_model);

                    list_final_model.add(model);
                }
            } else {
                model = new NormalTableFinalModel();
                model.setIsEmpty("yes");
                model.setModel_name(jsonObject.getString("model_name"));
                list_model = new ArrayList<>();
                try {
                    list_model = deepCopy(list_info);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                model.setList_model(list_model);
                list_final_model.add(model);
            }

        } catch (Exception e) {
            L.d("yy", "NormalTableFinalModel Parse Error");
            e.printStackTrace();
        }

        return list_final_model;
    }

    /**
     * 递归深克隆
     *
     * @param src
     * @param dest
     */
    public static void copy(List src, List dest) {
        for (int i = 0; i < src.size(); i++) {
            Object obj = src.get(i);
            if (obj instanceof List) {
                dest.add(new ArrayList());
                copy((List) obj, (List) ((List) dest).get(i));
            } else {
                dest.add(obj);
            }
        }
    }

    /**
     * 序列化/反序列化 深克隆
     *
     * @param src
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ArrayList deepCopy(ArrayList src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        ArrayList dest = (ArrayList) in.readObject();
        return dest;
    }

}
