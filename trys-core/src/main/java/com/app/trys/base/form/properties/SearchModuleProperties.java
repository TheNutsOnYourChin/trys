package com.app.trys.base.form.properties;

import com.app.trys.base.form.SearchModule;
import com.app.trys.object.IdDTO;
import lombok.Getter;

/**
 * 查询表单的属性
 *
 * @author linjf
 * @since 2025-02-26
 */
@Getter
public class SearchModuleProperties {
    /**
     * 基础属性
     */
    private final FormProperties baseProperties;
    /**
     * 结果类型
     */
    private final Class<? extends IdDTO<?>> resultClass;

    public SearchModuleProperties(SearchModule searchModule) {
        this(new FormProperties(searchModule.base()), searchModule);
    }

    public SearchModuleProperties(FormProperties baseProperties, SearchModule searchModule) {
        this.baseProperties = baseProperties;
        this.resultClass = searchModule.resultClass();
    }

}
