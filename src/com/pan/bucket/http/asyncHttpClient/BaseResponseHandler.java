package com.pan.bucket.http.asyncHttpClient;

import org.json.JSONObject;

import android.content.Context;

import com.pan.bucket.R;
import com.pan.bucket.customviews.CommLoading;

public class BaseResponseHandler implements IWSResponseHandler
{

    private Context context;
    private Boolean hasLoading = false; //默认无loading
    private Integer loadingString = R.string.loading_text; //默认loading文字
    private Boolean canCancelReq = false; //默认loading框不能取消

    public BaseResponseHandler()
    {
    }

    public BaseResponseHandler(Context context, Integer loadingString, Boolean canCancelReq)
    {
        this.context = context;
        if (null!=loadingString)
        this.loadingString = loadingString;
        if (null!=canCancelReq)
        this.canCancelReq = canCancelReq;
        hasLoading = true;
    }


    @Override
    public void onPrepare()
    {
        if (hasLoading)
        {
            if (canCancelReq)
            {
                CommLoading.show(context, loadingString);
            } else
            {
                CommLoading.showWithoutCancel(context, loadingString);
            }
        }
    }

    
    @Override
    public void onStart()
    {
    }

    @Override
    public void onSuccess(int statusCode, JSONObject response)
    {
    }

    @Override
    public void onFailure(Throwable error, String content)
    {
    }

    @Override
    public void onFinish()
    {
    }

    @Override
    public void onSuccess(int statusCode, String response)
    {
    }
}
