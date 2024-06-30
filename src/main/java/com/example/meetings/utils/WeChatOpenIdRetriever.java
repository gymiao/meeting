package com.example.meetings.utils;


import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;

public class WeChatOpenIdRetriever {

    public static String getOpenId(String code) throws IOException {
        // 替换为您的小程序的 AppID 和 AppSecret
        String appId = "wx1ca07d825d95e185";
        String appSecret = "697e16ccba36dc7af8573921f1aad23d";

        // 构建微信开放平台的接口 URL
        String url = "https://api.weixin.qq.com/sns/jscode2session" +
                "?appid=" + appId +
                "&secret=" + appSecret +
                "&js_code=" + code +
                "&grant_type=authorization_code";

        // 发送 HTTP 请求
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        // 读取响应内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // 解析 JSON 响应，提取 openid
        // 注意：实际中建议使用 JSON 解析库，如 Jackson 或 Gson
        String responseBody = response.toString();
        System.out.println("responseBody: " + responseBody);
        String openid = parseOpenIdFromJson(responseBody);

        return openid;
    }

    private static String parseOpenIdFromJson(String json) {
        // 这里是一个简单的示例，实际上您应该使用 JSON 解析库来提取字段
        // 注意：微信开放平台返回的 JSON 中可能包含其他有用的信息，具体取决于您的需求
        return json.contains("openid") ? json.split("\"openid\":\"")[1].split("\"")[0] : null;
    }

    public static void main(String[] args) {
        try {
            String code = "your_code"; // 替换为前端传递过来的 code
            String openid = getOpenId(code);
            System.out.println("用户的openid：" + openid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}