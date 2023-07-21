package com.glg204.wothome.house.service;

import com.glg204.wothome.house.dao.HousePlanDAO;
import com.glg204.wothome.house.dao.RoomDAO;
import com.glg204.wothome.house.domain.Room;
import com.glg204.wothome.house.dto.RoomDTO;
import com.glg204.wothome.user.dao.UserDAO;
import com.glg204.wothome.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HousePlanServiceImpl implements HousePlanService {

    @Autowired
    HousePlanDAO housePlanDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    RoomDAO roomDAO;

    @Autowired
    RoomDTOMapper roomDTOMapper;

    @Override
    public List<RoomDTO> getRooms(Principal principal) {
        Optional<User> user = userDAO.getUserByEmail(principal.getName());
        return user.map(currentUser -> housePlanDAO.getHousePlan(currentUser)
                .map(housePlan -> roomDAO.getRoomsByHousePlan(housePlan)
                        .stream().map(room -> roomDTOMapper.toDTO(room)).collect(Collectors.toList())).orElseGet(() -> {
                    housePlanDAO.createHousePlan(currentUser);
                    return new ArrayList<>();
                })).orElseGet(ArrayList::new);
    }
}
