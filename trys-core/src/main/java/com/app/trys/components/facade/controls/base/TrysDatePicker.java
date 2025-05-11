package com.app.trys.components.facade.controls.base;

import com.app.trys.components.facade.controls.ControlAdapter;
import com.app.trys.exceptions.ExceptionUtils;
import com.app.trys.utils.DateUtils;
import javafx.scene.control.DatePicker;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author linjf
 * @since 2025-04-22
 */
public class TrysDatePicker extends DatePicker implements ControlAdapter {

    @Getter
    private LocalDate defValue;

    public static TrysDatePicker of(){
        return new TrysDatePicker();
    }

    private TrysDatePicker() {
    }

    @Override
    public Date getCurrentValue() {
        return DateUtils.toDate(getValue());
    }

    @Override
    public void setCurrentValue(Object newValue) {
        setValue(parseLocalDate(newValue));
    }


    @Override
    public void setDefValue(Object defValue) {
        this.defValue = parseLocalDate(defValue);
    }

    private LocalDate parseLocalDate(Object source) {
        if(source == null){
            return null;
        }
        ExceptionUtils.isTure(source instanceof String || source instanceof Date || source instanceof LocalDate, "DatePicker defValue must be [String, Date, LocalDate]");
        return source instanceof String ? DateUtils.toLocalDate((String) source)
                : source instanceof Date ? DateUtils.toLocalDate((Date) source)
                : (LocalDate) source;
    }

}
