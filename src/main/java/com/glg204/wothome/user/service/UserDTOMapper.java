package com.glg204.wothome.user.service;

import com.glg204.wothome.authentification.domain.WOTUser;
import com.glg204.wothome.user.domain.User;
import com.glg204.wothome.user.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserDTOMapper {


    public User fromCreationDTO(UserDTO userDTO, WOTUser wotUser) {
        User user = new User(wotUser);
        fillFromDTO(user, userDTO);
        return user;
    }

    private void fillFromDTO(User user, UserDTO userDTO) {
        user.setAddress(userDTO.getAddress());
        user.setTelephone(userDTO.getTelephone());
    }

    public UserDTO toDTO(User user) {
      return new UserDTO(user.getWotUser().getEmail(),
              user.getWotUser().getFirstName(),
              user.getWotUser().getLastName(),
              user.getTelephone(),
              user.getAddress());
    }
}

