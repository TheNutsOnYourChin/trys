package com.app.trys.utils.optional;

/**
 * @author linjf
 * @since 2023/2/24
 */
public class IntegerOptional extends AbstractBaseOptional<Integer> {

	private IntegerOptional(int val){
		this.value = val;
	}

	public static IntegerOptional of(int val){
		return new IntegerOptional(val);
	}

	public static IntegerOptional zero(){
		return new IntegerOptional(0);
	}

	public IntegerOptional increment(){
		return set( ++ this.value);
	}

	public IntegerOptional add(int val){
		return set(this.value + val);
	}

	public IntegerOptional subtract(int val){
		return set(this.value - val);
	}
}
