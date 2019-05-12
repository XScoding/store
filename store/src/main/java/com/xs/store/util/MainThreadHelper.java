package com.xs.store.util;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by xs on 2019/2/20.
 */

public enum MainThreadHelper {

    INSTANCE;

    private Handler main = new Handler(Looper.getMainLooper());

    /**
     * execute runnable
     *
     * @param runnable
     */
    public void execute(Runnable runnable) {
        main.post(runnable);
    }

}
