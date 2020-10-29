package org.enhance.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MD5Util {

	private static final String MD5_ALGORITHM = "MD5";
	private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String generateFileMD5(File file) {
		try (InputStream fis = new FileInputStream(file)) {
			return generateStreamMD5(fis);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public static String generateStreamMD5(InputStream inputStream) {
		MessageDigest md5MessageDigest = null;
		try {
			md5MessageDigest = MessageDigest.getInstance(MD5_ALGORITHM);
			byte[] buffer = new byte[1024];
			int numRead;
			while (0 < (numRead = inputStream.read(buffer))) {
				md5MessageDigest.update(buffer, 0, numRead);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		assert md5MessageDigest != null;
		return bufferToHex(md5MessageDigest.digest());
	}

	private static String bufferToHex(byte[] bytes) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte[] bytes, int m, int n) {
		StringBuilder sb = new StringBuilder(2 * n);
		int k = m + n;
		for (int l = m; l < k; ++l) {
			char c0 = hexDigits[((bytes[l] & 0xF0) >> 4)];
			char c1 = hexDigits[(bytes[l] & 0xF)];
			sb.append(c0);
			sb.append(c1);
		}
		return sb.toString();
	}

}
