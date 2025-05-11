package com.app.trys.event.request.bus;

import com.app.trys.base.bus.msg.AppMsgBus;
import com.app.trys.base.bus.msg.Level;
import com.app.trys.base.service.RequestService;
import com.app.trys.event.request.OnRequestEvent;
import com.app.trys.event.request.listener.OnRequestEventHandler;
import com.app.trys.event.request.listener.OnRequestEventListener;
import com.app.trys.exceptions.ExceptionUtils;
import com.app.trys.facade.BeanListener;
import com.app.trys.object.Result;
import com.app.trys.utils.ReflectUtils;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求事件总线，发送业务层面的请求事件
 *
 * @author linjf
 * @since 2025-02-26
 */
public interface RequestBus extends BeanPostProcessor, BeanListener {

    /**
     * 事件对应的方法
     */
    Map<Class<? extends OnRequestEvent>, OnRequestEventListener> getEventHandlerMap();

    /**
     * 发起事件请求，并返回
     * @param event 请求事件
     * @return 请求结果
     */
    Result send(OnRequestEvent event);

    /**
     * 发起事件请求，异步执行
     * @param event 请求事件
     */
    void asyncInvoke(OnRequestEvent event);

    @Component
    class RequestBusImpl implements RequestBus {

        @Getter
        private final Map<Class<? extends OnRequestEvent>, OnRequestEventListener> eventHandlerMap = new HashMap<>();

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            // 对所有请求方法监听
            if(bean instanceof RequestService){
                Arrays.stream(bean.getClass().getInterfaces()).filter(i -> ReflectUtils.extendsFrom(i, RequestService.class)).forEach(interfaceType -> {
                    List<Method> methods = ReflectUtils.getMethods(interfaceType);
                    methods.forEach(method -> {
                        OnRequestEventHandler eventHandler = method.getAnnotation(OnRequestEventHandler.class);
                        if(eventHandler != null && !OnRequestEvent.class.equals(eventHandler.value())){
                            method.setAccessible(true);
                            ExceptionUtils.isNull(getEventHandlerMap().get(eventHandler.value()), "重复的事件控制器：{0}", eventHandler.value().getSimpleName());
                            getEventHandlerMap().put(eventHandler.value(), event -> Result.success(ReflectUtils.invoke(bean, method, event.getSource())));
                        }
                    });
                });
            }
            return bean;
        }

        @Override
        public Result send(OnRequestEvent event) {
            OnRequestEventListener listener = getEventHandlerMap().get(event.getClass());
            ExceptionUtils.nonNull(listener, "找不到接收请求事件的方法：{0}", event.getClass().getSimpleName());
            try {
                Result result = listener.handle(event);
                AppMsgBus.publish(Level.SUCCESS, "成功{0}, 对象:{1}", event.getInfo(), event.getSource());
                return result;
            } catch (Exception e) {
                return Result.fail(e.getMessage());
            }
        }

        @Override
        public void asyncInvoke(OnRequestEvent event) {
            // todo
        }
    }
}
