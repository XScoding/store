package com.xs.store;

import com.xs.store.util.MainThreadHelper;
import com.xs.store.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangzhengtao767 on 2019/5/7.
 */

public class StoreManager {

    private static StoreManager instance;

    private StoreManager() {
    }

    public static StoreManager getInstance() {
        if (instance == null) {
            instance = new StoreManager();
        }
        return instance;
    }

    /**
     * stores
     */
    private List<Store> stores = new ArrayList<>();

    /**
     * observers
     */
    private List<StoreObserver> observers = new ArrayList<>();

    /**
     * call map
     */
    private Map<Class, List<Call>> callMap = new HashMap<>();
    private Map<Class, List<Call2>> call2Map = new HashMap<>();
    private Map<Class, List<Call3>> call3Map = new HashMap<>();

    public StoreManager register(StoreObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
        return this;
    }

    public void unRegister(StoreObserver observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    public void callStore(Class clz, final Call call) {
        if (callMap.containsKey(clz)) {
            List<Call> calls = callMap.get(clz);
            if (calls == null) {
                calls = new ArrayList<>();
                calls.add(call);
                callMap.put(clz, calls);
            } else {
                if (!calls.contains(call)) {
                    calls.add(call);
                }
            }
        } else {
            List<Call> calls = new ArrayList<>();
            calls.add(call);
            callMap.put(clz, calls);
        }
        final Store store = classToStore(clz);
        if (store != null) {
            callStore(store, call);
        }

    }

    public void call2Store(Class cls, Class cls2, final Call2 call) {
        call2StoreMap(cls, call);
        call2StoreMap(cls2, call);
        final Store store = classToStore(cls);
        final Store store2 = classToStore(cls2);
        if (store != null) {
            callStore(store, store2, call);
        }

    }

    private void call2StoreMap(Class cls, Call2 call) {
        if (call2Map.containsKey(cls)) {
            List<Call2> calls = call2Map.get(cls);
            if (calls == null) {
                calls = new ArrayList<>();
                calls.add(call);
                call2Map.put(cls, calls);
            } else {
                if (!calls.contains(call)) {
                    calls.add(call);
                }
            }
        } else {
            List<Call2> calls = new ArrayList<>();
            calls.add(call);
            call2Map.put(cls, calls);
        }
    }

    public void call3Store(Class clz, Class clz2, Class clz3, final Call3 call) {
        call3StoreMap(clz, call);
        call3StoreMap(clz2, call);
        call3StoreMap(clz3, call);
        final Store store = classToStore(clz);
        final Store store2 = classToStore(clz2);
        final Store store3 = classToStore(clz3);
        if (store != null) {
            callStore(store, store2, store3, call);
        }

    }

    private void call3StoreMap(Class cls, Call3 call) {
        if (call3Map.containsKey(cls)) {
            List<Call3> calls = call3Map.get(cls);
            if (calls == null) {
                calls = new ArrayList<>();
                calls.add(call);
                call3Map.put(cls, calls);
            } else {
                if (!calls.contains(call)) {
                    calls.add(call);
                }
            }
        } else {
            List<Call3> calls = new ArrayList<>();
            calls.add(call);
            call3Map.put(cls, calls);
        }
    }

    public void removeCall(Class clz, Call call) {
        if (callMap.containsKey(clz)) {
            if (callMap.get(clz).contains(call)) {
                callMap.get(clz).remove(call);
            }
        }
    }

    public void removeCall2(Class clz, Class clz2, Call2 call) {
        removeCall2Map(clz, call);
        removeCall2Map(clz2, call);
    }

    public void removeCall2Map(Class clz, Call2 call) {
        if (call2Map.containsKey(clz)) {
            if (call2Map.get(clz).contains(call)) {
                call2Map.get(clz).remove(call);
            }
        }
    }

    public void removeCall3(Class clz, Class clz2, Class clz3, Call3 call) {
        removeCall3Map(clz, call);
        removeCall3Map(clz2, call);
        removeCall3Map(clz3, call);
    }

    public void removeCall3Map(Class clz, Call3 call) {
        if (call3Map.containsKey(clz)) {
            if (call3Map.get(clz).contains(call)) {
                call3Map.get(clz).remove(call);
            }
        }
    }

    public <S extends Store> S actionToStore(StoreAction action) {
        return actionToStore(action,2);
    }

    public <S extends Store> S actionToStore(StoreAction action,int index) {
        Class genericityClass = Util.getGenericityClass(action, index);
        return classToStore(genericityClass);
    }

    public void storeToCall(StoreAction action) {
        storeToCall(action,2);
    }

    public void storeToCall(StoreAction action,int index) {
        Class genericityClass = Util.getGenericityClass(action, index);
        storeToCall(genericityClass);
    }

    public void storeToCall(Class genericityClass) {
        if (genericityClass != null) {
            List<Call> calls = callMap.get(genericityClass);
            if (calls != null) {
                for (Call call : calls) {
                    callStore(classToStore(genericityClass), call);
                }
            }
            List<Call2> call2s = call2Map.get(genericityClass);
            if (call2s != null) {
                for (Call2 call2 : call2s) {
                    Class first = Util.getGenericityInterface(call2);
                    Class second = Util.getGenericityInterface(call2, 1);
                    callStore(classToStore(first), classToStore(second), call2);
                }
            }
            List<Call3> call3s = call3Map.get(genericityClass);
            if (call3s != null) {
                for (Call3 call3 : call3s) {
                    Class first = Util.getGenericityInterface(call3);
                    Class second = Util.getGenericityInterface(call3, 1);
                    Class third = Util.getGenericityInterface(call3, 2);
                    callStore(classToStore(first), classToStore(second), classToStore(third), call3);
                }
            }
        }
    }

    private void callStore(final Store store, final Call call) {
        MainThreadHelper.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                call.handle(store);
            }
        });
    }

    private void callStore(final Store store, final Store store2, final Call2 call) {
        MainThreadHelper.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                call.handle(store, store2);
            }
        });
    }

    private void callStore(final Store store, final Store store2, final Store store3, final Call3 call) {
        MainThreadHelper.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                call.handle(store, store2, store3);
            }
        });
    }

    private <S extends Store> S classToStore(Class cls) {
        if (cls != null) {
            for (Store store : stores) {
                if (store.getClass() == cls) {
                    return (S) store;
                }
            }
            Store store = createStore(cls);
            if (store != null) {
                stores.add(store);
                return (S) store;
            }
        }
        return null;
    }

    private <S extends Store> Store createStore(Class<S> cls) {
        Store store = null;
        try {
            if (cls != null & Store.class.isAssignableFrom(cls)) {
                store = (Store) cls.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return store;
    }

    public <S extends Store> S getStore(Class<S> cls) {
        for (Store store : stores) {
            if (store.getClass() == cls) {
                return (S) store;
            }
        }
        return null;
    }


}
