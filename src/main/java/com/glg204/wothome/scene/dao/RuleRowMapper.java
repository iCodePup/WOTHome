package com.glg204.wothome.scene.dao;

import com.glg204.wothome.scene.domain.*;
import com.glg204.wothome.user.domain.User;
import com.glg204.wothome.webofthings.dao.ThingDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;


public class RuleRowMapper implements RowMapper<Rule> {

    private final JdbcTemplate jdbcTemplate;

    private final User currentUser;

    private final ThingDAO thingDAO;

    public RuleRowMapper(User currentUser, ThingDAO thingDAO, JdbcTemplate jdbcTemplate) {
        this.currentUser = currentUser;
        this.jdbcTemplate = jdbcTemplate;
        this.thingDAO = thingDAO;
    }

    @Override
    public Rule mapRow(ResultSet rs, int rowNum) throws SQLException {
        Rule rule = new Rule();
        rule.setId(rs.getLong("rule_id"));
        rule.setName(rs.getString("name"));
        rule.setUser(currentUser);
        Action action = new Action();
        action.setId(rs.getLong("action_id"));
        if (rs.getObject("action_thing_id") != null) {
            thingDAO.getById(rs.getLong("action_thing_id")).ifPresent(action::setThing);
        }
        action.setProperty(rs.getString("action_property"));
        action.setValue(rs.getString("action_value"));
        rule.setAction(action);
        long triggerExpressionId = rs.getLong("trigger_expression_id");
        TriggerExpression triggerExpression = mapTriggerExpression(rs, triggerExpressionId);
        rule.setTriggerExpression(triggerExpression);
        return rule;
    }


    private TriggerExpression mapTriggerExpression(ResultSet rs, long expressionId) throws SQLException {
        String query = getTriggerExpressionQuery();

        if (rs.getObject("and_expression_id") != null && rs.getLong("and_expression_id") == expressionId) {
            if (jdbcTemplate.getDataSource() != null ) {
                Connection connection = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement preparedStatementFirst = connection.prepareStatement(query);
                preparedStatementFirst.setLong(1, rs.getLong("first_and_expression_id"));
                ResultSet firstRs = preparedStatementFirst.executeQuery();

                PreparedStatement preparedStatementSecond = connection.prepareStatement(query);
                preparedStatementSecond.setLong(1, rs.getLong("second_and_expression_id"));
                ResultSet secondRs = preparedStatementSecond.executeQuery();
                firstRs.next();
                secondRs.next();

                TriggerAndExpression triggerAndExpression = new TriggerAndExpression(
                        mapTriggerExpression(firstRs, rs.getLong("first_and_expression_id")),
                        mapTriggerExpression(secondRs, rs.getLong("second_and_expression_id")));
                triggerAndExpression.setId(expressionId);
                firstRs.close();
                secondRs.close();
                preparedStatementFirst.close();
                preparedStatementSecond.close();
                connection.close();
                return triggerAndExpression;
            }
        }
        if (rs.getObject("or_expression_id") != null && rs.getLong("or_expression_id") == expressionId) {
            if (jdbcTemplate.getDataSource() != null ) {
                Connection connection = jdbcTemplate.getDataSource().getConnection();

                PreparedStatement preparedStatementFirst = connection.prepareStatement(query);
                preparedStatementFirst.setLong(1, rs.getLong("first_or_expression_id"));
                ResultSet firstRs = preparedStatementFirst.executeQuery();
                PreparedStatement preparedStatementSecond = connection.prepareStatement(query);
                preparedStatementSecond.setLong(1, rs.getLong("second_or_expression_id"));
                ResultSet secondRs = preparedStatementSecond.executeQuery();
                firstRs.next();
                secondRs.next();
                TriggerOrExpression triggerOrExpression = new TriggerOrExpression(
                        mapTriggerExpression(firstRs, rs.getLong("first_or_expression_id")),
                        mapTriggerExpression(secondRs, rs.getLong("second_or_expression_id")));
                triggerOrExpression.setId(expressionId);
                firstRs.close();
                secondRs.close();
                preparedStatementFirst.close();
                preparedStatementSecond.close();
                connection.close();
                return triggerOrExpression;
            }
        }
        if (rs.getObject("thing_expression_id") != null && rs.getLong("thing_expression_id") == expressionId) {
            TriggerThingExpression thingExpression = new TriggerThingExpression();
            if (rs.getObject("thing_expression_thing_id") != null) {
                thingDAO.getById(rs.getLong("thing_expression_thing_id")).ifPresent(thingExpression::setThing);
            }
            thingExpression.setProperty(rs.getString("thing_expression_property"));
            thingExpression.setValue(rs.getString("thing_expression_value"));
            thingExpression.setId(rs.getLong("thing_expression_id"));
            return thingExpression;
        }

        if (rs.getObject("timer_expression_id") != null && rs.getLong("timer_expression_id") == expressionId) {
            Instant runtime = rs.getTimestamp("timer_expression_runtime").toInstant();
            TriggerTimerExpression triggerTimerExpression = new TriggerTimerExpression(runtime);
            triggerTimerExpression.setId(rs.getLong("thing_expression_id"));
            return triggerTimerExpression;
        }
        return null; //  null si l'expression est pas reconnu
    }


    private String getTriggerExpressionQuery() {
        return "SELECT  te.id AS trigger_expression_id, ttae.id AS and_expression_id," +
                " ttae.first_expression_id AS first_and_expression_id, ttae.second_expression_id AS second_and_expression_id, " +
                "ttoe.id AS or_expression_id, ttoe.first_expression_id AS first_or_expression_id, " +
                "ttoe.second_expression_id AS second_or_expression_id, ttte.id AS timer_expression_id," +
                " ttte.runtime AS timer_expression_runtime, tthe.id AS thing_expression_id, " +
                "tthe.thing_id AS thing_expression_thing_id, tthe.property AS thing_expression_property, " +
                "tthe.value AS thing_expression_value" +
                " FROM trigger_expression te " +
                " LEFT JOIN trigger_and_expression ttae ON te.id = ttae.id " +
                "LEFT JOIN trigger_or_expression ttoe ON te.id = ttoe.id" +
                " LEFT JOIN trigger_timer_expression ttte ON te.id = ttte.id" +
                " LEFT JOIN trigger_thing_expression tthe ON te.id = tthe.id where te.id = ?";
    }
}
