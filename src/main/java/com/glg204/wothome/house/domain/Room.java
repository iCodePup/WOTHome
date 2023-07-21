package com.glg204.wothome.house.domain;

public class Room {

    private Long id;

    private String name;

    private Double surface;

    public Room() {
    }

    public Room(Long id, String name, Double surface) {
        this.id = id;
        this.name = name;
        this.surface = surface;
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

    public Double getSurface() {
        return surface;
    }

    public void setSurface(Double surface) {
        this.surface = surface;
    }
}
