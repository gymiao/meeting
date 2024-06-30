package com.example.meetings.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("zone")
public class Zone {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 区域Id
    @TableField("zoneId")
    private String zoneId;

    // 区域名
    @TableField("zoneName")
    private String zoneName;
}
