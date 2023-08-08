package com.glg204.wothome.scene.service;

import com.glg204.wothome.scene.dao.RuleDAO;
import com.glg204.wothome.scene.domain.Rule;
import com.glg204.wothome.scene.dto.RuleDTO;
import com.glg204.wothome.user.dao.UserDAO;
import com.glg204.wothome.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    RuleDAO ruleDAO;

    @Autowired
    RuleDTOMapper ruleDTOMapper;


    @Override
    public boolean addRule(Principal principal, RuleDTO ruleDTO) {

        Optional<User> user = userDAO.getUserByEmail(principal.getName());
        user.map(currentUser -> {
                    Rule r = ruleDTOMapper.fromDTO(ruleDTO, currentUser);
                    ruleDAO.save(r);

                    return true;
                }


        );


        System.out.println(ruleDTO.getName());
        return false;
    }
}
