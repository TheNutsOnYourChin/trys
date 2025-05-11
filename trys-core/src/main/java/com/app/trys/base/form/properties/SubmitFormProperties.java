package com.app.trys.base.form.properties;

import com.app.trys.base.form.SubmitForm;
import lombok.Getter;

/**
 * 表单属性
 *
 * @author linjf
 * @since 2025-02-26
 */
@Getter
public class SubmitFormProperties {
    /**
     * 基础属性
     */
    private final FormProperties baseProperties;

    /**
     * 提交按钮名称
     */
    private final String submitText;

    public SubmitFormProperties(FormProperties baseProperties, SubmitForm submitForm){
        this.baseProperties = baseProperties;
        this.submitText = submitForm.submitText();
    }

}
