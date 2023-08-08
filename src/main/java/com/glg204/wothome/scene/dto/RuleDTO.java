package com.glg204.wothome.scene.dto;


public class RuleDTO {

    private String name;

    private TriggerExpressionDTO triggerExpressionDTO;

    private ActionDTO actionDTO;

    public RuleDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActionDTO getActionDTO() {
        return actionDTO;
    }

    public void setActionDTO(ActionDTO actionDTO) {
        this.actionDTO = actionDTO;
    }

    public TriggerExpressionDTO getTriggerExpressionDTO() {
        return triggerExpressionDTO;
    }

    public void setTriggerExpressionDTO(TriggerExpressionDTO triggerExpressionDTO) {
        this.triggerExpressionDTO = triggerExpressionDTO;
    }
}
