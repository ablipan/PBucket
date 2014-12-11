/**
 * author :  lipan
 * filename :  ConnectionChangeReceiver.java
 * create_time : 2014年5月27日 下午3:27:22
 */
package com.pan.bucket.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.pan.bucket.utils.CheckPhoneStatus;

/**
 * @author : lipan
 * @create_time : 2014年5月27日 下午3:27:22
 * @desc : 监听手机网络变化广播, 转发为本地广播
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public class ConnectionChangeReceiver extends BroadcastReceiver
{

    public static final String CONNECTION_OK = "connectionIsOk";
    
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //网络状态改变，并且为可用状态时,发送本地广播
        if(CheckPhoneStatus.checkNetWorkStatus(context))
        {
            LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(CONNECTION_OK));
        }
    }

}
