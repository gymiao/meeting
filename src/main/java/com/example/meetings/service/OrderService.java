package com.example.meetings.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.meetings.entity.Order;
import com.example.meetings.entity.Room;

import java.util.List;

public interface OrderService extends IService<Order> {
    public List<Order> getByTimeAndRoom(Room room);

    public List<Order> getAllOccupy();

    public Order getOrderId(int orderId);

    public boolean addOrUpdate(Order order);

    public boolean del(Order order);

    public boolean isAvi(String startTime, String endTime, Order order);

    // 获取某个用户的所有预约
    public List<Order> getByUserId(String userSsno);

    // 获取某个管理员的所有预约
    public List<Order> getByAdminId(String adminSsno);

    // 获取某个房间的所有预约
    public List<Order> getByRoomId(String roomId);

    public boolean isExsit(Order order);

    public List<Order> summaryUser(String userSsno, String startTime, String endTime);

    public List<Order> summaryAdmin( String startTime, String endTime);

    String orderSummary(String userSsno, String startTime, String endTime);

    String outputPerson(Integer orderId);


//    public boolean isExsit(String startTime, String endTime, String roomId, String userSsno);
}
