package com.siteexample.attestation.repo;

import com.siteexample.attestation.models.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByDepartmentContainingAndStructureUnitContainingOrderByDateFirstprotocolDesc(
            String department,
            String structureUnit);

    List<Post> findByDepartmentContainingAndStructureUnitContainingAndProfessionCategoryContainingAndSurnameContainingAndNameContainingAndPatronymicContainingAndPositionContainingAndNumFirstprotocolContainingOrderByDateFirstprotocolDesc(
            String department,
            String structureUnit,
            String professionCategory,
            String surname,
            String name,
            String patronymic,
            String position,
            String idprotocol);

    List<Post> findByDepartmentContainingAndStructureUnitContainingAndProfessionCategoryContainingAndSurnameContainingAndNameContainingAndPatronymicContainingAndPositionContainingAndNumFirstprotocolContainingAndDateFirstprotocolOrderByDateFirstprotocolDesc(
            String department,
            String structureUnit,
            String professionCategory,
            String surname,
            String name,
            String patronymic,
            String position,
            String idprotocol,
            Date dateFirstprotocol);

    List<Post> findByDepartmentContainingAndStructureUnitContainingAndProfessionCategoryContainingAndSurnameContainingAndNameContainingAndPatronymicContainingAndPositionContainingAndNumFirstprotocolContainingAndDateFirstprotocolAfterAndDateFirstprotocolBeforeOrderByDateFirstprotocolDesc(
            String department,
            String structureUnit,
            String professionCategory,
            String surname,
            String name,
            String patronymic,
            String position,
            String idprotocol,
            Date dateFrom,
            Date dateTo);

    List<Post> findByName(String name);

    List<Post> findByDateFirstprotocol(Date dateFirstprotocol);


}
