package com.xs.store.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by wangzhengtao767 on 2019/5/7.
 */

public class Util {


    /**
     * 获取接口泛型类名
     *
     * @param obj
     * @return
     */
    public static Class getGenericityInterface(Object obj) {
        return getGenericityInterface(obj,0);
    }

    /**
     * 获取接口泛型类名
     *
     * @param obj
     * @param i
     * @return
     */
    public static Class getGenericityInterface(Object obj, int i) {
        try {
            Type[] types = obj.getClass().getGenericInterfaces();
            ParameterizedType genericSuperclass = (ParameterizedType) types[0];
            Class aClass = (Class) (genericSuperclass.getActualTypeArguments()[i]);
            return aClass;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取接口泛型类名
     *
     * @param obj
     * @param i
     * @return
     */
    public static Class getGenericityClass(Object obj, int i) {
        try {
            Type types = obj.getClass().getGenericSuperclass();
            ParameterizedType genericSuperclass = (ParameterizedType) types;
            Class aClass = (Class) (genericSuperclass.getActualTypeArguments()[i]);
            return aClass;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

}
