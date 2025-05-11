package com.app.trys.config.bean;

import com.app.trys.config.BaseProperty;
import com.app.trys.config.FrameInjectConfig;
import com.app.trys.generator.SceneGenerator;
import de.felixroske.jfxsupport.GUIState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * @author linjf
 * @since 2025-04-03
 */
@Configuration
public class AfterRunner implements ApplicationRunner {

    @Autowired
    private SceneGenerator sceneGenerator;
    @Autowired
    private FrameInjectConfig frameInjectConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BaseProperty.putInjectConfig(frameInjectConfig);
        sceneGenerator.setSceneCss(GUIState.getScene());
    }

}
