package com.app.trys.utils.optional;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * @author linjf
 * @since 2023/1/3
 */
public interface BaseOptional<T> extends Serializable {

	boolean isPresent();

	void ifPresent(Consumer<T> consumer);

	T get();

	T orElse(T elseVal);

	<R extends BaseOptional<T>> R set(T value);

	void ifAbsent(Runnable run);
}
