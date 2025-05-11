package com.app.trys.base.form;

import com.app.trys.base.dto.EnumKV;
import javafx.scene.control.SelectionMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 配置在{@link Form}标记的类属性上
 *
 * @author linjf
 * @since 2023/3/2
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CtrlComboBox {

	/**
	 * 表单控件的基础属性
	 */
	Ctrl base() default @Ctrl;
	/**
	 * 名称
	 **/
	String name() default "";
	/**
	 * 各个选项的键值对目录 {@link EnumKV#getValue()}
	 */
	String optionsName() default "空列表";
	/**
	 * 多选
	 */
	SelectionMode selectionMode() default SelectionMode.SINGLE;

	/**
	 * 默认值
	 */
	long[] defValue() default {};
}
