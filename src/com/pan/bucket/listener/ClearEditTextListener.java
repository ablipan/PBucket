/**
 * author :  lipan
 * filename :  ClearEditTextListener.java
 * create_time : 2014年9月10日 下午5:05:16
 */
package com.pan.bucket.listener;

import android.view.MotionEvent;
import android.view.View;

/**
 * @author : lipan
 * @create_time : 2014年9月10日 下午5:05:16
 * @desc : 带清除按钮文本框监听类
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public abstract class ClearEditTextListener
{
    public void afterTextChanged(String s)// 文本改变事件
    {
    } 
    public void onTouchEvent(MotionEvent event)// 触摸事件
    {
    }
    public void onFocusChange(View v, boolean hasFocus) // 焦点改变事件
    {
        
    }
    public void onFocus() // 获得焦点改变事件
    {
    }
}
