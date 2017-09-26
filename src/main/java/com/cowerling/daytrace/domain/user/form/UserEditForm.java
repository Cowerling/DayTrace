package com.cowerling.daytrace.domain.user.form;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserEditForm {
    @NotNull
    @Size(min = 6, max = 30)
    private String originName;

    @NotNull
    @Size(min = 6, max = 30)
    private String name;

    @NotNull
    private String role;

    private String alias;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 6, max = 30)
    private String password;

    private String gender;
    private String birthday;
    private String phone;
    private String brief;
    private String[] medals;
    private String[] originMedals;
    private int page;

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String[] getMedals() {
        return medals;
    }

    public void setMedals(String[] medals) {
        this.medals = medals;
    }

    public String[] getOriginMedals() {
        return originMedals;
    }

    public void setOriginMedals(String[] originMedals) {
        this.originMedals = originMedals;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
