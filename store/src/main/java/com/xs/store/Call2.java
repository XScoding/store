package com.xs.store;

/**
 * Created by wangzhengtao767 on 2019/5/7.
 */

public interface Call2<T extends Store,T2 extends Store> {


    void handle(T t, T2 t2);

}
