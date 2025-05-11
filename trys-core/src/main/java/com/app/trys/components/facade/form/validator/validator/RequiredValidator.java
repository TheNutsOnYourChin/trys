package com.app.trys.components.facade.form.validator.validator;

import com.app.trys.object.DTO;
import com.app.trys.utils.CollUtils;
import com.app.trys.utils.StringUtils;

import java.util.Collection;

/**
 * 必填校验
 * @author linjf
 * @since 2025-03-27
 */
public class RequiredValidator implements ControlValidator {

    @Override
    public String check(DTO formValue, Object controlValue) {
        return StringUtils.isEmpty(controlValue) || controlValue instanceof Collection && CollUtils.isEmpty((Collection<?>) controlValue) ? "This field is required." : StringUtils.EMPTY;
    }
}
