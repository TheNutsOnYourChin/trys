package com.app.trys.util;

import com.app.trys.config.BaseProperty;
import com.app.trys.utils.Actions;
import javafx.beans.binding.NumberExpression;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author linjf
 * @since 2023/2/24
 */
public class ComponentUtils {



	public static void addNode(Pane parent, Node... nodes) {
		if(nodes == null){
			return;
		}
		ObservableList<Node> children = parent.getChildren();
		for (Node node : nodes) {
			if (node != null && !children.contains(node)) {
				children.add(node);
			}
		}
	}

	public static FlowPane createFlowPane(){
		FlowPane pane = new FlowPane();
		pane.setVgap(BaseProperty.padding);
		pane.setHgap(BaseProperty.padding);
		return pane;
	}


	public static NumberExpression bindProperty(Property<Number> target,
												NumberExpression bindTo,
												Function<NumberExpression, NumberExpression> bindCalc,
												Consumer<NumberExpression> beforeBind){
		NumberExpression binding = bindCalc != null ? bindCalc.apply(bindTo) : bindTo;
		// 绑定前先解绑
		Actions.ifTrue(target.isBound(), target::unbind);
		Actions.ifTrue(beforeBind != null, () -> beforeBind.accept(binding));
		target.bind(binding);
		return binding;
	}

	public static void autoBoxSize(Region box, Region bindTo){
		autoBoxSize(box, bindTo, Function.identity(), Function.identity());
	}

	public static void autoBoxSize(Region box, Region bindTo,
								   Function<NumberExpression, NumberExpression> widthCalc,
								   Function<NumberExpression, NumberExpression> heightCalc){
		// 父容器变化时使适应宽高
		// 首次打开初始化，只能先初始化后绑定
		if(widthCalc != null){
			bindProperty(box.prefWidthProperty(), bindTo.widthProperty(), widthCalc, b -> box.setPrefWidth(b.getValue().doubleValue()));
		}
		if(heightCalc != null){
			bindProperty(box.prefHeightProperty(), bindTo.heightProperty(), heightCalc, b -> box.setPrefHeight(b.getValue().doubleValue()));
		}
//		// 父容器变化时使表格适应宽高, 传统的监听方式去绑定尺寸
//		ChangeListener<Number> widthChangeListener = (o, oldVal, newVal) -> box.setPrefWidth(box.getWidth() + newVal.doubleValue() - oldVal.doubleValue());
//		ChangeListener<Number> heightChangeListener = (o, oldVal, newVal) -> box.setPrefWidth(box.getHeight() + newVal.doubleValue() - oldVal.doubleValue());
//		((Region)box.getParent()).widthProperty().addListener(widthChangeListener);
//		((Region)box.getParent()).heightProperty().addListener(heightChangeListener);
	}


}
