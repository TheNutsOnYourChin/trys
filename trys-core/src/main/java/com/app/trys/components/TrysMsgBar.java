package com.app.trys.components;

import com.app.trys.base.ImageLoader;
import com.app.trys.base.bus.msg.AppMsgBus;
import com.app.trys.base.bus.msg.Level;
import com.app.trys.components.facade.controls.base.TrysLabel;
import com.app.trys.config.BaseProperty;
import com.app.trys.util.TooltipUtils;
import com.app.trys.utils.CollUtils;
import com.app.trys.utils.DateUtils;
import com.app.trys.utils.SpringContextUtils;
import com.app.trys.utils.StringUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.List;

/**
 * 信息栏
 * 显示信息等，按通知设置体表和信息
 * @author linjf
 * @since 2023/5/15
 */
public class TrysMsgBar extends HBox {

	private TrysMsgBar(List<Level> receiveLvList,  ImageLoader imageLoader){
		setSpacing(BaseProperty.padding);
		setAlignment(Pos.CENTER_LEFT);
		String current = DateUtils.current();
		TrysLabel msgText = TrysLabel.of(current);
		Tooltip msgTooltip = TooltipUtils.installTooltip(msgText, current);
		ImageView msgIcon = new ImageView(imageLoader.getLevelImage(Level.NORMAL));
		getChildren().addAll(msgIcon, msgText);
		receiveLvList.forEach(receiveLv -> AppMsgBus.addListener(receiveLv, (lv, event) -> {
            msgIcon.setImage(imageLoader.getLevelImage(lv));
            msgTooltip.setText(StringUtils.wrap(event.getMsg(), 200));
            msgText.setText(event.getMsg());
        }));

	}

	public static TrysMsgBar of(Level... receiveLv){
		return new TrysMsgBar(CollUtils.toList(receiveLv), SpringContextUtils.getBean(ImageLoader.class));
	}
}
