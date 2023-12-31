package com.glg204.wothome.webofthings.dao;

import com.glg204.wothome.house.domain.Room;
import com.glg204.wothome.user.domain.User;
import com.glg204.wothome.webofthings.domain.Thing;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ThingDAO {
    Collection<Thing> getUserThings(User user);

    Optional<Thing> getByURL(String url);

    Optional<Thing> getById(Long id);

    boolean setThingAlive(String name, String url, boolean b);

    void save(Thing thing);

    Optional<Thing> getByName(String name);

    List<Thing> getThings();

    boolean setThingRoom(Long thingId, Long roomId);

    List<Thing> getThingsByRoom(User currentUser, Room room);
}
