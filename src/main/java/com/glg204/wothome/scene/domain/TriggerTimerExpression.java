package com.glg204.wothome.scene.domain;

import jakarta.persistence.Entity;

import java.time.Instant;

public class TriggerTimerExpression extends TriggerExpression {

    private Instant runtime;

    @Override
    public void process() {

    }
}
