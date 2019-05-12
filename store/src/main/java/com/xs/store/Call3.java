package com.xs.store;

/**
 * Created by wangzhengtao767 on 2019/5/7.
 */

public interface Call3<T extends Store,T2 extends Store,T3 extends Store> {


    void handle(T t, T2 t2, T3 t3);

}
