package com.glg204.wothome.webofthings.service;

import com.glg204.wothome.webofthings.dao.ThingDAO;
import com.glg204.wothome.webofthings.dto.ThingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThingServiceImpl implements ThingService{

    @Autowired
    ThingDAO thingDAO;

    @Autowired
    ThingDTOMapper thingDTOMapper;

    @Override
    public List<ThingDTO> getThings() {
        return thingDAO.getThings().stream().map(thing -> thingDTOMapper.toDTO(thing)).toList();
    }
}
