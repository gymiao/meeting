package com.example.meetings.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.meetings.entity.Building;
import com.example.meetings.entity.Room;
import com.example.meetings.mapper.RoomMapper;
import com.example.meetings.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {
    @Override
    public List<Room> getAllRoom() {
        return this.list();
    }

    @Override
    public boolean addOrUpdate(Room room) {
        if(room.getId() == null) {
            return this.save(room);
        } else {
            return this.updateById(room);
        }
    }





    @Override
    public Room getOne(String roomId) {
        return lambdaQuery().eq(roomId!=null, Room::getRoomId, roomId).one();
    }

    @Override
    public List<Room> getByBuilding(Building building) {
        return lambdaQuery().eq(building.getBuildingId()!=null, Room::getBuildingId, building.getBuildingId()).list();
    }

    @Override
    public Room getOneById(String roomId) {
        return lambdaQuery().eq(roomId!=null, Room::getRoomId, roomId).one();
    }


}
