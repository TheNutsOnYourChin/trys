package com.app.trys.base.enums;

import com.app.trys.base.dto.OptionsSupplier;
import com.app.trys.base.dto.TrysComboBoxItemKV;
import com.app.trys.exceptions.ExceptionUtils;
import com.app.trys.utils.CollUtils;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.*;
import java.util.function.Consumer;

/**
 * 下拉选项注册器
 * @author linjf
 * @since 2023/2/23
 */
@Component
public class SelectOptionsRegister {

	/**
	 * 下拉列表类型缓存
	 */
	private final Map<OptionsSupplier, List<TrysComboBoxItemKV>> selectOptionsMap = new HashMap<>();
	/**
	 * 监听下拉列表的数据刷新
	 */
	private final Map<OptionsSupplier, List<Consumer<List<TrysComboBoxItemKV>>>> onRefreshListenersMap = new HashMap<>();

	private final OptionsSupplier NULL = new OptionsSupplier(-1L, "空列表", Collections::emptyList);
	{
		registry(NULL);
	}



	public void registry(OptionsSupplier optionsSupplier){
		registry(optionsSupplier, false);
	}


	public void refresh(OptionsSupplier optionsSupplier){
		registry(optionsSupplier, true);
	}

	public void registry(OptionsSupplier optionsSupplier, boolean cover){
		List<TrysComboBoxItemKV> optionList = optionsSupplier.getOptionList();
		ExceptionUtils.isTure(cover || !selectOptionsMap.containsKey(optionsSupplier), "重复注册的下拉列表,itemData: {0}", optionsSupplier);
		ExceptionUtils.isTure(cover || selectOptionsMap.keySet().stream().noneMatch(key -> Objects.equals(key.getValue(), optionsSupplier.getValue())), "重复注册的下拉列表,itemData: {0}", optionsSupplier);
		selectOptionsMap.put(optionsSupplier, optionList);
		CollUtils.emptyIfNull(onRefreshListenersMap.get(optionsSupplier)).forEach(v -> v.accept(findList(optionsSupplier)));
	}


	public void addOnRefreshListener(OptionsSupplier optionsSupplier, Consumer<List<TrysComboBoxItemKV>> listener){
		this.onRefreshListenersMap.computeIfAbsent(optionsSupplier, k -> new ArrayList<>()).add(listener);
	}

	public  List<TrysComboBoxItemKV> findList(OptionsSupplier optionsSupplier){
		List<TrysComboBoxItemKV> itemList = selectOptionsMap.get(optionsSupplier);
		ExceptionUtils.nonNull(itemList,"未找到该类型的下拉列表,constCode: {0}", optionsSupplier.getId());
		return itemList;
	}

	public  List<TrysComboBoxItemKV> findListByName(String name){
		Optional<List<TrysComboBoxItemKV>> itemListOpt = selectOptionsMap.entrySet().stream().filter(e -> Objects.equals(e.getKey().getValue(), name)).findFirst().map(Map.Entry::getValue);
		List<TrysComboBoxItemKV> itemList = itemListOpt.orElse(null);
		ExceptionUtils.nonNull(itemList,"未找到该类型的下拉列表,name: {0}", name);
		return itemList;
	}


	/**
	 * 按id获取选项提供者
	 * @param name 下拉选项名称
	 * @return 选项提供者
	 */
	public OptionsSupplier findOptionsSupplierByName(String name) {
		return selectOptionsMap.keySet().stream().filter(kv -> Objects.equals(kv.getValue(), name)).findFirst()
				.orElseThrow(() -> new NoSuchElementException(MessageFormat.format("未找到该类型的下拉列表:{0}", name)));
	}
	/**
	 * 按id获取选项kv值
	 * @param option 选项类型
	 * @param id 选项id
	 * @return 选项键值对
	 */
	public TrysComboBoxItemKV findKV(OptionsSupplier option, Long id) {
		return findList(option).stream().filter(kv -> Objects.equals(kv.getId(), id)).findAny().orElse(null);
	}
	/**
	 * 按id获取选项kv值
	 * @param option 选项类型
	 * @param id 选项id
	 * @return 选项键值对
	 */
	public String findName(OptionsSupplier option, Long id) {
		return Optional.ofNullable(findKV(option, id)).map(TrysComboBoxItemKV::getValue).orElse(null);
	}

}
