package com.example.meetings.controller;

import com.example.meetings.entity.Building;
import com.example.meetings.entity.Zone;
import com.example.meetings.service.BuildingService;
import com.example.meetings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/building")
public class BuildingController {

    @Autowired
    BuildingService buildingService;

    @Autowired
    UserService userService;

    @GetMapping("/getAll")
    public List<Building> getAll() {
        return buildingService.getAll();
    }

    @PostMapping("/addOrUpdate")
    public boolean addorUpdate(@RequestBody Building building, @RequestParam String adminSsnos) {

        if ( userService.getUserPri(adminSsnos) != 1 ) {
            return false;
        }
        return buildingService.addOrUpdate(building);
    }

    @PostMapping("/del")
    public boolean del(@RequestParam Integer id, @RequestParam String adminSsnos) {
        if ( userService.getUserPri(adminSsnos) != 1 ) {
            return false;
        }
        Building building = new Building();
        building.setId(id);
        return buildingService.removeById(building);
    }

    @PostMapping("/getByZoneId")
    public List<Building> getByZone(@RequestBody Zone zone) {
        return buildingService.getByZone(zone);
    }

}
