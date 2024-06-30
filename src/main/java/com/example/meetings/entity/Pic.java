package com.example.meetings.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("pics")
public class Pic {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("orderId")
    private int orderId;

    @TableField("link")
    private String link;
    /**

     CREATE TABLE pics (
     id INT AUTO_INCREMENT PRIMARY KEY,
     orderId INT NOT NULL,
     link VARCHAR(1000)
     );
     */
}
