package com.example.meetings.controller;

import com.example.meetings.entity.Key;
import com.example.meetings.entity.User;
import com.example.meetings.service.KeyService;
import com.example.meetings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/key")
public class KeyController {

    @Autowired
    KeyService keyService;

    @Autowired
    UserService userService;

    // 获取所有房间
    @GetMapping("/getAll")
    public List<Key> getAll(@RequestParam("ssno") String adminSsnos) {
        if ( userService.getUserPri(adminSsnos) != 1 ) {
            return null;
        }
        return keyService.getAll();
    }

    // 获取自己的
    @GetMapping("/getOnes")
    public List<Key> getOne(@RequestParam("ssno") String ssno) {
        if(ssno == null) {
            return null;
        }
        return keyService.getOne(ssno);
    }

    /**
     * /key/del
     * @param keyId
     * @param ssno
     * @return
     */
    @GetMapping("/del")
    public boolean del(@RequestParam Integer keyId, @RequestParam String ssno) {
        Key key = new Key();
        key.setId(keyId);
        key = keyService.getOne(key);
        User u = userService.findBySsno(ssno);
        if(u == null) {
            return false;
        }
        if(u.getPriority() == 1) {
            return keyService.removeById(key);
        }
        if(!key.getSsno().equals(ssno)) {
            return false;
        }
        return keyService.removeById(key);
    }
}
