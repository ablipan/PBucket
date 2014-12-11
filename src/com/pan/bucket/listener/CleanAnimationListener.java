/**
 * author :  lipan
 * filename :  CleanAnimationListener.java
 * create_time : 2014年5月7日 下午6:01:32
 */
package com.pan.bucket.listener;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

/**
 * @author : lipan
 * @create_time : 2014年5月7日 下午6:01:32
 * @desc : 在动画完成后清除视图的动画
 * @update_time :
 * @update_desc :
 *
 */
public class CleanAnimationListener implements AnimationListener
{
    private View animV;

    public CleanAnimationListener(View v)
    {
        animV = v;
    }

    @Override
    public void onAnimationStart(Animation animation)
    {
    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        animV.clearAnimation();
    }

    @Override
    public void onAnimationRepeat(Animation animation)
    {
    }
}
