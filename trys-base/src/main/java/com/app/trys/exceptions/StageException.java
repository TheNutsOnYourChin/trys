package com.app.trys.exceptions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

/**
 * @author linjf
 * @since 2023/2/16
 */
@Getter
@Slf4j
public class StageException extends RuntimeException{

	private String code;

	public StageException(Exception e, String msg, Object... params){
		super(MessageFormat.format(msg, params), e);
		log.error(super.getMessage());
	}
	public StageException(String code , String msg, Object... params){
		super(MessageFormat.format(msg, params));
		log.error(super.getMessage());
	}

	public StageException(String msg, Object... params){
		super(MessageFormat.format(msg, params));
		log.error(super.getMessage());
	}

}
