package org.itstep.safetywork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class LoginController {


        private final UserDetailsManager userDetailsManager;
        private final PasswordEncoder encoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @ResponseBody
    @GetMapping("/encode")
    public String encode(String password) {
        return encoder.encode(password);
    }
}
