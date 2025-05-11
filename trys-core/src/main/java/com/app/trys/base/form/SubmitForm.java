package com.app.trys.base.form;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 保存表单属性
 *
 * @author linjf
 * @since 2023/2/23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SubmitForm {
	/**
	 * 表单基础属性
	 */
	Form base();


	String submitText() default "保存";
}
