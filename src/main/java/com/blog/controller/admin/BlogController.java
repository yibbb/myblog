package com.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.entity.Blog;
import com.blog.entity.Type;
import com.blog.entity.User;
import com.blog.queryDto.BlogQuery;
import com.blog.queryDto.SearchBlog;
import com.blog.queryDto.ShowBlog;
import com.blog.service.BlogService;
import com.blog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {
    @Resource
    private BlogService blogService;
    @Resource
    private TypeService typeService;

    //跳转新增博客页面
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.list());
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }

    //博客新增
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        User user = (User) session.getAttribute("user");
        blog.setUser(user);
        System.out.println("----------------------->"+blog.getTypeId());
        Type type=typeService.getById(blog.getTypeId());
        System.out.println("------------------------->"+type.toString());
        blog.setType(type);
        blog.setTypeId(type.getId());
        blog.setUserId(user.getId());
        blog.setViews(0);
        boolean save = blogService.save(blog);
        if(save){
            attributes.addFlashAttribute("message","新增成功");
        }else {
            attributes.addFlashAttribute("message","新增失败");
        }
        return "redirect:/admin/blogs";
    }

    //博客列表
    @GetMapping("/blogs")
    public String blogs(Model model, @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum){
        String orderBy="update_time desc";
        PageHelper.startPage(pageNum,10,orderBy);
        List<BlogQuery> list=blogService.getallblog();
        PageInfo<BlogQuery> pageInfo=new PageInfo<>(list);
        model.addAttribute("types",typeService.list());
        model.addAttribute("pageInfo",pageInfo);
        return "admin/blogs";
    }

    //删除博客
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable long id,RedirectAttributes attributes){
        blogService.removeById(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }

    //跳转编辑修改文章
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable long id,Model model){
        ShowBlog showBlog=blogService.getShowBlog(id);
        List<Type> list=typeService.list();
        model.addAttribute("blog", showBlog);
        model.addAttribute("types", list);
        return "admin/blogs-input";
    }

    //编辑修改文章
    @PostMapping("/blogs/{id}")
    public String editPost(Blog blog,RedirectAttributes attributes){
        blogService.updateById(blog);
        return "redirect:/admin/blogs";
    }

    //搜索博客管理列表
    @PostMapping("/blogs/search")
    public String search(SearchBlog searchBlog,Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        List<BlogQuery> list=blogService.getBlogSearch(searchBlog);
        PageHelper.startPage(pageNum,10);
        PageInfo<BlogQuery> pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/blogs::blogList";
    }
}
