package com.xs.store;

/**
 * Created by wangzhengtao767 on 2019/5/7.
 */

public interface Action<I,O,S extends Store> {

    void handleObserver(StoreObserver observer);

    void handleAction(I i, Callback<I, O> callback);

    boolean isLive();

    void cancel();

}
