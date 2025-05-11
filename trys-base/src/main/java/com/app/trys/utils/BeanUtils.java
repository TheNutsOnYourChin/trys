package com.app.trys.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author linjf
 * @since 2023/2/16
 */
public class BeanUtils {


	public static <T> T copy(Object source, Class<T> targetClass) {
		return source == null ? null : Actions.doTry(() -> {
			T target = ReflectUtils.newInstance(targetClass);
			copy(source, target);
			return target;
		}, "BeanUtils复制属性时发生异常");
	}


	public static <T> void copy(Object source, T target) {
		if(source != null){
			Actions.doTry(() -> {
				org.springframework.beans.BeanUtils.copyProperties(source, target);
				return target;
			}, "BeanUtils复制属性时发生异常");
		}
	}

	public static <T> T copyByGetter(Object source, T target) {
		copyByGetter(source, target, false);
		return target;
	}
	/**
	 * 拷贝时忽略sourse的空字段
	 */
	public static <T> T copyByGetterIgnoreNull(Object source, T target) {
		copyByGetter(source, target, true);
		return target;
	}
	public static <T> void copyByGetter(Object source, T target, boolean ignoreNull) {
		if(source != null){
			Map<String, Field> targetFieldMap = ReflectUtils.getFields(target).stream().collect(Collectors.toMap(f -> "get" + f.getName().toLowerCase(), Function.identity()));
			ReflectUtils.getMethods(source.getClass()).stream()
					.filter(method -> method.getName().startsWith("get") && method.getParameterCount() == 0)
					.forEach(getter -> Actions.doTry(() -> {
						Field targetField = targetFieldMap.get(getter.getName().toLowerCase());
						Object result = getter.invoke(source);
						if(ignoreNull && result == null){
							return;
						}
						if (targetField != null && ReflectUtils.isSameClass(targetField.getType(), getter.getReturnType())) {
							targetField.set(target, result);
						}
					}, "BeanUtils复制属性时发生异常"));
		}
	}


	public static <S, T> List<T> copyList(List<S> source, Class<T> targetClass) {
		if (CollUtils.isEmpty(source)) {
			return new ArrayList<>(Collections.emptyList());
		}
		return source.stream().map(s -> copy(s, targetClass)).collect(Collectors.toList());

	}

}
