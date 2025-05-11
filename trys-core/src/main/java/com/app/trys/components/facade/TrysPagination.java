package com.app.trys.components.facade;

import com.app.trys.components.facade.controls.TrysControlWithLabel;
import com.app.trys.components.facade.controls.base.TrysButton;
import com.app.trys.components.facade.controls.base.TrysNumberInput;

import com.app.trys.config.BaseProperty;
import com.app.trys.object.TablePageDTO;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * 页码选择器
 * @author linjf
 * @since 2023/2/24
 */
public class TrysPagination extends FlowPane {

	// 分页信息
	private final TablePageDTO<?> pageResultDTO = new TablePageDTO<>();

	private final TrysControlWithLabel jump;

	public static TrysPagination of(TablePageDTO<?> pageInitDTO, BiConsumer<Long, Long> onTurnPage){
		return new TrysPagination(pageInitDTO, onTurnPage);
	}

	/**
	 * 分页组件
	 * @param pageInitDTO 初始的分页结果DTO，发生在打开页面的时候
	 * @param onTurnPage 翻页回调 BiConsumer(每页大小，第几页）
	 */
	private TrysPagination(TablePageDTO<?> pageInitDTO, BiConsumer<Long, Long> onTurnPage){
		Objects.requireNonNull(onTurnPage);

		TrysNumberInput pageNumberInput = TrysNumberInput.of(Long.class, toPageNum -> {
			Long to = (Long) toPageNum;
			if (pageResultDTO.allowJumpPage(to)) {
				onTurnPage.accept(pageResultDTO.getSize(), to);
				pageResultDTO.setCurrent(to);
			}
		});
		pageNumberInput.setPromptText("跳转");
		this.jump = TrysControlWithLabel.ofSimple(pageMsg(), pageNumberInput);

		TrysButton prev = TrysButton.ofNormal("上一页", e -> {
			if (pageResultDTO.allowPrevPage()) {
				onTurnPage.accept(pageResultDTO.getSize(), pageResultDTO.getCurrent() - 1);
			}
		});
		TrysButton next = TrysButton.ofNormal("下一页", e -> {
			if(pageResultDTO.allowNextPage()){
				onTurnPage.accept(pageResultDTO.getSize(), pageResultDTO.getCurrent() + 1);
			}
		});
		setAlignment(Pos.CENTER);
		setHgap(BaseProperty.padding);
		setVgap(BaseProperty.padding);
		getChildren().addAll(jump, prev, next);
		setPageResultDTO(pageInitDTO);
	}

	/** 设置分页信息 **/
	public void setPageResultDTO(TablePageDTO<?> tablePageDTO) {
		if(tablePageDTO != null) {
			this.pageResultDTO.setCurrent(tablePageDTO.getCurrent());
			this.pageResultDTO.setSize(tablePageDTO.getSize());
			this.pageResultDTO.setTotalPage(tablePageDTO.getTotalPage());
			this.pageResultDTO.setTotal(tablePageDTO.getTotal());
		}
		this.jump.setLabelText(pageMsg());
	}


	public String pageMsg(){
		return "第" + pageResultDTO.getCurrent() + "/" + pageResultDTO.getTotalPage() + "页";
	}
}
