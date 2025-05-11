package com.app.trys.event.request;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public abstract class OnRequestEvent extends ApplicationEvent {

    private final String info;

    public OnRequestEvent(Object source, String info) {
        super(source);
        this.info = info;
    }
}
