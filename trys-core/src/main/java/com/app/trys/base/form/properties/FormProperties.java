package com.app.trys.base.form.properties;

import com.app.trys.base.form.Form;
import com.app.trys.event.request.OnRequestEvent;
import com.app.trys.object.IdDTO;
import lombok.Getter;

/**
 * 表单属性
 *
 * @author linjf
 * @since 2025-02-26
 */
@Getter
public class FormProperties {

    /**
     * 表单标题
     */
    private final String title;
    /**
     * 短标题
     */
    private final String shortTitle;

    /**
     * 提交时发布事件
     */
    protected final Class<? extends OnRequestEvent> event;
    /**
     * 回车即查询
     */
    private final boolean onEnter;
    /**
     * 在表单的按钮栏加入按钮，点击按钮弹出对应的功能模块
     */
    private final Class<? extends IdDTO<?>>[] btnArea;


    public FormProperties(Form form){
        this.title = form.title();
        this.event = form.event();
        this.onEnter = form.onEnter();
        this.btnArea = form.btnArea();
        this.shortTitle = form.shortTitle();
    }

}
