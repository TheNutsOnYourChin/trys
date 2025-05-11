package com.app.trys.exceptions;

import com.app.trys.utils.CollUtils;
import com.app.trys.utils.ObjUtils;
import com.app.trys.utils.StringUtils;

import java.text.MessageFormat;
import java.util.*;

/**
 * @author linjf
 * @since 2023/2/16
 */
public class ExceptionUtils {

	public static final String indent = "        ";
	public static final String at = indent + "at ";
	public static final String point = ".";
	public static final String left_bracket = "(";
	public static final String right_bracket = ")";
	public static final String colon = ": ";
	public static final String nextLine = "\r\n";

	/**
	 * 模仿报错格式输出异常方法栈
	 */
	public static String buildStackInfo(Throwable e) {
		List<StackTraceElement> stackTraceList = new ArrayList<>();
		// 到main方法为止
		for (StackTraceElement item : e.getStackTrace()) {
			if (Objects.equals(item.getMethodName(), "main") || item.getMethodName().contains("$main$")) {
				stackTraceList.add(item);
				break;
			}
			stackTraceList.add(item);
		}
		StringBuilder stackInfo = new StringBuilder(ObjUtils.defaultIfEmpty(e.getMessage(), StringUtils.EMPTY));
		e = ObjUtils.defaultIfEmpty(e.getCause(), e);
		stackInfo.append(nextLine);
		String exceptionLine = "Exception in thread \"{0}\" {1}: {2}\r\n";
		String threadName = Thread.currentThread().getName();
		String exceptionType = e.getClass().getName();
		stackInfo.append(MessageFormat.format(exceptionLine, threadName, exceptionType, e.getMessage()));
		for (StackTraceElement item : stackTraceList) {
			stackInfo.append(at);
			stackInfo.append(item.getClassName());
			stackInfo.append(point);
			stackInfo.append(item.getMethodName());
			stackInfo.append(left_bracket);
			stackInfo.append(item.getFileName());
			if (item.getLineNumber() > 0) {
				stackInfo.append(colon);
				stackInfo.append(item.getLineNumber());
			}
			stackInfo.append(right_bracket);
			stackInfo.append(nextLine);
		}
		return stackInfo.toString();
	}

	public static BaseException newException(Exception e, String errorMsg, Object... params) {
		return new BaseException(e, errorMsg, params);
	}

	public static BaseException newException(String errorMsg, Object... params) {
		return new BaseException(errorMsg, params);
	}

	public static void isTure(boolean b, String msg, Object... params) {
		if (!b) {
			throw newException(msg, params);
		}
	}

	public static void isNull(Object o, String msg, Object... params) {
		if (o != null) {
			throw newException(msg, params);
		}
	}

	public static void isNotNull(Object o, String msg, Object... params) {
		if (o == null) {
			throw newException(msg, params);
		}
	}

	public static void nonNull(Object o, String msg, Object... params) {
		if (o == null) {
			throw newException(msg, params);
		}
	}

	public static void nonEmpty(Collection<?> c, String msg, Object... params) {
		if (CollUtils.isEmpty(c)) {
			throw newException(msg, params);
		}
	}

	public static void nonEmpty(Map<?, ?> m, String msg, Object... params) {
		if (CollUtils.isEmpty(m)) {
			throw newException(msg, params);
		}
	}

}
