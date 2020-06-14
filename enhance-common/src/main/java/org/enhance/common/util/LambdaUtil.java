package org.enhance.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * org.enhance.common.util-LambdaUtil lambda工具
 *
 * @classname: LambdaUtil
 * @description: lambda工具
 * @author: JiangGengchao
 * @date: 2020-06-14
 **/
public class LambdaUtil {
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
}
