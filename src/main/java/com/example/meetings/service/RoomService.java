package com.example.meetings.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.meetings.entity.Building;
import com.example.meetings.entity.Room;

import java.util.List;

public interface RoomService extends IService<Room> {
    // 获取所有房间
    List<Room> getAllRoom();

    // 添加或更新房间
    boolean addOrUpdate(Room room);


    // 根据房间Id获取
    Room getOne(String roomId);

    // 根据building获得
    List<Room> getByBuilding(Building building);

    // 根据房间Id获取
    Room getOneById(String roomId);
}
