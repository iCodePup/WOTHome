package com.glg204.wothome.webofthings.service;

import com.glg204.wothome.webofthings.dto.ThingDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ThingService {

    List<ThingDTO> getThings();
}
