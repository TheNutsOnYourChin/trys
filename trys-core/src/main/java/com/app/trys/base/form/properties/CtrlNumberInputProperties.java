package com.app.trys.base.form.properties;

import com.app.trys.base.form.CtrlNumberInput;
import com.app.trys.utils.StringUtils;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * 数字输入框属性
 * @author linjf
 * @since 2025-02-28
 */
@Getter
public class CtrlNumberInputProperties extends CtrlProperties {

    /**
     * 数字输入类型，整数、小数等
     */
    private final Class<?> numberInputType;
    /**
     * 提示文本
     */
    private final String prompt;
    /**
     * 默认值
     */
    private final BigDecimal defValue;


    public CtrlNumberInputProperties(CtrlNumberInput ctrl, Class<?> numberInputType) {
        super(ctrl.name(), ctrl.base());
        this.numberInputType = numberInputType == null ? BigDecimal.class : numberInputType;
        this.prompt = ctrl.prompt();
        this.defValue = StringUtils.isEmpty(ctrl.defValue()) ? null : new BigDecimal(ctrl.defValue());
    }

}
