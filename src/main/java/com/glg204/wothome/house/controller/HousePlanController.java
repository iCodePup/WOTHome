package com.glg204.wothome.house.controller;


import com.glg204.wothome.house.dto.RoomDTO;
import com.glg204.wothome.house.dto.RoomWithThingsDTO;
import com.glg204.wothome.house.service.HousePlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/houseplan")
public class HousePlanController {

    @Autowired
    HousePlanService housePlanService;

    @GetMapping("/room")
    public ResponseEntity<List<RoomDTO>> getRooms(Principal principal) {
        List<RoomDTO> roomDTOList = housePlanService.getRooms(principal);
        return ResponseEntity.ok(roomDTOList);
    }

    @GetMapping("/room/things")
    public ResponseEntity<List<RoomWithThingsDTO>> getRoomsWithThings(Principal principal) {
        List<RoomWithThingsDTO> roomsWithThingsList = housePlanService.getRoomsWithThings(principal);
        return ResponseEntity.ok(roomsWithThingsList);
    }

    @PostMapping("/room")
    public ResponseEntity<String> addRoom(Principal principal, @Valid @RequestBody RoomDTO roomDTO) {
        if (housePlanService.addRoom(principal, roomDTO)) {
            return ResponseEntity.ok("Pièce ajoutée");
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/room")
    public ResponseEntity<String> updateRoom(Principal principal, @Valid @RequestBody RoomDTO roomDTO) {
        if (housePlanService.updateRoom(principal, roomDTO)) {
            return ResponseEntity.ok("Pièce sauvegardée");
        }
        return ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("/room/{id}")
    public ResponseEntity<Boolean> deleteRoom(@PathVariable Long id) {
        if (housePlanService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(Exception ex) {
        StringBuilder builder = new StringBuilder();

        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validationException = (MethodArgumentNotValidException) ex;
            validationException.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                builder.append(String.format("%s: %s ", fieldName, errorMessage));
            });
        }

        String responseMessage = builder.toString().trim();
        String jsonResponse = String.format("{\"message\": \"%s\"}", responseMessage);
        return ResponseEntity.badRequest().body(jsonResponse);
    }
}
