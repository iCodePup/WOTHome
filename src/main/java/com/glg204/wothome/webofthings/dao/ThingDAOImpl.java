
package com.glg204.wothome.webofthings.dao;

import com.glg204.wothome.user.domain.User;
import com.glg204.wothome.webofthings.domain.Thing;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class ThingDAOImpl {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Collection<Thing> getUserThings(User user) {
        String sqlGetThing = "select * from thing";
        try {
            List<Thing> things = jdbcTemplate.queryForList(sqlGetThing).stream().map(row -> {
                Thing t = new Thing(Long.parseLong(row.get("id").toString()),
                        String.valueOf(row.get("type")),
                        String.valueOf(row.get("title")),
                        String.valueOf(row.get("description")),
                        Integer.parseInt(row.get("port").toString()),
                        user);
                return t;
            }).toList();
            return things;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }

    }
}
