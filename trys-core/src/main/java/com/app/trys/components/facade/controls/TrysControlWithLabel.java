package com.app.trys.components.facade.controls;

import com.app.trys.base.form.properties.CtrlProperties;
import com.app.trys.components.facade.controls.base.TrysLabel;
import com.app.trys.components.facade.form.validator.validator.ControlValidator;
import com.app.trys.config.BaseProperty;
import com.app.trys.exceptions.ExceptionUtils;
import com.app.trys.util.ComponentUtils;
import com.app.trys.utils.StringUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;


/**
 * 表单控件，带标签的组件
 *
 * @author linjf
 * @since 2023/2/27
 */
@Getter
public class TrysControlWithLabel extends GridPane implements ControlAdapter {

	/**
	 * 属性
	 */
	private CtrlProperties ctrlProperties;
	/**
	 * 表单控件标签
	 */
	private Label label;
	/**
	 * 警告信息
	 */
	private Label msg;
	/**
	 * 表单控件输入组件（桥接）
	 */
	private ControlAdapter control;
	/**
	 * 表单控件标签所在列
	 */
	private ColumnConstraints labelColumnConstraints;
	/**
	 * 表单控件输入组件所在列
	 */
	private ColumnConstraints ctrlColumnConstraints;

	/**
	 * 绑定字段名
	 */
	@Setter
	private Field bindField;



	public static TrysControlWithLabel ofSimple(String name, ControlAdapter control) {
		CtrlProperties properties = new CtrlProperties(name, 1, null, false, true);
		return new TrysControlWithLabel(properties, control);
	}

	public static TrysControlWithLabel of(CtrlProperties properties, ControlAdapter control) {
		return new TrysControlWithLabel(properties, control);
	}

	private TrysControlWithLabel(CtrlProperties ctrlProperties, ControlAdapter control) {
		this.ctrlProperties = ctrlProperties;

		this.label = TrysLabel.of(ctrlProperties.getName());
		this.control = control;
		this.msg = TrysLabel.ofWarning(StringUtils.EMPTY);
		this.labelColumnConstraints = new ColumnConstraints();
		this.ctrlColumnConstraints = new ColumnConstraints();
		getColumnConstraints().addAll(labelColumnConstraints, ctrlColumnConstraints);
		add(label, 0, 0);
		add(this.control.asRegion(), 1, 0);

		initStyle();

	}

	private void initStyle() {
		setAlignment(Pos.TOP_LEFT);
		int widthWeight = ctrlProperties.getWidthWeight();
		// 权重 * 宽度 - 内边距 * (权重 - 1)
		int totalWidth = widthWeight * BaseProperty.controlLineW + BaseProperty.padding * (widthWeight - 1);
		int totalHeight = BaseProperty.controlLineH;
		setPrefHeight(totalHeight);
		setPrefWidth(totalWidth);

		label.setAlignment(Pos.CENTER_RIGHT);
		int labelWidth = BaseProperty.controlLabelW;
		label.setPrefWidth(labelWidth);

		int ctrlWidth = totalWidth - labelWidth;
		control.asRegion().setPrefWidth(ctrlWidth);
		control.asRegion().setPrefHeight(totalHeight);

		this.setLabelText(ctrlProperties.getName());

//		setStyle("-fx-background-color: #499");
	}


	public void setLabelText(String text) {
		setLabelText(text, ctrlProperties.isAutoLabelWidth());
	}

	/**
	 * 重新设置标签内容，并根据内容调整标签宽度占比
	 *
	 * @param text      标签文本
	 * @param autoWidth 根据标签内容调整标签宽度占比
	 */
	public void setLabelText(String text, boolean autoWidth) {
		label.setText(text);
		// 如果是需要完整显示标签文本，则标签在默认长度 ~ 200之间自动调整
		double labelWidth = autoWidth ? BaseProperty.padding + Math.min(Math.max(text.length() * 12, BaseProperty.controlLabelW), 200) : label.getPrefWidth();
		label.setPrefWidth(labelWidth);

		// 标签挤压输入控件
		ComponentUtils.autoBoxSize(control.asRegion(), this, w -> w.subtract(labelWidth), null);
		labelColumnConstraints.setPrefWidth(label.getPrefWidth());
		ComponentUtils.bindProperty(ctrlColumnConstraints.prefWidthProperty(), this.widthProperty(), w -> w.subtract(labelWidth), b -> ctrlColumnConstraints.setPrefWidth(b.getValue().doubleValue()));
	}


	public Field getBindField(){
		ExceptionUtils.nonNull(bindField, "表单控件缺少绑定字段");
		return bindField;
	}

	/**
	 * 校验列表
	 */
	public Class<? extends ControlValidator>[] getValidators(){
		return ctrlProperties.getValidators();
	}

	/**
	 * 设置警告信息，有则显示，无则隐藏
	 */
	public void setMsgText(String text){
		getChildren().remove(this.msg);
		if(StringUtils.nonEmpty(text)){
			msg.setText(text);
			add(msg, 1, 1);
		}
	}

	@Override
	public Object getCurrentValue(){
		return control.getCurrentValue();
	}

	@Override
	public void setCurrentValue(Object newValue) {
		control.setCurrentValue(newValue);
	}

	/**
	 * 重置信息，移除校验警告
	 */
	@Override
	public void reset() {
		setMsgText(null);
		control.reset();
	}


	@Override
	public Object getDefValue() {
		return control.getDefValue();
	}


	@Override
	public void setDefValue(Object defValue){
		control.setDefValue(defValue);
	}
}
