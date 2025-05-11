package com.app.trys.base.table;

import com.app.trys.event.request.OnRequestEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询页面表格属性
 *
 * @author linjf
 * @since 2023/2/23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ViewTable {


	/**
	 * 第一列为行号序列
	 **/
	boolean withIdxCol() default true;
	/**
	 * 是否分页
	 **/
	boolean pageable() default true;
	/**
	 * 分页大小
	 */
	long pageSize() default 50;
	/**
	 * 修改时发布事件
	 */
	Class<? extends OnRequestEvent> onEditEvent() default OnRequestEvent.class;
	/**
	 * 删除时发布事件
	 */
	Class<? extends OnRequestEvent> onDelEvent() default OnRequestEvent.class;

}
