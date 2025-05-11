package com.app.trys.base.form;

import com.app.trys.event.request.OnRequestEvent;
import com.app.trys.object.IdDTO;
import com.app.trys.utils.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Form {

    /**
     * 表单标题
     */
    String title() default StringUtils.EMPTY;
    /**
     * 短标题
     */
    String shortTitle() default "新增";
    /**
     * 提交时发布事件
     */
    Class<? extends OnRequestEvent> event();
    /**
     * 回车即提交
     */
    boolean onEnter() default false;
    /**
     * 在表单的按钮栏加入按钮，点击按钮弹出对应的功能模块
     */
    Class<? extends IdDTO<?>>[] btnArea() default {};
}
