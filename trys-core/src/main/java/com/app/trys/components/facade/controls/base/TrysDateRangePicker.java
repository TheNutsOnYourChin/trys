package com.app.trys.components.facade.controls.base;

import com.app.trys.components.facade.controls.ControlAdapter;
import com.app.trys.object.DateRange;
import com.app.trys.util.ComponentUtils;
import com.app.trys.utils.DateUtils;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.util.Calendar;
import java.util.Date;

/**
 * @author linjf
 * @since 2025-04-22
 */
public class TrysDateRangePicker extends HBox implements ControlAdapter {


    private TrysDatePicker bgnDatePicker;

    private TrysDatePicker endDatePicker;



    public static TrysDateRangePicker of(){
        return new TrysDateRangePicker();
    }

    private TrysDateRangePicker() {
        bgnDatePicker = TrysDatePicker.of();
        endDatePicker = TrysDatePicker.of();
        this.setAlignment(Pos.CENTER_LEFT);
        TrysLabel label = TrysLabel.of("~");
        label.setPrefWidth(10);
        this.getChildren().addAll(bgnDatePicker, label, endDatePicker);
        ComponentUtils.autoBoxSize(bgnDatePicker, this, w -> w.subtract(label.getPrefWidth()).divide(2), null);
        ComponentUtils.autoBoxSize(endDatePicker, this, w -> w.subtract(label.getPrefWidth()).divide(2), null);
    }

    @Override
    public DateRange getCurrentValue() {
        // 这里应该是结束日期的最后一刻
        Date endOfDay = DateUtils.endOf(endDatePicker.getCurrentValue(), Calendar.DATE);
        return new DateRange(bgnDatePicker.getCurrentValue(), endOfDay);
    }

    @Override
    public void setCurrentValue(Object newValue) {
        DateRange dateRange = (DateRange) newValue;
        bgnDatePicker.setCurrentValue(dateRange.getBgn());
        endDatePicker.setCurrentValue(dateRange.getEnd());
    }

    @Override
    public Object getDefValue() {
        return new DateRange(DateUtils.toDate(bgnDatePicker.getDefValue()), DateUtils.toDate(endDatePicker.getDefValue()));
    }


    @Override
    public void setDefValue(Object defValue) {
        DateRange dateRange = (DateRange) defValue;
        this.bgnDatePicker.setDefValue(dateRange.getBgn());
        this.endDatePicker.setDefValue(dateRange.getEnd());
    }

}
