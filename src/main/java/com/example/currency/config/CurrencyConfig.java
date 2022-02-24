package com.example.currency.config;

import com.example.currency.entity.Role;
import com.example.currency.entity.UserApp;
import com.example.currency.repository.RoleRepository;
import com.example.currency.repository.UserAppRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CurrencyConfig {
    @Autowired
    UserAppRepository userAppRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    @Bean
    CommandLineRunner commandLineRunner(RoleRepository roleRepository){
        return args -> {

            Role admin =  new Role(null, "ROLE_Admin");
            Role user =   new Role(null, "ROLE_User");
            Role mod =   new Role(null, "ROLE_Moderator");
            roleRepository.saveAll(List.of(admin, user, mod));

            Role roles = roleRepository.findByName("ROLE_Admin");
            Role roles2 = roleRepository.findByName("ROLE_User");

            System.out.println(roles);

            UserApp maciek = UserApp.builder().nickname("Maciek").password("pass5").roles(Arrays.asList(roles)).build();
            userAppRepository.save(maciek);
            System.out.println(maciek);

        };


    }
}
