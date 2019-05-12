package com.xs.store.test;

import com.xs.store.StoreAction;

/**
 * Created by wangzhengtao767 on 2019/5/8.
 */

public class AAction extends StoreAction<In,Out,AStore> {



    @Override
    public void store(AStore testStore, Out o) {
        testStore.setName(o.getMessage());
        testStore.setAge(18);
        String message = testStore.getIdea().getMessage();
        testStore.getIdea().setMessage(message + " + " + o.getMessage());
    }


    @Override
    public void handle(In in) {
        if (true) {
            success(in,new Out(in.getName()));
        } else {
            error(-1,"",null);
        }

    }

}
