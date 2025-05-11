package com.app.trys.manager;

import com.app.trys.generator.DialogGenerator;
import com.app.trys.generator.SceneGenerator;
import com.app.trys.utils.Actions;
import com.app.trys.utils.SpringContextUtils;
import com.app.trys.utils.optional.ObjectOptional;
import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.*;

;

/**
 * @author linjf
 * @since 2023/2/15
 */
@Component
@Slf4j
public class WindowManager implements BeanPostProcessor {

	private static final ObjectOptional<WindowManager> INSTANCE = ObjectOptional.ofNull();
	public static WindowManager getInstance(){
		return INSTANCE.get();
	}


	/**
	 * 选项框窗口
	 */
	private Window comboBoxWindow;

	/**
	 * 窗口树 TODO 是否有必要
	 */
	private WindowNode windowTree = new WindowNode();



	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// 加入管理
		this.setMainWindow(AbstractJavaFxApplicationSupport.mainStage);
		INSTANCE.set(this);
		return bean;
	}


	public void setMainWindow(Window window){
		windowTree.setWindow(window);
		window.setOnCloseRequest(e -> {
			e.consume();
			exit();
		});
	}

	public Window getMainWindow(){
		checkBeforeManage();
		return windowTree.getWindow();
	}

	private void registry(Window window){
		this.windowTree.addChildren(window);
		window.setOnCloseRequest(e -> close(window));
	}


	public Window create(){
		return create(null);
	}

	public Window create(Window parent){
		checkBeforeManage();
		Stage stage = new Stage();
		stage.initOwner(parent);
		return stage;
	}

	public Window create(String title, Parent root){
		Stage stage = new Stage();
		stage.setTitle(title);
		stage.setScene(SceneGenerator.getInstance().generateScene(root));
		return stage;
	}


	public Window copy(Window source, Class<? extends AbstractFxmlView> viewClass){
		Stage sourceStage = (Stage) source;
		Stage copyStage = new Stage();
		{
			copyStage.setWidth(sourceStage.getWidth());
			copyStage.setHeight(sourceStage.getHeight());
			copyStage.setTitle(sourceStage.getTitle() + " - 副本");
			copyStage.getIcons().addAll(sourceStage.getIcons());
		}
		AbstractFxmlView viewBean = SpringContextUtils.getBean(viewClass);
		copyStage.setScene(SceneGenerator.getInstance().generateScene(viewBean.getView()));
		copyStage.show();
		registry(copyStage);
		return copyStage;
	}


	/**
	 * 销毁
	 */
	public void dispose(Window window){
		if(windowTree.window == window){
			windowTree = null;
		}
		if (windowTree != null) {
			windowTree.remove(window);
		}
	}

	private void checkBeforeManage(){
		Objects.requireNonNull(windowTree.getWindow(), "未找到主窗口");
	}



	public void show(Window window){
		if(window instanceof Stage){
			Stage stage = (Stage) window;
			// 解除最小化，再显示, 显示时聚焦
			stage.setIconified(false);
			stage.show();
			stage.requestFocus();
		}
		// 暂时没有支持其他窗口类型
	}


	public void hide(Window window){
		if (window != null){
			window.hide();
		}
	}

	public void close(Window window){
		if(window instanceof Stage){
			Stage stage = (Stage) window;
			stage.close();
			dispose(stage);
		}
		// 暂时没有支持其他窗口类型
	}

	public Window getComboBoxWindow(){
		if (comboBoxWindow == null){
			Stage stage = new Stage();
			stage.setResizable(false);
			comboBoxWindow = new Stage();
		}
		return comboBoxWindow;
	}

	/**
	 * 打开筛选窗口，切换场景和标题
	 * @param scene 场景
	 * @param title 窗口标题
	 */
	public void showComboBoxWindow(Scene scene, String title){
		Window comboBoxWindow = getComboBoxWindow();
		Stage stage = (Stage) comboBoxWindow;
		stage.setTitle(title);
		stage.setScene(scene);
		show(stage);
	}


	public void hideComboBoxWindow(){
		hide(getComboBoxWindow());
	}


	public void exit(){
		DialogGenerator.getInstance().confirm("退出程序", "退出程序", okEvent -> {
			dispose(windowTree.getWindow());
			SpringContextUtils.getAsApplicationContext().close();
			Platform.exit();
		}, Actions.NULL_ACTION_EVENT);

	}

	@Getter
	@Setter
	public static class WindowNode implements Map.Entry<Window, Set<WindowNode>> {

		private Window window;

		private Set<WindowNode> children;


		public void addChildren(Window... nodes){
			for (Window node : nodes) {
				WindowNode windowNode = new WindowNode();
				windowNode.window = node;
				getValue().add(windowNode);
			}
		}


		public void remove(Window window){
			if(window == null) {
				return;
			}
			Iterator<WindowNode> iterator = children.iterator();
			while(iterator.hasNext()){
				WindowNode windowNode = iterator.next();
				if(windowNode.window == window){
					iterator.remove();
					break;
				} else {
					windowNode.children.forEach(c -> c.remove(window));
				}
			}
		}


		@Override
		public Window getKey() {
			return window;
		}

		@Override
		public Set<WindowNode> getValue() {
			return children == null ? children = new HashSet<>() : children;
		}

		@Override
		public Set<WindowNode> setValue(Set<WindowNode> children) {
			return this.children = children;
		}
	}

}
