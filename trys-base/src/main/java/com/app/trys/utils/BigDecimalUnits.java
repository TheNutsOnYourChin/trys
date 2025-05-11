package com.app.trys.utils;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @author linjf
 * @since 2023/2/24
 */
public class BigDecimalUnits {

	/** 四舍五入，8位小数 **/
	public static final MathContext MATH_CONTEXT = new MathContext(8);

	public static final BigDecimal HUNDRED = new BigDecimal(100);

	public static BigDecimal add(BigDecimal a, BigDecimal b) {
		return a == null ? b : b == null ? a : a.add(b);
	}

	public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
		return b == null ? a : a == null ? b.negate() : a.subtract(b);
	}

	public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
		return a == null || b == null ? null : a.multiply(b);
	}

	public static BigDecimal divide(BigDecimal a, BigDecimal b) {
		return a == null || b == null ? null : a.divide(b, MATH_CONTEXT);
	}

	public static BigDecimal pow(BigDecimal a, Integer power) {
		return a == null || power == null ? null : a.pow(power);
	}

	public static boolean gt(BigDecimal left, BigDecimal right){
		return left.compareTo(right) > 0;
	}

	public static boolean ge(BigDecimal left, BigDecimal right){
		return left.compareTo(right) >= 0;
	}

	public static boolean lt(BigDecimal left, BigDecimal right){
		return left.compareTo(right) < 0;
	}

	public static boolean le(BigDecimal left, BigDecimal right){
		return left.compareTo(right) <= 0;
	}

	public static BigDecimal max(BigDecimal left, BigDecimal right){
		return gt(left, right) ? left : right;
	}

	public static BigDecimal min(BigDecimal left, BigDecimal right){
		return lt(left, right) ? left : right;
	}

	/**
	 * 保留8位有效小数
	 */
	public static BigDecimal format8(BigDecimal val) {
		return val == null ? null : new BigDecimal(val.setScale(MATH_CONTEXT.getPrecision(), MATH_CONTEXT.getRoundingMode()).stripTrailingZeros().toPlainString());
	}

}
