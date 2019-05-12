package com.xs.store;

import com.xs.store.util.MainThreadHelper;

/**
 * Created by wangzhengtao767 on 2019/5/8.
 */

public abstract class StoreAction2<I, O, S extends Store,S2 extends Store> extends StoreAction<I, O, S>{

    @Override
    public final void store(S s, O o) {

    }

    public abstract void store(S s, S2 s2, O o);

    @Override
    public void success(final I i, final O o) {
        if (callback != null) {
            S s = StoreManager.getInstance().actionToStore(this);
            S2 s2 = StoreManager.getInstance().actionToStore(this,3);
            store(s,s2,o);
            StoreManager.getInstance().storeToCall(this);
            StoreManager.getInstance().storeToCall(this,3);
            MainThreadHelper.INSTANCE.execute(new Runnable() {
                @Override
                public void run() {
                    callback.success(i, o);
                }
            });
        }
        actionFinish();
    }
}
