package com.glg204.wothome.house.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Valid
public class RoomDTO {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Double surface;

    private List<Long> thingsId;

    public RoomDTO() {
        this.thingsId = new ArrayList<>();
    }

    public RoomDTO(Long id, String name, Double surface, List<Long> thingsId) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.thingsId = thingsId;
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

    public List<Long> getThingsId() {
        return thingsId;
    }

    public void setThingsId(List<Long> thingsId) {
        this.thingsId = thingsId;
    }
}
