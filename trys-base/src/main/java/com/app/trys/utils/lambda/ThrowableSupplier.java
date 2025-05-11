package com.app.trys.utils.lambda;

@FunctionalInterface
public interface ThrowableSupplier<R> {


	ThrowableSupplier<Object> EMPTY = () -> null;

	/**
	 * 获取一个结果
	 */
	R get() throws Exception;
}
