package com.example.meetings.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.meetings.entity.TemplateData;
import com.example.meetings.entity.User;
import com.example.meetings.entity.WxMssVo;
import com.example.meetings.mapper.UserMapper;
import com.example.meetings.service.UserService;
import com.example.meetings.service.WxSerivice;
import com.example.meetings.utils.Result;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private WxSerivice wxSerivice;


    @Override
    public List<User> getAllUser() {
        return this.list();
    }

    @Override
    // 得到所有管理员
    public List<User> getPriUser(Integer pri) {
        return lambdaQuery().eq(pri!=null,User::getPriority, pri).list();
    }

    @Override
    public boolean addOrUpdateUser(User user) {
        if(user.getId()==null) {
            // 新增
            return this.save(user);
        } else {
            return this.updateById(user);
        }
    }

    @Override
    public User findBySsno(String userSsno) {
        return lambdaQuery().eq(userSsno!=null, User::getSsno, userSsno).one();
    }

    @Override
    public User login(User user) {
        if(user.getSsno()==null || user.getPwd()==null) {
            return null;
        }

        User newUser = lambdaQuery().eq(user.getSsno()!=null, User::getSsno, user.getSsno()).one();
        if(newUser == null) {
            return null;
        }

        if(newUser.getPriority() < 0) {
            return null;
        }

        if(newUser.getPwd().equals(user.getPwd())) {
            return newUser;
        } else {
            return null;
        }
    }

    private List<String> getStrings(String ssno, String name) {
        List<String> res = new ArrayList<>();
        String accessToken = wxSerivice.getWxAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="+accessToken;
        // https://api. weixin. qq. com/cgi-bin/message/subscribe/send?access_token=
        // https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=
        List<String> adminOpenIds = this.getAdminOpenId();
        for(int i=0; i<adminOpenIds.size(); i++) {
            String toUser = adminOpenIds.get(i);
            WxMssVo wxMssVo = new WxMssVo();
            wxMssVo.setTemplate_id("wlT4yguSNMvuhtjH6zSIfu24U8wh3UqY4thbeqchTUU");
            wxMssVo.setTouser(toUser);
            wxMssVo.setPage("pages/my/register/index");

            Map<String, TemplateData> m = new HashMap<>();
            m.put("thing2", new TemplateData(name));
            m.put("thing5", new TemplateData(ssno));

            wxMssVo.setData(m);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, wxMssVo, String.class);
            res.add(stringResponseEntity.getBody());
        }
        return res;
    }

    @Override
    public Result  register(User user) {
        String ssno = user.getSsno();
        if(ssno == null) {

            return Result.fail("学号不能为空");
            //            return "学号不能为空";
        }

        User u = lambdaQuery().eq(ssno !=null, User::getSsno, ssno).one();
        if(u != null) {
            return Result.fail("学工号已经被注册");
//            return "学工号已经被注册";
        }

        if(user.getPwd() == null) {
            return Result.fail("密码不能为空");
//            return "密码不能为空";
        }
        if(ssno.substring(0, 3).equals("702") ||
                ((ssno.substring(0, 2).equals("SX")
                || ssno.substring(0, 2).equals("SF")
                || ssno.substring(0, 2).equals("SZ")
                || ssno.substring(0, 2).equals("SL")
                || ssno.substring(0, 2).equals("BZ")
                || ssno.substring(0, 2).equals("BX"))
                && ssno.substring(4, 6).equals("10")
                )
        ) {
            user.setPriority(0);
            this.save(user);
            return Result.ok("注册成功，账号可以正常使用");
        } else {
            user.setPriority(-1);
            // 发送消息
        }
        this.save(user);
        this.getStrings(ssno, user.getName());
        return Result.ok("注册成功，等待管理员审核");
//        return "注册成功，等待管理员审核";
    }

    @Override
    public boolean setPri(User user) {
        if(user.getSsno() == null || user.getPriority() == null) {
            return false;
        }
        return this.updateById(user);
    }

    @Override
    public int getUserPri(String ssno) {
        User user = lambdaQuery().eq(ssno!=null, User::getSsno, ssno).one();

        if(user == null) {
            System.out.println("------------"+ssno);
            return -1;
        }
        return user.getPriority();
    }

    @Override
    public List<String> getAdminOpenId() {
        List<User> users = lambdaQuery().eq(User::getPriority, 1).list();
        List<String> openIds = new ArrayList<>();
        for(User user: users) {
            if(user.getOpenid() != null) openIds.add(user.getOpenid());
        }
        return openIds;
    }

    @Override
    public String getUserOpenId(String ssno) {
        return lambdaQuery().eq(ssno!=null, User::getSsno, ssno).one().getOpenid();
    }


}
