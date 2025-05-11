package com.app.trys.view;

import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import lombok.Data;

/**
 * @author linjf
 * @since 2023/6/27
 */
@Data
//@Accessors(chain = true)
public class MainViewContext {
	/**
	 * 根容器
	 */
	private Node root;
	/**
	 * 菜单栏
	 */
	private MenuBar menuBar;
	/**
	 * 标签页
	 */
	private TabPane tabPane;
}
