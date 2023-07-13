package com.glg204.wothome.config;

import com.glg204.wothome.authentification.domain.WOTUser;
import com.glg204.wothome.authentification.service.WOTUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    WOTUserService wotUserService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        WOTUser user = wotUserService.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
        return mapUserToUserDetails(user);
    }

    private UserDetails mapUserToUserDetails(WOTUser user) {
        User.UserBuilder builder = User.builder()
                .username(user.getUsername())
                .password(user.getPassword());
        HashSet<String> roles = new HashSet<>();
        switch (user.getRole()) {
            case CLIENT:
                roles.add("CLIENT");
            default:
                break;
        }
        builder.roles(roles.toArray(new String[roles.size()]));
        UserDetails details = builder.build();
        return details;
    }
}