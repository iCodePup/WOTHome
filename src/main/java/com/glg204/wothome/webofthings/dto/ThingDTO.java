package com.glg204.wothome.webofthings.dto;

import com.glg204.wothome.user.dto.UserDTO;

public class ThingDTO {

    private Long id;

    private String name;

    private String url;

    private Boolean alive;

    private UserDTO user;

    public ThingDTO() {
    }

    public ThingDTO(Long id, String name, String url, Boolean alive) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.alive = alive;
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

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
