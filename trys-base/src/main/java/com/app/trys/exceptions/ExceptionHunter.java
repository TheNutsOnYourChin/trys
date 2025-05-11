package com.app.trys.exceptions;

import com.app.trys.facade.BeanListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

/**
 * 全局异常捕获
 *
 * @author linjf
 * @since 2023/2/16
 */
@Order(Integer.MIN_VALUE)
@Component
@Slf4j
public class ExceptionHunter implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		// 对所有Service层方法捕获异常
		if(bean instanceof BeanListener){
			return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), new TryCatchProxy(bean));
			// cglib代理，此时 @Autowired 注入会失效，可能是序列化生成代理子类导致的丢失
//			Enhancer enhancer = new Enhancer();
//			enhancer.setSuperclass(bean.getClass());
//			enhancer.setCallback((MethodInterceptor) (o, method, params, methodProxy) -> {
//				Class<?> returnType = method.getReturnType();
//				// 捕获方法异常
//				try {
//					return methodProxy.invokeSuper(o, params);
//				} catch (Exception e) {
//                    System.out.println("报错-------------------------------------------------------");
//                    throw e;
//				}
//			});
//			Object o = enhancer.create();
//			BeanUtils.copyByGetter(bean, o);
//			return o;
		}
		return bean;
	}

}
