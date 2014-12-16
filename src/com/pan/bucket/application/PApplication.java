package com.pan.bucket.application;

import org.litepal.LitePalApplication;

import android.content.Context;

import com.pan.bucket.thread.ThreadPool;

/**
 * 自定义Application 继承 LitePalApplication  
 * 详见
 * https://github.com/LitePalFramework/LitePal
 * 
 * @author Pan
 * 
 */
public class PApplication extends LitePalApplication {
	
	protected Context context;
	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		
		initLogger(); // 日志记录
		initImageLoader(); // 图片加载器
		initHttpUtils(); // http工具类
		initCrashHandler(); // 初始化程序崩溃捕捉处理
		initPrefs(); // 初始化SharedPreference
		initThreadPool(); // 线程池
	}

	protected void initThreadPool() {
		ThreadPool.initThreadPool(-1);
	}
	protected void initHttpUtils() {
	}
	protected void initPrefs() {
	}
	protected void initCrashHandler() {
	}
	protected void initLogger() {
	}
	protected void initImageLoader() {
	}

}
