package org.enhance.swing.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * 文件工具
 * 
 * @author JiangGengchao
 * @datae 2017年12月17日
 */
public class FileUtil {

	/**
	 * 创建目录
	 * 
	 * @param directoryPath
	 * @return
	 */
	public static boolean createDirectory(String directoryPath) {
		File F0 = new File(directoryPath);
		if (!F0.exists()) {
			if (!F0.mkdir()) {

			}
		}
		return true;
	}

	/**
	 * 复制目录
	 * 
	 * @param SrcDirectoryPath
	 * @param DesDirectoryPath
	 * @return
	 */
	public static boolean copyDirectory(String SrcDirectoryPath, String DesDirectoryPath) {
		try {
			File F0 = new File(DesDirectoryPath);
			if (!F0.exists()) {
				if (!F0.mkdir()) {
				}
			} else
				return false;
			File F = new File(SrcDirectoryPath);
			File[] allFile = F.listFiles();
			int totalNum = allFile.length;
			String srcName = "";
			String desName = "";
			int currentFile = 0;
			for (currentFile = 0; currentFile < totalNum; currentFile++) {
				if (!allFile[currentFile].isDirectory()) {
					srcName = allFile[currentFile].toString();
					desName = DesDirectoryPath + "\\" + allFile[currentFile].getName();
					copyFile(srcName, desName);
				} else {
					if (copyDirectory(allFile[currentFile].getPath().toString(), DesDirectoryPath + "\\" + allFile[currentFile].getName().toString())) {
					} else {
						System.out.println("SubDirectory Copy Error!");
					}
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 有效文件名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String validFileName(String fileName) {
		String validS = ":*?\"<>|\\/";
		String r = fileName;
		for (int i = 0; i < validS.length(); i++)
			r = r.replaceAll("\\" + validS.charAt(i), "");
		return r;
	}

	public static void copyFile(String in, String out) throws Exception {
		copyFile(new File(in), new File(out));
	}

	/**
	 * 复制文件
	 * 
	 * @param in
	 * @param out
	 * @throws Exception
	 */
	public static void copyFile(File in, File out) throws Exception {
		FileInputStream fis = new FileInputStream(in);
		FileOutputStream fos = new FileOutputStream(out);
		byte[] buf = new byte[1024];
		int i = 0;
		while ((i = fis.read(buf)) != -1) {
			fos.write(buf, 0, i);
		}
		fis.close();
		fos.close();
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 */
	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			file.delete();
		}
	}

	/**
	 * 打开文件
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public static void openFile(String filePath) throws IOException {
		String cmd = "rundll32 url.dll FileProtocolHandler file://" + filePath;
		@SuppressWarnings("unused")
        Process p = Runtime.getRuntime().exec(cmd);
	}

	/**
	 * 保存文件
	 * 
	 * @param fileame
	 * @param contents
	 */
	public static void saveFile(String fileame, String contents) {
		PrintStream printStream = null;
		try {
			fileame = fileame.replace("\\", "/");
			// 先把已存在的文件删除
			deleteFile(fileame);
			printStream = new PrintStream(fileame, "utf-8");
			printStream.println(contents.toString());// 将字符串写入文件
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (printStream != null)
				printStream.close();
		}
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean existFile(String filePath) {
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			return true;
		}
		return false;
	}
}