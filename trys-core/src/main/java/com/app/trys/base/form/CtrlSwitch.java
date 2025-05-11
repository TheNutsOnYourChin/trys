package com.app.trys.base.form;

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
public @interface CtrlSwitch {

	/**
	 * 表单控件的基础属性
	 */
	Ctrl base() default @Ctrl;
	/**
	 * 名称
	 **/
	String name() default "";
	/**
	 * 启用时按钮文本
	 */
	String onTrueText() default "已启用";

	/**
	 * 关闭时按钮文本
	 */
	String onFalseText() default "已关闭";
	/**
	 * 默认值
	 */
	boolean defValue() default false;
}
