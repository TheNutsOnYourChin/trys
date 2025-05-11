package com.app.trys.account.event;

import com.app.trys.event.request.OnRequestEvent;

/**
 * 这里的作用更像是web应用的controller
 *
 * @author linjf
 * @since 2025-03-28
 */
public class TrysAccountEvents {

    public static class Query extends OnRequestEvent {
        public Query(Object source) {
            super(source, "查询");
        }
    }

    public static class Save extends OnRequestEvent {
        public Save(Object source) {
            super(source, "保存");
        }
    }

    public static class Delete extends OnRequestEvent {
        public Delete(Object source) {
            super(source, "删除");
        }
    }
}
