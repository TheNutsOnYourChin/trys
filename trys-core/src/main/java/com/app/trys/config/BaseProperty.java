package com.app.trys.config;

import javafx.geometry.Insets;


/**
 * @author linjf
 * @since 2023/2/15
 */
public class BaseProperty {


	public static void putInjectConfig(FrameInjectConfig config) {
		mainFrameTitle = config.getMainFrameTitle();
		mainFrameTitle = config.getMainFrameTitle();
		mainFrameX = config.getMainFrameX();
		mainFrameY = config.getMainFrameY();
		mainFrameWidth = config.getMainFrameWidth();
		mainFrameHeight = config.getMainFrameHeight();
	}
	/**
	 * 主窗口标题
	 **/
	public static String mainFrameTitle;
	/**
	 * 主窗口x坐标位置
	 **/
	public static int mainFrameX;
	/**
	 * 主窗口y坐标位置
	 **/
	public static int mainFrameY;
	/**
	 * 主窗口宽度
	 **/
	public static int mainFrameWidth;
	/**
	 * 主窗口高度
	 **/
	public static int mainFrameHeight;


	/**
	 * 边距
	 **/
	public static int marginPx = 2;
	/**
	 * 组件内边距，通常设置padding的时候组件的尺寸会变大
	 **/
	public static final int padding = 5;
	/**
	 * 表单控件行高
	 **/
	public static final int controlLineH = 27;
	public static final int controlLineW = 240;
	/**
	 * 表单控件的标签宽度
	 **/
	public static final int controlLabelW = 60;
	/**
	 * 滚动条窗口窗口最小高度, 即使设置为0也会变成37，原因未知
	 **/
	public static final int scrollPaneMinHeight = 37;


	public static final Insets PADDING = new Insets(BaseProperty.padding);
	public static final Insets PADDING_T = new Insets(BaseProperty.padding, 0, 0, 0);
	public static final Insets PADDING_T_B = new Insets(BaseProperty.padding, 0, BaseProperty.padding, 0);
	public static final Insets PADDING_L_R = new Insets(0, BaseProperty.padding, 0, BaseProperty.padding);

}
