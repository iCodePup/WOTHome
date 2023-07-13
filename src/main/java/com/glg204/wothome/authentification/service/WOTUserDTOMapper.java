package com.glg204.wothome.authentification.service;

import com.glg204.wothome.authentification.domain.WOTUser;
import com.glg204.wothome.authentification.domain.WOTUserRole;
import com.glg204.wothome.user.dto.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class WOTUserDTOMapper {

    public WOTUser fromCreationDTO(PasswordEncoder passwordEncoder, UserDTO userDTO) {
        WOTUser wotUser = new WOTUser(userDTO.getEmail(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                passwordEncoder.encode(userDTO.getPassword()),
                WOTUserRole.CLIENT);
        return wotUser;
    }
}
