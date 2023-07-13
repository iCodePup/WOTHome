package com.glg204.wothome.user.service;

import com.glg204.wothome.authentification.exception.EmailAlreadyExistsException;
import com.glg204.wothome.user.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.security.Principal;
import java.util.List;


@Service
@Validated
public interface UserService {

    public Integer save(PasswordEncoder passwordEncoder, @Valid UserDTO userDTO) throws EmailAlreadyExistsException;

}
