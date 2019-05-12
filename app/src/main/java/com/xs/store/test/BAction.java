package com.xs.store.test;

import android.os.Handler;

import com.xs.store.StoreAction2;

/**
 * Created by wangzhengtao767 on 2019/5/8.
 */

public class BAction extends StoreAction2<In,Out,AStore,BStore> {



    @Override
    public void store(AStore testStore, BStore secondStore, Out o) {
        testStore.setName(o.getMessage());
        testStore.setAge(20);
        String message = testStore.getIdea().getMessage();
        testStore.getIdea().setMessage(message + " + " + o.getMessage());

        secondStore.setMsg(o.getMessage() + 20);
    }


    @Override
    public void handle(final In in) {
        if (true) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    success(in,new Out(in.getName()));
                }
            },2000);

        } else {
            error(-1,"",null);
        }

    }

}
