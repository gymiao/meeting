package com.example.meetings.controller;

import cn.hutool.http.HttpUtil;
import com.example.meetings.entity.TemplateData;
import com.example.meetings.entity.WxMssVo;
import com.example.meetings.service.UserService;
import com.example.meetings.service.WxSerivice;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx")
public class WxController {
    @Autowired
    WxSerivice wxSerivice;

    @Autowired
    UserService userService;

    /**
     * /wx/sndMsg
     * @param data
     * @return
     */
    @GetMapping("/sndMsg1")
    public String sendMsg1(@RequestParam("data") String data) {
        return wxSerivice.sendMsg(data);
    }

    @PostMapping("/sndMsg")
    public String sendMsg(@RequestBody String data) {
        return wxSerivice.sendMsg(data);
    }


    @GetMapping("/subRegToAdmins")
    public List<String> solveRegister(@RequestParam("ssno") String ssno, @RequestParam("name") String name) {
        if(ssno == null || name == null) {
            return null;
        }
        List<String> res = getStrings(ssno, name);

        return res;
    }

    private List<String> getStrings(String ssno, String name) {
        List<String> res = new ArrayList<>();
        String accessToken = wxSerivice.getWxAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="+accessToken;
        // https://api. weixin. qq. com/cgi-bin/message/subscribe/send?access_token=
        // https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=
        List<String> adminOpenIds = userService.getAdminOpenId();
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


    /**
         * /wx/subRegToAdmins
         * @param ssno 自己的学号
         * @param name 自己的姓名
         * @return
         */
    @GetMapping("/subRegToAdmins1")
    public List<String> solveRegister1(@RequestParam("ssno") String ssno, @RequestParam("name") String name) {


        List<String> adminOpenIds = userService.getAdminOpenId();
        List<String> res = new ArrayList<>();
        for(int i=0; i<adminOpenIds.size(); i++) {
            String toUser = adminOpenIds.get(i);
            if(toUser != null) {
                String messageStr = "{\n" +

                        "   \"template_id\" : \"wlT4yguSNMvuhtjH6zSIfu24U8wh3UqY4thbeqchTUU\",\n" +
                        "   \"page\" : \"pages/my/register/index\",\n" +
                        "   \"touser\" : \""+toUser+"\",\n" +
                        "   \"data\" : {\n" +
                            "   \"thing2\" : {\n" +
                            "   \"value\" : \""+name+"\",\n" +
                            "    },\n" +
                            "   \"thing5\" : {\n" +
                            "   \"value\" : \""+ssno+"\",\n" +
                            "    },\n" +
                        "    },\n" +
                        "   \"miniprogram_state\" : \"formal\",\n" +
                        "   \"lang\" : \"zh_CN\",\n" +
                        "}";
                res.add(wxSerivice.sendMsg(messageStr)) ;
            }
        }
        return res;
    }


    /**
     * /wx/getaccessToken
     * @return
     */
    @GetMapping("/getaccessToken")
    public String getAccessToken() {
        return wxSerivice.getWxAccessToken();
    }

    @GetMapping("/subRegToUser")
    public String solveRegisterAdmin(@RequestParam("ssno") String ssno, @RequestParam("name") String name, @RequestParam("status") String status) {



        String toUser = userService.getUserOpenId(ssno);
        if(toUser == null) {
            return "用户不存在";
        }
        String templateId="WbH2vIcHCW_AxA4o_NuRt2eMbvVU3gPASodG4xAR-_8"; // 模板id
        String appId="wx1ca07d825d95e185";  // 小程序appid

        String messageStr = "{\n" +
                "   \"touser\" : \""+toUser+"\",\n" +


                "   \"msgtype\" : \"template_msg\",\n" +

                "   \"template_msg\" : {\n" +
                "        \"template_id\": \""+templateId+ "\",\n" +
                "        \"url\": \"http://www.qq.com\",\n" +
                "\t\t\"miniprogram\":\n" +
                "\t\t{\n" +
                "\t\t\t\"appid\":\""+appId+"\",\n" +
                "\t\t\t\"pagepath\":\"/index.html\"\n" +
                "\t\t},\n" +
                "        \"content_item\": [\n" +
                "            {\n" +
                "                \"key\": \"提交人\",\n" +
                "                \"value\": \""+ssno+"\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"key\": \"姓名\",\n" +
                "                \"value\": \""+name+"\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"key\": \"提交类型\",\n" +
                "                \"value\": \""+status+"\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "}";
        return wxSerivice.sendMsg(messageStr);
    }

    /**
     * 用户预约后发送信息给管理员
     * @param name 用户的姓名
     * @param room 会议室
     * @param time 预约时间
     * @param phone 联系电话
     * @param topic 会议名称
     * @param ssno 管理员的学号
     * @return
     */
    @GetMapping("/subOrderToAdmin")
    public String solveOrder(@RequestParam("name") String name, @RequestParam("room") String room,
                             @RequestParam("time") String time, @RequestParam("phone") String phone,
                             @RequestParam("topic") String topic, @RequestParam("ssno") String ssno) {


        String toUser = userService.getUserOpenId(ssno);
        if(toUser == null) {
            return "用户不存在";
        }
        String templateId="FZ71ocYKhV_3a_DS9CR0yqpxEP3C4wOAPD-VfChC2IE"; // 模板id
        String appId="wx1ca07d825d95e185";  // 小程序appid

        String messageStr = "{\n" +
                "   \"touser\" : \""+toUser+"\",\n" +


                "   \"msgtype\" : \"template_msg\",\n" +

                "   \"template_msg\" : {\n" +
                "        \"template_id\": \""+templateId+ "\",\n" +
                "        \"url\": \"http://www.qq.com\",\n" +
                "\t\t\"miniprogram\":\n" +
                "\t\t{\n" +
                "\t\t\t\"appid\":\""+appId+"\",\n" +
                "\t\t\t\"pagepath\":\"/index.html\"\n" +
                "\t\t},\n" +
                "        \"content_item\": [\n" +
                "            {\n" +
                "                \"key\": \"预约人\",\n" +
                "                \"value\": \""+name+"\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"key\": \"预约地点\",\n" +
                "                \"value\": \""+room+"\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"key\": \"预约时间\",\n" +
                "                \"value\": \""+time+"\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"key\": \"联系电话\",\n" +
                "                \"value\": \""+phone+"\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"key\": \"会议名称\",\n" +
                "                \"value\": \""+topic+"\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "}";
        return wxSerivice.sendMsg(messageStr);
    }

    /**
     * 管理员审核后发送信息给用户
     * @param ssno 用户的学号
     * @param topic 会议名称
     * @param room  会议室
     * @param name 用户的姓名
     * @param time 预约时间
     * @param status 审核状态
     * @return
     */
    @GetMapping("/subOrderToUser")
    public String solveOrderAdmin(@RequestParam("ssno") String ssno, @RequestParam("topic") String topic,
                                  @RequestParam("room") String room, @RequestParam("name") String name,
                                  @RequestParam("time") String time, @RequestParam("status") String status) {


        String toUser = userService.getUserOpenId(ssno);
        if(toUser == null) {
            return "用户不存在";
        }
        String templateId="WbH2vIcHCW_AxA4o_NuRt1qLGF4lvweCuNiSCswFSsg"; // 模板id
        String appId="wx1ca07d825d95e185";  // 小程序appid

        String messageStr = "{\n" +
                "   \"touser\" : \""+toUser+"\",\n" +


                "   \"msgtype\" : \"template_msg\",\n" +

                "   \"template_msg\" : {\n" +
                "        \"template_id\": \""+templateId+ "\",\n" +
                "        \"url\": \"http://www.qq.com\",\n" +
                "\t\t\"miniprogram\":\n" +
                "\t\t{\n" +
                "\t\t\t\"appid\":\""+appId+"\",\n" +
                "\t\t\t\"pagepath\":\"/index.html\"\n" +
                "\t\t},\n" +
                "        \"content_item\": [\n" +
                "            {\n" +
                "                \"key\": \"活动名称\",\n" +
                "                \"value\": \""+topic+"\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"key\": \"地址\",\n" +
                "                \"value\": \""+room+"\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"key\": \"姓名\",\n" +
                "                \"value\": \""+name+"\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"key\": \"预约时间\",\n" +
                "                \"value\": \""+time+"\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"key\": \"审核状态\",\n" +
                "                \"value\": \""+status+"\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "}";
        return wxSerivice.sendMsg(messageStr);
    }

}
