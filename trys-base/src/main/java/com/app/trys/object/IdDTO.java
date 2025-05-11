package com.app.trys.object;

/**
 * @author linjf
 * @since 2023/2/21
 */
public interface IdDTO<ID> extends DTO {

	ID getId();

	void setId(ID id);

}
