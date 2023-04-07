package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {
    public List<Comment> getComment(Long blogId);

    public void deletebyId(Long blogId,Long comentId);
}
