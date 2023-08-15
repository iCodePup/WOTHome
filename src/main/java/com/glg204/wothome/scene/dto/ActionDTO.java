package com.glg204.wothome.scene.dto;


import com.glg204.wothome.webofthings.dto.ThingDTO;
import jakarta.validation.constraints.NotNull;

public class ActionDTO {

    @NotNull
    private ThingDTO thingDTO;

    @NotNull
    private String property;

    @NotNull
    private String value;

    public ActionDTO() {
    }

    public ThingDTO getThingDTO() {
        return thingDTO;
    }

    public void setThingDTO(ThingDTO thingDTO) {
        this.thingDTO = thingDTO;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
