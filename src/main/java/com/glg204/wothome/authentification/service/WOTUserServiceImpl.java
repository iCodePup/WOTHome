package com.glg204.wothome.authentification.service;

import com.glg204.wothome.authentification.dao.WOTUserDAO;
import com.glg204.wothome.authentification.domain.WOTUser;
import com.glg204.wothome.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WOTUserServiceImpl implements WOTUserService {

    @Autowired
    WOTUserDAO wotUserDAO;

    @Autowired
    WOTUserDTOMapper wotUserDTOMapper;

    @Override
    public Optional<WOTUser> getUserByUsername(String username) throws UsernameNotFoundException {
        Optional<WOTUser> optYapsUser = wotUserDAO.findByEmail(username);
        if (optYapsUser.isPresent()) {
            WOTUser wotUser = optYapsUser.get();
            return Optional.of(wotUser);
//            User.UserBuilder builder = User.builder()
//                    .username(username)
//                    .password(wotUser.getPassword());
//            HashSet<String> roles = new HashSet<>();
//
//            switch (wotUser.getRole()) {
//                case ADMIN:
//                    roles.add("ADMIN");
//                    break;
//                case CLIENT:
//                    roles.add("CLIENT");
//                default:
//                    break;
//            }
//            builder.roles(roles.toArray(new String[roles.size()]));
//            UserDetails details = builder.build();
//            return details;
        } else {
            return Optional.empty();
        }
    }


    public boolean existsByEmail(String email) {
        return wotUserDAO.existsByEmail(email);
    }

    public WOTUser save(PasswordEncoder passwordEncoder,UserDTO userDTO) {

        WOTUser wotUser = wotUserDTOMapper.fromCreationDTO(passwordEncoder,userDTO);
        wotUserDAO.save(wotUser);
        return wotUser;
    }
}