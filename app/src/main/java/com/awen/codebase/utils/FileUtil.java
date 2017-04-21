package com.awen.codebase.utils;

import java.io.File;
import java.io.IOException;

import android.os.StatFs;
import android.text.TextUtils;

public final class FileUtil {

	public static void makeDir(String fileName) {
		File directory = new File(fileName).getParentFile();
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	public static void makePathDir(String path) {
		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdirs();
		} else {
			return;
		}
	}

	public static void makeDirNoMedia(String dirPath) {
		File nm = new File(dirPath);
		if (nm.exists())
			return;
		nm.mkdirs();

	}

	public static void makeFile(String fileName) {
		File file = new File(fileName);
		try {
			if (!file.exists()) {
				file.createNewFile();
			} else if (file.isFile()) {
				file.delete();
				file.createNewFile();
			} else if (file.isDirectory()) {
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static long getDirSize(File f) {
		long size = 0;
		try {
			File flist[] = f.listFiles();
			for (int i = 0; i < flist.length; i++) {
				if (flist[i].isDirectory()) {
					size = size + getDirSize(flist[i]);
				} else {
					size = size + flist[i].length();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	public static String getStorageSize(String path) {
		StatFs sf = new StatFs(path);
		long count = sf.getBlockCount();
		long size = sf.getBlockSize();
		long len = count * size;
		double r = len / 1024.0; // kB
		if (r <= 0)
			return String.format("%.2f bytes", len);
		double rr = r / 1024.0; // MB
		if (rr <= 0)
			return String.format("%.2f KB", r);
		double rrr = rr / 1024.0;
		if (rrr <= 0)
			return String.format("%.2f MB", rr);

		return String.format("%.2f GB", rrr);
	}

	public static boolean isFileExists(String path) {
		boolean result = false;
		if (!TextUtils.isEmpty(path)) {
			File file = new File(path);
			result = file.exists();
		}
		return result;
	}

	public static boolean delDir(File f) {
		try {
			if (f.exists() && f.isDirectory()) {// 鍒ゆ柇鏄枃浠惰繕鏄洰褰?
				if (f.listFiles().length == 0) {// 鑻ョ洰褰曚笅娌℃湁鏂囦欢鍒欑洿鎺ュ垹闄?
					f.delete();
				} else {// 鑻ユ湁鍒欐妸鏂囦欢鏀捐繘鏁扮粍锛屽苟鍒ゆ柇鏄惁鏈変笅绾х洰褰?
					File delFile[] = f.listFiles();
					int i = f.listFiles().length;
					for (int j = 0; j < i; j++) {
						if (delFile[j].isDirectory()) {
							delDir(delFile[j]);// 閫掑綊璋冪敤del鏂规硶骞跺彇寰楀瓙鐩綍璺緞
						}
						delFile[j].delete();
					}// 鍒犻櫎鏂囦欢
					f.delete();
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 创建一个文件，创建成功返回true
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean createFile(String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}

				return file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 删除一个文件
	 * 
	 * @param filePath
	 *            要删除的文件路径名
	 * @return true if this file was deleted, false otherwise
	 */
	public static boolean deleteFile(String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists()) {
				return file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
