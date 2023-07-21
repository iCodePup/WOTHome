package com.glg204.wothome.webofthings.controller;

import com.glg204.wothome.webofthings.dto.ThingDTO;
import com.glg204.wothome.webofthings.service.ThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/things")
public class ThingController {

    @Autowired
    ThingService thingService;

    @GetMapping("")
    public ResponseEntity<List<ThingDTO>> getThings() {
        List<ThingDTO> thingDTOList = thingService.getThings();
        return ResponseEntity.ok(thingDTOList);
    }

}
