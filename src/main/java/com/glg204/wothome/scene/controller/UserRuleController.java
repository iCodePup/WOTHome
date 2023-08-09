package com.glg204.wothome.scene.controller;

import com.glg204.wothome.scene.dto.RuleDTO;
import com.glg204.wothome.scene.service.RuleService;
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
public class UserRuleController {

    @Autowired
    RuleService ruleService;

    @GetMapping("/rule")
    public ResponseEntity<List<RuleDTO>> getRules(Principal principal) {
        List<RuleDTO> ruleDTOList = ruleService.getRules(principal);
        return ResponseEntity.ok(ruleDTOList);
    }

    @PostMapping("/rule")
    public ResponseEntity<String> addRule(Principal principal, @Valid @RequestBody RuleDTO ruleDTO) {
        if (ruleService.addRule(principal, ruleDTO)) {
            return ResponseEntity.ok("Régle ajoutée");
        }
        return ResponseEntity.internalServerError().build();
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
        errors.put("message", "Une erreur de validation est survenue");
        return errors;
    }

}
