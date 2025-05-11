package com.app.trys.base.dto;

import com.app.trys.facade.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author linjf
 * @since 2023/4/25
 */
@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
public class EnumKV implements BaseEnum {

	private final Long id;

	private final String value;

}
