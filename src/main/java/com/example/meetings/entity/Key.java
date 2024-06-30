package com.example.meetings.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Data
@TableName("secretkeys")
public class Key {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 房间Id
    @TableField("roomId")
    private String roomId;

    // 密码
    @TableField("pwd")
    private String pwd;

    // 学工号
    @TableField("ssno")
    private String ssno;

    // 开始时间
    @TableField("startDate")
    private String startDate;

    // 结束时间
    @TableField("endDate")
    private String endDate;

    // 是否过期
    @TableField("status")
    private Integer status;
}
