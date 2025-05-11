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
public @interface CtrlDateRange {

	/**
	 * 表单控件的基础属性
	 */
	Ctrl base() default @Ctrl(widthWeight = 2);
	/**
	 * 名称
	 **/
	String name() default StringUtils.EMPTY;
	/**
	 * 默认起始日,yyyy-MM-dd
	 */
	String defBgnValue() default StringUtils.EMPTY;
	/**
	 * 默认终止日,yyyy-MM-dd
	 */
	String defEndValue() default StringUtils.EMPTY;

}
