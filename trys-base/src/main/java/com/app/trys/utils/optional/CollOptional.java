package com.app.trys.utils.optional;

import com.app.trys.utils.CollUtils;

import java.util.Collection;

public class CollOptional<T> extends AbstractBaseOptional<Collection<T>>{

    public static <T> CollOptional<T> ofNull(){
        return new CollOptional<>();
    }

    public static <T> CollOptional<T> of(Collection<T> o){
        CollOptional<T> opt = new CollOptional<>();
        opt.set(o);
        return opt;
    }

    @Override
    public boolean isPresent(){
        return CollUtils.isNotEmpty(value);
    }
}
