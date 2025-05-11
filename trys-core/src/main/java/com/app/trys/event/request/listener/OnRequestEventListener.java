package com.app.trys.event.request.listener;

import com.app.trys.event.request.OnRequestEvent;
import com.app.trys.object.Result;

/**
 * 请求事件监听
 *
 * @author linjf
 * @since 2025-02-26
 */
@FunctionalInterface
public interface OnRequestEventListener {

    Result handle(OnRequestEvent event) throws Exception;
}
