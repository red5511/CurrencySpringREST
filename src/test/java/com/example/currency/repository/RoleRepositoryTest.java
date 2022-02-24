package com.example.currency.repository;

import com.example.currency.entity.Currency;
import com.example.currency.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepositoryTest {
    @Autowired
    RoleRepository roleRepository;
    @Test
    public void shouldFindRoleByNameWhenExist(){

        String roleName = "Admin";
        Role role = Role.builder().name(roleName).build();
        roleRepository.save(role);
        assertNotNull(roleRepository.findByName(roleName));
    }
    @Test
    public void shouldFindRoleByNameWhenNotExist(){

        String roleName = "Admin";
        assertNull(roleRepository.findByName(roleName));
    }
}