package com.siteexample.attestation.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String directorate;
    private String department;
    private String structureUnit;
    private String professionCategory;
    private String position;
    private String surname;
    private String name;
    private String patronymic;
    private String numFirstprotocol;
    private String a1_firstprotocol;
    private String b83_firstprotocol;
    private String b93_firstprotocol;
    private Date dateFirstprotocol;
    private String fileFirstprotocol;
    private String numSecondprotocol;
    private String a1_secondprotocol;
    private String b83_secondprotocol;
    private String b93_secondprotocol;
    private Date dateSecondprotocol;
    private String fileSecondprotocol;
    private Date datePreparation;
    private String userComment;
    private String comment;
    private int views;

    public Post() {
    }

    // Переопределяем конструктор для создания маски поиска


    public Post(String department, String structureUnit, String professionCategory, String position, String surname, String name, String patronymic, String numFirstprotocol, Date dateFirstprotocol) {
        this.department = department;
        this.structureUnit = structureUnit;
        this.professionCategory = professionCategory;
        this.position = position;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.numFirstprotocol = numFirstprotocol;
        this.dateFirstprotocol = dateFirstprotocol;
    }

    public Post(
            String directorate,
            String department,
            String structureUnit,
            String professionCategory,
            String position,
            String surname,
            String name,
            String patronymic,
            String numFirstprotocol,
            String a1_firstprotocol,
            String b83_firstprotocol,
            String b93_firstprotocol,
            Date dateFirstprotocol,
            String fileFirstprotocol,
            String numSecondprotocol,
            String a1_secondprotocol,
            String b83_secondprotocol,
            String b93_secondprotocol,
            Date dateSecondprotocol,
            String fileSecondprotocol,
            Date datePreparation) {
        this.directorate = directorate;
        this.department = department;
        this.structureUnit = structureUnit;
        this.professionCategory = professionCategory;
        this.position = position;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.numFirstprotocol = numFirstprotocol;
        this.a1_firstprotocol = a1_firstprotocol;
        this.b83_firstprotocol = b83_firstprotocol;
        this.b93_firstprotocol = b93_firstprotocol;
        this.dateFirstprotocol = dateFirstprotocol;
        this.fileFirstprotocol = fileFirstprotocol;
        this.numSecondprotocol = numSecondprotocol;
        this.a1_secondprotocol = a1_secondprotocol;
        this.b83_secondprotocol = b83_secondprotocol;
        this.b93_secondprotocol = b93_secondprotocol;
        this.dateSecondprotocol = dateSecondprotocol;
        this.fileSecondprotocol = fileSecondprotocol;
        this.datePreparation = datePreparation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDirectorate() {
        return directorate;
    }

    public void setDirectorate(String directorate) {
        this.directorate = directorate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStructureUnit() {
        return structureUnit;
    }

    public void setStructureUnit(String structureUnit) {
        this.structureUnit = structureUnit;
    }

    public String getProfessionCategory() {
        return professionCategory;
    }

    public void setProfessionCategory(String professionCategory) {
        this.professionCategory = professionCategory;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getNumFirstprotocol() {
        return numFirstprotocol;
    }

    public void setNumFirstprotocol(String numFirstprotocol) {
        this.numFirstprotocol = numFirstprotocol;
    }

    public String getA1_firstprotocol() {
        return a1_firstprotocol;
    }

    public void setA1_firstprotocol(String a1_firstprotocol) {
        this.a1_firstprotocol = a1_firstprotocol;
    }

    public String getB83_firstprotocol() {
        return b83_firstprotocol;
    }

    public void setB83_firstprotocol(String b83_firstprotocol) {
        this.b83_firstprotocol = b83_firstprotocol;
    }

    public String getB93_firstprotocol() {
        return b93_firstprotocol;
    }

    public void setB93_firstprotocol(String b93_firstprotocol) {
        this.b93_firstprotocol = b93_firstprotocol;
    }

    public Date getDateFirstprotocol() {
        return dateFirstprotocol;
    }

    public void setDateFirstprotocol(Date dateFirstprotocol) {
        this.dateFirstprotocol = dateFirstprotocol;
    }

    public String getNumSecondprotocol() {
        return numSecondprotocol;
    }

    public void setNumSecondprotocol(String numSecondprotocol) {
        this.numSecondprotocol = numSecondprotocol;
    }

    public String getA1_secondprotocol() {
        return a1_secondprotocol;
    }

    public void setA1_secondprotocol(String a1_secondprotocol) {
        this.a1_secondprotocol = a1_secondprotocol;
    }

    public String getB83_secondprotocol() {
        return b83_secondprotocol;
    }

    public void setB83_secondprotocol(String b83_secondprotocol) {
        this.b83_secondprotocol = b83_secondprotocol;
    }

    public String getB93_secondprotocol() {
        return b93_secondprotocol;
    }

    public void setB93_secondprotocol(String b93_secondprotocol) {
        this.b93_secondprotocol = b93_secondprotocol;
    }

    public Date getDateSecondprotocol() {
        return dateSecondprotocol;
    }

    public void setDateSecondprotocol(Date dateSecondprotocol) {
        this.dateSecondprotocol = dateSecondprotocol;
    }

    public Date getDatePreparation() {
        return datePreparation;
    }

    public void setDatePreparation(Date datePreparation) {
        this.datePreparation = datePreparation;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getFileFirstprotocol() {
        return fileFirstprotocol;
    }

    public void setFileFirstprotocol(String fileFirstprotocol) {
        this.fileFirstprotocol = fileFirstprotocol;
    }

    public String getFileSecondprotocol() {
        return fileSecondprotocol;
    }

    public void setFileSecondprotocol(String fileSecondprotocol) {
        this.fileSecondprotocol = fileSecondprotocol;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
