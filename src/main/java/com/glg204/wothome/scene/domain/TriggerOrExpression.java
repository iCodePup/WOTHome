package com.glg204.wothome.scene.domain;

public class TriggerOrExpression extends TriggerExpression{

    private TriggerExpression firstExpression;

    private TriggerExpression secondExpression;

    public TriggerOrExpression() {
    }

    public TriggerOrExpression(TriggerExpression firstExpression, TriggerExpression secondExpression) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
    }

    public TriggerExpression getFirstExpression() {
        return firstExpression;
    }

    public void setFirstExpression(TriggerExpression firstExpression) {
        this.firstExpression = firstExpression;
    }

    public TriggerExpression getSecondExpression() {
        return secondExpression;
    }

    public void setSecondExpression(TriggerExpression secondExpression) {
        this.secondExpression = secondExpression;
    }

    @Override
    public void process() {

    }
}
