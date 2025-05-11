package com.app.trys.base.bus.msg;

import com.app.trys.facade.BaseEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 信息等级ALL < NORMAL < SUCCESS < WARN < ERROR
 * @author linjf
 * @since 2023/4/24
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Level implements BaseEnum {
	NORMAL(1L, "普通"),
	SUCCESS(2L, "成功"),
	WARN(3L, "警告"),
	ERROR(4L, "错误");

	private final Long id;
	private final String value;

}
