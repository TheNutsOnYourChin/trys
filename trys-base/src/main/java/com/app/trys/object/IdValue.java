package com.app.trys.object;

import com.app.trys.rock.Intern;
import lombok.Getter;
import lombok.Setter;

/**
 * @author linjf
 * @since 2023/3/24
 */
@Getter
public class IdValue<T> {

	public static long idIncrement = 0;

	private final long id;

	@Setter
	private long parentId;

	private final T value;

	public IdValue(T value){
		this.id = nextId();
		this.parentId = -1L;
		this.value = value;
	}

	public IdValue(T value, long parentId){
		this.id = nextId();
		this.parentId = parentId;
		this.value = value;
	}

	private Long nextId(){
		synchronized(Intern.idIncrement){
			return idIncrement ++;
		}
	}
}
