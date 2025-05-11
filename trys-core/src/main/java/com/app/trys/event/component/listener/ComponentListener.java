package com.app.trys.event.component.listener;

import com.app.trys.event.component.ComponentEvent;

import java.util.EventListener;

/**
 * 组件事件监听
 *
 * @author linjf
 * @since 2025-03-31
 */
@FunctionalInterface
public interface ComponentListener extends EventListener {

    void handle(ComponentEvent event);
}
