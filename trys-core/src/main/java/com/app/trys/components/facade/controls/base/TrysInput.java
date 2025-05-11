package com.app.trys.components.facade.controls.base;

import java.util.function.Consumer;

/**
 * @author linjf
 * @since 2023/2/21
 */
public class TrysInput extends TrysTextField {


	private TrysInput(Consumer<String> onEnterPress){
		super(onEnterPress);
	}

	public static TrysInput of(){
		return new TrysInput(null);
	}
	public static TrysInput of(Consumer<String> onEnterPress){
		return new TrysInput(onEnterPress);
	}

}
