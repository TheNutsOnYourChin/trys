package com.app.trys.utils;

import com.app.trys.constant.SystemConst;
import com.app.trys.exceptions.ExceptionUtils;
import com.app.trys.utils.lambda.ThrowableExecutor;
import com.app.trys.utils.lambda.ThrowableSupplier;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.function.Consumer;

/**
 * 执行类
 * @author linjf
 * @since 2023/2/16
 */
@SuppressWarnings(SystemConst.UNCHECKED)
public class Actions {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actions.class);

    public static final Runnable NULL_ACTION = () -> {};

    public static final EventHandler<ActionEvent> NULL_ACTION_EVENT = e -> {};

    public static final Consumer<?> NULL_CONSUMER = o -> {};

    /**
     * 执行过程，抛出其中的异常
     * @param action lambda
     */
    public static void doTry(ThrowableExecutor action, String errorMsg, Object... params){
        try {
            action.todo();
        }catch (Exception e){
            LOGGER.error(MessageFormat.format(errorMsg, params), e);
            throw ExceptionUtils.newException(e, errorMsg, params);
        }
    }

    /**
     * 执行过程，抛出其中的异常
     * @param action lambda
     */
    @SuppressWarnings(SystemConst.ALL)
    public static <T> T doTry(ThrowableSupplier<T> action, String errorMsg, Object... params){
        try {
            return action.get();
        }catch (Exception e){
            LOGGER.error(MessageFormat.format(errorMsg, params), e);
            throw ExceptionUtils.newException(e, errorMsg, params);
        }
    }

    public static void ifTrue(boolean bool, Runnable forTrue){
        ifElse(bool, forTrue, NULL_ACTION);
    }

    public static void ifElse(boolean bool, Runnable forTrue, Runnable forFalse){
        (bool ? forTrue : forFalse).run();
    }

    public static <T> Consumer<T> nullConsumer(){
        return (Consumer<T>)NULL_CONSUMER;
    }

}
