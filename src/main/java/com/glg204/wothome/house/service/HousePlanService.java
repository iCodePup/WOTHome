package com.glg204.wothome.house.service;

import com.glg204.wothome.house.dto.RoomDTO;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface HousePlanService {
    List<RoomDTO> getRooms(Principal principal);

    Boolean addRoom(Principal principal, RoomDTO roomDTO);

    boolean delete(Long id);

    boolean updateRoom(Principal principal, RoomDTO roomDTO);
}
