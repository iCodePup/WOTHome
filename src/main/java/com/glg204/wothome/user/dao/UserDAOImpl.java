package com.glg204.wothome.user.dao;

import com.glg204.wothome.authentification.dao.WOTUserDAO;
import com.glg204.wothome.authentification.domain.WOTUser;
import com.glg204.wothome.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    WOTUserDAO wotUserDAO;

    @Override
    public Integer save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into enduser (email,telephone,address) values(?,?,?)", new String[]{"id"});
            ps.setString(1, user.getWotUser().getEmail());
            ps.setString(2, user.getTelephone());
            ps.setString(3, user.getAddress());
            return ps;

        }, keyHolder);
        return (Integer) keyHolder.getKey();
    }

    @Override
    public List<Optional<User>> getUsers() {
        String sqlGetThing = "select * from enduser";
        try {
            return jdbcTemplate.queryForList(sqlGetThing).stream().map(row -> {
                return wotUserDAO.findByEmail(String.valueOf(row.get("email"))).map(wtUser -> {
                    WOTUser wotUser = wotUserDAO.findByEmail(String.valueOf(row.get("email"))).get();
                    User c = new User(
                            Long.parseLong(row.get("id").toString()),
                            wotUser,
                            String.valueOf(row.get("telephone")),
                            String.valueOf(row.get("address")));
                    return Optional.of(c);
                }).orElseGet(() -> Optional.empty());

            }).toList();
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject("select * from enduser where email = ?", new Object[]{email}, (rs, rowNum) -> {
                Optional<WOTUser> wotUser = wotUserDAO.findByEmail(String.valueOf(rs.getString("email")));
                if (wotUser.isPresent()) {
                    User c = new User(
                            rs.getLong("id"),
                            wotUser.get(),
                            rs.getString("telephone"),
                            rs.getString("address"));
                    return Optional.of(c);
                } else {
                    return Optional.empty();
                }
            });
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getById(long clientid) {
        String sqlGetClient = "select * from user where id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlGetClient, new Object[]{clientid}, (rs, rowNum) -> {
                Optional<WOTUser> wotUser = wotUserDAO.findByEmail(String.valueOf(rs.getString("email")));
                if (wotUser.isPresent()) {
                    User c = new User(
                            rs.getLong("id"),
                            wotUser.get(),
                            rs.getString("telephone"),
                            rs.getString("address"));
                    return Optional.of(c);
                } else {
                    return Optional.empty();
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean setUserToThing(Long thingId, Long userId) {
        String sql = "UPDATE thing SET enduserid = ? WHERE id = ?";
        Object[] args = new Object[]{userId, thingId};
        return jdbcTemplate.update(sql, args) == 1;
    }
}
