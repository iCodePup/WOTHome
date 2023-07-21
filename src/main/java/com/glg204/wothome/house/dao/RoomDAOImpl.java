package com.glg204.wothome.house.dao;

import com.glg204.wothome.house.domain.HousePlan;
import com.glg204.wothome.house.domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RoomDAOImpl implements RoomDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Room> getRoomsByHousePlan(HousePlan housePlan) {
        String sqlGetThingInStore = "select * from room where houseplanid = ?";
        try {
            List<Room> rooms = jdbcTemplate.queryForList(sqlGetThingInStore, new Object[]{housePlan.getId()}).stream().map(row -> new Room(
                    Long.parseLong(row.get("id").toString()),
                    String.valueOf(row.get("name")),
                    Double.parseDouble(row.get("surface").toString()))).toList();
            return rooms;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
