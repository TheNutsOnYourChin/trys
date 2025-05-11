package com.app.trys.event.component;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public abstract class ComponentEvent extends ApplicationEvent {

    private final Class<?> componentClass;

    protected ComponentEvent(Class<?> componentClass, Object source) {
        super(source);
        this.componentClass = componentClass;
    }
}
