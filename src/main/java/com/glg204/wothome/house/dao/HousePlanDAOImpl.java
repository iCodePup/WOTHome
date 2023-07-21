package com.glg204.wothome.house.dao;

import com.glg204.wothome.house.domain.HousePlan;
import com.glg204.wothome.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class HousePlanDAOImpl implements HousePlanDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Optional<HousePlan> getHousePlan(User user) {
        String sqlGetThingInStore = "select * from houseplan where enduserid = ?";
        try {
            HousePlan housePlan = jdbcTemplate.queryForObject(sqlGetThingInStore, new Object[]{user.getId()}, (rs, rowNum) -> {
                Long aId = rs.getLong("id");
                return new HousePlan(
                        aId, user);
            });
            return Optional.of(housePlan);
        } catch (
                EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Integer createHousePlan(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into houseplan (enduserid) values(?)");
            ps.setLong(1, user.getId());
            return ps;
        }, keyHolder);
        return (Integer) keyHolder.getKey();

    }
}
