package com.example.meetings.mapper;

import com.example.meetings.entity.Room;
import com.example.meetings.entity.User;
import com.example.meetings.service.RoomService;
import com.example.meetings.service.UserService;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {
    @Autowired
    private RoomService roomService;

    @Test
    public void select() {

    }
}
