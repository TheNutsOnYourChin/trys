package com.app.trys.components.facade.controls.factory;

import com.app.trys.components.facade.controls.base.TrysButton;
import com.app.trys.util.ComponentUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

/**
 * @author linjf
 * @since 2025-03-27
 */
public class ButtonSetFactory {

    public FlowPane buttonSet(Button... buttons) {
        FlowPane pane = ComponentUtils.createFlowPane();
        pane.setAlignment(Pos.CENTER);
        ComponentUtils.addNode(pane, buttons);
        return pane;
    }

    public FlowPane onSaveButtonSet(EventHandler<ActionEvent> onSave, EventHandler<ActionEvent> onReset, EventHandler<ActionEvent> onCancel) {
        return formButtonSet("保存", onSave, onReset, onCancel);
    }

    public FlowPane onSubmitButtonSet(EventHandler<ActionEvent> onOk, EventHandler<ActionEvent> onReset, EventHandler<ActionEvent> onCancel) {
        return formButtonSet("确认", onOk, onReset, onCancel);
    }

    public FlowPane formButtonSet(String okName, EventHandler<ActionEvent> onOk, EventHandler<ActionEvent> onReset, EventHandler<ActionEvent> onCancel) {
        return buttonSet(TrysButton.ofDefault(okName, onOk), TrysButton.ofDefault("重置", onReset), TrysButton.ofNormal("取消", onCancel));
    }
}
