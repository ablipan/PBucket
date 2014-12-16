package com.pan.bucket.customviews;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Timer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ZoomButtonsController;

import com.pan.bucket.R;

/**
 * 封装webView控件，设置其基本属性
 */
@SuppressWarnings("static-access")
@SuppressLint("SetJavaScriptEnabled")
public class CommonWebView extends WebView {

	private static final String TAG = CommonWebView.class.getSimpleName();

	// Timeout时长
	private static final long TIMEOUT = 30000;

	private static class MyHandler extends Handler {
		private WeakReference<CommonWebView> activity;

		public MyHandler(CommonWebView activity) {
			this.activity = new WeakReference<CommonWebView>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			CommonWebView _this = activity.get();
			if (_this == null)
				return;

			_this.handleMessage(msg);
		}
	}

	public void handleMessage(Message msg) {
		if (statusListener != null) {
			statusListener.onTimeOut();
		}
		stopLoading();
		removeAllViews();
		destroy();
	}

	private Handler myHandler = new MyHandler(this);
	public WebViewStatusListener statusListener;
	public Timer timer;
	private int progress = 0;
	private Runnable timeOutTimer;

	public CommonWebView(Context context) {
		super(context);
		init(context);

	}

	public CommonWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	@SuppressLint("NewApi")
	private void init(Context context) {
		WebSettings settings = this.getSettings();
		settings.setJavaScriptEnabled(true);
		// 编码
		settings.setDefaultTextEncodingName("UTF-8");
		// 使用缓存
		settings.setAppCacheEnabled(false);
		//
		settings.setCacheMode(settings.LOAD_NO_CACHE);
		// 正常模式
		settings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
		// 放大page，填充webview
		settings.setLoadWithOverviewMode(true);

		// 11以上可以直接隐藏工具栏
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			// 禁用缩放工具栏
			settings.setDisplayZoomControls(false);
		} else {
			settings.setBuiltInZoomControls(true);
			setZoomControlGone(this);
		}
		// 隐藏滚动条
		setScrollbarFadingEnabled(true);
		// 滚动条样式-在webview中
		setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

		setWebViewClient(new MyWebViewClient());
		setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				if (statusListener != null)
					statusListener.onReceivedTitle(title);
			} 
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				progress = view.getProgress();
			}

		});

	}

	/**
	 * 隐藏缩放工具栏
	 * 
	 * @param view
	 */
	@SuppressWarnings("rawtypes")
	public void setZoomControlGone(View view) {
		Class classType;
		Field field;
		try {
			classType = WebView.class;
			field = classType.getDeclaredField("mZoomButtonsController");
			field.setAccessible(true);
			ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(
					view);
			mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
			try {
				field.set(view, mZoomButtonsController);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class MyWebViewClient extends WebViewClient {

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			Log.i(TAG, "onReceivedError");
			finish();
			if (statusListener != null) {
				statusListener.onRecevieError();
			}
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			loadUrl(url);
			progress = 0;
			return true;
		}

		@Override
		public void onPageStarted(final WebView view, final String url,
				Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			if (!CommLoading.isShowing()) {
				CommLoading.show(getContext(),
						R.string.loading_text);
			}
			try {
				timeOutTimer = new Runnable() {
					@Override
					public void run() {
						if (progress < 60) {
							myHandler.sendEmptyMessage(0);
							finish();
						}
					}
				};
				myHandler.postDelayed(timeOutTimer, TIMEOUT);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.i(TAG, "onPageStarted");
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			Log.i(TAG, "onPageFinished");
			finish();
			if (statusListener != null)
				statusListener.onPageFinished();
		}

		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler,
				SslError error) {
			finish();
			handler.proceed();
			Log.i(TAG, "onReceivedSslError");
		}

	}

	public interface WebViewStatusListener {
		public void onReceivedTitle(String title);

		public void onRecevieError();

		public void onTimeOut();

		// public void onProgressDialogCancle();
		public void onPageFinished();

		public boolean onShouldOverrideUrlLoading(String url);
	}

	// (1)需要设置网页加载失败后的显示效果
	public void setStatusListener(WebViewStatusListener listener) {
		statusListener = listener;
	}

	private void finish() {
		myHandler.removeCallbacks(timeOutTimer);
		CommLoading.dismiss();
	}

	@Override
	public void destroy() {
		super.destroy();
		myHandler.removeCallbacks(timeOutTimer);
	}

}
