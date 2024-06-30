package com.example.meetings.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.meetings.entity.Key;
import com.example.meetings.entity.User;

import java.util.List;

public interface KeyService extends IService<Key> {
    // all
    public List<Key> getAll();

    // 获取自己的
    public List<Key> getOne(String ssno);

    // 添加
    public boolean addOrUpdate(Key key);

    // 删除
    public boolean del(Key key);

    // 是否过期
    public Key getOne(Key key);


}
