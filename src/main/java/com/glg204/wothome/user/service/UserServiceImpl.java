package com.glg204.wothome.user.service;

import com.glg204.wothome.authentification.domain.WOTUser;
import com.glg204.wothome.authentification.exception.EmailAlreadyExistsException;
import com.glg204.wothome.authentification.service.WOTUserService;
import com.glg204.wothome.user.dao.UserDAO;
import com.glg204.wothome.user.domain.User;
import com.glg204.wothome.user.dto.UserDTO;
import com.glg204.wothome.webofthings.dao.ThingDAO;
import com.glg204.wothome.webofthings.dto.ThingDTO;
import com.glg204.wothome.webofthings.service.ThingDTOMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Validated
public class UserServiceImpl implements UserService {

    @Autowired
    WOTUserService wotUserService;

    @Autowired
    UserDAO userDAO;

    @Autowired
    ThingDAO thingDAO;

    @Autowired
    ThingDTOMapper thingDTOMapper;

    @Autowired
    UserDTOMapper clientDTOMapper;

    @Override
    public Integer save(PasswordEncoder passwordEncoder, @Valid UserDTO userDTO) throws EmailAlreadyExistsException {
        if (wotUserService.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException();
        } else {
            WOTUser wotUser = wotUserService.save(passwordEncoder, userDTO);
            // Creates the corresponding user.
            return userDAO.save(clientDTOMapper.fromCreationDTO(userDTO, wotUser));
        }
    }

    @Override
    public List<ThingDTO> getUserThings(Principal p) {
        Optional<User> user = userDAO.getUserByEmail(p.getName());
        return user.map(value -> thingDAO.getUserThings(value).stream()
                .map(thing -> thingDTOMapper.toDTO(thing)).collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @Override
    public boolean addThingToUser(Principal p, Long thingId) {
        Optional<User> client = userDAO.getUserByEmail(p.getName());
        return client.filter(value -> userDAO.setUserToThing(thingId, value.getId())).isPresent();
    }
}
