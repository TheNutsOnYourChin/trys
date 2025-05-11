package com.app.trys.works.enums.kv;

import com.app.trys.facade.BaseEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否
 * @author linjf
 * @since 2023/2/22
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum YesOrNo implements BaseEnum {

	No(0L, "否"),
	Yes(1L, "是");

	private final Long id;

	private final String value;


}
