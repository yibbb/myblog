package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.dao.TypeDao;
import com.blog.entity.Type;
import com.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TypeServiceImpl extends ServiceImpl<TypeDao, Type> implements TypeService {
    @Resource
    private  TypeDao typeDao;

    //分页查询分类信息
    @Override
    public Page<Type> getlists(Integer pageNum) {
        Page page=new Page(pageNum,10);
        LambdaQueryWrapper<Type> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Type::getId);
        Page<Type> result = typeDao.selectPage(page, queryWrapper);
        return result;
    }

}
