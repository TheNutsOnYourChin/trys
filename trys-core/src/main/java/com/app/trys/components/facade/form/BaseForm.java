package com.app.trys.components.facade.form;

import com.app.trys.object.IdDTO;

import java.util.Optional;

/**
 * 表单
 * @author linjf
 * @since 2023/4/6
 */
public interface BaseForm<VALUE extends IdDTO<?>> {


	/**
	 * 获取表单值
	 * @return dto
	 */
	VALUE getValue();

	/**
	 * 检查并获取表单值，检查不通过时返回空
	 * @return dto
	 */
	Optional<VALUE> getAndCheckValue();

	/**
	 * 提交
	 */
	void submit();

	/**
	 * 重置
	 */
	void reset();
	/**
	 * 重置
	 */
	void setDefValue(VALUE defValue);
}
