package com.pengfei.tcpdump;

import android.content.Context;
import android.os.Environment;
import android.text.format.DateFormat;

import java.io.File;
import java.util.Date;

/**
 * 文件工具类
 * 
 * @author king
 * 
 */
public class FileUtils {

	/**
	 * sd卡是否可用
	 * 
	 * @return
	 */
	public static boolean isSdCardAvailable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * 创建根缓存目录
	 * 
	 * @return
	 */
	public static String createRootPath(Context context) {
		String cacheRootPath;
		if (isSdCardAvailable()) {
			// /sdcard/Android/data/<application package>/cache
			cacheRootPath = context.getExternalCacheDir().getPath();
		} else {
			// /data/data/<application package>/cache
			cacheRootPath = context.getCacheDir().getPath();
		}
		return cacheRootPath;
	}
	private final static String LOGS_FOLDER = "pjsip_log";

	private static File getStorageFolder(Context ctxt, boolean preferCache) {
		File root = Environment.getExternalStorageDirectory();
		if(!root.canWrite() || preferCache) {
			root = ctxt.getCacheDir();
		}

		if (root.canWrite()){
			File dir = new File(root.getAbsolutePath() + File.separator + "mtclib");
			if(!dir.exists()) {
				dir.mkdirs();
			}
			return dir;
		}
		return null;
	}


	private static File getSubFolder(Context ctxt, String subFolder, boolean preferCache) {
		File root = getStorageFolder(ctxt, preferCache);
		if(root != null) {
			File dir = new File(root.getAbsoluteFile() + File.separator + subFolder);
			dir.mkdirs();
			return dir;
		}
		return null;
	}

	public static File getLogsFolder(Context ctxt) {
		return getSubFolder(ctxt, LOGS_FOLDER, false);
	}

	public static File getLogsFile(Context ctxt, boolean isPjsip) {
		File dir = FileUtils.getLogsFolder(ctxt);
		File outFile = null;
		if( dir != null) {
			Date d = new Date();
			StringBuffer fileName = new StringBuffer();
			if(isPjsip) {
				fileName.append("pjsip");
			}
			fileName.append("logs_");
			fileName.append(DateFormat.format("yy-MM-dd_kkmmss", d));
			fileName.append(".txt");
			outFile = new File(dir.getAbsoluteFile() + File.separator + fileName.toString());
		}

		return outFile;
	}
}
