package com.app.trys.base.form.properties;

import com.app.trys.base.form.CtrlInput;
import lombok.Getter;

/**
 * 输入框属性
 * @author linjf
 * @since 2025-02-28
 */
@Getter
public class CtrlInputProperties extends CtrlProperties {

    /**
     * 提示文本
     */
    private final String prompt;
    /**
     * 最大字符长度
     */
    private final int maxLength;
    /**
     * 默认值
     */
    private final String defValue;


    public CtrlInputProperties(CtrlInput ctrl) {
        super(ctrl.name(), ctrl.base());
        this.prompt = ctrl.prompt();
        this.maxLength = ctrl.maxLength();
        this.defValue = ctrl.defValue();
    }

}
