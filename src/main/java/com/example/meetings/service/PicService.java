package com.example.meetings.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.meetings.entity.Key;
import com.example.meetings.entity.Pic;

import java.util.List;

public interface PicService extends IService<Pic> {
    // all
    public List<Pic> getAll(Integer orderId);


    // 添加
    public boolean add(Integer orderId, String link);

    // 删除
    public boolean del(Integer id);


    // 获取OrderId的所有图片
    public List<Pic> getByOrderId(Integer orderId);



}
