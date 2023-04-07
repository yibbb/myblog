package com.blog.controller;

import com.blog.entity.Comment;
import com.blog.entity.User;
import com.blog.queryDto.DetailedBlog;
import com.blog.service.BlogService;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 评论控制器
 */
@Controller
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;//头像

    //查询评论列表
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId,Model model){
        List<Comment> list=commentService.getComment(blogId);
        model.addAttribute("comments",list);
        return "blog::commentList";
    }

    //新增评论
    @PostMapping("/comments")
    public String post(Comment comment,HttpSession session,Model model){
        //设置头像
        User user = (User) session.getAttribute("user");
        //设置头像
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
        }
        Long blogId=comment.getBlogId();
        if(comment.getParentComment()!=null){
            comment.setParentCommentId(comment.getParentComment().getId());
        }
        comment.setCreateTime(new Date());
        commentService.save(comment);
        List<Comment> list=commentService.getComment(blogId);
        model.addAttribute("comments",list);
        return "blog::commentList";
    }

    //删除评论
    @GetMapping("/comment/{blogId}/{id}/delete")
    public String delete(@PathVariable Long blogId, @PathVariable Long id,
                         Comment comment, RedirectAttributes attributes,Model model){
        commentService.deletebyId(blogId,id);
        DetailedBlog detailedBlog = blogService.getDetailedBlog(blogId);
        List<Comment> list=commentService.getComment(blogId);
        model.addAttribute("comments",list);
        model.addAttribute("blog",detailedBlog);
        return "blog";
    }

}
