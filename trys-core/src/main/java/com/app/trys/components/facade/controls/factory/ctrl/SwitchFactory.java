package com.app.trys.components.facade.controls.factory.ctrl;

import com.app.trys.base.form.CtrlSwitch;
import com.app.trys.base.form.properties.CtrlSwitchProperties;
import com.app.trys.components.facade.controls.TrysControlWithLabel;
import com.app.trys.components.facade.controls.base.TrysSwitch;
import com.app.trys.exceptions.ExceptionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class SwitchFactory implements CtrlFactorySupport {

    @Override
    public boolean support(Annotation annotation) {
        return annotation instanceof CtrlSwitch;
    }

    @Override
    public TrysControlWithLabel create(Annotation annotation, Field bind) {
        ExceptionUtils.isTure(support(annotation), "不支持该控件");

        CtrlSwitchProperties properties = new CtrlSwitchProperties((CtrlSwitch) annotation);
        TrysSwitch switchCtrl = TrysSwitch.of();
        switchCtrl.setOnTrueText(properties.getOnTrueText());
        switchCtrl.setOnFalseText(properties.getOnFalseText());

        TrysControlWithLabel control = TrysControlWithLabel.of(properties, switchCtrl);
        control.setDefValue(properties.getDefValue());
        return control;
    }
}