package com.app.trys.components.facade.form.validator;

import com.app.trys.components.facade.controls.TrysControlWithLabel;
import com.app.trys.components.facade.form.BaseForm;
import com.app.trys.components.facade.form.validator.validator.ControlValidator;
import com.app.trys.object.IdDTO;
import com.app.trys.utils.Actions;
import com.app.trys.utils.ArrayUtil;
import com.app.trys.utils.ReflectUtils;
import com.app.trys.utils.StringUtils;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 校验器
 * @author linjf
 * @since 2023/4/4
 */
public class TrysFormValidator implements FormValidator {


	private final BaseForm<?> form;

	/**
	 * 是否校验通过
	 */
	private boolean valid = false;
	/**
	 * 表单控件对应的校验器
	 */
	@Getter
	private final Map<TrysControlWithLabel, List<ControlValidator>> controlValidatorsMap = new HashMap<>();

	public TrysFormValidator(BaseForm<?> form){
		this.form = form;
	}

	@Override
	public void removeWarning() {
		getControlValidatorsMap().keySet().forEach(control -> control.setMsgText(null));
	}


    @Override
    public void addControlValidator(TrysControlWithLabel control) {
		Actions.ifTrue(ArrayUtil.isNotEmpty(control.getValidators()), () -> this.getControlValidatorsMap().computeIfAbsent(control, k -> Arrays.stream(control.getValidators()).map(ReflectUtils::newInstance).collect(Collectors.toList())));
    }


	@Override
	public boolean verify() {
		IdDTO<?> formValue = form.getValue();
		this.valid = true;
		getControlValidatorsMap().forEach((control, validators) -> {
			Object controlValue = control.getCurrentValue();
			for (ControlValidator validator : validators) {
				String msg = validator.check(formValue, controlValue);
				control.setMsgText(msg);
				if(StringUtils.nonEmpty(msg)){
					this.valid = false;
					break;
				}
			}
		});
		return this.valid;
	}
}
