package com.app.trys.components.facade.form.validator.validator;

import com.app.trys.object.DTO;

/**
 * 校验接口
 * @author linjf
 * @since 2023/4/11
 */
@FunctionalInterface
public interface ControlValidator {
	/**
	 * 获取校验项
	 * @param formValue 表单数据
	 * @param controlValue 输入值
	 * @return 返回校验信息，为空则通过
	 */
	String check(DTO formValue, Object controlValue);
}
