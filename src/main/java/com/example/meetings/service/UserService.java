package com.example.meetings.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.meetings.entity.User;
import com.example.meetings.utils.Result;

import java.util.List;

public interface UserService extends IService<User> {

    // 获取所有User
    List<User> getAllUser();

    // 获取对应身份的User
    List<User> getPriUser(Integer pri);

    // 新增用户
    boolean addOrUpdateUser(User user);

    // 按照学号查找
    User findBySsno(String userSsno);

    // 登录
    User login(User user);

    // 注册用户
    Result register(User user);

    boolean setPri(User user);

    int getUserPri(String ssno);

    // 获取所有管理员的openId
    List<String> getAdminOpenId();

    // 获取用户的openId
    String getUserOpenId(String ssno);


}
