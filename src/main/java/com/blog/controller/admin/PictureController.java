package com.blog.controller.admin;

import com.blog.entity.Picture;
import com.blog.service.PictureService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

//照片墙
@Controller
@RequestMapping("/admin")
public class PictureController {
    @Resource
    private PictureService pictureService;

    //查询照片列表
    @GetMapping("/pictures")
    public String pictures(Model model, @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum){
        PageHelper.startPage(pageNum,10);
        List<Picture> list=pictureService.list();
        PageInfo<Picture> pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/pictures";
    }
    //跳转新增页面
    @GetMapping("/pictures/input")
    public String input(Model model){
        model.addAttribute("picture",new Picture());
        return "admin/pictures-input";
    }
    //照片新增
    @PostMapping("/pictures")
    public String post(Picture picture, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            return "admin/pictures-input";
        }
        boolean save = pictureService.save(picture);
        if(save){
            attributes.addFlashAttribute("message","新增成功");
        }else {
            attributes.addFlashAttribute("message","新增失败");
        }
        return "redirect:/admin/pictures";
    }
    //跳转照片编辑页面
    @GetMapping("/pictures/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("picture",pictureService.getById(id));
        return "admin/pictures-input";
    }
    //编辑相册
    @PostMapping("/pictures/{id}")
    public String editPost(Picture picture,RedirectAttributes attributes){
        boolean save = pictureService.updateById(picture);
        if(save){
            attributes.addFlashAttribute("message","修改成功");
        }else {
            attributes.addFlashAttribute("message","修改失败");
        }
        return "redirect:/admin/pictures";
    }
    //删除照片
    @GetMapping("/pictures/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        pictureService.removeById(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/pictures";
    }
}
