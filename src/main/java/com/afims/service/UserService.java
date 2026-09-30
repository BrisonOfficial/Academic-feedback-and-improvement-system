package com.afims.service;

import com.afims.dto.RegisterRequest;
import com.afims.entity.User;
import com.afims.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    final UserRepository repo;

    public UserService(UserRepository r) {
        repo = r;
    }

    public User register(RegisterRequest x) {
        if (repo.findByEmailIgnoreCase(x.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        if (!x.getPassword().equals(x.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        User u = new User();
        u.setFullName(x.getFullName());
        u.setEmail(x.getEmail().toLowerCase());
        u.setPhone(x.getPhone());
        // Academic/demo mode: store the entered password directly.
        u.setPassword(x.getPassword());
        u.setInstitution(x.getInstitution());
        u.setDepartment(x.getDepartment());
        u.setInstitutionId(x.getInstitutionId());
        u.setRole(x.getRole());
        u.setStatus(User.RegistrationStatus.PENDING);
        return repo.save(u);
    }

    public User byEmail(String e) {
        return repo.findByEmailIgnoreCase(e).orElseThrow();
    }
}
