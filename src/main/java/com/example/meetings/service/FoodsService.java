package com.example.meetings.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.meetings.entity.Building;
import com.example.meetings.entity.Foods;
import com.example.meetings.entity.Room;

import java.util.List;

public interface FoodsService extends IService<Foods> {
    Boolean addOrUpdateFoods(Foods foods);

    Foods getFoodsByOrderId(Integer orderId);

}
