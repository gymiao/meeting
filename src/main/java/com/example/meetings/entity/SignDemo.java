package com.example.meetings.entity;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;

public class SignDemo {

    private static final String DEFAULT_ENCODING = "UTF-8";

    private static final String EXPIRATION_DATE_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private static final TimeZone GMT_TIMEZONE = TimeZone.getTimeZone("GMT");

    private static final long DEFAULT_EXPIRE_SECONDS = 300;

    private String ak="4ZSCQYV7YP45CNJGWR86";

    private String sk="Dj98q1FOU7n2C2oLbHpj7O3zUO4TJYJWDM7ncXS6";

    private String join(List<?> items) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i).toString();
            sb.append(item);
            if (i < items.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public String stringToSign(String[] tmpConditions, String expiration) {
        List<String> conditions = new ArrayList<>();
        Collections.addAll(conditions, tmpConditions);
        return "{\"expiration\":" + "\"" + expiration + "\"," + "\"conditions\":[" + join(conditions) + "]}";
    }

    public String getFormatExpiration(Date requestDate, long expires) {
        requestDate = requestDate != null ? requestDate : new Date();
        SimpleDateFormat expirationDateFormat = new SimpleDateFormat(EXPIRATION_DATE_FORMATTER);
        expirationDateFormat.setTimeZone(GMT_TIMEZONE);
        Date expiryDate = new Date(requestDate.getTime() + (expires <= 0 ? DEFAULT_EXPIRE_SECONDS : expires) * 1000);
        return expirationDateFormat.format(expiryDate);
    }

    public String postSignature(String policy) throws Exception {
        byte[] policyBase64 = Base64.getEncoder().encode(policy.getBytes(DEFAULT_ENCODING));
        SecretKeySpec signingKey = new SecretKeySpec(this.sk.getBytes(DEFAULT_ENCODING), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        return Base64.getEncoder().encodeToString(mac.doFinal(policyBase64));
    }

//    public static void main(String[] args) throws Exception {
//
//        String signature = demo.postSignature(policy);
//
//        // 表单中携带AccessKeyId、policy、signature的签名
//        System.out.println("authExpiration=" + authExpiration);
//        System.out.println("policy=" + policy);
//        System.out.println("policyBase64=" + policyBase64);
//        System.out.println("signature=" + signature);
//
//        // 表单中携带token的签名
//        System.out.println("token=" + demo.ak + ":" + signature + ":" + policyBase64);
//    }
}
