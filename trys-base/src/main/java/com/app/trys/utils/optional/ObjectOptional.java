package com.app.trys.utils.optional;

import com.app.trys.utils.CollUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author linjf
 * @since 2023/2/28
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectOptional<T> extends AbstractBaseOptional<T>{


	public static <T> ObjectOptional<T> ofNull(){
		return new ObjectOptional<>();
	}

	public static <T> ObjectOptional<T> of(T o){
		ObjectOptional<T> opt = new ObjectOptional<>();
		opt.set(o);
		return opt;
	}

}
