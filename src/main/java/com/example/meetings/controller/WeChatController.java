package com.example.meetings.controller;

import com.example.meetings.entity.Building;
import com.example.meetings.service.UserService;
import com.example.meetings.service.WxSerivice;
import com.example.meetings.utils.WeChatOpenIdRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/wechat")
public class WeChatController {
    @Autowired
    UserService userService;

    @Autowired
    WxSerivice wxSerivice;

    @GetMapping("/getOpenId")
    public String getAll(@RequestParam("code") String code) {
        try {
            String res = WeChatOpenIdRetriever.getOpenId(code);
            return res;
        } catch (IOException e) {
            return "获取失败" + e.getMessage();
        }
    }

    /**
     * /wechat/getWxToken
     * @return
     */
    @GetMapping("/getWxToken")
    public String getWxToken() {
        return wxSerivice.getWxAccessToken();
    }
}
