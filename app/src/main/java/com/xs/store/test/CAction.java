package com.xs.store.test;

import com.xs.store.StoreAction3;

/**
 * Created by wangzhengtao767 on 2019/5/8.
 */

public class CAction extends StoreAction3<In,Out,AStore,BStore,CStore> {



    @Override
    public void store(AStore testStore,BStore bStore,CStore cStore, Out o) {
        testStore.setName(o.getMessage());
        testStore.setAge(22);
        String message = testStore.getIdea().getMessage();
        testStore.getIdea().setMessage(message + " + " + o.getMessage());

        bStore.setMsg(o.getMessage() + 22);
        cStore.setMsg(o.getMessage() + 24);
    }


    @Override
    public void handle(final In in) {
        if (true) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    success(in,new Out(in.getName()));
                }
            }).start();

        } else {
            error(-1,"",null);
        }

    }

}
