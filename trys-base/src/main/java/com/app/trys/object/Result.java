package com.app.trys.object;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

/**
 * 通常是service返回结果
 * @author linjf
 * @since 2025-03-28
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Result implements DTO{

    public static final String SIMPLE_SUCCESS_MSG = "Success!";

    @Getter
    private boolean success;
    private String msg;
    private Object data;

    public static Result success() {
        return success(null);
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setSuccess(true);
        result.setMsg(SIMPLE_SUCCESS_MSG);
        result.setData(data);
        return result;
    }

    public static Result fail(String msg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(msg);
        return result;
    }

    public void ifSuccess(Consumer<Object> consumer) {
        if(isSuccess()) {
            consumer.accept(data);
        }
    }

    public void ifFail(Consumer<Object> consumer) {
        if(!isSuccess()) {
            consumer.accept(data);
        }
    }

}
