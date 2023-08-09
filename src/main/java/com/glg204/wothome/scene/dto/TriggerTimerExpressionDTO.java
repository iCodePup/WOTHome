package com.glg204.wothome.scene.dto;


import java.time.Instant;

public class TriggerTimerExpressionDTO extends TriggerExpressionDTO {

    private Instant runtime;

    public TriggerTimerExpressionDTO() {
    }

    public TriggerTimerExpressionDTO(Instant runtime) {
        this.runtime = runtime;
    }

    public Instant getRuntime() {
        return runtime;
    }

    public void setRuntime(Instant runtime) {
        this.runtime = runtime;
    }
}
