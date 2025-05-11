package com.app.trys.base.bus.msg;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 总线事件 - 消息事件
 *
 * @author linjf
 * @since 2023/4/26
 */
@Getter
@AllArgsConstructor
public class BusMsgEvent<T> implements BusEvent {
	/**
	 * 级别

	 */
	private Level level;

	/**
	 * 原始数据
	 */
	private T data;

	/**
	 * 消息
	 */
	private String msg;

	/**
	 * 消息源头
	 */
	private Object from;

	public BusMsgEvent(Level level, String msg) {
		this.msg = msg;
		this.level = level;
	}
}
