package com.glg204.wothome.scene.service;


import com.glg204.wothome.scene.dto.RuleDTO;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface RuleService {

    boolean addRule(Principal principal, RuleDTO ruleDTO);

    List<RuleDTO> getRules(Principal principal);

    Optional<RuleDTO> getRuleById(Principal principal, Long id);

    boolean delete(Long id);
}
