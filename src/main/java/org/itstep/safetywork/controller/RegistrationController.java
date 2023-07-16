package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder encoder;

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(CustomUser user) {
        if (user.isPasswordsEquals()) {
            userDetailsManager.createUser(User.builder()
                    .username(user.login)
                    .password(encoder.encode(user.password))
                    .roles(user.role)
                    .build());
            return "redirect:/register";
        }
        return "register";
    }

    public record CustomUser(String login, String password, String passwordConfirm, String role) {
        public boolean isPasswordsEquals() {
            return Objects.equals(password, passwordConfirm);
        }
    }
}
