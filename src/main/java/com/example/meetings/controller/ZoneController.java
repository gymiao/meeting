package com.example.meetings.controller;

import com.example.meetings.entity.Zone;
import com.example.meetings.service.UserService;
import com.example.meetings.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zone")
public class ZoneController {
    @Autowired
    ZoneService zoneService;

    @Autowired
    UserService userService;

    @GetMapping("/getAll")
    public List<Zone> getAll() {
        return zoneService.getAll();
    }

    @PostMapping("/addOrUpdate")
    public boolean addorupdate(@RequestBody Zone zone,@RequestParam String adminSsnos) {
        if ( userService.getUserPri(adminSsnos) != 1 ) {
            return false;
        }
        return zoneService.addOrUpdate(zone);
    }

    @PostMapping("/del")
    public boolean del(@RequestParam Integer id,@RequestParam String adminSsnos) {
        Zone zone = new Zone();
        zone.setId(id);
        if ( userService.getUserPri(adminSsnos) != 1 ) {
            return false;
        }
        return zoneService.removeById(zone );
    }
}
