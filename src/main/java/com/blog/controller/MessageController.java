package com.blog.controller;

import com.blog.entity.Message;
import com.blog.entity.User;
import com.blog.service.MessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {
    @Resource
    private MessageService messageService;

    @Value("${message.avarer}")
    private String avatar;

    /**
     * 获取留言板信息
     * @param model
     * @param pageNum 留言板页数
     * @return
     */
    @GetMapping("/message")
    public String message(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,15);
        List<Message> list=messageService.getMessageList();
        PageInfo<Message> pageInfo=new PageInfo<>(list);
        model.addAttribute("messages",pageInfo);
        return "message";
    }

    /**
     * 新增留言
     */
    @PostMapping("/message")
    public String post(Message message, HttpSession session,
                       Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        User user = (User) session.getAttribute("user");
        //设置头像
        if (user != null) {
            message.setAvatar(user.getAvatar());
            message.setAdminMessage(true);
        } else {
            message.setAvatar(avatar);
        }
        //判断父评论是否存在
        if(message.getParentMessage()!=null){
            //保存父评论id
            message.setParentMessageId(message.getParentMessage().getId());
        }
        message.setCreateTime(new Date());
        messageService.save(message);

        //跳转到留言板页面
        PageHelper.startPage(pageNum,15);
        List<Message> list=messageService.getMessageList();
        PageInfo<Message> pageInfo=new PageInfo<>(list);
        model.addAttribute("messages",pageInfo);
        return "message";
    }

    /**
     * 删除留言
     */
    @GetMapping("/messages/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            messageService.deleteMessage(id);
        }
        return "redirect:/message";
    }
}
