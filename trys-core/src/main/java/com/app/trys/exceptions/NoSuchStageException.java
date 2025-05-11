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
public class NoSuchStageException extends StageException{


	public NoSuchStageException(Long id){
		super(MessageFormat.format("Frame id is {0}", id));
		log.error(super.getMessage());
	}
	public NoSuchStageException(String title){
		super(MessageFormat.format("Frame title is {0}", title));
		log.error(super.getMessage());
	}


}
