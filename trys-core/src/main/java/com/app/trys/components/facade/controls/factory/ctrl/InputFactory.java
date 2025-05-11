package com.app.trys.components.facade.controls.factory.ctrl;

import com.app.trys.base.form.CtrlInput;
import com.app.trys.base.form.properties.CtrlInputProperties;
import com.app.trys.components.facade.controls.TrysControlWithLabel;
import com.app.trys.components.facade.controls.base.TrysInput;
import com.app.trys.exceptions.ExceptionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class InputFactory implements CtrlFactorySupport {


    @Override
    public boolean support(Annotation annotation) {
        return annotation instanceof CtrlInput;
    }

    @Override
    public TrysControlWithLabel create(Annotation annotation, Field bind) {
        ExceptionUtils.isTure(support(annotation), "不支持该控件");
        CtrlInputProperties properties = new CtrlInputProperties((CtrlInput) annotation);

        TrysInput ctrl = TrysInput.of();
        ctrl.setMaxLength(properties.getMaxLength());
        ctrl.setPromptText(properties.getPrompt());

        TrysControlWithLabel control = TrysControlWithLabel.of(properties, ctrl);
        control.setDefValue(properties.getDefValue());
        return control;
    }
}