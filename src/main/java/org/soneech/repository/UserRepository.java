package org.soneech.repository;

import org.soneech.models.Role;
import org.soneech.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByPhoneNumber(String email);
    User findByName(String name);

    @Query("SELECT u FROM User u WHERE u.roles in :roles")
    List<User> findByRoles(List<Role> roles);
}
