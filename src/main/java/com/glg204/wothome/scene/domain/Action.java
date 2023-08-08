package com.glg204.wothome.scene.domain;

import com.glg204.wothome.webofthings.domain.Thing;

public class Action {

    private Long id;

    private Thing thing;

    private String property;

    private String value;

    public Action() {
    }

    public Action(Thing thing, String property, String value) {
        this.thing = thing;
        this.property = property;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public Thing getThing() {
        return thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
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
