package com.app.trys.base.form.properties;

import com.app.trys.base.form.CtrlSwitch;
import lombok.Getter;

/**
 * 开关属性
 * @author linjf
 * @since 2025-02-28
 */
@Getter
public class CtrlSwitchProperties extends CtrlProperties {

    /**
     * 启用时按钮文本
     */
    private final String onTrueText;
    /**
     * 关闭时按钮文本
     */
    private final String onFalseText;
    /**
     * 默认值
     */
    private final boolean defValue;


    public CtrlSwitchProperties(CtrlSwitch ctrl) {
        super(ctrl.name(), ctrl.base());
        this.onTrueText = ctrl.onTrueText();
        this.onFalseText = ctrl.onFalseText();
        this.defValue = ctrl.defValue();
    }

    public boolean getDefValue() {
        return defValue;
    }
}
