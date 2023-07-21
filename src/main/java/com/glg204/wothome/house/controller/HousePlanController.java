package com.glg204.wothome.house.controller;


import com.glg204.wothome.house.dto.RoomDTO;
import com.glg204.wothome.house.service.HousePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/houseplan")
public class HousePlanController {

    @Autowired
    HousePlanService housePlanService;

    @GetMapping("/room")
    public ResponseEntity<List<RoomDTO>> getRooms(Principal principal) {
        List<RoomDTO> roomDTOList = housePlanService.getRooms(principal);
        return ResponseEntity.ok(roomDTOList);
    }
}
