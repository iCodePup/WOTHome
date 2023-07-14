package com.glg204.wothome.webofthings.controller;

import com.glg204.wothome.webofthings.service.ThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thing/discover")
public class ThingController {

    @Autowired
    ThingService thingService;

}
