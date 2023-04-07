package com.blog.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 留言实体类
 * @Date: Created in 11:44 2020/6/1
 * @Author: ONESTAR
 * @QQ群: 530311074
 * @URL: https://onestar.newstar.net.cn/
 */
@Data
public class Message {

    private Long id;//主键
    private String nickname;//昵称
    private String email;//邮箱
    private String content;//内容
    private String avatar;//头像
    private Date createTime;//创建时间
    private Long parentMessageId;//父留言id
    private boolean adminMessage;//是否为管理员留言

    //回复留言
    @TableField(exist = false)
    private List<Message> replyMessages = new ArrayList<>();//回复留言集合
    @TableField(exist = false)
    private Message parentMessage;//父留言
    @TableField(exist = false)
    private String parentNickname;//父留言昵称

    private String parentEmail;

    public String getParentEmail() {
        return parentEmail;
    }

    public String setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
        return parentEmail;
    }



    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }



    public Long getParentMessageId() {
        return parentMessageId;
    }

    public void setParentMessageId(Long parentMessageId) {
        this.parentMessageId = parentMessageId;
    }

    public boolean isAdminMessage() {
        return adminMessage;
    }

    public void setAdminMessage(boolean adminMessage) {
        this.adminMessage = adminMessage;
    }

    public List<Message> getReplyMessages() {
        return replyMessages;
    }

    public void setReplyMessages(List<Message> replyMessages) {
        this.replyMessages = replyMessages;
    }

    public Message getParentMessage() {
        return parentMessage;
    }

    public void setParentMessage(Message parentMessage) {
        this.parentMessage = parentMessage;
    }

    public String getParentNickname() {
        return parentNickname;
    }

    public void setParentNickname(String parentNickname) {
        this.parentNickname = parentNickname;
    }


    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", parentMessageId=" + parentMessageId +
                ", adminMessage=" + adminMessage +
                ", replyMessages=" + replyMessages +
                ", parentMessage=" + parentMessage +
                ", parentNickname='" + parentNickname + '\'' +
                ", parentEmail='" + parentEmail + '\'' +
                '}';
    }
}