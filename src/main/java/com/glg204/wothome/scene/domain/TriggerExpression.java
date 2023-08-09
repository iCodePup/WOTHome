package com.glg204.wothome.scene.domain;

public abstract class TriggerExpression {

    private Long id;

    public abstract void process();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
