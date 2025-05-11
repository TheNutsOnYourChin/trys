package com.app.trys.enums;

import com.app.trys.base.dto.OptionsSupplier;
import com.app.trys.base.dto.TrysComboBoxItemKV;
import com.app.trys.works.enums.kv.YesOrNo;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 下拉选项
 * @author linjf
 * @since 2023/4/26
 */
public class OptionsEnum {

	public static OptionsSupplier yesOrNo = new OptionsSupplier(1L, "是否", () -> Arrays.stream(YesOrNo.values()).map(TrysComboBoxItemKV::of).collect(Collectors.toList()));

}
