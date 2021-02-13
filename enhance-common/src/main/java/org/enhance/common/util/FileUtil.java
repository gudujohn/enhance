package org.enhance.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件处理工具
 * 
 * @author JiangGengchao
 * @data 2016年6月12日
 */
@Slf4j
public class FileUtil {
	/**
	 * get absolute path
	 */
	public static String getSystemAbsolutePath() {
		return ClassLoader.getSystemResource("").getPath();
	}

	/**
	 * 获取一个文件，没有就新建一个文件
	 */
	public static File getFile(String filePath) {
		File file = new File(filePath);
		if (exists(filePath)) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				log.error(e.getMessage());
				return null;
			}
		}
		return file;
	}

	public static void createFolder(String folderPath) {
		File file = new File(folderPath);
		if (exists(folderPath)) {
			file.mkdirs();
		}
	}

	/**
	 * 判断文件是否存在
	 */
	public static boolean exists(String filePath) {
		File file = new File(filePath);
		return !file.exists();
	}

	public static String getCreationTime(File file) {
		return getCreationTime(file, DateFormatUtil.COMPACT_DATETIME_FORMAT);
	}

	public static String getCreationTime(File file, String format) {
		BasicFileAttributes attr = null;
		try {
			Path path = file.toPath();
			attr = Files.readAttributes(path, BasicFileAttributes.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 创建时间
		assert attr != null;
		Instant instant = attr.creationTime().toInstant();
//		// 更新时间
//        Instant instant = attr.lastModifiedTime().toInstant();
//		// 上次访问时间
//        Instant instant = attr.lastAccessTime().toInstant();
		return DateTimeFormatter.ofPattern(format).withZone(ZoneId.systemDefault()).format(instant);
	}

	/**
	 * 
	 * @param file 文件
	 * @return 文件大小
	 */
	public static long getFileSize(File file) {
		if (!file.exists() || !file.isFile()) {
			log.error(file.getName() + " is not exists or file!");
			return -1;
		}
		return file.length();
	}

	/**
	 * 计算文件大小，将long类型转换为String类型
	 *
	 * @param file 文件
	 * @return String
	 */
	public static String getFileSizeStr(File file) {
		long filesize = getFileSize(file);
		String ssize;
		double temp = (double) filesize / 1024;
		if (temp >= 1024) {
			temp = temp / 1024;
			if (temp >= 1024) {
				temp = temp / 1024;
				ssize = temp + "000";
				ssize = ssize.substring(0, ssize.indexOf(".") + 3) + "GB";
			} else {
				ssize = temp + "000";
				ssize = ssize.substring(0, ssize.indexOf(".") + 3) + "MB";
			}
		} else {
			ssize = temp + "000";
			ssize = ssize.substring(0, ssize.indexOf(".") + 3) + "KB";
		}
		return ssize;
	}

	/**
	 * 通过其后缀名判断其是否合法,合法后缀名为常见的
	 *
	 * @param suffix 后缀名
	 * @return 合法返回true，不合法返回false
	 */
	public static boolean isSafe(String suffix) {
		suffix = suffix.toLowerCase();
		return suffix.equals("ppt") || suffix.equals("xls") || suffix.equals("pdf") || suffix.equals("docx") || suffix.equals("doc") || suffix.equals("rar") || suffix.equals("zip")
				|| suffix.equals("jpg") || suffix.equals("gif") || suffix.equals("jpeg") || suffix.equals("png") || suffix.equals("svg") || suffix.equals("msi") || suffix.equals("txt")
				|| suffix.equals("pptx") || suffix.equals("xlsx") || suffix.equals("rm") || suffix.equals("rmvb") || suffix.equals("wmv") || suffix.equals("mp4") || suffix.equals("3gp")
				|| suffix.equals("mkv") || suffix.equals("avi");
	}

	/**
	 * 通过其后缀名判断其是否是图片
	 *
	 * @param suffix 后缀名
	 * @return 合法返回true，不合法返回false
	 */
	public static boolean isPic(String suffix) {
		suffix = suffix.toLowerCase();
		return suffix.equals("jpg") || suffix.equals("gif") || suffix.equals("jpeg") || suffix.equals("png");
	}
}
