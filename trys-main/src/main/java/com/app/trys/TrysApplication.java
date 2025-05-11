package com.app.trys;

import com.app.trys.constant.SystemConst;
import com.app.trys.view.MainView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import de.felixroske.jfxsupport.GUIState;
import de.felixroske.jfxsupport.SplashScreen;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication(scanBasePackages = "com.app.trys")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TrysApplication extends AbstractJavaFxApplicationSupport {


	/**
	 * 因为java11中JavaFX需要在其它类中通过class对象参数启动，所以用{@link Starter}去启动它
	 */
	public static void launchApp(String[] args) {
		launch(TrysApplication.class, MainView.class, new SplashScreen(){
			@Override
			public String getImagePath() {
				// 图片路径
				return "/images/icon_01.jpg";
			}
		}, args);
	}


	@Override
	public void start(final Stage stage) throws Exception {
		// 提前设置scene，借此导入样式，这样在整个场景中就能够使用样式
		Scene scene = new Scene(new Pane());
		GUIState.setScene(scene);
		super.start(stage);
	}

	@Override
	public void beforeInitialView(Stage stage, ConfigurableApplicationContext ctx) {
	}

	@Override
	@SuppressWarnings(SystemConst.ALL)
	public Collection<Image> loadDefaultIcons() {
		return Arrays.asList(
				new Image(getClass().getResource("/icons/app_icon_24x24.png").toExternalForm()),
				new Image(getClass().getResource("/icons/app_icon_24x24.png").toExternalForm()),
				new Image(getClass().getResource("/icons/app_icon_36x36.png").toExternalForm()),
				new Image(getClass().getResource("/icons/app_icon_42x42.png").toExternalForm()),
				new Image(getClass().getResource("/icons/app_icon_64x64.png").toExternalForm())
		);
	}
}
