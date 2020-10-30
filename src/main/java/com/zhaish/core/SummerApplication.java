package com.zhaish.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @datetime:2020/10/9 16:14
 * @author: zhaish
 * @desc:
 **/
public class SummerApplication {
    public void run(Class clazz,String... args) {
        ApplicationContext applicationContext = new ApplicationContext("","");
        try {
            Constructor constructor =  clazz.getDeclaredConstructor();
            constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
