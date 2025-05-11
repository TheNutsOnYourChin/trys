package com.app.trys.base.form.properties;

import com.app.trys.base.form.Ctrl;
import com.app.trys.components.facade.form.validator.validator.ControlValidator;
import com.app.trys.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @author linjf
 * @since 2025-03-19
 */
@Getter
public class CtrlProperties {

    /**
     * 名称
     **/
    @Setter
    protected String name;
    /**
     * 宽度占比，例如设置为2，则占用2个表单控件的宽度
     **/
    protected final int widthWeight;
    /**
     * 校验
     */
    protected final Class<? extends ControlValidator>[] validators;
    /**
     * 修改表单时，该字段处于不可更改状态
     */
    protected final boolean disableOnEdit;
    /**
     * 自动修正表单控件标签，使该项能够完整显示标签文本
     */
    protected final boolean autoLabelWidth;

    protected CtrlProperties(String name, Ctrl base) {
        // 外部传入的名称优先
        this.name = StringUtils.isEmpty(name) ? base.name() : name;
        this.widthWeight = base.widthWeight();
        this.validators = base.validators();
        this.disableOnEdit = base.disableOnEdit();
        this.autoLabelWidth = base.autoLabelWidth();
    }

    public CtrlProperties(String name, int widthWeight, Class<? extends ControlValidator>[] validators, boolean disableOnEdit, boolean autoLabelWidth) {
        this.name = name;
        this.widthWeight = widthWeight;
        this.validators = validators == null ? new Class[0] : validators;
        this.disableOnEdit = disableOnEdit;
        this.autoLabelWidth = autoLabelWidth;
    }
}
