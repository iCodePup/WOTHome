package com.glg204.wothome.scene.dto;


import jakarta.validation.constraints.NotNull;

public class TriggerThingExpressionDTO extends TriggerExpressionDTO {

    @NotNull
    private Long thingId;

    @NotNull
    private String property;

    @NotNull
    private String value;

    public TriggerThingExpressionDTO() {
    }


    public TriggerThingExpressionDTO(Long thingId, String property, String value) {
        this.thingId = thingId;
        this.property = property;
        this.value = value;
    }

    public Long getThingId() {
        return thingId;
    }

    public void setThingId(Long thingId) {
        this.thingId = thingId;
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
