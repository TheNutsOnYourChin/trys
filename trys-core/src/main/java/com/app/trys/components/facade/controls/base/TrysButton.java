package com.app.trys.components.facade.controls.base;

import com.app.trys.base.css.TrysStyleClass;
import com.app.trys.utils.Actions;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import lombok.Getter;
import lombok.Setter;

/**
 * @author linjf
 * @since 2023/2/15
 */
@Getter
@Setter
public class TrysButton extends Button {

	/**
	 * 值
	 */
	private Object value;


	private TrysButton(String text, EventHandler<ActionEvent> onClick){
		setText(text);
//		getStyleClass().setAll(TrysStyleClass.BUTTON);
		setOnAction(e -> Actions.ifTrue(isFocused(), () -> onClick.handle(e)));
	}

	public static TrysButton ofDefault(String name, EventHandler<ActionEvent> onClick){
		TrysButton btn = new TrysButton(name, onClick);
		btn.setDefaultButton(true);
		btn.setStyle(TrysStyleClass.white_text);
		return btn;
	}

	public static TrysButton ofNormal(String name, EventHandler<ActionEvent> onClick){
		return new TrysButton(name, onClick);
	}

	public static TrysButton ofLabel(String name, Object value, EventHandler<ActionEvent> onClick){
		TrysButton btn = new TrysButton(name, onClick);
		btn.setValue(value);
		return btn;
	}

	public static TrysButton ofWaring(String name, EventHandler<ActionEvent> onClick){
		TrysButton btn = new TrysButton(name, onClick);
		btn.getStyleClass().add(0, TrysStyleClass.danger_btn);
		btn.setStyle(TrysStyleClass.white_text);
		return btn;
	}

	
}
