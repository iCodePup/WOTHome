package com.glg204.wothome.scene.domain;

import java.time.Instant;

public class TriggerTimerExpression extends TriggerExpression {

    private Instant runtime;

    public TriggerTimerExpression(Instant runtime) {
        this.runtime = runtime;
    }

    public Instant getRuntime() {
        return runtime;
    }

    public void setRuntime(Instant runtime) {
        this.runtime = runtime;
    }

    @Override
    public void process() {

    }
}
