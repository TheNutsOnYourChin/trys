package com.app.trys.base.table;

import java.lang.annotation.*;

/**
 * 配置在{@link ViewTable}标记的类属性上
 * 展示表格数据所需要的列信息
 *
 * @author linjf
 * @since 2023/3/1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ViewTableCol {

	/**
	 * 列名
	 **/
	String name();

	/**
	 * 宽度
	 **/
	int width() default 150;

	/**
	 * 最大宽度
	 **/
	int maxWidth() default 1000;

	/**
	 * 最大宽度
	 **/
	int minWidth() default 50;

}
