package com.glg204.wothome.scene.dto;


import jakarta.validation.constraints.NotNull;

public class RuleDTO {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private TriggerExpressionDTO triggerExpressionDTO;

    @NotNull
    private ActionDTO actionDTO;

    public RuleDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
