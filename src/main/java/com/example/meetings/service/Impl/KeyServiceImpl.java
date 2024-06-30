package com.example.meetings.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.meetings.entity.Building;
import com.example.meetings.entity.Key;
import com.example.meetings.entity.Room;
import com.example.meetings.entity.User;
import com.example.meetings.mapper.BuildingMapper;
import com.example.meetings.mapper.KeyMapper;
import com.example.meetings.service.BuildingService;
import com.example.meetings.service.KeyService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class KeyServiceImpl extends ServiceImpl<KeyMapper, Key> implements KeyService {

    public void updateStatus() {
        String currentDate = "" + new Date().getTime();
        List<Key> keys = this.list();
        for(Key key:keys) {
            if(currentDate.compareTo(key.getEndDate())>=0) {
                key.setStatus(0);
                this.updateById(key);
            }
        }
    }

    @Override
    public List<Key> getAll() {
        updateStatus();
        return this.list();
    }

    @Override
    public List<Key> getOne(String ssno) {
        updateStatus();
        return lambdaQuery().eq(ssno!=null, Key::getSsno, ssno).list();
    }

    @Override
    public boolean addOrUpdate(Key key) {
        if(key.getId() == null) {
            return this.save(key);
        } else {
            return this.updateById(key);
        }
    }

    @Override
    public boolean del(Key key) {
        return this.removeById(key);
    }

    @Override
    public Key getOne(Key key) {
        if(key.getId() == null) {
            return null;
        }
        return this.getById(key.getId());
    }


}
