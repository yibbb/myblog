package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.Comment;
import com.blog.entity.Message;

import java.util.List;

public interface MessageService extends IService<Message> {
    List<Message> getMessageList();

    void deleteMessage(Long id);
}
