package com.cowerling.daytrace.domain.user.form;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

public class UserForm {
    @NotNull
    @Email
    private String email;

    private String password;

    private String photo;

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
