package com.glg204.wothome.house.service;

import com.glg204.wothome.house.domain.Room;
import com.glg204.wothome.house.dto.RoomDTO;
import org.springframework.stereotype.Service;

@Service
public class RoomDTOMapper {

    public RoomDTO toDTO(Room room) {
        return new RoomDTO(room.getId(), room.getName(), room.getSurface());
    }
}
