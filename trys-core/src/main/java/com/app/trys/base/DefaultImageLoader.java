package com.app.trys.base;

import com.app.trys.base.bus.msg.Level;
import javafx.scene.image.Image;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 来源 https://www.iconarchive.com/show/web-icons-by-virtuallnk.html
 * @author linjf
 * @since 2023/4/25
 */
@Component
public class DefaultImageLoader implements ImageLoader{


	private final static Image ERROR = new Image("/icons/error-icon.png");
	private final static Image NORMAL = new Image("/icons/info-icon.png");
	private final static Image SUCCESS = new Image("/icons/success-icon.png");
	private final static Image WARN = new Image("/icons/warning-icon.png");

	private final Map<Object, Image> imageCacheMap = new HashMap<>();

	@Override
	public void loadImageResource(Object key, String filePath) {
		imageCacheMap.put(key, new Image(filePath));
	}

	@Override
	public Image getImage(Object key) {
		return imageCacheMap.get(key);
	}

	@Override
	public Image getLevelImage(Level level) {
		switch (level) {
			case NORMAL: return NORMAL;
			case SUCCESS: return SUCCESS;
			case WARN: return WARN;
			case ERROR: return ERROR;
		}
		return null;
	}
}
