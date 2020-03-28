package com.john.mybatis.util;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.Assert;

/**
 * AssertionUtil
 * 
 * @author JiangGengchao
 * @date 2018年3月16日
 */
public abstract class AssertionUtil extends Assert {

    public static void isNegative(double value, String message) {
        if (!MybatisDetect.isNegative(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isPositive(double value, String message) {
        if (!MybatisDetect.isPositive(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    /* notEmpty */
    public static void notEmpty(String[] string, String message) {
        if (!MybatisDetect.notEmpty(string)) {
            throw new IllegalArgumentException(message);
        }
    }

    /* notEmpty */
    public static void notEmpty(byte[] bytes, String message) {
        if (!MybatisDetect.notEmpty(bytes)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(long[] values, String message) {
        if (!MybatisDetect.notEmpty(values)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(int[] values, String message) {
        if (!MybatisDetect.notEmpty(values)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(short[] values, String message) {
        if (!MybatisDetect.notEmpty(values)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(List<?> list, String message) {
        if (!MybatisDetect.notEmpty(list)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(String string, String message) {
        if (!MybatisDetect.notEmpty(string)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Date date, String message) {
        if (null == date) {
            throw new IllegalArgumentException(message);
        }
    }

    /* isEmpty */
    public static void isEmpty(String[] string, String message) {
        if (MybatisDetect.notEmpty(string)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isEmpty(long[] values, String message) {
        if (MybatisDetect.notEmpty(values)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isEmpty(List<?> list, String message) {
        if (MybatisDetect.notEmpty(list)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isEmpty(String string, String message) {
        if (MybatisDetect.notEmpty(string)) {
            throw new IllegalArgumentException(message);
        }
    }

    /* onlyOne */
    public static void onlyOne(List<?> list, String message) {
        if (!MybatisDetect.onlyOne(list)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void equals(Object obj1, Object obj2, String message) {
        if (null == obj1 && null == obj2) {
            return;
        }
        if (null != obj1 && null != obj2 && obj1.equals(obj2)) {
            return;
        }
        throw new IllegalArgumentException(message);
    }

    /**
     * 如果两者是同一个则报错
     * 
     * @param obj1
     * @param obj2
     * @param message
     */
    public static void notEquals(Object obj1, Object obj2, String message) {
        if (null == obj1 && null == obj2) {
            throw new IllegalArgumentException(message);
        }
        if (null != obj1 && null != obj2 && obj1.equals(obj2)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNullAndNotEquals(Object obj1, Object obj2, String message) {
        if (null == obj1 || null == obj2) {
            throw new IllegalArgumentException(message);
        }
        if (obj1.equals(obj2)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验value是否是纯数字 NumberUtils.isDigits： "0512" -> true; "5.96" -> fasle; "s5" ->
     * false;
     * 
     * @param value
     * @param message
     */
    public static void isDigits(String value, String message) {
        if (!NumberUtils.isDigits(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验value是否是数字（小数） NumberUtils.isNumber: "5.96" -> true; "5s" -> false; "00644"
     * -> true;
     * 
     * @param value
     * @param message
     */
    public static void isNumber(String value, String message) {
        if (!NumberUtils.isCreatable(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验value的长度是否大于length
     * 
     * @param value
     * @param length
     * @param message
     */
    public static void greaterThanLength(String value, int length, String message) {
        if (value.length() <= length) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验value的长度是否大于等于length
     * 
     * @param value
     * @param length
     * @param message
     */
    public static void greaterThanOrEqualLength(String value, int length, String message) {
        if (value.length() < length) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验value的长度是否小于length
     * 
     * @param value
     * @param length
     * @param message
     */
    public static void lessThanLength(String value, int length, String message) {
        if (value.length() >= length) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验value的长度是否小于等于length
     * 
     * @param value
     * @param length
     * @param message
     */
    public static void lessThanOrEqualLength(String value, int length, String message) {
        if (value.length() > length) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验value长度是否在开区间内
     * 
     * @param value
     * @param min
     * @param max
     * @param message
     */
    public static void betweenLength(String value, int min, int max, String message) {
        if (min >= max) {
            throw new IllegalArgumentException("最小值: " + min + "不能大于等于最大值: " + max);
        }
        if (value.length() <= min || value.length() >= max) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验value长度是否在闭区间内
     * 
     * @param value
     * @param min
     * @param max
     * @param message
     */
    public static void betweenOrEqualLength(String value, int min, int max, String message) {
        if (min > max) {
            throw new IllegalArgumentException("最小值: " + min + "不能大于最大值: " + max);
        }
        if (value.length() < min || value.length() > max) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验value的字节长度是否小于等于length
     * 
     * @param value
     * @param length
     * @param message
     */
    public static void lessThanOrEqualLengthInByte(String value, int length, String message) {
        if (value.getBytes().length > length) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断str不能包含数组keys中的字符串，否则抛错
     * 
     * @param str
     * @param keys
     * @param message
     */
    public static void notContains(String str, String[] keys, String message) {
        if (null == str) {
            throw new IllegalArgumentException(message);
        }
        for (int i = 0; i < keys.length; i++) {
            if (str.contains(keys[i])) {
                throw new IllegalArgumentException(message);
            }
        }
    }

    /**
     * 检测value是否ipv4格式的ip地址 匹配 127.0.0.1 | 255.255.255.0 | 192.168.0.1 不匹配
     * 1200.5.4.3 | abc.def.ghi.jkl | 255.foo.bar.1
     * 
     * @param value
     * @param message
     */
    public static void isIpV4NetworkAddress(String value, String message) {
        Pattern pattern = Pattern.compile(
                "^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$");
        if (!pattern.matcher(value).matches()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 检测value是否ipv4格式的带掩码的ip地址 匹配 192.168.0.1/32 | 255.255.0.0/1 不匹配 010.0.0.0 |
     * 192.168.0.1/33 | 256.0.1.55
     * 
     * @param value
     * @param message
     */
    public static void isIpV4NetworkAddressWithMask(String value, String message) {
        Pattern pattern = Pattern
                .compile("^((0|1[0-9]{0,2}|2[0-9]{0,1}|2[0-4][0-9]|25[0-5]|[3-9][0-9]{0,1})\\.){3}(0|1[0-9]{0,2}|2[0-9]{0,1}|2[0-4][0-9]|25[0-5]|[3-9][0-9]{0,1})(\\/([0-9]|[1-2][0-9]|3[0-2]))$");
        if (!pattern.matcher(value).matches()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断str取值是否在字符串数组列表中 如果str不匹配字符串数组中任意一个字符串则抛错
     * 
     * @param value
     * @param enums
     * @param message
     */
    public static void in(String str, String[] enums, String message) {
        if (!MybatisDetect.notEmpty(enums)) {
            throw new IllegalArgumentException(message + "，枚举集合为空");
        }
        boolean match = false;
        for (int i = 0; i < enums.length; i++) {
            if (null != enums[i] && enums[i].equals(str)) {
                match = true;
                break;
            }
        }
        if (!match) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 如果str是以key结束的则ok，否则报错
     * 
     * @param str
     * @param key
     * @param message
     */
    public static void endsWith(String str, String key, String message) {
        if (null != str && str.endsWith(key)) {
            return;
        } else {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 匹配正则表达式
     * 
     * @param value
     * @param regexp
     * @param message
     */
    public static void matchRegexp(String value, String regexp, String message) {
        if (!value.matches(regexp)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断是否为小数
     * 
     * @param value
     * @param message
     */
    public static void isDecimal(String value, String message) {
        matchRegexp(value, "\\d+\\.\\d+$|-\\d+\\.\\d+$", message);
    }

}