package org.enhance.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;
import org.enhance.common.exception.InternalAssertionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TarUtil {

	private static final int BUFFER_SIZE = 2048;

	/**
	 * 文件filesToTar打包到完整路径名tarPath
	 * 
	 * @param filesToTar
	 * @param fileName
	 * @throws IOException
	 */
	public static void tar(List<File> filesToTar, String fileName) {
		try (FileOutputStream fos = new FileOutputStream(fileName); TarArchiveOutputStream tos = new TarArchiveOutputStream(fos)) {
			StopWatch watch = new StopWatch();
			for (File file : filesToTar) {
				watch.start();
				String tempFileName = file.getName();
				try (InputStream ins = new FileInputStream(file)) {
					log.debug("File:{} is on compressing.", tempFileName);
					tos.putArchiveEntry(new TarArchiveEntry(file));
					IOUtils.copy(ins, tos);
					tos.closeArchiveEntry();
				}
				watch.stop();
				log.debug("File:{};tar compress complete. Cost {} ms.", tempFileName, watch.getLastTaskTimeMillis());
			}
			log.info("Tar File:{} compression complete. Total cost {} ms", fileName, watch.getTotalTimeMillis());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new InternalAssertionException("tar打包文件失败", e);
		}
	}

	/**
	 * 解压tar.gz 文件
	 * 
	 * @param file      要解压的tar.gz文件对象
	 * @param outputDir 要解压到某个指定的目录下
	 * @throws IOException
	 */
	public static void unTar(File file, String outputDir) {
		try (TarInputStream tarIn = new TarInputStream(new BufferedInputStream(new FileInputStream(file)), 1024 * 2)) {
			createDirectory(outputDir, null);// 创建输出目录

			TarEntry entry = null;
			while ((entry = tarIn.getNextEntry()) != null) {
				if (entry.isDirectory()) {// 是目录
					entry.getName();
					createDirectory(outputDir, entry.getName());// 创建空目录
				} else {// 是文件
					File tmpFile = new File(outputDir + "/" + entry.getName());
					createDirectory(tmpFile.getParent() + "/", null);// 创建输出目录
					try (OutputStream out = new FileOutputStream(tmpFile)) {
						int length = 0;
						byte[] b = new byte[2048];
						while ((length = tarIn.read(b)) != -1) {
							out.write(b, 0, length);
						}
					}
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new InternalAssertionException("解压归档文件出现异常", e);
		}
	}

	/**
	 * 构建目录
	 * 
	 * @param outputDir
	 * @param subDir
	 */
	public static void createDirectory(String outputDir, String subDir) {
		File file = new File(outputDir);
		if (!(subDir == null || subDir.trim().equals(""))) {// 子目录不为空
			file = new File(outputDir + "/" + subDir);
		}
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.mkdirs();
		}
	}

}