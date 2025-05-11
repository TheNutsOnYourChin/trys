package com.app.trys.components.facade.controls.base;

import com.app.trys.components.facade.controls.ControlAdapter;
import com.app.trys.utils.ReflectUtils;
import javafx.scene.control.ToggleButton;
import lombok.Data;
import lombok.Getter;

/**
 * 开关
 * @author linjf
 * @since 2023/5/15
 */
@Data
public class TrysSwitch extends ToggleButton implements ControlAdapter {

	@Getter
	private Boolean defValue;

	private String onTrueText = "是";

	private String onFalseText = "否";

	private TrysSwitch(){
		selectedProperty().addListener((obs, o, n) -> this.setText(n ? onTrueText : onFalseText));
	}

	public static TrysSwitch of(){
		return new TrysSwitch();
	}



	@Override
	public void setCurrentValue(Object newValue) {
		if(newValue != null && ReflectUtils.isSameClass(newValue.getClass(), Boolean.class)){
			Boolean boolValue = (Boolean) newValue;
			this.setSelected(boolValue);
			this.setText(boolValue ? onTrueText : onFalseText);
		}
	}

	@Override
	public void setDefValue(Object defValue) {
		this.defValue = (Boolean)defValue;
	}

	@Override
	public Boolean getCurrentValue(){
		return isSelected();
	}

}
