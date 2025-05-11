package com.app.trys.base.bus.msg;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 消息总线熟属性
 * @author linjf
 * @since 2023/4/26
 */
@Getter
@Configuration
public class BusProperty {

	/**
	 * log等级 ALL < NORMAL < SUCCESS < WARN < ERROR
	 */
	@Value("${bus.log-level:SUCCESS}")
	private Level logLevel;
}
