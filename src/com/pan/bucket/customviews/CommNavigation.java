/**
 * author :  lipan
 * filename :  CommNavigation.java
 * create_time : 2014年4月16日 上午11:10:26
 */
package com.pan.bucket.customviews;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pan.bucket.R;
import com.pan.bucket.utils.StringB;
import com.pan.bucket.utils.ViewUtils;


/**
 * 公用导航栏
 * @author : lipan
 * @create_time : 2014年4月16日 上午11:10:26
 * @desc : 导航栏
 * @update_time :
 * @update_desc :
 *
 *使用方法：
 * 1.在命名空间里引入R文件位置
 *  xmlns:cuz="http://schemas.android.com/apk/res/com.sets.speedtest"
 *  
 * 2.添加自定Bar，属性的使用详见attrs.xml 的 公用导航栏属性：CommNavigation
 *  <!-- Navigation Bar -->
    <com.ailk.zt4android.common.CommNavigation 
        android:layout_width="match_parent"
        android:layout_height="@dimen/nav_height"
        cuz:nav_title_text="@string/login_navi_title"
        cuz:nav_has_left_btn="true"
        cuz:nav_has_right_btn="true"
        cuz:nav_right_drawable="@drawable/actionbar_menu"
    >
    </com.ailk.zt4android.common.CommNavigation>
 * 
 */
public class CommNavigation extends RelativeLayout
{
    
    private TextView tvTitle; //标题文本

    private Button btnLeft; //左边按钮
    private int btnLeftWidth; //左边按钮宽度
    private int btnLeftHeight; //左边按钮高度
    private RelativeLayout btnLeftLayout; //左边按钮背景————为了扩大按钮的触发区域
    private int btnLeftLayoutWidth; //左边按钮背景宽度
    
    private TextView btnRight; //右边按钮
    private RelativeLayout btnRightLayout; //右边按钮背景————为了扩大按钮的触发区域
    private int btnRightLayoutWidth; //右边按钮背景宽度
    
    private String strTitle; //标题文字
    private String strBtnRight; //右边按钮文字
    
//    private int left_drawable; //左边按钮图片
    private Drawable right_drawable; //右边按钮图片
    private int btnRightWidth; //左边按钮宽度
    private int btnRightHeight; //左边按钮高度
    
    private boolean hasLeftBtn; // 是否有左侧按钮
    private boolean hasRightBtn; // 是否有右边按钮
    
    private String onLeftClickListener; // 左边按钮监听
    private String onRightClickListener; // 右边按钮监听
    
    private int naviHeight;//导航栏高度
    
//    private int naviBgColor;//导航栏背景颜色
    
    private int textColor;//文字颜色 
    
    private float titleSize;//导航栏标题文本大小
    
    private float rightSize;//右侧文本大小
    
    private Drawable backDrawable;//返回按钮图片资源
    
    private int btnMarginPix;//按钮边距
    
    private Context context;
    
    /**
     * 
     * @param context
     * @param title
     * @param hasLeftBtn
     */
    public CommNavigation(Context context , String title , Boolean hasLeftBtn)
    {
        super(context);
        this.context = context; 
        this.hasLeftBtn = hasLeftBtn;
        this.strTitle = title;
        initAttrs(null);
        initContent();
    }

    
    /**
     * @param context
     * @param attrs
     */
    public CommNavigation(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context; 
        initAttrs(attrs);
        initContent();
    }

    //设置导航栏属性
    @SuppressWarnings("deprecation")
    private void initContent()
    {
        //垂直居中
        setGravity(Gravity.CENTER_VERTICAL);
        LayoutParams naviParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, naviHeight);
        setLayoutParams(naviParams);
//        setBackgroundColor(naviBgColor);
        ViewUtils.setBackground(this, R.drawable.navi_top);
        //左边按钮
        if(hasLeftBtn)
        {
            OnLeftClickListener leftClickListener = new OnLeftClickListener();
            btnLeftLayout = new RelativeLayout(context);
            
            //使用LayoutParams的时候要注意区分需要引用RelativeLayout 还是 LinearLayout的 LayoutParams
            RelativeLayout.LayoutParams btnLeftLayoutParams = new RelativeLayout.LayoutParams(btnLeftLayoutWidth , LayoutParams.MATCH_PARENT);
            //相对位置居左
            btnLeftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            btnLeftLayout.setLayoutParams(btnLeftLayoutParams);
            btnLeftLayout.setGravity(Gravity.LEFT + Gravity.CENTER_VERTICAL);
            
            btnLeft = new Button(context);
            RelativeLayout.LayoutParams btnLeftParams = new RelativeLayout.LayoutParams(btnLeftWidth , btnLeftHeight);
            btnLeftParams.setMargins(btnMarginPix, 0, 0, 0);
            btnLeft.setLayoutParams(btnLeftParams);
            btnLeft.setBackgroundDrawable(backDrawable);
            btnLeft.setVisibility(View.VISIBLE);
            btnLeft.setId(R.id.navi_btn_left);
            
            //给按钮的背景添加点击事件
            btnLeftLayout.setOnClickListener(leftClickListener);
            btnLeft.setOnClickListener(leftClickListener);
            btnLeftLayout.addView(btnLeft);
            btnLeftLayout.setId(R.id.navi_btn_left_layout);
            addView(btnLeftLayout);
            
            //添加按钮touch透明事件
            ViewUtils.addViewTouchAlpha(btnLeftLayout, btnLeft);
        }
        
        //中间标题
        tvTitle = new TextView(context);
        RelativeLayout.LayoutParams centerParam = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        tvTitle.setLayoutParams(centerParam);
        tvTitle.setTextColor(textColor);
        tvTitle.setText(strTitle);
        tvTitle.setTextSize(titleSize);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setId(R.id.navi_title);
        addView(tvTitle);
        
        //右侧按钮
        if(hasRightBtn)
        {
            OnRightClickListener rightClickListener = new OnRightClickListener();
            
            btnRightLayout = new RelativeLayout(context);
            btnRightLayout.setId(R.id.navi_btn_right_layout);
            RelativeLayout.LayoutParams btnRightLayoutParams = new RelativeLayout.LayoutParams(btnRightLayoutWidth , LayoutParams.MATCH_PARENT);
            //相对位置居右
            btnRightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            btnRightLayout.setLayoutParams(btnRightLayoutParams);
            btnRightLayout.setGravity(Gravity.RIGHT + Gravity.CENTER_VERTICAL);
            
            btnRight = new TextView(context);
            btnRight.setId(R.id.navi_btn_right);
            
            //右边显示文字
            if(!"".equals(strBtnRight) && null!=strBtnRight)
            {
                RelativeLayout.LayoutParams btnRightParams = new RelativeLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                btnRightParams.setMargins(0, 0, btnMarginPix, 0);
                btnRight.setLayoutParams(btnRightParams);
                btnRight.setTextSize(rightSize);
                btnRight.setText(strBtnRight);
                btnRight.setTextColor(textColor);
//                ViewUtils.setBackground(btnRight, R.drawable.comm_navi_button_bg);
            }else
            {
                //右边显示图片，高度和宽度为定义的宽高
                RelativeLayout.LayoutParams btnRightParams = new RelativeLayout.LayoutParams(
                        btnRightWidth, btnRightHeight);
                btnRightParams.setMargins(0, 0, btnMarginPix, 0);
                btnRight.setLayoutParams(btnRightParams);
                btnRight.setBackgroundDrawable(right_drawable);
                
                //添加按钮touch透明事件
                ViewUtils.addViewTouchAlpha(btnRightLayout, btnRight);
            }
            
            btnRight.setVisibility(View.VISIBLE);
            
            //清除文字留白
//            btnRight.setPadding(0, 0, 0, 0);
            //给按钮的背景添加点击事件
            btnRightLayout.setOnClickListener(rightClickListener);
            btnRight.setOnClickListener(rightClickListener);
            btnRightLayout.addView(btnRight);
            addView(btnRightLayout);
            
        }
    }

    /**
     * 左侧按钮点击事件
     */
    private class OnLeftClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            //如果有指定的Click监听事件
            if(StringB.isNotBlank(onLeftClickListener))
            {
                invokeMethod(onLeftClickListener , v);
            }else
            {
                ((Activity)context).finish();
                ((Activity)context).overridePendingTransition(R.anim.anim_null, R.anim.push_right_out);
            }
        }
    }
    
    /**
     * 右侧按钮点击事件
     */
    private class OnRightClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            //如果有指定的Click监听事件
            if(StringB.isNotBlank(onRightClickListener))
            {
                invokeMethod(onRightClickListener , v);
            }
        }
    }
    
    /**
     * 反射调用Activity中定义的click方法
     * @param handlerName
     */
    private void invokeMethod(final String handlerName , View v)
    {
        Method mHandler;
        
        try {
            mHandler = getContext().getClass().getMethod(handlerName , View.class);
        } catch (NoSuchMethodException e) {
            int id = getId();
            String idText = id == NO_ID ? "" : " with id '"
                    + getContext().getResources().getResourceEntryName(
                        id) + "'";
            throw new IllegalStateException("Could not find a method " +
                    handlerName + "in the activity "
                    + getContext().getClass() + " for onClick handler"
                    + " on view " + CommNavigation.this.getClass() + idText, e);
        }
        try {
            mHandler.invoke(getContext(), v);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Could not execute non "
                    + "public method of the activity", e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("Could not execute "
                    + "method of the activity", e);
        }
    }
    
    /**
     * 初始化属性
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs)
    {
        if(null != attrs)
        {
            TypedArray typedArray = context.obtainStyledAttributes(attrs , R.styleable.CommNavigation);
            
            hasLeftBtn = typedArray.getBoolean(R.styleable.CommNavigation_nav_has_left_btn, false);
            hasRightBtn = typedArray.getBoolean(R.styleable.CommNavigation_nav_has_right_btn, false);
            
            strTitle  = typedArray.getString(R.styleable.CommNavigation_nav_title_text);
            strBtnRight  = typedArray.getString(R.styleable.CommNavigation_nav_right_text);
            
            right_drawable = typedArray.getDrawable(R.styleable.CommNavigation_nav_right_drawable);
            
            onLeftClickListener  = typedArray.getString(R.styleable.CommNavigation_nav_OnLeftClick);
            onRightClickListener  = typedArray.getString(R.styleable.CommNavigation_nav_OnRightClick);
            
            typedArray.recycle();
        }
        
        Resources resources = getResources();
        
        //文字颜色 
        textColor = resources.getColor(R.color.white);
        
        //导航栏标题文本大小
//        titleSize = resources.getDimension(R.dimen.nav_title_size);
        titleSize = ViewUtils.getDimen(getContext(),R.dimen.text_size_large);
        
        //右侧文本大小
        rightSize = ViewUtils.getDimen(getContext(),R.dimen.text_size_small);
        
        //返回按钮图片资源
        backDrawable = resources.getDrawable(R.drawable.comm_nav_back);
        btnLeftWidth = resources.getDimensionPixelSize(R.dimen.gap_12);
        btnLeftHeight = resources.getDimensionPixelSize(R.dimen.gap_20);
        
        //右侧图片按钮大小
        btnRightWidth = resources.getDimensionPixelSize(R.dimen.gap_25);
        btnRightHeight = resources.getDimensionPixelSize(R.dimen.gap_25);
        
        //返回按钮背景尺寸
        btnLeftLayoutWidth = resources.getDimensionPixelSize(R.dimen.gap_80);
        
        btnRightLayoutWidth = resources.getDimensionPixelSize(R.dimen.gap_100);
        
        //按钮边距
        btnMarginPix = resources.getDimensionPixelSize(R.dimen.gap_15);
        
        naviHeight = resources.getDimensionPixelSize(R.dimen.gap_48);
        
//        naviBgColor = resources.getColor(R.color.navi_bg);
        
    }
    
    /**
     * 设置title
     * @param title
     */
    public static void setTitle(Activity context ,int title)
    {
        setTitle(context , context.getString(title));
    }

    /**
     * 设置title
     * @param title
     */
    public static void setTitle(Activity context ,String title)
    {
        TextView titleTV = (TextView)context.findViewById(R.id.navi_title);
        titleTV.setText(title);
    }

    /**
     * 设置右边button显示
     * @param title
     */
    public static void setRightVisible(Activity context)
    {
        Button btn_right = (Button)context.findViewById(R.id.navi_btn_right);
        btn_right.setVisibility(View.VISIBLE);
    }
    
    /**
     * 设置有边button隐藏
     * @param title
     */
    public static void setRightGone(Activity context)
    {
        Button btn_right = (Button)context.findViewById(R.id.navi_btn_right);
        btn_right.setVisibility(View.GONE);
    }
    
}
