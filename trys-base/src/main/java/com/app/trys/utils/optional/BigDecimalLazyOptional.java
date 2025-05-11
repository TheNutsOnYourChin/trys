package com.app.trys.utils.optional;

import com.app.trys.constant.SystemConst;

import java.math.BigDecimal;
import java.util.function.Function;

import static com.app.trys.utils.optional.CalcSignEnum.*;


/**
 * 不实时计算，获取结果时才进行计算，可以看到其中的计算过程
 *
 * BigDecimal 操作类，更符合中缀表达式的表现形式
 * 遵循左优先的计算规则, 即:
 * of(1).add(2).multiply(3) 相当于 (1 - 2) * 3
 * 若想得到 1 - 2 * 3 的效果,可以写为:
 * of(1).add(2, multiplyWith(3))
 *
 * ※ 因为存储了计算过程，内存消耗可能较大，谨慎使用
 * @author linjf
 * @since 2023/5/18
 */
public class BigDecimalLazyOptional extends AbstractBaseOptional<BigDecimal> {

	/**
	 * 过程缓存
	 */
	private CalcNode root;


	private BigDecimalLazyOptional(CalcNode node) {
		this.root = node;
	}

	public static BigDecimalLazyOptional of(Object val) {
		return new BigDecimalLazyOptional(CalcNode.ofVal(val));
	}

	public static BigDecimalLazyOptional zero() {
		return of(BigDecimal.ZERO);
	}

	public static BigDecimalLazyOptional empty() {
		return of(null);
	}

	/**
	 * 括号
	 */
	public static BigDecimalLazyOptional brackets(Object val, NodeFunction with) {
		return BigDecimalLazyOptional.of(with.apply(val));
	}

	public static NodeFunction addWith(Object val) {
		return calcWith(ADD, val);
	}

	public static NodeFunction addWith(Object val, NodeFunction with) {
		return calcWith(ADD, val, with);
	}

	public static NodeFunction subtractWith(Object val) {
		return calcWith(SUBTRACT, val);
	}

	public static NodeFunction subtractWith(Object val, NodeFunction with) {
		return calcWith(SUBTRACT, val, with);
	}

	public static NodeFunction multiplyWith(Object val) {
		return calcWith(MULTIPLY, val);
	}

	public static NodeFunction multiplyWith(Object val, NodeFunction with) {
		return calcWith(MULTIPLY, val, with);
	}

	public static NodeFunction divideWith(Object val) {
		return calcWith(DIVIDE, val);
	}

	public static NodeFunction divideWith(Object val, NodeFunction with) {
		return calcWith(DIVIDE, val, with);
	}

	public static NodeFunction powWith(Integer val) {
		return calcWith(POWER, val);
	}

	public static NodeFunction maxWith(Object val) {
		return calcWith(MAX, val);
	}

	public static NodeFunction minWith(Object val) {
		return calcWith(MIN, val);
	}

	private static NodeFunction calcWith(CalcSignEnum sign, Object val) {
		return left -> CalcNode.ofSign(left, sign, val);
	}

	private static NodeFunction calcWith(CalcSignEnum sign, Object val, NodeFunction with) {
		return calcWith(sign, with.apply(val));
	}


	private BigDecimal calc(CalcNode node) {
		if(node.isValNode()){
			return BigDecimalOptional.parse(node.val);
		}
		return BigDecimalOptional.calc(calc(node.left), node.sign, calc(node.right));
	}

	public BigDecimalLazyOptional add(Object val) {
		return set(CalcNode.ofSign(root, ADD, val));
	}

	public BigDecimalLazyOptional add(Object val, NodeFunction with) {
		return set(CalcNode.ofSign(root, ADD, with.apply(val)));
	}

	public BigDecimalLazyOptional subtract(Object val) {
		return set(CalcNode.ofSign(root, SUBTRACT, val));
	}

	public BigDecimalLazyOptional subtract(Object val, NodeFunction with) {
		return set(CalcNode.ofSign(root, SUBTRACT, with.apply(val)));
	}

	public BigDecimalLazyOptional multiply(Object val) {
		return set(CalcNode.ofSign(root, MULTIPLY, val));
	}

	public BigDecimalLazyOptional multiply(Object val, NodeFunction with) {
		return set(CalcNode.ofSign(root, MULTIPLY, with.apply(val)));
	}

	public BigDecimalLazyOptional divide(Object val) {
		return set(CalcNode.ofSign(root, DIVIDE, val));
	}

	public BigDecimalLazyOptional divide(Object val, NodeFunction with) {
		return set(CalcNode.ofSign(root, DIVIDE, with.apply(val)));
	}

	public BigDecimalLazyOptional pow(int n) {
		return set(CalcNode.ofSign(root, POWER, n));
	}

	public BigDecimalLazyOptional max(Object val) {
		return set(CalcNode.ofSign(root, MAX, val));
	}

	public BigDecimalLazyOptional min(Object val) {
		return set(CalcNode.ofSign(root, MIN, val));
	}


	@Override
	public BigDecimal get() {
		return value != null ? value : (value = calc(root));
	}

	public CalcNode getRoot() {
		return root;
	}

	@Override
	public String toString(){
		return String.valueOf(root);
	}

	@Override
	@SuppressWarnings(SystemConst.UNCHECKED)
	public BigDecimalLazyOptional set(BigDecimal value){
		throw new UnsupportedOperationException();
	}


	private BigDecimalLazyOptional set(CalcNode root){
		this.value = null;
		this.root = root;
		return this;
	}

	interface NodeFunction extends Function<Object, CalcNode> {
	}
}
