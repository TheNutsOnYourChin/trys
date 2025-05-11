package com.app.trys.event.request.publisher;

import com.app.trys.event.request.OnRequestEvent;
import com.app.trys.event.request.bus.RequestBus;
import com.app.trys.object.Result;
import com.app.trys.utils.SpringContextUtils;
import com.app.trys.utils.optional.ObjectOptional;

/**
 * 业务事件发布，用于标记一个组件可以发布一些事件
 *
 * @author linjf
 * @since 2025-03-31
 */
public interface OnRequestEventPublisher {

    ObjectOptional<RequestBus> bus = ObjectOptional.ofNull();

    /**
     * 同步事件
     */
    default Result publish(OnRequestEvent event){
        bus.ifAbsent(() -> bus.set(SpringContextUtils.getBean(RequestBus.class)));
        return bus.get().send(event);
    }

    /**
     * 异步事件
     */
    default void publishAsync(OnRequestEvent event){
        // todo 异步
        bus.ifAbsent(() -> bus.set(SpringContextUtils.getBean(RequestBus.class)));
        bus.get().send(event);
    }
}
