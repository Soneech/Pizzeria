package org.soneech.controllers;

import org.soneech.domain.Role;
import org.soneech.domain.User;
import org.soneech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class AuthController {
    private final UserService userService;
    private final String emailErrorMessage = "Пользователь с такой почтой уже существует";
    private final String passwordErrorMessage = "Пароли не совпадают";

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "/auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User user, Model model) {
        User userFromDataBase = userService.loadUserByEmail(user.getEmail());

        if (userFromDataBase != null) {
            model.addAttribute("errorMessage", emailErrorMessage);
            return "/auth/registration";
        }

        else if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("errorMessage", passwordErrorMessage);
            return "/auth/registration";
        }

        //user.setRoles(Collections.singleton(Role.USER));
        userService.saveUser(user);
        return "redirect:/login";
    }
}
