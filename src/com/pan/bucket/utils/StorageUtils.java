package com.pan.bucket.utils;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.nostra13.universalimageloader.utils.L;

/**
 * 
 * 存储管理
 * 
 * @author Pan
 * 
 */
public class StorageUtils {

	public static final String MEDIA_MOUNTED = "mounted";
	private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

	/**
	 * 日志目录...mnt/sdcard/Android/data/xxxx/logs
	 * 
	 * @param context
	 * @param preferExternal
	 *            优先外部存储
	 * @return
	 */
	public static File getLogDirectory(Context context, boolean preferExternal) {
		File appCacheDir = null;
		String externalStorageState;
		try {
			externalStorageState = Environment.getExternalStorageState();
		} catch (NullPointerException e) { // (sh)it happens (Issue #660)
			externalStorageState = "";
		}
		if (preferExternal && MEDIA_MOUNTED.equals(externalStorageState)
				&& hasExternalStoragePermission(context)) {
			appCacheDir = getExternalLogDir(context);
		}
		if (appCacheDir == null) {
			appCacheDir = context.getCacheDir();
		}
		if (appCacheDir == null) {
			String cacheDirPath = "/data/data/" + context.getPackageName()
					+ "/logs/";
			L.w("Can't define system log directory! '%s' will be used.",
					cacheDirPath);
			appCacheDir = new File(cacheDirPath);
		}
		return appCacheDir;
	}

	/**
	 * 日志文件外部存储路径
	 * 
	 * @param context
	 * @return
	 */
	private static File getExternalLogDir(Context context) {
		File dataDir = new File(new File(
				Environment.getExternalStorageDirectory(), "Android"), "data");
		File appLogDir = new File(new File(dataDir, context.getPackageName()),
				"logs");
		if (!appLogDir.exists()) {
			if (!appLogDir.mkdirs()) {
				L.w("Unable to create external log directory");
				return null;
			}
			try {
				new File(appLogDir, ".nomedia").createNewFile();
			} catch (IOException e) {
				L.i("Can't create \".nomedia\" file in application external cache directory");
			}
		}
		return appLogDir;
	}

	private static boolean hasExternalStoragePermission(Context context) {
		int perm = context
				.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
		return perm == PackageManager.PERMISSION_GRANTED;
	}
}
