package com.app.trys.view;

import com.app.trys.account.dto.query.TrysAccountQueryDTO;
import com.app.trys.base.bus.msg.Level;
import com.app.trys.components.TrysMenuBar;
import com.app.trys.components.TrysMsgBar;
import com.app.trys.components.facade.TrysSearchTableModule;
import com.app.trys.config.BaseProperty;
import com.app.trys.manager.WindowManager;
import com.app.trys.object.BasePage;
import com.app.trys.util.ComponentUtils;
import com.app.trys.utils.Actions;
import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.function.Function;

@FXMLView
public class MainView extends AbstractFxmlView {


	@Autowired
	private WindowManager windowManager;

	@Override
	public Parent getView() {
		MainViewContext context = new MainViewContext();

		VBox root = new VBox();
		root.setPadding(BaseProperty.PADDING);
		context.setRoot(root);
		{
			// 标签页
			TabPane tabPane = new TabPane();
			context.setTabPane(tabPane);
//			tabPane.setStyle("-fx-background-color: #567");

			// 菜单栏
			MenuBar menuBar = buildMenuBar(context);
			context.setMenuBar(menuBar);
			// 接收所有等级的信息
			root.getChildren().addAll(menuBar, tabPane, TrysMsgBar.of(Level.values()));
		}

		// 模拟点击菜单打开标签页
		context.getMenuBar().getMenus().get(1).getItems().get(0).getOnAction().handle(null);
		return root;
	}


	@SafeVarargs
    private final void addSearchModuleTab(MainViewContext context, String name, Class<? extends BasePage>... moduleTypes) {
		Tab tab = new Tab(name);
		{
			int length = moduleTypes.length;
			HBox hBox = new HBox();
			hBox.setSpacing(BaseProperty.padding);
			hBox.setPadding(BaseProperty.PADDING_T_B);
			for (Class<? extends BasePage> moduleType : moduleTypes) {
				TrysSearchTableModule<?> searchModule = TrysSearchTableModule.of(moduleType);
				hBox.getChildren().add(searchModule);
				// 在窗口自然打开后，再绑定尺寸
				Platform.runLater(() -> ComponentUtils.autoBoxSize(searchModule, hBox, w -> w.divide(length), Function.identity()));
			}
			tab.setContent(hBox);
		}
		openTab(context.getTabPane(), tab);
	}

	private void openTab(TabPane tabPane, Tab tab){
		ObservableList<Tab> tabs = tabPane.getTabs();

		Actions.ifTrue(tabs.stream().noneMatch(t -> Objects.equals(t.getText(), tab.getText())), () -> tabs.add(tab));
		// 打开时选中
		tabPane.getSelectionModel().select(tab);
		// 关闭时卸载tab
		tab.setOnClosed(v -> tabs.remove(tab));
	}


	private MenuBar buildMenuBar(MainViewContext context){
		MenuBar menuBar = TrysMenuBar.ofDefault();
		{
			Menu menu = new Menu("菜单");
			{
				MenuItem newWin = new MenuItem("新窗口");
				newWin.setOnAction(e -> {
					Window sourceWin = context.getRoot().getScene().getWindow();
					windowManager.copy(sourceWin, this.getClass());
				});

				MenuItem exit = new MenuItem("退出");
				exit.setOnAction(e -> windowManager.exit());

				menu.getItems().addAll(newWin, new SeparatorMenuItem(), exit);
			}
			menuBar.getMenus().add(menu);

			Menu open = new Menu("打开");
			{
				String accountTabName = "账户";
				MenuItem accountItem = new MenuItem(accountTabName);
				accountItem.setOnAction(e -> addSearchModuleTab(context, accountTabName, TrysAccountQueryDTO.class));
				open.getItems().addAll(accountItem);
			}
			menuBar.getMenus().add(open);
		}
		return menuBar;
	}

}