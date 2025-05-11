package com.app.trys.components.facade.controls.factory.ctrl;

import com.app.trys.base.dto.OptionsSupplier;
import com.app.trys.base.enums.SelectOptionsRegister;
import com.app.trys.base.form.CtrlComboBox;
import com.app.trys.base.form.properties.CtrlComboBoxProperties;
import com.app.trys.components.facade.controls.TrysControlWithLabel;
import com.app.trys.components.facade.controls.base.TrysComboBox;
import com.app.trys.exceptions.ExceptionUtils;
import com.app.trys.utils.SpringContextUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ComboBoxFactory implements CtrlFactorySupport {


    @Override
    public boolean support(Annotation annotation) {
        return annotation instanceof CtrlComboBox;
    }

    @Override
    public TrysControlWithLabel create(Annotation annotation, Field bind) {
        ExceptionUtils.isTure(support(annotation), "不支持该控件");
        // 选项列表
        CtrlComboBoxProperties properties = new CtrlComboBoxProperties((CtrlComboBox)annotation);
        String optionsName = properties.getOptionsName();
        SelectOptionsRegister itemRegister = SpringContextUtils.getBean(SelectOptionsRegister.class);
        OptionsSupplier optionsSupplier = itemRegister.findOptionsSupplierByName(optionsName);
        TrysComboBox comboBox = TrysComboBox.of(optionsSupplier, properties.getSelectionMode());

        TrysControlWithLabel control = TrysControlWithLabel.of(properties, comboBox);
        control.setDefValue(properties.getDefValue());
        return control;
    }
}