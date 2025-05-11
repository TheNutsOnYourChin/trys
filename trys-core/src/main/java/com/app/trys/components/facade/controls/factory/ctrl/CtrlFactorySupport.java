package com.app.trys.components.facade.controls.factory.ctrl;

import com.app.trys.components.facade.controls.TrysControlWithLabel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 控件工厂支持
 */
public interface CtrlFactorySupport {

    boolean support(Annotation annotation);

    TrysControlWithLabel create(Annotation annotation, Field bind);
}