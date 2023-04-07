package com.blog.controller;

import com.blog.service.PictureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

/**
 * 照片墙页面展示
 */
@Controller
public class PictureShowController {

    @Resource
    private PictureService pictureService;

    @GetMapping("/picture")
    public String picture(Model model){
        model.addAttribute("pictures",pictureService.list());
        return "picture";
    }
}
