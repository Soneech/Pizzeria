package org.soneech.service;

import org.soneech.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegistrationService {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserService userService,
                               PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Transactional
    public Map<String, String> checkAndRegister(User user) {
        Map<String, String> report = new HashMap<>();
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            report.put("passwordError", "Пароли не совпадают");
        }
        if (userService.getUserByPhoneNumber(user.getPhoneNumber()) != null) {
            report.put("existPhoneError", "Пользователь с таким номером телефона уже существует");
        }
        if (userService.getUserByName(user.getName()) != null) {
            report.put("existNameError", "Пользователь с таким именем уже существует");
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(roleService.getRoleById(2L)));

            userService.saveUser(user);
        }
        return report;
    }
}
