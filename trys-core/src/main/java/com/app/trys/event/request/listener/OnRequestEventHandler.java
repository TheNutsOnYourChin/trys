package com.app.trys.event.request.listener;

import com.app.trys.event.request.OnRequestEvent;

import java.lang.annotation.*;

/**
 * 请求数据时的监听
 *
 * @author linjf
 * @since 2024/12/8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnRequestEventHandler {

	/**
	 * 发布事件
	 */
	Class<? extends OnRequestEvent> value();
}
