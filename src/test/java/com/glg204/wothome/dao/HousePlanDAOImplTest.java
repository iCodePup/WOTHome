package com.glg204.wothome.dao;

import com.glg204.wothome.house.dao.HousePlanDAOImpl;
import com.glg204.wothome.house.domain.HousePlan;
import com.glg204.wothome.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class HousePlanDAOImplTest {

    @InjectMocks
    private HousePlanDAOImpl housePlanDAO;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetHousePlan() {
        User user = new User();
        user.setId(1L);
        HousePlan housePlan = new HousePlan(1L, user);
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(housePlan);
        Optional<HousePlan> result = housePlanDAO.getHousePlan(user);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(housePlan.getId(), result.get().getId());
        Assertions.assertEquals(housePlan.getUser(), result.get().getUser());
    }

    @Test
    public void testGetHousePlanEmpty() {
        User user = new User();
        user.setId(1L);
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).thenThrow(EmptyResultDataAccessException.class);
        Optional<HousePlan> result = housePlanDAO.getHousePlan(user);
        Assertions.assertTrue(result.isEmpty());
    }

}
