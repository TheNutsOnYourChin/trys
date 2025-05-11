package com.app.trys.components.facade.form;

import com.app.trys.base.form.SubmitForm;
import com.app.trys.base.form.properties.SubmitFormProperties;
import com.app.trys.components.facade.controls.base.TrysButton;
import com.app.trys.components.facade.controls.factory.ButtonSetFactory;
import com.app.trys.object.IdDTO;
import com.app.trys.utils.ReflectUtils;
import javafx.scene.layout.FlowPane;
import lombok.Getter;

/**
 * 流式布局表单
 * @author linjf
 * @since 2023/3/27
 */
@Getter
public class TrysSubmitForm<VALUE extends IdDTO<?>> extends TrysAbstractForm<VALUE>{
	private static final int SAVE = 0;
	private static final int UPDATE = 1;
	private final int mode;

	private final SubmitFormProperties submitFormProperties;

	private TrysButton submitButton;

	public static <F extends IdDTO<?>> TrysSubmitForm<F> ofSave(Class<F> formClass) {
		return new TrysSubmitForm<>(formClass, SAVE);
	}

	public static <F extends IdDTO<?>> TrysSubmitForm<F> ofEdit(Class<F> formClass) {
		return new TrysSubmitForm<>(formClass, UPDATE);
	}

	private TrysSubmitForm(Class<VALUE> formClass, int mode) {
		super(formClass);
		SubmitForm submitForm = ReflectUtils.getAndCheckAnnotation(formClass, SubmitForm.class);
		this.submitFormProperties = new SubmitFormProperties(super.getFormProperties(), submitForm);
		this.submitButton.setText(submitFormProperties.getSubmitText());
		this.mode = mode;
		// 初始化表单组件的状态
		initControlByMode();

	}

	private void initControlByMode() {
		if(mode == UPDATE) {
			// 在更新表单中,不可编辑的一些输入组件
			getControlList().forEach(control -> control.setDisable(control.getCtrlProperties().isDisableOnEdit()));
		}
	}


	/**
	 * 不同表单对于按钮的功能和排序都可能不一样，这里适用继承
	 */
	protected FlowPane createBtnPane() {
		// 默认为 保存、重置、取消按钮
		FlowPane funcPane = new ButtonSetFactory().onSubmitButtonSet( e -> this.submit(), e -> this.reset(), e -> {
			getScene().getWindow().hide();
			this.reset();
		});
		this.submitButton = (TrysButton) funcPane.getChildren().get(0);
		// 功能按钮放在在"提交"后面
		createModulesByProperties().forEach(v -> funcPane.getChildren().add(1, v));
		return funcPane;
	}

}

