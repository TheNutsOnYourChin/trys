package com.app.trys.components.facade.controls.base;

import javafx.scene.control.Label;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


/**
 * @author linjf
 * @since 2023/2/24
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrysLabel extends Label {

	public TrysLabel(String text){
		setText(text);
	}


	public static TrysLabel of(String text){
		return new TrysLabel(text);
	}

	public static TrysLabel ofWarning(String text){
		TrysLabel label = new TrysLabel(text);
		label.setStyle("-fx-text-fill: #F00");
		return label;
	}

}
