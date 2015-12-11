package me.zhangls.rilintech.net;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * @author zhaokaiqiang
 * @ClassName: com.drd.piaojubao.utils.JSONParser
 * @Description: Json对象转换器
 * @date 2014-8-27 上午9:44:23
 */
public class JSONParser {

	protected static Gson gson = new Gson();

	public static String toString(Object obj) {
		return gson.toJson(obj);
	}

	/**
	 * @param type 类型反射(Class<?>)或反射令牌(TypeToken)
	 * @return Object
	 * @throws
	 * @Description: 将标准JSON字符串反序列化为对象
	 */
	public static Object toObject(String jsonString, Object type) {
		jsonString = jsonString.replace("&nbsp", "");
		jsonString = jsonString.replace("﹠nbsp", "");
		jsonString = jsonString.replace("nbsp", "");
		jsonString = jsonString.replace("&amp;", "");
		jsonString = jsonString.replace("&amp", "");
		jsonString = jsonString.replace("amp", "");
		if (type instanceof Type) {
			try {
				return gson.fromJson(jsonString, (Type) type);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return null;
			}
		} else if (type instanceof Class<?>) {
			try {
				return gson.fromJson(jsonString, (Class<?>) type);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			throw new RuntimeException("只能是Class<?>或者通过TypeToken获取的Type类型");
		}
	}
}
