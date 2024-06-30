package com.example.meetings.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.meetings.entity.Building;
import com.example.meetings.entity.Zone;
import com.example.meetings.mapper.BuildingMapper;
import com.example.meetings.service.BuildingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building> implements BuildingService {
    @Override
    public List<Building> getAll() {
        return this.list();
    }

    @Override
    public boolean addOrUpdate(Building building) {

        if(building.getId() == null) {
            return this.save(building);
        } else {
            return this.updateById(building);
        }
    }

    @Override
    public boolean delBuilding(Building building) {
        return this.removeById(building);
    }

    @Override
    public List<Building> getByZone(Zone zone) {
        return lambdaQuery().eq(zone.getZoneId()!=null, Building::getZoneId, zone.getZoneId()).list();
    }
}
