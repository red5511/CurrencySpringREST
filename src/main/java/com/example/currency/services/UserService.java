package com.example.currency.services;

import com.example.currency.entity.UserApp;
import com.example.currency.repository.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserAppRepository userAppRepository;

    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(PasswordEncoder pass) {
        passwordEncoder = pass;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserApp userApp = userAppRepository.findByNickname(s);
        if (userApp == null){
            throw new UsernameNotFoundException("User o podanych danych nie instniej!");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userApp.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(userApp.getNickname(), passwordEncoder.encode(userApp.getPassword()), authorities);
    }
}
