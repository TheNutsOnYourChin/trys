package com.app.trys.components.facade;

import com.app.trys.base.form.SearchModule;
import com.app.trys.base.form.properties.SearchModuleProperties;
import com.app.trys.components.facade.form.TrysSearchForm;
import com.app.trys.components.facade.table.TrysTableView;
import com.app.trys.event.component.OnDelSuccessEvent;
import com.app.trys.event.component.OnSubmitSuccessEvent;
import com.app.trys.exceptions.ExceptionUtils;
import com.app.trys.object.BasePage;
import com.app.trys.object.IdDTO;
import com.app.trys.object.Result;
import com.app.trys.object.TablePageDTO;
import com.app.trys.utils.ReflectUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 一个完整的查询表格页面
 *
 * @author linjf
 * @since 2023/2/24
 */
@Slf4j
public class TrysSearchTableModule<QUERY extends BasePage> extends TrysSearchForm<QUERY> {

	protected SearchModuleProperties searchModuleProperties;

	/**
	 * 数据表格
	 */
	private final TrysTableView<? extends IdDTO<?>> tableView;

	public static <S extends BasePage> TrysSearchTableModule<S> of(Class<S> searchClass) {
		return new TrysSearchTableModule<>(searchClass);
	}

	private TrysSearchTableModule(Class<QUERY> searchClass) {
		super(searchClass);

		SearchModule searchModule = ReflectUtils.getAndCheckAnnotation(searchClass, SearchModule.class);
		this.searchModuleProperties = new SearchModuleProperties(super.getFormProperties(), searchModule);

		// 数据表格, 翻页时同步分页参数，做查询
		Class<? extends IdDTO<?>> resultClass = this.searchModuleProperties.getResultClass();
		this.tableView = TrysTableView.of(resultClass, (size, toPage) -> {
			this.getDto().setSize(size);
			this.getDto().setCurrent(toPage);
			this.submit();
		});
		this.getChildren().addAll(tableView);

		// 编辑成功后触发
		register(resultClass, OnSubmitSuccessEvent.class, e -> this.submit());
		register(resultClass, OnDelSuccessEvent.class, e -> this.submit());

//		Platform.runLater(ComponentUtils.autoBoxSize(this));

		// 初始化查询
		this.submit();
	}


	@Override
	public void beforeSubmit(QUERY query) {

	}

	@Override
	public void afterSubmit(QUERY query, Result result) {
		ExceptionUtils.isTure(result.getData() instanceof TablePageDTO, "返回类型必须是{0}", TablePageDTO.class.getSimpleName());
		this.tableView.setData((TablePageDTO<?>) result.getData());
	}


}
