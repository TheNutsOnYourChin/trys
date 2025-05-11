package com.app.trys.utils;

import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author linjf
 * @since 2023/2/16
 */
public class CollUtils extends CollectionUtils{

	public static <T> List<T> emptyIfNull(List<T> list){
		return isEmpty(list) ? Collections.emptyList() : list;
	}

	public static boolean isNotEmpty(Collection<?> collection){
		return !isEmpty(collection);
	}


	/**
	 * 为foreach提供下标
	 * list.forEach(index((item, i) -> SystemConst.out.println(item + "," + i)));
	 * 它不应该在并行流中使用，因为并行流是无序的
	 *
	 * @param bc  bc
	 * @param <T> 泛型T
	 * @return 结果
	 */
	public static <T> Consumer<? super T> index(BiConsumer<T, Integer> bc) {
		AtomicInteger i = new AtomicInteger(0);
		return t -> bc.accept(t, i.getAndIncrement());
	}


	@SafeVarargs
	public static <T> List<T> toList(T... array) {
		if (ArrayUtil.isEmpty(array)) {
			return new ArrayList<>();
		}
		final List<T> arrayList = new ArrayList<>(array.length);
		Collections.addAll(arrayList, array);
		return arrayList;
	}

	public static <K, V> Map<K, V> emptyIfNull(Map<K, V> map) {
		return null == map ? Collections.emptyMap() : map;
	}

	/**
	 * 获取列表值
	 * @param join 每项的分隔符
	 */
	public static String toValueString(List<?> list, String join){
		StringBuilder sb = new StringBuilder();
		list.forEach(item -> {
			sb.append("(");
			List<Field> fields = ReflectUtils.getFields(item);
			fields.forEach(field -> sb.append(ReflectUtils.getFieldValueByGetter(field, item)).append(StringUtils.SPLIT));
			sb.deleteCharAt(sb.length() - 1);
			sb.append(")").append(join);
		});
		if (isNotEmpty(list)) {
			sb.delete(sb.length() - join.length(), sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 检查集合泛型的每一项是否都为指定的类型
	 * 例如：有时候反射设值的List<Long>，里面的值却是Int类型
	 * @param c 集合
	 * @param clazz 类型
	 */
	public static <T, R extends Collection<T>> R checkItem(Collection<?> c, Class<T> clazz) {
		if(isNotEmpty(c)){
			for (Object item : c) {
				if(item != null && !Objects.equals(item.getClass(), clazz)){
					throw new IllegalArgumentException("列表类型必须是" + clazz.getName());
				}
			}
		}
		return (R)c;
	}

    public static <T> T get(List<T> list, int i) {
		if(isEmpty(list) || i < 0 || list.size() <= i){
			return null;
		}
		return list.get(i);
    }
}
