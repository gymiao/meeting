package com.example.meetings.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("building")
public class Building {
    // 主键
    @TableId(value = "id", type= IdType.AUTO)
    private Integer id;

    // 楼层Id
    @TableField("buildingId")
    private String buildingId;

    // 楼层名
    @TableField("buildingName")
    private String buildingName;

    // 区域Id
    @TableField("zoneId")
    private String zoneId;
}
