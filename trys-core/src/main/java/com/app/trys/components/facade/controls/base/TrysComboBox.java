package com.app.trys.components.facade.controls.base;

import com.app.trys.base.dto.OptionsSupplier;
import com.app.trys.base.dto.TrysComboBoxItemKV;
import com.app.trys.base.enums.SelectOptionsRegister;
import com.app.trys.components.facade.controls.ControlAdapter;
import com.app.trys.components.facade.controls.factory.ButtonSetFactory;
import com.app.trys.components.facade.table.TrysFilterView;
import com.app.trys.config.BaseProperty;
import com.app.trys.exceptions.ExceptionUtils;
import com.app.trys.generator.SceneGenerator;
import com.app.trys.manager.WindowManager;
import com.app.trys.utils.Actions;
import com.app.trys.utils.CollUtils;
import com.app.trys.utils.ReflectUtils;
import com.app.trys.utils.SpringContextUtils;
import com.app.trys.utils.optional.ObjectOptional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 下拉单选控制器
 *
 * @author linjf
 * @since 2023/2/22
 */
public class TrysComboBox extends ComboBox<String> implements ControlAdapter {

	/**
	 * 名称
	 */
	@Setter
	private String name;
	/**
	 * 原生数据
	 */
	private final List<TrysComboBoxItemKV> itemList = new ArrayList<>();
	/**
	 * 转成值列表, 基本上只是给普通的下拉使用
	 */
	private final ObservableList<String> valueItemList;
	/**
	 * 单选多选
	 */
	private final SelectionMode selectionMode;
	/**
	 * 选项表格
	 */
	private TrysFilterView itemListView;
	/**
	 * 选中的数据
	 */
	private final ObjectOptional<List<Long>> selected = ObjectOptional.ofNull();
	/**
	 * 选中时回调
	 */
	private final List<Consumer<Object>> onSelectListenerList = new ArrayList<>();

	/**
	 * 默认值
	 */
	@Getter
	private List<Long> defValue;

	private TrysComboBox(OptionsSupplier optionsSupplier, SelectionMode selectionMode) {
		this.selectionMode = selectionMode;

		SelectOptionsRegister selectOptionsRegister = SpringContextUtils.getBean(SelectOptionsRegister.class);
		List<TrysComboBoxItemKV> valList = selectOptionsRegister.findList(optionsSupplier);

		// 加入一个空选项
		if(CollUtils.isNotEmpty(valList) && !valList.get(0).equals(TrysComboBoxItemKV.NULL)){
			valList.add(0, TrysComboBoxItemKV.NULL);
		}

		this.itemList.addAll(valList);
		List<String> itemValueList = this.itemList.stream().map(TrysComboBoxItemKV::getValue).collect(Collectors.toList());
		this.valueItemList = FXCollections.observableArrayList(itemValueList);

		initCheck();

		// 大于20个选项则开启筛选窗口
		boolean filterWindow = valList.size() > 20 || SelectionMode.MULTIPLE.equals(selectionMode);
		Actions.ifElse(filterWindow, this::filterWindow, this::itemFilter);

		// 监听下拉列表数据发生改变
		selectOptionsRegister.addOnRefreshListener(optionsSupplier, lastList -> {
			if(filterWindow){
				this.itemList.clear();
				this.itemList.addAll(lastList);
				this.itemListView.refreshItemList(lastList);
			} else {
				List<String> lastValueList = lastList.stream().map(TrysComboBoxItemKV::getValue).collect(Collectors.toList());
				this.valueItemList.clear();
				this.valueItemList.addAll(lastValueList);
			}
			reset();
		});
	}

	private void filterWindow() {
		double width = 300;
		double height = 500;
		boolean isMulti = SelectionMode.MULTIPLE.equals(this.selectionMode);

		VBox vBox = new VBox();
		vBox.setPrefHeight(height);
		vBox.setPrefWidth(width);
		vBox.setSpacing(BaseProperty.padding);
		vBox.setPadding(BaseProperty.PADDING);
		Scene scene = SceneGenerator.getInstance().generateScene(vBox);
		// 创建筛选表格
		itemListView = isMulti ? TrysFilterView.ofMulti(this.itemList) : TrysFilterView.ofSingle(this.itemList);
		itemListView.setPrefHeight(vBox.getPrefHeight());

		// 触发隐藏窗口，取回窗口中选中的值，并显示成文本
		Runnable onHide = () -> {
			WindowManager.getInstance().hideComboBoxWindow();
			if(Objects.equals(WindowManager.getInstance().getComboBoxWindow().getScene(), scene)) {
				List<Long> value = itemListView.getValue().stream().map(v -> (Long)v).collect(Collectors.toList());
				this.setCurrentValue(value);
			}
		};

		// 行双击选中，单选时会关闭窗口
		itemListView.addOnSelectedRowDoubleClick(row -> {
			onSelectListenerList.forEach(l -> l.accept(row.getId()));
			Actions.ifTrue(!isMulti, onHide);
		});
		// 点击确定时关闭
		FlowPane confirmBtnBox = new ButtonSetFactory().buttonSet(TrysButton.ofDefault("确定", e -> onHide.run()));
		vBox.getChildren().addAll(itemListView, confirmBtnBox);
		// 失去聚焦时关闭窗口
		WindowManager.getInstance().getComboBoxWindow()
				.focusedProperty().addListener((o, oldValue, newValue) -> Actions.ifTrue (!newValue, onHide));
		// 激活筛选表时，设置表值
		addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			WindowManager.getInstance().showComboBoxWindow(scene, name);
			itemListView.setValue(getCurrentValue());
		});

	}

	private void itemFilter() {
		setItems(valueItemList);
//		// 实现输入框过滤
//		setEditable(true);
//		List<KeyCode> noMatch = CollUtils.toList(KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT);
//		TextField editor = getEditor();
//		editor.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
//			if (noMatch.contains(e.getCode())) {
//				return;
//			}
//			String text = editor.getText();
//			if (StringUtils.isEmpty(text)) {
//				setItems(allItemList);
//			} else {
//				filteredItemList.setAll(TrysComboBoxItemKV.NULL.getValue());
//				filteredItemList.addAll(allItemList.stream().filter(v -> v.contains(text)).collect(Collectors.toList()));
//				setItems(filteredItemList);
//			}
//			System.out.println(getSelectionModel().getSelectedItem());
//			hide();
//			show();
//			System.out.println("按下：" + text);
//		});

		getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
			// 选中时回调
			this.selected.set(Collections.singletonList(findByValue(newValue).getId()));
			valueItemList.stream().filter(v -> v.equals(newValue)).findAny().ifPresent(v -> onSelectListenerList.forEach(l -> l.accept(findByValue(newValue).getId())));
		});
//		setPlaceholder(TrysButton.ofNormal("Nothing.", e -> {}));
	}

	public static TrysComboBox of(OptionsSupplier optionsSupplier, SelectionMode selectionMode) {
		return new TrysComboBox(optionsSupplier, selectionMode);
	}


	public void addOnSelectListener(Consumer<Object> onSelect){
		this.onSelectListenerList.add(onSelect);
	}


	private void initCheck() {
		if (CollUtils.isNotEmpty(itemList) && !Objects.equals(itemList.stream().map(TrysComboBoxItemKV::getValue).distinct().count(), (long)itemList.size())) {
			itemList.forEach(kv1 -> itemList.forEach(kv2 -> ExceptionUtils.isTure(kv1 == kv2 || !Objects.equals(kv1.getValue(), kv2.getValue()), "下拉列表出现重复的项: {0}", kv1.getValue())));
		}
	}

	private TrysComboBoxItemKV findByValue(String value) {
		return this.itemList.stream().filter(kv -> Objects.equals(kv.getValue(), value)).findFirst().orElse(TrysComboBoxItemKV.NULL);
	}


	@Override
	public Object getCurrentValue() {
		return selectionMode == SelectionMode.SINGLE ? CollUtils.get(this.selected.get(), 0) : this.selected.get();
	}


	@Override
	public void setCurrentValue(Object value) {
		List<Long> finalValue = parseValue(value);
		this.selected.set(finalValue);
		String text = this.itemList.stream().filter(item -> finalValue.contains(item.getId())).map(TrysComboBoxItemKV::getValue).collect(Collectors.joining(","));
		getSelectionModel().select(text);
	}


	/**
	 * 默认值
	 * @param defValue 默认值 long、long[]、Long[]、List<Long>
	 */
	@Override
	public void setDefValue(Object defValue) {
		this.defValue = parseValue(defValue);
	}

	private List<Long> parseValue(Object o) {
		if(o == null){
			return Collections.emptyList();
		}
		List<Long> value;
		if(ReflectUtils.isSameClass(o.getClass(), Long.class)){
			value = CollUtils.toList((Long)o);
		} else if(ReflectUtils.extendsFrom(o.getClass(), Collection.class)){
			value = CollUtils.checkItem((Collection<?>) o, Long.class);
		} else if (o.getClass().isArray()){
			value = Arrays.stream((long[]) o).boxed().collect(Collectors.toList());
		} else {
			throw new IllegalArgumentException("多选ComboBox设置默认值必须是long、Long、long[] 或 List<long>");
		}
		if(selectionMode == SelectionMode.SINGLE){
			value = CollUtils.get(value, 0) == null ? Collections.emptyList() : CollUtils.toList(CollUtils.get(value, 0));
		}
		return value;
	}


}
