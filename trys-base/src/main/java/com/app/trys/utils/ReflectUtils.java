package com.app.trys.utils;

import com.app.trys.exceptions.ExceptionUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 反射工具类
 *
 * @author linjf
 * @since 2023/2/22
 */
public class ReflectUtils {

	public static final Map<Class<?>, Class<?>> primitiveToClassMap = new IdentityHashMap<>(8);

	static {
		primitiveToClassMap.put(Boolean.TYPE, Boolean.class);
		primitiveToClassMap.put(Byte.TYPE, Byte.class);
		primitiveToClassMap.put(Character.TYPE, Character.class);
		primitiveToClassMap.put(Double.TYPE, Double.class);
		primitiveToClassMap.put(Float.TYPE, Float.class);
		primitiveToClassMap.put(Integer.TYPE, Integer.class);
		primitiveToClassMap.put(Long.TYPE, Long.class);
		primitiveToClassMap.put(Short.TYPE, Short.class);
		primitiveToClassMap.put(Void.TYPE, Void.class);
	}

	/**
	 * 获取注解并检查缺失
	 *
	 * @param clazz  目标类
	 * @param aClass 注解类型
	 * @param <A>    注解类
	 * @return 注解类型
	 */
	public static <A extends Annotation> A getAndCheckAnnotation(Class<?> clazz, Class<A> aClass) {
		if (clazz == null) {
			throw ExceptionUtils.newException("缺少被注解的类对象参数class");
		} else if (aClass == null) {
			throw ExceptionUtils.newException("缺少注解类对象参数aClass");
		} else {
			A annotation = clazz.getAnnotation(aClass);
			ExceptionUtils.nonNull(annotation, "缺少@{0}注解", aClass.getSimpleName());
			return annotation;
		}
	}


	public static Field getField(Object o, String name) {
		return o != null ? getField(o.getClass(), name) : null;
	}

	public static Field getField(Class<?> c, String name) {
		if (c != null) {
			return Actions.doTry(() -> {
				for (Field field : getFields(c)) {
					if (field.getName().equals(name)) {
						return field;
					}
				}
				throw new NoSuchFieldException();
			}, "获取字段失败，对象：{0}，字段{1}", c.getSimpleName(), name);
		}
		return null;
	}

	public static List<Field> getFields(Object o) {
		return o != null ? getFields(o.getClass()) : Collections.emptyList();
	}

	public static List<Field> getFields(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<>();
		Class<?> c = clazz;
		do {
			for (Field f : c.getDeclaredFields()) {
				f.setAccessible(true);
				fieldList.add(f);
			}
		} while ((c = c.getSuperclass()) != null);
		return fieldList;
	}

	public static <A extends Annotation> List<A> getFieldAnnotations(Class<?> clazz, Class<A> aClass) {
		return getFields(clazz).stream().map(f -> f.getAnnotation(aClass)).filter(Objects::nonNull).collect(Collectors.toList());
	}

	public static <T> T newInstance(Class<T> clazz, Object... params) {
		Object[] finalParams = ObjUtils.defaultIfEmpty(params, new Object[0]);
		return Actions.doTry(() -> {
			for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
				if(finalParams.length == constructor.getParameterCount()){
					return (T)constructor.newInstance(finalParams);
				}
			}
			throw new NoSuchMethodException();
		}, "实例化失败, 找不到参数一致的构造器,Class:{0}", clazz.getName());
	}


	public static <T> Constructor<T> getConstructor(Class<T> c, Class<?>... parameterTypes) {
		try {
			return c.getConstructor(parameterTypes);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	/**
	 * 获取泛型类型
	 * @param c 有泛型的类
	 * @return 泛型类型列表
	 */
	public static Class<?>[] getActualClasses(Class<?> c){
		return (Class<?>[]) ((ParameterizedType)c.getGenericSuperclass()).getActualTypeArguments();
	}


	public static void set(Object o, String fieldName, Object value) {
		Class<?> c = o.getClass();
		Actions.doTry(() -> {
			Field field = ReflectUtils.getField(o, fieldName);
			field.setAccessible(true);
			field.set(o, value);
		},"反射赋值失败，class: {0}， field: {1}", c, fieldName);
	}

	public static void set(Object o, Field field, Object value) {
		Class<?> c = o.getClass();
		field.setAccessible(true);
		Actions.doTry(() -> field.set(o, value),"反射赋值失败，class: {0}， field: {1}", c, field.getName());
	}

	/**
	 * 判断A是否继承B
	 * @param type A
	 * @param superType B
	 */
	public static boolean extendsFrom(Class<?> type, Class<?> superType) {
		return superType.isAssignableFrom(type);
	}

	/**
	 * 获取字段值
	 * @param f 字段
	 * @param o 对象
	 * @return 字段值
	 */
	public static Object getFieldValue(Field f, Object o) {
		return Actions.doTry(() -> {
			f.setAccessible(true);
			return f.get(o);
		}, "反射取值失败，对象:{0}, 取值字段:{1}", o, f.getName());
	}
	/**
	 * 按getter获取字段值
	 * @param f 字段
	 * @param o 对象
	 * @return 字段值
	 */
	public static Object getFieldValueByGetter(Field f, Object o) {
		String getterName = "get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
		return Actions.doTry(() -> {
			Method getter = o.getClass().getMethod(getterName);
			ExceptionUtils.nonNull(getter, "找不到{0}对应的getter方法");
			return getter.invoke(o);
		}, "getter反射取值失败，对象:{0}, 取值字段:{1}", o, f.getName());
	}

	/**
	 * 获取子类
	 * @param parentClass 父类
	 * @return 子类列表
	 */
	public static List<Class<?>> getChildClassOf(Class<?> parentClass, String scanPath) {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AssignableTypeFilter(parentClass));
		Set<BeanDefinition> components = provider.findCandidateComponents(scanPath);
		return components.stream()
				.map(component -> Actions.doTry(() -> Class.forName(component.getBeanClassName()), "Class.forName()方法获取类异常, 类名:{0}", component.getBeanClassName()))
				.collect(Collectors.toList());
	}

	public static Method getMethod(Class<?> c, String name, Class<?>... parameterTypes) {
		try {
			return c.getMethod(name, parameterTypes);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	public static List<Method> getMethods(Class<?> c) {
		List<Method> methodList = new ArrayList<>();
		if(c == null){
			return methodList;
		}
		do {
			methodList.addAll(Arrays.asList(c.getDeclaredMethods()));
		} while ((c = c.getSuperclass()) != null);
		return methodList;
	}

	public static boolean isMethodExist(Class<?> c, String name, Class<?> returnType, Class<?>... parameterTypes) {
		Method method = getMethod(c, name, parameterTypes);
		return method != null && Objects.equals(method.getReturnType(), returnType);
	}


	public static Object invoke(Object obj, Method method, Object... params) {
		return Actions.doTry(() -> method.invoke(obj, params), "invoke出现异常");
	}

	public static boolean isSameClass(Class<?> c1, Class<?> c2) {
		if(Objects.equals(c1, c2)){
			return true;
		}
		if(c1.isPrimitive()){
			return Objects.equals(c1, primitiveToClassMap.get(c2));
		}else if(c2.isPrimitive()){
			return Objects.equals(c2, primitiveToClassMap.get(c1));
		}
		return false;
	}

	public static Class<?> parseClassIfPrimitive(Class<?> type){
		return type.isPrimitive() ? primitiveToClassMap.get(type) : type;
	}
}
