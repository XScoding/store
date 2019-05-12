package com.xs.store;

/**
 * Created by wangzhengtao767 on 2019/5/7.
 */

public interface Call<T extends Store> {


    void handle(T t);

}
