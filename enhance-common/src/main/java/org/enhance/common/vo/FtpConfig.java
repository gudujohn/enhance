package org.enhance.common.vo;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.enhance.common.util.Detect;
import org.enhance.common.util.ObjectUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FtpConfig {

	private String ip;
	private int port;
	private String username;
	private String password;
	private String defaultRemoteDir;
	private String encoding = "gbk";
	private boolean passiveMode = true;

	public FtpConfig() {

	}

	/**
	 * ip=192.168.120.152;port=21;username=irmds;password=aaaaaa;defaultDir=/ attachment;encoding=utf-8 <br />
	 * 以分号分隔的key=value的字符串, 顺序可以不固定, 但key值要保持一致 ip,port,username,password,defaultDir,encoding <br />
	 * 
	 * @param formatString
	 */
	public FtpConfig(String formatString) {
		if (Detect.notEmpty(formatString)) {
			String[] configs = formatString.split(";");
			if (Detect.notEmpty(configs)) {
				for (int i = 0; i < configs.length; i++) {
					String key = configs[i].split("=")[0];
					String value = configs[i].split("=")[1];
					if ("ip".equalsIgnoreCase(key)) {
						this.ip = value;
					} else if ("port".equalsIgnoreCase(key)) {
						this.port = Integer.valueOf(value);
					} else if ("username".equalsIgnoreCase(key)) {
						this.username = value;
					} else if ("password".equalsIgnoreCase(key)) {
						this.password = value;
					} else if ("root".equalsIgnoreCase(key) || "defaultDir".equalsIgnoreCase(key) || "defaultRemoteDir".equalsIgnoreCase(key)) {
						this.defaultRemoteDir = value;
					} else if ("encoding".equalsIgnoreCase(key)) {
						this.encoding = value;
					} else if ("passiveMode".equalsIgnoreCase(key)) {
						this.passiveMode = ObjectUtil.asPrimitiveBoolean(value, true);
					}
				}
			}
		}
	}

	public boolean initFinished() {
		return Detect.notEmpty(ip) && (0 < port) && Detect.notEmpty(username) && Detect.notEmpty(password) && Detect.notEmpty(encoding);
	}

	@Override
	public String toString() {
		ToStringBuilder tsBuilder = new ToStringBuilder(this);
		tsBuilder.append("ip=" + ip);
		tsBuilder.append("port=" + port);
		tsBuilder.append("username=" + username);
		tsBuilder.append("password=" + password);
		tsBuilder.append("defaultRemoteDir=" + defaultRemoteDir);
		tsBuilder.append("encoding=" + encoding);
		tsBuilder.append("passiveMode=" + passiveMode);
		return tsBuilder.toString();
	}

	public boolean compareByBasicProperties(FtpConfig otherFtpConfig) {
		return StringUtils.equals(ip, otherFtpConfig.getIp()) //
				&& port == otherFtpConfig.getPort() //
				&& StringUtils.equals(username, otherFtpConfig.getUsername()) //
				&& StringUtils.equals(password, otherFtpConfig.getPassword()); //
	}

	public String generateFtpPrefix() {
		return "ftp://" + this.username + ":" + this.password + "@" + this.ip + ":" + this.port;
	}
}