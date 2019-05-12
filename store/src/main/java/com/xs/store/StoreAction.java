package com.xs.store;

import com.xs.store.util.MainThreadHelper;

/**
 * Created by wangzhengtao767 on 2019/5/8.
 */

public abstract class StoreAction<I, O, S extends Store> implements Action<I, O, S>, Callback<I, O> {

    protected boolean isLive = true;

    protected Callback callback;

    protected StoreObserver observer;

    @Override
    public void handleAction(I i, Callback<I, O> callback) {
        this.callback = callback;
        start();
        handle(i);
    }

    @Override
    public boolean isLive() {
        return isLive;
    }

    @Override
    public void cancel() {
        callback = null;
        isLive = false;
    }

    public abstract void store(S s, O o);

    public abstract void handle(I i);

    @Override
    public void start() {
        if (callback != null) {
            MainThreadHelper.INSTANCE.execute(new Runnable() {
                @Override
                public void run() {
                    callback.start();
                }
            });
        }
    }

    @Override
    public void success(final I i, final O o) {
        if (callback != null) {
            S s = StoreManager.getInstance().actionToStore(this);
            store(s, o);
            StoreManager.getInstance().storeToCall(this);
            MainThreadHelper.INSTANCE.execute(new Runnable() {
                @Override
                public void run() {
                    callback.success(i, o);
                }
            });
        }
        actionFinish();
    }

    @Override
    public void error(final int code, final String message, final Throwable e) {
        if (callback != null) {
            MainThreadHelper.INSTANCE.execute(new Runnable() {
                @Override
                public void run() {
                    callback.error(code, message, e);
                }
            });
        }
        actionFinish();
    }

    @Override
    public void handleObserver(StoreObserver observer) {
        this.observer = observer;
        this.observer.addAction(this);
    }

    protected void actionFinish() {
        observer.removeAction(this);
        isLive = false;
        observer = null;
    }
}
