package com.xs.store.test;

import com.xs.store.Store;

/**
 * Created by wangzhengtao767 on 2019/5/9.
 */

public class CStore implements Store {


    private String msg = "CStore";

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
