package com.app.trys.object;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author linjf
 * @since 2023/2/21
 */
@Data
@Accessors(chain = true)
public abstract class BasePage extends LongIdDTO implements  Clearable {

	/**
	 * 对于mybatis-plus来说，size < 0 即可查全部
	 */
	private long size = 50L;

	private long current = 1L;

	/**
	 * 不分页时的页数
	 */
	public static final long NO_PAGE_SIZE = -1L;
}
