package com.app.trys.utils.lambda;

@FunctionalInterface
public interface Continue<T> {

	Continue<Object> EMPTY = t -> {};

	/**
	 * 执行一个过程
	 */
	void then(T t);
}