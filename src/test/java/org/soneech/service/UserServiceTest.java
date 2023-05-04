package org.soneech.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.soneech.repository.UserRepository;
import org.soneech.models.User;

import java.util.List;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Captor
    ArgumentCaptor<User> captor;

    @Test
    public void loadUserById() {
        User user = new User();
        user.setId(1L);
        user.setName("Soneech");

        Mockito.when(userRepository.getById(1L)).thenReturn(user);
        UserService userService = new UserService(userRepository);

        Assertions.assertEquals(userService.loadUserById(1L).getName(), "Soneech");
    }

    @Test
    public void loadUserByName() {
        User user = new User();
        user.setName("Soneech");

        Mockito.when(userRepository.findByName("Soneech")).thenReturn(user);
        UserService userService = new UserService(userRepository);

        Assertions.assertEquals(userService.loadUserByName("Soneech").getName(), "Soneech");
    }

    @Test
    public void saveUser() {
        User user = new User();
        user.setName("Soneech");
        user.setEmail("soneech@gmail.com");

        UserService userService = new UserService(userRepository);
        userService.saveUser(user);

        Mockito.verify(userRepository).save(captor.capture());
        User captured = captor.getValue();
        Assertions.assertEquals("Soneech", captured.getName());
    }

    @Test
    public void getAllUsers() {
        User user = new User();
        user.setName("Katya");
        User user1 = new User();
        user1.setName("Vasya");

        Mockito.when(userRepository.findAll()).thenReturn(List.of(user, user1));
        UserService userService = new UserService(userRepository);

        Assertions.assertEquals(2, userService.getAllUsers().size());
        Assertions.assertEquals("Katya", userService.getAllUsers().get(0).getName());
    }
}
