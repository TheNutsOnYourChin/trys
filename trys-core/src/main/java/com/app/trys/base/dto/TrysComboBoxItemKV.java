package com.app.trys.base.dto;

import com.app.trys.base.form.CtrlInput;
import com.app.trys.base.table.ViewTable;
import com.app.trys.base.table.ViewTableCol;
import com.app.trys.facade.BaseEnum;
import com.app.trys.object.LongIdDTO;
import com.app.trys.utils.StringUtils;
import lombok.Getter;

/**
 * 选择项的KV对
 * <P>
 *     ※ 当父类存在属性字段时，会导致下拉无法选择，原因未知
 * </P>
 * @author linjf
 * @since 2023/2/22
 */
@Getter
@ViewTable
public class TrysComboBoxItemKV extends LongIdDTO {

	public static final TrysComboBoxItemKV NULL = new TrysComboBoxItemKV(null, StringUtils.EMPTY);

	@ViewTableCol(name = "选项")
	@CtrlInput(name = "模糊过滤")
	private final String value;

	public TrysComboBoxItemKV(Long id, String value) {
		this.id = id;
		this.value = value;
	}

	public static TrysComboBoxItemKV of(BaseEnum baseEnum){
		return new TrysComboBoxItemKV(baseEnum.getId(), baseEnum.getValue());
	}

	public static TrysComboBoxItemKV of(Long key, String value){
		return new TrysComboBoxItemKV(key, value);
	}

	@Override
	public String toString(){
		return value;
	}

	@Override
	public void setId(Long id) {
		throw new UnsupportedOperationException();
	}
}
