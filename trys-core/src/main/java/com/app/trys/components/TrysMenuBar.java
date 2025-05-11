package com.app.trys.components;

import com.app.trys.base.css.TrysStyleClass;
import javafx.scene.control.MenuBar;

/**
 * 菜单栏
 * @author linjf
 * @since 2023/4/28
 */
public class TrysMenuBar extends MenuBar {


	public static TrysMenuBar ofDefault(){
		TrysMenuBar menuBar = new TrysMenuBar();
		menuBar.getStyleClass().add(TrysStyleClass.pane_border_b);
		return menuBar;
	}
}
