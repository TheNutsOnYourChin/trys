package com.app.trys.generator;

import com.app.trys.base.css.TrysStyleClass;
import com.app.trys.utils.optional.ObjectOptional;
import javafx.scene.Parent;
import javafx.scene.Scene;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 生成场景
 * @author linjf
 * @since 2023/2/15
 */
public interface SceneGenerator extends Generator, InitializingBean {

	ObjectOptional<SceneGenerator> INSTANCE = ObjectOptional.ofNull();

	static SceneGenerator getInstance(){
		return INSTANCE.get();
	}

	@Override
	default void afterPropertiesSet() {
		INSTANCE.set(this);
	}
	/**
	 * 获取一个场景
	 * @param root 根容器
	 * @return 场景对象
	 */
	Scene generateScene(Parent root);

	/**
	 * 设置场景样式
	 */
	void setSceneCss(Scene scene);


	@Component
	class SceneGeneratorImpl implements SceneGenerator {

		@Override
		public Scene generateScene(Parent root) {
			Scene scene = new Scene(root);
			setSceneCss(scene);
			return scene;
		}

		@Override
		public void setSceneCss(Scene scene) {
			new JMetro(Style.LIGHT).setScene(scene);
			scene.getStylesheets().addAll(TrysStyleClass.cssList);
		}
	}
}
