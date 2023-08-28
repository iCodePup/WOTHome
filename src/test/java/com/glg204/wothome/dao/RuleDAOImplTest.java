package com.glg204.wothome.dao;

import com.glg204.wothome.scene.dao.RuleDAO;
import com.glg204.wothome.scene.dao.RuleDAOImpl;
import com.glg204.wothome.scene.dao.RuleRowMapper;
import com.glg204.wothome.scene.domain.*;
import com.glg204.wothome.user.dao.UserDAO;
import com.glg204.wothome.user.domain.User;
import com.glg204.wothome.webofthings.dao.ThingDAO;
import com.glg204.wothome.webofthings.domain.Thing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RuleDAOImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private ThingDAO thingDAO;

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private RuleDAO ruleDAO = new RuleDAOImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private List<Rule> createMockedRuleList() {
        List<Rule> mockedRules = new ArrayList<>();
        Rule rule1 = new Rule();
        rule1.setId(1L);
        rule1.setName("Rule 1");
        // Set other properties
        mockedRules.add(rule1);

        Rule rule2 = new Rule();
        rule2.setId(2L);
        rule2.setName("Rule 2");
        // Set other properties
        mockedRules.add(rule2);
        return mockedRules;
    }

    @Test
    public void testGetRules() throws Exception {
        User user = new User();
        user.setId(1L);
        List<Rule> mockedRules = createMockedRuleList();
        RuleRowMapper ruleRowMapper = mock(RuleRowMapper.class);
        when(ruleRowMapper.mapRow(any(ResultSet.class), anyInt()))
                .thenReturn(mockedRules.get(0));
        when(userDAO.getById(anyLong())).thenReturn(Optional.of(user));
        Thing mockedThing = new Thing();
        when(thingDAO.getById(anyLong())).thenReturn(Optional.of(mockedThing));
        when(jdbcTemplate.query(anyString(), any(Object[].class), eq(ruleRowMapper)))
                .thenReturn(mockedRules);
        List<Rule> rules = ruleDAO.getRules(user);
        assertNotNull(rules);
        //
//        assertEquals(mockedRules.size(), rules.size());
//        assertEquals(mockedRules.get(0), rules.get(0));
    }

}
