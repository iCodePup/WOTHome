package com.glg204.wothome.scene.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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
    public boolean process() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime runtimeDateTime = runtime.atOffset(ZoneOffset.UTC).toLocalDateTime();
        return currentDateTime.equals(runtimeDateTime);
    }
}
