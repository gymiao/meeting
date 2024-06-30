package com.example.meetings.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("foods")
public class Foods {
    // 主键
    @TableId(value = "id", type= IdType.AUTO)
    private Integer id;

    // 订单Id
    @TableField("orderId")
    private Integer orderId;

    // 人数
    @TableField("cnts")
    private Integer cnts;

    // 链接
    @TableField("names")
    private String names;
}

