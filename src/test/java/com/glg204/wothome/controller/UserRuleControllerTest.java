package com.glg204.wothome.controller;


import com.glg204.wothome.scene.controller.UserRuleController;
import com.glg204.wothome.scene.dto.RuleDTO;
import com.glg204.wothome.scene.exception.InvalidRuleException;
import com.glg204.wothome.scene.service.RuleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;

class UserRuleControllerTest {

    @Mock
    private RuleService ruleService;

    @InjectMocks
    private UserRuleController userRuleController;

    private Principal principal;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        principal = mock(Principal.class);
    }

    @Test
    public void testGetRules() {
        List<RuleDTO> ruleDTOList = new ArrayList<>();
        when(ruleService.getRules(principal)).thenReturn(ruleDTOList);
        ResponseEntity<List<RuleDTO>> responseEntity = userRuleController.getRules(principal);
        Assertions.assertEquals(ruleDTOList, responseEntity.getBody());
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
        verify(ruleService, times(1)).getRules(principal);
    }

    @Test
    public void testGetRuleById_Found() {
        Long ruleId = 1L;
        RuleDTO ruleDTO = new RuleDTO();
        when(ruleService.getRuleById(principal, ruleId)).thenReturn(Optional.of(ruleDTO));
        ResponseEntity<RuleDTO> responseEntity = userRuleController.getRules(principal, ruleId);
        Assertions.assertEquals(ruleDTO, responseEntity.getBody());
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
        verify(ruleService, times(1)).getRuleById(principal, ruleId);
    }

    @Test
    public void testGetRuleById_NotFound() {
        Long ruleId = 1L;
        when(ruleService.getRuleById(principal, ruleId)).thenReturn(Optional.empty());
        ResponseEntity<RuleDTO> responseEntity = userRuleController.getRules(principal, ruleId);
        Assertions.assertNull(responseEntity.getBody());
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
        verify(ruleService, times(1)).getRuleById(principal, ruleId);
    }

    @Test
    public void testDeleteRule_Success() {
        Long ruleId = 1L;
        when(ruleService.delete(ruleId)).thenReturn(true);
        ResponseEntity<Boolean> responseEntity = userRuleController.deleteRule(ruleId);
        Assertions.assertNull(responseEntity.getBody());
        Assertions.assertEquals(204, responseEntity.getStatusCodeValue());
        verify(ruleService, times(1)).delete(ruleId);
    }

    @Test
    public void testDeleteRule_NotFound() {
        Long ruleId = 1L;
        when(ruleService.delete(ruleId)).thenReturn(false);
        ResponseEntity<Boolean> responseEntity = userRuleController.deleteRule(ruleId);
        Assertions.assertNull(responseEntity.getBody());
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
        verify(ruleService, times(1)).delete(ruleId);
    }

    @Test
    public void testAddRule_Success() {
        RuleDTO ruleDTO = new RuleDTO();
        ruleDTO.setName("ma règle");
        when(ruleService.addRule(principal, ruleDTO)).thenReturn(true);
        ResponseEntity<String> responseEntity = userRuleController.addRule(principal, ruleDTO);
        Assertions.assertEquals("Régle ajoutée", responseEntity.getBody());
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
        verify(ruleService, times(1)).addRule(principal, ruleDTO);
    }

    @Test
    public void testAddRule_InvalidInput() {
        RuleDTO ruleDTO = new RuleDTO(); // echec , pas de name
        Assertions.assertThrows(InvalidRuleException.class, () -> {
            userRuleController.addRule(principal, ruleDTO);
        });
        verify(ruleService, never()).addRule(principal, ruleDTO);
    }


}
