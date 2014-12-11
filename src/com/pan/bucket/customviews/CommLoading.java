/**
 * author :  lipan
 * filename :  LoadingDialog.java
 * create_time : 2014年4月11日 下午8:43:53
 */
package com.pan.bucket.customviews;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pan.bucket.R;
import com.pan.bucket.http.asyncHttpClient.WSClient;
import com.pan.bucket.utils.ViewUtils;

/**
 * @author : lipan
 * 
 * @create_time : 2014年4月11日 下午8:43:53
 * 
 * @desc : Loading时显示的dialog...
 * 
 * @update_time :
 * @update_desc :
 * 
 *  使用方法： 
 *  
 *  //显示 loading
 *  CommLoading.show(params); 
 *  
 *  //显示loading，监听laoding的隐藏事件
 *  CommLoading.show(context, new OnDismissListener(){ //....  });
 *  
 *  //隐藏loading
 *  CommLoading.dismiss();
 * 
 * 
 */
@SuppressLint("InflateParams")
public class CommLoading
{

    // loading Dialog
    public static Dialog loadingDialog;

    private static Animation loadingAnimation;
    /**
     * 显示loading框
     * 
     * @param context
     *            上下文对象
     */
    public static Dialog show(Context context)
    {
        return show(context, null, null);
    }

    /**
     * 显示loading框
     * 
     * @param context
     *            上下文对象
     * @param loadingText
     *            loading文字，默认为 “加载中...”
     */
    public static Dialog show(Context context, Integer loadingText)
    {
        return show(context, loadingText, null);
    }

    /**
     * 显示loading框
     * 
     * @param context
     *            上下文对象
     * @param loadingText
     *            loading文字，默认为 “加载中...”
     * @param dismissListener
     *            dialog消失时触发的事件...如dialog消失时,取消当前Http请求
     */
    public static Dialog show(final Context context, Integer loadingText,
            OnDismissListener onDismissListener)
    {
        loadingAnimation = AnimationUtils.loadAnimation(context,
                R.anim.loading_animation);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // loading布局视图
        View layout = inflater.inflate(R.layout.comm_loading, null);

        // loading的圆圈...
        ImageView loadImage = (ImageView) layout
                .findViewById(R.id.loading_circle);

        // 添加旋转动画
        loadImage.setAnimation(loadingAnimation);

        // 如果自定义了loading的文字
        if (null != loadingText)
        {
            TextView loadingTextView = (TextView) layout
                    .findViewById(R.id.loading_text);
            loadingTextView.setText(context.getString(loadingText));
        }

        // 取消加载按钮
        ImageButton loadingCancelImg = (ImageButton) layout
         .findViewById(R.id.loading_cancel);
        //外层layout
        View loadingCancelView = (View) layout
                .findViewById(R.id.loading_cancel_view);
        loadingCancelImg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                WSClient.cancel(context);
                ViewUtils.finishActivity(context);
            }
        });
        loadingCancelView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                WSClient.cancel(context);
                ViewUtils.finishActivity(context);
            }
        });

        //
        ViewUtils.addViewTouchAlpha(loadingCancelView, loadingCancelImg);
        
        loadingDialog = new Dialog(context, R.style.loading_dialog);

//        Window window = loadingDialog.getWindow();
//        //设置显示动画  
//        window.setWindowAnimations(R.style.comm_dialog); 
//        
        loadingDialog.setContentView(layout);
        // LayoutParams lay = loadingDialog.getWindow().getAttributes();
        // setParams(context, lay);

        if (null != onDismissListener)
        {
            loadingDialog.setOnDismissListener(onDismissListener);
        }

        //防止loading时点击旁边消失了...
        loadingDialog.setCanceledOnTouchOutside(false);
        
        loadingDialog.show();

        return loadingDialog;
    }
    
    /**
     * 显示没有取消按钮的loading框
     * 
     * @param context
     *            上下文对象
     */
    public static Dialog showWithoutCancel(final Context context, Integer loadingText)
    {
        loadingAnimation = AnimationUtils.loadAnimation(context,
                R.anim.loading_animation);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        // loading布局视图
        View layout = inflater.inflate(R.layout.comm_loading, null);
        
        // loading的圆圈...
        ImageView loadImage = (ImageView) layout
                .findViewById(R.id.loading_circle);
        
        // 添加旋转动画
        loadImage.setAnimation(loadingAnimation);
        
        // 如果自定义了loading的文字
        if (null != loadingText)
        {
            TextView loadingTextView = (TextView) layout
                    .findViewById(R.id.loading_text);
            loadingTextView.setText(context.getString(loadingText));
        }

        //隐藏分割线
        View loadingSeparateLine = (View) layout
        .findViewById(R.id.loading_separate_line);
        loadingSeparateLine.setVisibility(View.GONE);
        
        // 隐藏取消加载按钮
        View loadingCancelImg = (View) layout
                .findViewById(R.id.loading_cancel_view);
        loadingCancelImg.setVisibility(View.GONE);
        
        loadingDialog = new Dialog(context, R.style.loading_dialog);
        //不可取消
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(layout);
        
        //防止loading时点击外围后消失...
        loadingDialog.setCanceledOnTouchOutside(false);
        
        loadingDialog.show();
        
        return loadingDialog;
    }

    /**
     * 是否在显示...
     * @return
     */
    public static boolean isShowing()
    {
        if(null == loadingDialog)
        {
            return false;
        }
        return loadingDialog.isShowing();
    }
    
    /**
     * 隐藏loading框
     */
    public static void dismiss()
    {
        if (null != loadingDialog && loadingDialog.isShowing())
        {
            loadingAnimation.cancel();
            loadingDialog.dismiss();
        }
    }
}
