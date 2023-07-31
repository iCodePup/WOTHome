package com.glg204.wothome.house.service;

import com.glg204.wothome.house.dto.RoomDTO;
import com.glg204.wothome.house.dto.RoomWithThingsDTO;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface HousePlanService {
    List<RoomDTO> getRooms(Principal principal);

    Boolean addRoom(Principal principal, RoomDTO roomDTO);

    boolean delete(Long roomId);

    boolean updateRoom(Principal principal, RoomDTO roomDTO);

    List<RoomWithThingsDTO> getRoomsWithThings(Principal principal);
}
