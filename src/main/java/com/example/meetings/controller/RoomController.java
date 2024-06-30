package com.example.meetings.controller;

import com.example.meetings.entity.*;
import com.example.meetings.service.*;
import com.example.meetings.utils.GetKey;
import com.example.meetings.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomService roomService;

    @Autowired
    UserService userService;

    @Autowired
    KeyService keyService;


    @Autowired
    OrderService occupyService;

    @Autowired
    FoodsService foodsService;

    // 获取房间
    @GetMapping("/getOne")
    public Room getOne(@RequestParam String roomId) {

        return roomService.getOne(roomId);
    }


    // 获取所有房间
    @GetMapping("/getAll")
    public List<Room> getAllRoom() {
        return roomService.getAllRoom();
    }

    @GetMapping("/del")
    public Boolean rmRoom(@RequestParam Integer id, @RequestParam String adminSsnos) {
        if ( userService.getUserPri(adminSsnos) != 1 ) {
            return false;
        }
        Room room = new Room();
        room.setId(id);
        return roomService.removeById(room);
    }


    // 添加或者更新房间
    @PostMapping("/addOrUpdate")
    public boolean addOrUpdate(@RequestBody Room room, @RequestParam("ssno") String adminSsnos) {
        if ( userService.getUserPri(adminSsnos) != 1 ) {
            return false;
        }
        return roomService.addOrUpdate(room);
    }

    // 申请房间
    @PostMapping("/reserveRoom")
    public Result reserveRoom(@RequestBody Order order) {

        String roomId = order.getRoomId();
        String userSsno = order.getSsno();
        Integer type = order.getType();
        String detail = order.getDetail();
        String adminSsno = order.getAdmin();
        String startTime = order.getStartTime();
        String endTime = order.getEndTime();
        int cnts = order.getCnts();
        String names = order.getNames();
        if (type == null) {
            return Result.fail("请选择会议类型");
        }
        if(cnts >0 && names == null) {
            return Result.fail("请填写订餐人员名单");
        }
        // 参数是否完整
        if(roomId == null || userSsno == null || type == null || detail == null || adminSsno == null || startTime == null || endTime == null) {
            return Result.fail("参数不完整");
        }

        long s = Long.valueOf(startTime);
        long e = Long.valueOf(endTime);
        long dur = 3*60*60*1000;
        if(e - s > dur) {
            return Result.fail("预约时间不能超过3小时");
        }

        // 房间是否存在
        Room room = roomService.getOne(roomId);
        User user = userService.findBySsno(userSsno);
        if(room == null) {
            return Result.fail("不存在该房间");
        }

        if(user == null) {
            return Result.fail("不存在该用户");
        }

        String ssno = user.getSsno();
        // 更新自己的预约信息。普通用户每次只能使用完，再去预约
//        if(ssno.substring(0, 3).equals("702") ) {
//            System.out.println("老师预约，可以预约多个");
//        }
//        // 普通用户，只能约一个
//        else if(user.getPriority() != 1) {
//            List<Order> orders = occupyService.getByUserId(userSsno);
//            int cnt = 0;
//            for(Order o : orders) {
//                if(o.getStatus() == 1) {
//                    long l = System.currentTimeMillis();
//                    String currentTime = String.valueOf(l);
//                    // 已过期
//                    if(o.getEndTime().compareTo(currentTime) <= 0) {
//                        o.setStatus(-2);
//                        occupyService.updateById(o);
//                    } else {
//                        cnt ++;
//                    }
//                }
//            }
//            if(cnt > 0) {
//                return Result.fail("你已经有预约过的房间了，请使用完后，再来预约新房间");
//            }
//        }



        // 房间是否已被占用
        if(occupyService.isAvi(startTime, endTime, order) == false) {
            return Result.fail("该房间已被占用");
        }

        // 已经预约过
        if(occupyService.isExsit(order)) {
            return Result.fail("已提交该时段预约。不可重复预约...");
        }



        if(user.getPriority() == 1) {
            // 直接申请密码
            String pwd = GetKey.getKey(room.getRoomId(), startTime, endTime);
            // 保存新的key
            Key key = new Key();
            key.setRoomId(room.getRoomId());
            key.setPwd(pwd);
            key.setSsno(user.getSsno());
            key.setStartDate(startTime);
            key.setEndDate(endTime);
            // 可以使用
            key.setStatus(1);
            keyService.addOrUpdate(key);
            order.setPicture("0");
            order.setStatus(1);
            occupyService.addOrUpdate(order);

            return Result.ok("密码申请成功！");
//                return "你是管理员，密码申请成功！";
        }



        User admin = userService.findBySsno(adminSsno);
        if(admin == null || admin.getPriority() != 1) {
            return Result.fail("没有该管理员");
        }



        order.setStatus(0);
        order.setPicture("0");
        occupyService.addOrUpdate(order);
        return Result.ok("申请已提交给管理员审核！");
    }

    // 根据buildingId获取
    @PostMapping("/getByBuildingId")
    public List<Room> getByBuilding(@RequestBody Building building) {
        return roomService.getByBuilding(building);
    }


}
