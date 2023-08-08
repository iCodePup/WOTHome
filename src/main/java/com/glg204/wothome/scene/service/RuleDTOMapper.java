package com.glg204.wothome.scene.service;

import com.glg204.wothome.scene.domain.Rule;
import com.glg204.wothome.scene.dto.RuleDTO;
import com.glg204.wothome.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class RuleDTOMapper {
    public Rule fromDTO(RuleDTO ruleDTO, User currentUser) {

        Rule rule = new Rule(ruleDTO.getName(), currentUser);

        rule.setAction();
        rule.setTriggerExpression();
        return rule;
    }
}
