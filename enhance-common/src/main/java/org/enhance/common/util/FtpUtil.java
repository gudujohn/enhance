package org.enhance.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;
import org.apache.commons.net.ftp.FTPReply;
import org.enhance.common.exception.InternalAssertionException;
import org.enhance.common.vo.FtpConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FtpUtil {

	private static final String SEPARATOR = "/";

	public static List<String> listFiles(FtpConfig cfg, String dir) {
		List<String> fileNames = new ArrayList<>();

		String remotePath = generateRemoteDir(cfg.getDefaultRemoteDir(), dir);
		int fileCacheSize = 5;
		FTPClient ftpClient = null;
		try {
			ftpClient = connect(cfg);
			FTPListParseEngine f = ftpClient.initiateListParsing(remotePath);
			while (null != f && f.hasNext()) {
				FTPFile[] files = f.getNext(fileCacheSize);
				for (FTPFile file : files) {
					fileNames.add(file.getName());
				}
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		} finally {
			disconnect(ftpClient);
		}
		return fileNames;
	}

	public static boolean putFile(FtpConfig cfg, String dir, File file) {
		return putFile(cfg, dir, file.getName(), file);
	}

	public static boolean putFile(FtpConfig cfg, String dir, String remoteFileName, File file) {
		try (FileInputStream inputStream = new FileInputStream(file)) {
			return putFile(cfg, dir, remoteFileName, inputStream);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

	public static boolean putFile(FtpConfig cfg, String dir, String fileName, InputStream fileInputStream) {
		FTPClient ftpClient = null;

		if (Detect.notEmpty(fileName)) {
			try {
				ftpClient = connect(cfg);
				String remoteDir = generateRemoteDir(cfg.getDefaultRemoteDir(), dir);

				changeDirecroty(ftpClient, remoteDir);
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.storeFile(fileName, fileInputStream);
				return true;
			} catch (Throwable e) {
				log.error(e.getMessage(), e);
			} finally {
				disconnect(ftpClient);
			}
		}
		return false;
	}

	public static void deleteFiles(FtpConfig cfg, String dir, List<String> fileNames) {
		FTPClient ftpClient = null;
		try {
			ftpClient = connect(cfg);
			String remoteDir = generateRemoteDir(cfg.getDefaultRemoteDir(), dir);
			ftpClient.changeWorkingDirectory(remoteDir);
			String[] fileNamesOnServer = ftpClient.listNames();
			Collections.sort(fileNames);
			if (Detect.notEmpty(fileNames) && Detect.notEmpty(fileNamesOnServer)) {
				for (String s : fileNamesOnServer) {
					if (-1 < Collections.binarySearch(fileNames, s)) {
						ftpClient.dele(s);
					}
				}
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		} finally {
			disconnect(ftpClient);
		}
	}

	private static String generateRemoteDir(String defaultRemoteDir, String subDir) {
		String[] defaultRemoteDirs = Detect.notEmpty(defaultRemoteDir) ? defaultRemoteDir.equals(SEPARATOR) ? new String[] {StringUtils.EMPTY} : defaultRemoteDir.split(SEPARATOR) : ArrayUtils.EMPTY_STRING_ARRAY;
		String[] subDirs = Detect.notEmpty(subDir) ?  subDir.split(SEPARATOR) : ArrayUtils.EMPTY_STRING_ARRAY;
		String[] remoteDirs = ArrayUtils.addAll(defaultRemoteDirs, subDirs);
		return StringUtils.join(remoteDirs, SEPARATOR);
	}

	private static void changeDirecroty(FTPClient ftpClient, String remoteDir) throws Exception {
		String directory = remoteDir;
		if (!directory.endsWith(SEPARATOR)) {
			directory += SEPARATOR;
		}
		if (!directory.equalsIgnoreCase(SEPARATOR) && !ftpClient.changeWorkingDirectory(directory)) {
			int start = 0;
			if (directory.startsWith(SEPARATOR)) {
				start = 1;
			}
			int end = directory.indexOf(SEPARATOR, start);
			String path = "";
			while (end > start) {
				String subDirectory = remoteDir.substring(start, end);
				path = path + SEPARATOR + subDirectory;
				if (!ftpClient.changeWorkingDirectory(path)) {
					if (ftpClient.makeDirectory(subDirectory)) {
						ftpClient.changeWorkingDirectory(subDirectory);
					} else {
						log.error("创建目录[" + subDirectory + "]失败");
						ftpClient.changeWorkingDirectory(subDirectory);
					}
				} else {
					ftpClient.changeWorkingDirectory(subDirectory);
				}

				start = end + 1;
				end = directory.indexOf(SEPARATOR, start);
			}
		}
	}

	private static FTPClient connect(FtpConfig ftpConfig) {
		return connect(ftpConfig, 10000);
	}

	private static FTPClient connect(FtpConfig ftpConfig, int dataTimeout) {
		String serverIp = ftpConfig.getIp();
		String username = ftpConfig.getUsername();
		String password = ftpConfig.getPassword();
		int port = ftpConfig.getPort();
		String encoding = ftpConfig.getEncoding();
		boolean passiveMode = ftpConfig.isPassiveMode();

		FTPClient ftpClient = null;
		try {
			ftpClient = new FTPClient();
			int connectTime = Integer.parseInt(System.getProperty("JxConnectionTimeOut", "3000"));
			ftpClient.setConnectTimeout(connectTime);
			ftpClient.setControlEncoding(encoding);
			ftpClient.connect(serverIp, port);
			ftpClient.login(username, password);
			int returnCode;
			if (passiveMode) {
				ftpClient.enterLocalPassiveMode();
				ftpClient.pasv();
			}
			returnCode = ftpClient.getReplyCode();
			ftpClient.setDataTimeout(dataTimeout);
			if (!FTPReply.isPositiveCompletion(returnCode)) {
				if (log.isDebugEnabled()) {
					log.debug("FTP server refused connection.returnCode=" + returnCode);
				}
				throw new InternalAssertionException("FTP server refused connection.returnCode=" + returnCode);
			}
			if (log.isDebugEnabled()) {
				log.debug("connected to FTP server. ip=" + serverIp + ", port=" + port);
			}
			return ftpClient;
		} catch (Throwable e) {
			log.error("username=" + username + ", password=" + password + ", serverIp=" + serverIp + ", port=" + port + ", encoding=" + encoding, e);
			if (null != ftpClient) {
				try {
					ftpClient.disconnect();
				} catch (IOException ignored) {
				}
			}
			throw new InternalAssertionException(e);
		}
	}

	/**
	 * 优化下： ftp.isConnected最好要写在finally里面，不要直接写在ftp.logout()后面, 这样子写会有很大的隐患。
	 *
	 * @param ftpClient
	 */
	private static void disconnect(FTPClient ftpClient) {
		if (null != ftpClient) {
			try {
				ftpClient.logout();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			} finally {
				if (ftpClient.isConnected()) {
					try {
						ftpClient.disconnect();
					} catch (IOException ignored) {
					}
				}
			}
		}
	}

}
