package com.glg204.wothome.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glg204.wothome.house.controller.HousePlanController;
import com.glg204.wothome.house.dto.RoomDTO;
import com.glg204.wothome.house.dto.RoomWithThingsDTO;
import com.glg204.wothome.house.service.HousePlanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class HousePlanControllerTest {

    @InjectMocks
    private HousePlanController housePlanController;

    @Mock
    private HousePlanService housePlanService;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRooms() throws Exception {
        List<RoomDTO> roomDTOList = new ArrayList<>();
        roomDTOList.add(new RoomDTO());
        roomDTOList.add(new RoomDTO());
        Principal principal = mock(Principal.class);
        when(housePlanService.getRooms(principal)).thenReturn(roomDTOList);
        ResponseEntity<List<RoomDTO>> responseEntity = housePlanController.getRooms(principal);
        List<RoomDTO> result = responseEntity.getBody();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(roomDTOList.size(), result.size());
        verify(housePlanService).getRooms(principal);
    }


    @Test
    public void testGetRoomsWithThings() throws Exception {
        List<RoomWithThingsDTO> roomWithThingsDTOList = new ArrayList<>();
        roomWithThingsDTOList.add(new RoomWithThingsDTO());
        roomWithThingsDTOList.add(new RoomWithThingsDTO());
        Principal principal = mock(Principal.class);
        when(housePlanService.getRoomsWithThings(principal)).thenReturn(roomWithThingsDTOList);
        ResponseEntity<List<RoomWithThingsDTO>> responseEntity = housePlanController.getRoomsWithThings(principal);
        List<RoomWithThingsDTO> result = responseEntity.getBody();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(roomWithThingsDTOList.size(), result.size());
        verify(housePlanService).getRoomsWithThings(principal);
    }

    @Test
    public void testAddRoom() throws Exception {
        RoomDTO roomDTO = new RoomDTO();
        Principal principal = mock(Principal.class);
        when(housePlanService.addRoom(principal, roomDTO)).thenReturn(true);
        ResponseEntity<String> responseEntity = housePlanController.addRoom(principal, roomDTO);
        String result = responseEntity.getBody();
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Pièce ajoutée", result);
        verify(housePlanService).addRoom(principal, roomDTO);
    }

    @Test
    public void testUpdateRoom() throws Exception {
        RoomDTO roomDTO = new RoomDTO();
        Principal principal = mock(Principal.class);
        when(housePlanService.updateRoom(principal, roomDTO)).thenReturn(true);
        ResponseEntity<String> responseEntity = housePlanController.updateRoom(principal, roomDTO);
        String result = responseEntity.getBody();
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Pièce sauvegardée", result);
        verify(housePlanService).updateRoom(principal, roomDTO);
    }

    @Test
    public void testDeleteRoom() throws Exception {
        Long roomId = 1L;
        when(housePlanService.delete(roomId)).thenReturn(true);
        ResponseEntity<Boolean> responseEntity = housePlanController.deleteRoom(roomId);
        Assertions.assertEquals(204, responseEntity.getStatusCodeValue());
        verify(housePlanService).delete(roomId);
    }
}

