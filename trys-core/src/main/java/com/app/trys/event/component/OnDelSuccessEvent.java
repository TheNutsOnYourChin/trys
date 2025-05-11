package com.app.trys.event.component;

/**
 * 删除成功事件
 * @author linjf
 * @since 2025-03-31
 */
public class OnDelSuccessEvent extends ComponentEvent {

    public OnDelSuccessEvent(Class<?> componentClass, Object source) {
        super(componentClass, source);
    }
}
