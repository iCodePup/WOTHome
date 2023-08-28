package com.glg204.wothome.service;

import com.glg204.wothome.house.service.HousePlanServiceImpl;
import com.glg204.wothome.house.service.RoomDTOMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

public class HousePlanServiceImplTest {

    @InjectMocks
    private HousePlanServiceImpl housePlanService;

    @Mock
    private HousePlanDAO housePlanDAO;

    @Mock
    private UserDAO userDAO;

    @Mock
    private RoomDAO roomDAO;

    @Mock
    private ThingDAO thingDAO;

    @Mock
    private RoomDTOMapper roomDTOMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRooms() {
        User user = new User();
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("fab@cnam.com");
        when(userDAO.getUserByEmail("fab@cnam.com")).thenReturn(Optional.of(user));

        HousePlan housePlan = new HousePlan();
        when(housePlanDAO.getHousePlan(user)).thenReturn(Optional.of(housePlan));

        Room room1 = new Room();
        Room room2 = new Room();
        List<Room> roomList = new ArrayList<>();
        roomList.add(room1);
        roomList.add(room2);
        when(roomDAO.getRoomsByHousePlan(housePlan)).thenReturn(roomList);

        List<Thing> thingList = new ArrayList<>();
        when(thingDAO.getThingsByRoom(user, room1)).thenReturn(thingList);
        when(thingDAO.getThingsByRoom(user, room2)).thenReturn(thingList);

        List<RoomDTO> result = housePlanService.getRooms(principal);
        Assertions.assertEquals(roomList.size(), result.size());
    }

    @Test
    public void testAddRoom() {
        User user = new User();
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("fab@cnam.com");
        when(userDAO.getUserByEmail("fab@cnam.com")).thenReturn(Optional.of(user));
        HousePlan housePlan = new HousePlan();
        when(housePlanDAO.getHousePlan(user)).thenReturn(Optional.of(housePlan));
        RoomDTO roomDTO = new RoomDTO();
        List<Long> thingsId = new ArrayList<>();
        thingsId.add(1L);
        thingsId.add(2L);
        roomDTO.setThingsId(thingsId);
        when(roomDAO.save(any(Room.class))).thenReturn(1L);
        when(thingDAO.setThingRoom(anyLong(), anyLong())).thenReturn(true);
        boolean result = housePlanService.addRoom(principal, roomDTO);
        Assertions.assertTrue(result);
    }

    @Test
    public void testDeleteRoom() {
        Long roomId = 1L;
        when(roomDAO.delete(roomId)).thenReturn(true);
        boolean result = housePlanService.delete(roomId);
        Assertions.assertTrue(result);
    }

    @Test
    public void testUpdateRoom() {
        User user = new User();
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("fab@cnam.com");
        when(userDAO.getUserByEmail("fab@cnam.com")).thenReturn(Optional.of(user));
        HousePlan housePlan = new HousePlan();
        when(housePlanDAO.getHousePlan(user)).thenReturn(Optional.of(housePlan));
        RoomDTO roomDTO = new RoomDTO();
        List<Long> thingsId = new ArrayList<>();
        thingsId.add(1L);
        thingsId.add(2L);
        roomDTO.setThingsId(thingsId);
        when(roomDAO.update(any(Room.class))).thenReturn(true);
        when(thingDAO.setThingRoom(anyLong(), anyLong())).thenReturn(true);
        boolean result = housePlanService.updateRoom(principal, roomDTO);
        //Assertions.assertTrue(result);
    }

    @Test
    public void testGetRoomsWithThings() {
        User user = new User();
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("fab@cnam.com");
        when(userDAO.getUserByEmail("fab@cnam.com")).thenReturn(Optional.of(user));
        HousePlan housePlan = new HousePlan();
        when(housePlanDAO.getHousePlan(user)).thenReturn(Optional.of(housePlan));
        Room room1 = new Room();
        Room room2 = new Room();
        List<Room> roomList = new ArrayList<>();
        roomList.add(room1);
        roomList.add(room2);
        when(roomDAO.getRoomsByHousePlan(housePlan)).thenReturn(roomList);
        List<Thing> thingList = new ArrayList<>();
        when(thingDAO.getThingsByRoom(user, room1)).thenReturn(thingList);
        when(thingDAO.getThingsByRoom(user, room2)).thenReturn(thingList);
        List<RoomWithThingsDTO> result = housePlanService.getRoomsWithThings(principal);
        Assertions.assertEquals(roomList.size(), result.size());
    }
}

