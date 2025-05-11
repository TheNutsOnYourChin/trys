package com.app.trys.utils.optional;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.Supplier;

/**
 * @author linjf
 * @since 2023/7/11
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AsyncOptional<T>  extends AbstractBaseOptional<T>{

	@Setter
	private RuntimeException exception;

	public static <T> AsyncOptional<T> of(Supplier<T> supplier){
		AsyncOptional<T> opt = new AsyncOptional<>();
		new Thread(() -> {
			try {
				opt.set(supplier.get());
			} catch (RuntimeException e){
				opt.setException(e);
			}
		}).start();
		return opt;
	}

	@Override
	public T get() {
		while (!isPresent()) {
			if(exception != null){
				throw exception;
			}
			Thread.onSpinWait();
		}
		return value;
	}
}
