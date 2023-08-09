package com.glg204.wothome.scene.dao;


import com.glg204.wothome.scene.domain.Rule;
import com.glg204.wothome.user.domain.User;

import java.util.List;

public interface RuleDAO {

    List<Rule> getAllRules(User currentUser);

    Long save(Rule r);
}
