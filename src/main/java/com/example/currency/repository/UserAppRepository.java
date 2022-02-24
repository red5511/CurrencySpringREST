package com.example.currency.repository;

import com.example.currency.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAppRepository extends JpaRepository<UserApp, Long> {
    UserApp findByNickname(String nick);
}
