package com.glg204.wothome.house.dao;

import com.glg204.wothome.house.domain.HousePlan;
import com.glg204.wothome.house.domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
                            Double.parseDouble(row.get("surface").toString()),
                            housePlan))
                    .toList();
            return rooms;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Long save(Room room) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into room (name,surface,houseplanid) values(?,?,?)", new String[]{"id"});
            ps.setString(1, room.getName());
            ps.setDouble(2, room.getSurface());
            ps.setLong(3, room.getHousePlan().getId());
            return ps;

        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from room where id = ?";
        Object[] args = new Object[]{id};
        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public boolean update(Room room) {
        String sql = "update room set name = ?, surface = ? WHERE id = ?";
        Object[] args = new Object[]{room.getName(), room.getSurface(), room.getId()};
        return jdbcTemplate.update(sql, args) == 1;
    }
}
