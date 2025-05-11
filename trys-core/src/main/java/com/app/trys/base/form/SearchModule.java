package com.app.trys.base.form;

import com.app.trys.object.IdDTO;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询页面基础属性
 *
 * @author linjf
 * @since 2025/2/24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SearchModule {

	/**
	 * 表单基础属性
	 */
	Form base();
	/**
	 * 结果类型
	 */
	Class<? extends IdDTO<?>> resultClass();
}
