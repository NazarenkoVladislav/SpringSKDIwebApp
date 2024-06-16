package com.siteexample.attestation.services;

import com.siteexample.attestation.enums.Role;
import com.siteexample.attestation.models.User;
import com.siteexample.attestation.repo.UserRepository;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    @Value("${upload.path}")
    private String uploadPath;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) {
            return false;
        }
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_ADMIN);
        // Сохраняем пользователя в базу
        userRepository.save(user);
        return true;
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (user.isActive()) {
                // Бан пользователя
                user.setActive(false);
            } else {
                // Разбан пользователя
                user.setActive(true);
            }
        }
        // Сохраняем его статус
        assert user != null;
        userRepository.save(user);
    }

    // Возвращаем авторизованного пользователя
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public void userEdit(
            User user,
            String roles,
            String department,
            String unit,
            String surname,
            String name,
            String patronymic,
            MultipartFile file) throws IOException {
        // Преобразуем все роли в Сет строк
//        Set<String> roles = Arrays.stream(Role.values())
//                .map(Role::name)
//                .collect(Collectors.toSet());
        // очищаем все роли пользователя
        user.getRoles().clear();
//        for (String key : form.keySet()) {
//            if (roles.contains(key)) {
//                user.getRoles().add(Role.valueOf(key));
//            }
//        }
        user.getRoles().add(Role.valueOf(roles));
        user.setDepartment(department);
        user.setUnit(unit);
        user.setSurname(surname);
        user.setName(name);
        user.setPatronymic(patronymic);
        // Загружаем файл
        if (!file.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadDir.getAbsolutePath() + "/" + resultFileName));
            // Ложим файл в юзера
            user.setAvatar(resultFileName);
        }
        userRepository.save(user);
    }
}
