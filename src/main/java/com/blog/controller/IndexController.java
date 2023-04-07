package com.blog.controller;


import com.blog.queryDto.DetailedBlog;
import com.blog.queryDto.FirstPageBlog;
import com.blog.queryDto.RecommendBlog;
import com.blog.service.BlogService;
import com.blog.service.CommentService;
import com.blog.service.MessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * 首页控制器
 */
@Controller
public class IndexController {
    @Resource
    private BlogService  blogService;
    @Resource
    private CommentService commentService;
    @Resource
    private MessageService messageService;

    //分页查询博客列表
    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum,
                        RedirectAttributes attributes){
        PageHelper.startPage(pageNum,10);
        List<FirstPageBlog> allFirstPageBlog=blogService.getAllFirstPageBlog();
        List<RecommendBlog> recommendBlogs=blogService.getAllRecommendBlog();
        for (FirstPageBlog a:allFirstPageBlog
             ) {
            System.out.println("--------------------------------========"+a.toString());
        }
        PageInfo<FirstPageBlog> pageBlogPageInfo=new PageInfo<>(allFirstPageBlog);
        model.addAttribute("pageInfo",pageBlogPageInfo);
        model.addAttribute("recommendedBlogs",recommendBlogs);
        return "index";
    }

    //搜索博客
    @PostMapping("/search")
    public String search(Model model, @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum,
                         @RequestParam String query){
        PageHelper.startPage(pageNum,100);
        List<FirstPageBlog> list=blogService.getSearchBlog(query);
        PageInfo<FirstPageBlog> pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("query",query);
        return "search";
    }
    //博客信息统计
    @GetMapping("/footer/blogmessage")
    public String blogMessage(Model model){
        int blogTotal=blogService.count();
        int comment=commentService.count();
        int message=messageService.count();
        int blogView=blogService.getViewtotal();

        model.addAttribute("blogTotal",blogTotal);
        model.addAttribute("blogViewTotal",blogView);
        model.addAttribute("blogCommentTotal",comment);
        model.addAttribute("blogMessageTotal",message);

        return "index :: blogMessage";
    }

    //跳转博客详情页面
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        DetailedBlog detailedBlog=blogService.getDetailedBlog(id);
        model.addAttribute("blog",detailedBlog);
        return "blog";
    }

}
