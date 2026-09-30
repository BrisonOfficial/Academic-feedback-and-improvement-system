package com.afims.config;

import com.afims.entity.User;
import com.afims.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoAccountInitializer {

    private static final String DEMO_PASSWORD = "password";

    @Bean
    public CommandLineRunner initializeDemoAccounts(UserRepository users) {
        return args -> {
            upsert(users, "AFIMS Admin", "admin@afims.local", "ADMIN");
            upsert(users, "AFIMS Faculty", "faculty@afims.local", "FACULTY");
            upsert(users, "AFIMS HOD", "hod@afims.local", "HOD");
            upsert(users, "AFIMS Student", "student@afims.local", "STUDENT");
        };
    }

    private void upsert(
            UserRepository users,
            String fullName,
            String email,
            String roleName) {

        User user = users.findByEmailIgnoreCase(email)
                .orElseGet(User::new);

        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone("9000000000");
        // Academic/demo mode: store the demo password directly.
        user.setPassword(DEMO_PASSWORD);
        user.setRole(User.Role.valueOf(roleName));
        user.setStatus(User.RegistrationStatus.ACTIVE);

        if (user.getInstitution() == null) {
            user.setInstitution("AFIMS Demo University");
        }
        if (user.getDepartment() == null) {
            user.setDepartment("Computer Science and Engineering");
        }
        if (user.getInstitutionId() == null) {
            user.setInstitutionId("AFIMS-001");
        }

        users.save(user);
    }
}
