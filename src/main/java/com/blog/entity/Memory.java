package com.blog.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName: Menory
 * @Description: 流年记实体类
 * @Author ONESTAR
 * @Date: 2020/10/13 22:37
 * @QQ群：530311074
 * @URL：https://onestar.newstar.net.cn/
 * @Version 1.0
 */
@Data
public class Memory {

    private Long id;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    private String pictureAddress;
    private String memory;

    public Memory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPictureAddress() {
        return pictureAddress;
    }

    public void setPictureAddress(String pictureAddress) {
        this.pictureAddress = pictureAddress;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "Memory{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", pictureAddress='" + pictureAddress + '\'' +
                ", memory='" + memory + '\'' +
                '}';
    }
}
