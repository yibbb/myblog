package com.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FriendLinkController {

    @GetMapping("/friends")
    public String friends(Model model){
        return "friends";
    }
}
