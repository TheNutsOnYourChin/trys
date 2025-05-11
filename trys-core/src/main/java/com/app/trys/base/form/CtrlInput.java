package com.app.trys.base.form;

import com.app.trys.utils.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置在{@link Form}标记的类属性上
 *
 * @author linjf
 * @since 2023/3/2
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CtrlInput {

	/**
	 * 表单控件的基础属性
	 */
	Ctrl base() default @Ctrl;
	/**
	 * 名称
	 **/
	String name() default "";
	/**
	 * 提示文本
	 */
	String prompt() default StringUtils.EMPTY;
	/**
	 * 最大字符长度
	 */
	int maxLength() default 100;
	/**
	 * 默认值
	 */
	String defValue() default StringUtils.EMPTY;
}
