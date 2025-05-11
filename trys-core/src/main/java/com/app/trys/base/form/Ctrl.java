package com.app.trys.base.form;

import com.app.trys.components.facade.form.validator.validator.ControlValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 配置在{@link Form}标记的类属性上
 *
 * @author linjf
 * @since 2023/3/2
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Ctrl {

	/**
	 * 名称
	 **/
	String name() default "";
	/**
	 * 宽度占比，例如设置为2，则占用2个表单控件的宽度
	 **/
	int widthWeight() default 1;
	/**
	 * 校验
	 */
	Class<? extends ControlValidator>[] validators() default {};
	/**
	 * 修改表单时，该字段处于不可更改状态
	 */
	boolean disableOnEdit() default false;
	/**
	 * 自动修正表单控件标签，使该项能够完整显示标签文本
	 */
	boolean autoLabelWidth() default true;
}
