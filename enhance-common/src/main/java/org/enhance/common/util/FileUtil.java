package org.enhance.common.util;

import java.io.File;
import java.io.IOException;

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
	 * 
	 * @return
	 */
	public static String getSystemAbsolutePath() {
		String path = ClassLoader.getSystemResource("").getPath();
		return path;
	}

	/**
	 * 获取一个文件，没有就新建一个文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static File getFile(String filePath) {
		File file = new File(filePath);
		if (!exists(filePath)) {
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
		if (!exists(folderPath)) {
			file.mkdirs();
		}
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean exists(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}
}
