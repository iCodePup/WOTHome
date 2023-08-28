package com.glg204.wothome.domain;

import com.glg204.wothome.scene.domain.TriggerTimerExpression;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TriggerTimerExpressionTest {

    @Test
    public void testProcess_CurrentTimeEqualsRuntime() {
        Instant runtime = Instant.parse("2023-08-30T12:00:00Z");
        TriggerTimerExpression triggerExpression = new TriggerTimerExpression(runtime);
        triggerExpression.setCurrentDateTime(LocalDateTime.parse("2023-08-30T12:00:00"));
        boolean result = triggerExpression.process();
        assertTrue(result);
    }

    @Test
    public void testProcess_CurrentTimeBeforeRuntime() {
        Instant runtime = Instant.parse("2023-08-30T12:00:00Z");
        LocalDateTime currentTime = LocalDateTime.parse("2023-08-30T11:00:00");
        TriggerTimerExpression triggerExpression = new TriggerTimerExpression(runtime);
        triggerExpression.setCurrentDateTime(currentTime);
        boolean result = triggerExpression.process();
        assertFalse(result);
    }

    @Test
    public void testProcess_CurrentTimeAfterRuntime() {
        Instant runtime = Instant.parse("2023-08-30T12:00:00Z");
        LocalDateTime currentTime = LocalDateTime.parse("2023-08-30T11:00:00");
        TriggerTimerExpression triggerExpression = new TriggerTimerExpression(runtime);
        triggerExpression.setCurrentDateTime(currentTime);
        boolean result = triggerExpression.process();
        assertFalse(result);
    }
}
