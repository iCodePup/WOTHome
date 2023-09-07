package com.glg204.wothome.scene.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TriggerTimerExpression extends TriggerExpression {

    private Instant runtime;

    private LocalDateTime currentDateTime;

    public TriggerTimerExpression(Instant runtime) {
        this.runtime = runtime;
        currentDateTime = LocalDateTime.now();
    }

    @Override
    public boolean process() {
        LocalDateTime runtimeDateTime = runtime.atOffset(ZoneOffset.UTC).toLocalDateTime();
        return currentDateTime.equals(runtimeDateTime);
    }

    public Instant getRuntime() {
        return runtime;
    }

    public void setRuntime(Instant runtime) {
        this.runtime = runtime;
    }

    public void setCurrentDateTime(LocalDateTime currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

}
