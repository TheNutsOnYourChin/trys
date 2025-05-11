package com.app.trys.base.form.properties;

import com.app.trys.base.dto.EnumKV;
import com.app.trys.base.form.CtrlComboBox;
import javafx.scene.control.SelectionMode;
import lombok.Getter;

/**
 * 多选框属性
 * @author linjf
 * @since 2025-02-28
 */
@Getter
public class CtrlComboBoxProperties extends CtrlProperties {

    /**
     * 各个选项的键值对目录 {@link EnumKV#getValue()}
     */
    private final String optionsName;
    /**
     * 多选，多选时对应的字段必须是列表
     */
    private final SelectionMode selectionMode;

    /**
     * 默认值
     */
    private final long[] defValue;


    public CtrlComboBoxProperties(CtrlComboBox ctrl) {
        super(ctrl.name(), ctrl.base());
        this.optionsName = ctrl.optionsName();
        this.selectionMode = ctrl.selectionMode();
        this.defValue = ctrl.defValue();
    }
}
