package com.glg204.wothome.scene.dto;


import com.glg204.wothome.webofthings.domain.Thing;

import java.util.List;

public class ActionDTO {

    private Long thingId;

    private String property;

    private String value;


    public ActionDTO() {
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
