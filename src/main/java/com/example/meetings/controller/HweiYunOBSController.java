package com.example.meetings.controller;


import com.obs.services.ObsClient;
import com.obs.services.model.*;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: ObsController
 * @Description: OBS服务器Controller
 * @Author: wuhuiju
 * @Version: 1.0
 */
@RestController
@RequestMapping({ "/oss" })
public class HweiYunOBSController {

    /**
     * 完整域名为/oss/policy
     * 返回结果
     formParams.put("x-obs-acl", "public-read");
     formParams.put("content-type", "text/plain");
     formParams.put("accessId", accessId);
     formParams.put("policy", response.getPolicy());
     formParams.put("signature", response.getSignature());
     formParams.put("dir", dir);
     formParams.put("host", host);
     */
    @CrossOrigin
    @GetMapping("/policy")
    public Map<String, Object>  policy() {
        // 访问Id
        String accessId = "4ZSCQYV7YP45CNJGWR86";

        // 访问密钥
        String accessKey = "Dj98q1FOU7n2C2oLbHpj7O3zUO4TJYJWDM7ncXS6";

        // Endpoint
        String endpoint = "obs.cn-east-3.myhuaweicloud.com";

        // 填写Bucket名称
        String bucket = "picnuaa";

        // 填写Host地址，格式为https://bucketname.endpoint。
        String host = "https://" + bucket + "." + endpoint;

        // 设置上传到OSS文件的前缀，可置空此项
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // 生成文件夹，以日期为文件夹名
        String dateform = simpleDateFormat.format(new Date());
        String dir = "pic/" + dateform + "/";

        // 创建ObsClient实例
        // 使用永久AK/SK初始化客户端
        ObsClient obsClient = new ObsClient(accessId, accessKey,endpoint);

        try {
            // 生成基于表单上传的请求
            PostSignatureRequest request = new PostSignatureRequest();

            // 返回结果
            Map<String, Object> formParams = new HashMap<String, Object>();

            // 设置对象访问权限为公共读
            formParams.put("x-obs-acl", "public-read");
            // 设置对象MIME类型
            formParams.put("content-type", "image/jpeg");
            request.setFormParams(formParams);

            // 设置表单上传请求有效期，单位：秒
            request.setExpires(3600);
            PostSignatureResponse response = obsClient.createPostSignature(request);

            formParams.put("accessId", accessId);
            formParams.put("policy", response.getPolicy());
            formParams.put("signature", response.getSignature());
            formParams.put("dir", dir);
            formParams.put("host", host);
            return formParams;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
