package com.example.meetings.controller;

import com.example.meetings.entity.User;
import com.example.meetings.service.UserService;
import com.example.meetings.utils.JWT.JWTUtils;
import com.example.meetings.utils.Result;
import com.example.meetings.utils.WeChatOpenIdRetriever;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    // 测试token是否过期
    @GetMapping("/token")
    public Boolean token(@RequestParam("token") String token) {
        return true;
    }

    // 登录
    @GetMapping("/login")
    public Map<String, Object> login(@RequestParam("ssno") String ssno, @RequestParam("pwd") String pwd) {
//        log.info("学工名：[{}]",ssno);
//        log.info("密码：[{}]",pwd);
        System.out.println("学工名:" + ssno);
        System.out.println("密码:" + pwd);
        User user = new User();
        user.setSsno(ssno);
        user.setPwd(pwd);
        User u = userService.findBySsno(ssno);

        Map<String, Object> map = new HashMap<>();
        if(u == null) {
            System.out.println("用户不存在");
            map.put("msg","用户不存在");
            return map;
        }
        if(u.getPriority() == -1) {
            System.out.println("用户未审核");
            map.put("msg","用户未审核");
            return map;
        }
        try {
            User userDB = userService.login(user);
            Map<String,String> payload = new HashMap<>();
            //用户登录成功后的信息放入payload
            payload.put("id", String.valueOf(userDB.getId()));
            payload.put("username",userDB.getSsno());
            //生成JWT令牌
            String token = JWTUtils.getToken(payload);
            map.put("state",true);
            map.put("token",token);
            map.put("msg","认证成功");
        }catch (Exception e){
            map.put("state",false);
            map.put("msg",e.getMessage());
        }
        return map;
    }

    // 用户列表
    @GetMapping("/getAllUser")
    public List<User> getAllUser(@RequestParam("admin") String adminSsnos) {
        if ( userService.getUserPri(adminSsnos) != 1 ) {
            System.out.println("what");
            return null;
        }
        System.out.println("this");
        return userService.getAllUser();
    }

    // 获取不同权限用户列表
//    @PostMapping("/getPriUser")
//    public List<User> getPriUser(@RequestParam("pri") Integer pri) {
//        return userService.getPriUser(pri);
//    }

    // 添加或者更新用户
    @GetMapping("/addOrUpdate")
    public boolean addOrUpdate(@RequestParam("id") int id, @RequestParam("ssno") String ssno, @RequestParam("wxId") String wxId,
                               @RequestParam("pri") int pri, @RequestParam("name") String name,
                               @RequestParam("pwd") String pwd,@RequestParam("admin") String adminSsnos, @RequestParam("openid")String openid) {
        User user = new User();
        user.setId(id);
        user.setSsno(ssno);
        user.setWxId(wxId);
        user.setPriority(pri);
        user.setName(name);
        user.setPwd(pwd);
        user.setOpenid(openid);

        // 用户存在
        User user1 = userService.getById(id);
        if(user1 == null) {
            return false;
        }
        // 管理员审核
        if ( userService.getUserPri(adminSsnos) == 1 ) {
            return userService.addOrUpdateUser(user);
        }

        // 用户自己修改自己的信息
        if(adminSsnos.equals(user1.getSsno())) {
            user.setPriority(user1.getPriority());
            return userService.addOrUpdateUser(user);
        }
        return false;
    }

//     按照学工号查询用户
    @PostMapping("/getOne")
    public User getOne(@RequestParam String userSsno) {
        return userService.findBySsno(userSsno);
    }

    // 设置权限
//    @GetMapping("/setPri")
//    public boolean setPri(@RequestBody User user) {
//        return userService.setPri(user);
//    }

    // 注册
    @PostMapping("/register")
    public Result register(@RequestParam("id") int id, @RequestParam("ssno") String ssno, @RequestParam("wxId") String wxId,
                           @RequestParam("pri") int pri, @RequestParam("name") String name,
                           @RequestParam("pwd") String pwd, @RequestParam("openid")String openid,
                           @RequestParam("code") String code) {
        if(ssno == null || name == null || pwd == null || wxId == null) {
            return Result.fail("信息不完整");
            //            return "信息不完整";
        }

        if(ssno.length() == 0 || name.length() == 0 || pwd.length() == 0 || wxId.length() == 0) {
            return Result.fail("信息不完整");
//            return "信息不完整";
        }
        User user = new User();
        user.setId(id);
        user.setSsno(ssno);
        user.setWxId(wxId);
        user.setPriority(pri);
        user.setName(name);
        user.setPwd(pwd);
        openid = null;

        try {
            openid = WeChatOpenIdRetriever.getOpenId(code);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        user.setOpenid(openid);
        return userService.register(user);
    }

    /**
     * /user/getAdminOpenIds
     * @return
     */
    @GetMapping("/getAdminOpenIds")
    public List<String> getAdminOpenIds() {
        return userService.getAdminOpenId();
    }

    /**
     * /user/getUserOpenId
     * @param ssno
     * @return
     */
    @GetMapping("/getUserOpenId")
    public String getUserOpenId(@RequestParam String ssno) {
        return userService.getUserOpenId(ssno);
    }



    @GetMapping("/del")
    public boolean del(@RequestParam String ssno, @RequestParam String adminSsnos) {
        User u = userService.findBySsno(adminSsnos);
        // 用户不存在
        if(u == null) {
            return false;
        }
        // 是本人操作
        if(ssno.equals(u.getSsno())) {
            return userService.removeById(u);
        }
        // 管理员操作
        if ( userService.getUserPri(adminSsnos) != 1 ) {
            return false;
        }
        u = userService.findBySsno(ssno);
        if(u == null) {
            return false;
        }
        return userService.removeById(u);
    }


}
