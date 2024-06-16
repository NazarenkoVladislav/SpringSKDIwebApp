package com.siteexample.attestation.controllers;

import com.siteexample.attestation.models.User;
import com.siteexample.attestation.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    @GetMapping("/")
    public String greeting(Principal principal, Model model) {
        // Находим авторизованного пользователя и передаем на страницу
        User authuser = userService.getUserByPrincipal(principal);
        model.addAttribute("authuser", authuser);
        return "greeting";
    }

    @GetMapping("/home")
    public String home(Principal principal, Model model) {
        model.addAttribute("title", "Главная страница");
        // Передаем информацию об открытой странице: 0 - Главная
        int page = 0;
        model.addAttribute("page", page);
        // Находим авторизованного пользователя и передаем на страницу
        User authuser = userService.getUserByPrincipal(principal);
        model.addAttribute("authuser", authuser);
        return "home";
    }

    @GetMapping("/contracts")
    public String contracts(Principal principal, Model model) {
        model.addAttribute("title", "Договоры");
        // Передаем информацию об открытой странице: 2 - Договоры
        int page = 2;
        model.addAttribute("page", page);
        // Находим авторизованного пользователя и передаем на страницу
        User authuser = userService.getUserByPrincipal(principal);
        model.addAttribute("authuser", authuser);
        return "contracts";
    }

}
