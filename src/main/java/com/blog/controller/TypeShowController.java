package com.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.entity.Blog;
import com.blog.entity.Type;
import com.blog.queryDto.FirstPageBlog;
import com.blog.service.BlogService;
import com.blog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.jsqlparser.statement.select.First;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TypeShowController {
    @Resource
    private TypeService typeService;

    @Resource
    private BlogService blogService;
    /**
     * 查询分类
     */
    @GetMapping("/types/{id}")
    public String types(@RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum, @PathVariable Long id, Model model){
        List<Type> list=typeService.list();
        //第一次进入分类页面时候
        if (id==-1){
            id=list.get(0).getId();
        }
        //查询分类下的博客信息并保存
        list.stream().map((item)->{
            QueryWrapper<Blog> wrapper=new QueryWrapper<>();
            wrapper.eq("type_id",item.getId());
            List<Blog> blogs=blogService.list(wrapper);
            item.setBlogs(blogs);
            return item;
        }).collect(Collectors.toList());

        model.addAttribute("types",list);
        //获取分类下的博客
        List<FirstPageBlog> pageBlogs=blogService.getBlogByTypeId(id);
        PageHelper.startPage(pageNum,10);
        PageInfo<FirstPageBlog> pageBlogPageInfo=new PageInfo<>(pageBlogs);
        model.addAttribute("pageInfo",pageBlogPageInfo);
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
