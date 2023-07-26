package com.glg204.wothome.house.service;

import com.glg204.wothome.house.domain.HousePlan;
import com.glg204.wothome.house.domain.Room;
import com.glg204.wothome.house.dto.RoomDTO;
import com.glg204.wothome.webofthings.domain.Thing;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomDTOMapper {

    public RoomDTO toDTO(Room room, List<Thing> thingList) {
        List<Long> thingsIdList = thingList.stream()
                .map(Thing::getId)
                .collect(Collectors.toList());
        return new RoomDTO(room.getId(), room.getName(), room.getSurface(),thingsIdList);
    }

    public Room fromDTO(HousePlan housePlan, RoomDTO roomDTO) {
        return new Room(roomDTO.getId(), roomDTO.getName(), roomDTO.getSurface(), housePlan);
    }
}
