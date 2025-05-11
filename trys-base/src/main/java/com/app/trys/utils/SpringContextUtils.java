package com.app.trys.utils;

import com.app.trys.constant.SystemConst;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author linjf
 * @since 2023/2/28
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

	private static ApplicationContext context;

    @Override
	public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public static ApplicationContext getApplicationContext(){
		return context;
	}

	public static ConfigurableApplicationContext getAsApplicationContext(){
		return (ConfigurableApplicationContext)context;
	}

	public static Object getBean(String name){
		return StringUtils.isEmpty(name) ? null : context.getBean(name);
	}

	public static <T> T getBean(Class<T> clazz){
		Objects.requireNonNull(clazz,"getBean()缺少Class类型参数");
		T bean = context.getBean(clazz);
		Objects.requireNonNull(bean,"找不到Bean");
		return bean;
	}

	public static <T> T getBean(String name, Class<T> clazz){
		Objects.requireNonNull(name,"getBean()缺少name参数");
		Objects.requireNonNull(clazz,"getBean()缺少Class类型参数");
		T bean = context.getBean(name, clazz);
		Objects.requireNonNull(bean,"找不到Bean");
		return bean;
	}

	@SuppressWarnings(SystemConst.UNCHECKED)
    public static <T> List<T> getBeans(Class<T> clazz) {
		return (List<T>)Arrays.stream(getApplicationContext().getBeanNamesForType(clazz)).map(SpringContextUtils::getBean).collect(Collectors.toList());
    }
}
