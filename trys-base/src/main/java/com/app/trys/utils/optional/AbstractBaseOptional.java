package com.app.trys.utils.optional;

import com.app.trys.constant.SystemConst;
import com.app.trys.utils.Actions;

import java.util.function.Consumer;

/**
 * @author linjf
 * @since 2023/1/3
 */
public abstract class AbstractBaseOptional<T> implements BaseOptional<T> {


	protected T value;

	@Override
	public boolean isPresent(){
		return value != null;
	}

	@Override
	public void ifPresent(Consumer<T> consumer){
		Actions.ifTrue(isPresent(), () -> consumer.accept(value));
	}

	@Override
	public void ifAbsent(Runnable run){
		Actions.ifTrue(!isPresent(), run);
	}

	@Override
	public T get(){
		return value;
	}

	@Override
	public T orElse(T elseVal){
		return isPresent() ? value : elseVal;
	}

	@Override
	@SuppressWarnings(SystemConst.UNCHECKED)
	public <R extends BaseOptional<T>> R set(T value){
		this.value = value;
		return (R)this;
	}


}
