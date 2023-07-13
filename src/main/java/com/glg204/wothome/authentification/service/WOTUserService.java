package com.glg204.wothome.authentification.service;

import com.glg204.wothome.authentification.domain.WOTUser;
import com.glg204.wothome.user.dto.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public interface WOTUserService {

    public Optional<WOTUser> getUserByUsername(String username);

    public boolean existsByEmail(String email);

    public WOTUser save(PasswordEncoder passwordEncoder,UserDTO userDTO);
}
