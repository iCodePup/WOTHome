package com.glg204.wothome.webofthings.domain;

import com.glg204.wothome.house.domain.Room;
import com.glg204.wothome.user.domain.User;

public class Thing {

    private Long id;

    private String name;

    private String url;

    private Boolean alive;

    private User user;

    private Room room;

    public Thing() {
    }

    public Thing(String name, String url, Boolean alive) {
        this.name = name;
        this.url = url;
        this.alive = alive;
    }

    public Thing(Long id, String name, String url, Boolean alive) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.alive = alive;
    }

    public Thing(Long id, String name, String url, Boolean alive, User user) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.alive = alive;
        this.user = user;
    }

    public Thing(Long id, String name, String url, Boolean alive, User user,Room room) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.alive = alive;
        this.user = user;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
