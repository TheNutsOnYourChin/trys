package com.app.trys.components.facade.controls;

import javafx.scene.layout.Region;

/**
 * 控件的适配器接口，用于拓展功能
 * @author linjf
 * @since 2023/4/6
 */
public interface ControlAdapter {

	/**
	 * 获取值
	 */
	Object getCurrentValue();
	/**
	 * 设置值
	 */
	void setCurrentValue(Object newValue);
	/**
	 * 重置
	 */
	default void reset(){
		setCurrentValue(getDefValue());
	}

	/**
	 * 获取默认值
	 */
	Object getDefValue();

	/**
	 * 设置默认值
	 * @param defValue 默认值
	 */
	void setDefValue(Object defValue);


	default Region asRegion(){
		return (Region)this;
	}
}
