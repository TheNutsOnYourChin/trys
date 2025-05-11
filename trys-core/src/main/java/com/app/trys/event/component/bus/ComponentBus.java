package com.app.trys.event.component.bus;


import com.app.trys.event.component.ComponentEvent;
import com.app.trys.event.component.listener.ComponentListener;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 组件事件总线，用于组件之间的联系
 *
 * @author linjf
 * @since 2025-03-31
 */
public interface ComponentBus {

    void register(Class<?> componentClass, Class<? extends ComponentEvent> eventClass, ComponentListener listener);

    void unregister(Class<?> componentClass, Class<? extends ComponentEvent> eventClass);

    void send(ComponentEvent event);

    @Component
    class ComponentBusImpl implements ComponentBus {

        private final Map<Class<?>, Map<Class<? extends ComponentEvent>, List<ComponentListener>>> listenerMap = new HashMap<>();

        @Override
        public void register(Class<?> componentClass, Class<? extends ComponentEvent> eventClass, ComponentListener listener) {
            listenerMap.computeIfAbsent(componentClass, k -> new HashMap<>())
                    .computeIfAbsent(eventClass, k -> new ArrayList<>())
                    .add(listener);
        }

        @Override
        public void unregister(Class<?> componentClass, Class<? extends ComponentEvent> eventClass) {
            Map<Class<? extends ComponentEvent>, List<ComponentListener>> eventToListenerMap = listenerMap.get(componentClass);
            if(eventToListenerMap == null){
                return;
            }
            eventToListenerMap.remove(eventClass);
            if(eventToListenerMap.isEmpty()){
                listenerMap.remove(componentClass);
            }
        }

        @Override
        public void send(ComponentEvent event) {
            Optional.ofNullable(listenerMap.get(event.getComponentClass()))
                    .map(m -> m.get(event.getClass()))
                    .ifPresent(listeners -> listeners.forEach(listener -> listener.handle(event)));
        }
    }
}
