package com.app.trys.generator;

import com.app.trys.base.ImageLoader;
import com.app.trys.base.bus.msg.Level;
import com.app.trys.base.css.TrysStyleClass;
import com.app.trys.components.facade.controls.base.TrysButton;
import com.app.trys.components.facade.controls.base.TrysLabel;
import com.app.trys.components.facade.controls.factory.ButtonSetFactory;
import com.app.trys.config.BaseProperty;
import com.app.trys.event.EventAdapter;
import com.app.trys.event.component.OnDelSuccessEvent;
import com.app.trys.event.request.OnRequestEvent;
import com.app.trys.manager.WindowManager;
import com.app.trys.utils.ReflectUtils;
import com.app.trys.utils.optional.ObjectOptional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 窗口生成器
 *
 * @author linjf
 * @since 2023/2/15
 */
public interface DialogGenerator extends Generator, InitializingBean {

	ObjectOptional<DialogGenerator> INSTANCE = ObjectOptional.ofNull();

	static DialogGenerator getInstance(){
		return INSTANCE.get();
	}

	@Override
	default void afterPropertiesSet() {
		INSTANCE.set(this);
	}

	void normal(String msg);

	void success(String msg, EventHandler<ActionEvent> onOK);

	void delConfirm(Object value, Class<? extends OnRequestEvent> onDelEvent);

	void confirm(String title, String msg, EventHandler<ActionEvent> onOK, EventHandler<ActionEvent> onCancel);

	Window baseDialog(Level lv, String title, String msg, TrysButton... buttons);

	@Component
	class DialogGeneratorImpl implements DialogGenerator {

		@Autowired
		private SceneGenerator sceneGenerator;
		@Autowired
		private WindowManager windowManager;
		@Autowired
		private ImageLoader imageLoader;
		@Autowired
		private EventAdapter eventAdapter;

		public static final int ICON_HEIGHT = 30;

		@Override
		public void normal(String msg) {
			ObjectOptional<Window> dialogOpt = ObjectOptional.ofNull();
			dialogOpt.set(baseDialog(Level.NORMAL, "一个消息", msg, TrysButton.ofWaring("确定", e -> hideAfterClick(dialogOpt, e))));
		}

		@Override
		public void success(String msg, EventHandler<ActionEvent> onOK) {
			ObjectOptional<Window> dialogOpt = ObjectOptional.ofNull();
			dialogOpt.set(baseDialog(
					Level.SUCCESS,
					"已完成",
					msg,
					TrysButton.ofWaring("确定", e -> hideAfterClick(dialogOpt, onOK, e))
			));
		}

		@Override
		public void delConfirm(Object value, Class<? extends OnRequestEvent> onDelEvent) {
			ObjectOptional<Window> dialogOpt = ObjectOptional.ofNull();
			dialogOpt.set(baseDialog(
					Level.WARN,
					"删除",
					"确定要删除吗？",
					TrysButton.ofWaring("确定", e -> {
						hideAfterClick(dialogOpt, e);
						// 发布删除成功事件
						eventAdapter.publish(ReflectUtils.newInstance(onDelEvent, value));
						eventAdapter.publish(new OnDelSuccessEvent(value.getClass(), value));
					}),
					TrysButton.ofNormal("取消", e -> hideAfterClick(dialogOpt, e))
			));
		}

		@Override
		public void confirm(String title, String msg, EventHandler<ActionEvent> onOK, EventHandler<ActionEvent> onCancel) {
			ObjectOptional<Window> dialogOpt = ObjectOptional.ofNull();
			dialogOpt.set(baseDialog(
					Level.NORMAL,
					title,
					msg,
					TrysButton.ofDefault("确定", e -> hideAfterClick(dialogOpt, onOK, e)),
					TrysButton.ofNormal("取消", e -> hideAfterClick(dialogOpt, onCancel, e))
			));
		}


		private void hideAfterClick(ObjectOptional<Window> dialogOpt, ActionEvent e) {
			// 执行事件后关闭对话框
			hideAfterClick(dialogOpt, null, e);
		}

		private void hideAfterClick(ObjectOptional<Window> dialogOpt, EventHandler<ActionEvent> onClick, ActionEvent e) {
			// 执行事件后关闭对话框
			if (onClick != null) {
				onClick.handle(e);
			}
			dialogOpt.ifPresent(windowManager::hide);
		}

		@Override
		public Window baseDialog(Level lv, String title, String msg, TrysButton... buttons) {
			VBox vBox = new VBox();
			vBox.setPadding(BaseProperty.PADDING);
			vBox.setSpacing(BaseProperty.padding);
			{
				HBox content = new HBox();
				content.setSpacing(BaseProperty.padding);
				content.getStyleClass().add(TrysStyleClass.pane_border_b);
				{
					ImageView imageView = new ImageView(imageLoader.getLevelImage(lv));
					imageView.setFitHeight(ICON_HEIGHT);
					imageView.setPreserveRatio(true);
					TrysLabel msgLabel = TrysLabel.of(msg);
					msgLabel.setPrefHeight(ICON_HEIGHT);
					msgLabel.setStyle("-fx-font-size: 16;");
					content.getChildren().addAll(imageView, msgLabel);
				}
				vBox.getChildren().add(content);
				// 按钮集合
				FlowPane btnSet = new ButtonSetFactory().buttonSet(buttons);
				btnSet.setAlignment(Pos.CENTER);
				vBox.getChildren().add(btnSet);
			}
			Stage dialog = (Stage) windowManager.create(title, vBox);
			dialog.setResizable(false);
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.setWidth(300);
			dialog.getIcons().add(imageLoader.getLevelImage(lv));
			dialog.show();
			return dialog;
		}
	}
}
