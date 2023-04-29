package org.soneech.service;

import org.soneech.domain.User;
import org.soneech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public String register(User user) {
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            return "Пароли не совпадают";
        }
        else if (userRepository.findByEmail(user.getEmail()) != null) {
            return "Пользователь с такой почтой уже существует";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return null;
    }
}
