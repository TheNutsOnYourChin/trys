package com.app.trys.account.dto;

import com.app.trys.account.event.TrysAccountEvents;
import com.app.trys.base.form.*;
import com.app.trys.base.table.ViewTable;
import com.app.trys.base.table.ViewTableCol;
import com.app.trys.components.facade.form.validator.validator.RequiredValidator;
import com.app.trys.object.LongIdDTO;
import lombok.Data;

/**
 * @author linjf
 * @since 2023/2/21
 */
@Data
@SubmitForm(base = @Form(title = "账户信息", event = TrysAccountEvents.Save.class))
@ViewTable(onEditEvent = TrysAccountEvents.Save.class, onDelEvent = TrysAccountEvents.Delete.class)
public class TrysAccountDTO extends LongIdDTO {

	@ViewTableCol(name = "名称")
	@CtrlInput(base = @Ctrl(name = "名称", disableOnEdit = true, validators = RequiredValidator.class))
	private String name;

	@CtrlInput(name = "网址")
	@ViewTableCol(name = "网址")
	private String webSite;

	@ViewTableCol(name = "账户")
	@CtrlInput(base = @Ctrl(name = "账户", validators = RequiredValidator.class))
	private String account;

	@ViewTableCol(name = "密码")
	@CtrlInput(base = @Ctrl(name = "密码", validators = RequiredValidator.class))
	private String pw;

	@ViewTableCol(name = "盐值")
	@CtrlComboBox(base = @Ctrl(name = "盐值"), optionsName = "是否")
	private Boolean salt;
}
