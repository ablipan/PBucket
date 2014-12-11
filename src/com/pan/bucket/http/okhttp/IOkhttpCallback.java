package com.pan.bucket.http.okhttp;


public interface IOkhttpCallback {
	public void onResponse(String json);
	public void onFailure(String  error);
}
