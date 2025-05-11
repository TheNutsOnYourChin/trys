package com.app.trys.object;


import com.app.trys.utils.Actions;
import com.app.trys.utils.ReflectUtils;
import lombok.Data;

import java.lang.reflect.Field;

/**
 * @author linjf
 * @since 2023/2/21
 */
@Data
public abstract class LongIdDTO implements IdDTO<Long>, DTO, Clearable {

	protected Long id;

	public boolean isIdExist(){
		return id != null;
	}

	public void clear(){
		Actions.doTry(() -> {
			for (Field field : ReflectUtils.getFields(this.getClass())) {
				Object val = null;
				Class<?> type = field.getType();
				if(type.isPrimitive()){
					if(type == boolean.class){
						val = false;
					} else if (type == byte.class || type == short.class || type == int.class){
						val = 0;
					}  else if (type == long.class){
						val = 0L;
					}  else if (type == float.class){
						val = 0.0F;
					}  else if (type == char.class){
						val = '\u0000';
					}  else if (type == double.class){
						val = 0.0;
					}
				}
				field.set(this, val);
			}
		}, "重置数据时发生异常，class: {0}", this.getClass().getSimpleName());
	}
}
