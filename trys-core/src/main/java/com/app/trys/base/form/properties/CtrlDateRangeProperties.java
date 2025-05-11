package com.app.trys.base.form.properties;

import com.app.trys.base.form.CtrlDateRange;
import com.app.trys.object.DateRange;
import com.app.trys.utils.Actions;
import com.app.trys.utils.DateUtils;
import lombok.Getter;

/**
 * 日期范围选择器属性
 * @author linjf
 * @since 2025-02-28
 */
@Getter
public class CtrlDateRangeProperties extends CtrlProperties {
    /**
     * 默认值
     */
    private DateRange defValue = new DateRange();

    public CtrlDateRangeProperties(CtrlDateRange ctrl) {
        super(ctrl.name(), ctrl.base());
        defValue.setBgn(DateUtils.parse(ctrl.defBgnValue()));
        defValue.setEnd(DateUtils.parse(ctrl.defEndValue()));
    }
}
