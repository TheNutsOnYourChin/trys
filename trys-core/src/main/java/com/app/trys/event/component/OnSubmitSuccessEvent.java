package com.app.trys.event.component;

/**
 * 提交成功事件
 * @author linjf
 * @since 2025-03-31
 */
public class OnSubmitSuccessEvent extends ComponentEvent {

    public OnSubmitSuccessEvent(Class<?> componentClass, Object source) {
        super(componentClass, source);
    }
}
