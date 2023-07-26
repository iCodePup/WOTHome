package com.glg204.wothome.house.controller;


import com.glg204.wothome.house.dto.RoomDTO;
import com.glg204.wothome.house.service.HousePlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/room")
    public ResponseEntity<String> addRoom(Principal principal, @Valid @RequestBody RoomDTO roomDTO) {
        if (housePlanService.addRoom(principal, roomDTO)) {
            return ResponseEntity.ok("Pièce ajouté");
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        errors.put("message","Erreur dans le formulaire");
        return errors;
    }
}
