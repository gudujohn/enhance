package com.john.mybatis.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * StringUtil
 * 
 * @author JiangGengchao
 * @date 2018年3月16日
 */
public abstract class MybatisStringUtil extends StringUtils {

    private static Logger logger = LoggerFactory.getLogger(MybatisStringUtil.class);

    public static final String COMMA = ",";
    public static final String DEFAULT_DELIMITER = COMMA;

    public static double[] toDoubleArray(String value) {
        return toDoubleArray(value, DEFAULT_DELIMITER);
    }

    public static double[] toDoubleArray(String value, String delimiter) {
        if (MybatisDetect.notEmpty(value)) {
            String[] values = StringUtils.split(value, delimiter);
            double[] doubleValues = new double[values.length];
            for (int i = 0, len = values.length; i < len; i++) {
                doubleValues[i] = Double.parseDouble(values[i]);
            }
            return doubleValues;
        }
        return null;
    }

    public static String escapeBracket(String str) {
        if (str != null) {
            str = escapeRightBracket(escapeLeftBracket(str));
        }
        return str;
    }

    public static String escapeLeftBracket(String str) {
        if (str != null) {
            str = str.replaceAll("\\(", "\\\\(");
        }
        return str;
    }

    public static String escapeRightBracket(String str) {
        if (str != null) {
            str = str.replaceAll("\\)", "\\\\)");
        }
        return str;
    }

    public static short[] toShortArray(String value) {
        return toShortArray(value, DEFAULT_DELIMITER);
    }

    public static short[] toShortArray(String value, String delimiter) {
        if (MybatisDetect.notEmpty(value)) {
            String[] values = StringUtils.split(value, delimiter);
            short[] shortValues = new short[values.length];
            for (int i = 0, len = values.length; i < len; i++) {
                shortValues[i] = Short.parseShort(values[i]);
            }
            return shortValues;
        }
        return null;
    }

    public static Long[] toLongArray(String value) {
        return toLongArray(value, DEFAULT_DELIMITER);
    }

    public static Long[] toLongArray(String value, String delimiter) {
        if (MybatisDetect.notEmpty(value)) {
            String[] values = StringUtils.split(value, delimiter);
            Long[] longValues = new Long[values.length];
            for (int i = 0, len = values.length; i < len; i++) {
                longValues[i] = Long.parseLong(values[i]);
            }
            return longValues;
        }
        return null;
    }

    public static int getByteLength(String str) {
        if (StringUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return str.getBytes("GBK").length;
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("GBK charset not supported", e);
        }
    }

    public static String trim(String string, int byteLength) {
        try {
            if (StringUtils.isNotEmpty(string) && string.getBytes("GBK").length > byteLength) {
                byte[] bytes = string.getBytes("GBK");
                bytes = ArrayUtils.subarray(bytes, 0, byteLength);
                String s = new String(bytes, "GBK");
                return s;
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("GBK charset not supported", e);
        }
        return string;
    }

    public static String escapeVarchar(String value) {
        if (StringUtils.isBlank(value) || value.length() <= 2000) {
            return value;
        }
        return value.substring(0, 1990);
    }

    public static String replaceEnd(String str, String endStr, String replaceStr) {
        if (StringUtils.isBlank(str))
            return StringUtils.EMPTY;
        return str.substring(0, str.lastIndexOf(endStr)) + replaceStr;
    }

    public static String changeSpecialChar(String s) {
        s = s.replace("\\", "\\\\");
        s = s.replace("\r", "\\r");
        s = s.replace("\n", "\\n");
        s = s.replace("\"", "\\\"");
        s = s.replace("\'", "\\\'");
        return s;
    }

    public static String replaceSpecialCharForCatSearch(String s) {
        for (char c = Character.MIN_VALUE; c < Character.MAX_VALUE; c++) {
            if (!Character.isLetterOrDigit(c) && c != 12288 && !(c > 65280 && c < 65375)) {
                // 12288是全角空格
                // 65281是！, 65374是～，之间都是全角符号
                s = StringUtils.replace(s, Character.toString(c), " ");
            }
        }
        return s;
    }

    public static String[] splitByLinebreak(String rawStr) {
        String[] ret = null;
        if (rawStr.contains("\r\n")) {
            ret = rawStr.split("\r\n");
        } else if (rawStr.contains("\r")) {
            ret = rawStr.split("\r");
        } else if (rawStr.contains("\n")) {
            ret = rawStr.split("\n");
        } else {
            ret = new String[] { rawStr };
        }
        return ret;
    }

    public static Map<String, String> keyValuesToMap(String[] strs) {
        Map<String, String> map = new HashMap<>();
        if (MybatisDetect.notEmpty(strs)) {
            for (String str : strs) {
                String[] keyValue = str.split("=");
                if (MybatisDetect.notEmpty(keyValue) && 2 == keyValue.length) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    map.put(key, value);
                }
            }
        }
        return map;
    }

    public static boolean startsWith(String str, String[] prefixs) {
        if (StringUtils.isBlank(str)) {
            return false;
        }

        if (MybatisDetect.isEmpty(prefixs)) {
            return false;
        }

        for (String prefix : prefixs) {
            if (str.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> toStringList(String value) {
        return toStringList(value, DEFAULT_DELIMITER);
    }

    public static List<String> toStringList(String value, String delimiter) {
        if (!MybatisDetect.notEmpty(value)) {
            return null;
        }
        String[] values = StringUtils.split(value, delimiter);

        List<String> listValues = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            listValues.add(i, values[i]);
        }
        return listValues;
    }

    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        // 去掉“-”符号
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    public static String arrayToString(String[] list, String split) {
        if (list == null || list.length == 0)
            return null;

        String result = "";
        if (split == null || split.equals(""))
            split = ",";

        for (String str : list) {
            result += str + split;
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    /**
     * 本方法不适合带中文字符串，如有需要，需修改代码；保证截取的字符串完整
     * 
     * @param sourceStr
     *            原始串，逗号分割
     * @param maxLength
     *            目标串的最大长度
     * @return
     */
    public static String splitWithMaxLength(String sourceStr, int maxLength) {
        StringBuilder sb = new StringBuilder();
        String[] tmpList = sourceStr.split(",");
        for (int i = 0; i < tmpList.length; i++) {
            String curStr = tmpList[i].trim();
            sb.append(curStr);
            if (sb.length() < maxLength) {
                if (i < tmpList.length - 1) { // 还有下一个字符串
                    String nextStr = tmpList[i + 1].trim();
                    if (nextStr.length() + sb.length() + 1 > maxLength) { // 当前sb的长度+下一个字符串长度+逗号的长度(1)
                        break;
                    } else {
                        sb.append(",");
                    }
                }
            } else {
                break;
            }
        }
        return sb.toString();
    }

    public static String escapeQuotes(String str) {
        if (str != null) {
            str = escapeSingleQuotes(str);
            str = escapeDoubleQuotes(str);
        }
        return str;
    }

    public static String escapeSingleQuotes(String str) {
        if (str != null) {
            str = str.replaceAll("'", "\\\\'");
        }
        return str;
    }

    public static String escapeDoubleQuotes(String str) {
        if (str != null) {
            str = str.replaceAll("\"", "\\\\\\\"");
        }
        return str;
    }

    public static String quoteString(String str) {
        return "'" + str + "'";
    }

    public static String arrayToSqlInString(String[] convertStr) {
        String str = "";
        StringBuilder sb = new StringBuilder();
        for (String rv : convertStr) {
            if (MybatisDetect.notEmpty(rv)) {
                sb.append("'").append(rv).append("',");
            }
        }
        if (sb != null && sb.length() > 0) {
            str = sb.toString();
            str = StringUtils.removeEnd(str, ",");
        }
        return str;
    }

    public static String getSplitPartByIndex(String str, String separator, int index) {
        if (MybatisDetect.notEmpty(str)) {
            String[] strs = str.split(separator);
            if (strs.length > index) {
                return strs[index];
            }
        }
        return null;
    }
}
