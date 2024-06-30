package com.example.meetings.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.meetings.entity.Building;
import com.example.meetings.entity.Zone;
import com.example.meetings.mapper.BuildingMapper;
import com.example.meetings.mapper.ZoneMapper;
import com.example.meetings.service.BuildingService;
import com.example.meetings.service.ZoneService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneServiceImpl extends ServiceImpl<ZoneMapper, Zone> implements ZoneService {
    @Override
    public List<Zone> getAll() {
        return this.list();
    }

    @Override
    public boolean addOrUpdate(Zone zone) {
        if(zone.getId() == null) {
            return this.save(zone);
        } else {
            return this.updateById(zone);
        }
    }

    @Override
    public boolean del(Zone zone) {
        return this.removeById(zone);
    }
}
