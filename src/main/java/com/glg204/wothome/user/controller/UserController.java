package com.glg204.wothome.user.controller;

import com.glg204.wothome.user.service.UserService;
import com.glg204.wothome.webofthings.dto.ThingDTO;
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

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/thing")
    public ResponseEntity<List<ThingDTO>> getUserThings(Principal p) {
        List<ThingDTO> thingDTOList = userService.getUserThings(p);
        return ResponseEntity.ok(thingDTOList);
    }

    @PostMapping("/thing")
    public ResponseEntity<String> purchaseThingInStore(@Valid @RequestBody ThingDTO thingDTO, Principal p) {
        if (userService.addThingToUser(p, thingDTO.getId())) {
            return ResponseEntity.ok("Objet connecté ajouté");
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        //todo add verification double password
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
