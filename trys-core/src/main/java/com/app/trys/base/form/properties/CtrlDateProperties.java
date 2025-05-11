package com.app.trys.base.form.properties;

import com.app.trys.base.form.CtrlDate;
import com.app.trys.utils.DateUtils;
import lombok.Getter;

import java.util.Date;

/**
 * 日期选择器属性
 * @author linjf
 * @since 2025-02-28
 */
@Getter
public class CtrlDateProperties extends CtrlProperties {
    /**
     * 默认值
     */
    private Date defValue;

    public CtrlDateProperties(CtrlDate ctrl) {
        super(ctrl.name(), ctrl.base());
        this.defValue = DateUtils.parse(ctrl.defValue());
    }

}
