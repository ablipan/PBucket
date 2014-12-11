package com.pan.bucket.customviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.pan.bucket.R;
import com.pan.bucket.utils.StringB;

/**
 * 带清除按钮的EditText
 * 
 * @author : lipan
 * @create_time : 2014年4月15日 下午8:21:13
 * @desc :
 * @update_time :
 * @update_desc :
 * 
 */
@SuppressLint({ "DrawAllocation", "ClickableViewAccessibility" })
public class ClearEditText extends EditText implements OnFocusChangeListener, TextWatcher
{
    // private static final String TAG = "ClearEditText";

    public static final int SHAKE_TIMES = 3; // 每秒抖动次数...

    private boolean hasBottomBorder; // 是否有下划线
    private boolean isNumber; // 是否只能输入数字
    
    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;

    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;

    public ClearEditText(Context context)
    {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs)
    {
        // 这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        super(context, attrs, android.R.attr.editTextStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText);

        hasBottomBorder = typedArray.getBoolean(R.styleable.ClearEditText_has_bottom_border, true);
        isNumber = typedArray.getBoolean(R.styleable.ClearEditText_is_number, false);
        typedArray.recycle();

        init();
    }

    /**
     * 在按钮的底部画一条直线
     */
    @Override
    protected void onDraw(Canvas canvas)
    {
        if (hasBottomBorder)
        {
            Paint paint = new Paint();
            paint.setStyle(Style.STROKE);
            paint.setColor(getResources().getColor(R.color.grey82));
            canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1, this.getHeight() - 1,
                    paint);
        }
        super.onDraw(canvas);

    }

    private void init()
    {
        // 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null)
        {
            // throw new
            // NullPointerException("You can add drawableRight attribute in XML");
            mClearDrawable = getResources().getDrawable(R.drawable.selector_text_clear_btn);
        }

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
                mClearDrawable.getIntrinsicHeight());
        // 默认设置隐藏图标
        setClearIconVisible(false);
        // 设置焦点改变的监听
        setOnFocusChangeListener(this);
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    /**
     * 当手指抬起的位置在clean的图标的区域 我们将此视为进行清除操作 getWidth():得到控件的宽度
     * event.getX():抬起时的坐标(改坐标是相对于控件本身而言的)
     * getTotalPaddingRight():clean的图标左边缘至控件右边缘的距离
     * getPaddingRight():clean的图标右边缘至控件右边缘的距离 于是: getWidth() -
     * getTotalPaddingRight()表示: 控件左边到clean的图标左边缘的区域 getWidth() -
     * getPaddingRight()表示: 控件左边到clean的图标右边缘的区域 所以这两者之间的区域刚好是clean的图标的区域
     * 
     * 为了防止手大按不到..稍微加大点击的判断区域
     */
    public static final int OFFSET_ = 10; // 误差值

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
            if (getCompoundDrawables()[2] != null)
            {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight() - OFFSET_)
                        && (event.getX() < ((getWidth() - getPaddingRight() + OFFSET_)));

                if (touchable)
                {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus)
    {
        this.hasFoucs = hasFocus;
        if (hasFocus)
        {
            setClearIconVisible(getText().length() > 0);
        } else
        {
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * 
     * @param visible
     */
    protected void setClearIconVisible(boolean visible)
    {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right,
                getCompoundDrawables()[3]);
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after)
    {

        String editable = getText().toString();
        // 过滤数字以外的字符
        if (isNumber)
        {
            String str = StringB.stringFilter(StringB.EXP_NUM, editable);
            if (!editable.equals(str))
            {
                setText(str);
            }
            setSelection(length());
        }

        // 根据输入框的内容判断清除按钮的显隐藏
        if (hasFoucs)
        {
            setClearIconVisible(editable.length() > 0);
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
    }

    @Override
    public void afterTextChanged(Editable s)
    {
    }

    /**
     * 设置晃动动画
     */
    public void setShakeAnimation()
    {
        this.setAnimation(shakeAnimation(2));
    }

    /**
     * 晃动动画
     * 
     * @param counts
     *            1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts)
    {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(400);
        return translateAnimation;
    }

}
