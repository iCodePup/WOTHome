package com.glg204.wothome.webofthings.dao;

import com.glg204.wothome.user.domain.User;
import com.glg204.wothome.webofthings.domain.Thing;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ThingDAO {
    Collection<Thing> getUserThings(User user);

    Optional<Thing> findByURL(String url);

    boolean setThingAlive(String url, boolean b);

    void save(Thing thing);
}
