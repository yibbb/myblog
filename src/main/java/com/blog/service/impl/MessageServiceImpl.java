package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.dao.CommentDao;
import com.blog.dao.MessageDao;
import com.blog.entity.Comment;
import com.blog.entity.Message;
import com.blog.service.CommentService;
import com.blog.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, Message> implements MessageService {
    @Resource
    private MessageDao messageDao;

    //存放迭代找出的所有子代的集合
    private List<Message> tempReplys = new ArrayList<>();

    //查询所有留言
    @Override
    public List<Message> getMessageList() {
        //查询父留言
        QueryWrapper<Message> wrapper=new QueryWrapper<>();
        wrapper.eq("parent_message_id",-1);
        List<Message> list=this.list(wrapper);
        if(list.size()>0){
            for (Message message:list
                 ) {
                Long id=message.getId();
                String name=message.getNickname();
                //查询一级留言
                QueryWrapper<Message> wrapper1=new QueryWrapper<>();
                wrapper1.eq("parent_message_id",id);
                List<Message> childMessage=this.list(wrapper1);
                //按顺序存储子评论
                childMessage(childMessage,name);
                message.setReplyMessages(tempReplys);
                tempReplys=new ArrayList<>();
            }
        }
        return list;
    }

    //把子留言封装到tempReplys中
    private void childMessage(List<Message> messages,String parentNickName){
        //判断是否有子留言
        if(messages.size()>0){
            for (Message message:messages
                 ) {
                Long id=message.getId();
                String name=message.getNickname();
                message.setParentNickname(parentNickName);
                //添加一级子评论
                tempReplys.add(message);
                //添加二级评论
                SecondMessage(id,name);
            }
        }
    }
    //把二级留言保存到tempReplys
    private void SecondMessage(Long commentId,String parentNickName){
        //查询子留言列表
        QueryWrapper<Message> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("parent_message_id",commentId);
        List<Message> list=this.list(queryWrapper);
        if(list.size()>0){
            for (Message message:list
                 ) {
                Long id=message.getId();
                String name=message.getNickname();
                message.setParentNickname(parentNickName);
                tempReplys.add(message);
                SecondMessage(id,name);
            }
        }
    }

    /**
     * 删除留言
     * @param id
     */
    @Override
    public void deleteMessage(Long id) {
        //删除父留言
        this.removeById(id);
        //删除子留言
        deleteChild(id);
    }
    //删除子留言
    private void deleteChild(Long commentId){
        //根据父留言查询所有子留言
        QueryWrapper<Message> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("parent_message_id",commentId);
        List<Message> list=this.list(queryWrapper);
        //循环删除所有子留言
        if(list.size()>0){
            for (Message message:list
            ) {
                Long id=message.getId();
                removeById(id);
                deleteMessage(id);
            }
        }
    }

}
