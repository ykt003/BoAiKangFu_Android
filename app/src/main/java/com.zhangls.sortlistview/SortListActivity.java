package com.zhangls.sortlistview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenjuly.mylibrary.SpinnerLoader;
import com.zhangls.sortlistview.SideBar.OnTouchingLetterChangedListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.activity.BaseActivity;
import me.zhangls.rilintech.cache.ACache;
import me.zhangls.rilintech.constants.NetUrlAddress;
import me.zhangls.rilintech.model.IcdInfo;
import me.zhangls.rilintech.model.IcfInfo;
import me.zhangls.rilintech.utils.HttpUtils;
import me.zhangls.rilintech.utils.NetUtils;

/**
 * create by zsn on 15/12/08
 */
public class SortListActivity extends BaseActivity {
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;
    //进度条
    private SpinnerLoader mSpinnerLoader;
    public static boolean isICDLoaded;
    public static boolean isICFLoaded;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList = new ArrayList<SortModel>();
    //private List<IcdInfo> mIcdDataList=new ArrayList<IcdInfo>();

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    //不同字母表
    private int flag;
    //缓存
    private ACache mCache;
    private ArrayList<IcdInfo> mIcdInfoListCache;
    private ArrayList<IcfInfo> mIcfInfoListCache;
    private int user_auth_id;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1234:
                    mSpinnerLoader.setVisibility(View.GONE);
                    adapter = new SortAdapter(SortListActivity.this, SourceDateList);
                    sortListView.setAdapter(adapter);
                    break;
                case 4321:
                    mSpinnerLoader.setVisibility(View.GONE);
                    adapter = new SortAdapter(SortListActivity.this, SourceDateList);
                    sortListView.setAdapter(adapter);
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_list_main);

        flag = getIntent().getIntExtra("flag", 0);
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        user_auth_id = preferences.getInt("use_id", 2);
        initViews();

    }

    private void initViews() {

        mSpinnerLoader = (SpinnerLoader) findViewById(R.id.spinnerloader);
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                //Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
                Intent intent_1 = new Intent();
                intent_1.putExtra("result", ((SortModel) adapter.getItem(position)).getName());
                SortListActivity.this.setResult(RESULT_OK, intent_1);
                SortListActivity.this.finish();
            }
        });

        mCache = ACache.get(this);

        if (flag == 1) {

         loadIcdDataByCache();
        }

        if (flag == 2) {

          loadIcfDataByCache();
        }
        if (flag == 3) {
            loadIcdDataByNetworkType();
        }
        if (flag == 4) {
            loadIcfDataByNetworkType();
        }
        //获取数据源
        //SourceDateList = filledData(getResources().getStringArray(R.array.date));

//		boolean flag=true;
//		long startMills = System.currentTimeMillis();
//		while(flag){
//			long nowMills = System.currentTimeMillis();
//			if(nowMills-startMills>=3000){
//
//				flag=false;
//			}
//		}
//		// 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);

        adapter = new SortAdapter(SortListActivity.this, SourceDateList);
        sortListView.setAdapter(adapter);

        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                mSpinnerLoader.setVisibility(View.VISIBLE);
                mSpinnerLoader.setPointcolor(Color.BLUE);
            }

            @Override
            public void afterTextChanged(Editable s) {

                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }
        });
    }

    /**
     * 加载icd网络数据
     */
    private void loadIcdDataByNetworkType() {
        mSpinnerLoader.setVisibility(View.VISIBLE);
        mSpinnerLoader.setPointcolor(Color.BLUE);

        if (NetUtils.isConnected(SortListActivity.this)) {
            new myIcdAsyncTask().execute();
//			RequestParams params=new RequestParams();
//			params.put("token", "9ba2a51a1458421c245bace706ec159faf88247327aa2f607a1c8dcdb7f33673119bf62e32b" +
//							"d29723df40542944a5c2de8e50c089e61cee3f242fe4cab343b80");
//
//			HttpUtil.get(NetUrlAddress.ICD_DATA_URL,params,new JsonHttpResponseHandler(){
//				@Override
//				public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//					ArrayList icdInfoList=IcdInfo.parse(response);
//					Log.i("icd", "mIcdDataList.size()=" +  icdInfoList.size());
//					String [] data=new String[icdInfoList.size()];
//					for(int i=0;i<icdInfoList.size();i++){
//						IcdInfo icdInfo = (IcdInfo) icdInfoList.get(i);
//						String name = icdInfo.getTerm();
//						data[i]=name;
//						Log.i("icd","data[i]="+data[i]);
//					}
//					SourceDateList =filledData(data);
//					Log.i("icd", "SourceDateList.size()" + SourceDateList.size());
//					mSpinnerLoader.setVisibility(View.GONE);
//					// 根据a-z进行排序源数据
//					Collections.sort(SourceDateList, pinyinComparator);
//
//					adapter = new SortAdapter(SortListActivity.this, SourceDateList);
//					sortListView.setAdapter(adapter);
//					//添加至缓存
//					mCache.put("icdJSONArray",response);
//				}
//			});

//			executeRequest(new Request4Icd(NetUrlAddress.ICD_DATA_URL, new Response.Listener<ArrayList<IcdInfo>>() {
//				@Override
//				public void onResponse(ArrayList<IcdInfo> response) {
//					mIcdDataList.addAll(response);
//					Log.i("icd","mIcdDateList=="+mIcdDataList);
//				}
//			}, new Response.ErrorListener() {
//				@Override
//				public void onErrorResponse(VolleyError error) {
//					Log.i("icd","error="+error);
//				}
//			}));
        } else {
            Toast.makeText(this, "无网络，请检查网络连接", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 加载icd缓冲数据
     */
    private void loadIcdDataByCache() {

        mSpinnerLoader.setVisibility(View.VISIBLE);
        mSpinnerLoader.setPointcolor(Color.BLUE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONArray icdJSONArray = mCache.getAsJSONArray("icdJSONArray");
                List<IcdInfo> icdList = getIcdCacheDatas(icdJSONArray);
                String[] data = new String[icdList.size()];
                for (int i = 0; i < icdList.size(); i++) {
                    IcdInfo icdInfo = (IcdInfo) icdList.get(i);
                    String name = icdInfo.getTerm();
                    data[i] = name;
                }
                SourceDateList = filledData(data);
               // 根据a-z进行排序源数据
                Collections.sort(SourceDateList, pinyinComparator);
                Message msg = new Message();
                msg.what = 1234;
                handler.sendMessage(msg);
            }
        }).start();
    }

    /**
     * 解析icd数据
     * @param jsonArr
     * @return
     */
    private List<IcdInfo> getIcdCacheDatas(JSONArray jsonArr) {
        mIcdInfoListCache = IcdInfo.parseCache(jsonArr);
        return mIcdInfoListCache;
    }

    /**
     * 加载网络Icf数据
     */
    private void loadIcfDataByNetworkType() {
        mSpinnerLoader.setVisibility(View.VISIBLE);
        mSpinnerLoader.setPointcolor(Color.BLUE);

        if (NetUtils.isConnected(SortListActivity.this)) {
            new myIcfAsyncTask().execute();
//			RequestParams params = new RequestParams();
//			params.put("token", "9ba2a51a1458421c245bace706ec159faf88247327aa2f607a1c8dcdb7f33673119bf62e32b" +
//					"d29723df40542944a5c2de8e50c089e61cee3f242fe4cab343b80");
//
//			HttpUtil.get(NetUrlAddress.ICF_DATA_URL, params, new JsonHttpResponseHandler() {
//				@Override
//				public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//					ArrayList icfInfoList = IcfInfo.parse(response);
//					Log.i("icf", "mIcfDataList.size()=" + icfInfoList.size());
//					String[] data = new String[icfInfoList.size()];
//					for (int i = 0; i < icfInfoList.size(); i++) {
//						IcfInfo icfInfo = (IcfInfo) icfInfoList.get(i);
//						String name = icfInfo.getTerm();
//						data[i] = name;
//						Log.i("icf", "data[i]=" + data[i]);
//					}
//					SourceDateList = filledData(data);
//					Log.i("icf", "SourceDateList.size()" + SourceDateList.size());
//					mSpinnerLoader.setVisibility(View.GONE);
//					// 根据a-z进行排序源数据
//					Collections.sort(SourceDateList, pinyinComparator);
//
//					adapter = new SortAdapter(SortListActivity.this, SourceDateList);
//					sortListView.setAdapter(adapter);
//					//添加至缓存
//					mCache.put("icfJSONArray", response);
//				}
//			});
        } else {
            Toast.makeText(this, "无网络，请检查网络连接", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 加载icf缓冲数据
     */
    private void loadIcfDataByCache() {
        mSpinnerLoader.setVisibility(View.VISIBLE);
        mSpinnerLoader.setPointcolor(Color.BLUE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONArray icfJSONArray = mCache.getAsJSONArray("icfJSONArray");
                List<IcfInfo> icfList = getIcfCacheDatas(icfJSONArray);
                String[] data = new String[icfList.size()];
                for (int i = 0; i < icfList.size(); i++) {
                    IcfInfo icfInfo = (IcfInfo) icfList.get(i);
                    String name = icfInfo.getTerm();
                    data[i] = name;
                }
                SourceDateList = filledData(data);
                // 根据a-z进行排序源数据
                Collections.sort(SourceDateList, pinyinComparator);
                Message msg = new Message();
                msg.what = 4321;
                handler.sendMessage(msg);
            }
        }).start();
    }

    /**
     * 解析icf数据
     * @param jsonArr
     * @return
     */
    private List<IcfInfo> getIcfCacheDatas(JSONArray jsonArr) {
        mIcfInfoListCache = IcfInfo.parseCache(jsonArr);
        return mIcfInfoListCache;
    }

    /**
     * 为ListView填充数据
     *
     * @param data
     * @return
     */

    private List<SortModel> filledData(String[] data) {

        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < data.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(data[i]);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(data[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(final String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {

            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 ||
                        characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        List<SortModel> finalFilterDateList = filterDateList;
        // 根据a-z进行排序
        Collections.sort(finalFilterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
        mSpinnerLoader.setVisibility(View.GONE);
    }

    /**
     * 异步加载icd
     */
    private class myIcdAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String params = "token=" + NetUrlAddress.token + "&&user_auth_id=" + user_auth_id;
            String response = HttpUtils.doPost(NetUrlAddress.ICD_DATA_URL, params);
            Log.d("oo", "response==" + response);
            ArrayList icdInfoList = null;
            try {
                icdInfoList = IcdInfo.parse(new JSONArray(response));

                Log.i("icd", "mIcdDataList.size()=" + icdInfoList.size());
                String[] data = new String[icdInfoList.size()];
                for (int i = 0; i < icdInfoList.size(); i++) {
                    IcdInfo icdInfo = (IcdInfo) icdInfoList.get(i);
                    String name = icdInfo.getTerm();
                    data[i] = name;
                    Log.i("icd", "data[i]=" + data[i]);
                }
                SourceDateList = filledData(data);
                Log.i("icd", "SourceDateList.size()" + SourceDateList.size());
                // 根据a-z进行排序源数据
                Collections.sort(SourceDateList, pinyinComparator);
                //添加至缓存
                mCache.put("icdJSONArray", new JSONArray(response));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response.length() > 0) {
                mSpinnerLoader.setVisibility(View.GONE);
                adapter = new SortAdapter(SortListActivity.this, SourceDateList);
                sortListView.setAdapter(adapter);
                isICDLoaded=true;
            }

        }
    }

    /**
     * 异步加载icf
     */
    private class myIcfAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String params = "token=" + NetUrlAddress.token + "&&user_auth_id=" + user_auth_id;
            String response = HttpUtils.doPost(NetUrlAddress.ICF_DATA_URL, params);
            Log.d("oo", "response==" + response);
            ArrayList icfInfoList = null;
            try {
                icfInfoList = IcfInfo.parse(new JSONArray(response));

                Log.i("icf", "mIcfDataList.size()=" + icfInfoList.size());
                String[] data = new String[icfInfoList.size()];
                for (int i = 0; i < icfInfoList.size(); i++) {
                    IcfInfo icfInfo = (IcfInfo) icfInfoList.get(i);
                    String name = icfInfo.getTerm();
                    data[i] = name;
                    Log.i("icf", "data[i]=" + data[i]);
                }
                SourceDateList = filledData(data);
                Log.i("icf", "SourceDateList.size()" + SourceDateList.size());

                // 根据a-z进行排序源数据
                Collections.sort(SourceDateList, pinyinComparator);
                //添加至缓存
                mCache.put("icfJSONArray", new JSONArray(response));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response.length() > 0) {
                mSpinnerLoader.setVisibility(View.GONE);
                adapter = new SortAdapter(SortListActivity.this, SourceDateList);
                sortListView.setAdapter(adapter);
                isICFLoaded=true;
            }

        }
    }

}