package com.app.trys.utils.lambda;

@FunctionalInterface
public interface ThrowableExecutor {

	ThrowableExecutor EMPTY = () -> {};

	/**
	 * 执行一个过程
	 */
	void todo() throws Exception;
}
