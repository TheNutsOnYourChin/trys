package com.app.trys.components.facade.table;

import com.app.trys.base.bus.msg.AppMsgBus;
import com.app.trys.base.bus.msg.Level;
import com.app.trys.base.form.properties.TableProperties;
import com.app.trys.base.table.ViewTable;
import com.app.trys.base.table.ViewTableCol;
import com.app.trys.components.facade.TrysPagination;
import com.app.trys.components.facade.controls.base.TrysButton;
import com.app.trys.config.BaseProperty;
import com.app.trys.constant.SystemConst;
import com.app.trys.generator.BtnWithPopupGenerator;
import com.app.trys.generator.DialogGenerator;
import com.app.trys.object.IdDTO;
import com.app.trys.object.TablePageDTO;
import com.app.trys.utils.Actions;
import com.app.trys.utils.ReflectUtils;
import javafx.collections.FXCollections;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;

/**
 * 数据表格
 * <p>
 *     - 设置列宽的时候，未设置的列将均分剩余宽度，达到占满容器的效果
 * </p>
 **/
public class TrysTableView<ROW extends IdDTO<?>> extends TrysAbstractTable<ROW> {

	private final TableProperties tableProperties;
	/**
	 * 分页及数据
	 */
	@Getter
	private TablePageDTO<ROW> tablePageDTO = new TablePageDTO<>();
	/**
	 * 分页组件
	 */
	private TrysPagination pagination;
//	/**
//	 * 修改成功回调
//	 */
//	private final List<Consumer<ROW>> onEditSuccessListener = new ArrayList<>();
//	private final List<Consumer<ROW>> onDelSuccessListener = new ArrayList<>();



	/**
	 * 查询模块的常规表格
	 * @param onTurnPage 翻页回调
	 */
	public static <ROW extends IdDTO<?>> TrysTableView<ROW> of(Class<ROW> rowClass, BiConsumer<Long, Long> onTurnPage) {
		return new TrysTableView<>(rowClass, onTurnPage);
	}


	private TrysTableView(Class<ROW> rowClass, BiConsumer<Long, Long> onTurnPage) {
		super(rowClass);
		this.tableProperties = new TableProperties(ReflectUtils.getAndCheckAnnotation(rowClass, ViewTable.class));
		this.tableView.setEditable(this.tableProperties.isEditable());
		this.getChildren().add(this.tableView);
		// 是否可分页
		if(tableProperties.isPageable()) {
			this.tablePageDTO.setSize(this.tableProperties.getPageSize());
			this.getChildren().add(this.pagination = TrysPagination.of(this.tablePageDTO, onTurnPage));
			this.pagination.setPadding(BaseProperty.PADDING_T);
		}
		buildColList();
		// 多选
		setSelectionMode(SelectionMode.MULTIPLE);

//		Platform.runLater(ComponentUtils.autoBoxSize(this));

	}


	@Override
	protected void buildColList() {
		// 序号列
		if(tableProperties.isWithIdxCol()) {
			TableColumn<ROW, Long> idx = new TableColumn<>("序号");
			idx.setCellFactory(col -> new PropertyIdxFactory());
			idx.setPrefWidth(60);
			idx.setSortable(false);
			colList.add(idx);
		}
		// 普通列
		normalCol();
		// 有操作事件，加入操作列
		if(tableProperties.isEditable()) {
			TableColumn<ROW, Object> operation = new TableColumn<>("操作");
			operation.setPrefWidth(150);
			operation.setCellFactory(col -> new TableOperationCell());
			colList.add(operation);
		}

	}

	private void normalCol() {
		ReflectUtils.getFields(rowClass).forEach(f -> {
			ViewTableCol colAnno = f.getAnnotation(ViewTableCol.class);
			if (colAnno != null) {
				TableColumn<ROW, Object> col = new TableColumn<>(colAnno.name());
				col.setCellFactory(c -> new TableNormalCell(f));
				col.setPrefWidth(colAnno.width());
				colList.add(col);
			}
		});
	}

	@SuppressWarnings(SystemConst.ALL)
	public void setData(TablePageDTO<?> pageResultDTO) {

		this.tablePageDTO = (TablePageDTO<ROW>)pageResultDTO;
		Actions.ifTrue(this.pagination != null, () -> this.pagination.setPageResultDTO(pageResultDTO));
		this.setItems(FXCollections.observableArrayList(tablePageDTO.getList()));
	}


	class PropertyIdxFactory extends TableCell<ROW, Long> {
		@Override
		protected void updateItem(Long item, boolean empty) {
			super.updateItem(item, empty);
			if(!empty || this.getTableRow() != null) {
				setText(null);
				setGraphic(null);
				if(this.getTableRow().getIndex() < tablePageDTO.getList().size()) {
					// 这里判断了行数小于数据长度才加序号， 否则会出现有序号但数据为空的行
					setText(String.valueOf(tablePageDTO.getRowNum(this.getTableRow().getIndex())));
				}
			}
		}
	}

	class TableNormalCell extends TableCell<ROW, Object> {

		/**
		 * 绑定字段
		 */
		private final Field bindField;

		public TableNormalCell(Field bindField) {
			this.bindField = bindField;
			setOnMouseClicked(e -> {
				// 左键双击复制
				if(e.getButton().name().equals(MouseButton.PRIMARY.name()) && e.getClickCount() == 2) {
					ClipboardContent content = new ClipboardContent();
					content.putString(getText());
					Clipboard.getSystemClipboard().setContent(content);
					AppMsgBus.publish(Level.NORMAL, "已复制 {0}" , getText());
				}
			});
		}


		@Override
		protected void updateItem(Object item, boolean empty) {
			super.updateItem(item, empty);
			// 如果此列为空默认不添加元素
			if (empty || this.getTableRow() == null || this.getTableRow().getItem() == null) {
				setText(null);
			} else {
				Object value = ReflectUtils.getFieldValueByGetter(bindField, this.getTableRow().getItem());
				setText(value != null ? value.toString() : null);
			}
			setGraphic(null);
		}
	}


	class TableOperationCell extends TableCell<ROW, Object> {
		@Override
		protected void updateItem(Object item, boolean empty) {
			super.updateItem(item, empty);
			HBox btnBox = new HBox();
			btnBox.setSpacing(BaseProperty.padding);
			if(tableProperties.getOnEditEvent() != null) {
				TrysButton editBtn = BtnWithPopupGenerator.getInstance().generateEditFormCallBtn(rowClass, this.getTableRow().getItem()).orElseThrow(() -> new IllegalArgumentException("无法为这一行创建编辑按钮"));
				btnBox.getChildren().addAll(editBtn);
			}
			if(tableProperties.getOnDelEvent() != null) {
				TrysButton delBtn = TrysButton.ofWaring("删除", e -> DialogGenerator.getInstance().delConfirm(this.getTableRow().getItem(), tableProperties.getOnDelEvent()));
				btnBox.getChildren().addAll(delBtn);
			}
			if (empty) {
				//如果此列为空默认不添加元素
				setText(null);
				setGraphic(null);
			} else {
				this.setGraphic(btnBox);
			}
		}
	}


}