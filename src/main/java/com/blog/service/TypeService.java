package com.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.Type;

import java.util.List;

public interface TypeService extends IService<Type> {

    Page<Type> getlists(Integer pageNum);

}
