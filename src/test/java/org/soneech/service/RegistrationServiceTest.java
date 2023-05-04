package org.soneech.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.soneech.models.Role;
import org.soneech.models.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private RoleService roleService;

    @Test
    public void checkAndRegister() {
        User user = new User();
        user.setId(1L);
        user.setName("Soneech");
        user.setPassword("password");

        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        user.setRoles(Collections.singleton(role));

        Mockito.when(roleService.getRoleById(1L)).thenReturn(role);
        String encodedPassword = "encoded-password";

        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn(encodedPassword);
        RegistrationService registrationService = new RegistrationService(userService, passwordEncoder, roleService);
        registrationService.checkAndRegister(user);

        Mockito.verify(userService).saveUser(user);
        Assertions.assertEquals(encodedPassword, user.getPassword());
    }
}
