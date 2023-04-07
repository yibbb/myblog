package com.blog.common;
//mybatis-plus公共字段填充功能，自定义元数据对象处理器

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjecthandler implements MetaObjectHandler {
    /**
     * 插入操作自动填充，进行数据库插入操作会自动跳转到这个方法,需要在对应实体类上的属性添加注解
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
    }

    /**
     * 更新操作自动填充，进行数据库更新操作会自动跳转到这个方法
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime",LocalDateTime.now());
    }
}
