/**
 * author :  lipan
 * filename :  StreamUtils.java
 * create_time : 2014年8月28日 下午5:55:06
 */
package com.pan.bucket.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;

/**
 * @author : lipan
 * @create_time : 2014年8月28日 下午5:55:06
 * @desc : Stream Utils
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
public class StreamUtils {
	/**
	 * 输入流转字节数组
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static byte[] getBytes(InputStream in) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int n;
		while ((n = in.read(b)) != -1) {
			out.write(b, 0, n);
		}
		in.close();
		return out.toByteArray();
	}

	/**
	 * SDcard是否挂载
	 * 
	 * @return
	 */
	public static boolean isSDcardMounted() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	/**
	 * 在手机目录/SD卡中创建目录
	 * 
	 * @param context
	 * @param path 需要创建的目录
	 * @return
	 */
	public static File mkLocalDir(Context context, String path) {
		File dir;
		if (isSDcardMounted()) {
			dir = new File(Environment.getExternalStorageDirectory() + File.separator + path); // SDcard
		} else {
			dir = new File(context.getFilesDir().getAbsolutePath() + File.separator + path); // 手机内存
		}
		if (!dir.exists())
			dir.mkdirs();
		return dir;
	}
}
