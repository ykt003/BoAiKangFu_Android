package me.zhangls.rilintech.net;

/**
 *
 * Created by zhaokaiqiang on 15/4/8.
 */
//public class Request4PatientInfo extends Request<ArrayList<PatientInfo>> {
//
//	private static final String TAG = "me.zhangls.rilintech.net.request4PatientInfo";
//	private Response.Listener<ArrayList<PatientInfo>> listener;
//
//	public Request4PatientInfo(String url, Response.Listener<ArrayList<PatientInfo>> listener,
//							   Response.ErrorListener errorListener) {
//		super(Method.GET, url, errorListener);
//		this.listener = listener;
//	}
//	@Override
//	protected Response<ArrayList<PatientInfo>> parseNetworkResponse(NetworkResponse response) {
//
//		try {
//			String resultStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//			Log.i(TAG,"resultStr="+resultStr);
//			/*JSONObject resultObj = new JSONObject(resultStr);
//			JSONArray postsArray = resultObj.optJSONArray("posts");*/
//			JSONArray  postsArray=new JSONArray(resultStr);
//			return Response.success(PatientInfo.parse(postsArray), HttpHeaderParser.parseCacheHeaders(response));
//		} catch (Exception e) {
//			e.printStackTrace();
//			return Response.error(new ParseError(e));
//		}
//	}
//
//
//
//	@Override
//	protected void deliverResponse(ArrayList<PatientInfo> response) {
//		listener.onResponse(response);
//	}
//
//
//
//
//}
