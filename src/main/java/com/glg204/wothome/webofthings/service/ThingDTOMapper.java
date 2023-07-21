package com.glg204.wothome.webofthings.service;

import com.glg204.wothome.user.domain.User;
import com.glg204.wothome.user.dto.UserDTO;
import com.glg204.wothome.user.service.UserDTOMapper;
import com.glg204.wothome.webofthings.domain.Thing;
import com.glg204.wothome.webofthings.dto.ThingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThingDTOMapper {

    @Autowired
    UserDTOMapper userDTOMapper;

    public ThingDTO toDTO(Thing thing) {
        User c = thing.getUser();
        ThingDTO thingDTO = new ThingDTO(
                thing.getId(),
                thing.getName(),
                thing.getUrl(),
                thing.getAlive());
        if (c != null) {
            UserDTO userDTO = userDTOMapper.toDTO(c);
            thingDTO.setUser(userDTO);
        }
        return thingDTO;
    }
}



