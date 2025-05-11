package com.app.trys.utils.optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.text.MessageFormat;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CalcNode{
	Object val;
	CalcNode left;
	CalcSignEnum sign;
	CalcNode right;

	public static CalcNode ofVal(Object val) {
		if(val instanceof BigDecimalLazyOptional){
			return ((BigDecimalLazyOptional) val).getRoot();
		}
		if(val instanceof CalcNode){
			return (CalcNode)val;
		}
		return new CalcNode(val, null, null, null);
	}

	public static CalcNode ofSign(Object left, CalcSignEnum sign, Object right) {
		return new CalcNode(null, ofVal(left), sign, ofVal(right));
	}

	@Override
	public String toString() {
		if (sign != null) {
			switch (sign) {
				case ADD: return MessageFormat.format("{0} + {1}", left, right);
				case SUBTRACT: return MessageFormat.format("{0} - {1}", left, right);
				case MULTIPLY: return MessageFormat.format("{0} * {1}", formatBrackets(left), formatBrackets(right));
				case DIVIDE: return MessageFormat.format("{0} / {1}", formatBrackets(left), formatBrackets(right));
				case POWER: return MessageFormat.format("{0} ^ {1}", formatBrackets(left), right);
				case MAX: return MessageFormat.format("max({0}, {1})", left, right);
				case MIN: return MessageFormat.format("min({0}, {1})", left, right);
			}
		}
		return String.valueOf(val);
	}

	private String formatBrackets(CalcNode child){
		return child.isLowerThen(sign) ? "(" + child + ")" : child.toString();
	}


	/**
	 * 叶子节点，即值节点
	 */
	public boolean isValNode(){
		return sign == null;
	}

	/**
	 * 非叶子节点，即计算节点
	 */
	public boolean isCalcNode(){
		return sign != null;
	}

	/**
	 * 当前计算节点是否比目标等级更低
	 */
	public boolean isLowerThen(CalcSignEnum targetSign){
		return isCalcNode() && sign.isLowerThen(targetSign);
	}
}