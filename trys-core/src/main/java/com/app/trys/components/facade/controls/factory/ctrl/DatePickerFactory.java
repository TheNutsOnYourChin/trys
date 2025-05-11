package com.app.trys.components.facade.controls.factory.ctrl;

import com.app.trys.base.form.CtrlDate;
import com.app.trys.base.form.properties.CtrlDateProperties;
import com.app.trys.components.facade.controls.TrysControlWithLabel;
import com.app.trys.components.facade.controls.base.TrysDatePicker;
import com.app.trys.exceptions.ExceptionUtils;
import com.app.trys.utils.ReflectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Optional;

public class DatePickerFactory implements CtrlFactorySupport {

    @Override
    public boolean support(Annotation annotation) {
        return annotation instanceof CtrlDate;
    }

    @Override
    public TrysControlWithLabel create(Annotation annotation, Field bind) {
        ExceptionUtils.isTure(support(annotation), "不支持该控件");
        Optional.ofNullable(bind).map(Field::getType).filter(v -> ReflectUtils.isSameClass(v, Date.class)).orElseThrow(() ->  ExceptionUtils.newException("DatePicker需要{0}类型"));

        CtrlDateProperties properties = new CtrlDateProperties((CtrlDate) annotation);
        TrysDatePicker ctrl = TrysDatePicker.of();

        TrysControlWithLabel control = TrysControlWithLabel.of(properties, ctrl);
        control.setDefValue(properties.getDefValue());

        return control;
    }
}