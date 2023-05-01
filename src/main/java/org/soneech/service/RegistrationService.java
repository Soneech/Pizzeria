package org.soneech.service;

import org.soneech.security.User;
import org.soneech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository,
                               PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Transactional
    public Map<String, String> checkAndRegister(User user) {
        Map<String, String> report = new HashMap<>();
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            report.put("passwordError", "Пароли не совпадают");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            report.put("existError", "Пользователь с такой почтой уже существует");
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(roleService.getRoleById(1L)));

            userRepository.save(user);
        }

        return report;
    }
}
