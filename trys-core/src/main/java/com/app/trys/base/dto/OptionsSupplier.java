package com.app.trys.base.dto;


import java.util.List;
import java.util.function.Supplier;

/**
 * 下拉列表提供者
 * @author linjf
 * @since 2023/6/27
 */
public class OptionsSupplier extends EnumKV{

	/**
	 * 下拉列表生成器
	 */
	private final Supplier<List<TrysComboBoxItemKV>> optionsSupplier;

	public OptionsSupplier(Long id, String value,Supplier<List<TrysComboBoxItemKV>> optionsSupplier) {
		super(id, value);
		this.optionsSupplier = optionsSupplier;
	}

	public List<TrysComboBoxItemKV> getOptionList(){
		return optionsSupplier.get();
	}
}
