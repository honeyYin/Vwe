package com.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @description 文件上传工具类
 * @author 
 */
public class UploadUtil {

	public static String retDateStringMk() {
		return DateUtil.getStringDateShort().replaceAll("-", "");
	}

	/**
	 * @description 根据路径创建目录
	 * @param path
	 */
	public static void mkDirectory(String path) {
		File file;
		try {
			file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			file = null;
		}
	}

	/**
	 * @description 返回由时间组成的文件名称
	 * @return String
	 */
	public static String getNewFileName(String fileName) {
		// 将文件名以.的形式拆分
		String[] split = fileName.split("\\.");
		// 获文件的有效后缀
		String extendFile = "." + split[split.length - 1].toLowerCase();
		return UUID.randomUUID().toString() + extendFile;
	}
	
	public static String upload(File file, String path, String srcFileName) {
		File fileDir = new File(path);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		String fileName = path + File.separator +  getNewFileName(srcFileName);
		File newFile = new File(fileName);
		copy(file, newFile);
		return fileName;
	}

	/**
	 * 
	 * @param src
	 *            源
	 * @param dst
	 *            目标
	 * @return
	 */
	public static void copy(File src, File dst) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), 2048);
			out = new BufferedOutputStream(new FileOutputStream(dst), 2048);
			byte[] buffer = new byte[2048];
			while (in.read(buffer) > 0) {
				out.write(buffer);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (null != in) {
				try {
					in.close();
				}
				catch (Exception e) {
				}
			}
			if (null != out) {
				try {
					in.close();
				}
				catch (Exception e) {
				}
			}
		}
	}
}
