package com.glg204.wothome.scene.controller;

import com.glg204.wothome.scene.dto.*;
import com.glg204.wothome.scene.exception.InvalidRuleException;
import com.glg204.wothome.scene.service.RuleService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class UserRuleController {

    @Autowired
    RuleService ruleService;

    @GetMapping("/rule")
    public ResponseEntity<List<RuleDTO>> getRules(Principal principal) {
        List<RuleDTO> ruleDTOList = ruleService.getRules(principal);
        return ResponseEntity.ok(ruleDTOList);
    }

    @GetMapping("/rule/{id}")
    public ResponseEntity<RuleDTO> getRules(Principal principal, @PathVariable Long id) {
        Optional<RuleDTO> ruleDTOOptional = ruleService.getRuleById(principal, id);
        return ruleDTOOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/rule/{id}")
    public ResponseEntity<Boolean> deleteRule(@PathVariable Long id) {
        if (ruleService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/rule")
    public ResponseEntity<String> addRule(Principal principal, @Valid @RequestBody RuleDTO ruleDTO) {
        if (Strings.isEmpty(ruleDTO.getName())) {
            throw new InvalidRuleException("Nom de règle manquant");
        }
        if (ruleDTO.getActionDTO() != null) {
            if (Strings.isEmpty(ruleDTO.getActionDTO().getValue())) {
                throw new InvalidRuleException("Action manquante");
            }
        }

        if (ruleDTO.getTriggerExpressionDTO() != null) {
            if (ruleIsComplete(ruleDTO.getTriggerExpressionDTO())) {
                throw new InvalidRuleException("Expression manquante ou incomplète");
            }
        }

        if (ruleService.addRule(principal, ruleDTO)) {
            return ResponseEntity.ok("Régle ajoutée");
        }
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, InvalidRuleException.class,})
    public ResponseEntity<Object> handleValidationExceptions(Exception ex) {
        StringBuilder builder = new StringBuilder();

        if (ex instanceof MethodArgumentNotValidException validationException) {
            validationException.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                builder.append(String.format("%s: %s ", fieldName, errorMessage));
            });
        } else if (ex != null) {
            builder.append(ex.getMessage());
        }

        String responseMessage = builder.toString().trim();
        String jsonResponse = String.format("{\"message\": \"%s\"}", responseMessage);
        return ResponseEntity.badRequest().body(jsonResponse);
    }

    private boolean ruleIsComplete(TriggerExpressionDTO triggerExpressionDTO) {
        if (triggerExpressionDTO instanceof TriggerThingExpressionDTO) {
            TriggerThingExpressionDTO trigger = (TriggerThingExpressionDTO) triggerExpressionDTO;
            if (trigger.getValue() == null || Strings.isEmpty(trigger.getValue())) {
                return true;
            }
        }
        if (triggerExpressionDTO instanceof TriggerTimerExpressionDTO) {
            TriggerTimerExpressionDTO trigger = (TriggerTimerExpressionDTO) triggerExpressionDTO;
            if (trigger.getRuntime() == null) {
                return true;
            }
        }
        if (triggerExpressionDTO instanceof TriggerOrExpressionDTO) {
            TriggerOrExpressionDTO trigger = (TriggerOrExpressionDTO) triggerExpressionDTO;
            if (trigger.getFirstExpression() != null && ruleIsComplete(trigger.getFirstExpression())) {
                return true;
            }
            if (trigger.getSecondExpression() != null && ruleIsComplete(trigger.getSecondExpression())) {
                return true;
            }
        } else if (triggerExpressionDTO instanceof TriggerAndExpressionDTO) {
            TriggerAndExpressionDTO trigger = (TriggerAndExpressionDTO) triggerExpressionDTO;
            if (trigger.getFirstExpression() != null && ruleIsComplete(trigger.getFirstExpression())) {
                return true;
            }
            if (trigger.getSecondExpression() != null && ruleIsComplete(trigger.getSecondExpression())) {
                return true;
            }
        }
        return false;
    }
}
