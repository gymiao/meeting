package com.example.meetings.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@TableName("orders")
public class Order {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("roomId")
    private String roomId;

    @TableField("startTime")
    private String startTime;

    @TableField("endTime")
    private String endTime;

    // 会议类型
    @TableField("type")
    private Integer type;

    @TableField("ssno")
    private String ssno;


    // 会议详情
    @TableField("detail")
    private String detail;

    // 会议状态,会议是否申请成功
    @TableField("status")
    private Integer status;

    @TableField("feekback")
    private String feekback;

    @TableField("picture")
    private String picture;

    @TableField("admin")
    private String admin;

    @TableField("wordurl")
    private String wordurl;

    // 订餐人数
    @TableField("cnts")
    private Integer cnts;

    // 订餐人名
    @TableField("names")
    private String names;

    // 负责人
    @TableField("leader")
    private String leader;

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("单号:" + id + "\n");
        stringBuffer.append("会议室:" + roomId + "\n");

        SimpleDateFormat sdf= new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Long l1 = Long.valueOf(startTime);
        Date date1 = new Date(l1);
        Long l2 = Long.valueOf(endTime);
        Date date2 = new Date(l2);

        stringBuffer.append("开始时间:" + sdf.format(date1) + "\n");
        stringBuffer.append("结束时间:" + sdf.format(date2) + "\n");
        if(type == 1) {
            stringBuffer.append("类型:组会\n");
        } else if(type == 2) {
            stringBuffer.append("类型:工作会\n");
        } else if (type == 3) {
            stringBuffer.append("类型:报告\n");
        } else if (type == 4) {
            stringBuffer.append("类型:座谈会\n");

        } else if (type == 5) {
            stringBuffer.append("类型:党会\n");

        } else if (type == 6) {
            stringBuffer.append("类型:教师大会\n");

        } else if (type == 7) {
            stringBuffer.append("类型:学生大会\n");

        } else if (type == 8) {
            stringBuffer.append("类型:交流会\n");

        } else if (type == 9) {
            stringBuffer.append("类型:午餐会\n");

        } else if (type == 10) {
            stringBuffer.append("类型:答辩\n");

        } else {
            stringBuffer.append("类型:其他\n");        }
        stringBuffer.append("申请人学号:" + ssno + "\n");
        stringBuffer.append("会议细节:" + detail + "\n");
        if(status == 0) {
            stringBuffer.append("预约状态:未审核\n");
        } else if (status == 1) {
            stringBuffer.append("预约状态:已审核\n");
        } else if (status == -2) {
            stringBuffer.append("预约状态:预约已过期且用户未反馈\n");
        } else if (status == 2) {
            stringBuffer.append("预约状态:已反馈\n");
        }
        stringBuffer.append("反馈:" + feekback + "\n");
        stringBuffer.append("审核人学号:" + admin + "\n");
        return stringBuffer.toString();
    }
}
