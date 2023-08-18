package com.glg204.wothome.scene.dao;


import com.glg204.wothome.scene.domain.Rule;
import com.glg204.wothome.user.domain.User;

import java.util.List;

public interface RuleDAO {

    List<Rule> getRules();

    List<Rule> getRules(User currentUser);

    Rule getRuleById(User currentUser, Long id);

    Long save(Rule r);
}
