package com.blog.util;

/**
 * 基于TheadLocal封装工具类，用户保存和存取当前登录用户id
 */
public class BaseContext {
    private  static ThreadLocal<Long> threadLocal=new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
