//package com.app.trys.generator.impl.modulegenerator;
//
//import com.app.trys.base.form.SaveForm;
//import com.app.trys.components.facade.controls.base.TrysButton;
//import com.app.trys.components.facade.form.TrysSaveForm;
//import com.app.trys.generator.ModuleGenerator;
//import com.app.trys.manager.WindowManager;
//import com.app.trys.object.IdDTO;
//import javafx.scene.layout.Region;
//import javafx.stage.Window;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
///**
// * 生成一个按钮，点击出现更新表单窗口
// *
// * @author linjf
// * @since 2025-03-28
// */
//@Component("EditFormBtnGenerator")
//public class EditFormBtnGenerator implements ModuleGenerator {
//
//    @Autowired
//    private WindowManager windowManager;
//
//    @Override
//    public <T extends IdDTO> Optional<Region> generate(Class<T> dtoClass) {
//        return Optional.of(dtoClass).filter(t -> t.isAnnotationPresent(SaveForm.class)).map(t -> TrysButton.ofDefault("编辑", e -> {
//            TrysSaveForm<T> form = TrysSaveForm.ofEdit(t);
//            String title = "编辑 - " + form.getFormProperties().getTitle();
//            Window formWindow = windowManager.createSimpleWindow(title, form);
//            // 成功了就关闭窗口
//            form.addOnSuccessListener(result -> windowManager.close(formWindow));
//            windowManager.show(formWindow);
//        }));
//    }
//
//}
