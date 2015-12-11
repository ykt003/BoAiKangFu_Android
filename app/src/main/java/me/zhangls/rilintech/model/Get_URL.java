package me.zhangls.rilintech.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import me.zhangls.rilintech.utils.L;

public class Get_URL {
    public static String executeHttpPost() {
        String result = null;
        URL url = null;
        HttpURLConnection connection = null;
        InputStreamReader in = null;
        try {
            url = new URL("http://192.168.0.123:3003/app_scale/index/1.json?" +
                    "token=9ba2a51a1458421c245bace706ec159faf88247327aa2f607a1c8dcdb7" +
                    "f33673119bf62e32bd29723df40542944a5c2de8e50c089e61cee3f242fe4cab343b80" +
                    "&patient_info_id=131" +
                    "&menu_lib_id=82" +
                    "&user_auth_id=2");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Charset", "utf-8");
            DataOutputStream dop = new DataOutputStream(
                    connection.getOutputStream());
            dop.writeBytes("token=alexzhou");
            dop.flush();
            dop.close();

            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }


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


    public static ArrayList<Model_one> setupViews(String strResult) {
        ArrayList<Model_one> list = null;
        try {
            JSONObject jsonObj = new JSONObject(strResult);
            JSONArray jarray = jsonObj.getJSONArray("list");
            list = new ArrayList<>();
            ArrayList<Model_two> list11 = null;
            ArrayList<Model_three> list1 = null;
            ArrayList<Model_four> list2 = null;

            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jsonObject = jarray.getJSONObject(i);

                String groupid = jsonObject.getString("groupid");
                String evaluation_person = jsonObject.getString("evaluation_person");
                String evaluation_time_note = jsonObject.getString("evaluation_time_note");
                String evaluation_time = jsonObject.getString("evaluation_time");
                JSONArray ja = jsonObject.getJSONArray("header_questions");
                boolean has_sum = jsonObject.getBoolean("has_sum");
                boolean has_grade = jsonObject.getBoolean("has_grade");

                list11 = new ArrayList<>();

                Model_one a3 = new Model_one();

                for (int j = 0; j < ja.length(); j++) {


                    String header = ja.getJSONObject(j).getString("header");
                    JSONArray jar = ja.getJSONObject(j).getJSONArray("questions");


                    Model_two a2 = new Model_two();

                    list1 = new ArrayList<>();

                    for (int k = 0; k < jar.length(); k++) {
                        boolean in_sum=false;
                        String id="";
                        JSONArray jsy = jar.getJSONObject(k).getJSONArray("options");
                        String question = jar.getJSONObject(k).getString("question");
                        String value = jar.getJSONObject(k).getString("value");
                        try{
                            id=jar.getJSONObject(k).getString("id");
                        }catch (Exception e){
                            L.d("aa", "错误id＝＝"+e.toString());
                        }

                        try {
                            in_sum = jar.getJSONObject(k).getBoolean("in_sum");
                        }catch (Exception e){
                            L.d("aa", "错误in_sum＝＝"+e.toString());
                        }




                        Model_three a1 = new Model_three();

                        list2 = new ArrayList<>();

                        for (int l = 0; l < jsy.length(); l++) {


                            String content = jsy.getJSONObject(l).getString("content");
                            String value1 = jsy.getJSONObject(l).getString("value");

                            Model_four a = new Model_four();
                            a.setContent(content);
                            a.setValue(value1);
                            list2.add(a);
                        }//four
                        a1.setOptions(list2);
                        L.d("aa", "id==-----" + id);
                        a1.setId(id);
                        a1.setQuestion(question);
                        a1.setValue(value);
                        L.d("aa", "in_sum==-----" + in_sum);
                        a1.setIn_sum(in_sum);
                        list1.add(a1);//three
                    }
                    a2.setHeader(header);
                    a2.setQuestions(list1);
                    list11.add(a2);
                }
                a3.setGroupid(groupid);
                a3.setEvaluation_person(evaluation_person);
                a3.setEvaluation_time_note(evaluation_time_note);
                a3.setEvaluation_time(evaluation_time);
                a3.setHas_sum(has_sum);
                a3.setHas_grade(has_grade);
                a3.setHeader_questions(list11);
                list.add(a3);

            }
        } catch (JSONException e) {
            System.out.println("Json parse error");
            e.printStackTrace();
        }
        return list;

    }


}
