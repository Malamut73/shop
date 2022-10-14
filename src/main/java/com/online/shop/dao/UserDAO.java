package com.online.shop.dao;

import com.online.shop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Long> {
    User findFirstByName(String name);
    User findFirstByActiveCode(String activateCode);
}
