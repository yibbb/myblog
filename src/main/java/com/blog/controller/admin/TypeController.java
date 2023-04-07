package com.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.entity.Type;
import com.blog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Resource
    private TypeService typeService;

    //分页查询分类列表
    @GetMapping("/types")
    public String list(Model model, @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum){
       String orderBy="id desc";
        PageHelper.startPage(pageNum,10,orderBy);
        List<Type> list=typeService.list();
        PageInfo<Type> pageInfo=new PageInfo<Type>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/types";
    }

    //返回新增页面
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }
    //新增分类
    @PostMapping("/types")
    public String post( Type type, RedirectAttributes attributes) {
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("name", type.getName());
        Type type1 = typeService.getOne(wrapper);
        if (type1 != null) {
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        }
        boolean save = typeService.save(type);
        if (save) {
            attributes.addFlashAttribute("message", "新增成功");
        } else {
            attributes.addFlashAttribute("message", "新增失败");
        }
        return "redirect:/admin/types";
    }
    //跳转修改分类页面
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("type",typeService.getById(id));
        return "admin/types-input";
    }
    //编辑修改分类
    @PostMapping("/types/{id}")
    public String editPost(Type type,RedirectAttributes attributes){
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("name", type.getName());
        Type type1 = typeService.getOne(wrapper);
        if (type1 != null) {
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        }
        boolean flag = typeService.updateById(type);
        if (flag) {
            attributes.addFlashAttribute("message", "新增成功");
        } else {
            attributes.addFlashAttribute("message", "新增失败");
        }
        return "redirect:/admin/types";
    }
    //删除分类
    @GetMapping("types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.removeById(id);
        attributes.addFlashAttribute("massage","删除成功");
        return "redirect:/admin/types";
    }
}
