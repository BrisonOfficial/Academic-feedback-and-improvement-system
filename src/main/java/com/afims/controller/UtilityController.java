package com.afims.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.afims.entity.User;
import com.afims.repository.NotificationRepository;
import com.afims.repository.UserRepository;
@Controller
public class UtilityController {
    private final UserRepository users;
    private final NotificationRepository notifications;
    public UtilityController(            UserRepository users,            NotificationRepository notifications) {
        this.users = users;
        this.notifications = notifications;
    }
    // Get currently logged-in user
    private User me(            org.springframework.security.core.Authentication authentication) {
        return users                .findByEmailIgnoreCase(authentication.getName())                .orElseThrow();
    }
    // Notifications page
    @GetMapping("/notifications")    public String notifications(            Model model,            org.springframework.security.core.Authentication authentication) {
        User user = me(authentication);
        model.addAttribute(                "items",                notifications.findTop20ByUserIdOrderByCreatedAtDesc(                        user.getId()                )        );
        return "notifications";
    }
    // Mark notification as read
    @PostMapping("/notifications/{id}/read")    public String read(
    @PathVariable Long id,            org.springframework.security.core.Authentication authentication) {
        var notification                = notifications.findById(id)                        .orElseThrow();
        User user = me(authentication);
        if (notification.getUser().getId().equals(user.getId())) {
            notification.setReadFlag(true);
            notifications.save(notification);
        }
        return "redirect:/notifications";
    }
    // Profile page
    @GetMapping("/profile")    public String profile(            Model model,            org.springframework.security.core.Authentication authentication) {
        model.addAttribute(                "user",                me(authentication)        );
        return "profile";
    }
}
