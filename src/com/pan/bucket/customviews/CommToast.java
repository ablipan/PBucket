/**
 * author :  lipan
 * filename :  CommonToast.java
 * create_time : 2014年4月14日 上午9:53:03
 */
package com.pan.bucket.customviews;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;

import com.github.johnpersano.supertoasts.SuperActivityToast;
import com.github.johnpersano.supertoasts.SuperCardToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.SuperToast.Animations;
import com.github.johnpersano.supertoasts.SuperToast.Type;
import com.github.johnpersano.supertoasts.util.Style;
import com.pan.bucket.R;
import com.pan.bucket.utils.ViewUtils;

/**
 * @author : lipan
 * @create_time : 2014年4月14日 上午9:53:03
 * @desc : Toast
 * @update_time :
 * @update_desc :
 * 
 * 使用方法：
 * 
 *  //显示Toast
 *  CommToast.showInfo("xxxx"); 
 * 
 *  该方法返回值为toast对象，可以对toast对象进行其他设置
 */
public class CommToast
{

//    private static Toast toast;

    // 默认显示时长
    private static final int DEF_DURATION = 800;
    
    //默认居中
    private static final int DEF_GRAVITY = Gravity.CENTER;
    
    //默认不偏移
    private static final int DEF_OFFSET = 0;

    /**
     * @param context
     *            上下文对象
     * @param info
     *            显示文本信息
     * @return
     */
    public static SuperToast showInfo(Context context, String info)
    {
        return showInfo(context, info, DEF_DURATION, DEF_GRAVITY , DEF_OFFSET , DEF_OFFSET);
    }

    /**
     * @param context
     *            上下文对象
     * @param resId
     *            资源id
     * @return
     */
    public static SuperToast showInfo(Context context, int resId)
    {
        return showInfo(context, context.getString(resId), DEF_DURATION, DEF_GRAVITY , DEF_OFFSET , DEF_OFFSET);
    }
    
    /**
     * 
     * @param context
     * @param resId
     * @param gravity
     * @param offsetX
     * @param offsetY
     * @return
     */
    public static SuperToast showInfo(Context context, int resId, int gravity , int offsetX , int offsetY)
    {
        return showInfo(context, context.getString(resId), DEF_DURATION , gravity , offsetX , offsetY);
    }
    
    /**
     * 
     * @param context
     * @param info
     * @param gravity
     * @param offsetX
     * @param offsetY
     * @return
     */
    public static SuperToast showInfo(Context context, String info, int gravity , int offsetX , int offsetY)
    {
        return showInfo(context, info , DEF_DURATION , gravity , offsetX , offsetY);
    }

    /**
     * 
     * @param context
     * @param info
     * @param duration
     * @param gravity
     * @param offsetX
     * @param offsetY
     * @return
     */
    public static SuperToast showInfo(Context context, String info, Integer duration, int gravity , int offsetX , int offsetY)
    {
        if (null == duration)
        {
            duration = DEF_DURATION;
        }
        SuperToast toast = SuperToast.create(context, info, duration ,Style.getStyle(Style.BLACK , SuperToast.Animations.SCALE));
       
        //默认居中显示
        toast.setGravity(gravity , offsetX, offsetY);
        
        //默认显示info图标
        toast.setIcon(SuperToast.Icon.Dark.INFO, SuperToast.IconPosition.LEFT);
        
        //不重复显示toast
        SuperToast.cancelAllSuperToasts();
        toast.show();
        return toast;
    }
    
    /**
     * 消除所有toast
     */
    public static void dismiss()
    {
        SuperToast.cancelAllSuperToasts();
    }
    
    /**
     * 消除所有toast
     */
    public static void dismissAll()
    {
        SuperToast.cancelAllSuperToasts();
        SuperCardToast.cancelAllSuperCardToasts();
        SuperActivityToast.cancelAllSuperActivityToasts();
    }
    
    /**
     * 显示带按钮卡条Toast
     * @param activity
     * @param btnText
     * @param text
     * @return
     */
    public static SuperCardToast showButtonCard(Activity activity , String btnText , String text)
    {
        // 显示测速结果
        SuperCardToast toast = new SuperCardToast(activity, Type.BUTTON, Style.getStyle(
                Style.WHITE, Animations.SCALE));
        toast.setButtonIcon(SuperToast.Icon.Light.REFRESH, btnText);
        // 计算速度
        toast.setText(text);
        toast.setButtonTextColor(activity.getResources().getColor(R.color.grey10));
        toast.setTextSize(ViewUtils.getDimen(activity, R.dimen.text_size_esmall)); //toast字体大小
        toast.setIndeterminate(true);
        toast.show();
        return toast;
    }
    
    /**
     * 显示不带按钮卡条Toast
     * @param activity
     * @param btnText
     * @param text
     * @return
     */
    public static SuperCardToast showCard(Activity activity , String text)
    {
        // 显示常态toast
        SuperCardToast toast = new SuperCardToast(activity, Type.STANDARD,
                Style.getStyle(Style.WHITE, Animations.SCALE));
        toast.setText(text);
        toast.setIndeterminate(true);
        toast.show();
        return toast;
    }
    
    /**
     * 显示进度条Toast
     * @param activity
     * @param text
     * @return
     */
    public static SuperCardToast showProgressCard(Activity activity , String text)
    {
        SuperCardToast toast = new SuperCardToast(activity, Type.PROGRESS_HORIZONTAL,
                Style.getStyle(Style.WHITE, Animations.SCALE));
        toast.setIndeterminate(true);
        toast.setText(text);
        toast.show();
        return toast;
    }
    
    /**
     * Loading Toast
     * @param activity
     * @param text
     * @return
     */
    public static SuperCardToast showLoadingCard(Activity activity , String text)
    {
        SuperCardToast toast = new SuperCardToast(activity, Type.PROGRESS,
                Style.getStyle(Style.WHITE, Animations.SCALE));
        toast.setIndeterminate(true);
        toast.setText(text);
        toast.show();
        return toast;
    }
}
