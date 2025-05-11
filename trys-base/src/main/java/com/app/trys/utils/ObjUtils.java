package com.app.trys.utils;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author linjf
 * @since 2023/4/24
 */
public class ObjUtils {

	public static <T> T defaultIfEmpty(T t, T def) {
		if (t == null || StringUtils.isEmpty(t)) {
			return def;
		}
		return t;
	}

	public static <T, R> R parseIfExist(T t, Function<T, R> parse) {
		if (t == null || StringUtils.isEmpty(t)) {
			return null;
		}
		return parse.apply(t);
	}
}
