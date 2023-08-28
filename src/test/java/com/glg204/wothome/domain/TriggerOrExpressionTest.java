package com.glg204.wothome.domain;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.glg204.wothome.scene.domain.*;


class TriggerOrExpressionTest {

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

        TriggerOrExpression orExpression = new TriggerOrExpression(firstExpression, secondExpression);
        assertTrue(orExpression.process());
    }

    @Test
    public void testProcess_FirstTrue() {
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

        TriggerOrExpression orExpression = new TriggerOrExpression(firstExpression, secondExpression);
        assertTrue(orExpression.process());
    }

    @Test
    public void testProcess_SecondTrue() {
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

        TriggerOrExpression orExpression = new TriggerOrExpression(firstExpression, secondExpression);
        assertTrue(orExpression.process());
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

        TriggerOrExpression orExpression = new TriggerOrExpression(firstExpression, secondExpression);
        assertFalse(orExpression.process());
    }
}

