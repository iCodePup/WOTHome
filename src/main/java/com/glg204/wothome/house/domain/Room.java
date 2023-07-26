package com.glg204.wothome.house.domain;

public class Room {

    private Long id;

    private String name;

    private Double surface;

    private HousePlan housePlan;

    public Room() {
    }

    public Room( Long id,String name, Double surface, HousePlan housePlan) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.housePlan = housePlan;
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

    public HousePlan getHousePlan() {
        return housePlan;
    }

    public void setHousePlan(HousePlan housePlan) {
        this.housePlan = housePlan;
    }
}
