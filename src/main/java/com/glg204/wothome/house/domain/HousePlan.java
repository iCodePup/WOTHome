package com.glg204.wothome.house.domain;

import com.glg204.wothome.user.domain.User;

public class HousePlan {

    private Long id;

    private User user;

    public HousePlan() {
    }

    public HousePlan(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
