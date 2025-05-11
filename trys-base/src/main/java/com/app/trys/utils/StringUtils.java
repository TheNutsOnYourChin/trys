package com.app.trys.utils;

/**
 * @author linjf
 * @since 2023/2/16
 */
public class StringUtils {

	public static final String EMPTY = "";
	public static final String BLANK = " ";
	public static final String NULL = "null";
	public static final String BAR = "-";
	public static final String POUND = "#";
	public static final String ASTERISK = "*";
	public static final String COMMA = ",";
	public static final String SPLIT = "，";
	public static final String WRAP = "\r\n";

	public static boolean isEmpty(Object o){
		if(o == null){
			return true;
		}else {
			String s = o.toString().trim();
			return EMPTY.equals(s) || NULL.equals(s);
		}
	}

	public static boolean nonEmpty(Object o){
		return !isEmpty(o);
	}

	/**
	 * 换行， 加入\r\n
	 * @param lineLength 每行字符数
	 */
	public static String wrap(String s, int lineLength){
		if(isEmpty(s) || s.length() <= lineLength){
			return s;
		}
		StringBuilder sb = new StringBuilder();
		int idx = 0;
		int lastLineIdx = s.length() / lineLength;
		int lineCount = lastLineIdx + 1;
		for (int i = 0; i < lineCount; i++) {
			sb.append(s, idx, Math.min(s.length(), idx + lineLength)).append(i != lastLineIdx ? WRAP : EMPTY);
			idx += lineLength;
		}
		return sb.toString();
	}
}
