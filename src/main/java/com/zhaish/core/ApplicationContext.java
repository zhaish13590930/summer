package com.zhaish.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @datetime:2020/10/9 16:11
 * @author: zhaish
 * @desc:
 **/
public class ApplicationContext implements Context {
    private String id;
    private String displayName;
    private long startupDate;
    private Map<String, Bean> registerBeanMap = new HashMap<>();

    public ApplicationContext(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public long getStartupDate() {
        return startupDate;
    }

    @Override
    public Bean getBean(String name) {
        return registerBeanMap.get(name);
    }

    @Override
    public Bean getBean(Class clazz) {
        String className = clazz.getSimpleName();
        return registerBeanMap.get(className);
    }

    public void registerBean(String beanName,Bean object){
        registerBeanMap.put(beanName, object);
    };
}
