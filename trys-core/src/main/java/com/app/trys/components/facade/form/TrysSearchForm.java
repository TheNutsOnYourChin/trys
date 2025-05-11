package com.app.trys.components.facade.form;

import com.app.trys.components.facade.controls.base.TrysButton;
import com.app.trys.components.facade.controls.factory.ButtonSetFactory;
import com.app.trys.object.IdDTO;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;

/**
 * 查询表单
 *
 * @author linjf
 * @since 2023/3/27
 */
public abstract class TrysSearchForm<VALUE extends IdDTO<?>> extends TrysAbstractForm<VALUE> {


	public TrysSearchForm(Class<VALUE> formClass){
		super(formClass);

		getButtonPane().setAlignment(Pos.CENTER_RIGHT);
	}

	@Override
	protected FlowPane createBtnPane() {
		FlowPane funcBar = new ButtonSetFactory().buttonSet();
		funcBar.getChildren().add(TrysButton.ofDefault("查询", e -> this.submit()));
		createModulesByProperties().forEach(v -> funcBar.getChildren().add(v));
        funcBar.getChildren().add(TrysButton.ofDefault("重置", e -> reset()));
		return funcBar;
	}
}

