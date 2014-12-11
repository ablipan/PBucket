package com.pan.bucket.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autowire.AndroidAutowire;
import com.pan.bucket.R;
import com.pan.bucket.customviews.CommDialog;
import com.pan.bucket.customviews.CommLoading;
import com.pan.bucket.customviews.CommToast;

/**
 * Fragment基类
 * 
 * @author Pan
 * 
 */
public abstract class BaseFragment extends Fragment{
	public Context context;
	public Activity activity;
	protected View contentView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		activity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// Load any annotated fields from the bundle
		AndroidAutowire.loadFieldsFromBundle(savedInstanceState, this,
				BaseFragment.class);

		// Load the content view using the AndroidLayout annotation
		contentView = super.onCreateView(inflater, container,
				savedInstanceState);
		if (contentView == null) {
			int layoutResource = AndroidAutowire.getLayoutResourceByAnnotation(
					this, getActivity(), BaseFragment.class);
			if (layoutResource == 0) {
				return null;
			}
			contentView = inflater.inflate(layoutResource, container, false);
		}
		// If we have the content view, autowire the Fragment's views
		autowireViews(contentView);
		// Callback for when autowiring is complete
		afterAutowire(savedInstanceState);
		return contentView;
	}

	protected void autowireViews(View contentView){
        AndroidAutowire.autowireFragment(this, BaseFragment.class, contentView, getActivity());
    }

	protected abstract void afterAutowire(Bundle savedInstanceState);
	public Context getContext() {
		return context;
	}

	/** 使用Dialog进行调试 **/
	protected void debugWithDialog(String s) {
		CommDialog.showInfoDialog(getContext(), s, null);
	}

	/** 使用toast进行调试 **/
	protected void debugWithToast(String s) {
		CommToast.showInfo(getContext(), s);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

		// 回收Dialog资源
		CommLoading.dismiss();
		CommDialog.dismiss();
		CommToast.dismiss();
	}

	
	
	/***************************** Start Activity Begin*****************************/
	protected void startActivity(Class<?> cls) {
		startActivity(cls, null);
	}

	protected void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
			startActivity(intent);
			activity.overridePendingTransition(R.anim.push_left_in, R.anim.anim_null);
		}
	}

	protected void startActivity(String action) {
		Intent intent = new Intent();
		intent.setAction(action);
		if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
			startActivity(intent);
			activity.overridePendingTransition(R.anim.push_left_in, R.anim.anim_null);
		}
	}

	protected void startActivity(String action, Bundle bundle) {
		Intent intent = new Intent();
		intent.setAction(action);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
			startActivity(intent);
			activity.overridePendingTransition(R.anim.push_left_in, R.anim.anim_null);
		}
	}
	/***************************** Start Activity End*****************************/

}
