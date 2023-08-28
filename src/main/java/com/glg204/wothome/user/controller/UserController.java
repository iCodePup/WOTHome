package com.glg204.wothome.user.controller;

import com.glg204.wothome.user.service.UserService;
import com.glg204.wothome.webofthings.dto.ThingDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
    public ResponseEntity<String> addThingToUser(@Valid @RequestBody ThingDTO thingDTO, Principal p) {
        if (userService.addThingToUser(p, thingDTO.getId())) {
            return ResponseEntity.ok("Objet connecté ajouté");
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(Exception ex) {
        StringBuilder builder = new StringBuilder();

        if (ex instanceof MethodArgumentNotValidException validationException) {
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
