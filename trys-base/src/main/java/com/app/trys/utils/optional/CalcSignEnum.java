package com.app.trys.utils.optional;

import com.app.trys.facade.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 运算符
 *
 * 1 + 2 * 3 ^ 4
 * 这条算式先算3的4次方，再算乘号，再算加号
 * 所以运算优先级 次方 > 乘法 > 加法
 *
 * @author linjf
 * @since 2023/5/17
 */
@Getter
@AllArgsConstructor
public enum CalcSignEnum implements BaseEnum {

	ADD(0L, 0, "加"),
	SUBTRACT(1L, 0, "减"),
	MULTIPLY(2L, 100, "乘"),
	DIVIDE(3L, 100, "除"),
	POWER(4L, 200, "次方"),
	MAX(5L, Integer.MAX_VALUE, "最大"),
	MIN(6L, Integer.MAX_VALUE, "最小");

	private final Long id;
	/**
	 * 运算优先级，等级越高，计算时优先级越高
	 */
	private final int level;

	private final String value;


	public boolean isLowerThen(CalcSignEnum sign){
		return level < sign.level;
	}

	@Override
	public String toString(){
		return value;
	}
}
