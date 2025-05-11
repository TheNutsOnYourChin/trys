package com.app.trys.utils;

import com.app.trys.utils.optional.AbstractBaseOptional;
import com.app.trys.utils.optional.BaseOptional;
import com.app.trys.utils.optional.BigDecimalOptional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collector;

/**
 * @author linjf
 * @since 2023/4/23
 */
public class MyCollectors {



	/**
	 * 合计收集器
	 *
	 * @return Collector
	 */
	public static Collector<BigDecimal, BigDecimalOptional, BigDecimal> toSum() {
		return Collector.of(BigDecimalOptional::zero, BigDecimalOptional::add, BigDecimalOptional::add, BaseOptional::get);
	}

	/**
	 * 指定属性的合计收集器
	 *
	 * @return Collector
	 */
	public static <T> Collector<T, BigDecimalOptional, BigDecimal> toSum(Function<T, BigDecimal> mapper) {
		return Collector.of(BigDecimalOptional::zero, (result, item) -> result.add(mapper.apply(item)), BigDecimalOptional::add, BaseOptional::get);
	}


	/**
	 * 平均值收集器
	 *
	 * @return Collector
	 */
	public static Collector<BigDecimal, BigDecimalOptional, BigDecimal> toAvg() {
		Map<BigDecimalOptional, AtomicInteger> sizeMap = new HashMap<>();
		return Collector.of(BigDecimalOptional::zero, (result, item) -> {
			sizeMap.computeIfAbsent(result, r -> new AtomicInteger()).incrementAndGet();
			result.add(item);
		}, (left, right) -> left.add(right.get()), opt -> (sizeMap.get(opt).get() > 1 ? opt.divide(sizeMap.get(opt)) : opt).get());
	}

	/**
	 * 指定属性的平均值收集器
	 *
	 * @return Collector
	 */
	public static <T> Collector<T, BigDecimalOptional, BigDecimal> toAvg(Function<T, BigDecimal> mapper) {
		Map<BigDecimalOptional, AtomicInteger> sizeMap = new HashMap<>();
		return Collector.of(BigDecimalOptional::zero, (result, item) -> {
			sizeMap.computeIfAbsent(result, r -> new AtomicInteger()).incrementAndGet();
			result.add(mapper.apply(item));
		}, (left, right) -> left.add(right.get()), opt -> (sizeMap.get(opt).get() > 1 ? opt.divide(sizeMap.get(opt)) : opt).get());
	}


	public static <T> Collector<BigDecimal, BigDecimalOptional, BigDecimal> toMax() {
		return Collector.of(BigDecimalOptional::empty, BigDecimalOptional::max, BigDecimalOptional::max, AbstractBaseOptional::get);
	}

	public static <T> Collector<T, BigDecimalOptional, BigDecimal> toMax(Function<T, BigDecimal> mapper) {
		return Collector.of(BigDecimalOptional::empty, (result, item) -> result.max(mapper.apply(item)), BigDecimalOptional::max, AbstractBaseOptional::get);
	}

	public static <T> Collector<BigDecimal, BigDecimalOptional, BigDecimal> toMin() {
		return Collector.of(BigDecimalOptional::empty, BigDecimalOptional::min, BigDecimalOptional::min, AbstractBaseOptional::get);
	}

	public static <T> Collector<T, BigDecimalOptional, BigDecimal> toMin(Function<T, BigDecimal> mapper) {
		return Collector.of(BigDecimalOptional::empty, (result, item) -> result.min(mapper.apply(item)), BigDecimalOptional::min, AbstractBaseOptional::get);
	}

}
