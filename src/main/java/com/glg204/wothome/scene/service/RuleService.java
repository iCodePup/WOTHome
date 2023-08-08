package com.glg204.wothome.scene.service;


import com.glg204.wothome.scene.dto.RuleDTO;

import java.security.Principal;

public interface RuleService {

    boolean addRule(Principal principal, RuleDTO ruleDTO);
}
