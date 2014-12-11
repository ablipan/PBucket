/**
 * author :  lipan
 * filename :  CommDialog.java
 * create_time : 2014年4月14日 下午3:55:33
 */
package com.pan.bucket.customviews;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pan.bucket.R;
import com.pan.bucket.listener.OnDialogClickListener;
import com.pan.bucket.utils.StringB;
import com.pan.bucket.utils.ViewUtils;

/**
 * @author : lipan
 * @create_time : 2014年4月14日 下午3:55:33
 * @desc : 通用提示框...
 * @update_time :
 * @update_desc : 使用方法：
 * 
 *              弹出提示信息Dialog：CommAlertDialog.showInfoDialog(params);
 * 
 *              弹出Confirm Dialog：showConfirmDialog.showConfirmDialog(params);
 * 
 */
public class CommDialog
{
    public static AlertDialog alertDialog;

    public static AlertDialog showInfoDialog(Context context,String message ,final OnDialogClickListener dialogClickListener )
    {
        return showInfoDialog(context, null , message, null, true, true, dialogClickListener);
    }
    
    /**
     * 
     * 显示提示信息框
     * 
     * @param context
     *            上下文对象
     * @param title
     *            标题
     * @param message
     *            要显示的文本信息
     * @param btnText
     *            取消按钮、确定按钮显示文字
     * @param canceledOnTouchOutside
     *            点击Dialog周围隐藏dialog
     * @param cancelClickListener
     *            取消按钮点击事件
     * @param cancelAble
     *            点击返回按钮是否可退出
     * @param onConfirmClickListener
     *            确定按钮点击事件
     * @return
     */
    @SuppressLint("InflateParams")
    public static AlertDialog showInfoDialog(Context context, Integer title , String message,
            String btnText, Boolean canceledOnTouchOutside,
            Boolean cancelAble, final OnDialogClickListener dialogClickListener)
    {
        // 自定义 alertdialog 视图
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.comm_dialog_info, null);
        
        // 标题
        TextView dialogTitle = (TextView)dialogView.findViewById(R.id.dialogTitle);
        if (null!=title)
        {
            dialogTitle.setText(title);
        }
        
        // 内容
        LinearLayout dialogContent = (LinearLayout)dialogView.findViewById(R.id.dialogContent);
        TextView dialogText = new TextView(context);
        dialogText.setText(message);
        dialogText.setTextSize(ViewUtils.getDimen(context, R.dimen.text_size_small));
        dialogContent.addView(dialogText);
        
        // 取消按钮
        Button confirmBtn = (Button) dialogView.findViewById(R.id.dialog_confirm);
        if (!StringB.isBlank(btnText))
        {
            confirmBtn.setText(btnText);
        }
        confirmBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
                if (null != dialogClickListener)
                {
                    dialogClickListener.onClick(v);
                }
            }
        });

        dismiss();
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        
        //必须先show()才能获取到window对象。
        LayoutParams layoutParam = alertDialog.getWindow().getAttributes();
        layoutParam.width = ViewUtils.getDeviceWidth(context)*6/7;
        layoutParam.height = LayoutParams.WRAP_CONTENT;
        alertDialog.getWindow().setAttributes(layoutParam);
        
        alertDialog.setContentView(dialogView);
        if (null != canceledOnTouchOutside)
        {
            alertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        }
        alertDialog.setCancelable(cancelAble);
        return alertDialog;
    }
    
    public static AlertDialog showConfirmDialog(Context context,Integer messageId ,final OnDialogClickListener dialogClickListener )
    {
        return showConfirmDialog(context, null , messageId, null, null, true, true, dialogClickListener);
    }
    
    /**
     * 
     * 显示对话框
     * 
     * @param context
     *            上下文对象
     * @param title
     *            标题
     * @param message
     *            要显示的文本信息
     * @param btnText
     *            取消按钮、确定按钮显示文字
     * @param canceledOnTouchOutside
     *            点击Dialog周围隐藏dialog
     * @param cancelClickListener
     *            取消按钮点击事件
     * @param cancelAble
     *            点击返回按钮是否可退出
     * @param onConfirmClickListener
     *            确定按钮点击事件
     * @return
     */
    @SuppressLint("InflateParams")
    public static AlertDialog showConfirmDialog(Context context,Integer title , Integer message,
            String leftBtnText, String rightBtnText, Boolean canceledOnTouchOutside,
            Boolean cancelAble, final OnDialogClickListener dialogClickListener)
    {
        // 自定义 alertdialog 视图
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.comm_dialog_confirm, null);
        
        // 标题
        TextView dialogTitle = (TextView)dialogView.findViewById(R.id.dialogTitle);
        if (null!=title)
        {
            ViewUtils.show(dialogTitle);
            dialogTitle.setText(title);
        }
        
        // 内容
        LinearLayout dialogContent = (LinearLayout)dialogView.findViewById(R.id.dialogContent);
        TextView dialogText = new TextView(context);
        dialogText.setText(message);
        dialogText.setTextSize(ViewUtils.getDimen(context, R.dimen.text_size_small));
        dialogContent.addView(dialogText);
        
        // 取消按钮
        Button cancelBtn = (Button) dialogView.findViewById(R.id.dialog_cancel);
        if (!StringB.isBlank(leftBtnText))
        {
            cancelBtn.setText(leftBtnText);
        }
        cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
                if (null != dialogClickListener)
                {
                    dialogClickListener.onLeftClick(v);
                }
            }
        });
        
        // 确定按钮
        Button confirmBtn = (Button) dialogView.findViewById(R.id.dialog_confirm);
        if (!StringB.isBlank(rightBtnText))
        {
            confirmBtn.setText(rightBtnText);
        }
        confirmBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
                if (null != dialogClickListener)
                {
                    dialogClickListener.onRightClick(v);
                }
            }
        });
        
        dismiss();
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        
        //必须先show()才能获取到window对象。
        LayoutParams layoutParam = alertDialog.getWindow().getAttributes();
        layoutParam.width = ViewUtils.getDeviceWidth(context)*6/7;
        layoutParam.height = LayoutParams.WRAP_CONTENT;
        alertDialog.getWindow().setAttributes(layoutParam);
        
        alertDialog.setContentView(dialogView);
        if (null != canceledOnTouchOutside)
        {
            alertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        }
        alertDialog.setCancelable(cancelAble);
        return alertDialog;
    }

    
    /**
     * 
     * 显示对话框
     * 
     * @param context
     *            上下文对象
     * @param title
     *            标题
     * @param view
     *            要显示的view
     * @param btnText
     *            取消按钮、确定按钮显示文字
     * @param canceledOnTouchOutside
     *            点击Dialog周围隐藏dialog
     * @param cancelClickListener
     *            取消按钮点击事件
     * @param cancelAble
     *            点击返回按钮是否可退出
     * @param onConfirmClickListener
     *            确定按钮点击事件
     * @return
     */
    @SuppressLint("InflateParams")
    public static AlertDialog showConfirmDialog(Context context,Integer title , View view,
            String leftBtnText, String rightBtnText, Boolean canceledOnTouchOutside,
            Boolean cancelAble, final OnDialogClickListener dialogClickListener)
    {
        // 自定义 alertdialog 视图
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.comm_dialog_confirm, null);
        
        // 标题
        TextView dialogTitle = (TextView)dialogView.findViewById(R.id.dialogTitle);
        if (null!=title)
        {
            ViewUtils.show(dialogTitle);
            dialogTitle.setText(title);
        }
        
        // 内容
        LinearLayout dialogContent = (LinearLayout)dialogView.findViewById(R.id.dialogContent);
        dialogContent.addView(view);
        
        // 取消按钮
        Button cancelBtn = (Button) dialogView.findViewById(R.id.dialog_cancel);
        if (!StringB.isBlank(leftBtnText))
        {
            cancelBtn.setText(leftBtnText);
        }
        cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
                if (null != dialogClickListener)
                {
                    dialogClickListener.onLeftClick(v);
                }
            }
        });
        
        // 确定按钮
        Button confirmBtn = (Button) dialogView.findViewById(R.id.dialog_confirm);
        if (!StringB.isBlank(rightBtnText))
        {
            confirmBtn.setText(rightBtnText);
        }
        confirmBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
                if (null != dialogClickListener)
                {
                    dialogClickListener.onRightClick(v);
                }
            }
        });
        
        dismiss();
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        
        //必须先show()才能获取到window对象。
        LayoutParams layoutParam = alertDialog.getWindow().getAttributes();
        layoutParam.width = ViewUtils.getDeviceWidth(context)*6/7;
        layoutParam.height = LayoutParams.WRAP_CONTENT;
        alertDialog.getWindow().setAttributes(layoutParam);
        
        alertDialog.setContentView(dialogView);
        if (null != canceledOnTouchOutside)
        {
            alertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        }
        alertDialog.setCancelable(cancelAble);
        return alertDialog;
    }
    
    /**
     * 是否在显示...
     * 
     * @return
     */
    public static boolean isShowing()
    {
        if (null == alertDialog)
        {
            return false;
        }
        return alertDialog.isShowing();
    }

    /**
     * 隐藏Dialog
     */
    public static void dismiss()
    {
        if (null != alertDialog && alertDialog.isShowing())
        {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

}
