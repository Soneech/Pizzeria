package org.soneech.service;

import org.soneech.security.User;
import org.soneech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
            userRepository.save(user);
        }

        return report;
    }
}
