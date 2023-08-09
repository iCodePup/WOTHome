package com.glg204.wothome.scene.dao;


import com.glg204.wothome.scene.domain.*;
import com.glg204.wothome.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
public class RuleDAOImpl implements RuleDAO {

    private final Logger logger = LoggerFactory.getLogger(RuleDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Rule> getAllRules(User currentUser) {
        String sql = "SELECT r.id AS rule_id, r.name, " +
                "a.id AS action_id, a.property AS action_property, a.value AS action_value, " +
                "te.id AS trigger_expression_id, " +
                "ttae.id AS and_expression_id, ttae.first_expression_id AS first_and_expression_id, ttae.second_expression_id AS second_and_expression_id, " +
                "ttoe.id AS or_expression_id, ttoe.first_expression_id AS first_or_expression_id, ttoe.second_expression_id AS second_or_expression_id, " +
                "ttte.id AS timer_expression_id, ttte.runtime AS timer_expression_runtime, " +
                "tthe.id AS thing_expression_id, tthe.thing_id AS thing_expression_thing_id, tthe.property AS thing_expression_property, tthe.value AS thing_expression_value " +
                "FROM rule r " +
                "JOIN action a ON r.actionid = a.id " +
                "JOIN trigger_expression te ON r.triggerexpressionid = te.id " +
                "LEFT JOIN trigger_and_expression ttae ON te.id = ttae.id " +
                "LEFT JOIN trigger_or_expression ttoe ON te.id = ttoe.id " +
                "LEFT JOIN trigger_timer_expression ttte ON te.id = ttte.id " +
                "LEFT JOIN trigger_thing_expression tthe ON te.id = tthe.id " +
                "WHERE enduserid = ?";

        return jdbcTemplate.query(sql, new Object[]{currentUser.getId()}, new RuleRowMapper(currentUser, jdbcTemplate));
    }


    public Long save(Rule rule) {
        if (rule.getAction() != null && rule.getTriggerExpression() != null) {
            Long triggerExpressionId = saveTriggerExpression(rule.getTriggerExpression());
            Long actionId = saveAction(rule.getAction());

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO rule (name, enduserid, actionid, triggerexpressionid) VALUES (?,?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, rule.getName());
                ps.setLong(2, rule.getUser().getId());
                ps.setLong(3, actionId);
                ps.setLong(4, triggerExpressionId);
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
