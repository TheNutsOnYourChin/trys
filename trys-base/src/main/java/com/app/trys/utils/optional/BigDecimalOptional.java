package com.app.trys.utils.optional;

import com.app.trys.utils.BigDecimalUnits;

import java.math.BigDecimal;
import java.util.function.Function;

import static com.app.trys.utils.optional.CalcSignEnum.*;


/**
 * BigDecimal 操作类，更符合中缀表达式的表现形式
 * 遵循左优先的计算规则, 即:
 * 	of(1).add(2).multiply(3) 相当于 (1 - 2) * 3
 * 若想得到 1 - 2 * 3 的效果,可以写为:
 * 	of(1).add(2, multiplyWith(3))
 *
 * 可以参考 {@link BigDecimalLazyOptional}来查看计算过程
 *
 * @see BigDecimalLazyOptional
 * @author linjf
 * @since 2022/12/7
 */
public class BigDecimalOptional extends AbstractBaseOptional<BigDecimal> {


	private BigDecimalOptional(BigDecimal value){
		this.value = value;
	}

	public static BigDecimalOptional of(Object value){
		return new BigDecimalOptional(parse(value));
	}

	public static BigDecimalOptional zero(){
		return of(BigDecimal.ZERO);
	}

	public static BigDecimalOptional empty(){
		return of(null);
	}

	/**
	 * 括号
	 */
	public static BigDecimalOptional brackets(Object val, ValFunction with){
		return of(with.apply(val));
	}

	public static ValFunction addWith(Object val){
		return calcWith(ADD, val);
	}

	public static ValFunction addWith(Object val, ValFunction with){
		return calcWith(ADD, val, with);
	}

	public static ValFunction subtractWith(Object val){
		return calcWith(SUBTRACT, val);
	}

	public static ValFunction subtractWith(Object val, ValFunction with){
		return calcWith(SUBTRACT, val, with);
	}

	public static ValFunction multiplyWith(Object val){
		return calcWith(MULTIPLY, val);
	}

	public static ValFunction multiplyWith(Object val, ValFunction with){
		return calcWith(MULTIPLY, val, with);
	}

	public static ValFunction divideWith(Object val){
		return calcWith(DIVIDE, val);
	}

	public static ValFunction divideWith(Object val, ValFunction with){
		return calcWith(DIVIDE, val, with);
	}

	public static ValFunction powWith(int val){
		return calcWith(POWER, val);
	}

	private static ValFunction calcWith(CalcSignEnum sign, Object val){
		return left -> calc(parse(left), sign, val);
	}

	private static ValFunction calcWith(CalcSignEnum sign, Object val, ValFunction with){
		return calcWith(sign, with.apply(val));
	}

	static BigDecimal calc(BigDecimal left, CalcSignEnum sign, Object right){
		BigDecimal rightVal = parse(right);
		switch (sign){
			case ADD: return BigDecimalUnits.add(left, rightVal);
			case SUBTRACT: return BigDecimalUnits.subtract(left, rightVal);
			case MULTIPLY: return BigDecimalUnits.multiply(left, rightVal);
			case DIVIDE: return BigDecimalUnits.divide(left, rightVal);
			case POWER: return BigDecimalUnits.pow(left, rightVal.intValue());
			case MAX: return BigDecimalUnits.max(left, rightVal);
			case MIN: return BigDecimalUnits.min(left, rightVal);
			default: throw new IllegalArgumentException("不支持的运算符：" + sign);
		}
	}
	private BigDecimal calc(CalcSignEnum sign, Object val){
		return calc(value, sign, val);
	}

	public BigDecimalOptional add(Object val){
		return set(calc(ADD, val));
	}

	public BigDecimalOptional add(Object val, ValFunction with){
		return set(calc(ADD, with.apply(val)));
	}

	public BigDecimalOptional subtract(Object val){
		return set(calc(SUBTRACT, val));
	}

	public BigDecimalOptional subtract(Object val, ValFunction with){
		return set(calc(SUBTRACT, with.apply(val)));
	}

	public BigDecimalOptional multiply(Object val){
		return set(calc(MULTIPLY, val));
	}

	public BigDecimalOptional multiply(Object val, ValFunction with){
		return set(calc(MULTIPLY, with.apply(val)));
	}

	public BigDecimalOptional divide(Object val){
		return set(calc(DIVIDE, val));
	}

	public BigDecimalOptional divide(Object val, ValFunction with){
		return set(calc(DIVIDE, with.apply(val)));
	}

	public BigDecimalOptional pow(int n) {
		return set(calc(POWER, n));
	}


	public BigDecimalOptional format() {
		return set(BigDecimalUnits.format8(value));
	}

	public BigDecimalOptional abs() {
		return set(isPresent() ? value.abs() : null);
	}

	public BigDecimalOptional negate() {
		return set(isPresent() ? value.negate() : null);
	}

	public BigDecimalOptional max(Object val) {
		return set(calc(MAX, val));
	}

	public BigDecimalOptional min(Object val) {
		return set(calc(MIN, val));
	}


	static BigDecimal parse(Object o){
		return o == null || o instanceof BigDecimal ? (BigDecimal) o
				: o instanceof BigDecimalOptional ? ((BigDecimalOptional)o).get()
				: new BigDecimal(o.toString());
	}


	interface ValFunction extends Function<Object, BigDecimal>{}
}
