package com.app.trys.object;

import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 分页查询结果
 * @author linjf
 * @since 2023/2/24
 */
@Data
public class TablePageDTO<T> implements DTO {

	private Long current = 1L;
	private Long size = 0L;
	private Long total = 0L;
	private Long totalPage = 0L;
	private List<T> list = Collections.emptyList();

	public void prev(){
		if(allowPrevPage()){
			current--;
		}
	}

	public void next(){
		if(allowNextPage())
			current++;
	}

	public boolean allowPrevPage(){
		return current > 1;
	}

	public boolean allowNextPage(){
		return current < totalPage;
	}

	public boolean allowJumpPage(Long targetPageNum){
		return targetPageNum != null && 1 <= targetPageNum && targetPageNum <= totalPage && !Objects.equals(targetPageNum, current);
	}


	public Long getRowNum(int rowIdx){
		return size * (current - 1) + rowIdx + 1;
	}
}
