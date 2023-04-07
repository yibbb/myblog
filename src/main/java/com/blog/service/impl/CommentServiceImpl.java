package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.dao.BlogDao;
import com.blog.dao.CommentDao;
import com.blog.entity.Comment;
import com.blog.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 博客评论业务层
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {
    @Resource
    private BlogDao blogDao;
    @Resource
    private CommentDao commentDao;
    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    //查询博客中父节点的评论
    public List<Comment> getComment(Long blogId){
        QueryWrapper<Comment> wrapper=new QueryWrapper<>();
        wrapper.eq("blog_id",blogId).eq("parent_comment_id",-1);
        List<Comment> list=this.list(wrapper);
        for (Comment comment:list
             ) {
            Long commentId=comment.getId();
            String nickname=comment.getNickname();
            //查询子评论
            QueryWrapper<Comment> wrapper1=new QueryWrapper<>();
            wrapper1.eq("blog_id",blogId).eq("parent_comment_id",commentId);
            List<Comment> childComments=this.list(wrapper1);
            childComment(blogId,childComments,nickname);
            //按顺序存储子评论
            comment.setReplyComments(tempReplys);
            tempReplys=new ArrayList<>();
        }
        return list;
    }

    //把子评论和二级评论封装到tempReplys中
    public void childComment(Long blogId,List<Comment> comments,String parentNickName) {
        //判断是否有一级评论
        if (comments.size() >= 0) {
            //循环查找存储一级二级评论
            for (Comment comment : comments
            ) {
                Long childId = comment.getId();
                String childName = comment.getNickname();
                comment.setParentNickname(parentNickName);
                tempReplys.add(comment);
                //查询二级评论
                secondComment(blogId,childId,childName);
            }
        }
    }

    //把二级评论封装到tempReplys中
    private void secondComment(Long blogId,Long childId,String parentNickName) {
        //根据一级评论id查询二级评论
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("blog_id",blogId).eq("parent_comment_id",childId);
        List<Comment> list=this.list(queryWrapper);

        //查询二级评论
        if(list.size()>0) {
            for (Comment comment: list
                 ) {
                String name=comment.getNickname();
                comment.setParentNickname(parentNickName);
                Long id=comment.getId();
                tempReplys.add(comment);
                secondComment(blogId,id,name);
            }
        }
    }

    //删除所有评论
    @Override
    public void deletebyId(Long blogId,Long comentId) {
        //删除父评论
        this.removeById(comentId);
        deleteSecond(blogId,comentId);


//        //查询子评论
//        QueryWrapper<Comment> wrapper=new QueryWrapper<>();
//        wrapper.eq("parent_comment_id",comentId).eq("blog_id",blogId);
//        List<Comment> child = this.list(wrapper);
//        //判断是否有子评论
//        if(child.size()>0) {
//            for (Comment comment : child
//            ) {
//                Long id = comment.getId();
//                //删除子评论
//                this.removeById(id);
//                //删除二级评论
//                deleteSecond(blogId,id);
//            }
//        }
    }

    //删除所有子评论
    public void deleteSecond(Long blogId,Long commentId){
        //根据父评论id查询二级评论
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("blog_id",blogId).eq("parent_comment_id",commentId);
        List<Comment> list=this.list(queryWrapper);

        //删除二级评论
        if (list.size()>0){
            for (Comment comment:list
                 ) {
                Long id=comment.getId();
                this.removeById(id);
                deleteSecond(blogId,id);
            }
        }
    }

}
