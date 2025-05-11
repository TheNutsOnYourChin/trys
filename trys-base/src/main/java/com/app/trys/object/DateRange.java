package com.app.trys.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 日期范围
 *
 * @author linjf
 * @since 2025-04-23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateRange implements DTO{

    private Date bgn;

    private Date end;

    public boolean check(){
        return bgn == null || end == null || !bgn.after(end);
    }
}
