package com.siteexample.attestation.controllers;

import com.siteexample.attestation.enums.Role;
import com.siteexample.attestation.enums.StructureUnitEnum;
import com.siteexample.attestation.models.User;
import com.siteexample.attestation.repo.UserRepository;
import com.siteexample.attestation.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin")
    public String admin(Principal principal, Model model) {
        model.addAttribute("users", userService.list());
        // Находим авторизованного пользователя
        User authuser = userService.getUserByPrincipal(principal);
        model.addAttribute("authuser", authuser);
        return "admin";
    }


    @GetMapping("/admin/user/{user}")
    public String userInfo(@PathVariable("user") User user, Principal principal, Model model) {
        model.addAttribute("user", user);
        // Находим авторизованного пользователя
        User authuser = userService.getUserByPrincipal(principal);
        model.addAttribute("authuser", authuser);
        return "user";
    }

    @GetMapping("/admin/user/edit/{user}")
    public String userEdit(@PathVariable("user") User user, Principal principal, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        // Находим авторизованного пользователя
        User authuser = userService.getUserByPrincipal(principal);
        model.addAttribute("authuser", authuser);
        // Создаем список для selectDepartment
        List<String> optionsDepartment = new ArrayList<String>();
        optionsDepartment.add("Служба охраны труда");
        optionsDepartment.add("Служба вагонного хозяйства");
        optionsDepartment.add("Служба пути");
        optionsDepartment.add("Служба автоматики и телемеханики");
        model.addAttribute("optionsDepartment", optionsDepartment);
        // Создаем список для selectStructureUnit
        model.addAttribute("optionsStructureUnit", Arrays.stream(StructureUnitEnum.values()).toList());
        return "user-edit";
    }

    @PostMapping("/admin/user/edit/{userId}")
    public String userEdit(
            @RequestParam(value = "userId") long id,
            @RequestParam String roles,
            @RequestParam String department,
            @RequestParam String unit,
            @RequestParam String surname,
            @RequestParam String name,
            @RequestParam String patronymic,
            @RequestParam("file") MultipartFile file,
            Principal principal,
            Model model) throws IOException {
        User user = userRepository.findById(id).orElseThrow();
        userService.userEdit(user, roles, department, unit, surname, name, patronymic, file);
        // Находим авторизованного пользователя
        User authuser = userService.getUserByPrincipal(principal);
        model.addAttribute("authuser", authuser);
        return "redirect:/admin";
    }


    @PostMapping("/admin/user/ban/{id}")
    public String userBan(@PathVariable("id") long id) {
        userService.banUser(id);
        return "redirect:/admin";
    }

}
