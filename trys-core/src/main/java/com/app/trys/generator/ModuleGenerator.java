package com.app.trys.generator;

import com.app.trys.components.facade.controls.base.TrysButton;
import com.app.trys.object.IdDTO;

import java.util.Optional;

/**
 * 模块组件生成器
 *
 * @author linjf
 * @since 2025-03-28
 */
public interface ModuleGenerator extends Generator {

    /**
     * 按指定的类型生成组件
     *
     * @param dtoClass 组件注解类型
     * @return 组件
     */
    <T extends IdDTO<?>> Optional<TrysButton> generate(Class<T> dtoClass);
}
