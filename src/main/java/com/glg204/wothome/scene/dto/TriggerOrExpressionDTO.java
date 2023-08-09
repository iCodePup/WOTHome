package com.glg204.wothome.scene.dto;

public class TriggerOrExpressionDTO extends TriggerExpressionDTO {

    private TriggerExpressionDTO firstExpression;

    private TriggerExpressionDTO secondExpression;


    public TriggerOrExpressionDTO() {
    }

    public TriggerOrExpressionDTO(TriggerExpressionDTO firstExpression, TriggerExpressionDTO secondExpression) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
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
