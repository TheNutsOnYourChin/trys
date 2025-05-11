package com.app.trys.base;

import com.app.trys.base.bus.msg.Level;
import com.app.trys.facade.Loader;
import javafx.scene.image.Image;

/**
 * 图片资源载入
 * @author linjf
 * @since 2023/3/22
 */
public interface ImageLoader extends Loader {


	/**
	 * 加载图片资源
	 * @param key 唯一键
	 * @param path 路径
	 */
	void loadImageResource(Object key, String path);

	/**
	 * 获取一张图片
	 */
	Image getImage(Object key);
	/**
	 * 获取一张警告图片
	 */
	Image getLevelImage(Level level);
}
