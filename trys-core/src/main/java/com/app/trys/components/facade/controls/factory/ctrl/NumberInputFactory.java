package com.app.trys.components.facade.controls.factory.ctrl;

import com.app.trys.base.form.CtrlNumberInput;
import com.app.trys.base.form.properties.CtrlNumberInputProperties;
import com.app.trys.components.facade.controls.TrysControlWithLabel;
import com.app.trys.components.facade.controls.base.TrysNumberInput;
import com.app.trys.exceptions.ExceptionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;

public class NumberInputFactory implements CtrlFactorySupport {


    @Override
    public boolean support(Annotation annotation) {
        return annotation instanceof CtrlNumberInput;
    }

    @Override
    public TrysControlWithLabel create(Annotation annotation, Field bind) {
        ExceptionUtils.isTure(support(annotation), "不支持该控件");
        Class<?> fieldType = Optional.ofNullable(bind).map(Field::getType).orElse(null);
        CtrlNumberInputProperties properties = new CtrlNumberInputProperties((CtrlNumberInput) annotation, fieldType);

        TrysNumberInput numberInput = TrysNumberInput.of(fieldType);
        numberInput.setPromptText(properties.getPrompt());

        TrysControlWithLabel control = TrysControlWithLabel.of(properties, numberInput);
        control.setDefValue(properties.getDefValue());
        return control;
    }
}