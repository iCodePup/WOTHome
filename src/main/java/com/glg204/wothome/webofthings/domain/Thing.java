package com.glg204.wothome.webofthings.domain;

import com.glg204.wothome.authentification.domain.WOTUser;
import com.glg204.wothome.user.domain.User;

public class Thing {

    private Long id;

    private String type;

    private String title;

    private String description;

    private Integer port;

    private User user;

    public Thing() {
    }

    public Thing(Long id, String type, String title, String description, Integer port, User user) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.port = port;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
