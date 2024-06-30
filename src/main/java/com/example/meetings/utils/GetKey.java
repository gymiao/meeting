package com.example.meetings.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

public class GetKey {
    public static String getKey(String roomId,String startTime ,String endDate) {
        RestTemplate restTemplate=new RestTemplate();
        String token = "A47E152DA1B56EC0BBC12BCA3DAECE2112591BF76CA4E01BB72A6E008A698DCA";
//        String roomId = "1028702056070213";
        //        String endDate = "2023 12 25 21 12 6";


        String url = "https://iot.nuaa.edu.cn/istp/api/access_auth_pwd/"+token+"/"+roomId +"/rwApply/"+startTime+"/"+endDate;
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(url, null, String.class);
        String body=responseEntity.getBody();
        int idx = body.indexOf("pwdText");
        if(idx != -1) {
            return body.substring(idx+10, idx+16);
        }
        return body;
    }
}
