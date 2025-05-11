package com.app.trys.components.facade.table;

import com.app.trys.base.bus.msg.AppMsgBus;
import com.app.trys.base.bus.msg.Level;
import com.app.trys.event.EventAdapter;
import com.app.trys.base.key.KeyComb;
import com.app.trys.utils.CollUtils;
import com.app.trys.utils.StringUtils;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author linjf
 * @since 2023/3/30
 */
public abstract class TrysAbstractTable<ROW> extends VBox implements EventAdapter {


	/**
	 * 行的类
	 */
	protected Class<ROW> rowClass;
	/**
	 * 数据列表
	 */
	protected ObservableList<ROW> itemList;
	/**
	 * 列属性
	 */
	protected ObservableList<TableColumn<ROW, ?>> colList;
	/**
	 * 表格组件
	 */
	protected TableView<ROW> tableView;
	/**
	 * 行双击事件
	 */
	protected final List<Consumer<ROW>> rowOnDoubleClickListenerList = new ArrayList<>();


	public TrysAbstractTable(Class<ROW> rowClass){
		this.rowClass = rowClass;
		this.tableView = new TableView<>();
		this.tableView.setRowFactory(new RowFactory());
		this.colList = tableView.getColumns();
		this.tableView.setOnKeyPressed(e -> {
			if(KeyComb.COPY.match(e)){
				// 多行复制
				ObservableList<ROW> rows = this.tableView.getSelectionModel().getSelectedItems();
				String rowsValue = CollUtils.toValueString(rows, StringUtils.WRAP);
				ClipboardContent content = new ClipboardContent();
				content.putString(rowsValue);
				Clipboard.getSystemClipboard().setContent(content);

				AppMsgBus.publish(Level.NORMAL,"已复制多行 {0}", rows);
			}
		});
	}


	protected abstract void buildColList();

	public void setItems(ObservableList<ROW> items) {
		this.itemList = items;
		this.tableView.setItems(items);
		this.tableView.refresh();
	}


	public void setWidth(double width){
		super.setWidth(width);
		this.tableView.setPrefWidth(width);
	}

	public void setHeight(double height){
		super.setHeight(height);
		this.tableView.setPrefHeight(height);
	}


	public void setSelectionMode(SelectionMode selectionMode){
		this.tableView.getSelectionModel().setSelectionMode(selectionMode);
	}

	/**
	 * 双击选中行
	 */
	public void addOnSelectedRowDoubleClick(Consumer<ROW> consumer){
		this.rowOnDoubleClickListenerList.add(consumer);
	}



	public class RowFactory implements Callback<TableView<ROW>, TableRow<ROW>> {
		@Override
		public TableRow<ROW> call(TableView<ROW> param) {
			TableRow<ROW> row = new TableRow<ROW>();
			row.setOnMouseClicked(e -> {
				// 左键双击回调
				if (e.getButton().name().equals(MouseButton.PRIMARY.name()) && e.getClickCount() == 2 && !row.isEmpty()) {
					ROW item = row.getItem();
					rowOnDoubleClickListenerList.forEach(l -> l.accept(item));
				}
			});
			return row;
		}
	}

}
