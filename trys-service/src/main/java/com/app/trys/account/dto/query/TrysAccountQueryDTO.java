package com.app.trys.account.dto.query;

import com.app.trys.account.dto.TrysAccountDTO;
import com.app.trys.account.event.TrysAccountEvents;
import com.app.trys.base.form.*;
import com.app.trys.object.BasePage;
import lombok.Data;

/**
 * @author linjf
 * @since 2023/2/21
 */
@Data
@SearchModule(
		base = @Form(event = TrysAccountEvents.Query.class, btnArea = TrysAccountDTO.class),
		resultClass = TrysAccountDTO.class
)
public class TrysAccountQueryDTO extends BasePage {

	@CtrlInput(name = "名称")
	private String name;
	@CtrlInput(name = "网址")
	private String webSite;
	@CtrlInput(name = "账户")
	private String account;
	@CtrlInput(name = "密码")
	private String pw;
	@CtrlComboBox(name = "盐值", optionsName = "是否")
	private String salt;
}
