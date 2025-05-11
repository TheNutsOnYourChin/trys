package com.app.trys.event;

import com.app.trys.event.component.ComponentEvent;
import com.app.trys.event.component.bus.ComponentBus;
import com.app.trys.event.component.listener.ComponentListener;
import com.app.trys.event.component.publisher.ComponentEventPublisher;
import com.app.trys.event.request.publisher.OnRequestEventPublisher;
import com.app.trys.utils.SpringContextUtils;
import org.springframework.stereotype.Component;

/**
 * 把两种事件发布器结合一下，方便写代码
 *
 * @author linjf
 * @since 2025-03-31
 */
public interface EventAdapter extends ComponentEventPublisher, OnRequestEventPublisher {


    default void register(Class<?> componentClass, Class<? extends ComponentEvent> eventClass, ComponentListener listener){
        ComponentEventPublisher.bus.ifAbsent(() -> ComponentEventPublisher.bus.set(SpringContextUtils.getBean(ComponentBus.class)));
        ComponentEventPublisher.bus.get().register(componentClass, eventClass, listener);
    }

    default void unregister(Class<?> componentClass, Class<? extends ComponentEvent> eventClass){
        ComponentEventPublisher.bus.ifAbsent(() -> ComponentEventPublisher.bus.set(SpringContextUtils.getBean(ComponentBus.class)));
        ComponentEventPublisher.bus.get().unregister(componentClass, eventClass);
    }

    @Component
    class EventAdapterImpl implements EventAdapter {}
}
