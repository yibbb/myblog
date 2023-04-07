package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.dao.MemoryDao;
import com.blog.dao.UserMapper;
import com.blog.entity.Memory;
import com.blog.entity.User;
import com.blog.service.MemoryService;
import com.blog.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class MemoryServiceImpl extends ServiceImpl<MemoryDao, Memory> implements MemoryService {
}
