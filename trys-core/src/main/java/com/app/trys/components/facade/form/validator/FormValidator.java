package com.app.trys.components.facade.form.validator;

import com.app.trys.components.facade.controls.TrysControlWithLabel;
import com.app.trys.components.facade.form.validator.validator.ControlValidator;
import com.app.trys.facade.Validator;

import java.util.List;
import java.util.Map;

/**
 * 校验器接口，用于验证表单
 * @author linjf
 * @since 2023/4/4
 */
public interface FormValidator extends Validator {

	/**
	 * 校验器列表
	 * Map(表单控件, [(表单值，表单控件值) -> return 校验结果信息])
	 */
	Map<TrysControlWithLabel, List<ControlValidator>> getControlValidatorsMap();

	/**
	 * 添加一个校验项
	 */
	void addControlValidator(TrysControlWithLabel control);

	/**
	 * 移除警告信息
	 */
	void removeWarning();
	/**
	 * 执行校验
	 */
	boolean verify();


}
