package com.online.shop.service;

import com.online.shop.domain.User;
import com.online.shop.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

//  for security
public interface UserService extends UserDetailsService {
    boolean save(UserDTO userDTO);
    void save(User user);
    List<UserDTO> getAll();
    User findByName(String name);
    void updateProfile(UserDTO userDTO);
}
