package com.siteexample.attestation.controllers;

import com.siteexample.attestation.enums.Role;
import com.siteexample.attestation.enums.StructureUnitEnum;
import com.siteexample.attestation.models.User;
import com.siteexample.attestation.repo.PostRepository;
import com.siteexample.attestation.repo.UserRepository;
import com.siteexample.attestation.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequiredArgsConstructor
public class UserController {

    @Value("${upload.path}")
    private String uploadPath;

    private final UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Пользователь с email: " + user.getEmail() + " уже существует");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/userInfo")
    public String userInfoItself(Principal principal, Model model) {
        // Находим авторизованного пользователя и передаем на страницу
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("authuser", user);   // Для Header'a
        model.addAttribute("user", user);       // Для страницы в общем
        return "user";
    }

    // Изменение своих данных (не для админа)
    @GetMapping("/user/edit")
    public String userEdit(Principal principal, Model model) {
        // Передаем пользователя для изменения
        User user = userService.getUserByPrincipal(principal);
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

    @PostMapping("/user/edit")
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
        return "redirect:/userInfo";
    }


}
