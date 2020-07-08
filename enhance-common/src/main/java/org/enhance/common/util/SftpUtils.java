package org.enhance.common.util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.enhance.common.exception.InternalAssertionException;
import org.enhance.common.vo.FtpConfig;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SftpUtils {

	private static final String SEPARATOR = "/";

	public static List<String> listFiles(FtpConfig cfg, String dir) {
		String username = cfg.getUsername();
		String password = cfg.getPassword();
		String serverIp = cfg.getIp();
		int port = cfg.getPort();
		String remotePath = cfg.getDefaultRemoteDir() + SEPARATOR + dir;
		String encoding = cfg.getEncoding();

		List<String> fileNameList = new ArrayList<>();
		SFtpUtilOptions sFtpUtilOptions = null;
		try {
			sFtpUtilOptions = connect(username, password, serverIp, port, encoding);
			ChannelSftp chSftp = sFtpUtilOptions.getChSftp();
			Vector<ChannelSftp.LsEntry> fileList = chSftp.ls(remotePath);
			fileNameList = getFileNameList(fileList);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		} finally {
			disconnect(sFtpUtilOptions);
		}
		return fileNameList;
	}
//
//	/**
//	 * 
//	 * @Title: getFile @Description: 下载文件到本地目录 @param @param username @param @param password @param @param sftpServerIp @param @param port @param @param remotePath @param @param fileName @param @param
//	 *         localPath @param @param encoding @param @return 参数 @author liumenglong @return boolean 返回类型 @throws
//	 */
//	public static boolean getFile(String username, String password, String sftpServerIp, int port, String remotePath, String fileName, //
//			String localPath, String encoding) {
//		remotePath = analysisFilePath(remotePath, fileName);
//		fileName = analysisFileName(fileName);
//		if (localPath != null) {
//			File localDir = new File(localPath);
//			if (!localDir.exists()) {
//				localDir.mkdirs();
//			}
//		}
//		SFtpUtilOptions sFtpUtilOptions = null;
//		try {
//			sFtpUtilOptions = connect(username, password, sftpServerIp, port, encoding);
//			ChannelSftp chSftp = sFtpUtilOptions.getChSftp();
//			String sftpFilePath = remotePath + SEPARATOR + fileName;
//			String localFilePath = localPath + File.separator + fileName;
//			chSftp.get(sftpFilePath, localFilePath);
//			return true;
//		} catch (Throwable e) {
//			log.error(e.getMessage(), e);
//		} finally {
//			disconnect(sFtpUtilOptions);
//		}
//		return false;
//	}
//
//	/**
//	 * 分析ftp的文件名称 传参时有可能用户不设置默认根路径，而把路径完整信息放在文件名上，所以需要额外分析，设置文件名称
//	 *
//	 * @param fileName
//	 * @return
//	 */
//	static String analysisFileName(String fileName) {
//		if (!Detect.notEmpty(fileName)) {
//			throw new IllegalArgumentException("文件名不能为空");
//		} else {
//			int idx = fileName.lastIndexOf("/");
//			if (0 > idx) {
//				idx = fileName.lastIndexOf("\\");
//				if (0 > idx) {
//					// idx = 0;
//					// 文件名不是以\或/开头则原样返回
//					return fileName;
//				}
//			}
//			fileName = fileName.substring(idx + 1);
//			return fileName;
//		}
//	}
//
//	/**
//	 * 分析ftp的文件目录路径 传参时有可能用户不设置默认根路径，而把路径完整信息放在文件名上，所以需要额外分析，设置文件的目录路径
//	 *
//	 * @param remotePath
//	 * @param fileName
//	 * @return
//	 */
//	static String analysisFilePath(String remotePath, String fileName) {
//		if (Detect.notEmpty(remotePath)) {
//			return remotePath;
//		} else if (!Detect.notEmpty(fileName)) {
//			throw new IllegalArgumentException("文件名不能为空");
//		} else {
//			String path;
//			int idx = fileName.lastIndexOf("/");
//			if (0 > idx) {
//				idx = fileName.lastIndexOf("\\");
//				if (0 > idx) {
//					// idx = 0;
//					// 路径为空，文件名不是以\或/开头则默认是根路径
//					return "/";
//				}
//			}
//			path = fileName.substring(0, idx + 1);
//			return path;
//		}
//	}

	public static void setEncoding(ChannelSftp chSftp, String encoding) {
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

	public static void deleteFiles(FtpConfig cfg, String dir, List<String> fileNames) {
		String username = cfg.getUsername();
		String password = cfg.getPassword();
		String serverIp = cfg.getIp();
		int port = cfg.getPort();
		String remotePath = cfg.getDefaultRemoteDir() + SEPARATOR + dir;
		String encoding = cfg.getEncoding();

		SFtpUtilOptions sFtpUtilOptions = null;
		try {
			sFtpUtilOptions = connect(username, password, serverIp, port, encoding);
			ChannelSftp chSftp = sFtpUtilOptions.getChSftp();
			chSftp.cd(remotePath);
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
			disconnect(sFtpUtilOptions);
		}
	}

	public static List<String> getFileNameList(Vector<ChannelSftp.LsEntry> fileList) {
		List<String> fileNameList = new ArrayList<String>();
		if (Detect.notEmpty(fileList)) {
			Iterator<ChannelSftp.LsEntry> it = fileList.iterator();
			while (it.hasNext()) {
				String fileName = it.next().getFilename();
				if (".".equals(fileName) || "..".equals(fileName)) {
					continue;
				}
				fileNameList.add(fileName);
			}
		}
		return fileNameList;

	}

	private static void disconnect(SFtpUtilOptions sFtpUtilOptions) {
		if (sFtpUtilOptions != null) {
			if (sFtpUtilOptions.getChSftp() != null) {
				sFtpUtilOptions.getChSftp().quit();
			}
			if (sFtpUtilOptions.getChannel() != null) {
				sFtpUtilOptions.getChannel().disconnect();
			}
			if (sFtpUtilOptions.getChannel() != null) {
				sFtpUtilOptions.getChannel().disconnect();
			}
		}
	}

	public static boolean putFile(FtpConfig cfg, String dir, File file) {
		SFtpUtilOptions sFtpUtilOptions = null;
		try {
			sFtpUtilOptions = connect(cfg.getUsername(), cfg.getPassword(), cfg.getIp(), cfg.getPort(), cfg.getEncoding());
			ChannelSftp chSftp = sFtpUtilOptions.getChSftp();
			String fileName = file.getName();
			String sftpFilePath;
			if (Detect.notEmpty(dir)) {
				String remoteDir = cfg.getDefaultRemoteDir() + SEPARATOR + dir;
				try {
					Vector content = chSftp.ls(remoteDir);
					if (content == null) {
						chSftp.mkdir(remoteDir);
					}
				} catch (Exception e1) {
					chSftp.mkdir(remoteDir);
				}
				sftpFilePath = remoteDir + SEPARATOR + fileName;
			} else {
				sftpFilePath = cfg.getDefaultRemoteDir() + SEPARATOR + fileName;
			}
			chSftp.put(file.getAbsolutePath(), sftpFilePath);
			return true;
		} catch (Throwable e) {
			throw new InternalAssertionException("文件上传失败: " + e.getMessage(), e);
		} finally {
			disconnect(sFtpUtilOptions);
		}
	}

	private static SFtpUtilOptions connect(String username, String password, String serverIp, int port, String encoding) {
		try {
			SFtpUtilOptions sFtpUtilOptions = new SFtpUtilOptions();
			JSch jsch = new JSch();
			Session session = null;
			Channel channel = null;
			session = jsch.getSession(username, serverIp, port);
			session.setPassword(password);
			session.setTimeout(100000);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			sFtpUtilOptions.setSession(session);
			sFtpUtilOptions.setChannel(channel);
			if (Detect.notEmpty(encoding)) {
				setEncoding(sFtpUtilOptions.getChSftp(), encoding);
			}
			return sFtpUtilOptions;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new InternalAssertionException(e);
		}
	}

}

class SFtpUtilOptions {

	Session session = null;
	Channel channel = null;

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public ChannelSftp getChSftp() {
		return (ChannelSftp) channel;
	}

}