package com.app.trys.base.css;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author linjf
 * @since 2023/4/24
 */
public class TrysStyleClass {

	public static final List<String> cssList = new ArrayList<>();

	static {
//		cssList.add(TrysStyleClass.class.getClassLoader().getResource("css/pane.css").toExternalForm());
		cssList.add(Objects.requireNonNull(TrysStyleClass.class.getResource("/css/pane.css")).toExternalForm());
		cssList.add(Objects.requireNonNull(TrysStyleClass.class.getResource("/css/control.css")).toExternalForm());
	}

	/**
	 * 白色字体
	 */
	public static final String white_text = "-fx-text-fill: #FFF;";
	public static final String red_backgoup = "-fx-text-fill: #FFF;";
	/**
	 * 容器分割线 - 底部
	 */
	public static final String pane_border_b = "pane-border-b";
	/**
	 * 红色按钮
	 */
	public static final String danger_btn = "danger-btn";
}
