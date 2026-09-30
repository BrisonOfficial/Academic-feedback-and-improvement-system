package com.afims.repository;
import com.afims.entity.User;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmailIgnoreCase(String email);
    long countByRole(User.Role role);
    long countByStatus(User.RegistrationStatus status);
    List<User> findByRole(User.Role role);
}
