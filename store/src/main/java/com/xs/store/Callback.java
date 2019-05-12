package com.xs.store;

/**
 * Created by wangzhengtao767 on 2019/5/7.
 */

public interface Callback<I,O> {

    void start();

    void success(I i, O o);

    void error(int code, String message, Throwable e);
}
