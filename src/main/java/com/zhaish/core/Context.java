package com.zhaish.core;

/**
 * @datetime:2020/10/9 16:00
 * @author: zhaish
 * @desc:
 **/
public interface Context {
    /***
     * id
     * @return
     */
    String getId();

    /**
     * 显示名称
     * @return
     */
    String getDisplayName();

    /**
     * 开始加载时间戳
     * @return
     */
    long getStartupDate();

    Bean getBean(String name);

    Bean getBean(Class clazz);

}
