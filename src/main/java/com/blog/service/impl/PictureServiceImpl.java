package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.dao.PictureDao;
import com.blog.entity.Picture;
import com.blog.service.PictureService;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl extends ServiceImpl<PictureDao, Picture> implements PictureService {
}
