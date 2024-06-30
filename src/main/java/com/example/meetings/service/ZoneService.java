package com.example.meetings.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.meetings.entity.Zone;

import java.util.List;

public interface ZoneService extends IService<Zone> {
    // 获取所有请求
    public List<Zone> getAll();

    // 添加或者修改请求
    public boolean addOrUpdate(Zone zone);

    // 删除请求
    public boolean del(Zone zone);
}
