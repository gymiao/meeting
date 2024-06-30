package com.example.meetings.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.meetings.entity.User;
import com.example.meetings.entity.Zone;

public interface ZoneMapper extends BaseMapper<Zone> {
    public User findById(Integer id);
}
