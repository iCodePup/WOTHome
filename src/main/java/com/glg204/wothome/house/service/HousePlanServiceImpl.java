package com.glg204.wothome.house.service;

import com.glg204.wothome.house.dao.HousePlanDAO;
import com.glg204.wothome.house.dao.RoomDAO;
import com.glg204.wothome.house.domain.HousePlan;
import com.glg204.wothome.house.domain.Room;
import com.glg204.wothome.house.dto.RoomDTO;
import com.glg204.wothome.house.dto.RoomWithThingsDTO;
import com.glg204.wothome.user.dao.UserDAO;
import com.glg204.wothome.user.domain.User;
import com.glg204.wothome.webofthings.dao.ThingDAO;
import com.glg204.wothome.webofthings.domain.Thing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HousePlanServiceImpl implements HousePlanService {

    @Autowired
    HousePlanDAO housePlanDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    RoomDAO roomDAO;

    @Autowired
    ThingDAO thingDAO;

    @Autowired
    RoomDTOMapper roomDTOMapper;

    @Override
    public List<RoomDTO> getRooms(Principal principal) {
        Optional<User> user = userDAO.getUserByEmail(principal.getName());
        return user.map(currentUser -> housePlanDAO.getHousePlan(currentUser)
                .map(housePlan -> roomDAO.getRoomsByHousePlan(housePlan)
                        .stream().map(room -> {
                            List<Thing> thingList = thingDAO.getThingsByRoom(currentUser, room);
                            return roomDTOMapper.toDTO(room, thingList);
                        }).collect(Collectors.toList())).orElseGet(() -> {
                    housePlanDAO.createHousePlan(currentUser);
                    return new ArrayList<>();
                })).orElseGet(ArrayList::new);
    }

    @Override
    public Boolean addRoom(Principal principal, RoomDTO roomDTO) {
        Optional<User> user = userDAO.getUserByEmail(principal.getName());
        return user.map(currentUser -> housePlanDAO.getHousePlan(currentUser)
                        .map(housePlan -> this.addRoom(housePlan, roomDTO))
                        .orElseGet(() -> this.addRoom(housePlanDAO.createHousePlan(currentUser), roomDTO)))
                .orElse(false);
    }

    @Override
    public boolean delete(Long roomId) {
        return roomDAO.delete(roomId);
    }

    @Override
    public boolean updateRoom(Principal principal, RoomDTO roomDTO) {
        Optional<User> user = userDAO.getUserByEmail(principal.getName());
        return user.map(currentUser -> housePlanDAO.getHousePlan(currentUser)
                        .map(housePlan -> this.updateRoom(housePlan, roomDTO))
                        .orElseGet(() -> this.updateRoom(housePlanDAO.createHousePlan(currentUser), roomDTO)))
                .orElse(false);
    }

    @Override
    public List<RoomWithThingsDTO> getRoomsWithThings(Principal principal) {
        Optional<User> user = userDAO.getUserByEmail(principal.getName());
        return user.map(currentUser -> housePlanDAO.getHousePlan(currentUser)
                .map(housePlan -> roomDAO.getRoomsByHousePlan(housePlan)
                        .stream().map(room -> {
                            List<Thing> thingList = thingDAO.getThingsByRoom(currentUser, room);
                            return roomDTOMapper.toDTOWithThings(room, thingList);
                        }).collect(Collectors.toList())).orElseGet(() -> {
                    housePlanDAO.createHousePlan(currentUser);
                    return new ArrayList<>();
                })).orElseGet(ArrayList::new);
    }


    private boolean addRoom(HousePlan housePlan, RoomDTO roomDTO) {
        Room room = roomDTOMapper.fromDTO(housePlan, roomDTO);
        Long roomId = roomDAO.save(room);
        room.setId(roomId);
        boolean updated = roomId >= 0;
        for (Long thingId : roomDTO.getThingsId()) {
            updated &= thingDAO.setThingRoom(thingId, room.getId());
        }
        return updated;
    }

    private boolean updateRoom(HousePlan housePlan, RoomDTO roomDTO) {
        Room room = roomDTOMapper.fromDTO(housePlan, roomDTO);
        boolean updated = roomDAO.update(room);
        for (Long thingId : roomDTO.getThingsId()) {
            updated &= thingDAO.setThingRoom(thingId, room.getId());
        }
        return updated;
    }
}
