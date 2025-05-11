package com.app.trys.base.bus.msg;

import com.app.trys.utils.DateUtils;
import com.app.trys.utils.SpringContextUtils;
import com.app.trys.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * 信息总线
 * @author linjf
 * @since 2023/4/24
 */
@Component
@Slf4j
public class AppMsgBus {


	@Autowired
	private BusProperty busProperty;
	/**
	 * 广播列表
	 */
	private final Map<Level, List<BiConsumer<Level, BusMsgEvent<?>>>> listenerMap = new HashMap<>();


	/**
	 * 添加
	 * @param level 信息等级
	 * @param msgConsumer 消费者
	 */
	public static void addListener(Level level, BiConsumer<Level, BusMsgEvent<?>> msgConsumer){
		AppMsgBus bus = SpringContextUtils.getBean(AppMsgBus.class);
		bus.addMsgListener(level, msgConsumer);
	}

	public static void publish(Level level, String msg, Object... params){
		AppMsgBus bus = SpringContextUtils.getBean(AppMsgBus.class);
		bus.publishMsg(level, msg, params);
	}

	/**
	 * 添加
	 * @param level 信息等级
	 * @param msgConsumer 消费者
	 */
	public void addMsgListener(Level level, BiConsumer<Level, BusMsgEvent<?>> msgConsumer){
		listenerMap.computeIfAbsent(level, k -> new ArrayList<>()).add(msgConsumer);
	}

	/**
	 * 发布
	 * 如果监听者不限制监听等级,则接收所有广播 限制了等级,则只监听目标等级
	 */
	public <T> void publishMsg(Level lv, String msg, Object... params){
		msg = MessageFormat.format(msg, params);
		Date date = new Date();
		String publishMsg = DateUtils.formatHhmmss(date)  + StringUtils.BLANK + msg;
		BusMsgEvent<T> event = new BusMsgEvent<>(lv, publishMsg);
		listenerMap.getOrDefault(lv, Collections.emptyList()).forEach(msgConsumer -> msgConsumer.accept(lv, event));

		// 通知等级大于等于设定等级，则打印日志
		if(lv.getId() >= busProperty.getLogLevel().getId()) {
			switch (lv) {
				case NORMAL:
				case SUCCESS:
					log.info(msg);
					return;
				case WARN:
					log.warn(msg);
					return;
				case ERROR:
					log.error(msg);
			}
		}
	}
}
