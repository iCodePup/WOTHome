package com.glg204.wothome.house.dao;

import com.glg204.wothome.house.domain.HousePlan;
import com.glg204.wothome.house.domain.Room;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomDAO {
    List<Room> getRoomsByHousePlan(HousePlan housePlan);
}
