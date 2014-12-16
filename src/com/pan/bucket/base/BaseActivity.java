package com.pan.bucket.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.autowire.AndroidAutowire;
import com.autowire.BaseAutowireActivity;
import com.pan.bucket.R;
import com.pan.bucket.customviews.CommDialog;
import com.pan.bucket.customviews.CommLoading;
import com.pan.bucket.customviews.CommToast;
import com.pan.bucket.listener.CommListener;
import com.swipebacklayout.app.SwipeBackActivity;
import com.swipebacklayout.app.SwipeBackActivityBase;

/**
 * @author : lipan
 * @create_time : 2014年8月15日 下午3:24:44
 * @desc : Activity基类
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
public abstract class BaseActivity extends SwipeBackActivity implements
		SwipeBackActivityBase {

	private CommListener listener;
	protected Context context;
	protected Activity activity;
	protected LayoutInflater inflater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		activity = this;
		inflater = getLayoutInflater();
		// 注解
		AndroidAutowire.loadFieldsFromBundle(savedInstanceState, this,
				BaseAutowireActivity.class);

		int layoutId = AndroidAutowire.getLayoutResourceByAnnotation(this,
				this, BaseAutowireActivity.class);
		// 如果没有使用layout注解，直接返回...
		if (layoutId == 0) {
			return;
		}
		setContentView(layoutId);
		afterAutowire(savedInstanceState);
	}

	// 代替onCreate方法
	protected abstract void afterAutowire(Bundle savedInstanceState);

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		// 注入视图的Views
		AndroidAutowire.autowire(this, BaseAutowireActivity.class);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// 注入参数
		AndroidAutowire.saveFieldsToBundle(outState, this,
				BaseAutowireActivity.class);
	}

	@Override
	protected void onStart() {
		super.onStart();

		// 给每个view添加点击事件...
		View needHideKeyBord = findViewById(R.id.main_parent);
		if (null != needHideKeyBord) {
			setupUI(needHideKeyBord);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 回收Dialog资源
		CommLoading.dismiss();
		CommDialog.dismiss();
		CommToast.dismiss();
	}

	/**
	 * 按钮点击事件
	 * 
	 * @param v
	 */
	public void BtnClick(View v) {
	}

	/***************************** Start Activity Begin *****************************/
	/** 通过Class跳转界面 **/
	protected void startActivity(Class<?> cls) {
		startActivity(cls, null);
	}

	/** 含有Bundle通过Class跳转界面 **/
	protected void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}
	}

	/** 通过Action跳转界面 **/
	protected void startActivity(String action) {
		Intent intent = new Intent();
		intent.setAction(action);
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}
	}

	/** 含有Data通过Action跳转界面 **/
	protected void startActivity(String action, Uri data) {
		Intent intent = new Intent();
		intent.setAction(action);
		intent.setData(data);
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}
	}

	/** 结束Activity **/
	protected void finishActivity() {
		super.finish();
	}

	/** 结束Activity **/
	protected void finishActivityWithAnimation() {
		super.finish();
		overridePendingTransition(R.anim.anim_null, R.anim.push_right_out);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		/** 由于style中android:windowIsTranslucent=true影响theme中的返回动画....监控返回按钮 **/
		overridePendingTransition(R.anim.anim_null, R.anim.push_right_out);
	}
	/***************************** Start Activity End *****************************/

	/**
	 * 获得Parcelable中的参数
	 * 
	 * @return
	 */
	protected Object getParcel(String key) {
		return getIntent().getParcelableExtra(key);
	}

	/**
	 * 获得Parcelable中的参数
	 * 
	 * @return
	 */
	protected Object getParcelArrayList(String key) {
		return getIntent().getParcelableArrayListExtra(key);
	}

	/**
	 * 获得Activity
	 * 
	 * @return
	 */
	protected Activity getActivity() {
		return (Activity) this;
	}

	/** 保持屏幕常亮 **/
	protected void keepScreenOn() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	/** 取消屏幕常亮 **/
	protected void keepWake() {
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}

	/** 启动服务 **/
	protected void startService(Class<?> cls) {
		startService(cls, null);
	}

	/** 启动服务 **/
	protected void startService(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent(this, cls);

		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startService(intent);
	}

	/** 使用Dialog进行调试 **/
	protected void debugWithDialog(String s) {
		CommDialog.showInfoDialog(context, s, null);
	}

	/** 使用toast进行调试 **/
	protected void debugWithToast(String s) {
		CommToast.showInfo(context, s);
	}

	// 设置监听事件
	protected void setListener(CommListener listener) {
		this.listener = listener;
	}

	/**
	 * 给每个非输入框的view添加touch事件
	 * 
	 * @param view
	 */
	private void setupUI(View view) {
		Log.i("View.Tag", view.getTag() + "");
		if (!(view instanceof EditText)) {
			view.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (null != listener)
						listener.allViewsOnTouchExcludeInput();
					return false;
				}

			});
		}
		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				View innerView = ((ViewGroup) view).getChildAt(i);

				setupUI(innerView);
			}
		}
	}
	
}
