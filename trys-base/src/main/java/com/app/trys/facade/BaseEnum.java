package com.app.trys.facade;

/**
 * @author linjf
 * @since 2023/2/22
 */
public interface BaseEnum  {

	default Long getParentId(){
		return null;
	}

	Long getId();

	String getValue();

}
