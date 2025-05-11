package com.app.trys.generator.impl.modulegenerator;

import com.app.trys.components.facade.controls.base.TrysButton;
import com.app.trys.generator.ModuleGenerator;
import com.app.trys.object.IdDTO;
import com.app.trys.generator.BtnWithPopupGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 生成一个按钮，点击出现保存表单窗口
 *
 * @author linjf
 * @since 2025-03-28
 */
@Component("SaveFormBtnGenerator")
public class SaveFormBtnGenerator implements ModuleGenerator {

    @Autowired
    private BtnWithPopupGenerator btnWithPopupGenerator;

    @Override
    public <T extends IdDTO<?>> Optional<TrysButton> generate(Class<T> dtoClass) {
        return btnWithPopupGenerator.generateSaveFormCallBtn(dtoClass);
    }

}
