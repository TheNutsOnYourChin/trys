package com.app.trys.util;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;

/**
 * @author linjf
 * @since 2025-03-28
 */
public class TooltipUtils {

    public static Tooltip installTooltip(Node node, String text) {
        Tooltip t = new Tooltip(text);
        Tooltip.install(node, t);
        return t;
    }
}
