package com.app.trys.components.facade.controls.base;

import com.app.trys.components.facade.controls.ControlAdapter;
import com.app.trys.util.Constants;
import com.app.trys.utils.Actions;
import com.app.trys.utils.StringUtils;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author linjf
 * @since 2023/2/21
 */
public abstract class TrysTextField extends TextField implements ControlAdapter {

	/**
	 * 回车键监听任务列表
	 */
	private final List<Consumer<String>> onEnterPressListenerList = new ArrayList<>();
	/**
	 * 最大输入长度
	 */
	private ChangeListener<String> lengthListener = Constants.EMPTY_CHANGE_LISTENER;
	/**
	 * 默认值
	 */
	@Getter
	protected String defValue;


	protected TrysTextField() {
		this(null);
	}

	protected TrysTextField(Consumer<String> onEnterPress) {
//		getStyleClass().addAll(TrysStyleClass.INPUT);
		// 按下回车后执行回调
		Actions.ifTrue(onEnterPress != null, () -> onEnterPressListenerList.add(onEnterPress));
		addEventFilter(KeyEvent.KEY_PRESSED, e -> Actions.ifTrue(e.getCode().equals(KeyCode.ENTER), () -> onEnterPressListenerList.forEach(l -> l.accept(getText()))));
	}



	/**
	 * 回车键监听
	 */
	public void addOnEnterPressListener(Consumer<String> onEnterPress){
		onEnterPressListenerList.add(onEnterPress);
	}

	@Override
	public Object getCurrentValue() {
		return getText();
	}

	@Override
	public void setCurrentValue(Object text) {
		setText(text == null ? StringUtils.EMPTY : text.toString());
	}

	/**
	 * 设置默认值
	 * @param defValue 默认值
	 */
	@Override
	public void setDefValue(Object defValue){
		this.defValue = defValue != null ? defValue.toString() : null;
	}


	public void setMaxLength(int maxLength) {
		this.textProperty().removeListener(this.lengthListener);
		this.lengthListener = (observable, oldValue, newValue) -> Actions.ifTrue(getText().length() > maxLength, () -> setText(oldValue));
		this.textProperty().addListener(lengthListener);
	}
}
