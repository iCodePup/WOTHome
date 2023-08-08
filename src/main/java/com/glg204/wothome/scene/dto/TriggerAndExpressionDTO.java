package com.glg204.wothome.scene.dto;

public class TriggerAndExpressionDTO extends TriggerExpressionDTO {

    private TriggerExpressionDTO firstExpression;

    private TriggerExpressionDTO secondExpression;


    public TriggerAndExpressionDTO() {
    }


    public TriggerExpressionDTO getFirstExpression() {
        return firstExpression;
    }

    public void setFirstExpression(TriggerExpressionDTO firstExpression) {
        this.firstExpression = firstExpression;
    }

    public TriggerExpressionDTO getSecondExpression() {
        return secondExpression;
    }

    public void setSecondExpression(TriggerExpressionDTO secondExpression) {
        this.secondExpression = secondExpression;
    }
}
