package com.app.trys.components.facade.controls.factory;

import com.app.trys.base.form.properties.CtrlProperties;
import com.app.trys.components.facade.controls.TrysControlWithLabel;
import com.app.trys.components.facade.controls.base.TrysNumberInput;
import com.app.trys.components.facade.controls.factory.ctrl.*;
import com.app.trys.utils.CollUtils;
import com.app.trys.utils.ReflectUtils;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/**
 * @author linjf
 * @since 2025-03-25
 */
@Accessors(chain = true)
@Setter
public class ControlFactory {

    private Annotation annotation;

    private Field bindField;

    private static List<CtrlFactorySupport> factories = Collections.unmodifiableList(CollUtils.toList(
            new NumberInputFactory(),
            new ComboBoxFactory(),
            new InputFactory(),
            new SwitchFactory(),
            new DatePickerFactory(),
            new DateRangePickerFactory()
    ));


    public ControlFactory(Annotation annotation) {
        this.annotation = annotation;
    }

    public static TrysControlWithLabel createWithId(Class<?> dtoClass) {
        String idName = "id";
        Field id = ReflectUtils.getField(dtoClass, idName);
        CtrlProperties properties = new CtrlProperties(idName, 0, null, false, false);
        TrysControlWithLabel control = TrysControlWithLabel.of(properties, TrysNumberInput.of(id.getType()));
        control.setManaged(false);
        control.setVisible(false);
        control.setBindField(id);

        return control;
    }

    public TrysControlWithLabel create() {
        CtrlFactorySupport ctrlFactory = factories.stream().filter(factory -> factory.support(annotation)).findFirst().orElseThrow(() -> new RuntimeException("No control factory found!"));
        TrysControlWithLabel controlWithLabel = ctrlFactory.create(annotation, bindField);

        controlWithLabel.reset();
        controlWithLabel.setBindField(bindField);
//        controlWithLabel.setStyle("-fx-background-color: #507");
        return controlWithLabel;
    }


}
