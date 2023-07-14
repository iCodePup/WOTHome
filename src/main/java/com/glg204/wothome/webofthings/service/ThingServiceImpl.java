package com.glg204.wothome.webofthings.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThingServiceImpl implements ThingService{

    @Autowired
    JMDNSDiscoverService jmdnsDiscoverService;

}
