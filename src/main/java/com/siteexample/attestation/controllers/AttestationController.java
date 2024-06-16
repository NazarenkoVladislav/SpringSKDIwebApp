package com.siteexample.attestation.controllers;

import com.siteexample.attestation.enums.DepartmentEnum;
import com.siteexample.attestation.enums.ProfessionCategoryEnum;
import com.siteexample.attestation.enums.StructureUnitEnum;
import com.siteexample.attestation.models.Post;
import com.siteexample.attestation.models.User;
import com.siteexample.attestation.repo.PostRepository;
import com.siteexample.attestation.services.AttestationService;
import com.siteexample.attestation.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class AttestationController {

    private final UserService userService;
    private final AttestationService attestationService;

    // Создаем список аттестуемых (пока пустой, затем заполним)
    List<Post> posts = new ArrayList<>();;

    // Передаем все записи из базы данных
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/output")
    public void attestationOutput(HttpServletResponse response) throws IOException {
//        List<Post> posts = new ArrayList<>();
//        postRepository.findAll().forEach(posts::add);

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=attestations.xls";
        response.setHeader(headerKey, headerValue);

        attestationService.generateExcelAtt(response, posts);
    }

    @PostMapping("output")
    public void attOutput(@RequestParam List<Post> attestations, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=attestations.xls";
        response.setHeader(headerKey, headerValue);

        attestationService.generateExcelAtt(response, attestations);
    }

    @GetMapping("/attestation")
    public String attestationMain(Principal principal, Model model) {
//        Iterable<Post> posts = postRepository.findAll();
        // Находим авторизованного пользователя и передаем на страницу
        User authuser = userService.getUserByPrincipal(principal);
        model.addAttribute("authuser", authuser);
        // Создаем контейнер searchPost для передачи параметров поиска
        Post searchPost = new Post(authuser.getDepartment(), authuser.getUnit(), "", "", "", "", "", "", null);
        model.addAttribute("searchPost", searchPost);
        // Ищем всех коллег аттестованных и конвертируем Iterable в ArrayList
//        List<Post> posts = new ArrayList<>();
        // Очищаем posts перед заполнением
        if (!posts.isEmpty()) posts.clear();
        postRepository.findByDepartmentContainingAndStructureUnitContainingOrderByDateFirstprotocolDesc(authuser.getDepartment(), authuser.getUnit()).forEach(posts::add);
        // передаем в шаблон все записи из БД
        model.addAttribute("posts", posts);
        // Передаем информацию об открытой странице: 1 - Аттестация, 2 - Договоры
        int page = 1;
        model.addAttribute("page", page);
        // Создаем список для selectDepartment
        model.addAttribute("optionsDepartment", Arrays.stream(DepartmentEnum.values()).toList());
        // Создаем список для selectStructureUnit
        model.addAttribute("optionsStructureUnit", Arrays.stream(StructureUnitEnum.values()).toList());
        // Создаем список для selectProfessionCategory
        model.addAttribute("optionsProfessionCategory", Arrays.stream(ProfessionCategoryEnum.values()).toList());
        return "attestation-main";
    }

    @GetMapping("/attestation/add")
    public String attestationAdd(Principal principal, Model model) {
        // Создаем список для selectDepartment
        model.addAttribute("optionsStructureUnit", Arrays.stream(StructureUnitEnum.values()).toList());
        // Находим авторизованного пользователя и передаем на страницу
        User authuser = userService.getUserByPrincipal(principal);
        model.addAttribute("authuser", authuser);
        return "attestation-add";
    }

    // Отслеживаем страницы с уникальным id в URL
    @GetMapping("/attestation/{id}")
    public String attestationDetails(@PathVariable(value = "id") long id, Principal principal, Model model) {
        // Вводим проверку на наличие данной страницы в БД
        if (!postRepository.existsById(id)) {
            return "redirect:/attestation";
        }
        Optional<Post> post = postRepository.findById(id);
        // Помещаем Optional в ArrayList для удобства работы в шаблоне
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        // Находим авторизованного пользователя и передаем на страницу
        User authuser = userService.getUserByPrincipal(principal);
        model.addAttribute("authuser", authuser);
        return "attestation-details";
    }

    // Страница редактирования записи в БД
    @GetMapping("/attestation/{id}/edit")
    public String attestationEdit(@PathVariable(value = "id") long id, Principal principal, Model model) {
        // Вводим проверку на наличие данной страницы в БД
        if (!postRepository.existsById(id)) {
            return "redirect:/attestation";
        }
        Optional<Post> post = postRepository.findById(id);
        // Помещаем Optional в ArrayList для удобства работы в шаблоне
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        // Создаем список для selectDepartment
        List<String> optionsDepartment = new ArrayList<String>();
        optionsDepartment.add("Служба охраны труда");
        optionsDepartment.add("Служба вагонного хозяйства");
        optionsDepartment.add("Служба пути");
        optionsDepartment.add("Служба автоматики и телемеханики");
        model.addAttribute("optionsDepartment", optionsDepartment);
        // Создаем список для selectStructureUnit
        model.addAttribute("optionsStructureUnit", Arrays.stream(StructureUnitEnum.values()).toList());
        // Создаем список для selectProfessionCategory
        List<String> optionsProfessionCategory = new ArrayList<String>();
        optionsProfessionCategory.add("Руководитель");
        optionsProfessionCategory.add("Специалист");
        model.addAttribute("optionsProfessionCategory", optionsProfessionCategory);
        // Находим авторизованного пользователя и передаем на страницу
        User authuser = userService.getUserByPrincipal(principal);
        model.addAttribute("authuser", authuser);
        return "attestation-edit";
    }

    // Обработка запроса пост
    @PostMapping("/attestation/add")
    public String attestationPostAdd(
            @RequestParam String department,
            @RequestParam String structureUnit,
            @RequestParam String professionCategory,
            @RequestParam String position,
            @RequestParam String surname,
            @RequestParam String name,
            @RequestParam String patronymic,
            @RequestParam String numFirstprotocol,
            @RequestParam String a1_firstprotocol,
            @RequestParam String b83_firstprotocol,
            @RequestParam String b93_firstprotocol,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFirstprotocol,
            Model model
    ) {
        // Создаем объект Post
        Post post = new Post(
                "СКДИ",
                department,
                structureUnit,
                professionCategory,
                position,
                surname,
                name,
                patronymic,
                numFirstprotocol,
                a1_firstprotocol,
                b83_firstprotocol,
                b93_firstprotocol,
                dateFirstprotocol,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        // Сохраняем в БД объект Post
        postRepository.save(post);
        return "redirect:/attestation";
    }

    // Обработка запроса редактирования записи в БД
    @PostMapping("/attestation/{id}/edit")
    public String attestationPostUpdate(
            @PathVariable(value = "id") long id,
            @RequestParam String department,
            @RequestParam String structureUnit,
            @RequestParam String professionCategory,
            @RequestParam String position,
            @RequestParam String surname,
            @RequestParam String name,
            @RequestParam String patronymic,
            @RequestParam String numFirstprotocol,
            @RequestParam String a1_firstprotocol,
            @RequestParam String b83_firstprotocol,
            @RequestParam String b93_firstprotocol,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFirstprotocol,
            Model model
    ) {
        // Создаем объект Post, ссылаясь по id
        Post post = postRepository.findById(id).orElseThrow();
        // заполняем даные в объект
        post.setDepartment(department);
        post.setStructureUnit(structureUnit);
        post.setProfessionCategory(professionCategory);
        post.setPosition(position);
        post.setSurname(surname);
        post.setName(name);
        post.setPatronymic(patronymic);
        post.setNumFirstprotocol(numFirstprotocol);
        post.setA1_firstprotocol(a1_firstprotocol);
        post.setB83_firstprotocol(b83_firstprotocol);
        post.setB93_firstprotocol(b93_firstprotocol);
        post.setDateFirstprotocol(dateFirstprotocol);
        // Сохраняем в БД объект Post
        postRepository.save(post);
        return "redirect:/attestation";
    }

    // Обработка запроса удаления записи в БД
    @PostMapping("/attestation/{id}/remove")
    public String attestationPostDelete(
            @PathVariable(value = "id") long id,
            Model model
    ) {
        // Находим объект Post, ссылаясь по id
        Post post = postRepository.findById(id).orElseThrow();
        // Удаляем объект
        postRepository.delete(post);
        // Редирект на главную по аттестации
        return "redirect:/attestation";
    }


    // Обработка поиска
    @PostMapping("attestationfilter")
    public String filter(
            @RequestParam String department,
            @RequestParam String structureUnit,
            @RequestParam String professionCategory,
            @RequestParam String fullname,
            @RequestParam String position,
            @RequestParam String idprotocol,
            @RequestParam(name = "dateFirstprotocol", defaultValue = "1900-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFirstprotocol,
            @RequestParam(name = "dateFrom", defaultValue = "1900-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
            @RequestParam(name = "dateTo", defaultValue = "2100-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo,
            Principal principal,
            Model model
    ) throws ParseException {
        // Создаем список аттестуемых (пока пустой, затем заполним)
//        Iterable<Post> posts;
        // ПОдготавливаем для дальнейшего сравнения дат
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // Если дата заполнена и заполнена корректно (старше 2019 года), то ищем еще и по дате // dateFirstprotocol.before(formatter.parse("2019-01-01"))
        // Разбиваем Name на Фамилию, имя и отчество для поиска
        String[] names = fullname.split(" ", 3);
        String name = "";
        String patronymic = "";
        if (names.length > 1) {
            name = names[1];
        } else if (names.length > 2) {
            patronymic = names[2];
        }
        // Очищаем posts перед заполнением
        if (!posts.isEmpty()) posts.clear();
        // Если ни одна дата в поле не заполнена, то ищем только по текстовым полям (Фамилия, имя, и .т.д.)
        if (dateFirstprotocol.before(formatter.parse("2000-01-01")) && dateFrom.before(formatter.parse("2000-01-01")) && dateTo.after(formatter.parse("2099-01-01"))) {
            posts = postRepository.findByDepartmentContainingAndStructureUnitContainingAndProfessionCategoryContainingAndSurnameContainingAndNameContainingAndPatronymicContainingAndPositionContainingAndNumFirstprotocolContainingOrderByDateFirstprotocolDesc(
                    department, structureUnit, professionCategory, names[0], name, patronymic, position, idprotocol
            );
        } else if (dateFirstprotocol.after(formatter.parse("2000-01-01")) && dateFrom.before(formatter.parse("2000-01-01")) && dateTo.after(formatter.parse("2099-01-01"))) {
            // Если даты фильтрации С и ПО не заполнены, а заполнена дата протокола, то ведем поиск по одной дате протокола
            posts = postRepository.findByDepartmentContainingAndStructureUnitContainingAndProfessionCategoryContainingAndSurnameContainingAndNameContainingAndPatronymicContainingAndPositionContainingAndNumFirstprotocolContainingAndDateFirstprotocolOrderByDateFirstprotocolDesc(
                    department, structureUnit, professionCategory, names[0], name, patronymic, position, idprotocol, dateFirstprotocol
            );
        } else {
            // Все остальные варианты - когда заполнена хоть одна дата DateFrom или DateTo, всегда будет поиск только по ним
            posts = postRepository.findByDepartmentContainingAndStructureUnitContainingAndProfessionCategoryContainingAndSurnameContainingAndNameContainingAndPatronymicContainingAndPositionContainingAndNumFirstprotocolContainingAndDateFirstprotocolAfterAndDateFirstprotocolBeforeOrderByDateFirstprotocolDesc(
                    department, structureUnit, professionCategory, names[0], name, patronymic, position, idprotocol, dateFrom, dateTo
            );
        }
        // Создаем контейнер searchPost для передачи параметров поиска и если дата == 1900 году, то обнуляем её
        if (dateFirstprotocol.before(formatter.parse("2000-01-01"))) dateFirstprotocol = null;
        Post searchPost = new Post(department, structureUnit, professionCategory, position, names[0], name, patronymic, idprotocol, dateFirstprotocol);
        model.addAttribute("searchPost", searchPost);
        // После нахождения всех записей, отправляем коллекцию на страницу
        model.addAttribute( "posts", posts);
        // Создаем список для selectDepartment
        model.addAttribute("optionsDepartment", Arrays.stream(DepartmentEnum.values()).toList());
        // Создаем список для selectStructureUnit
        model.addAttribute("optionsStructureUnit", Arrays.stream(StructureUnitEnum.values()).toList());
        // Создаем список для selectProfessionCategory
        model.addAttribute("optionsProfessionCategory", Arrays.stream(ProfessionCategoryEnum.values()).toList());
        // Находим авторизованного пользователя и передаем на страницу
        User authuser = userService.getUserByPrincipal(principal);
        model.addAttribute("authuser", authuser);
    return "attestation-main";
    }
}
