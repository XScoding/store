package com.xs.store;

import com.xs.store.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangzhengtao767 on 2019/5/7.
 */

public class StoreObserver {

    private StoreManager manager;

    /**
     * call
     */
    private Map<Class,Call> callMap = new HashMap<>();

    /**
     * call2
     */
    private Map<Class,Call2> call2Map = new HashMap<>();

    /**
     * call3
     */
    private Map<Class,Call3> call3Map = new HashMap<>();

    /**
     * action
     */
    private List<Action> actions = new ArrayList<>();

    public void onCreate() {
        manager = StoreManager.getInstance().register(this);
    }

    public <T extends Store> void refresh(Call<T> call) {
        if (call == null) return;
        createError();
        Class genericityClass = Util.getGenericityInterface(call);
        genericityError(genericityClass,call.getClass().getName());

        Call oldCall = callMap.get(genericityClass);
        if (oldCall != null) {
            manager.removeCall(genericityClass,oldCall);
            callMap.remove(genericityClass);
        }
        manager.callStore(genericityClass,call);
        callMap.put(genericityClass,call);
    }

    public <T extends Store,T2 extends Store> void refresh(Call2<T,T2> call) {
        if (call == null) return;
        createError();
        Class genericityClass = Util.getGenericityInterface(call);
        Class genericityClass2 = Util.getGenericityInterface(call,1);
        genericityError(genericityClass,call.getClass().getName());
        genericityError(genericityClass2,call.getClass().getName());

        if (call2Map.get(genericityClass) != call2Map.get(genericityClass2)) {
            throw new IllegalArgumentException("StoreObserver don`t refresh same Store");
        }
        Call2 oldCall = call2Map.get(genericityClass);
        if (oldCall != null) {
            manager.removeCall2(genericityClass,genericityClass2,oldCall);
            call2Map.remove(genericityClass);
            call2Map.remove(genericityClass2);
        }
        manager.call2Store(genericityClass,genericityClass2,call);
        call2Map.put(genericityClass,call);
        call2Map.put(genericityClass2,call);

    }

    public <T extends Store,T2 extends Store,T3 extends Store> void refresh(Call3<T,T2,T3> call) {
        if (call == null) return;
        createError();
        Class genericityClass = Util.getGenericityInterface(call);
        Class genericityClass2 = Util.getGenericityInterface(call,1);
        Class genericityClass3 = Util.getGenericityInterface(call,2);
        genericityError(genericityClass,call.getClass().getName());
        genericityError(genericityClass2,call.getClass().getName());
        genericityError(genericityClass3,call.getClass().getName());

        if (call3Map.get(genericityClass) != call3Map.get(genericityClass2) && call3Map.get(genericityClass) != call3Map.get(genericityClass3)) {
            throw new IllegalArgumentException("StoreObserver don`t refresh same Store");
        }
        Call3 oldCall = call3Map.get(genericityClass);
        if (oldCall != null) {
            manager.removeCall3(genericityClass,genericityClass2,genericityClass3,oldCall);
            call3Map.remove(genericityClass);
            call3Map.remove(genericityClass2);
            call3Map.remove(genericityClass3);
        }
        manager.call3Store(genericityClass,genericityClass2,genericityClass3,call);
        call3Map.put(genericityClass,call);
        call3Map.put(genericityClass2,call);
        call3Map.put(genericityClass3,call);
    }

    public <I,O,S extends Store> void handAciton(Class<? extends StoreAction<I,O,S>> cls, I i, Callback<I,O> callback) {
        handAciton(cls,i,callback,true);

    }

    public <I,O,S extends Store> void handAciton(Class<? extends StoreAction<I,O,S>> cls, I i, Callback<I,O> callback,boolean isExcuteHandle) {
        StoreAction action = createAction(cls);
        if (action == null) throw new IllegalArgumentException("action: " + cls.getName() +  " can not reflect");
        handAciton(action,i,callback,isExcuteHandle);
    }

    public <I,O,S extends Store> void handAciton(StoreAction<I,O,S> action, I i, Callback<I,O> callback) {
        handAciton(action,i,callback,true);
    }

    public <I,O,S extends Store> void handAciton(StoreAction<I,O,S> action, I i, Callback<I,O> callback,boolean isExcuteHandle) {
        createError();
        if (action == null) throw new IllegalArgumentException("action is null");
        action.handleObserver(this);
        if (isExcuteHandle) {
            action.handleAction(i,callback);
        } else {
            Class genericityClass = Util.getGenericityClass(action, 2);
            Store store = manager.getStore(genericityClass);
            if (store == null) {
                action.handleAction(i,callback);
            } else {
                manager.storeToCall(genericityClass);
            }
        }
    }

    private <I,O,S extends Store> StoreAction createAction(Class<? extends StoreAction<I,O,S>> cls) {
        StoreAction action = null;
        try {
            if (cls != null & StoreAction.class.isAssignableFrom(cls)) {
                action = (StoreAction) cls.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return action;
    }

    public void onDestroy() {
        createError();
        if (!callMap.isEmpty()) {
            for (Map.Entry<Class, Call> entry : callMap.entrySet()) {
                manager.removeCall(entry.getKey(),entry.getValue());
            }
        }
        if (!call2Map.isEmpty()) {
            for (Map.Entry<Class, Call2> call2Entry : call2Map.entrySet()) {
                manager.removeCall2Map(call2Entry.getKey(),call2Entry.getValue());
            }
        }
        if (!call3Map.isEmpty()) {
            for (Map.Entry<Class, Call3> call3Entry : call3Map.entrySet()) {
                manager.removeCall3Map(call3Entry.getKey(),call3Entry.getValue());
            }
        }
        if (!actions.isEmpty()) {
            for (Action action : actions) {
                if (action.isLive()) {
                    action.cancel();
                }
            }
            actions.clear();
        }
        StoreManager.getInstance().unRegister(this);
        manager = null;
    }

    private void createError() {
        if (manager == null) throw new IllegalArgumentException("StoreObserver must be onCreate");
    }

    private void genericityError(Class genericityClass,String className) {
        if (genericityClass == null) throw new IllegalArgumentException("Call: " + className +" Genericity is null");
    }

    public void removeAction(StoreAction action) {
        if (action != null && actions.contains(action)) {
            actions.remove(action);
        }
    }

    public void addAction(StoreAction action) {
        if (action != null && !actions.contains(action)) {
            actions.add(action);
        }
    }

    public  <S extends Store> S getStore(Class<S> cls) {
        return manager.getStore(cls);
    }
}
