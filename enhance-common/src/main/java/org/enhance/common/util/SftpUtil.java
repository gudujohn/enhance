package org.enhance.common.util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.enhance.common.exception.InternalAssertionException;
import org.enhance.common.vo.FtpConfig;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SftpUtil {

	private static final String SEPARATOR = "/";

	public static List<String> listFiles(FtpConfig cfg, String dir) {
		String username = cfg.getUsername();
		String password = cfg.getPassword();
		String serverIp = cfg.getIp();
		int port = cfg.getPort();
		String remotePath = cfg.getDefaultRemoteDir() + SEPARATOR + dir;
		String encoding = cfg.getEncoding();

		List<String> fileNameList = new ArrayList<>();
		ChannelSftp chSftp = null;
		try {
			chSftp = connect(username, password, serverIp, port, encoding);
			@SuppressWarnings("unchecked")
			Vector<ChannelSftp.LsEntry> fileList = chSftp.ls(remotePath);
			fileNameList = getFileNameList(fileList);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		} finally {
			disconnect(chSftp);
		}
		return fileNameList;
	}

	public static boolean putFile(FtpConfig cfg, String dir, File file) {
		return putFile(cfg, dir, file.getName(), file);
	}

	public static boolean putFile(FtpConfig cfg, String dir, String remoteFileName, File file) {
		ChannelSftp chSftp = null;
		try {
			chSftp = connect(cfg.getUsername(), cfg.getPassword(), cfg.getIp(), cfg.getPort(), cfg.getEncoding());

			String remoteDir = generateRemoteDir(cfg.getDefaultRemoteDir(), dir);
			checkRemoteDir(chSftp, remoteDir);

			String sftpFilePath = remoteDir + SEPARATOR + remoteFileName;
			chSftp.put(file.getAbsolutePath(), sftpFilePath);
			return true;
		} catch (Throwable e) {
			throw new InternalAssertionException("文件上传失败: " + e.getMessage(), e);
		} finally {
			disconnect(chSftp);
		}
	}

	public static void deleteFiles(FtpConfig cfg, String dir, List<String> fileNames) {
		String username = cfg.getUsername();
		String password = cfg.getPassword();
		String serverIp = cfg.getIp();
		int port = cfg.getPort();
		String remotePath = cfg.getDefaultRemoteDir() + SEPARATOR + dir;
		String encoding = cfg.getEncoding();

		ChannelSftp chSftp = null;
		try {
			chSftp = connect(username, password, serverIp, port, encoding);
			chSftp.cd(remotePath);
			@SuppressWarnings("unchecked")
			Vector<ChannelSftp.LsEntry> fileList = chSftp.ls(remotePath);
			List<String> fileNameList = getFileNameList(fileList);
			for (String fileName : fileNames) {
				if (fileNameList.contains(fileName)) {
					chSftp.rm(fileName);
				}
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		} finally {
			disconnect(chSftp);
		}
	}

	private static void checkRemoteDir(ChannelSftp chSftp, String remoteDir) {
		String[] dirs = remoteDir.split("/");
		String checkRemoteDir = StringUtils.EMPTY;
		for (String tempDir : dirs) {
			if (Detect.isEmpty(tempDir)) {
				continue;
			}
			checkRemoteDir = checkRemoteDir + SEPARATOR + tempDir;
			try {
				chSftp.cd(checkRemoteDir);
			} catch (Exception e) {
				try {
					chSftp.mkdir(checkRemoteDir);
				} catch (Exception e1) {
					throw new InternalAssertionException("make dir faile:" + checkRemoteDir, e1);
				}
			}
		}
	}

	private static String generateRemoteDir(String defaultRemoteDir, String subDir) {
		String[] defaultRemoteDirs = Detect.notEmpty(defaultRemoteDir) ? defaultRemoteDir.equals(SEPARATOR) ? new String[] { StringUtils.EMPTY } : defaultRemoteDir.split(SEPARATOR)
				: ArrayUtils.EMPTY_STRING_ARRAY;
		String[] subDirs = Detect.notEmpty(subDir) ? subDir.split(SEPARATOR) : ArrayUtils.EMPTY_STRING_ARRAY;
		String[] remoteDirs = ArrayUtils.addAll(defaultRemoteDirs, subDirs);
		return StringUtils.join(remoteDirs, SEPARATOR);
	}

	private static List<String> getFileNameList(Vector<ChannelSftp.LsEntry> fileList) {
		List<String> fileNameList = new ArrayList<>();
		if (Detect.notEmpty(fileList)) {
			for (ChannelSftp.LsEntry lsEntry : fileList) {
				String fileName = lsEntry.getFilename();
				if (".".equals(fileName) || "..".equals(fileName)) {
					continue;
				}
				fileNameList.add(fileName);
			}
		}
		return fileNameList;
	}

	private static void setEncoding(ChannelSftp chSftp, String encoding) {
		try {
			// 3-5版本不能设置编码，使用反射绕过限制
			if (chSftp != null) {
				Class<ChannelSftp> cl = ChannelSftp.class;
				Field f1 = cl.getDeclaredField("server_version");
				f1.setAccessible(true);
				f1.set(chSftp, 2);
				chSftp.setFilenameEncoding(StringUtils.upperCase(encoding));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new InternalAssertionException(e);
		}
	}

	private static void disconnect(ChannelSftp channelSftp) {
		if (channelSftp != null && channelSftp.isConnected()) {
			channelSftp.disconnect();
			try {
				Session session = channelSftp.getSession();
				if (session != null) {
					session.disconnect();
				}
			} catch (Exception e) {
				log.error("关闭Sftp对象中的session异常：", e);
			}
		}
	}

	private static ChannelSftp connect(String username, String password, String serverIp, int port, String encoding) {
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(username, serverIp, port);
			session.setPassword(password);
			session.setTimeout(100000);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
			channel.connect();
			if (Detect.notEmpty(encoding)) {
				setEncoding(channel, encoding);
			}
			return channel;
		} catch (Exception e) {
			log.error(e.getMessage() + "username={}, password={}, serverIp={}, port={}, encoding={}", username, password, serverIp, port, encoding);
			throw new InternalAssertionException(e);
		}
	}
}