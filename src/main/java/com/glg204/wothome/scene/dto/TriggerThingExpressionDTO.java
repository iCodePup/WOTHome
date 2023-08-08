package com.glg204.wothome.scene.dto;


public class TriggerThingExpressionDTO extends TriggerExpressionDTO {

    private Long thingId;

    private String property;

    private String value;

    public TriggerThingExpressionDTO() {
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
