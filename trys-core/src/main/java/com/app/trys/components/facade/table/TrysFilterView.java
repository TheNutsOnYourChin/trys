package com.app.trys.components.facade.table;

import com.app.trys.base.dto.TrysComboBoxItemKV;
import com.app.trys.base.form.CtrlInput;
import com.app.trys.base.form.properties.CtrlInputProperties;
import com.app.trys.base.table.ViewTable;
import com.app.trys.base.table.ViewTableCol;
import com.app.trys.components.facade.controls.TrysControlWithLabel;
import com.app.trys.components.facade.controls.base.TrysButton;
import com.app.trys.components.facade.controls.base.TrysInput;
import com.app.trys.config.BaseProperty;
import com.app.trys.exceptions.ExceptionUtils;
import com.app.trys.util.ComponentUtils;
import com.app.trys.utils.ReflectUtils;
import com.app.trys.utils.StringUtils;
import com.app.trys.utils.optional.ObjectOptional;
import javafx.application.Platform;
import javafx.beans.binding.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import lombok.Setter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static javafx.scene.control.SelectionMode.MULTIPLE;
import static javafx.scene.control.SelectionMode.SINGLE;

/**
 * 数据表格
 * <p>
 *     - 设置列宽的时候，未设置的列将均分剩余宽度，达到占满容器的效果
 * </p>
 **/
@Setter
public class TrysFilterView extends TrysAbstractTable<TrysComboBoxItemKV> {

	/**
	 * 所有选项都创建为按钮，表示为选中
	 */
	private final Map<Object, Node> selectedMap = new HashMap<>();

	/**
	 * 展示选中列表
	 */
	private final FlowPane selectedPane;

	/**
	 * 过滤用的表格
	 */
	public static TrysFilterView ofMulti(List<TrysComboBoxItemKV> itemList) {
		return new TrysFilterView(itemList, MULTIPLE);
	}

	public static TrysFilterView ofSingle(List<TrysComboBoxItemKV> itemList) {
		return new TrysFilterView(itemList, SINGLE);
	}


	private TrysFilterView(List<TrysComboBoxItemKV> itemList, SelectionMode selectionMode) {
		super(TrysComboBoxItemKV.class);
		setSpacing(BaseProperty.padding);
		ReflectUtils.getAndCheckAnnotation(rowClass, ViewTable.class);
		// 剔除空选项
		itemList = itemList.stream().filter(kv -> !Objects.equals(kv, TrysComboBoxItemKV.NULL)).collect(Collectors.toList());
		this.itemList = FXCollections.observableArrayList(itemList);

		List<CtrlInput> inputList = ReflectUtils.getFieldAnnotations(this.rowClass, CtrlInput.class);
		ExceptionUtils.nonEmpty(inputList, "需要且只要一个过滤条件");

		CtrlInputProperties inputProperties = new CtrlInputProperties(inputList.get(0));
		TrysControlWithLabel filterInput = TrysControlWithLabel.of(inputProperties, TrysInput.of());
		// 输入时过滤
		setItems(this.itemList);
		filterInput.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
			String text = String.valueOf(filterInput.getCurrentValue());
			if(StringUtils.isEmpty(text))
				setItems(this.itemList);
			else
				setItems(FXCollections.observableArrayList(this.itemList.stream().filter(kv -> kv.getValue().contains(text)).collect(Collectors.toList())));
		});

		this.selectedPane = ComponentUtils.createFlowPane();

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollPane.setContent(selectedPane);


		// 选项转成按钮样式 展示选中项, 点击时移除
		refreshSelectedMap();

		// 单选时每次选中都替换，多选时选中列表可滚动，适配窗口高度
		super.setSelectionMode(selectionMode);
		switch (selectionMode){
			case SINGLE:
				getChildren().addAll(filterInput, this.tableView, scrollPane);
				addOnSelectedRowDoubleClick(row -> {
					selectedPane.getChildren().clear();
					ComponentUtils.addNode(selectedPane, this.selectedMap.get(row.getId()));
				});
				break;
			case MULTIPLE:
				// ㈠ tableView需要被VBox限制，否者会完全填充高度且不能重置高度，因为本身也在Vbox中，this.class继承了VBox
				VBox tv = new VBox(this.tableView);
				// 监听选中个数导致的高度变化
				scrollPane.setPrefHeight(BaseProperty.scrollPaneMinHeight);
				Platform.runLater(() -> {
					tv.setPrefHeight(this.getScene().getHeight() - scrollPane.getPrefHeight());
					int maxScrollPaneHeight = 100;
					// 滚动条高度跟随最外层
					ComponentUtils.autoBoxSize(scrollPane, this, Function.identity(), null);
					ComponentUtils.autoBoxSize(selectedPane, scrollPane, w -> w.subtract(10), null);
					ComponentUtils.autoBoxSize(scrollPane, selectedPane, null, h -> {
						int add = 33;
						// 不知为何滚动条高度会比里面少一些
						return new When(h.greaterThan(maxScrollPaneHeight))
								.then(maxScrollPaneHeight + add)
								.otherwise(h.add(add));
					});
					ComponentUtils.autoBoxSize(tv, selectedPane, null, h -> {
						// 外层高度减去滚动条高度
						return new When(h.greaterThan(maxScrollPaneHeight))
								.then(this.heightProperty().subtract(maxScrollPaneHeight))
								.otherwise(this.heightProperty().subtract(h));
					});
				});
				addOnSelectedRowDoubleClick(row -> ComponentUtils.addNode(selectedPane, this.selectedMap.get(row.getId())));
				getChildren().addAll(filterInput, tv, scrollPane);

				break;
		}
		buildColList();
	}


	/**
	 * 刷新可选按钮列表
	 */
	private void refreshSelectedMap() {
		this.selectedMap.clear();
		this.itemList.forEach(item -> {
			ObjectOptional<Node> btOpt = ObjectOptional.ofNull();
			btOpt.set(TrysButton.ofLabel("× " + item.getValue(), item.getId(), e -> selectedPane.getChildren().remove(btOpt.get())));
			this.selectedMap.put(item.getId(), btOpt.get());
		});
	}

	/**
	 * 刷新整个可选数居时使用
	 * @param items 新的可选数据
	 */
	public void refreshItemList(List<TrysComboBoxItemKV> items){
		itemList.clear();
		itemList.addAll(items.stream().filter(kv -> !Objects.equals(kv, TrysComboBoxItemKV.NULL)).collect(Collectors.toList()));
		setItems(itemList);
		refreshSelectedMap();
	}

	@Override
	protected void buildColList(){
		// 普通列
		ReflectUtils.getFields(rowClass).forEach(f -> {
			ViewTableCol colAnno = f.getAnnotation(ViewTableCol.class);
			if (colAnno != null) {
				TableColumn<TrysComboBoxItemKV, String> col = new TableColumn<>(colAnno.name());
				col.setCellFactory(c -> new TableKVCell());
				col.setPrefWidth(colAnno.width());
				colList.add(col);
			}
		});
	}


	@Override
	public void setItems(ObservableList<TrysComboBoxItemKV> items) {
		this.tableView.setItems(items);
		this.tableView.refresh();
	}


	@Override
	public void setSelectionMode(SelectionMode selectionMode){
		throw new UnsupportedOperationException();
	}

	public List<Object> getValue(){
        return selectedPane.getChildren().stream().map(child -> ((TrysButton) child).getValue()).collect(Collectors.toList());
	}

	public void setValue(Object value){
		selectedPane.getChildren().clear();
		if(value != null){
			if(value instanceof Collection){
				Collection<?> values = (Collection<?>) value;
				values.forEach(k -> ComponentUtils.addNode(selectedPane, this.selectedMap.get(k)));
			}else {
				ComponentUtils.addNode(selectedPane, this.selectedMap.get(value));
			}
		}
	}


	static class TableKVCell extends TableCell<TrysComboBoxItemKV, String> {

		@Override
		protected void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if (empty || this.getTableRow() == null || this.getTableRow().getItem() == null) {
				setText(null);
			} else {
				TrysComboBoxItemKV kv = this.getTableRow().getItem();
				setText(kv != null ? kv.getValue() : null);
			}
			setGraphic(null);
		}
	}
}