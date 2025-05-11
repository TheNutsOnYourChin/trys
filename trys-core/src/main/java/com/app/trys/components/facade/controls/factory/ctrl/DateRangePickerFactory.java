package com.app.trys.components.facade.controls.factory.ctrl;

import com.app.trys.base.form.CtrlDateRange;
import com.app.trys.base.form.properties.CtrlDateRangeProperties;
import com.app.trys.components.facade.controls.TrysControlWithLabel;
import com.app.trys.components.facade.controls.base.TrysDateRangePicker;
import com.app.trys.exceptions.ExceptionUtils;
import com.app.trys.object.DateRange;
import com.app.trys.utils.ReflectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;

public class DateRangePickerFactory implements CtrlFactorySupport {

    @Override
    public boolean support(Annotation annotation) {
        return annotation instanceof CtrlDateRange;
    }

    @Override
    public TrysControlWithLabel create(Annotation annotation, Field bind) {
        ExceptionUtils.isTure(support(annotation), "不支持该控件");
        Optional.ofNullable(bind).map(Field::getType).filter(v -> ReflectUtils.isSameClass(v, DateRange.class)).orElseThrow(() ->  ExceptionUtils.newException("DatePicker需要{0}类型", DateRange.class.getName()));

        CtrlDateRangeProperties properties = new CtrlDateRangeProperties((CtrlDateRange) annotation);
        TrysDateRangePicker ctrl = TrysDateRangePicker.of();

        TrysControlWithLabel control = TrysControlWithLabel.of(properties, ctrl);
        control.setDefValue(properties.getDefValue());

        return control;
    }
}