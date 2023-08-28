package com.glg204.wothome.service;

import com.glg204.wothome.scene.dao.RuleDAO;
import com.glg204.wothome.scene.domain.Rule;
import com.glg204.wothome.scene.dto.RuleDTO;
import com.glg204.wothome.scene.service.RuleDTOMapper;
import com.glg204.wothome.scene.service.RuleServiceImpl;
import com.glg204.wothome.user.dao.UserDAO;
import com.glg204.wothome.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;

class RuleServiceImplTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private RuleDAO ruleDAO;

    @Mock
    private RuleDTOMapper ruleDTOMapper;

    @InjectMocks
    private RuleServiceImpl ruleService;

    private Principal principal;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        principal = mock(Principal.class);
    }

    @Test
    public void testAddRule_Success() {
        RuleDTO ruleDTO = new RuleDTO();
        User user = new User();
        when(userDAO.getUserByEmail(principal.getName())).thenReturn(Optional.of(user));
        when(ruleDTOMapper.fromDTO(ruleDTO, user)).thenReturn(new Rule());
        when(ruleDAO.save(any(Rule.class))).thenReturn(1L);
        boolean result = ruleService.addRule(principal, ruleDTO);
        Assertions.assertTrue(result);
        verify(userDAO, times(1)).getUserByEmail(principal.getName());
        verify(ruleDTOMapper, times(1)).fromDTO(ruleDTO, user);
        verify(ruleDAO, times(1)).save(any(Rule.class));
    }

    @Test
    public void testAddRule_Failure() {
        RuleDTO ruleDTO = new RuleDTO();
        User user = new User();
        when(userDAO.getUserByEmail(principal.getName())).thenReturn(Optional.of(user));
        when(ruleDTOMapper.fromDTO(ruleDTO, user)).thenReturn(new Rule());
        when(ruleDAO.save(any(Rule.class))).thenReturn((long) -1);
        boolean result = ruleService.addRule(principal, ruleDTO);
        Assertions.assertFalse(result);
        verify(userDAO, times(1)).getUserByEmail(principal.getName());
        verify(ruleDTOMapper, times(1)).fromDTO(ruleDTO, user);
        verify(ruleDAO, times(1)).save(any(Rule.class));
    }

    @Test
    public void testGetRules_WithUser() {
        User user = new User();
        when(userDAO.getUserByEmail(principal.getName())).thenReturn(Optional.of(user));
        when(ruleDAO.getRules(user)).thenReturn(new ArrayList<>());
        when(ruleDTOMapper.toDTO(any(Rule.class))).thenReturn(new RuleDTO());
        List<RuleDTO> ruleDTOList = ruleService.getRules(principal);
        Assertions.assertNotNull(ruleDTOList);
        Assertions.assertEquals(0, ruleDTOList.size());
        verify(userDAO, times(1)).getUserByEmail(principal.getName());
        verify(ruleDAO, times(1)).getRules(user);
        verify(ruleDTOMapper, times(0)).toDTO(any(Rule.class));
    }
}
