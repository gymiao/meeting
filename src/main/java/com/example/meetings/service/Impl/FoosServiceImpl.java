package com.example.meetings.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.meetings.entity.Building;
import com.example.meetings.entity.Foods;
import com.example.meetings.entity.Room;
import com.example.meetings.mapper.FoodMapper;
import com.example.meetings.mapper.RoomMapper;
import com.example.meetings.service.FoodsService;
import com.example.meetings.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoosServiceImpl extends ServiceImpl<FoodMapper, Foods> implements FoodsService {

    @Override
    public Boolean addOrUpdateFoods(Foods foods) {
        if (foods.getId() != null) {
            return updateById(foods);
        }

        return this.save(foods);
    }

    @Override
    public Foods getFoodsByOrderId(Integer orderId) {
        return lambdaQuery().eq(Foods::getOrderId, orderId).one();
    }
}
