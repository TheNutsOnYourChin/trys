package com.app.trys.generator;

import com.app.trys.base.form.SubmitForm;
import com.app.trys.components.facade.controls.base.TrysButton;
import com.app.trys.components.facade.form.TrysSubmitForm;
import com.app.trys.config.BaseProperty;
import com.app.trys.manager.WindowManager;
import com.app.trys.object.IdDTO;
import com.app.trys.utils.ReflectUtils;
import com.app.trys.utils.optional.ObjectOptional;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 触发弹窗的按钮
 *
 * @author linjf
 * @since 2025-03-28
 */
public interface BtnWithPopupGenerator extends Generator, InitializingBean {

    ObjectOptional<BtnWithPopupGenerator> INSTANCE = ObjectOptional.ofNull();

    static BtnWithPopupGenerator getInstance(){
        return INSTANCE.get();
    }

    @Override
    default void afterPropertiesSet() {
        INSTANCE.set(this);
    }

    /**
     * 点击按钮弹出一个保存表单窗口
     */
    <T extends IdDTO<?>> Optional<TrysButton> generateSaveFormCallBtn(Class<T> dtoClass);

    /**
     * 点击按钮弹出一个更新表单窗口
     */
    <T extends IdDTO<?>> Optional<TrysButton> generateEditFormCallBtn(Class<T> dtoClass, T value);

    @Component
    class BtnWithPopupGeneratorImpl implements BtnWithPopupGenerator {

        @Autowired
        private WindowManager windowManager;

        @Override
        public <T extends IdDTO<?>> Optional<TrysButton> generateSaveFormCallBtn(Class<T> dtoClass) {
            return Optional.of(dtoClass).filter(t -> t.isAnnotationPresent(SubmitForm.class)).map(t -> {
                String btnText = ReflectUtils.getAndCheckAnnotation(t, SubmitForm.class).base().shortTitle();
                return TrysButton.ofDefault(btnText, e -> {
                    TrysSubmitForm<T> form = TrysSubmitForm.ofSave(t);
                    String title = btnText + " - " + form.getFormProperties().getTitle();
                    newWindow((TrysButton)e.getSource(), title, form);
                });
            });
        }


        @Override
        public <T extends IdDTO<?>> Optional<TrysButton> generateEditFormCallBtn(Class<T> dtoClass, T value) {
            return Optional.of(dtoClass).filter(t -> t.isAnnotationPresent(SubmitForm.class)).map(t -> TrysButton.ofDefault("编辑", e -> {
                TrysSubmitForm<T> form = TrysSubmitForm.ofEdit(t);
                form.setDefValue(value);
                String title = "编辑 - " + form.getFormProperties().getTitle();
                newWindow((TrysButton)e.getSource(), title, form);
            }));
        }

        private <T extends IdDTO<?>> void newWindow(Parent parent, String title, TrysSubmitForm<T> form) {
            form.setPadding(BaseProperty.PADDING);
            Stage formWindow = (Stage) windowManager.create(title, form);
            formWindow.initOwner(parent.getScene().getWindow());
            formWindow.initModality(Modality.WINDOW_MODAL);
            // 先让窗口自适应高度，在把padding补回高度
            Platform.runLater(() -> formWindow.setHeight(formWindow.getHeight() + form.getPadding().getBottom()));
            windowManager.show(formWindow);
            // 成功了就关闭窗口
            form.addOnSuccessListener((value, result) -> windowManager.close(formWindow));
        }
    }

}
