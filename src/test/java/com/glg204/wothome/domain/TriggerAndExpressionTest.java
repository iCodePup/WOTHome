package com.glg204.wothome.domain;


import com.glg204.wothome.scene.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TriggerAndExpressionTest {

    @Test
    public void testProcess_BothTrue() {
        TriggerExpression firstExpression = new TriggerExpression() {
            @Override
            public boolean process() {
                return true;
            }
        };
        TriggerExpression secondExpression = new TriggerExpression() {
            @Override
            public boolean process() {
                return true;
            }
        };

        TriggerAndExpression andExpression = new TriggerAndExpression(firstExpression, secondExpression);
        assertTrue(andExpression.process());
    }

    @Test
    public void testProcess_FirstFalse() {
        TriggerExpression firstExpression = new TriggerExpression() {
            @Override
            public boolean process() {
                return false;
            }
        };
        TriggerExpression secondExpression = new TriggerExpression() {
            @Override
            public boolean process() {
                return true;
            }
        };

        TriggerAndExpression andExpression = new TriggerAndExpression(firstExpression, secondExpression);
        assertFalse(andExpression.process());
    }

    @Test
    public void testProcess_SecondFalse() {
        TriggerExpression firstExpression = new TriggerExpression() {
            @Override
            public boolean process() {
                return true;
            }
        };
        TriggerExpression secondExpression = new TriggerExpression() {
            @Override
            public boolean process() {
                return false;
            }
        };

        TriggerAndExpression andExpression = new TriggerAndExpression(firstExpression, secondExpression);
        assertFalse(andExpression.process());
    }

    @Test
    public void testProcess_BothFalse() {
        TriggerExpression firstExpression = new TriggerExpression() {
            @Override
            public boolean process() {
                return false;
            }
        };
        TriggerExpression secondExpression = new TriggerExpression() {
            @Override
            public boolean process() {
                return false;
            }
        };

        TriggerAndExpression andExpression = new TriggerAndExpression(firstExpression, secondExpression);
        assertFalse(andExpression.process());
    }
}


