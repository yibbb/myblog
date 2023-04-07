package com.blog.controller;

import com.blog.service.BlogService;
import com.blog.service.MemoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

/**
 * 时间轴页面显示控制器
 */
@Controller
public class ArchiveShowController {
    @Resource
    private MemoryService memoryService;

    @GetMapping("/archives")
    public String archive(Model model){
        model.addAttribute("memorys",memoryService.list());
        return "archives";
    }
}
