package com.example.meetings.service;

public interface WxSerivice {
    String getWxAccessToken();

    String sendMsg(String msg);

    String getPolicy();
}
