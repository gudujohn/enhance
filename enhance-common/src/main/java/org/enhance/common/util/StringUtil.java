package org.enhance.common.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	public static boolean startsWith(String str, String[] prefixs) {
		if (StringUtils.isBlank(str)) {
			return false;
		}

		if (Detect.isEmpty(prefixs)) {
			return false;
		}

		for (String prefix : prefixs) {
			if (str.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}
}
