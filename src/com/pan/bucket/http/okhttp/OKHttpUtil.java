package com.pan.bucket.http.okhttp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import android.content.Context;

import com.pan.bucket.exception.ExceptionUtils;
import com.pan.bucket.log.Logger;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * OKHttp工具类
 * 
 * @author Pan
 * 
 */
public class OKHttpUtil {
	private static final String TAG = "Http Request";

	private static OkHttpClient client;
	private static Context context;
	
	public static final MediaType JSON = MediaType
			.parse("application/json; charset=utf-8");
	
//	public static Gson GSON = new Gson();
	public static void initHttpUtils(Context contextInit) {
		context = contextInit;
		if (null == client) {
			client = new OkHttpClient();
			client.setConnectTimeout(10, TimeUnit.SECONDS); // 连接超时10秒
			client.setWriteTimeout(10, TimeUnit.SECONDS); // 写超时10秒
			client.setReadTimeout(30, TimeUnit.SECONDS); // 读超时30秒
		}
	}

	/**
	 * get请求
	 * @param url 请求url
	 * @param callback 回调函数
	 */
	public Call jsonGet(String url,final IOkhttpCallback callback) {
		Call call = null;
		try {
			Request request = new Request.Builder().url(url).build();
			call = client.newCall(request);
			call.enqueue(new Callback() {
				@Override
				public void onResponse(Response response) throws IOException {
					if (response.code() == 200) {
						callback.onResponse(response.body().string());
					}else
					{
						callback.onFailure("Unexpected code " + response);
						Logger.e(TAG, "Unexpected code " + response);
					}
				}
				@Override
				public void onFailure(Request request, IOException e) {
					Logger.e(TAG, ExceptionUtils.getDetailInfo(e));
				}
			});
		} catch (Exception e) {
			Logger.e(TAG, ExceptionUtils.getDetailInfo(e));
		}
		return call;
	}

	/**
	 * 发送Post请求
	 * @param url 请求url
	 * @param jsonString 请求json String
	 * @param callback 回调函数
	 */
	public static Call jsonPost(String url, String jsonString ,final IOkhttpCallback callback) {
		Call call = null;
		try {
			RequestBody body = RequestBody.create(JSON, jsonString);
			Request request = new Request
								.Builder()
								.post(body)
								.url(url)
								.build();
			call = client.newCall(request);
			call.enqueue(new Callback() {
				@Override
				public void onResponse(Response response) throws IOException {
					if (response.code() == 200) {
						callback.onResponse(response.body().string());
					}else
					{
						callback.onFailure("Unexpected code " + response);
						Logger.e(TAG, "Unexpected code " + response);
					}
				}
				@Override
				public void onFailure(Request request, IOException e) {
					Logger.e(TAG, ExceptionUtils.getDetailInfo(e));
				}
			});
		} catch (Exception e) {
			Logger.e(TAG, ExceptionUtils.getDetailInfo(e));
		}
		return call;
	}
}
