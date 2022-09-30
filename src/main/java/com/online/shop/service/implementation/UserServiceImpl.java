package com.online.shop.service.implementation;

import com.online.shop.dao.UserDAO;
import com.online.shop.domain.User;
import com.online.shop.domain.enums.Role;
import com.online.shop.dto.UserDTO;
import com.online.shop.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean save(UserDTO userDTO) {
        if (!Objects.equals(userDTO.getPassword(), userDTO.getMatchPassword())) {
            throw new RuntimeException("Password is not equals");
        }
        User user = User.builder()
                .name(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .role(Role.CLIENT)
                .build();
        userDAO.save(user);
        return true;
    }

    public void save(User user) {
        userDAO.save(user);
    }

    public List<UserDTO> getAll() {
        return userDAO.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public User findByName(String name) {
        return userDAO.findFirstByName(name);
    }

    @Transactional
    public void updateProfile(UserDTO userDTO) {
        User savedUser = userDAO.findFirstByName(userDTO.getUsername());
        if(savedUser == null){
            throw new RuntimeException("User mot found by name" + userDTO.getUsername());
        }

        boolean isChanged = false;
        if(userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()){
            savedUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            isChanged = true;
        }


        if(!Objects.equals(userDTO.getEmail(), savedUser.getEmail())){
            savedUser.setEmail(userDTO.getEmail());
            isChanged = true;
        }

        if(isChanged){
            userDAO.save(savedUser);
        }

    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userDAO.findFirstByName(username);
            if(user == null){
                throw new UsernameNotFoundException("User not found with name:" + username);
            }

        List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority(user.getRole().name()));
            return new org.springframework.security.core.userdetails.User(
                    user.getName(),
                    user.getPassword(),
                    roles
            );

    }

    private UserDTO toDto(User user) {
        return UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
    }
}
