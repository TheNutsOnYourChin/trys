package com.app.trys.event.component.publisher;

import com.app.trys.event.component.ComponentEvent;
import com.app.trys.event.component.bus.ComponentBus;
import com.app.trys.utils.SpringContextUtils;
import com.app.trys.utils.optional.ObjectOptional;

/**
 * 组件事件发布，用于标记一个组可以发布一些事件
 *
 * @author linjf
 * @since 2025-03-31
 */
public interface ComponentEventPublisher {

    ObjectOptional<ComponentBus> bus = ObjectOptional.ofNull();

    /**
     * 同步事件
     */
    default void publish(ComponentEvent event){
        bus.ifAbsent(() -> bus.set(SpringContextUtils.getBean(ComponentBus.class)));
        bus.get().send(event);
    }

    /**
     * 异步事件
     */
    default void publishAsync(ComponentEvent event){
        // todo 异步
        bus.ifAbsent(() -> bus.set(SpringContextUtils.getBean(ComponentBus.class)));
        bus.get().send(event);
    }
}
