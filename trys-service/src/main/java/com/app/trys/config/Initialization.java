package com.app.trys.config;

import com.app.trys.base.enums.SelectOptionsRegister;
import com.app.trys.enums.OptionsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author linjf
 * @since 2025-04-03
 */
@Component
public class Initialization implements ApplicationRunner {

    @Autowired
    private SelectOptionsRegister selectOptionsRegister;


    @Override public void run(ApplicationArguments args) throws Exception {
        // 注册下拉选项
        registryComboBoxItem();
    }


    private void registryComboBoxItem(){
        selectOptionsRegister.registry(OptionsEnum.yesOrNo);
    }
}
