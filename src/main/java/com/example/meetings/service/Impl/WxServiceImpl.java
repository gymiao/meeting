package com.example.meetings.service.Impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.example.meetings.service.WxSerivice;
import lombok.val;
import org.jetbrains.annotations.TestOnly;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WxServiceImpl implements WxSerivice {

    String appId="wx1ca07d825d95e185";


    String appSecret="697e16ccba36dc7af8573921f1aad23d";

    @Override
    public String getWxAccessToken() {
        System.out.println("appId:" + appId);
        System.out.println("appSecret:" + appSecret);
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ appId +"&secret="+ appSecret;
        String result = HttpUtil.get(requestUrl);
        val responseJsonObject = JSONUtil.parseObj(result);
        if(ObjectUtil.isNull(responseJsonObject)) {
            return "响应异常";
        }
        String accessToken = responseJsonObject.getStr("access_token");
        return accessToken;
    }

    @Override
    public String sendMsg( String msg) {
        String accessToken = this.getWxAccessToken();
        // https://api. weixin. qq. com/cgi-bin/message/subscribe/send?access_token=
        // https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=
        String returnMsg = HttpUtil.post("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="+accessToken, msg);
        cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(returnMsg);
        return jsonObject.toString();
    }

    @Override
    public String getPolicy() {
        return null;
    }
}
