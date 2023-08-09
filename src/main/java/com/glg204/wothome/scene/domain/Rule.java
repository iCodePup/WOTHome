package com.glg204.wothome.scene.domain;

import com.glg204.wothome.user.domain.User;

public class Rule {

    private Long id;

    private String name;

    private User user;

    private TriggerExpression triggerExpression;

    private Action action;

    public Rule() {
    }

    public Rule(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TriggerExpression getTriggerExpression() {
        return triggerExpression;
    }

    public void setTriggerExpression(TriggerExpression triggerExpression) {
        this.triggerExpression = triggerExpression;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
