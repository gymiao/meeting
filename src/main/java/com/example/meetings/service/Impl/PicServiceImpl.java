package com.example.meetings.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.meetings.entity.Key;
import com.example.meetings.entity.Pic;
import com.example.meetings.mapper.KeyMapper;
import com.example.meetings.mapper.PicMapper;
import com.example.meetings.service.KeyService;
import com.example.meetings.service.PicService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PicServiceImpl extends ServiceImpl<PicMapper, Pic> implements PicService {


    @Override
    public List<Pic> getAll(Integer orderId) {
        return lambdaQuery().eq(orderId != null, Pic::getOrderId, orderId).list();
    }

    @Override
    public boolean add(Integer orderId, String link) {
        Pic pic = new Pic();
        pic.setOrderId(orderId);
        pic.setLink(link);
        return this.save(pic);
    }

    @Override
    public boolean del(Integer id) {
        return this.removeById(id);
    }

    @Override
    public List<Pic> getByOrderId(Integer orderId) {
        return lambdaQuery().eq(orderId != null, Pic::getOrderId, orderId).list();
    }
}
