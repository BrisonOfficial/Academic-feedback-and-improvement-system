package com.afims.controller;

import com.afims.dto.RegisterRequest;
import com.afims.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final UserService users;

    public HomeController(UserService users) {
        this.users = users;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("form", new RegisterRequest());
        return "auth/register";
    }

    @PostMapping("/register")
    public String doRegister(
            @Valid @ModelAttribute("form") RegisterRequest form,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            users.register(form);
            model.addAttribute(
                    "success",
                    "Registration submitted. An administrator must activate the account before login."
            );
            return "auth/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {

        String role = authentication
                .getAuthorities()
                .stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .orElse("");

        return switch (role) {
            case "ROLE_STUDENT" -> "redirect:/student/dashboard";
            case "ROLE_FACULTY" -> "redirect:/faculty/dashboard";
            case "ROLE_HOD" -> "redirect:/hod/dashboard";
            case "ROLE_ADMIN" -> "redirect:/admin/dashboard";
            default -> "redirect:/";
        };
    }
}
