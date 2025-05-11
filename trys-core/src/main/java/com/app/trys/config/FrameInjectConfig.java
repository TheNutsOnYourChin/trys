package com.app.trys.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author linjf
 * @since 2023/2/15
 */
@Getter
@Configuration
public class FrameInjectConfig {


	/**
	 * 主窗口标题
	 */
	@Value("${mainFrame.title:主窗口}")
	public String mainFrameTitle;
	/**
	 * 主窗口x坐标
	 */
	@Value("${mainFrame.x:300}")
	public Integer mainFrameX;
	/**
	 * 主窗y坐标
	 */
	@Value("${mainFrame.y:150}")
	public Integer mainFrameY;
	/**
	 * 主窗口宽度
	 */
	@Value("${mainFrame.width:1000}")
	public Integer mainFrameWidth;
	/**
	 * 主窗口高度
	 */
	@Value("${mainFrame.height:800}")
	public Integer mainFrameHeight;
}
