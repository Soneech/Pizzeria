package org.soneech.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.soneech.models.Role;
import org.soneech.repository.RoleRepository;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @Test
    public void getRoleById() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        Mockito.when(roleRepository.getById(1L)).thenReturn(role);
        RoleService roleService = new RoleService(roleRepository);

        Assertions.assertEquals(roleService.getRoleById(1L).getName(), "ADMIN");
    }
}
