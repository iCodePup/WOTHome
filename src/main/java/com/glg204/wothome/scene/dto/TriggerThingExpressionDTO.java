package com.glg204.wothome.scene.dto;


import com.glg204.wothome.webofthings.dto.ThingDTO;
import jakarta.validation.constraints.NotNull;

public class TriggerThingExpressionDTO extends TriggerExpressionDTO {

    @NotNull
    private ThingDTO thingDTO;

    @NotNull
    private String property;

    @NotNull
    private String value;

    public TriggerThingExpressionDTO() {
    }

    public TriggerThingExpressionDTO(ThingDTO thingDTO, String property, String value) {
        this.thingDTO = thingDTO;
        this.property = property;
        this.value = value;
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
