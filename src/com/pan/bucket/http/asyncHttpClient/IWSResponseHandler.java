package com.pan.bucket.http.asyncHttpClient;

import org.json.JSONObject;

public interface IWSResponseHandler {

    public void onPrepare();
    
	public void onStart();

	public void onSuccess(int statusCode, JSONObject response);
	
	public void onSuccess(int statusCode, String response);

	public void onFailure(Throwable error, String content);

	public void onFinish();

}
