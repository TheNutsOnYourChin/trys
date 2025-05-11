package com.app.trys.base.form.properties;

import com.app.trys.event.request.OnRequestEvent;
import com.app.trys.base.table.ViewTable;
import lombok.Getter;

/**
 * 表格属性
 *
 * @author linjf
 * @since 2025-02-27
 */
@Getter
public class TableProperties {
    /**
     * 第一列为行号序列
     **/
    private final boolean withIdxCol;
    /**
     * 是否分页
     **/
    private final boolean pageable;
    /**
     * 分页大小
     */
    private final long pageSize;
    /**
     * 编辑时发布事件
     */
    private Class<? extends OnRequestEvent> onEditEvent;
    /**
     * 删除时发布事件
     */
    private Class<? extends OnRequestEvent> onDelEvent;


    public TableProperties(ViewTable viewTable) {
        this(viewTable.withIdxCol(), viewTable.pageable(), viewTable.pageSize(), viewTable.onEditEvent(), viewTable.onDelEvent());
    }


    public TableProperties(boolean withIdxCol, boolean pageable, long pageSize, Class<? extends OnRequestEvent> onEditEvent, Class<? extends OnRequestEvent> onDelEvent) {
        this.withIdxCol = withIdxCol;
        this.pageable = pageable;
        this.pageSize = pageSize;
        this.onEditEvent = onEditEvent;
        this.onDelEvent = onDelEvent;
        check();
    }

    private void check() {
        if(OnRequestEvent.class.equals(onEditEvent)){
            onEditEvent = null;
        }
        if(OnRequestEvent.class.equals(onDelEvent)){
            onDelEvent = null;
        }
    }

    /**
     * 是否可编辑
     */
    public boolean isEditable(){
        return getOnEditEvent() != null || getOnDelEvent() != null;
    }
}
