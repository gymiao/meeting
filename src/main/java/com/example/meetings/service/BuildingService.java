package com.example.meetings.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.meetings.entity.Building;
import com.example.meetings.entity.Zone;

import java.util.List;

public interface BuildingService extends IService<Building> {
    // 获取所有
    public List<Building> getAll();

    // 添加或者修改
    public boolean addOrUpdate(Building building);

    // 删除
    public boolean delBuilding(Building building);

    public List<Building> getByZone(Zone zone);
}
