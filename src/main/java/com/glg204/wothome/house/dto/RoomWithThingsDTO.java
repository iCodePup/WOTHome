package com.glg204.wothome.house.dto;

import com.glg204.wothome.webofthings.dto.ThingDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;


public class RoomWithThingsDTO {

    private Long id;

    private String name;

    private Double surface;

    private List<ThingDTO> things;

    public RoomWithThingsDTO() {
        this.things = new ArrayList<>();
    }

    public RoomWithThingsDTO(Long id, String name, Double surface, List<ThingDTO> things) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.things = things;
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

    public List<ThingDTO> getThings() {
        return things;
    }

    public void setThings(List<ThingDTO> things) {
        this.things = things;
    }
}
