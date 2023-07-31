package com.glg204.wothome.house.service;

import com.glg204.wothome.house.domain.HousePlan;
import com.glg204.wothome.house.domain.Room;
import com.glg204.wothome.house.dto.RoomDTO;
import com.glg204.wothome.house.dto.RoomWithThingsDTO;
import com.glg204.wothome.webofthings.domain.Thing;
import com.glg204.wothome.webofthings.dto.ThingDTO;
import com.glg204.wothome.webofthings.service.ThingDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomDTOMapper {

    @Autowired
    ThingDTOMapper thingDTOMapper;

    public RoomDTO toDTO(Room room, List<Thing> thingList) {
        List<Long> thingsIdList = thingList.stream()
                .map(Thing::getId)
                .collect(Collectors.toList());
        return new RoomDTO(room.getId(), room.getName(), room.getSurface(), thingsIdList);
    }

    public RoomWithThingsDTO toDTOWithThings(Room room, List<Thing> thingList) {
        List<ThingDTO> thingDTOList = thingList.stream().map(thing -> thingDTOMapper.toDTO(thing)).toList();
        return new RoomWithThingsDTO(room.getId(), room.getName(), room.getSurface(), thingDTOList);
    }

    public Room fromDTO(HousePlan housePlan, RoomDTO roomDTO) {
        return new Room(roomDTO.getId(), roomDTO.getName(), roomDTO.getSurface(), housePlan);
    }
}
