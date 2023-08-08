package com.glg204.wothome.scene.controller;

import com.glg204.wothome.house.dto.RoomDTO;
import com.glg204.wothome.scene.dto.RuleDTO;
import com.glg204.wothome.scene.service.RuleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserRuleController {

    @Autowired
    RuleService ruleService;

    @PostMapping("/rule")
    public ResponseEntity<String> addRule(Principal principal, @Valid @RequestBody RuleDTO ruleDTO) {
        if (ruleService.addRule(principal, ruleDTO)) {
            return ResponseEntity.ok("Régle ajoutée");
        }
        return ResponseEntity.internalServerError().build();
    }

}
