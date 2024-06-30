package com.example.meetings.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("room")
public class Room {
    // 主键
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 房间Id
    @TableField("roomId")
    private String roomId;

    // 房间名称
    @TableField("roomName")
    private String roomName;

    // 房间类型
    @TableField("roomType")
    private Integer roomType;

    // 房间编号
    @TableField("roomNo")
    private String roomNo;

    // 楼层Id
    @TableField("buildingId")
    private String buildingId;

    // 房间容量
    @TableField("capacity")
    private Integer capacity;

    @TableField("detail")
    private String detail;



}
