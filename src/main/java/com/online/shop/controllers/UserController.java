package com.online.shop.controllers;


import com.online.shop.domain.User;
import com.online.shop.dto.UserDTO;
import com.online.shop.service.UserService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userService.getAll());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String newUser(Model model){
        System.out.println("called method newUser");
        model.addAttribute("user", new UserDTO());
        return "user";
    }

    @PostAuthorize("isAuthenticated() and #username == authentication.principal.username")
    @GetMapping("{name}/roles")
    @ResponseBody
    public String getRoles(@PathVariable("name") String username){
        System.out.println("called method getRole");
        User byName = userService.findByName(username);
        return byName.getRole().name();
    }

    @PostMapping("/new")
    public String saveUser(UserDTO userDTO, Model model){
        System.out.println("We've entered in a controller");

        if(userService.save(userDTO)){
            return "redirect:/users";
        }else{
            model.addAttribute("user", userDTO);
            return "user";
        }
    }

    @GetMapping("/profile")
    public String profileUser(Model model,
                              Principal principal){
        if(principal== null){
            throw new RuntimeException("Yoy are not authorize");
        }
        User user = userService.findByName(principal.getName());

        UserDTO userDTO = UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
        model.addAttribute("user", userDTO);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfileUser(UserDTO userDTO,
                                    Model model,
                                    Principal principal){
        if(principal == null || !Objects.equals(principal.getName(), userDTO.getUsername())){
            throw new RuntimeException("You are not authorize");
        }
        if(userDTO.getPassword() != null
            && !userDTO.getPassword().isEmpty()
            && !Objects.equals(userDTO.getPassword(), userDTO.getMatchPassword())){
            model.addAttribute("user", userDTO);
            return "profile";
        }

        userService.updateProfile(userDTO);
        return "redirect:/users/profile";
    }

}
