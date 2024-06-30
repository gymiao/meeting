package com.example.meetings.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    // 主键
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 学工号
    @TableField("ssno")
    private String ssno;

    // 微信id
    @TableField("wxId")
    private String wxId;

    // 是否为管理员
    @TableField("pri")
    private Integer priority;

    // 姓名
    @TableField("name")
    private String name;

    // openid
    @TableField("openid")
    private String openid;

    // 密码
    @TableField("pwd")
    private String pwd;
}
