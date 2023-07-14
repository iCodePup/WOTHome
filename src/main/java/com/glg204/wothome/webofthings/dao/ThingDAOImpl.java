
package com.glg204.wothome.webofthings.dao;

import com.glg204.wothome.user.dao.UserDAO;
import com.glg204.wothome.user.domain.User;
import com.glg204.wothome.webofthings.domain.Thing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class ThingDAOImpl implements ThingDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserDAO userDAO;

    public Collection<Thing> getUserThings(User user) {
        String sqlGetThing = "select * from thing";
        try {
            List<Thing> things = jdbcTemplate.queryForList(sqlGetThing).stream().map(row -> {
                Thing t = new Thing(Long.parseLong(row.get("id").toString()),
                        String.valueOf(row.get("name")),
                        String.valueOf(row.get("url")),
                        Boolean.parseBoolean(row.get("alive").toString()),
                        user);
                return t;
            }).toList();
            return things;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Thing> findByURL(String url) {
        String sqlGetThingInStore = "select * from thing where url = ?";
        try {
            Thing thing = jdbcTemplate.queryForObject(sqlGetThingInStore, new Object[]{url}, (rs, rowNum) -> {
                Long aId = rs.getLong("id");
                String name = rs.getString("name");
                String aUrl = rs.getString("url");
                Boolean alive = rs.getBoolean("alive");
                Thing t = new Thing(
                        aId, name, aUrl, alive);
                if (rs.getString("clientid") != null) {
                    Optional<User> optionalClient = userDAO.getById(Long.parseLong(rs.getString("clientid").toString()));
                    if (optionalClient.isPresent()) {
                        t.setUser(optionalClient.get());
                    }
                }
                return t;
            });
            return Optional.of(thing);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean setThingAlive(String url, boolean alive) {
        String sql = "UPDATE thing SET alive = ? WHERE url = ?";
        Object[] args = new Object[]{alive, url};
        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public void save(Thing thing) {

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        String sql = "insert into thing (name, url) values(?, ?);";
        int rowsAffected = jdbcTemplate.update(conn -> {
            // Pre-compiling SQL
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, thing.getName());
            ps.setString(2, thing.getUrl());
            return ps;
        }, generatedKeyHolder);
        Long id = generatedKeyHolder.getKey().longValue();
        thing.setId(id);
    }
}
