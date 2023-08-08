package com.glg204.wothome.scene.dao;


import com.glg204.wothome.scene.domain.*;
import com.glg204.wothome.user.domain.User;
import com.glg204.wothome.webofthings.domain.Thing;
import com.glg204.wothome.webofthings.service.ThingListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

@Repository
public class RuleDAOImpl implements RuleDAO {

    private Logger logger = LoggerFactory.getLogger(RuleDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long save(Rule rule) {
        if (rule.getAction() != null && rule.getTriggerExpression() != null) {
            Long triggerExpressionId = saveTriggerExpression(rule.getTriggerExpression());
            Long actionId = saveAction(rule.getAction());

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO rule (name, actionid, triggerexpressionid) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, rule.getName());
                ps.setLong(2, actionId);
                ps.setLong(3, triggerExpressionId);
                return ps;
            }, keyHolder);

            Map<String, Object> keys = keyHolder.getKeys();

            if (keys != null && keys.containsKey("id")) {
                return Long.parseLong(keys.get("id").toString());

            }
        }
        return (long) -1;
    }

    private Long saveTriggerExpression(TriggerExpression expression) {
        if (expression instanceof TriggerAndExpression) {
            return saveAndExpression((TriggerAndExpression) expression);
        } else if (expression instanceof TriggerOrExpression) {
            return saveOrExpression((TriggerOrExpression) expression);
        } else if (expression instanceof TriggerThingExpression) {
            return saveThingExpression((TriggerThingExpression) expression);
        } else if (expression instanceof TriggerTimerExpression) {
            return saveTimerExpression((TriggerTimerExpression) expression);
        }
        return (long) -1;
    }

    private Long saveAndExpression(TriggerAndExpression andExpression) {
        Long firstExpressionId = saveTriggerExpression(andExpression.getFirstExpression());
        Long secondExpressionId = saveTriggerExpression(andExpression.getSecondExpression());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO trigger_and_expression (first_expression_id, second_expression_id) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, firstExpressionId);
            ps.setLong(2, secondExpressionId);
            return ps;
        }, keyHolder);
        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null && keys.containsKey("id")) {
            return Long.parseLong(keys.get("id").toString());
        }
        return (long) -1;
    }

    private Long saveOrExpression(TriggerOrExpression orExpression) {
        Long firstExpressionId = saveTriggerExpression(orExpression.getFirstExpression());
        Long secondExpressionId = saveTriggerExpression(orExpression.getSecondExpression());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO trigger_or_expression (first_expression_id, second_expression_id) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, firstExpressionId);
            ps.setLong(2, secondExpressionId);
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null && keys.containsKey("id")) {
            return Long.parseLong(keys.get("id").toString());
        }
        return (long) -1;
    }

    private Long saveThingExpression(TriggerThingExpression thingExpression) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO trigger_thing_expression (thing_id, property, value) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, thingExpression.getThing().getId());
            ps.setString(2, thingExpression.getProperty());
            ps.setString(3, thingExpression.getValue());
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null && keys.containsKey("id")) {
            return Long.parseLong(keys.get("id").toString());
        }
        return (long) -1;
    }

    private Long saveTimerExpression(TriggerTimerExpression timerExpression) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO trigger_timer_expression (runtime) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, Timestamp.from(timerExpression.getRuntime()));
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null && keys.containsKey("id")) {
            return Long.parseLong(keys.get("id").toString());
        }
        return (long) -1;
    }

    private Long saveAction(Action action) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO action (thing_id, property, value) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, action.getThing().getId());
            ps.setString(2, action.getProperty());
            ps.setString(3, action.getValue());
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null && keys.containsKey("id")) {
            return Long.parseLong(keys.get("id").toString());
        }
        return (long) -1;
    }
}
