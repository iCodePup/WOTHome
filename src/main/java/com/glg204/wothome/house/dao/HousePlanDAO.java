package com.glg204.wothome.house.dao;

import com.glg204.wothome.house.domain.HousePlan;
import com.glg204.wothome.user.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HousePlanDAO {
    Optional<HousePlan> getHousePlan(User value);

    HousePlan createHousePlan(User user);
}
